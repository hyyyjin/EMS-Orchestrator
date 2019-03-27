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
<link
	href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/assets/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/style.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/style4.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/assets/script/jquery-1.12.4.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/assets/script/bootstrap.min.js"
	type="text/javascript"></script>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"
	type="text/javascript"></script>
<title>YapoeY EMS-EMA Report</title>

</head>
<body>

	<div class="main-container">
		<div class="header-bar">
			<button style="border-style: none; background-color: #53544d;"
				onclick="location.href='${pageContext.request.contextPath}/ema?eid=${eid}'">
				<img style="width: 200px; height: 90%;" src="img/yapoey.png" alt="">
			</button>
		</div>

		<div class="main-body">
			<div class="header"></div>
			<div class="container">
				<div class="sub_main_body_right">
					<div class="table-container">
						<table>
							<tr>
								<th>Name</th>
								<th>Value</th>
							</tr>
							<tr>
								<td>Number of Device :</td>
								<td id="count"></td>
							</tr>
							<tr>
								<td>Total Energy Consumption :</td>
								<td id="power"></td>
							</tr>
							<tr>
								<td>Average Consumption :</td>
								<td id="avg"></td>
							</tr>
							<tr>
								<td>Max Energy :</td>
								<td id="max"></td>
							</tr>
							<tr>
								<td>Min Energy :</td>
								<td id="min"></td>
							</tr>
							<tr>
								<td>Saved Energy :</td>
								<td>-</td>
							</tr>
							<tr>
								<td>Unit Price :</td>
								<td>${price }${itemUnit }</td>
							</tr>
							<tr>
								<td>Total Price :</td>
								<td id="total"></td>
							</tr>
							<tr>
								<!-- value for each attribute-->
							</tr>
						</table>
					</div>
				</div>
				<div class="sub_main_body_left" id="content">${param.content}
				</div>
			</div>
		</div>
	</div>

</body>
</html>
