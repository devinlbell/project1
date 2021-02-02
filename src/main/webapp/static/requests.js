const baseUrl = "http://localhost:8080/project-1-ers/api/";

function performGetRequest(url, callback) {
	const xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4) {
			if(xhr.status == 200) {
				callback(xhr.response);
			} else {
				console.error("Something went wrong with the get request to " + url);
			}
		}
	}
	xhr.send();
}

function performPostRequest(url, payload, goodCallback, badCallback) {
	const xhr = new XMLHttpRequest();
	xhr.open("POST", url);
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4) {
			if(xhr.status > 199 && xhr.status < 300) {
				console.log(xhr.response);
				goodCallback(xhr.response);
			} else {
				if(badCallback) {
					badCallback();
				} else {
					console.error("Something went wrong with the post request to " + url);
				}
			}
		}
	}
	console.log(url);
	console.log(typeof payload);
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(payload);
}