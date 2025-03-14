import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE_URL = 'http://localhost:8080/v3/store'; // Adjust the base URL as needed

export let options = {
    stages: [
        { duration: '30s', target: 50 }, // Ramp-up to 50 users over 30 seconds
        { duration: '1m', target: 50 },   // Stay at 50 users for 1 minute
        { duration: '30s', target: 0 },    // Ramp-down to 0 users
    ],
};

export default function () {
    // Create a new order
    let orderId = Math.floor(Math.random() * 1000); // Random ID for the order
    const order = {
        id: orderId,
        petId: Math.floor(Math.random() * 1000), // Random pet ID
        quantity: Math.floor(Math.random() * 10) + 1, // Random quantity
        shipDate: new Date().toISOString(),
        status: "placed",
        complete: true
    };

    let createOrderResponse = http.post(`${BASE_URL}/order`, JSON.stringify(order), {
        headers: { 'Content-Type': 'application/json' },
    });

    // Check response
    check(createOrderResponse, {
        'create order status is 200': (r) => r.status === 200,
        'response is not empty': (r) => r.body.length > 0,
    });

    // Optionally retrieve the created order
    let getOrderResponse = http.get(`${BASE_URL}/order/${orderId}`);
    check(getOrderResponse, {
        'get order status is 200': (r) => r.status === 200,
    });

    sleep(1); // Wait for 1 second before the next iteration
}
