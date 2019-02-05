package com.ace.alfox.game.models;

import com.ace.alfox.game.MonsterAITypes;
import com.ace.alfox.lib.Vector2;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

public class Enemy {
  @Id public NitriteId id;

  public String name = "Ferocious Squirrel";
  public int hp = 100;
  public int maxHP = 100;
  public int zoneId = 0;
  public Vector2 location = new Vector2(0, 0);
  public int damageRoll = 8;
  public int damageModifier = 1;
  public MonsterAITypes aiTypes = MonsterAITypes.PASSIVE;
  public int cooldown = 0;
}
