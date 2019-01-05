package com.ace.alfox;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

import com.ace.alfox.game.MoveAction;
import com.ace.alfox.game.interfaces.IAction;
import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.ActionResult;
import com.ace.alfox.lib.Vector2;
import com.ace.alfox.lib.data.Database;
import java.util.HashMap;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoveActionTests {
  private Player player;
  private IAction move;
  private static Database db;

  @BeforeClass
  public static void createConnection() {
    db = new Database(Database.USE_IN_MEMORY);
  }

  @Before
  public void setUp() {
    player = new Player();
    move = new MoveAction(db);
  }

  @Test
  public void moveNorth_yLocationShouldChange() {
    ActionResult result =
        move.applyAction(
            player,
            new HashMap<String, Object>() {
              {
                put("direction", "north");
              }
            });

    assertArrayEquals(result.player.location.toArray(), new Vector2(0, 1).toArray());
  }

  @Test
  public void invalidMoveDirection_shouldNotMove() {
    ActionResult result =
        move.applyAction(
            player,
            new HashMap<String, Object>() {
              {
                put("direction", "westward");
              }
            });

    assertFalse(result.ok);
    assertArrayEquals(result.player.location.toArray(), new Vector2(0, 0).toArray());
  }

  @AfterClass
  public static void closeUpShop() {
    db.destroy();
  }
}
