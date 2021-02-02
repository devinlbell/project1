window.onload = function() {
	if(sessionStorage.token) {
		let user = JSON.parse(sessionStorage.token);
		document.getElementById("barName").innerHTML = `Hi, ${user.name}`;
		if(user.role == "EMPLOYEE") {
			console.log("It's an employee!");
			document.getElementById("action-btn").innerHTML = "Add Reimbursement";
			document.getElementById("employeeModal").id = "actionModal";
			setupEmpPending();
			setupEmpApproved();
		} else {
			console.log("we've found a manager");
			document.getElementById("action-btn").innerHTML = "Approve/Deny Selections";
			document.getElementById("managerModal").id = "actionModal";
			setUpManagerPending();
			setUpManagerResolved();
		}
	}
}

let foundComps = [];
let selectedComps = [];

document.getElementById("tab1").addEventListener("click", showTab1);
document.getElementById("tab2").addEventListener("click", showTab2);
document.getElementById("logout-btn").addEventListener("click", logout);

function showTab1() {
	$('#tab1').tab('show');
}

function showTab2() {
	$('#tab2').tab('show');
}

function logout() {
	sessionStorage.removeItem("token");
}

document.getElementById("action-btn").addEventListener("click", openModal);
document.getElementById("close-btn-1").addEventListener("click", closeModal);
document.getElementById("close-btn-2").addEventListener("click", closeModal);
document.getElementById("applyComp-btn").addEventListener("click", applyForCompensation);
document.getElementById("approveComp-btn").addEventListener("click", approveCompensation);
document.getElementById("denyComp-btn").addEventListener("click", denyCompensation);

function openModal() {
	let user = JSON.parse(sessionStorage.token);
	if(user.role == "MANAGER") {
		grabSelections();
		// console.log("selections after grab: " + selectedComps);
		// console.log(selectedComps);
		// console.log(JSON.parse(selectedComps[0]));
	}
	$('#actionModal').modal('show');
}

function closeModal() {
	let user = JSON.parse(sessionStorage.token);
	if(user.role == "MANAGER") {
		removeSelections();
		// console.log("selections after grab: " + selectedComps);
		// console.log(selectedComps);
		// console.log(JSON.parse(selectedComps[0]));
	}
	$('#actionModal').modal('hide');
}

function grabSelections() {
	let rows = document.getElementsByClassName("comps");
	let idNum = 1;
	let selectedCompIds = [];
	selectedComps = [];
	console.log(rows);
	for(i in rows) {
		if(rows[i].checked) {

			let compId = parseInt(rows[i].id.split("comp")[idNum]);
			selectedCompIds.push(compId);
			
			// let comp = performGetRequest(compUrl, addSelection);
			// console.log("get response:")
			// console.log(comp);
			
		}
	}
	for(i in foundComps) {
		if(selectedCompIds.includes(foundComps[i].id)) {
			selectedComps.push(foundComps[i]);
			console.log("we have a match");
		}
		// let compUrl = baseUrl + `comps/${selectedComps[i]}`;
		// performGetRequest(compUrl, addSelection);
		// if(i == selectedComps.length -1) {
		// 	console.log("end of for loop");
		// 	console.log(foundComps);
		// 	console.log(foundComps[0]);
		// }
	}
	console.log(selectedComps);
	//console.log("after second loop")
//	console.log(JSON.parse(foundComps[i]));
	//performGetRequest()

	let tableHeaders = document.getElementById("modalTable-hdr");
	let header1 = document.createElement("th");
	let header2 = document.createElement("th");
	let header3 = document.createElement("th");

	header1.innerHTML = "Employee";
	header2.innerHTML = "Reimbursement Amount";
	header3.innerHTML = "Description";

	tableHeaders.appendChild(header1);
	tableHeaders.appendChild(header2);
	tableHeaders.appendChild(header3);
	let tableBody = document.getElementById("modalTableBody");
	// console.log("finished selections: " + selectedComps);
	
	// for(i in selectedComps) {
	// 	console.log(JSON.parse(selectedComps[i]));
	// }

	for (i in selectedComps) {
		console.log(selectedComps[i]);

		selectedComps[i].managerId = JSON.parse(sessionStorage.token).id;
		let row = document.createElement("tr");
		let employeeName = document.createElement("td");
		let amount = document.createElement("td");
		let description = document.createElement("td");
		employeeName.innerHTML = selectedComps[i].userId;
		amount.innerHTML = selectedComps[i].amount;
		description.innerHTML = selectedComps[i].description;
		tableBody.appendChild(row);
		row.appendChild(employeeName);
		row.appendChild(amount);
		row.appendChild(description);
	}

}

