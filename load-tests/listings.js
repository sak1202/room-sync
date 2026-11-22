import http from 'k6/http';
import { check, sleep } from 'k6';
// Start conservatively. Increase stages only after inspecting saturation and error budgets.
export const options = { stages: [ { duration: '1m', target: 50 }, { duration: '3m', target: 200 }, { duration: '1m', target: 0 } ], thresholds: { http_req_failed: ['rate<0.01'], http_req_duration: ['p(95)<500'] } };
export default function () {
  const city = ['Pune','Mumbai','Bengaluru','Hyderabad'][Math.floor(Math.random()*4)];
  const res = http.get(`http://localhost:8080/api/v1/listings?city=${city}&page=0&size=20`);
  check(res, { 'status is 200': r => r.status === 200 });
  sleep(0.2);
}
