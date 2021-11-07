<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/change_password_page.css"/>
		<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css">
		<title>Reset Password</title>
	</head>
	<body>
		<div class="main">
	    	<p class="sign" align="center">Reset Password</p>
	    	<form class="form1" method="post">
		      	<input class="un " type="password" align="center" name="newPassword" placeholder="Enter new password">
		      	<input class="un " type="password" align="center" name="confirmNewPassword" placeholder="Confirm new password">
		      	<input type="submit" class="submit" align="center" name="changePassword" value="Change Password">	
			</form>      
		</div>     
	</body>
</html>