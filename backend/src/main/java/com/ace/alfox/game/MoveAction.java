package com.ace.alfox.game;

import com.ace.alfox.game.interfaces.IAction;
import com.ace.alfox.game.models.Location;
import com.ace.alfox.lib.ActionResult;
import com.ace.alfox.lib.EventManager;
import com.ace.alfox.lib.PlayerAction;
import com.ace.alfox.lib.Vector2;
import com.ace.alfox.lib.data.CharacterLog;
import com.ace.alfox.lib.data.Database;
import java.time.Duration;
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
  private final EventManager eventManager;

  @Autowired
  public MoveAction(Database db, EventManager manager) {
    this.eventManager = manager;
    this.db = db;
  }

  @Override
  public ActionResult applyAction(long playerId, Map<String, Object> params) {
    ActionResult result = new ActionResult();
    String direction = (String) params.get("direction");

    if (direction == null || !directions.containsKey(direction)) {
      return result.notOk().log("Unknown direction"); // notice .log returns `ActionResult`
    }

    var coolDown = eventManager.getTimeToNextAction(playerId);
    if (coolDown > 0) {
      result.timeTillNextAction = coolDown;
      return result.notOk().log("Wait for your action");
    }

    eventManager.schedule(
        Duration.ofSeconds(5),
        () -> {
          var player = this.db.players.find(playerId);
          if (player.hp <= 0) {
            this.db.logs.insert(new CharacterLog(player.id, "You cannot move, you are dead."));
            return;
          }
          // player.attacking.clear() // if they were attacking anyone, they aren't anymore
          // maybe do running away mechanic here?
          player.location = player.location.add(directions.get(direction));
          this.db.logs.insert(new CharacterLog(player.id, "traveled " + direction));

          Location location = db.locations.find(player.location);
          this.db.logs.insert(
              new CharacterLog(player.id, "You see, " + location.title),
              new CharacterLog(player.id, location.description));
          this.db.players.update(player);
        },
        () -> playerId);

    result.timeTillNextAction = eventManager.getTimeToNextAction(playerId);
    return result;
  }
}
