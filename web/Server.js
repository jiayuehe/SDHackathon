function connectToServer(socket) {
    socket = new WebSocket("ws://localhost:4444");
    socket.onopen = function(event) {
        // TODO
    };
    socket.onmessage = function(event) {
        parseData(event.data);
        document.getElementById("mychat").innerHTML += event.data + "<br />";
    };
    socket.onclose = function(event) {
        // TODO
        document.getElementById("mychat").innerHTML += "Disconnected!";
    }
}

function parseData(data) {
    if (data.type = "CHAT") {

    }
    if (data.type = "MODIFY_PLAN") {

    }

}

function addLocations(dest,origin,price) {
    let content = JSON.stringify({
        dest: dest,
        origin: origin,
        startMonth: startMonth
    });

    let queryString = JSON.stringify({
        type: "ADD_LOCATION",
        userName: userName,
        tripName: tripName,
        content: content
    });

    socket.send(queryString);
    return false;
}

/*
function searchForCity(dest,origin,startMonth) {
    let content = JSON.stringify({
        dest: dest,
        origin: origin,
        startMonth: startMonth,
        endMonth: startMonth + 1
    });

    let queryString = JSON.stringify({
        type: "SEARCH",
        userName: userName,
        tripName: tripName,
        content: content
    });
    //let searchQuery = "search?dest="+ dest +"&from=" + from + "&time=" + time;

    socket.send(queryString);
    return false;
} */


function sendMessage() {
    /* TODO: change the following user and content */
    let queryString = JSON.stringify({
        type: "CHAT",
        userName: userName,
        tripName: tripName,
        content: content
    });

    socket.send(queryString);
    return false;
}

// City name, Dest, price,
function calculateResult() {
    /* TODO: change the following user and content */
    let queryString = JSON.stringify({
        type: "CALCULATE",
        userName: userName,
        tripName: tripName,
        content: content
    });

    socket.send(calculateMessage);
    return false;
}
