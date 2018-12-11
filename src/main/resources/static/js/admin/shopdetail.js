$(document).ready(function() {
	showlistService();
});

// list service of shop
function showlistService() {
	$.ajax({
		async : true,
		url : "http://localhost:8080/repairvehicle/admin/getservice",
		method : "GET",
		headers : {
			"accept" : "application/json;odata=verbose",
			"content-type" : "application/json;odata=verbose"

		},
		success : function(data) {
			$('.listService input').prop('checked', false);
			var idservices = $('#idservices').val();
			// console.log(JSON.parse(idservices));
			$.each(data, function(k, v) {
				var html = '<li><input type="checkbox" value=' + v.id + ' />'
						+ v.name + '</li>';
				$('.listService').append(html);
			});
			for (var i = 0; i < JSON.parse(idservices).length; i++) {
				var value = JSON.parse(idservices)[i];
				// console.log(value);
				$('.listService input[value=' + value + ']').prop('checked',
						true);
			}
		}
	});
}

// POST method
function create() {
	if(validateForm()){
		var serviceids = [];
		$('.listService input:checked').each(function() {
			var service = {
				id : parseInt(this.value)
			}
			serviceids.push(service);
		});
	
		var shop = {
			name : $("#shopname").val(),
			phone : $("#phone").val(),
			address : $("#address").val(),
			image : $("#image").val(),
			openTime : $("#opentime").val(),
			latitude : $("#latitude").val(),
			longitude : $("#longitude").val(),
			services : serviceids
		};
	
		$.ajax({
			url : 'http://localhost:8080/repairvehicle/admin/addshop',
			type : 'post',
			contentType : 'application/json',
			data : JSON.stringify(shop),
			error : function(error) {
				alert(error.responseText);
	
			}
		}).done(function() {
			alert("Shop created");
			setTimeout(function() {
				window.location.reload();
			}, 10)
		});
	}
}

// PUT method
function update() {
	if(validateForm()){
		var idshop = $("#idshop").val();
		var serviceids = [];
		$('.listService input:checked').each(function() {
			var service = {
				id : parseInt(this.value)
			}
			serviceids.push(service);
		});
		var shop = {
			name : $("#shopname").val(),
			phone : $("#phone").val(),
			address : $("#address").val(),
			image : $("#image").val(),
			openTime : $("#opentime").val(),
			latitude : $("#latitude").val(),
			longitude : $("#longitude").val(),
			services : serviceids
		};
		$.ajax({
			url : 'http://localhost:8080/repairvehicle/admin/updateshop/' + idshop,
			type : 'put',
			contentType : 'application/json',
			data : JSON.stringify(shop),
			error : function(error) {
				alert(error.responseText);
				setTimeout(function() {
					window.location.reload();
				}, 10)
			}
		}).done(function() {
			alert("Shop Updated");
			setTimeout(function() {
				window.location.reload();
			}, 10)
		});
	}
}

// DELETE method
function deleteShop(id) {
	$.ajax({
		url : "http://localhost:8080/repairvehicle/admin/deleteshop/" + id,
		method : "delete",
		contentType : 'application/json',
		error : function(error) {
			console.log(JSON.stringify(error));
		}
	}).done(function() {
		alert("Deleted Shop");
		showlistService();
		showtable();
	});
}

function validateForm() {
	var shopname = document.getElementById('shopname').value;
	var address = document.getElementById('address').value;
	var phone = document.getElementById('phone').value;
	var image = document.getElementById('image').value;
	var opentime = document.getElementById('opentime').value;
	var latitude = document.getElementById('latitude').value;
	var longitude = document.getElementById('longitude').value;
	var format = /[ !@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;
	var checkphonenumber = isNaN(phone);

	if (shopname == '') {
		alert('Shop name is not empty');
		return false;
	} else if (address == '') {
		alert('Address is not empty');
		return false;
	} else if (phone == '') {
		alert('Phone is not empty');
		return false;
	} else if (image == '') {
		alert('Image is not empty');
		return false;
	} else if (opentime == '') {
		alert('Open time is not empty');
		return false;
	} else if (latitude == '') {
		alert('Latitude is not empty');
		return false;
	} else if (longitude == '') {
		alert('Longitude is not empty');
		return false;
	} else if (checkphonenumber) {
		alert('Phone must be number');
		return false;
	} else if (isNaN(latitude)) {
		alert('Latitude must be number');
		return false;
	} else if (isNaN(longitude)) {
		alert('Longitude must be number');
		return false;
	} else if (phone.length > 11) {
		alert('Phone number must be less than 11 characters');
	} else {
		return true;
	}
}
