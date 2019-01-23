package net.xmetrics.bot.telegram.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Configuration {
  @Id
  @GeneratedValue
  private Long id;
  @Column
  private String tokenCI;
  @OneToOne
  private Chat chat;
}
