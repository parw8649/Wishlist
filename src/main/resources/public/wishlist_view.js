
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

async function add_to_my_list(itemName,userId){
    console.log("item name: ", itemName);
    console.log("userID: ", userId);

    var data;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
        console.log(typeof this.responseText);
        data = JSON.parse(this.responseText);
        console.log(data);
        delete data['itemId'];
        delete data['userId'];
        console.log(data);
        let dto = {
            "name":data['name'],
            "link":data['link'],
            "description":data['description'],
            "imgUrl":data['imgUrl'],
            "priority":data['priority'],
        }
        dto = JSON.stringify(dto)
        console.log("DTO: ", dto);

        var xhttp2 = new XMLHttpRequest();
        let DTOnUser = {"itemDTO": data,"username": username};
        DTOnUser = JSON.stringify(DTOnUser);
        xhttp2.open("POST", "http://127.0.0.1:8080/wishlist/items", true);
        xhttp2.setRequestHeader('accessToken',accessToken);
        xhttp2.setRequestHeader('Content-Type','application/json');
        xhttp2.send(DTOnUser);
        }
    };

    xhttp.open("GET", "http://127.0.0.1:8080/wishlist/items?item_name="+itemName+"&userId="+userId, true);
    xhttp.setRequestHeader('accessToken',accessToken);

    xhttp.send();
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

			xhttp.open("GET", "http://127.0.0.1:8080/wishlist/items", true);
			xhttp.setRequestHeader('AccessToken',accessToken);
			xhttp.send();
			}
fill_template();

var accessToken = "";
var adminAccessToken = "";

verify_login();

function verify_login() {

	let cookies = document.cookie.split("; ");

	if(cookies.length == 1) {
		window.location.replace("/wishlist/login_page.html");
	} else {
		for (c in cookies) {
			if (cookies[c].startsWith("accessToken")) {
				let token = cookies[c].split("=")
				accessToken = token[1];
			}
			if (cookies[c].startsWith("adminAccessToken")) {
				let token = cookies[c].split("=")
				adminAccessToken = token[1];
			}
		}
	}
}

function verifyAdmin() {
	if(adminAccessToken == "") {
		alert("Insufficient permissions");
	} else {
		window.location.replace("/wishlist/admin_view_users.html");
	}
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