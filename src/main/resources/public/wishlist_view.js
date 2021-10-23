var data;

async function fill_template() {
			// let url = "127.0.0.1:8080/wishlist/items";
			// let data = await getData(url);
			// console.log(data.error);
			// if (data.error == "There is nothing here"){
			// 	alert("Name not recognized")
			// }
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

			// Need to identify how to send header parameters and body parameters for API through below API call
			//var header = {loginAccessToken}
			//var body = {}
			xhttp.open("GET", "http://127.0.0.1:8080/wishlist/items", true);
//			xhttp.setRequestHeader('Authorization', 'Bearer ' + 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7InVzZXJJZCI6IlVTRVJfMTYzMzg4MTc3NzA1OSIsImVtYWlsSWQiOiJ1c2VyMTAudGVzdEB0ZXN0LmNvbSIsInVzZXJuYW1lIjoidGVzdHVzZXIxMCJ9LCJqdGkiOiJVU0VSXzE2MzM4ODE3NzcwNTkiLCJpYXQiOjE2MzQ0OTA4MDJ9.rSc8vGdW59w2Wkelk1-nOnKG_UiF3sZmkHGkTPW_ZpA');
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

let cookies = document.cookie.split("; ");
var accessToken = "";
for (c in cookies) {
	if (cookies[c].startsWith("accessToken")) {
		let token = cookies[c].split("=")
		accessToken = token[1];
	}
}

function logout() {
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.open("POST", "http://127.0.0.1:8080/wishlist/v1/user/logout", false);
	xmlhttp.setRequestHeader('accessToken', accessToken); //This needs to be used where API requires accessToken in header params
	xmlhttp.setRequestHeader("Content-Type", "application/json");

	xmlhttp.send(null);

	window.location.replace("/wishlist/");
}