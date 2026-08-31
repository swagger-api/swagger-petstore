import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE_URL = 'http://localhost:8080/api/v3/store'; // Adjust the base URL if necessary

export let options = {
    vus: 100, // Number of virtual users
    duration: '1m', // Test duration
};

export default function () {
    // Define the order payload
    const order = {
        id: 1,
        petId: 198772,
        quantity: 7,
        shipDate: new Date().toISOString(), // Use the current date and time
        status: "approved",
        complete: true
    };

    // Make the POST request
    let response = http.post(`${BASE_URL}/order`, JSON.stringify(order), {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
    });

    // Check response
    check(response, {
        'status is 200': (r) => r.status === 200,
        'response is not empty': (r) => r.body.length > 0,
    });

    sleep(1); // Wait for 1 second before the next iteration
}