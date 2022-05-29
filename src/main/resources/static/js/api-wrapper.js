const API_URL = "http://localhost:8080/api";
const api = {};

api.getUsers = async function() {
    const response = await fetch(API_URL + "/users");
    return await response.json();
}

api.getOneUser = async function(username) {
    const response = await fetch(API_URL + "/users/" + username);
    const responseText = await response.text();

    if(responseText.length > 0) {
        return JSON.parse(responseText);
    } else {
        return null;
    }
}

api.createUser = async function(userData) {
    const response = await fetch(API_URL + "/users", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(userData)
    });

    return await response.json();
}

api.modifyUser = async function(username, userData) {
    console.log("modifyUser:: ", userData);

    const response = await fetch(API_URL + "/users/" + username, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(userData)
    });

    return await response.json();
}

api.deleteUser = async function(username) {
    const response = await fetch(API_URL + "/users/" + username, {
        method: "DELETE"
    });

    return response.status === 200;
}