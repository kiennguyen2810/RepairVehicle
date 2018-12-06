$(document).ready(function() {
	initMap();
	var getServiceControlDiv = document.createElement('div');
	  var getServiceControl = new GetServiceControl(getServiceControlDiv, map);
	  getServiceControlDiv.index = 1;
	  map.controls[google.maps.ControlPosition.BOTTOM_CENTER].push(getServiceControlDiv);
});
     
function showModal_Service(){
	$('.listService').empty();
	 showlistService();
	 $("#modal_service").modal('show');
};
  

function GetServiceControl(controlDiv, map) {
  // Set CSS for the control border.
  var controlUI = document.createElement('div');
  controlUI.style.backgroundColor = 'red';
  controlUI.style.border = '2px solid #fff';
  controlUI.style.borderRadius = '25px';
  controlUI.style.boxShadow = '0 2px 6px rgba(0,0,0,.3)';
  controlUI.style.cursor = 'pointer';
  controlUI.style.marginBottom = '22px';
  controlUI.style.textAlign = 'center';
  controlUI.title = 'Click to get list Service';
  controlDiv.appendChild(controlUI);

  // Set CSS for the control interior.
  var controlText = document.createElement('div');
  controlText.style.color = 'rgb(255,255,255)';
  controlText.style.fontFamily = 'Roboto,Arial,sans-serif';
  controlText.style.fontSize = '20px';
  controlText.style.lineHeight = '50px';
  controlText.style.paddingLeft = '5px';
  controlText.style.paddingRight = '5px';
  controlText.innerHTML = 'SOS';
  controlUI.appendChild(controlText);

  // Setup the click event listeners: simply set the map to Chicago.
  controlUI.addEventListener('click', function() {
	  showModal_Service();
  });
}



/*API lấy service để show ra màn hình*/
function showlistService() {
	initMap();
	var getServiceControlDiv = document.createElement('div');
	  var getServiceControl = new GetServiceControl(getServiceControlDiv, map);
	  getServiceControlDiv.index = 1;
	  map.controls[google.maps.ControlPosition.BOTTOM_CENTER].push(getServiceControlDiv);
	$.ajax({
		async : false,
		url : "http://localhost:8080/repairvehicle/admin/getservice",
		method : "GET",
		headers : {
			"accept" : "application/json;odata=verbose",
			"content-type" : "application/json;odata=verbose"

		},
		success : function(data) {
			$('.listService input').prop('checked', false);
			$.each(data, function(k, v) {
				var html = '<li><input type="checkbox" value='+ v.id +' />' +" "+ v.name +  '</li>';
				$('.listService').append(html);
				});
		}
	});
};

//luu lich su sua chua
function saveHistoryRepair(idShop){
	var username = $("#username").text();
	var serviceids = [];
    $('.listService input:checked').each(function() {
    	var	serviceid = parseInt(this.value);
    	serviceids.push(serviceid);
    });
//    alert("service: " + serviceids.length);
//    alert("shop: " + idShop);
    
	$.ajax({
		url : 'http://localhost:8080/repairvehicle/historyrepair/savehistoryrepair/' + username + '/' + idShop,
		type : 'post',
		contentType : 'application/json',
		data : JSON.stringify(serviceids),
		error: function(error){
			alert(error.responseText);
		}
	}).done(function() {
		alert("Saved history repair!");
	});
    
    
}



//get list shop around
function getShopAround(){
	
	var latitude, longitude;
	//get location user
//	if (navigator.geolocation) {
//        navigator.geolocation.getCurrentPosition(function(position) {
//        	latitude = position.coords.latitude;
//        	longitude = position.coords.longitude;
        	latitude = 21.0309505;
        	longitude = 105.7828888,21;
        	console.log("lat 1:" + latitude);
        	console.log("long 1:" + longitude);
  //      	21.0309505,105.7828888,21
        	var userLatLng = {lat: latitude, lng: longitude};
        	var userLocation = new google.maps.Marker({
                position: userLatLng,
                map: map,
                //set icon gps
                icon:{
                    path: google.maps.SymbolPath.CIRCLE,
                    scale: 4,
//                    fillColor: "#1874CD",
                    strokeColor: 'RED',
                    fillOpacity: 0.5,
//                    strokeWeight: 0.4
                    
                }
              });
        	map.setCenter(new google.maps.LatLng(latitude, longitude));
        	map.setZoom(15);
        	neighborhoods = [];
        	var serviceids = [];
            $('.listService input:checked').each(function() {
            	serviceids.push(parseInt(this.value));
            });
        	$.ajax({
        		url : 'http://localhost:8080/repairvehicle/shop/getshoparound/' + latitude + '/' + longitude,
        		type : 'post',
        		contentType : 'application/json',
        		data : JSON.stringify(serviceids),
        		error: function(error){
        			alert(error.responseText);
        			
        		}
        	}).done(function(data) {
        		$.each(data, function(k, v) {
        			neighborhoods.push(v);
        			});
        		//drop marker list shop
        		drop(userLatLng);
        		
        	});
//        });
//    } else {
//        alert("Geolocation is not supported by this browser.");
//    }
	
//	latitude = 21;
//	longitude = 105.84;
	// marker location user
	
}
var neighborhoods = [];


