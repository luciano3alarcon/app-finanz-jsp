<jsp:useBean id="calcula" class="beans.BeanFinanzJsp"
	type="beans.BeanFinanzJsp" scope="page" />

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Finanz App (app-finanz-jsp)</title>
</head>
<body>
	<br />
	<center>
		<h2>Willkomen zum Schulungssystem</h2>

		<br>
		<table>
			<tr>
				<td align="center"><a href="speichernNutzer?acao=listartodos"><img
						title="Registrierung"
						alt="Klicken Sie hier, sich zu registrieren."
						src="resources/img/registrierung_icon.jpg" width="70px"
						height="70px"></a></td>
				<td align="center"><a
					href="produktBearbeitung?aktion=aufgelisteteprodukte"><img
						title="Produkte" alt="Klicken Sie hier die Produkte zu sehen"
						src="resources/img/products.jpg" width="70px" height="70px">
				</a></td>
			</tr>
			<tr>
				<td>Nutzer registrieren</td>
				<td>Produkt registrieren</td>
			</tr>
		</table>
	</center>

</body>
</html>