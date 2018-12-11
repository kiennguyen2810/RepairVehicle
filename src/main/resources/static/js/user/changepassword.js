function saveChange() {
	if(validateForm()){
		var username = $("#username").text();
		var currentpassword = $("#currentpassword").val();
		var newpassword = $("#newpassword").val();
		$.ajax({
			url : "http://localhost:8080/repairvehicle/savepassword/" + username + "/" + currentpassword + "/" + newpassword,
			method : "PUT",
			headers : {
				"accept" : "application/json;odata=verbose",
				"content-type" : "application/json;odata=verbose"
	
			},error : function(error) {
				alert(error.responseText);
				window.location.assign("/repairvehicle")
//				console.log(error.responseText);
			}
		}).done(function(data) {
			alert(data);
		});
	}

}

function validateForm() {
	var currentpassword = document.getElementById('currentpassword').value;
	var newpassword = document.getElementById('newpassword').value;
	var rpnewpassword = document.getElementById('rpnewpassword').value;
	var format = /[ !@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;

	if (currentpassword == '') {
		alert('Password is not empty');
		return false;
	} else if (newpassword == '') {
		alert('Password is not empty');
		return false;
	} else if (rpnewpassword == '') {
		alert('Password is not empty');
		return false;
	} else if (newpassword.length < 8) {
		alert('Password must be than 8 characters');
		return false;
	} else if (rpnewpassword != newpassword) {
		alert('Repeat Password is invalid');
		return false;
	} else {
		return true;
	}
}