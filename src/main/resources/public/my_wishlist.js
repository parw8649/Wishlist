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


async function delete_item(itemName,userId) {
    console.log("item name: ", itemName);
    console.log("userID: ", userId);

    var data;
    var xhttp = new XMLHttpRequest();

    xhttp.open("DELETE", "https://wishlist-app-group-c.herokuapp.com/wishlist/items?item_name="+itemName+"&username="+username, true);
    xhttp.setRequestHeader('AccessToken',accessToken);
    xhttp.send();

    location.reload();
    alert("Item Deleted");
}


async function fill_template() {
			var data;
			var xhttp = new XMLHttpRequest();
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


			xhttp.open("GET", "https://wishlist-app-group-c.herokuapp.com/wishlist/items" + "?list=" + username, true);
			xhttp.setRequestHeader('AccessToken',accessToken);
			xhttp.setRequestHeader('list',username);
			xhttp.send();
        }
fill_template();

function logout() {
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.open("POST", "https://wishlist-app-group-c.herokuapp.com/wishlist/v1/user/logout", false);
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