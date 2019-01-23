package net.xmetrics.bot.telegram.command;

import net.xmetrics.bot.jira.api.TempoRestClient;
import net.xmetrics.bot.telegram.command.exception.CommandException;
import net.xmetrics.bot.telegram.domain.entity.Chat;
import net.xmetrics.bot.telegram.domain.entity.Team;
import net.xmetrics.bot.telegram.repository.ChatRepository;
import net.xmetrics.bot.telegram.repository.TeamRepository;
import net.xmetrics.bot.telegram.service.ScrumBotService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class StartCommand implements Command {

  private final ScrumBotService scrumBotService;
  private final TempoRestClient tempoRestClient;
  private final ChatRepository chatRepository;
  private final TeamRepository teamRepository;

  public StartCommand(
    @Autowired ScrumBotService scrumBotService,
    @Autowired ChatRepository chatRepository,
    @Autowired TempoRestClient tempoRestClient,
    @Autowired TeamRepository teamRepository) {
    this.scrumBotService = scrumBotService;
    this.chatRepository = chatRepository;
    this.tempoRestClient = tempoRestClient;
    this.teamRepository = teamRepository;
  }

  @Override
  public BotApiMethod<Message> execute(long chatId, Update update) throws CommandException {
    if (scrumBotService.isChatConfigured(chatId)) {
      return new SendMessage()
        .setChatId(chatId)
        .setParseMode("Markdown")
        .setText("I'm sorry. Your chat has already been *configured*.");
    }

    String teamName = update.getMessage()
      .getText()
      .replaceAll("/start ", "");

    Team team = StreamSupport.stream(tempoRestClient.findTeams().claim().spliterator(), false)
      .filter(t -> StringUtils.equals(teamName, t.getName()))
      .findFirst()
      .map(t -> new Team()
        .setId(t.getId())
        .setName(t.getName())
        .setLead(t.getLead())
      )
      .orElseThrow(() -> new CommandException(
        new SendMessage()
        .setChatId(chatId)
        .setParseMode("Markdown")
        .setText(String.format("I'm sorry. Your team *%s* cannot be found in Tempo plugin. Please check you jira connectivity configuration", teamName))
      ));

    Chat chat = new Chat();
    chat.setChatId(chatId);
    chat.setTeamName(teamName);
    chat.setTeam(team);
    team.setChat(chat);

    chatRepository.save(chat);
    teamRepository.save(team);

    return new SendMessage()
      .setChatId(chatId)
      .setParseMode("Markdown")
      .setText(String.format("Your team *%s* has been successfully registered", teamName));
  }
}
