//AJAX call - GET
function getAllUsers() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("demo").innerHTML = this.responseText;
    }
  };

  // Need to identify how to send header parameters and body parameters for API through below API call
  //var header = {loginAccessToken}
  //var body = {}
  xhttp.open("GET", "http://127.0.0.1:8080/wishlist/v1/admin/users", true);
  xhttp.send();
}

document.querySelector("#login").addEventListener('click', login);

function login() {

    let username = $("#user").val();
    let password = $("#pass").val();

    let request = `{"username": "${username}","password": "${password}"}`;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "http://127.0.0.1:8080/wishlist/v1/user/login", false);
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    //xmlhttp.setRequestHeader('accessToken', accessToken); //This needs to be used where API requires accessToken in header params
    try {
        xmlhttp.send(request);
        res = JSON.parse(xmlhttp.responseText)

        let accessToken = res.data.accessToken;
        // put accessToken in cookie to access across scripts?
        document.cookie = "username=" + username + "; path=/";
        document.cookie = "firstName=" + res.data.firstName + "; path=/";
        document.cookie = "lastName=" + res.data.lastName + "; path=/";
        document.cookie = "emailId=" + res.data.emailId + "; path=/";
        document.cookie = "password=" + password + "; path=/";
        document.cookie = "accessToken=" + accessToken + "; path=/";

        window.location.replace("/wishlist/wishlist_view_page.html");
    } catch (BadRequestException) {
        $("#error").text("Invalid username or password");
    }
}