package com.ace.alfox.game;

import com.ace.alfox.lib.data.Database;
import com.ace.alfox.lib.data.Mob;
import java.security.SecureRandom;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TickLoop extends Thread {

  private static final SecureRandom random = new SecureRandom();

  private static final int TWENTY_SECONDS = 20 * 1000;
  private static final int TICK_DELAY = TWENTY_SECONDS;

  @Autowired private Database db;

  @PostConstruct
  private void startThread() {
    // start();
  }

  /**
   * This is not a very good game loop. If the server goes down, you lose the timer. There are a lot
   * of approaches, but for now we're just going to do this dumb simple thing to get it going.
   */
  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(TICK_DELAY);
        System.out.println("HEY LOOK AT ME MOM, NO MAIN LOOP");
        // Is this the best way to itereate over all the mobs?
        db.mobs
            .getDocumentCollection()
            .find()
            .forEach(
                mobData -> {
                  // Should mobs be dumb and just provide get/set, and there is something else that
                  // controls them?
                  // The benefit is that you can define interactions between things (mobs and
                  // players. mobs and the map, etc) without the mobs or players or map knowing
                  // anything about it.
                  Mob mob = db.mobs.getById(mobData.getId());
                  mob.move(jitter(), jitter());
                  db.mobs.update(mob);
                  System.out.print(mob);
                  System.out.print(" ");
                  System.out.println(mob.location);
                });
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /** Random number between [-1, 1] */
  private int jitter() {
    return random.nextInt(3) - 1;
  }
}
