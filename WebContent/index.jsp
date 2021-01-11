<jsp:useBean id="calcula" class="beans.BeanFinanzJsp"
	type="beans.BeanFinanzJsp" scope="page" />

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="myprefix" uri="WEB-INF/testtag.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Finanz App (app-finanz-jsp)</title>

<!-- Einbindung von CSS -->
<link rel="stylesheet" href="resources/css/estilo.css">

</head>
<body>

	<div class="login-page">
		<div class="form">
			<form class="login-form" action="LoginServlet" method="post " class="login-form" >
				Login: <input type="text" id="login" name="login" placeholder="username"> <br />
				Pass: <input 	type="password" id="password" name="password" placeholder="passwort"><br />
				<button type="submit" value="Send login">Senden</button>
			</form>
		</div>
	</div>

<!-- 	<form action="LoginServlet" method="post" class="register-form"> -->
<!-- 		Login: <input type="text" id="login" name="login" -->
<!-- 			placeholder="username"> <br /> Pass: <input type="password" -->
<!-- 			id="password" name="password"><br /> -->
<!-- 		<button type="submit" value="Send login">Senden</button> -->
<!-- 	</form> -->

</body>
</html>