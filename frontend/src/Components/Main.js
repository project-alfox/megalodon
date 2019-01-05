import React, { Component } from 'react';
import { perform } from '../API'
import { getMap, changeZone } from '../API/map'
import './Main.css';
import Login from './Login';
import _ from "underscore";

class Main extends Component {

  state = {
    map: null,
    player: null,
    message: ''
  };

  async refreshMap(player) {
    if (player == null) {
      return;
    }
    const response = await getMap();
    const { height, width, tiles } = response;
    const dataTiles = _.range(0, width).map(col => _.range(0, height).map(value => null));
    tiles.forEach(tile => {
      dataTiles[height - tile.y - 1][tile.x] = {
        type: tile.type,
      };
    });
    return { dataTiles, height, width };
  }

  async componentWillMount() {
    const map = await this.refreshMap(this.state.player);
    if (map !== null) {
      this.setState(map);
    }
  }

  async move(direction) {
    let results = await perform('move', { direction }).then(res => res.json());
    if (results.ok) {
      this.setState({ player: results.player, message: results.message })
    } else if (results.message != null) {
      this.setState({ message: results.message })
    }
  }

  async changeZone() {
    const { player } = this.state;
    if (player == null) {
      return;
    }

    await changeZone((player.zoneId + 1) % 3).then(async result => {
      if (result.status < 400) {
        const newPlayer = await result.json();
        const map = await this.refreshMap(newPlayer);
        this.setState({ player: newPlayer, map });
      } else {
        console.log("Unable to change zone");
      }
    })
  }

  async punch() {
    let results = await perform('battle', { action: 'punch' }).then(res => res.json());
    if (results.ok) {
      this.setState({ player: results.player, message: results.message })
    } else if (results.message != null) {
      this.setState({ message: results.message })
    }
  }

  renderCol = (row) => {
    if (row === null) {
      return <div className="map-col" />;
    } else if (row.type === 'mountain') {
      return <div className="map-col mountain">M</div>;
    } else if (row.type === 'plains') {
      return <div className="map-col plains">P</div>;
    } else if (row.type === 'forest') {
      return <div className="map-col forest">F</div>;
    } else if (row.type === 'water') {
      return <div className="map-col water">W</div>
    }
  };

  renderRow = (row) => <div className="map-row">{row.map(col => this.renderCol(col))}</div>;

  render() {
    const { player } = this.state;
    return (
      <div className="main-container">
        <div className="stats">
          Stats
          <Login onLogin={async player => {
            const map = await this.refreshMap(player);
            this.setState({ player, map });
          }} />
        </div>
        <div className="battle">
          <div>Fight fight fight!</div>
          {player != null && player.playerState === "IN_BATTLE" &&
          <button onClick={event => this.punch()}>PUNCH</button>
          }
        </div>
        <div className="map-area">
          {this.state.map != null &&
          <div className="map-container">
            {this.state.map.dataTiles.map(row => this.renderRow(row))}
          </div>
          }
          {this.state.player && this.state.player.location ?
            <div className="location">Map (x:{this.state.player.location[0]},
              y:{this.state.player.location[1]})</div> :
            false}
          <div className="navigation">
            <button onClick={event => this.move('west')}>West</button>
            <button onClick={event => this.move('north')}>North</button>
            <button onClick={event => this.move('south')}>South</button>
            <button onClick={event => this.move('east')}>East</button>
            <button onClick={event => this.changeZone()}>Next Zone</button>
          </div>
        </div>
        <div
          className="unused">{JSON.stringify(this.state.player)} {JSON.stringify(this.state.message)}</div>
      </div>
    );
  }
}

export default Main;
