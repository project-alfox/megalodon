package com.ace.alfox.game;

import com.ace.alfox.game.interfaces.IAction;
import com.ace.alfox.game.models.Player;
import com.ace.alfox.game.models.Player.PlayerState;
import com.ace.alfox.lib.ActionResult;
import com.ace.alfox.lib.Battle;
import com.ace.alfox.lib.PlayerAction;
import com.ace.alfox.lib.data.Database;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

@PlayerAction(alias = "battle")
public class BattleAction implements IAction {
  private final Database db;
  private final Random random = new Random();

  @Autowired
  public BattleAction(Database db) {
    this.db = db;
  }

  @Override
  public ActionResult applyAction(Player player, Map<String, Object> params) {
    ActionResult result = new ActionResult(player);
    if (player.hp <= 0) {
      return result.notOk().log("You're dead. No fighting while dead.");
    }

    if (!player.inBattle()) {
      return result.notOk().log("Not in battle wise guy.");
    }

    String action = (String) params.get("action");
    if (action == null || action.isEmpty()) {
      return result.notOk().log("Action null or empty");
    }

    if (!action.equals("punch")) {
      return result.notOk().log("Only punches are allowed, fool!");
    }

    Battle battle = db.battles.find(player);
    battle.enemyRemainingHealth -= 10;
    player.hp -= random.nextInt(5);
    if (battle.enemyRemainingHealth <= 0) {
      db.battles.remove(battle);
      player.activeBattleId = null;
      player.playerState = PlayerState.FREE;
      return result.log("VICTORY! You have defeated " + battle.enemyName);
    } else {
      db.battles.update(battle);
      return result.log(
          "Remaining hp " + battle.enemyRemainingHealth + " for enemy " + battle.enemyName);
    }
  }
}
