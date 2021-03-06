package com.ace.alfox.game.models;

import com.ace.alfox.lib.Vector2;
import com.ace.alfox.lib.data.Database;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.dizitart.no2.objects.Id;

public class Player {
  @Id public long id;
  public String name = "Jimmy Fred";
  public int hp = 100;
  public int maxHP = 100;
  public int zoneId = 0;
  public Vector2 location = new Vector2(0, 0);
  // Neither of these should be in player, they are user attributes, but no user class
  // exists as of time of addition
  public byte[] password = new byte[0];
  public byte[] salt = new byte[0];
  public int cooldown = 0;
  public HashMap<Integer, Ability> abilities = new HashMap<>();

  // @FIXME: We really shouldn't have DB or network stuff in the data classes.
  public static Player findPlayer(Database playerDatabase, HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    Long playerId = (Long) session.getAttribute("pid");

    if (playerId == null) {
      return null;
    }
    return playerDatabase.players.find(playerId);
  }
}
