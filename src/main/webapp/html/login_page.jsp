<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_page.css" type="text/css">
		<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css">
		<title>Sign in</title>
	</head>
	<body>
		<div class="main">
	    	<p class="sign" align="center">Sign in</p>
	    	<form class="form1" method="post">
	    		<p class = "forgot" align="center">${verificationMessage}</p>
		      	<input class="un " type="text" align="center" name="username" placeholder="Username">
		      	<input class="pass" type="password" align="center" name="password" placeholder="Password">
		      	<input type="submit" class="submit" align="center" value="Sign In">
		      	<p class = "forgot" align="center">${loginErrorMessage}</p>
		      	<p class="forgot" align="center"><a href="register">Register</p>
		      	<p class="forgot" align="center"><a href="forgot">Forgot Password</p>
			</form>      
		</div>     
	</body>
</html>