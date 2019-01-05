#### Getting Started

- Install the [JDK10](http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html)
- and run `./gradlew runJar`

To setup the project in IntelliJ, run `./gradlew idea`. This will only need to be run once ever.

#### Using

- To serve the frontend, set output directory to `src/main/resources/static`
- the "API" if you can call it that
  - `GET or POST /login?user=SomeUser`<br>
    This sets the session id to load allow loading a player. Required.
  - `POST /perform/<action>` for example,<br>
  ```javascript
  fetch("http://localhost:8080/perform/move", {
    method: 'post',
    credentials: 'same-origin',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({"direction":"north"})
  })
  // should return 200
  { "message":["traveled north"],
    "player":{
      "name":"Jimmy Fred",
      "hp":100,
      "location":[0,1]
    },
    "ok":true  }
  ```
  
#### Adding New Actions

- Create a new class in `com.ace.alfox.game` with the following template.<br>
```java
package com.ace.alfox.game;

import com.ace.alfox.game.interfaces.IAction;
import com.ace.alfox.lib.PlayerAction;
import com.ace.alfox.lib.ActionResult;

import java.util.Map;

@PlayerAction(alias="your-action-alias")
public class MoveAction implements IAction {
    @Override
    public Result applyAction(GameCharacter player, Map<String, Object> params) {
        Result result = new Result(player);
        // modify player to hearts' content.
        player.hp -= 50;
        return result;
    }
}
```
