package net.xmetrics.bot.telegram.command.exception;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public class CommandException extends Exception {

  private final BotApiMethod<Message> method;

  public CommandException(BotApiMethod<Message> method) {
    this.method = method;
  }

  public BotApiMethod<Message> getMethod() {
    return method;
  }
}
