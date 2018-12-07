$(document).ready(function() {
	showtable();
});




function showtable() {
	$('#tbodyid').empty();
	var username = $("#username").text();;
	$.ajax({
		async : true,
		url : "http://localhost:8080/repairvehicle/historyrepair/gethistoryrepair/" + username,
		method : "GET",
		headers : {
			"accept" : "application/json;odata=verbose",
			"content-type" : "application/json;odata=verbose"

		},success : function(data) {
			$.each(data,function(k, v) {
				var time = new Date(v.time);
				console.log(time);
				console.log(time.getMonth());
				var timeString = time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds() + " - " + time.getDate() + "/" + (time.getMonth()+1) + "/" + time.getFullYear();
				var html = '<tr><td>' + ++k + '</td>';
				html += '<td id="shop' + v.shop.id +'">' + v.shop.name + '</td>';
				html += '<td id="service' + v.service.id + '">' + v.service.name + '</td>';
				html += '<td id="time' + v.id + '">' + timeString + '</td>';
				html += '</tr>'
				$('#tbodyid').append(html);
		});

		},
		error : function(error) {
			console.log(JSON.stringify(error));
		}

		})
}

function search() {
	$('#tbodyid').empty();
	var username = $("#username").text();
	var textsearch = $("#textsearch").val();
	var date = $("#datepicker").val();
	console.log(date);
	$.ajax({
		async : true,
		url : "http://localhost:8080/repairvehicle/historyrepair/gethistoryrepair/?username="+ username + "&textsearch=" + textsearch + "&date=" + date,
		method : "GET",
		headers : {
			"accept" : "application/json;odata=verbose",
			"content-type" : "application/json;odata=verbose"

		},success : function(data) {
			$.each(data,function(k, v) {
				var time = new Date(v.time);
				var timeString = time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds()+ " - " + time.getDate() + "/" + (time.getMonth() + 1) + "/" + time.getFullYear();
				var html = '<tr><td>' + ++k + '</td>';
				html += '<td id="shop' + v.shop.id +'">' + v.shop.name + '</td>';
				html += '<td id="service' + v.service.id + '">' + v.service.name + '</td>';
				html += '<td id="time' + v.id + '">' + timeString + '</td>';
				html += '</tr>'
				$('#tbodyid').append(html);
		});
		$("#textsearch").val("");
		},
		error : function(error) {
			console.log(JSON.stringify(error));
		}

		})
}