export function doFetch(url, method) {
  return fetch(url, { method, headers: { 'Content-Type': 'application/json' } });
}

export function doFetchBody(url, method, body) {
  return fetch(url, { method, headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(body) });
}
