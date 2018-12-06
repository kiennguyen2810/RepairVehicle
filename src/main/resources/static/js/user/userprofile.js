$(document).ready(function() {
	showProfile();
});



function showProfile() {
	var username = $("#username").text();
	$.ajax({
		async : true,
		url : "http://localhost:8080/repairvehicle/getaccount/" + username,
		method : "GET",
		headers : {
			"accept" : "application/json;odata=verbose",
			"content-type" : "application/json;odata=verbose"
		},
		success : function(data) {
			$("#name").text(data.name);
			$("#email").text(data.email);
			$("#phone").text(data.phone);
			
		},
		error : function(error) {
			console.log(JSON.stringify(error));
		}

		})
}