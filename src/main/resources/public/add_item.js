
//Grabbing username and access token with cookie
var cookies = document.cookie.split("; ");
console.log(cookies);
var username = "";
var accessToken = "";
for (c in cookies) {
    if (cookies[c].startsWith("username")) {
        let user = cookies[c].split("=")
        username = user[1];
    }
    if (cookies[c].startsWith("accessToken")) {
        let token = cookies[c].split("=")
        accessToken = token[1];
    }
}
console.log("Accesstoken: ", accessToken);
console.log("username: ", username);

async function add_item() {
    let itemName = document.getElementById("addItemName").value;
    let itemLink = document.getElementById("addItemLink").value;
    let itemDescription = document.getElementById("addItemDescription").value;
    let itemImage = document.getElementById("addItemImage").value;
    let itemPriority = document.getElementById("addItemPriority").value;

    console.log("Name:", itemName);
    console.log("Link:", itemLink);
    console.log("Description:", itemDescription);
    console.log("image:", itemImage);

    let dto = {
        "name":itemName,
        "link":itemLink,
        "description":itemDescription,
        "imgUrl":itemImage,
        "priority":itemPriority,
    }

    var xhttp2 = new XMLHttpRequest();
        let DTOnUser = {"itemDTO": dto,"username": username};
        DTOnUser = JSON.stringify(DTOnUser);
        xhttp2.open("POST", "http://127.0.0.1:8080/wishlist/items", true);
        xhttp2.setRequestHeader('accessToken',accessToken);
        xhttp2.setRequestHeader('Content-Type','application/json');
        xhttp2.send(DTOnUser);

    location.reload();
    }

function logout() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "http://127.0.0.1:8080/wishlist/v1/user/logout", false);
    xmlhttp.setRequestHeader('accessToken', accessToken); //This needs to be used where API requires accessToken in header params
    xmlhttp.setRequestHeader("Content-Type", "application/json");

    xmlhttp.send(null);

    const d = new Date();
    d.setTime(d.getTime());

    document.cookie = "username=; expires=" + d.toUTCString() + "; path=/";
    document.cookie = "firstName=; expires=" + d.toUTCString() + "; path=/";
    document.cookie = "lastName=; expires=" + d.toUTCString() + "; path=/";
    document.cookie = "emailId=; expires=" + d.toUTCString() + "; path=/";
    document.cookie = "password=; expires=" + d.toUTCString() + "; path=/";
    document.cookie = "accessToken=; expires=" + d.toUTCString() + "; path=/";
    document.cookie = "adminAccessToken=; expires=" + d.toUTCString() + "; path=/";

    window.location.replace("/wishlist/");
}