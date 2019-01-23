package net.xmetrics.bot.jira.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class MemberDTO {
  private int id;
  private String name;
  private String avatarUrl;
  private byte[] avatar;
  private String role;
  private Date from;
  private Date to;
  private boolean availability;
}
