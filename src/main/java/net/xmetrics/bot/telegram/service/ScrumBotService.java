package net.xmetrics.bot.telegram.service;

import net.xmetrics.bot.telegram.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScrumBotService {

  @Autowired private ChatRepository chatRepository;

  public boolean isChatConfigured(long chatId) {
    return chatRepository.existsById(chatId);
  }

}
