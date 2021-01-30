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
import validator.CheckLogin;
import validator.CheckPhone;
import validator.IsPasswordValid;

@WebServlet("/speichernNutzer")
public class Nutzer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DaoNutzer daoNutzer = new DaoNutzer();
	IsPasswordValid isPassValid = new IsPasswordValid();
	CheckEmail ckechEmail = new CheckEmail();
	CheckPhone checkPhone = new CheckPhone();
	CheckLogin checkLogin = new CheckLogin();

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
			nutzer.setId(!id.isEmpty() ? Long.parseLong(id) : 0);
			nutzer.setLogin(login);
			nutzer.setPassword(passwort);
			nutzer.setName(name);
			nutzer.setRufnummer(rufnummer);
			nutzer.setEmail(email);

			try {

				/* hier wird ein Nutzter erstellt */
				
				if (id == null || id.isEmpty() && this.daoNutzer.validateLogin(login)) {
					if (this.checkLogin.isLoginValid(login) ) {
						request.setAttribute("messageNutzerName", "Überprüfen Sie Ihren Nutzername."); 
					}else if (!this.isPassValid.isValidPassword(passwort)) {
						request.setAttribute("messagePass", "Benutzen Sie Gross- und Kleinbuchstab, sowie Ziffern."); 
					}else if (!this.ckechEmail.isValidEmailAdresse(email) || email == null || email.isEmpty()) {
						request.setAttribute("messageEmail", "Diese E-Mailadresse ist ungültig.");
					} else if (this.checkPhone.isPhoneValid(rufnummer)) {
						request.setAttribute("messageRufnummer", "Überprüfen Sie Ihre Rufnummer");
					}else {
						this.daoNutzer.nutzerSpeichernDB(nutzer);	
					}

				} else if (id != null && !id.isEmpty()) {
					if (!this.daoNutzer.validateLoginUpdate(login, id)) {/* Login = Nutzername */
						request.setAttribute("message", "Dieser Username wird bereits verwendet.");
					}else if (!this.isPassValid.isValidPassword(passwort)) {
						request.setAttribute("message", "Benutzen Sie Gross- und Kleinbuchstab, sowie Ziffern.");
					} else if (this.checkPhone.isPhoneValid(rufnummer)) {
						request.setAttribute("message", "Überprüfen Sie Ihre Rufnummer");
					}else if (!this.ckechEmail.isValidEmailAdresse(email) || email == null || email.isEmpty()) {
						request.setAttribute("message", "Diese E-Mailadresse ist ungültig.");
					}else {
					this.daoNutzer.update(nutzer);
					}
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