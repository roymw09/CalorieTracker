<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/verify_email_page.css"/>
		<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css">
		<title>Verify Email</title>
	</head>
	<body>
		<div class="main">
	    	<p class="sign" align="center">Verify Email</p>
	    	<form class="form1" method="post">
		      	<input class="un " type="text" align="center" name="authCode" placeholder="Authentication Code">
		      	<input type="submit" class="submit" align="center" name="verifyButton" value="Verify">
		      	<input type="hidden" align="center" name="user">
			</form>      
		</div>     
	</body>
</html>