package net.xmetrics.bot.telegram.command;

import net.xmetrics.bot.telegram.command.exception.CommandException;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
  BotApiMethod<Message> execute(long chatId, Update update) throws CommandException;
}
