export const uri = process.env.REACT_APP_API_ENDPOINT || 'http://localhost:8080'

  /**
   * Perform an action on the server
   * @param {string} action The reducer to run on the server.
   * @param {object} parameters Passed directly to the server
   * @return {Promise} The HTTP request
   */
export function perform(action, parameters) {
  const request = fetch(`${uri}/perform/${action}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(parameters),
    credentials: 'include'
  });
  request
    .then(res => {
      if(res.status >= 400) {
        console.error(`🚨 Error when performing "${action}"\n`, res)
      }
    });
  // return the original promise so that consumers can see the status code
  return request
}
