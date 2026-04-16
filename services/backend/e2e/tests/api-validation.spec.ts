import { test, expect } from '@playwright/test';

test.describe('Message Simulator API Validation', () => {

  test('GET /status should return initial simulator status', async ({ request }) => {
    const response = await request.get('/status');
    expect(response.ok()).toBeTruthy();
    
    const body = await response.json();
    expect(body).toHaveProperty('running');
    expect(body).toHaveProperty('uptime');
  });

  test('POST /start should activate the simulator', async ({ request }) => {
    const response = await request.post('/start');
    // Ensure the execution completed
    expect(response.ok()).toBeTruthy();

    const statusResponse = await request.get('/status');
    const body = await statusResponse.json();
    
    expect(body.running).toBeTruthy();
  });

  test('POST /stop should halt the simulator', async ({ request }) => {
    const response = await request.post('/stop');
    expect(response.ok()).toBeTruthy();

    const statusResponse = await request.get('/status');
    const body = await statusResponse.json();
    
    expect(body.running).toBeFalsy();
    // Because uptime calculates from active timer, it drops to 0 when stopped
    expect(body.uptime).toBe(0);
  });
  
});
