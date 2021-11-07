<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register_page.css"/>
		<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css">
		<title>Sign Up</title>
	</head>
	<body>
		<div class="main">
	    	<p class="sign" align="center">Sign Up</p>
	    	<form class="form1" method="post">
	    		<p class = "forgot" align="center">${registerErrorMessage}</p>
	    		<input class="un " type="text" align="center" name="email" placeholder="Email">
		      	<input class="un " type="text" align="center" name="username" placeholder="Username">
		      	<input class="pass" type="password" align="center" name="password" placeholder="Password">
		      	<input type="submit" class="submit" align="center" name="signUpButton" value="Sign Up">
			</form>      
		</div>     
	</body>
</html>