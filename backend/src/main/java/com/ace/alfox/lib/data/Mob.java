package com.ace.alfox.lib.data;

import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

public class Mob {
  @Id public NitriteId id;

  public static Mob randomMob() {
    return new Mob();
  }
}
