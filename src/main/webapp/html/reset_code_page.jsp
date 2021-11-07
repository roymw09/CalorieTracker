<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset_code_page.css"/>
		<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css">
		<title>Reset Password</title>
	</head>
	<body>
		<div class="main">
	    	<p class="sign" align="center">Reset Password</p>
	    	<form class="form1" method="post">
		      	<input class="un " type="text" align="center" name="userEnteredResetCode" placeholder="Enter reset code">
		      	<input type="submit" class="submit" align="center" name="submitResetCode" value="Confirm">
		      	<input type="hidden" align="center" name="resetCode">
		      	<input type="hidden" align="center" name="userEmail">
			</form>      
		</div>     
	</body>
</html>