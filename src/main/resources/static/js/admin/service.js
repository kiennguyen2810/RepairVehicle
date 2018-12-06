$(document).ready(function() {
	showtable();
});

function showtable() {
	$('#tbodyid').empty();
	$.ajax({
		async : true,
		url : "http://localhost:8080/repairvehicle/admin/getservice",
		method : "GET",
		headers : {
			"accept" : "application/json;odata=verbose",
			"content-type" : "application/json;odata=verbose"

		},success : function(data) {
			$.each(data,function(k, v) {
				var html = '<tr><td>' + ++k + '</td>';
				html += '<td id="servicename' + v.id +'">' + v.name + '</td>';
				html += '<td id="price' + v.id + '">' + v.price + '</td>';
				html += '<td><input type="button" class="btn btn-info btnnnn" value="Edit" onclick="edit('
					+ v.id + ')"></td>'
				html += '<td><input type="button" value="Delete" class="btn btn-danger btnnnn" onclick="deleteService('
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


// POST method
function create() {
	if (validateForm()) {
		var servicename = $("#servicename").val(),
			price = $("#price").val();
		var service = {
				name: servicename, 
				price: price
		};
	
		$.ajax({
			url : 'http://localhost:8080/repairvehicle/admin/addservice',
			type : 'post',
			contentType : 'application/json',
			data : JSON.stringify(service),
			error: function(error){
				alert(error.responseText);
				setTimeout(function() {
					window.location.reload();
				}, 10)
			}
		}).done(function() {
			alert("Service created");
			showtable();
		});
	}
	
}

function edit(id) {
	var servicename = $('#servicename' + id).text();
	var price = $('#price' + id).text();
	console.log(servicename);
	console.log(price);
	
	$('#uId').val(id);
	$('#servicename').val(servicename);
	$('#price').val(price);
	
}

// PUT method
function update() {
	if (validateForm()){
		var servicename = $("#servicename").val(),
			price = $("#price").val(),
			id = $("#uId").val();
		var service = {
				name: servicename, 
				price: price
		};
		$.ajax({
			url : "http://localhost:8080/repairvehicle/admin/updateservice/" + id,
			method : "put",
			contentType : 'application/json',
			data : JSON.stringify(service),
			error : function(error) {
				alert("error");
				console.log(JSON.stringify(error));
			}
		}).done(function() {
			alert("Update Successfully")
			showtable();
		});
	}

}

// DELETE method
function deleteService(id) {
	$.ajax({
		url : "http://localhost:8080/repairvehicle/admin/deleteservice/" + id,
		method : "delete",
		contentType : 'application/json',
		error : function(error) {
			console.log(JSON.stringify(error));
		}
	}).done(function() {
		alert("Deleted Service");
//		showlistProject();
		showtable();
	});
}
// Search

function search() {
	var servicename = $("#servicename").val();
	$.ajax({
		url : "http://localhost:8080/repairvehicle/admin/getservice/" + servicename,
		method : "GET",
		headers : {
			"accept" : "application/json;odata=verbose",
			"content-type" : "application/json;odata=verbose"

		},success : function(data) {
			$("#tbodyid").empty();
			$.each(data,function(k, v) {
				var html = '<tr><td>' + ++k + '</td>';
				html += '<td id="servicename' + v.id +'">' + v.name + '</td>';
				html += '<td id="price' + v.id + '">' + v.price + '</td>';
				html += '<td><input type="button" class="btn btn-info btnnnn" value="Edit" onclick="edit('
					+ v.id + ')"></td>'
				html += '<td><input type="button" value="Delete" class="btn btn-danger btnnnn" onclick="deleteService('
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

function validateForm() {
	var servicename = document.getElementById('servicename').value;
	var price = document.getElementById('price').value;
	var checkPrice = isNaN(price);
	var format = /^[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]*$/;

	if (servicename == '') {
		alert('Service name is not empty');
		return false;
	} else if (servicename.match(format)) {
		alert('Service name must be not special characters');
		return false;
	} else if (servicename.length > 20) {
		alert('Service name must be less than 10 characters');
		return false;
	} else if (price == '') {
		alert('Price is not empty');
		return false;
	} else if (checkPrice == true) {
		alert('Price must be number');
		return false;
	}else if (price.length > 20){
		alert('Price must be less than 20 characters');
	}
	else {
		return true;
	}
}
