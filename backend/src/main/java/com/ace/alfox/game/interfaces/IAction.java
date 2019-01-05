package com.ace.alfox.game.interfaces;

import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.ActionResult;
import java.util.Map;

public interface IAction {
  ActionResult applyAction(Player player, Map<String, Object> params);
}
