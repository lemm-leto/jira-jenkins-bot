package net.xmetrics.bot.telegram.command;

import net.xmetrics.bot.jira.api.TempoRestClient;
import net.xmetrics.bot.jira.dto.MemberDTO;
import net.xmetrics.bot.telegram.command.exception.CommandException;
import net.xmetrics.bot.telegram.domain.entity.Chat;
import net.xmetrics.bot.telegram.domain.entity.Person;
import net.xmetrics.bot.telegram.domain.entity.Team;
import net.xmetrics.bot.telegram.repository.ChatRepository;
import net.xmetrics.bot.telegram.repository.PersonRepository;
import net.xmetrics.bot.telegram.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class TeamCommand implements Command {

  @Autowired private TeamRepository teamRepository;
  @Autowired private ChatRepository chatRepository;
  @Autowired private PersonRepository personRepository;
  @Autowired private TempoRestClient tempoRestClient;

  @Override
  public BotApiMethod<Message> execute(long chatId, Update update) throws CommandException {
    Chat chat = chatRepository.findById(chatId)
      .orElseThrow(() -> new CommandException(new SendMessage().setChatId(chatId).setParseMode("Markdown").setText("I'm sorry. Your chat is not configured.")));

    Team team = chat.getTeam();
    if (team.getPersons() == null || team.getPersons().isEmpty()) {
      team.setPersons(
        StreamSupport.stream(tempoRestClient.findMembers(team.getId()).claim().spliterator(), false)
          .filter(MemberDTO::isAvailability)
          .map(m -> new Person()
            .setId(m.getId())
            .setName(m.getName())
            .setAvatar(m.getAvatar())
            .setRole(m.getRole())
            .setSince(m.getFrom())
            .setTo(m.getTo())
          )
          .collect(Collectors.toSet())
      );

      teamRepository.save(team);

      team.getPersons().forEach(
        p -> personRepository.save(p.setTeam(team))
      );
    }

    return new SendMessage()
      .setChatId(chatId)
      .setParseMode("Markdown")
      .setText(getText(team));
  }

  private String getText(Team team) {
    return String.format(
      "*%s* led by *%s*\n\n" + getPersons(team.getPersons()),
      team.getName(),
      team.getLead()
    );
  }

  private String getPersons(Set<Person> persons) {
    StringBuilder sb = new StringBuilder();

    persons.forEach(p -> sb.append(String.format("*%s*. %s\n", p.getName(), p.getRole())));

    return sb.toString();
  }
}
