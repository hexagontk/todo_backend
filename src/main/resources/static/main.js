
const http = new XMLHttpRequest();

function httpSend(method, url, body, callback) {
    http.open(method, url);
    http.setRequestHeader('Content-type', 'application/json');
    http.send(JSON.stringify(body));
    http.onload = callback;
}

function httpGet(url, body, callback) {
    httpSend('GET', url, body, callback);
}

function httpPost(url, body, callback) {
    httpSend('POST', url, body, callback);
}

function add() {
    let body = { title: 'a', order: 0 };
    httpPost('/tasks', body, () => {

        console.log(http.responseText)
    });
}

function main() {
    const tbody = document.querySelector("tbody#tasks");
    const template = document.querySelector("#todoRow");

    httpGet('/tasks', null, () => {
        let response = JSON.parse(http.responseText);
        for (const tr of tbody.children) {
            tr.remove();
        }

        for (const task of response) {
            const clone = template.content.cloneNode(true);
            let td = clone.querySelectorAll("td");
            td[0].textContent = task.title;
            td[1].textContent = task.order;
            tbody.appendChild(clone);
        }

        console.log(http.responseText)
    });
}

document.body.onload = main;
