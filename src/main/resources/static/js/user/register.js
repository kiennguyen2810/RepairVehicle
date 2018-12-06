function register(){
	if(validateForm()){
		var account = {
				username: $("#username_signup").val(),
				password: $("#password_signup").val(),
		        name: $("#name").val(),
		        phone: $("#phone_signup").val(),
		        email: $("#email_signup").val(),
		    };
			
				$.ajax({
					url : 'http://localhost:8080/repairvehicle/register',
					type : 'post',
					contentType : 'application/json',
					data : JSON.stringify(account),
					error: function(error){
						alert(error.responseText);
						
					}
				}).done(function(data, textStatus, request) {
					alert(request.getResponseHeader('Result'));
					setTimeout(function() {
					window.location.reload();
				}, 10)
				});
	}	
}


function validateForm() {
	var name = document.getElementById('name').value;
	var email_signup = document.getElementById('email_signup').value;
	var phone_signup = document.getElementById('phone_signup').value;
	var username_signup = document.getElementById('username_signup').value;
	var password_signup = document.getElementById('password_signup').value;
	var rppassword_signup = document.getElementById('rppassword_signup').value;
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
	} else if (username_signup == '') {
		alert('Username is not empty');
		return false;
	} else if (format.test(username_signup)) {
		alert('Username must be not special characters');
		return false;
	} else if (username_signup.length < 4) {
		alert('Username must be than 4 characters');
		return false;
	} else if (password_signup == '') {
		alert('Password is not empty');
		return false;
	}  else if (password_signup.length < 8) {
		alert('Password must be than 8 characters');
		return false;
	}  else if (rppassword_signup != password_signup) {
		alert('Repeat Password is invalid');
		return false;
	}
	else {
		return true;
	}
}