function removeSelections() {
	document.getElementById("modalTableBody").innerHTML ="";
	document.getElementById("modalTable-hdr").innerHTML ="";
}

function addSelection(compensation) {
	//console.log("we've got a selection over here:");
	foundComps.push(compensation);
	// console.log(selectedComps);
}

function approveCompensation() {
	console.log("we're approving this!");
	performPostRequest(baseUrl +"comps/approve", JSON.stringify(selectedComps), addressSuccess, addressFail);

}

function denyCompensation() {
	performPostRequest(baseUrl +"comps/deny", JSON.stringify(selectedComps), addressSuccess, addressFail);
}

function addressSuccess() {
	document.getElementById("error-msg-2").hidden = true;
	closeModal();
	window.location.href = "home.html";
}

function addressFail() {
	document.getElementById("error-msg-2").hidden = false;
}

function applyForCompensation() {
	let amount = parseInt(document.getElementById("compAmount").value);
	let userId = JSON.parse(sessionStorage.token).id;
	let description = document.getElementById("description").value;
	let comp = {amount, userId, description};
	console.log(comp);
	performPostRequest(baseUrl +"comps", JSON.stringify(comp), succeedComp, failComp);
}

function succeedComp() {
	console.log("comp succeeded");
	document.getElementById("error-msg").hidden = true;
	closeModal();
	window.location.href = "home.html";
}

function failComp() {
	console.log("comp failed :(");
	document.getElementById("error-msg").hidden = false;
	
}

function setupEmpPending() {
	performGetRequest(baseUrl +"comps?status=PENDING&id=" + JSON.parse(sessionStorage.token).id, populatePendingTable);

}

function setupEmpApproved() {
	performGetRequest(baseUrl +"comps?status=RESOLVED&id=" + JSON.parse(sessionStorage.token).id, populateApprovedTable);
}

function populatePendingTable(compensations) {
	let tableHeaders = document.getElementById("table1-hdr");
	let header1 = document.createElement("th");
	let header2 = document.createElement("th");
	let header3 = document.createElement("th");
	header1.innerHTML = "Reimbursement Amount";
	header2.innerHTML = "Description";
	header3.innerHTML = "Status";
	tableHeaders.appendChild(header1);
	tableHeaders.appendChild(header2);
	tableHeaders.appendChild(header3);

	let tableBody = document.getElementById("pendingBody");
	let data = JSON.parse(compensations);
	console.log(data);
	for (i in data) {
		console.log(data[i]);
		let row = document.createElement("tr");
		let amount = document.createElement("td");
		let description = document.createElement("td");
		let status = document.createElement("td");
		amount.innerHTML = data[i].amount;
		description.innerHTML = data[i].description;
		status.innerHTML = data[i].status;
		tableBody.appendChild(row);
		row.appendChild(amount);
		row.appendChild(description);
		row.appendChild(status);
	}
}

