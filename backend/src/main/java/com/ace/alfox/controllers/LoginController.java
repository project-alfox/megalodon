package com.ace.alfox.controllers;

import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.data.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class LoginController {

  @Autowired private Database db;

  @GetMapping("/login")
  public ResponseEntity getLoginStatus(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    Long playerId = (Long) session.getAttribute("pid");
    if (playerId == null) {
      return ResponseEntity.status(401).build();
    } else {
      Player player = db.players.find(playerId);
      return ResponseEntity.status(200).body(player);
    }
  }

  /**
   * The login takes a mapped request body of fields mapped to the strings username and password
   * representing a login attempt.
   *
   * @param loginRequest
   * @param request
   * @return
   */
  @PostMapping("/login")
  public ResponseEntity login(
      @RequestBody Map<String, Object> loginRequest, HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    Player p =
        db.players.authenticate(
            loginRequest.get("username").toString(), loginRequest.get("password").toString());
    if (p == null) {
      return ResponseEntity.notFound().build();
    }
    session.setAttribute("pid", p.id);
    return ResponseEntity.ok().build();
  }

  /**
   * Signup takes a similar request to login, checks to see if the username is unique and if it is
   * signs up, then logs the user in.
   *
   * @param signupRequest
   * @param request
   * @return
   */
  @PostMapping("/signup")
  public ResponseEntity signup(
      @RequestBody Map<String, Object> signupRequest, HttpServletRequest request) {
    Player p = db.players.find(signupRequest.get("username").toString());

    if (p == null) {
      p =
          db.players.signUp(
              signupRequest.get("username").toString(), signupRequest.get("password").toString());
      System.out.println("Signed up: " + p.name + " (" + p.id + ")");
      login(signupRequest, request);
    } else {
      System.out.println(p.name + " " + p.salt);
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.noContent().build();
  }
}
