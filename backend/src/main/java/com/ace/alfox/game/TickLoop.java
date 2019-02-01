package com.ace.alfox.game;

import com.ace.alfox.lib.data.Database;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TickLoop extends Thread {
  private static final int TWENTY_SECONDS = 20 * 1000;
  private static final int TICK_DELAY = TWENTY_SECONDS;

  @Autowired private Database db;

  @PostConstruct
  private void startThread() {
    run();
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
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
