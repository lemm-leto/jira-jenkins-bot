package net.xmetrics.bot.telegram;

import net.xmetrics.bot.telegram.command.Command;
import net.xmetrics.bot.telegram.command.CommandFactory;
import net.xmetrics.bot.telegram.command.exception.CommandException;
import net.xmetrics.bot.telegram.service.ScrumBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Component
public class ScrumBot extends TelegramLongPollingBot {

  private final ScrumBotService scrumBotService;
  private final CommandFactory commandFactory;

  public ScrumBot(
    @Autowired ScrumBotService scrumBotService,
    @Autowired CommandFactory commandFactory
  ) {
    this.scrumBotService = scrumBotService;
    this.commandFactory = commandFactory;
  }

  @Override
  public void onUpdateReceived(Update update) {
    long chatId = Optional.ofNullable(update.getMessage())
      .map(Message::getChatId)
      .orElse(-1L);
    String message = Optional.ofNullable(update.getMessage())
      .map(Message::getText)
      .orElse("/dummy");

    Command command;

    if (scrumBotService.isChatConfigured(chatId)) {
      command = commandFactory.getCommand(message);
    } else if (message.startsWith("/start")) {
      command = commandFactory.getCommand(message);
    } else {
      command = commandFactory.getSendStartCommand();
    }

    BotApiMethod<Message> method;

    try {
      method = command.execute(chatId, update);
    } catch (CommandException e) {
      method = e.getMethod();
    }

    try {
      execute(method);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getBotUsername() {
    return "scrum_helper_bot";
  }

  @Override
  public String getBotToken() {
    return "789727197:AAF4igcxtrpv9k5cbSZo74eUBKDmFIbu7DE";
  }

}
