package com.ace.alfox.game.models;

import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

public class Zone {
    @Id
    public NitriteId zoneID;
    Tile[][] map;
}


