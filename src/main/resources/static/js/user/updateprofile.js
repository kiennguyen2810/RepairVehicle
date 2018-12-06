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
			console.log(data);
			$("#udname").val(data.name);
			$("#udemail").val(data.email);
			$("#udphone").val(data.phone);
			
		},
		error : function(error) {
			console.log(JSON.stringify(error));
		}

		})
}



function saveProfile(){
	if(validateForm()){
		var account = {
				username: $("#username").text(),
		        name: $("#udname").val(),
		        phone: $("#udphone").val(),
		        email: $("#udemail").val(),
		    };
		$.ajax({
			url : "http://localhost:8080/repairvehicle/saveprofile",
			method : "PUT",
			data : JSON.stringify(account),
			headers : {
				"accept" : "application/json;odata=verbose",
				"content-type" : "application/json;odata=verbose"
	
			},error : function(error) {
				alert(error.responseText);
				window.location.replace("userprofile");
//				console.log(error.responseText);
			}
		}).done(function(data) {
			alert(data);
//			showProfile();
		});
	}
};

function validateForm() {
	var name = document.getElementById('udname').value;
	var email_signup = document.getElementById('udemail').value;
	var phone_signup = document.getElementById('udphone').value;
	var format = /[ !@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;
	var checkphonenumber = isNaN(phone_signup);
	var format_email= /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

	if (name == '') {
		alert('Name is not empty');
		return false;
	}	else if (email_signup == '') {
		alert('Email is not empty');
		return false;
	} else if (!format_email.test(email_signup)) {
		alert('Email is invalid (name@example.com)');
		return false;
	} else if (phone_signup == '') {
		alert('Phone is not empty');
		return false;
	} else if (checkphonenumber) {
		alert('Phone must be number');
		return false;
	} else if (phone_signup.length > 11){
		alert('Phone number must be less than 11 characters');
		return false;
	} 
	else {
		return true;
	}
}