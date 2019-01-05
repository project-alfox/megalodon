package com.ace.alfox.api;

import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.data.Database;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapApi {

  @Autowired private Database playerDatabase;

  @GetMapping("/api/map")
  public ResponseEntity getMap(HttpServletRequest request) {
    Player player = Player.findPlayer(playerDatabase, request);
    if (player == null) {
      return ResponseEntity.status(401).build();
    }

    // TODO: Return maps based on an object instead of a string.
    // TODO: Return maps that are in a database, and not hard coded here.

    if (player.zoneId == 0) {
      return ResponseEntity.ok(
          "{\"zoneId\":0,\"zoneName\":\"Drax Region\",\"width\":4,\"height\":4,\"tiles\":[{\"tileId\":0,\"x\":0,\"y\":0,\"type\":\"plains\",\"actions\":[]},{\"tileId\":1,\"x\":1,\"y\":0,\"type\":\"plains\",\"actions\":[]},{\"tileId\":2,\"x\":2,\"y\":0,\"type\":\"plains\",\"actions\":[]},{\"tileId\":3,\"x\":1,\"y\":1,\"type\":\"plains\",\"actions\":[]},{\"tileId\":4,\"x\":2,\"y\":1,\"type\":\"forest\",\"actions\":[]},{\"tileId\":5,\"x\":3,\"y\":1,\"type\":\"water\",\"actions\":[]},{\"tileId\":6,\"x\":2,\"y\":2,\"type\":\"forest\",\"actions\":[]},{\"tileId\":7,\"x\":0,\"y\":3,\"type\":\"mountain\",\"actions\":[]},{\"tileId\":8,\"x\":1,\"y\":3,\"type\":\"plains\",\"actions\":[]},{\"tileId\":9,\"x\":2,\"y\":3,\"type\":\"forest\",\"actions\":[]},{\"tileId\":10,\"x\":3,\"y\":3,\"type\":\"forest\",\"actions\":[]}]}");
    } else if (player.zoneId == 1) {
      return ResponseEntity.ok(
          "{\"zoneId\":1,\"zoneName\":\"Nimbus Domain\",\"width\":4,\"height\":4,\"tiles\":[{\"tileId\":0,\"x\":0,\"y\":0,\"type\":\"mountain\",\"actions\":[]},{\"tileId\":1,\"x\":1,\"y\":0,\"type\":\"mountain\",\"actions\":[]},{\"tileId\":2,\"x\":2,\"y\":0,\"type\":\"mountain\",\"actions\":[]},{\"tileId\":3,\"x\":1,\"y\":1,\"type\":\"plains\",\"mountain\":[]},{\"tileId\":4,\"x\":2,\"y\":1,\"type\":\"forest\",\"actions\":[]},{\"tileId\":5,\"x\":3,\"y\":1,\"type\":\"mountain\",\"actions\":[]},{\"tileId\":6,\"x\":2,\"y\":2,\"type\":\"forest\",\"actions\":[]},{\"tileId\":7,\"x\":0,\"y\":3,\"type\":\"mountain\",\"actions\":[]},{\"tileId\":8,\"x\":1,\"y\":3,\"type\":\"plains\",\"actions\":[]},{\"tileId\":9,\"x\":2,\"y\":3,\"type\":\"forest\",\"actions\":[]},{\"tileId\":10,\"x\":3,\"y\":3,\"type\":\"forest\",\"actions\":[]}]}");
    } else {
      return ResponseEntity.ok(
          "{\"zoneId\":2,\"zoneName\":\"Calcite Septum\",\"width\":4,\"height\":4,\"tiles\":[{\"tileId\":0,\"x\":0,\"y\":0,\"type\":\"plains\",\"actions\":[]},{\"tileId\":1,\"x\":1,\"y\":0,\"type\":\"plains\",\"actions\":[]},{\"tileId\":2,\"x\":2,\"y\":0,\"type\":\"plains\",\"actions\":[]},{\"tileId\":3,\"x\":1,\"y\":1,\"type\":\"plains\",\"actions\":[]},{\"tileId\":4,\"x\":2,\"y\":1,\"type\":\"water\",\"actions\":[]},{\"tileId\":5,\"x\":3,\"y\":1,\"type\":\"water\",\"actions\":[]},{\"tileId\":6,\"x\":2,\"y\":2,\"type\":\"forest\",\"actions\":[]},{\"tileId\":7,\"x\":0,\"y\":3,\"type\":\"water\",\"actions\":[]},{\"tileId\":8,\"x\":1,\"y\":3,\"type\":\"water\",\"actions\":[]},{\"tileId\":9,\"x\":2,\"y\":3,\"type\":\"forest\",\"actions\":[]},{\"tileId\":10,\"x\":3,\"y\":3,\"type\":\"forest\",\"actions\":[]}]}");
    }
  }

  @GetMapping("/api/player")
  public ResponseEntity setZoneId(HttpServletRequest request, @RequestParam Integer zoneId) {
    Player player = Player.findPlayer(playerDatabase, request);
    if (player == null) {
      return ResponseEntity.status(401).build();
    }

    player.zoneId = zoneId;
    playerDatabase.players.update(player);
    return ResponseEntity.ok(player);
  }
}