function populateApprovedTable(compensations) {
	let tableHeaders = document.getElementById("table2-hdr");
	let header1 = document.createElement("th");
	let header2 = document.createElement("th");
	let header3 = document.createElement("th");
	header1.innerHTML = "Reimbursement Amount";
	header2.innerHTML = "Description";
	header3.innerHTML = "Approved By";
	tableHeaders.appendChild(header1);
	tableHeaders.appendChild(header2);
	tableHeaders.appendChild(header3);

	let tableBody = document.getElementById("approvedBody");
	let data = JSON.parse(compensations);
	console.log(data);
	for (i in data) {
		console.log(data[i]);
		let row = document.createElement("tr");
		let amount = document.createElement("td");
		let description = document.createElement("td");
		let manager = document.createElement("td");
		amount.innerHTML = data[i].amount;
		description.innerHTML = data[i].description;
		manager.innerHTML = data[i].managerId;
		tableBody.appendChild(row);
		row.appendChild(amount);
		row.appendChild(description);
		row.appendChild(manager);
	}
}


function setUpManagerPending() {
	performGetRequest(baseUrl +"comps?status=PENDING", populateManagerTable1);
}

function setUpManagerResolved() {
	performGetRequest(baseUrl +"comps", populateManagerTable2);
}

function populateManagerTable1(compensations) {
	let tableHeaders = document.getElementById("table1-hdr");
	let header0 = document.createElement("th");
	let header1 = document.createElement("th");
	let header2 = document.createElement("th");
	let header3 = document.createElement("th");
	header0.innerHTML = "Select for Reimbursement";
	header1.innerHTML = "Employee";
	header2.innerHTML = "Reimbursement Amount";
	header3.innerHTML = "Description";
	tableHeaders.appendChild(header0);
	tableHeaders.appendChild(header1);
	tableHeaders.appendChild(header2);
	tableHeaders.appendChild(header3);
	let tableBody = document.getElementById("pendingBody");
	let data = JSON.parse(compensations);
	console.log(data);
	for (i in data) {
		console.log(data[i]);
		foundComps.push(data[i]);
		let row = document.createElement("tr");
		let checkboxCell = document.createElement("td");
		let checkbox = document.createElement("input");
		let employeeName = document.createElement("td");
		let amount = document.createElement("td");
		let description = document.createElement("td");
		checkbox.type ="checkbox";
		checkbox.id = "comp" + data[i].id;
		checkbox.className = "comps";
		checkboxCell.appendChild(checkbox);
		employeeName.innerHTML = data[i].userId;
		amount.innerHTML = data[i].amount;
		description.innerHTML = data[i].description;
		tableBody.appendChild(row);
		row.appendChild(checkboxCell);
		row.appendChild(employeeName);
		row.appendChild(amount);
		row.appendChild(description);
	}
}

function populateManagerTable2(compensations) {
	let tableHeaders = document.getElementById("table2-hdr");
	let header1 = document.createElement("th");
	let header2 = document.createElement("th");
	let header3 = document.createElement("th");
	let header4 = document.createElement("th");
	header1.innerHTML = "Employee";
	header2.innerHTML = "Reimbursement Amount";
	header3.innerHTML = "Description";
	header4.innerHTML = "Approved by";
	tableHeaders.appendChild(header1);
	tableHeaders.appendChild(header2);
	tableHeaders.appendChild(header3);
	tableHeaders.appendChild(header4);
	let tableBody = document.getElementById("approvedBody");
	let data = JSON.parse(compensations);
	console.log(data);
	for (i in data) {
		console.log(data[i]);
		let row = document.createElement("tr");
		let employeeName = document.createElement("td");
		let amount = document.createElement("td");
		let description = document.createElement("td");
		let resolver = document.createElement("td");
		row.id = "" + data[i].id;
		employeeName.innerHTML = data[i].userId;
		amount.innerHTML = data[i].amount;
		description.innerHTML = data[i].description;
		resolver.innerHTML = data[i].managerId;
		tableBody.appendChild(row);
		row.appendChild(employeeName);
		row.appendChild(amount);
		row.appendChild(description);
		row.appendChild(resolver);
	}
}


