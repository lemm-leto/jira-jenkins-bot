package net.xmetrics.bot.telegram.command;

import net.xmetrics.bot.telegram.command.exception.CommandException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class JenkinsCommand implements Command {
  @Override
  public BotApiMethod<Message> execute(long chatId, Update update) throws CommandException {
    return null;
  }
}
