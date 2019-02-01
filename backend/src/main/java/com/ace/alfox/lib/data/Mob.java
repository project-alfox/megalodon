package com.ace.alfox.lib.data;

import com.ace.alfox.lib.Vector2;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

public class Mob {
  @Id public NitriteId id;
  public Vector2 location = new Vector2(0, 0);

  public static Mob randomMob() {
    return new Mob();
  }

  public void move(int xDelta, int yDelta) {
    location.setX(location.x() + xDelta).setY(location.y() + yDelta);
  }
}
