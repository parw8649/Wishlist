//Grabbing username and access token with cookie
var cookies = document.cookie.split("; ");
console.log(cookies);
var username = "";
var accessToken = "";
var userId = "";
for (c in cookies) {
    if (cookies[c].startsWith("username")) {
        let user = cookies[c].split("=")
        username = user[1];
    }
    if (cookies[c].startsWith("accessToken")) {
        let token = cookies[c].split("=")
        accessToken = token[1];
    }
    if (cookies[c].startsWith("userId")) {
            let id = cookies[c].split("=")
            userId = id[1];
        }
}
console.log("Accesstoken: ", accessToken);
console.log("username: ", username);

async function edit_item(index, OGName, itemId){


    let itemName = document.getElementById(index).elements[0].value;
    let itemLink = document.getElementById(index).elements[1].value;
    let itemDescription = document.getElementById(index).elements[2].value;
    let itemImage = document.getElementById(index).elements[3].value;
    let itemPriority = document.getElementById(index).elements[4].value;

    let data = {
            "name":itemName,
            "link":itemLink,
            "description":itemDescription,
            "imgUrl":itemImage,
            "userId":userId,
            "priority":itemPriority,
            "itemId":itemId,
        };

    console.log("DATATATAT: ", data);

    //PATCH ITEM WITH NEW VALUES
    let xhttp2 = new XMLHttpRequest();
    data = JSON.stringify(data);
    xhttp2.open("PATCH", "http://127.0.0.1:8080/wishlist/items?item_name="+OGName, true);
    xhttp2.setRequestHeader('accessToken',accessToken);
    xhttp2.setRequestHeader('Content-Type','application/json');
    xhttp2.send(data);

    location.reload();
}


async function fill_template() {
    var data;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
        console.log(typeof this.responseText);
        data = JSON.parse(this.responseText);
        console.log(data);
        var template = Handlebars.compile(document.querySelector("#wishlist_template").innerHTML);
        var filled = template(data, { noEscape: true });
        document.querySelector("#output").innerHTML = filled;
        }
    };


    xhttp.open("GET", "http://127.0.0.1:8080/wishlist/items" + "?list=" + username, true);
    xhttp.setRequestHeader('AccessToken',accessToken);
    xhttp.setRequestHeader('list',username);
    xhttp.send();
    }
fill_template();

function logout() {
	let xmlhttp = new XMLHttpRequest();
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

