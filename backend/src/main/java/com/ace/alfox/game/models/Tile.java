package com.ace.alfox.game.models;

import com.ace.alfox.game.TerrainTypes;
import com.ace.alfox.game.TileFlags;
import java.util.ArrayList;

public class Tile {
  public TerrainTypes terraintype = TerrainTypes.PLAINS;
  public ArrayList<Player> playersPresent = new ArrayList<>();
  public ArrayList<Enemy> enemiesPresent = new ArrayList<>();
  public ArrayList<TileFlags> flags = new ArrayList<>();
}
