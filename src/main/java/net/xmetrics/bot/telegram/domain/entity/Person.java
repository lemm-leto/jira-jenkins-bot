package net.xmetrics.bot.telegram.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
public class Person {
  @Id
  private int id;
  @Column
  private String name;
  @Lob
  private byte[] avatar;
  @Column
  private String role;
  @Column
  private Date since;
  @Column
  private Date to;
  @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
  private Team team;
}
