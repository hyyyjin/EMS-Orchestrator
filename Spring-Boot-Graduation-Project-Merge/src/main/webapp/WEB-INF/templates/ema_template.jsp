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
<title>YapoeY EMS-EMA Graph</title>
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
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/data.js"></script>

<!-- script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script> 1-->
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
					onclick="location.href='${pageContext.request.contextPath}/ema?eid=${eid}'"
					type="button">
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
							<img src="img/device_small.png" alt="">${device.type}
							${device.name}
						</button>
					</c:forEach>
				</div>
				<!-- end of the Sec ems 1-->
				<!-- 3rd ems 1-->
				<button class="dropdown-btn"
					onclick="location.href='${pageContext.request.contextPath}/report?eid=${eid}'">
					<img src="img/report_sm.png" alt="">
				</button>
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
									<img src="img/device.png" alt="">${device.type}
									${device.name}
								</button>
								<br>
							</c:forEach>
						</div>
					</li>
					<!--new li-->
					<li>
						<button class="dropdown-btn"
							onclick="location.href='${pageContext.request.contextPath}/report?eid=${eid}'">
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
					<h4>Dashboard for EMA</h4>
				</div>
				<div class="on-off-btn">
					<!-- form -->
					<!-- Button trigger modal -->
					<button type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#exampleModalLong">Event</button>
					<!-- Modal -->
					<div class="modal fade" id="exampleModalLong" tabindex="-1"
						role="dialog" aria-labelledby="exampleModalLongTitle"
						aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLongTitle">Event
										Form</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<!-- Example single danger button -->
									<%-- <select class="form-control">
										<c:forEach items="${yap_device}" var="tempDevice">
											<option>EMA ${tempDevice.deviceName}</option>
										</c:forEach>
									</select> --%>
									<form name="eventForm" action="event" method="POST">
										<input type="hidden" name="destEMA" value=${eid }> <input
											class="form-control" type="text" placeholder="Start YMD"
											name="startYMD"> <input class="form-control"
											type="text" placeholder="Start Time" name="starttime">
										<input class="form-control" type="text"
											placeholder="Threshold" name="threshold">
										<button type="submit" value="Submit form"
											class="btn btn-primary">Distribute Event</button>
									</form>
								</div>
							</div>
						</div>
					</div>
					<!-- end of the form-->
				</div>
			</div>

			<div class="call-out-container">
				<div class="call-out">
					<div class="call-out-title">
						<h6>Number of Subnodes</h6>
					</div>
					<div class="call-out-box">
						<div class="call-out-value" id="count">${count }</div>
						<div class="call-out-img">
							<img src="img/device_bar.png" alt="">
						</div>
					</div>
				</div>
				<div class="call-out">
					<div class="call-out-title">
						<h6>Total Energy Consumption</h6>
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
				<div class="container_graph" id="score">
					<!-- h1>${param.pageTitle}</h1> -->
					<div id="content">${param.content}</div>
				</div>
				<!-- end of the graph scope code-->
			</div>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script>
		$(function() {
			/*  Submit form using Ajax */
			$('button[type=submit]').click(function(e) {
				//Prevent default submission of form
				e.preventDefault();
				$.ajax({
					type : "POST",
					url : 'event',
					data : $('form[name=eventForm]').serialize()
				});
				$('input[type=text]').val('');
			});
		});
	</script>

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
	<script>
		function initMap() {
			//map option

			var yap_logo = {
				url : 'img/yapoey_pin.png',
				//state your size parameters in terms of pixels
				size : new google.maps.Size(70, 60),
				scaledSize : new google.maps.Size(60, 60),
				origin : new google.maps.Point(0, 0)
			}

			var myLatLng = {
				lat : 37.555697,
				lng : 127.046257
			};
			var map = new google.maps.Map(document.getElementById('map'), {
				zoom : 15,
				center : myLatLng
			});

			var marker = new google.maps.Marker({
				position : myLatLng,
				map : map,
				icon : yap_logo,
				title : 'Hello World!'
			});
		}
	</script>
	<!-- check google api key in MainController.java, or put your google api key -->
	<script
		src="https://maps.googleapis.com/maps/api/js?key=${key}&callback=initMap"
		async defer></script>

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
</body>
</html>
