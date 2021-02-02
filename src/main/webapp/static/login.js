window.onload = function() {
	if(sessionStorage.token) {
		window.location = "home.html";
	}
	
} 

document.getElementById("login-btn").addEventListener("click", openModal);
document.getElementById("close-btn").addEventListener("click", closeModal);
document.getElementById("login-submit-btn").addEventListener("click", attemptLogin);

function openModal() {
	$('#loginModal').modal('show');
}

function closeModal() {
	$('#loginModal').modal('hide');
}

function attemptLogin() {
	let username = document.getElementById("username").value;
	let password = document.getElementById("password").value;
	let credentials = {username, password};
	// console.log(JSON.stringify(credentials));
	performPostRequest(baseUrl + "users", JSON.stringify(credentials), goodLogin, badLogin);
}

function goodLogin(responseText) {
	console.log("We successfully logged in");
	document.getElementById("error-msg").hidden = true;
	if(responseText != null) {
		let user = JSON.parse(responseText);
		user.password = "hiddenpassword";
		sessionStorage.setItem("token", JSON.stringify(user))
		window.location.href = "home.html";

		
	}
	

}

function badLogin() {
	console.log("We couldn't log in :(");
	document.getElementById("error-msg").hidden = false;
}