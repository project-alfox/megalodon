package com.ace.alfox.lib.data;

import java.util.List;
import java.util.stream.IntStream;
import org.dizitart.no2.objects.ObjectRepository;

public class MobRepository extends ObjectRepositoryFacade<Mob> {

  private static final int DESIRED_MOB_COUNT = 3;

  MobRepository(ObjectRepository<Mob> objectRepository) {
    super(objectRepository);
    int mobCount = (int) getDocumentCollection().size();
    System.out.println(mobCount);
    // If there are less than 3 mobs on startup, let's create some more!
    IntStream.range(0, Math.max(0, DESIRED_MOB_COUNT - mobCount))
        .forEach(index -> insert(Mob.randomMob()));
  }

  public List<Mob> findAll() {
    return find().toList();
  }
}
