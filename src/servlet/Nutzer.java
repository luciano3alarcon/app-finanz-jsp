package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanFinanzJsp;
import dao.DaoNutzer;
import validator.CheckEmail;
import validator.CheckPhone;
import validator.IsPasswordValid;

@WebServlet("/speichernNutzer")
public class Nutzer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DaoNutzer daoNutzer = new DaoNutzer();
	IsPasswordValid isPassValid = new IsPasswordValid();
	CheckEmail ckechEmail = new CheckEmail();
	CheckPhone checkPhone = new CheckPhone();

	public Nutzer() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao"); // Siehe class nutzerregistrierung.jsp
			String user = request.getParameter("user");

			if (acao.equalsIgnoreCase("loeschen")) {
				this.daoNutzer.loeschen(user);

				RequestDispatcher view = request.getRequestDispatcher("/nutzerregistrierung.jsp");
				request.setAttribute("liste", this.daoNutzer.aufgelisteteNutzer());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("editieren")) {

				BeanFinanzJsp beanEdit = this.daoNutzer.consulta(user);
				RequestDispatcher view = request.getRequestDispatcher("/nutzerregistrierung.jsp");
				request.setAttribute("user", beanEdit);
				view.forward(request, response);

				this.daoNutzer.update(beanEdit);

			} else if (acao.equalsIgnoreCase("listartodos")) {

				RequestDispatcher view = request.getRequestDispatcher("/nutzerregistrierung.jsp");
				request.setAttribute("liste", this.daoNutzer.aufgelisteteNutzer());
				view.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Acao kommt aus dem Obj: nutzerregistrierung
		String acaoAbbrechen = request.getParameter("acao");

		if (acaoAbbrechen != null && acaoAbbrechen.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher view = request.getRequestDispatcher("/nutzerregistrierung.jsp");
				request.setAttribute("liste", this.daoNutzer.aufgelisteteNutzer());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String passwort = request.getParameter("password");
			String name = request.getParameter("name");
			String rufnummer = request.getParameter("rufnummer");
			String email = request.getParameter("email");

			BeanFinanzJsp nutzer = new BeanFinanzJsp();
			nutzer.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			nutzer.setLogin(login);
			nutzer.setPassword(passwort);
			nutzer.setName(name);
			nutzer.setRufnummer(rufnummer);
			nutzer.setEmail(email);

			try {
				/* hier wird ein Nutzter erstellt */
				String fehlerMeldung = null;
				boolean weiterGehen = true;

				if (id == null || id.isEmpty()) {

					if (login.isEmpty() || login.matches("admin")) {
						request.setAttribute("fehlerMeldung", "Überprüfen Sie Ihren Nutzername.");
						weiterGehen = false;

					} else if (!this.isPassValid.isValidPassword(passwort)) {
						request.setAttribute("fehlerMeldung", "Benutzen Sie Gross- und Kleinbuchstab, sowie Ziffern.");
						weiterGehen = false;

					} else if (name.isEmpty() || name.matches("") ) {
						request.setAttribute("fehlerMeldung", "Der Name darf nicht leer bleiben..");
						weiterGehen = false;

					} else if (!this.checkPhone.isPhoneValid(rufnummer)) {
						request.setAttribute("fehlerMeldung", "Überprüfen Sie Ihre Rufnummer.");
						weiterGehen = false;

					} else if (!this.ckechEmail.isValidEmailAdresse(email) || email == null || email.isEmpty()) {
						request.setAttribute("fehlerMeldung", "Diese E-Mailadresse ist ungültig.");
						weiterGehen = false;

					} else {
						this.daoNutzer.nutzerSpeichernDB(nutzer);
					}
				}

				else if (id != null && !id.isEmpty()) {
					if (login.matches("admin")) {
						request.setAttribute("fehlerMeldung", "Der Nutzername wird bereits verwendet.");
						weiterGehen = false;

					} else if (!this.isPassValid.isValidPassword(passwort)) {
						request.setAttribute("fehlerMeldung", "Benutzen Sie Gross- und Kleinbuchstab, sowie Ziffern.");
						weiterGehen = false;

					} else if (name.isEmpty()) {
						request.setAttribute("fehlerMeldung", "Der Name darf nicht leer bleiben..");
						weiterGehen = false;

					} else if (!this.checkPhone.isPhoneValid(rufnummer)) {
						request.setAttribute("fehlerMeldung", "Überprüfen Sie Ihre Rufnummer");
						weiterGehen = false;

					} else if (!this.ckechEmail.isValidEmailAdresse(email) || email == null || email.isEmpty()) {
						request.setAttribute("fehlerMeldung", "Diese E-Mailadresse ist ungültig.");
						weiterGehen = false;

					} else {
						this.daoNutzer.update(nutzer);
					}
				}

				if (!weiterGehen) {
					request.setAttribute("user", nutzer);
				}

				RequestDispatcher view = request.getRequestDispatcher("/nutzerregistrierung.jsp");
				request.setAttribute("liste", this.daoNutzer.aufgelisteteNutzer());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}