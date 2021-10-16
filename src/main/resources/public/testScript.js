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