$(document).ready(function() {
	showlistService();
	showtable();
});

function showlistService() {
	$('.listService').empty();
	$.ajax({
		async : true,
		url : "http://localhost:8080/repairvehicle/admin/getservice",
		method : "GET",
		headers : {
			"accept" : "application/json;odata=verbose",
			"content-type" : "application/json;odata=verbose"

		},
		success : function(data) {
			// console.log(data);
			$.each(data, function(k, v) {
//				console.log(v);
				var html = '<li><input type="checkbox" value='+ v.id +' />' + v.name +  '</li>';
				$('.listService').append(html);
				});
		}
	});
}


function showtable() {
	$('#tbodyid').empty();
	$.ajax({
		async : true,
		url : "http://localhost:8080/repairvehicle/admin/getAllShop",
		method : "GET",
		headers : {
			"accept" : "application/json;odata=verbose",
			"content-type" : "application/json;odata=verbose"
		},
		success : function(data) {
			$.each(data,function(k, v) {
				var html = '<tr><td>' + ++k + '</td>';
//				<a href="shopdetail/v.id">link text</a>
				html += '<td id="shopname' + v.id + '" > <a href="shopdetail/'+ v.id + '" title="Click to view detail or edit shop" >'+ v.name+ '</a></td>';
				html += '<td id="phone' + v.id + '" >' + v.phone + '</td>';
				html += '<td id="address' + v.id + '" >' + v.address + '</td>';
//				html += '<td><input type="button"  class="btn btn-info btnnnn" value="Edit" onclick="edit()"></td>'
				html += '<td><input type="button"  value="Delete" class="btn btn-danger btnnnn" onclick="deleteShop('
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
	location.href = "/repairvehicle/admin/shopdetail/";
	
}

function edit(listId) {
	$('.listProject input').prop('checked', false);
	var account = $('#account' + listId[0]).text();
	var salary = $('#salary' + listId[0]).text();
	console.log(listId);
	$('#uId').val(listId[0]);
	$('#account').val(account);
	$('#salary').val(salary);
	
	for(var i=1; i<listId.length; i++){
		var value = listId[i];
		$('.listProject input[value=' + value + ']').prop('checked', true);
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
// Search

function search() {
	var shopname = $("#shopname").val();
	var phone = $("#phone").val();
	var address = $("#address").val();
	
	$.ajax({
		url : "http://localhost:8080/repairvehicle/admin/searchShop?shopname="+ shopname + "&address=" + address + "&phone=" + phone ,
		method : "GET",
		headers : {
			"accept" : "application/json;odata=verbose",
			"content-type" : "application/json;odata=verbose"

		},success : function(data) {
			$("#tbodyid").empty();
			$.each(data,function(k, v) {
				var html = '<tr><td>' + ++k + '</td>';
//				<a href="shopdetail/v.id">link text</a>
				html += '<td id="shopname' + v.id + '" > <a href="shopdetail/'+ v.id + '">'+ v.name+ '</a></td>';
				html += '<td id="phone' + v.id + '" >' + v.phone + '</td>';
				html += '<td id="address' + v.id + '" >' + v.address + '</td>';
//				html += '<td><input type="button"  class="btn btn-info btnnnn" value="Edit" onclick="edit()"></td>'
				html += '<td><input type="button"  value="Delete" class="btn btn-danger btnnnn" onclick="deleteShop('
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
	var account = document.getElementById('account').value;
	var salary = document.getElementById('salary').value;
	var checkSalary = isNaN(salary);
	var format = /^[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]*$/;

	if (account == '') {
		alert('Account is not empty');
		return false;
	} else if (account.match(format)) {
		alert('Account must be not special characters');
		return false;
	} else if (account.length > 10) {
		alert('Account must be less than 10 characters');
		return false;
	} else if (salary == '') {
		alert('Salary is not empty');
		return false;
	} else if (checkSalary == true) {
		alert('Salary must be number');
		return false;
	}else if (salary.length > 20){
		alert('Salary must be less than 20 characters');
	}
	else {
		return true;
	}
}
