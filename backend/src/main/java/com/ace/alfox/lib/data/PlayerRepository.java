package com.ace.alfox.lib.data;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

import com.ace.alfox.game.models.Player;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.ObjectRepository;

public class PlayerRepository extends ObjectRepositoryFacade<Player> {

  private SecureRandom random = new SecureRandom();

  PlayerRepository(ObjectRepository<Player> or) {
    super(or);
  }

  /**
   * * Search for a player going by the given username
   *
   * @param username target username
   * @return The found Player or null if not found
   */
  public Player find(String username) {
    return super.find(eq("name", username)).firstOrDefault();
  }

  /**
   * Search for a player based on their PlayerId, their unique identifier.
   *
   * @param playerId player identifier
   * @return The Player or null if not found.
   */
  public Player find(Long playerId) {
    return super.find(eq("id", playerId)).firstOrDefault();
  }

  /**
   * Search for a player based on their PlayerId, their unique identifier.
   *
   * @param playerId player identifier
   * @return The Player or null if not found.
   */
  public Player find(NitriteId playerId) {
    return super.find(eq("id", playerId)).firstOrDefault();
  }

  /**
   * Authenticate checks the has of the username and password supplied
   *
   * @param username
   * @param password
   * @return
   */
  public Player authenticate(String username, String password) {
    Player player = find(username);
    byte[] test = new byte[0];
    if (player == null) {
      generateHash("lol", "lol".getBytes()); // to protect from timing attacks!
      return null;
    } else if (Arrays.equals(player.password, (test = generateHash(password, player.salt)))) {
      return player;
    } else {
      return null;
    }
  }

  /**
   * signUp functions takes a username and password, guaranteed that username is unique due to how
   * the controller handles it (baring some kind of timing issue?)
   *
   * <p>It generates a salt via SecureRandom, and then generates a hashed password based on the
   * password supplied, and the salt.
   *
   * @param username
   * @param password
   * @return
   */
  public Player signUp(String username, String password) {
    Player newPlayer = new Player();
    newPlayer.name = username;
    newPlayer.salt = new byte[32];
    random.nextBytes(newPlayer.salt);
    newPlayer.password = generateHash(password, newPlayer.salt);
    var result =
        super.insert(
            newPlayer); // Make sure to do some check here to make sure it's inserted! Evan was too
                        // vague.
    System.out.println("inserted " + result.getAffectedCount() + " rows");
    return newPlayer;
  }

  /**
   * generateHash takes MessageDigest and uses SHA-256 to generate hashes of the user salt and
   * password, 10000 times.
   *
   * @param password
   * @param salt
   * @return
   */
  private byte[] generateHash(String password, byte[] salt) {
    try {
      MessageDigest hash = MessageDigest.getInstance("SHA-256");
      hash.update(password.getBytes(StandardCharsets.UTF_8));
      hash.update(salt);
      byte[] tempHash = hash.digest();

      for (int i = 0; i < 10_000; i++) {
        hash.reset();
        tempHash = hash.digest(tempHash);
      }
      return tempHash;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return null;
  }
}
