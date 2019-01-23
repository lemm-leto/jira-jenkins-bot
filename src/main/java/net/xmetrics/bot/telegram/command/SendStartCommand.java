package net.xmetrics.bot.telegram.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SendStartCommand implements Command {

  @Override
  public BotApiMethod<Message> execute(long chatId, Update update) {
    return new SendMessage()
      .setChatId(chatId)
      .setParseMode("Markdown")
      .setText("I'm sorry but I can't find your chat registered.\n" +
        "Please register using following syntax:\n" +
        "/start *{team_name}*\n" +
        "For example:\n" +
        "/start My Amazing TeamDTO\n" +
        "*team_name* comes from Tempo plugin.");
  }

}
