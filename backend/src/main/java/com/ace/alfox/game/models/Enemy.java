package com.ace.alfox.game.models;

import com.ace.alfox.lib.Vector2;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

public class Enemy {
  @Id public NitriteId id;

  public String name = "Ferocious Squirrel";
  public int hp = 100;
  public Vector2 location = new Vector2(0, 0);
}
