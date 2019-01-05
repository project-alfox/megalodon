package com.ace.alfox.game.models;

import com.ace.alfox.game.interfaces.IAction;
import com.ace.alfox.lib.ActionResult;
import com.ace.alfox.lib.Vector2;
import com.ace.alfox.lib.data.Database;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.dizitart.no2.objects.Id;

public class Player {

  public static Player findPlayer(Database playerDatabase, HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    Long playerId = (Long) session.getAttribute("pid");

    if (playerId == null) {
      return null;
    }
    return playerDatabase.players.find(playerId);
  }

  @Id public Long id;
  public String name = "Jimmy Fred";
  public int hp = 100;
  public int zoneId = 0;
  public Vector2 location = new Vector2(0, 0);
  public PlayerState playerState = PlayerState.FREE;
  public Long activeBattleId = null;

  @JsonIgnore
  public byte[] password =
      new byte
          [0]; // Neither of these should be in player, they are user attributes, but no user class
  // exists as of time of addition

  @JsonIgnore public byte[] salt = new byte[0];

  public ActionResult applyAction(IAction doSomething, Map<String, Object> params) {
    return doSomething.applyAction(this, params);
  }

  public boolean canMove() {
    return PlayerState.FREE.equals(playerState);
  }

  public boolean inBattle() {
    return PlayerState.IN_BATTLE.equals(playerState);
  }

  public enum PlayerState {
    FREE,
    IN_BATTLE
  }
}
