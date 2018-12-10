function sendEmail() {
	if(validateForm()){
		var account = {
				username: $("#username").val(),
		        email: $("#email").val(),
		    };
				$.ajax({
					url : 'http://localhost:8080/repairvehicle/resetpass',
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
	var email = $("#email").val();
	var username = $("#username").val();
	var format_email= /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	
	if (username == '') {
		alert('Username is not empty');
		return false;
	} else if (email == '') {
		alert('Email is not empty');
		return false;
	} else if (!format_email.test(email)) {
		alert('Email is invalid (name@example.com)');
		return false;
	}	else {
		return true;
	}
}