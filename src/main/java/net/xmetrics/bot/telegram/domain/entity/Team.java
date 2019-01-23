package net.xmetrics.bot.telegram.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Team {
  @Id
  private int id;
  @Column
  private String name;
  @Column
  private String lead;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "team", targetEntity = Person.class, fetch = FetchType.EAGER)
  private Set<Person> persons;
  @OneToOne(cascade = CascadeType.ALL)
  private Chat chat;
}
