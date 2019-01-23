package net.xmetrics.bot.telegram.command;

import net.xmetrics.bot.telegram.command.exception.CommandException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelpCommand implements Command {
  @Override
  public BotApiMethod<Message> execute(long chatId, Update update) throws CommandException {
    return new SendMessage()
      .setChatId(chatId)
      .setParseMode("Markdown")
      .setText(
        "Available commands are:\n" +
        "_/help_. Displays available commands\n" +
        "_/start_ `{team_name}`. Configures bot to work with provided team name\n" +
        "_/team_. Displays information about the team\n"
      );
  }
}
