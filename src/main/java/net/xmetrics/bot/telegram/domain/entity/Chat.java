package net.xmetrics.bot.telegram.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Chat {
  @Id
  private long chatId;
  @Column
  private String teamName;
  @OneToOne(targetEntity = Team.class, mappedBy = "chat", cascade = CascadeType.ALL)
  private Team team;
}
