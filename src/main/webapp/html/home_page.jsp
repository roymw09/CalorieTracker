<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home_page.css" />
    <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css">
    <title>Home</title>
</head>

<body>
    <div class="float-container">
        <div class="main">
            <p class="sign" align="center">Daily Calorie In-take</p>
            <ul>
	            <c:forEach items="${userListOfFood}" var="foodList">
	            	<li>
		                <p class="forgot" align="center">${foodList.foodName}: ${foodList.calories}</p>
		                <form method="post">
		                	<button class="submit" type="submit" name="deleteFromDailyList">Delete</button>
		                	<input type="hidden" name="foodId" value="${foodList.food_id}"/>
		                </form>
		                <br />
	                </li>
	            </c:forEach>
            </ul>
            
            <p class="forgot" align="center">Total Daily Calories: ${totalDailyCalories}</p>
        </div>
        <div class="main">
        <form class="form1" method="post">
            <p class="sign" align="center">Home</p>
            <p class="forgot" align="center">Welcome, ${loggedInUser}</p>
            <p class="forgot" align="center"><a href="logout">Logout</a></p>
            <input class="un " type="search" name="foodSearch" placeholder="Search food">
            <input type="submit" class="submit" name="searchForFood" value="Search">	
            </form>
        </div>
        <div class="main">
            <p class="sign" align="center">Search Results</p>
            <ul>
            <c:forEach items="${searchedListOfFood}" var="foodListSearch">
            		<li>
            			<p class="forgot" align="center">${foodListSearch.foodName}</p>
            			<p class="forgot" align="center">Calories: ${foodListSearch.calories}</p>
            			<p class="forgot" align="center">Fat: ${foodListSearch.fat}g</p>
		            	<p class="forgot" align="center">Protein: ${foodListSearch.protein}g</p>
		            	<p class="forgot" align="center">Carbs: ${foodListSearch.carbohydrates}g</p>
            			<br />
                		<form method="post"> 
                    <!--add below inputs-->
                   			<input type="hidden" name="foodName" value="${foodListSearch.foodName}"/>
                    		<input type="hidden" name="foodCalories" value="${foodListSearch.calories}"/>
                    		<input type="hidden" name="foodFat" value="${foodListSearch.fat}"/>
                    		<input type="hidden" name="foodProtein" value="${foodListSearch.protein}"/>
                    		<input type="hidden" name="foodCarbs" value="${foodListSearch.carbohydrates}"/>
                    		<button class="submit" type="submit" name="addToDailyList">Add</button>
                    	</form>
                    </li>	
        		<br />
            </c:forEach>
            </ul>
        </div>
    </div>
</body>
</html>