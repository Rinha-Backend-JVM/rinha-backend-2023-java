import http from 'k6/http';
import { sleep } from 'k6';

export function setup() {
    people.forEach(p => {
        const response = http.post('http://host.docker.internal:8080/pessoas', JSON.stringify(p), {
            headers: { 'Content-Type': 'application/json' },
        });
        console.log(`POST Person ${p.name} ended with status ${response.status} and location ${response.headers['Location']}`)
    })
}

export default function () {
    http.get('http://host.docker.internal:8080/hw');
    sleep(1);
}

export const people = [
    {
        nome: "Andre",
        apelido: "Paschoal",
        stack: [
            "Java",
            "SpringBoot",
            "Postgres"
        ]
    },
    {
        nome: "Bruno",
        apelido: "Santos",
        stack: [
            "Java",
            "Vertx",
            "Postgres"
        ]
    },
]
