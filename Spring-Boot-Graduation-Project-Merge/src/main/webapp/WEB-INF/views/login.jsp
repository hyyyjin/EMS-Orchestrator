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
	href="${pageContext.request.contextPath}/assets/css/style1.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<title>YapoeY EMS-Log in</title>

<link rel="icon" type="image/png" href="images/icons/favicon.ico" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/fonts/iconic/css/material-design-iconic-font.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/animate/animate.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/select2/select2.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/assets/css/util.css">
</head>
<body>
	<div class="main-container">
		<div class="header-bar">
			<div class="image-container"></div>
		</div>
		<div class="main-body">
			<div class="login-tab">

				<form class="login100-form validate-form" name="loginform"
					onSubmit="return validateForm();" action="/menu" method="post">

					<div class="wrap-input100 validate-input"
						data-validate="Valid email is: a@b.c">
						<input class="input100" type="text" name="email" id="email">
						<span class="focus-input100" data-placeholder="Email"></span>
					</div>

					<div class="wrap-input100 validate-input"
						data-validate="Enter password">
						<span class="btn-show-pass"> <i class="zmdi zmdi-eye"></i>
						</span> <input class="input100" type="password" name="pass" id="pass">
						<span class="focus-input100" data-placeholder="Password"></span>
					</div>

					<div class="container-login100-form-btn">
						<div class="wrap-login100-form-btn">
							<div class="login100-form-bgbtn"></div>

							<button class="login100-form-btn" value="Login">Login</button>

						</div>
					</div>

					<div class="text-center p-t-115">
						<span class="txt1"> Don't have an account? </span> <a
							class="txt2" href="#"> Sign Up </a>
					</div>
				</form>


			</div>
		</div>
	</div>
	
	<script>
		function validateForm() {
			var un = document.loginform.email.value;
			var pw = document.loginform.pass.value;
			// currently username and password is fixed
			var username = "yapoey@gmail.com";
			var password = "123456";
			if ((un == username) && (pw == password)) {
				return true;
			} else {
				alert("Login was unsuccessful, please check your username and password");
				return false;
			}
		}
	</script>

	<!--===============================================================================================-->
	<script
		src="${pageContext.request.contextPath}/vendor/jquery/jquery-3.2.1.min.js"></script>
	<!--===============================================================================================-->
	<script
		src="${pageContext.request.contextPath}/vendor/animsition/js/animsition.min.js"></script>
	<!--===============================================================================================-->
	<script
		src="${pageContext.request.contextPath}/vendor/bootstrap/js/popper.js"></script>
	<script
		src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.min.js"></script>
	<!--===============================================================================================-->
	<script
		src="${pageContext.request.contextPath}/vendor/select2/select2.min.js"></script>
	<!--===============================================================================================-->
	<script
		src="${pageContext.request.contextPath}/vendor/daterangepicker/moment.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/vendor/daterangepicker/daterangepicker.js"></script>
	<!--===============================================================================================-->
	<script
		src="${pageContext.request.contextPath}/vendor/countdowntime/countdowntime.js"></script>
	<!--===============================================================================================-->
	<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
