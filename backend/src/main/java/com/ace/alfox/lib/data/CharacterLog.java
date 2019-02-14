package com.ace.alfox.lib.data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

public class CharacterLog {
  @Id public NitriteId id;
  public long playerId;
  public long timestamp;
  public String message;

  CharacterLog() {}

  public CharacterLog(long playerId, String message) {
    this.playerId = playerId;
    this.message = message;
    this.timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
  }
}
