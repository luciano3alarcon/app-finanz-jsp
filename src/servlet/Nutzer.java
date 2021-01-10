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

@WebServlet("/speichernNutzer")
public class Nutzer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoNutzer daoNutzer = new DaoNutzer();

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
				
				BeanFinanzJsp beanEdit = this.daoNutzer.editieren(user);
				RequestDispatcher view = request.getRequestDispatcher("/nutzerregistrierung.jsp");
				request.setAttribute("user", beanEdit);
				view.forward(request, response);
				
//				this.daoNutzer.update(beanEdit );
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String login = request.getParameter("login");
		String passwort = request.getParameter("password");

		BeanFinanzJsp nutzer = new BeanFinanzJsp();
		nutzer.setLogin(login);
		nutzer.setPassword(passwort);

		this.daoNutzer.nutzerSpeichernDB(nutzer);

		try {
			RequestDispatcher view = request.getRequestDispatcher("/nutzerregistrierung.jsp");
			request.setAttribute("liste", this.daoNutzer.aufgelisteteNutzer());
			view.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
