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

<!-- Adicionando JQuery -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
	crossorigin="anonymous"></script>

</head>
<body>

	<!-- Navegationsmenü  -->
	<a href="acessliberado.jsp">Home</a>
	<a href="produktBearbeitung?aktion=aufgelisteteprodukte">Produkt
		eintragen</a>
	<a href="index.jsp" onclick="alert('Sie werden abgemeldet.')">Abmelden
	</a>

	<br>
	<center>
		<h1>Nutzer</h1>
		<!-- 	Fehlermeldung muss angepasst werden -->
		<h1 style="color: red"; >${fehlerMeldung}</h1>

	</center>

	<!-- 	Javascript Validierung -->
	<!-- 	Wenn das Formular eine Updload-Datei bekommt, braucht es  enctype="multipart/form-data.  Das gilt nicht für Ajax-->
	<form action="speichernNutzer" method="post" id="formUser"
		onsubmit=" return eingabeValidierung() ? true : false"
		enctype="multipart/form-data">

		<!-- Eingabe Formular  -->
		<!-- 	 Hier werde ich eine Tabelle erstellen -->
		<ul class="form-style-1">
			<li>
				<table>
					<!-- erste Linie -->
					<tr>
						<td><input type="text" readonly="readonly" id="id" name="id"
							value="${user.id}" placeholder="schreiben geschützt"></td>

						<td><input type="text" id="login" name="login"
							value="${user.login}" placeholder="Username"></td>
					</tr>

					<!-- zweite Linie -->
					<tr>
						<td><input type="text" id="email" name="email"
							value="${user.email}" placeholder="E-Mailadresse"></td>

						<td><input type="password" id="password" name="password"
							value="${user.password}" placeholder="Password" /></td>
					</tr>

					<!-- dritte Linie -->
					<tr>
						<td><input type="text" id="name" name="name"
							value="${user.name}" placeholder="Vor- und Nachname"></td>

						<td><input type="text" id="strasse" name="strasse"
							value="${user.strasse}" placeholder="Adresse und Hausnummer"></td>
					</tr>

					<!-- vierte Linie -->
					<tr>
						<td><input type="text" id="plz" name="plz"
							onblur="plzAbfrage();" value="${user.plz}"
							placeholder="Postleitzahl"></td>

						<td><input type="text" id="stadt" name="stadt"
							value="${user.stadt}" placeholder="Stadt"></td>
					</tr>

					<!-- fünfte Linie -->
					<tr>
						<td><input type="text" id="bundesland" name="bundesland"
							value="${user.bundesland}" placeholder="Bundesland"></td>

						<td><input type="text" id="rufnummer" name="rufnummer"
							value="${user.rufnummer}" placeholder="Telefonummer"></td>
					<tr>

						<!-- Upload von Dateien -->
					<tr>
						<td>Bild:</td>
						<td><input type="file" id="uploadbild" name="uploadbild"
							value="Bild" /></td>
					</tr>
					<tr>
						<td>Dokument:</td>
						<td><input type="file"></td>
					</tr>

					<tr>
						<td><input type="submit" value="Speichern" />
						<td><input type="submit" value="Abbrechen"
							onclick="document.getElementById('formUser').action='speichernNutzer?acao=reset'" />
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<!-- Tabelle -->
	<div id="wrapper">
		<table id="keywords" cellspacing="0" cellpadding="0">
			<h1>Registrierte Nutzer</h1>
			<tr>
				<th>Id</th>
				<th>Nutzername</th>
				<th>Name</th>
				<th>Bild</th>
				<th>Rufnummer</th>
				<th>E-Mail</th>
				<th>PLZ</th>
				<th>Strasse</th>
				<th>Stadt</th>
				<th>Bundesland</th>
				<th>Editieren</th>
				<th>Löschen</th>
			</tr>
			<c:forEach items="${liste}" var="user">
				<tr>
					<td style="width: 150px"><c:out value="${user.id}" /></td>
					<td style="width: 150px"><c:out value="${user.login}" /></td>
					<td style="width: 150px"><c:out value="${user.name}" /></td>
					<td><img src='<c:out value="${user.tempBildUser}"/>' alt="image user" title="image user" width="32px" height="32px"   /></td>
					<td style="width: 150px"><c:out value="${user.rufnummer}" /></td>
					<td style="width: 150px"><c:out value="${user.email}" /></td>
					<td style="width: 150px"><c:out value="${user.plz}" /></td>
					<td style="width: 650px"><c:out value="${user.strasse}" /></td>
					<td style="width: 150px"><c:out value="${user.stadt}" /></td>
					<td style="width: 150px"><c:out value="${user.bundesland}" /></td>

					<td><a href="speichernNutzer?acao=editieren&user=${user.id }"><img
							title="Editieren" src="resources/img/edit_button.png"
							width="20px" height="20px" /> </a></td>

					<td><a href="speichernNutzer?acao=loeschen&user=${user.id}"><img
							title="Löschen" src="resources/img/delete-button.png"
							width="20px" height="20px" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<!-- JavaScript Feldern -->
	<script type="text/javascript">
		function eingabeValidierung() {

			if (document.getElementById("login").value == '') { // Validierung Username  
				alert('Geben Sie Ihren Username ein.');
				return false;
			} else if (document.getElementById("password").value == '') { //Validierung Passwort:
				alert('Geben Sie Ihr Passwort ein.');
				return false;
			} else if (document.getElementById("name").value == '') { //Validierung Name:
				alert('Geben Sie Ihren Namen ein.');
				return false;
			} else if (document.getElementById("rufnummer").value == '') { //Validierung Rufnummer:
				alert('Geben Sie Ihre Rufnummer ein.');
				return false;
			} else if (document.getElementById("email").value == '') { //Validierung E-Mail:
				alert('Geben Sie Ihre E-Mail ein.');
				return false;
			}
			return true; //Wenn alle Eingabe korrekt sind, landet die Code hier. 
		}

		function plzAbfrage() {

			var cep = $("#cep").val();
			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {

						if (!("erro" in dados)) {
							$("cep").val(dados.cep);
							$("#strasse").val(dados.logradouro);
							$("#stadt").val(dados.localidade);
							$("#bundesland").val(dados.uf);

						} else {
							alert("CEP não encontrado.");
							$("#cep").val('');
							$("#strasse").val('');
							$("#stadt").val('');
							$("#bundesland").val('');
						}
					});
		}
	</script>

</body>
</html>