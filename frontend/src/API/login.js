
import {uri} from './index'


function status() {

  return fetch(`${uri}/login`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    },
    credentials: 'include'
  }).then(res => {
    if(res.status < 400) return res.json()
    else throw res
  })
}

function loginAs(username) {
  return fetch(`${uri}/login?user=${username}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    credentials: 'include'
  }).then(res => status())
}

export default {
  status, loginAs
}
