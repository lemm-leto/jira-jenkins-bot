package net.xmetrics.bot.telegram.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandFactory {

  @Autowired private ApplicationContext applicationContext;

  private Map<String, Class<? extends Command>> commands = new HashMap<>();

  public CommandFactory() {
    commands.put("/start", StartCommand.class);
    commands.put("/team", TeamCommand.class);
    commands.put("/help", HelpCommand.class);
  }

  public Command getSendStartCommand() {
    return applicationContext.getBean(SendStartCommand.class);
  }

  public Command getCommand(String message) {
    int spaceIndex = message.indexOf(' ');
    String command = message.substring(0, spaceIndex > -1 ? spaceIndex : message.length());

    return applicationContext.getBean(commands.getOrDefault(command, DummyCommand.class));
  }

}
