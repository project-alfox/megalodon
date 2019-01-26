
import {uri} from './index'

let isLoggedIn = false

function status() {
  return fetch(`${uri}/login`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    },
    credentials: 'include'
  }).then(res => {
    if(res.status < 400) {
      isLoggedIn = true
      return res.json()
    } else {
      isLoggedIn = false
      throw res
    }
  })
}

function loginAs(username, password) {
  return fetch(`${uri}/login`, {
    method: 'POST',
    body: JSON.stringify({username, password}),
    headers: {
      'Content-Type': 'application/json'
    },
    credentials: 'include'
  }).then(res => status())
}

function signup(username, password) {
  return fetch(`${uri}/signup`, {
    method: 'POST',
    body: JSON.stringify({username, password}),
    headers: {
      'Content-Type': 'application/json'
    },
    credentials: 'include'
  }).then(res => {
    if(res.status < 400) {
      return res.json()
    } else {
      throw res
    }
  })
}

export default {
  status, loginAs, signup, isLoggedIn
}
