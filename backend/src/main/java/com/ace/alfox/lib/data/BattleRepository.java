package com.ace.alfox.lib.data;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

import com.ace.alfox.game.models.Player;
import com.ace.alfox.lib.Battle;
import java.util.Random;
import org.dizitart.no2.objects.ObjectRepository;

public class BattleRepository extends ObjectRepositoryFacade<Battle> {

  private static final String[] RANDOM_NAMES = {
    "Pikachu",
    "Trogdor",
    "Cloud Strife",
    "Mr Mime",
    "Evan's Beard",
    "Toenail Fungas",
    "Mrs Potato Head"
  };
  private final Random random = new Random();

  BattleRepository(ObjectRepository<Battle> battleObjectRepository) {
    super(battleObjectRepository);
  }

  /** * Create a new Battle. */
  public Battle newBattle() {
    Long id = random.nextLong();
    Battle battle = new Battle(id, getRandomName());
    super.insert(battle);
    return battle;
  }

  private String getRandomName() {
    return RANDOM_NAMES[random.nextInt(RANDOM_NAMES.length)];
  }

  /** Find the battle for this player. */
  public Battle find(Player player) {
    return super.find(eq("id", player.activeBattleId)).firstOrDefault();
  }
}
