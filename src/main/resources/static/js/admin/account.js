$(document).ready(function() {
	showtable();
});


function showtable() {
	$('#tbodyid').empty();
	$.ajax({
		async : true,
		url : "http://localhost:8080/repairvehicle/admin/getaccountuser",
		method : "GET",
		headers : {
			"accept" : "application/json;odata=verbose",
			"content-type" : "application/json;odata=verbose"
		},
		success : function(data) {
			$.each(data,function(k, v) {
				var html = '<tr><td>' + ++k + '</td>';
				html += '<td>' + v.username + '</td>';
				html += '<td>' + v.name + '</td>';
				html += '<td>' + v.phone + '</td>';
				html += '<td>' + v.email + '</td>';
				html += '<td><input type="button"  value="Delete" class="btn btn-danger btnnnn" onclick="deleteAccount('
						+ v.id + ')" ></td>'
				html += '</tr>'
				$('#tbodyid').append(html);
		});
		},
		error : function(error) {
			console.log(JSON.stringify(error));
		}

		})
}


// DELETE method
function deleteAccount(id) {
	$.ajax({
		url : "http://localhost:8080/repairvehicle/admin/deleteaccount/" + id,
		method : "delete",
		contentType : 'application/json',
		error : function(error) {
			console.log(JSON.stringify(error));
		}
	}).done(function() {
		alert("Deleted Account");
//		showlistProject();
		showtable();
	});
}

// Search
function search() {
	var username = $("#username").val();
	var name = $("#name").val();
	var email = $("#email").val();
	$.ajax({
		url : "http://localhost:8080/repairvehicle/admin/searchaccountuser?username="+ username + "&name=" + name + "&email=" + email ,
		method : "GET",
		headers : {
			"accept" : "application/json;odata=verbose",
			"content-type" : "application/json;odata=verbose"
		},
		success : function(data) {
			$("#tbodyid").empty();
			$.each(data,function(k, v) {
				var html = '<tr><td>' + ++k + '</td>';
				html += '<td>' + v.username + '</td>';
				html += '<td>' + v.name + '</td>';
				html += '<td>' + v.phone + '</td>';
				html += '<td>' + v.email + '</td>';
				html += '<td><input type="button"  value="Delete" class="btn btn-danger btnnnn" onclick="deleteAccount('
						+ v.id + ')" ></td>'
				html += '</tr>'
				$('#tbodyid').append(html);
			});

		},
		error : function(error) {
			console.log(JSON.stringify(error));
		}
			})
}

