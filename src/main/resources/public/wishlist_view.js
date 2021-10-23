
async function delete_item() {
    alert("Item Deleted");
}

async function fill_template() {
			// let url = "127.0.0.1:8080/wishlist/items";
			// let data = await getData(url);
			// console.log(data.error);
			// if (data.error == "There is nothing here"){
			// 	alert("Name not recognized")
			// }


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

			//Grabbing username and access token with cookie
			let cookies = document.cookie.split("; ");
			console.log(cookies);
            let username = "";
            let accessToken = "";
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
            console.log("USERNAME: ", accessToken);
            console.log("ACCESSTOKEN: ", username);


//			 Need to identify how to send header parameters and body parameters for API through below API call
//			var header = {loginAccessToken}
//			var body = {}

			xhttp.open("GET", "http://127.0.0.1:8080/wishlist/items", true);
			xhttp.setRequestHeader('AccessToken',accessToken);
			xhttp.setRequestHeader('list',username);
			xhttp.send();



//		 let data = [
//		 		{
//		 			"name": "Playstation 5",
//		 			"link": "https://www.bestbuy.com/site/sony-playstation-5-console/6426149.p?skuId=6426149",
//		 			"description": "For some epic gaming",
//		 			"imgUrl": "https://images.unsplash.com/photo-1606144042614-b2417e99c4e3?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8cGxheXN0YXRpb258ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
//		 			"userId": 7,
//		 			"priority": "high"
//		 		},
//		 		{
//		 			"name": "Xbox Series X",
//		 			"link": "https://www.bestbuy.com/site/microsoft-xbox-series-x-1tb-console-black/6428324.p?skuId=6428324",
//		 			"description": "For some epic gaming + Halo",
//		 			"imgUrl": "https://images.unsplash.com/photo-1605901309584-818e25960a8f?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8eGJveHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
//		 			"userId": 7,
//		 			"priority": "high"
//		 		},
//		 		{
//		 			"name": "Black Poplin Long Sleeve Button-Up",
//		 			"link": "https://www.dapperboi.com/collections/button-ups/products/black-poplin-long-sleeve-button-up",
//		 			"description": "comfy with good fit. medium please :D",
//		 			"imgUrl": "https://cdn.shopify.com/s/files/1/0876/1046/products/dapper-boi-shirts-pre-order-campaign-black-poplin-long-sleeve-button-up-30307667378349_555x.png?v=1631752257",
//		 			"userId": 7,
//		 			"priority": "med"
//		 		},
//		 	]
//
//            console.log(data);
//            var template = Handlebars.compile(document.querySelector("#wishlist_template").innerHTML);
//            var filled = template(data, { noEscape: true });
//            document.querySelector("#output").innerHTML = filled;
        }

		// async function getData(url){
		// 	let response = await fetch(url);
		// 	let data     = await response.json();
		// 	return data;
		// }
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