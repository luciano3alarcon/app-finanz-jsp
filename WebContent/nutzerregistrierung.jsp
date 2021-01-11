<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registrierung für neue Nutzer</title>
<!-- Einbindung von CSS -->
<link rel="stylesheet" href="resources/css/cadastro.css">
<link rel="stylesheet" href="resources/css/darstellungtable.css">

</head>
<body>
	<br>
	<center>
		<h1>Registrierung</h1>
	</center>

	<form action="speichernNutzer" method="post">
		<!-- 	 Hier werde ich eine Tabelle erstellen -->
		<ul class="form-style-1">
			<li>
				<table>
					<!-- erste Spalte Nutzerid-->
					<tr>
						<td>Userid:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" value="${user.id}"></td>
					</tr>
					<tr>
						<!-- zweite Spalte Nutzername-->
						<td>Nutzername:</td>
						<td><input type="text" id="login" name="login"
							value="${user.login}"></td>
					</tr>
					<tr>
						<!-- zweite Spalte Passwort -->
						<td>Password:</td>
						<td><input type="password" id="password" name="password"
							value="${user.password}" /></td>
					</tr>
					<tr>
						<!-- vierte Spalte Name-->
						<td>Name:</td>
						<td><input type="text" id="name" name="name"
							value="${user.name}"></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Speichern" /></td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<div id="wrapper">
		<table id="keywords" cellspacing="0" cellpadding="0">
			<h1>registriere Nutzer</h1>
			<tr>
				<th>Id</th>
				<th>Nutzername</th>
				<th>Passwort</th>
				<th>Name</th>
				<th>Editieren</th>
				<th>Löschen</th>
			</tr>
			<c:forEach items="${liste}" var="user">
				<tr>
					<td style="width: 150px"><c:out value="${user.id}" /></td>
					<td style="width: 150px"><c:out value="${user.login}" /></td>
					<td><c:out value="${user.password}" /></td>
					<td><c:out value="${user.name}" /></td>
					<td><a href="speichernNutzer?acao=editieren&user=${user.login}"><img title="Editieren" src="resources/img/edit_button.png" width="20px" height="20"> </a></td>
					<td><a href="speichernNutzer?acao=loeschen&user=${user.login}"><img title="Löschen" src="resources/img/delete-button.png"  width="20px" height="20"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>