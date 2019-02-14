package com.ace.alfox.game;

import com.ace.alfox.game.interfaces.IAction;
import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.ActionResult;
import com.ace.alfox.lib.EventManager;
import com.ace.alfox.lib.PlayerAction;
import com.ace.alfox.lib.data.Database;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@PlayerAction(alias = "self")
public class SelfAction implements IAction {

  @Autowired private Database db;
  @Autowired private EventManager eventManager;

  @Override
  public ActionResult applyAction(long playerId, Map<String, Object> params) {
    var result = new ActionResult();
    if (playerId == 0) {
      return result.notOk().log("No player");
    } else {
      Player player = db.players.find(playerId);
      ActionResult status = new ActionResult(player);
      status.message =
          db.logs
              .fetchLogs(player.id, 10)
              .stream()
              .map(log -> log.message)
              .collect(Collectors.toList());
      status.timeTillNextAction = eventManager.getTimeToNextAction(player.id);
      return status;
    }
  }
}
