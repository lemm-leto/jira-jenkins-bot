package net.xmetrics.bot.telegram.command;

import lombok.AllArgsConstructor;
import net.xmetrics.bot.telegram.command.exception.CommandException;
import net.xmetrics.bot.telegram.domain.entity.Chat;
import net.xmetrics.bot.telegram.domain.entity.Configuration;
import net.xmetrics.bot.telegram.repository.ChatRepository;
import net.xmetrics.bot.telegram.repository.ConfigurationRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class JenkinsCommand implements Command {

  private final ChatRepository chatRepository;
  private final ConfigurationRepository configurationRepository;

  @Override
  public BotApiMethod<Message> execute(long chatId, Update update) throws CommandException {
    Chat chat = chatRepository.findById(chatId)
      .orElseThrow(() -> new CommandException(new SendMessage().setChatId(chatId).setParseMode("Markdown").setText("I'm sorry. Your chat is not configured.")));

    Configuration configuration = Optional.ofNullable(chat.getConfiguration())
      .orElseGet(newConfiguration(chat));

    if (configuration.getId() == null) {
      configurationRepository.save(configuration);
    }

    return new SendMessage()
      .setChatId(chatId)
      .setParseMode("Markdown")
      .setText(String.format(
        "Make sure to configure jenkins build to send patch request with following content:\n" +
        "`{\n" +
        "  \"status\": \"RED\"\n" +
        "}`\n" +
        "Where status is enum: `[RED, YELLOW, GREEN]`\n" +
        "Request should be sent to the following url:\n" +
        "`http(s)://host:port/api/v1/jenkins/%s/build/status`",
        configuration.getTokenCI()
      ));
  }

  private Supplier<Configuration> newConfiguration(Chat chat) {
    return () -> new Configuration()
      .setChat(chat)
      .setTokenCI(UUID.randomUUID().toString());
  }
}
