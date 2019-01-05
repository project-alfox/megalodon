package com.ace.alfox.lib.data;

import com.ace.alfox.game.models.Location;
import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.Battle;
import javax.annotation.PreDestroy;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Database {
  public static final String USE_IN_MEMORY = "DON'T PERSIST TO DISK";
  private final Nitrite db;

  public final PlayerRepository players;
  public final LocationRepository locations;
  public final BattleRepository battles;

  /**
   * Open the given database or create it if it doesn't exist. Only one instance can have a database
   * open at a time.
   *
   * @param name The file path to the database, if the passed string is equal to
   *     {Database.USE_IN_MEMORY} the database is initialized in MemoryOnly mode and not persisted
   *     to disk. Useful for tests.
   */
  public Database(@Value(value = "game") String name) {
    NitriteBuilder _db = Nitrite.builder();
    if (!name.equals(USE_IN_MEMORY)) {
      _db = _db.filePath(name + ".db");
    }
    db = _db.openOrCreate();
    players = new PlayerRepository(db.getRepository(Player.class));
    locations = new LocationRepository(db.getRepository(Location.class));
    battles = new BattleRepository(db.getRepository(Battle.class));
  }

  @PreDestroy
  public void destroy() {
    db.close();
  }
}
