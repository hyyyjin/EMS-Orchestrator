<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<!-- MY CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- jsp link code -->
<link
	href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/assets/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/style.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/style2.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/assets/script/jquery-1.12.4.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/assets/script/bootstrap.min.js"
	type="text/javascript"></script>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js"></script>

<title>YapoeY EMS-Subnode Graph</title>
</head>

<body>
	<div class="yapoey-container">
		<div class="side-main-menu">
			<div class="yap-log"
				onclick="location.href='${pageContext.request.contextPath}/menu'"></div>
			<div class="yap-log-mobile"
				onclick="location.href='${pageContext.request.contextPath}/menu'"></div>
			<div class="main-menu-mobile">
				<!-- first ems 1-->
				<button class="dropdown-btn"
					onclick="location.href='${pageContext.request.contextPath}/ema?eid=${eid}'">
					<img src="img/ems_small.png" alt="">
				</button>
				<!-- end of the first ems 1-->
				<!-- Sec ems 1-->
				<button class="dropdown-btn">
					<img src="img/device_sm.png" alt="">
				</button>
				<div class="dropdown-container">
					<c:forEach items="${deviceList}" var="device">
						<button
							style="border-style: none; background-color: #5d5e59; color: #fff;"
							onclick="location.href='${pageContext.request.contextPath}/subnode?eid=${eid}&did=${device.name}'">
							<img src="img/device_small.png" alt=""> ${device.type }
							${device.name}
						</button>
						<br>
					</c:forEach>
				</div>
				<!-- end of the Sec ems 1-->
				<!-- 3rd ems 1-->
				<button class="dropdown-btn"
					onclick="location.href='${pageContext.request.contextPath}/report?eid=${eid }'">
					<img src="img/report_sm.png" alt="">
				</button>
				<!-- end of the 3rd ems 1-->
				<!-- end of the 3rd ems 1-->
			</div>
			<div class="main-menu">
				<ul>
					<li>
						<button class="dropdown-btn"
							onclick="location.href='${pageContext.request.contextPath}/ema?eid=${eid}'">
							<img src="img/ems.png" alt=""> EMA
						</button>
					</li>
					<!--new li-->
					<li>
						<button class="dropdown-btn">
							<img src="img/device_bar_main.png" alt=""> Sub Nodes <i
								class="fa fa-caret-down"></i>
						</button>
						<div class="dropdown-container">
							<c:forEach items="${deviceList}" var="device">
								<button
									style="border-style: none; background-color: #5d5e59; color: #fff;"
									onclick="location.href='${pageContext.request.contextPath}/subnode?eid=${eid}&did=${device.name}'">
									<img src="img/device.png" alt=""> ${device.type}
									${device.name}
								</button>
								<br>
							</c:forEach>
						</div>
					</li>
					<!--new li-->
					<li>
						<button class="dropdown-btn"
							onclick="location.href='${pageContext.request.contextPath}/report?eid=${eid }'">
							<img src="img/report.png" alt=""> Report
						</button>
					</li>
				</ul>
			</div>
			<div class="yap-sub-menu-footer">

				<div id="map"></div>

			</div>
		</div>

		<div class="main-page">
			<div class="main-header">
				<div class="welcome-logo"></div>
				<div class="welcome-titel">
					<h4>Dashboard for Subnode</h4>
				</div>
			</div>
			<div class="call-out-container">
				<div class="call-out">
					<div class="call-out-title">
						<h6>Subnode ID</h6>
					</div>
					<div class="call-out-box">
						<div class="call-out-value">${deviceId }</div>
						<div class="call-out-img">
							<img src="img/EMA.png" alt="">
						</div>
					</div>
				</div>
				<div class="call-out">
					<div class="call-out-title">
						<h6>Current Energy Consumed</h6>
					</div>
					<div class="call-out-box">
						<div class="call-out-value" id="power"></div>
						<div class="call-out-img">
							<img src="img/elec.png" alt="">
						</div>
					</div>
				</div>
			</div>
			<div class="main-page-container">
				<!-- starting to add code for graph from here-->
				<div class="container">
					<!-- h1>${param.pageTitle}</h1> -->
					<div id="content">${param.content}</div>
				</div>
				<!-- end of the graph scope code-->
			</div>
		</div>
	</div>

	<script>
		var dropdown = document.getElementsByClassName('dropdown-btn');
		var i;
		for (i = 0; i < dropdown.length; i++) {
			dropdown[i].addEventListener('click', function() {
				this.classList.toggle('active');
				var dropdownContent = this.nextElementSibling;
				if (dropdownContent.style.display === 'block') {
					dropdownContent.style.display = 'none';
				} else {
					dropdownContent.style.display = 'block';
				}
			});
		}
	</script>

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>
	<script>
			function initMap() {
				//map option

				var yap_logo = {
				url: 'img/yapoey_pin.png',
				//state your size parameters in terms of pixels
				size: new google.maps.Size(70, 60),
				scaledSize: new google.maps.Size(60, 60),
				origin: new google.maps.Point(0,0)}

					var myLatLng={lat:37.555697, lng:127.046257};
					var map = new google.maps.Map(document.getElementById('map'), {
						zoom: 15,
						center: myLatLng
					});

					var marker = new google.maps.Marker({
						position: myLatLng,
						map: map,
						icon:yap_logo,
						title: 'Hello World!'
					});
			}

		</script>
	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA-6hECTt9Q5DvhgQuaAk158tbPaotXw4o&callback=initMap"
		async defer></script>
</body>
</html>
