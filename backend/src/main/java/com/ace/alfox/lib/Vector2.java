package com.ace.alfox.lib;

import java.util.Vector;

public class Vector2 extends Vector<Integer> implements Comparable<Vector2> {
  public Vector2() {
    super();
  }

  public Vector2(int[] array) {
    this(array[0], array[1]);
  }

  public Vector2(int x, int y) {
    super(2, 0);
    add(x);
    add(y);
  }

  public Vector2 add(Vector2 vector) {
    return new Vector2(get(0) + vector.get(0), get(1) + vector.get(1));
  }

  public int x() {
    return this.get(0);
  }

  public int y() {
    return this.get(1);
  }

  @Override
  public int compareTo(Vector2 o) {
    return Math.abs(o.x() - x()) + Math.abs((o.y() - y()));
  }
}
