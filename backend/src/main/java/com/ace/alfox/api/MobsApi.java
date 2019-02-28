package com.ace.alfox.api;

import com.ace.alfox.lib.data.Database;
import com.ace.alfox.lib.data.Mob;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/** Provides the frontend access to all the currently existing mobs in the zone. */
@RestController
public class MobsApi {

  @Autowired private Database mobsDatabase;

  // For now we ignore the zone id
  @GetMapping("/api/mobs/zone/{zoneId}")
  public ResponseEntity getMobsInZone(HttpServletRequest request, @PathVariable int zoneId) {
    List<Mob> mobs = mobsDatabase.mobs.findAll();
    return ResponseEntity.ok(
        mobs.stream().map(mob -> new MobResult(mob.location.x(), mob.location.y())));
  }

  public static class MobResult {
    private final int x;
    private final int y;

    private MobResult(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }
  }
}
