<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Produkt Register</title>

<!-- Einbindung von CSS -->
<link rel="stylesheet" href="resources/css/cadastro.css">
<link rel="stylesheet" href="resources/css/darstellungtable.css">

</head>
<body>
	<a href="acessliberado.jsp"> Home </a>
	<a href="nutzerregistrierung.jsp"> Nutzer registrieren </a>

	<br>
	<center>
		<h1>Produkt Register</h1>

		<!-- 	Fehlermeldung muss angepasst werden -->
		<h1 style="color: red"; >${fehlerMeldungProdukt}</h1>
	</center>

	<!-- Formular  -->
	<form action="produktBearbeitung" method="post" id="formProdukt"
		onsubmit="return eingabeValidierung() ? true : false">

		<!-- 	 Hier werde ich eine Tabelle erstellen -->
		<ul class="form-style-1">
			<li>
				<table>
					<!-- erste Spalte Nutzerid-->
					<tr>
						<td>ProduktId:</td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							value="${produktListe.id}"></td>
					</tr>
					<tr>
						<!-- zweite Spalte Produktname-->
						<td>Produktname:</td>
						<td><input type="text" id="beschreibung" name="beschreibung"
							value="${produktListe.beschreibung}"></td>
					</tr>
					<tr>
						<!-- vierte Spalte Name-->
						<td>Preis:</td>
						<td><input type="text" id="preis" name="preis"
							value="${produktListe.preis}"></td>
					</tr>
					<tr>
						<td>Menge:</td>
						<td><input type="text" id="anzahl" name="anzahl"
							value="${produktListe.anzahl}"></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Speichern" /> <input
							type="submit" value="Abbrechen"
							onclick="document.getElementById('formProdukt').action='produktBearbeitung?aktion=reset" /></td>
					</tr>
				</table>
			</li>
		</ul>
	</form>
	<!-- Tabelle -->

	<div id="wrapper">
		<table id="keywords" cellspacing="0" cellpadding="0">
			<h1>aufgelistete Produkte</h1>
			<tr>
				<th>Id</th>
				<th>Beschreibung</th>
				<th>Preis</th>
				<th>Menge</th>
				<th>Editieren</th>
				<th>Löschen</th>
			</tr>
			<c:forEach items="${produktliste}" var="produkte">
				<tr>
					<td style="width: 150px"><c:out value="${produkte.id}" /></td>
					<td style="width: 150px"><c:out
							value="${produkte.beschreibung}" /></td>
					<td style="width: 150px"><c:out value="${produkte.preis}" /></td>
					<td style="width: 150px"><c:out value="${produkte.anzahl}" /></td>

					<td><a
						href="produktBearbeitung?aktion=editieren&produkte=${produkte.id}">
							<img title="Editieren" src="resources/img/edit_button.png"
							width="20px" height="20px">
					</a></td>
					<td><a
						href="produktBearbeitung?aktion=loeschen&produkte=${produkte.id}"><img
							title="Löschen" src="resources/img/delete-button.png"
							width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
	function eingabeValidierung(){
		
		if(document.getElementById("beschreibung").value == '') {
			alert('Geben Sie den Produktnamen ein.');
			return false; 
		
		}else if(document.getElementById('preis').value == ''){
			alert('Geben Sie den Preis ein.');
			return false; 
			
		}else if(document.getElementById('anzahl').value == ''){
			alert('Geben Sie die Produktmenge ein.');
			return false; 
		}
		return true; 
	}

</script>


</body>
</html>