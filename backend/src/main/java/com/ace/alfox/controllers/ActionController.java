package com.ace.alfox.controllers;

import com.ace.alfox.game.interfaces.IAction;
import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.ActionFactory;
import com.ace.alfox.lib.ActionResult;
import com.ace.alfox.lib.data.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
class ActionController {

  @Autowired private Database db;
  @Autowired private ActionFactory actionFactory;

  @RequestMapping(
    value = "/perform/{command}",
    method = {RequestMethod.GET, RequestMethod.POST}
  )
  public ResponseEntity performCommand(
      HttpServletRequest request,
      @PathVariable String command,
      @RequestBody Map<String, Object> params) {
    HttpSession session = request.getSession(true);
    Long playerId = (Long) session.getAttribute("pid");
    if (playerId == null) {
      return ResponseEntity.status(401).build();
    }
    Player player = db.players.find(playerId);
    if (player == null) {
      return ResponseEntity.notFound().build();
    }
    IAction doSomething = actionFactory.get(command);
    ActionResult result = player.applyAction(doSomething, params);
    db.players.update(player);

    return ResponseEntity.ok(result);
  }
}
