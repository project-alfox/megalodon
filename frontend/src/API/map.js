import { uri } from './index';

export function getMap() {
  return fetch(`${uri}/api/map`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    },
    credentials: 'include'
  }).then(result => {
    if (result.status < 400) {
      return result.json();
    } else {
      throw result;
    }
  })
}

export function changeZone(zoneId) {
  return fetch(`${uri}/api/player?zoneId=${zoneId}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    },
    credentials: 'include'
  });
}

// Keeping this around to visualize what the json looks like.
export function getMap2() {
  // This is a hard coded response for now until the backend is done. Essentially it returns a map like this:
  // 3|  M P F F
  // 2|      F
  // 1|    P F W
  // 0|  P P P
  // \----------
  //     0 1 2 3
  // M = mountain, P = plains, F = forest, W = water, and everything else is not walkable.
  return {
    "zoneId": 0,
    "zoneName": "Drax Region",
    "width": 4,
    "height": 4,
    "tiles": [
      { "tileId": 0, "x": 0, "y": 0, "type": "plains", "actions": [] },
      { "tileId": 1, "x": 1, "y": 0, "type": "plains", "actions": [] },
      { "tileId": 2, "x": 2, "y": 0, "type": "plains", "actions": [] },
      { "tileId": 3, "x": 1, "y": 1, "type": "plains", "actions": [] },
      { "tileId": 4, "x": 2, "y": 1, "type": "forest", "actions": [] },
      { "tileId": 5, "x": 3, "y": 1, "type": "water", "actions": [] },
      { "tileId": 6, "x": 2, "y": 2, "type": "forest", "actions": [] },
      { "tileId": 7, "x": 0, "y": 3, "type": "mountain", "actions": [] },
      { "tileId": 8, "x": 1, "y": 3, "type": "plains", "actions": [] },
      { "tileId": 9, "x": 2, "y": 3, "type": "forest", "actions": [] },
      { "tileId": 10, "x": 3, "y": 3, "type": "forest", "actions": [] },
    ]
  };
}
