package com.ace.alfox.lib;

import org.dizitart.no2.objects.Id;

public class Battle {
  @Id public Long id;
  public String enemyName = null;
  public String enemyAttack = null;
  public Integer enemyRemainingHealth = 100;

  /** This is unused directly, but is used by the DB layer so don't remvoe or things will break. */
  public Battle() {}

  public Battle(Long id, String enemyName) {
    this.id = id;
    this.enemyName = enemyName;
  }
}