var markers = [];


var map;
function initMap() {
  map = new google.maps.Map(document.getElementById('map'), {
  zoom: 15,
  center: {lat: 21.0033135, lng: 105.8411267}
     });
}

//chi duong
function calcRoute(userLatLng, position, directionsDisplay) {
	  var directionsService = new google.maps.DirectionsService();
	  var selectedMode = document.getElementById('mode').value;
//	  directionsDisplay.setMap(null);
	  var request = {
	      origin: userLatLng,
	      destination: position,
	      travelMode: google.maps.TravelMode[selectedMode]
	  };
	  directionsService.route(request, function(response, status) {
	    if (status == 'OK') {
	    	directionsDisplay.setDirections(response);
	    	console.log("directionsDisplay: " + directionsDisplay.getDirections());
	    	computeTotalDistance(directionsDisplay.getDirections());
//	    	console.log(response);
	    }
	  });
	  directionsDisplay.setMap(map);
}

	//drop marker list shop
   function drop(userLatLng) {
     clearMarkers();
     var infowindow = new google.maps.InfoWindow;
     var directionsDisplay = new google.maps.DirectionsRenderer({suppressMarkers: true});
     for (var i = 0; i < neighborhoods.length; i++) {
       addMarkerWithTimeout(neighborhoods[i], i * 200, userLatLng, infowindow, directionsDisplay);
//       console.log(neighborhoods[i]);
     }
   }


   
 //marker list shop
   function addMarkerWithTimeout(shop, timeout, userLatLng, infowindow, directionsDisplay) {
     window.setTimeout(function() {
    	 var position = {lat: shop.latitude, lng: shop.longitude}
    	 var image = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';
       markers.push(new google.maps.Marker({
         position: position,
         map: map,
         icon: image,
         animation: google.maps.Animation.DROP
       }));
       //event click marker shop
   	markers[timeout/200].addListener('click', function() {
//		alert(position.lat);
   		
		  var content = '<div class="card" style="width:300px">' +
			   ' <img class="card-img-top" src="'+ shop.image + '" alt="Card image" style="width:100%;height: 138px;">' +
			   ' <div class="card-body">' +
			     ' <h4 class="card-title">' + shop.name + '</h4>' +
			     ' <h6 class="card-text">Phone:' + shop.phone + '</h6>' +
			     ' <h6 class="card-text">Address: ' + shop.address + '</h6>' +
			     '<h6>Total Distance: <span id="total"></span></h6>' +
			      '<a href="tel:01658475125" class="btn btn-primary">Call me</a>&nbsp;&nbsp;' +
			      '<a onclick="saveHistoryRepair('+ shop.id +')" class="btn btn-success bt">Xác nhận sửa chữa</a>' +
			   '</div>' +
			 ' </div>';
		  
		  infowindow.setContent(content);
		  infowindow.setOptions({maxWidth:320});
//			 maxWidth: 320
		infowindow.open(map, markers[timeout/200]); //open infor shop
		calcRoute(userLatLng, position, directionsDisplay); //chi duong
	});
//       console.log("length" + markers.length);
     }, timeout);
     
   }
   //tinh khoang cach
   function computeTotalDistance(result) {
	   console.log(result)
	   var total = 0;
	   var myroute = result.routes[0];
	   for (var i = 0; i < myroute.legs.length; i++) {
	     total += myroute.legs[i].distance.value;
	   }
	   total = total / 1000;
	   document.getElementById('total').innerHTML = total + ' km';
	 }
   
   function clearMarkers() {
     for (var i = 0; i < markers.length; i++) {
       markers[i].setMap(null);
     }
     markers = [];
    
   }

