package com.ace.alfox.game;

import com.ace.alfox.game.interfaces.IAction;
import com.ace.alfox.game.models.Location;
import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.ActionResult;
import com.ace.alfox.lib.Battle;
import com.ace.alfox.lib.PlayerAction;
import com.ace.alfox.lib.Vector2;
import com.ace.alfox.lib.data.Database;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

@PlayerAction(alias = "move")
public class MoveAction implements IAction {
  private static final HashMap<String, Vector2> directions =
      new HashMap<>() {
        {
          put("north", new Vector2(0, 1));
          put("west", new Vector2(-1, 0));
          put("east", new Vector2(1, 0));
          put("south", new Vector2(0, -1));
        }
      };

  private final Database db;
  private final Random random = new Random();

  @Autowired
  public MoveAction(Database db) {
    this.db = db;
  }

  @Override
  public ActionResult applyAction(Player player, Map<String, Object> params) {
    ActionResult result = new ActionResult(player);

    /**
     * TODO: Consider whether dead players should be a state, or perhaps the "player.canMove()"
     * method would just check this. Might make the most sense for PlayerState to be PRIVATE to the
     * player, and not interacted with directly by anything but the Player class.
     */
    if (player.hp <= 0) {
      return result.notOk().log("You're dead.");
    }

    String direction = (String) params.get("direction");

    if (direction == null || !directions.containsKey(direction)) {
      return result.notOk().log("Unknown direction"); // notice .log returns `ActionResult`
    }

    // player.attacking.clear() // if they were attacking anyone, they aren't anymore
    // maybe do running away mechanic here?
    player.location = player.location.add(directions.get(direction));
    result.log("traveled " + direction);

    Location location = db.locations.find(player.location);
    result.log("You see, " + location.title).log(location.description);

    return result;
  }

  private String getBattleLog(Battle battle, Location location) {
    return "Player is in a battle at " + location.title + " against enemy " + battle.enemyName;
  }
}
