package net.xmetrics.bot.jira.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamDTO {
  private int id;
  private String name;
  private String lead;
}
