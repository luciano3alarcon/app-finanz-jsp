package servlet;

import java.io.IOException;
import java.io.InputStream;

import java.util.Base64;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.ByteArrayOutputStream;

import beans.BeanFinanzJsp;
import dao.DaoNutzer;
import validator.CheckEmail;
import validator.CheckPhone;
import validator.IsPasswordValid;

@WebServlet("/speichernNutzer")
@MultipartConfig
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
			String plz = request.getParameter("plz");
			String strasse = request.getParameter("strasse");
			String stadt = request.getParameter("stadt");
			String bundesland = request.getParameter("bundesland");

			BeanFinanzJsp nutzer = new BeanFinanzJsp();
			nutzer.setId((id != null && !id.isEmpty()) ? Long.parseLong(id) : null);
			nutzer.setLogin(login);
			nutzer.setPassword(passwort);
			nutzer.setName(name);
			nutzer.setRufnummer(rufnummer);
			nutzer.setEmail(email);
			nutzer.setPlz(plz);
			nutzer.setStrasse(strasse);
			nutzer.setStadt(stadt);
			nutzer.setBundesland(bundesland);

			try {

				if (ServletFileUpload.isMultipartContent(request)) {
					Part atribBild = request.getPart("uploadbild");
					String bildBase64 = Base64.getEncoder()
							.encodeToString(converteStreamZumByte(atribBild.getInputStream()));
					nutzer.setBild(bildBase64);
					nutzer.setContentType(atribBild.getContentType());
				}

				/* hier wird ein Nutzter erstellt */
				String fehlerMeldung = null;
				boolean weiterGehen = true;

				if (id == null || id.isEmpty()) {

					if (login.matches("admin") || login.matches("")) {
						request.setAttribute("fehlerMeldung", "Überprüfen Sie Ihren Nutzername.");
						weiterGehen = false;

					} else if (!this.isPassValid.isValidPassword(passwort)) {
						request.setAttribute("fehlerMeldung",
								"Benutzen Sie in Ihrem Password Gross- und Kleinbuchstab, sowie Ziffern.");
						weiterGehen = false;

					} else if (name.isEmpty() || name.matches("")) {
						request.setAttribute("fehlerMeldung", "Der Name darf nicht leer bleiben..");
						weiterGehen = false;

					} else if (!this.checkPhone.isPhoneValid(rufnummer)) {
						request.setAttribute("fehlerMeldung", "Überprüfen Sie Ihre Rufnummer.");
						weiterGehen = false;

					} else if (!this.ckechEmail.isValidEmailAdresse(email) || email == null || email.isEmpty()) {
						request.setAttribute("fehlerMeldung", "Diese E-Mailadresse ist ungültig.");
						weiterGehen = false;

					} else {
						request.setAttribute("fehlerMeldung", "Der Nutzer wurde erfolgreich registriert");
						this.daoNutzer.nutzerSpeichernDB(nutzer);
					}
				}

				else if (id != null) {
					if (login.matches("admin")) {
						request.setAttribute("fehlerMeldung", "Sie haben keine Berechtigung, den 'Admin' zu ändern.");
						weiterGehen = false;

					if (!this.isPassValid.isValidPassword(passwort)) {
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
						request.setAttribute("fehlerMeldung", "Die Information wurde aktualisiert.");
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

	/* Converter eingegangen image Bilddate in byte[] */
	private byte[] converteStreamZumByte(InputStream imageBild) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imageBild.read();
		while (reads != -1) {
			baos.write(reads);
			reads = imageBild.read();
		}
		return baos.toByteArray();
	}

}