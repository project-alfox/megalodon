package com.ace.alfox.lib.data;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

import java.util.List;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.SortOrder;
import org.dizitart.no2.objects.ObjectRepository;

public class LogRepository extends ObjectRepositoryFacade<CharacterLog> {

  public LogRepository(ObjectRepository<CharacterLog> or) {
    super(or);
  }

  public List<CharacterLog> fetchLogs(long player, int maxResults) {
    return super.find(
            eq("playerId", player),
            FindOptions.sort("timestamp", SortOrder.Descending).thenLimit(0, maxResults))
        .toList();
  }
}
