package com.ace.alfox.lib.data;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

import com.ace.alfox.game.models.Location;
import com.ace.alfox.lib.Vector2;
import org.dizitart.no2.objects.ObjectRepository;

public class LocationRepository extends ObjectRepositoryFacade<Location> {

  LocationRepository(ObjectRepository<Location> or) {
    super(or);
  }

  public Location find(Vector2 coordinates) {
    Location result = super.find(eq("coordinates", coordinates)).firstOrDefault();
    if (result == null) {
      // HACK I should look up the zone this location is in and provide the default
      result = new Location();
    }
    return result;
  }
}
