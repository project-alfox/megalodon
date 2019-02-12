package com.ace.alfox.game.models;

import com.ace.alfox.game.TargetTypes;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

public class Ability {
  @Id public NitriteId id;
  public int damageRoll;
  public int damageModifier;
  public TargetTypes targetType;
  public int cooldownTime;
}
