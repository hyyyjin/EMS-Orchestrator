function initMap() {
	// center lat and long
	var myCenter = new google.maps.LatLng(37.556615, 127.047567);
	var itbt = new google.maps.LatLng(37.5558429, 127.0494629);

	//map option
	var option = {
		zoom : 17,
		center : myCenter
	}
	var map = new google.maps.Map(document.getElementById('map'), option);
	var myMarker = new google.maps.Marker({
		position: itbt,
		label: 'ME',
		map: map
	});

	$.get("mapdata", function(data){
		//logo_icon
		var yap_logo = {
			url : 'img/yapoey_pin.png',
			//state your size parameters in terms of pixels
			size : new google.maps.Size(70, 60),
			scaledSize : new google.maps.Size(60, 60),
			origin : new google.maps.Point(0, 0)
		};

		var infoWindow = new google.maps.InfoWindow();
		function makeContent(i) {
			return '<div class="card" style="width: 18rem;">'
			+ '<div class="card-body">'
			+ '<h5 class="card-title"><img id="pick_me" src="img/yapoey_small.png">'
			+ data[i].name + '</h5>'
			+ '<p class="card-text">Latitude: '	+ data[i].lat + '</p>'
			+ '<p class="card-text">Longitude: ' + data[i].lng + '</p>'
			+ '<p class="card-text">Description: ' + data[i].desc +'</p>'
			+ '<form class="ozbotny"><a href="/ema?eid='+data[i].name+'" class="btn btn-primary">'
			+ data[i].name + ' Panel</a></form> ' + '</div>' + '</div>';
		}

		for (var i=0; i<data.length; i++) {
			var location = new google.maps.LatLng(data[i].lat, data[i].lng);
			var marker = new google.maps.Marker({
				position: location,
				icon: yap_logo,
				map: map
			});
			var path = new google.maps.Polyline({
				path: [itbt, location],
				strokeColor: "#0000FF",
				strokeOpacity: 0.8,
				strokeWeight: 1
			});
			path.setMap(map);
			google.maps.event.addListener(marker, 'click', (function(marker, i) {
				return function() {
					infoWindow.setContent(makeContent(i));
					infoWindow.open(map, marker);
				}
			}) (marker, i));
		}
	});
}