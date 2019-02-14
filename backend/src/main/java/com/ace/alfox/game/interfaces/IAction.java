package com.ace.alfox.game.interfaces;

import com.ace.alfox.lib.ActionResult;
import java.util.Map;

public interface IAction {
  ActionResult applyAction(long player, Map<String, Object> params);
}
