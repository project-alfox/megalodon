package com.ace.alfox.lib;

import com.ace.alfox.game.models.Player;
import java.util.LinkedList;
import java.util.List;

public class ActionResult {
  public List<String> message = new LinkedList<>();
  public Player player = null;
  public Battle activeBattle = null;
  public boolean ok = true;
  public long timeTillNextAction = 0;

  public ActionResult() {}

  public ActionResult(Player player) {
    this.player = player;
  }

  // TODO: make proper immutable
  public ActionResult log(String s) {
    message.add(s);
    return this;
  }

  public ActionResult notOk() {
    ok = false;
    return this;
  }
}
