<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0">
<meta charset="utf-8">
<link href="${pageContext.request.contextPath}/assets/css/map_style.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<!-- MY CSS -->
<!-- <link rel="stylesheet" -->
<%-- 	href="${pageContext.request.contextPath}/assets/css/style3.css"> --%>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<title>YapoeY EMS-Menu</title>
</head>
<body>
	<div class="main-container">
		<div class="header-bar">
			<div class="image-container"></div>
		</div>

		<div class="marker">
			<ul class="nav nav-tabs nav-justified" id="myTab" role="tablist">
				<li class="nav-item"><a class="nav-link active"
					id="pills-home-tab" data-toggle="pill" href="#pills-home"
					role="tab" aria-controls="pills-home" aria-selected="true">EMA
						Map</a></li>
				<li class="nav-item"><a class="nav-link" id="pills-profile-tab"
					data-toggle="pill" href="#pills-profile" role="tab"
					aria-controls="pills-profile" aria-selected="false">EMA Buttons</a>
				</li>
			</ul>
		</div>

		<div class="tab-content" id="pills-tabContent">
			<div class="tab-pane fade show active" id="pills-home"
				role="tabpanel" aria-labelledby="pills-home-tab">
				<div id="map"></div>
			</div>
			<div class="tab-pane fade center_mou" id="pills-profile"
				role="tabpanel" aria-labelledby="pills-profile-tab">
				<div class="center_mou">
					<c:forEach items="${nameList}" var="ema">
						<button class="button"
							onclick="location.href='${pageContext.request.contextPath}/ema?eid=${ema}'">${ema}</button>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
		
	<!-- initMap function -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/googleMap.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
		integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
		crossorigin="anonymous"></script>

	<!-- check google api key in MainController.java, or put your google api key -->
	<script
		src="https://maps.googleapis.com/maps/api/js?key=${key}&callback=initMap"
		async defer></script>

</body>
</html>