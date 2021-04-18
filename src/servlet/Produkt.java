package servlet;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanProdukt;
import dao.DaoProdukt;

@WebServlet("/produktBearbeitung")
public class Produkt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DaoProdukt daoProdukt = new DaoProdukt();

	public Produkt() {
		super();
	}

	/* Siehe Form in produktregister.jsp method get */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			String aktion = req.getParameter("aktion");
			String produkt = req.getParameter("produkte");

			if (aktion.equalsIgnoreCase("loeschen")) {
				this.daoProdukt.loeschen(produkt);

				RequestDispatcher view = req.getRequestDispatcher("/produktregister.jsp");
				req.setAttribute("produktliste", this.daoProdukt.aufgelisteteProdukte());
				view.forward(req, resp);

			} else if (aktion.equalsIgnoreCase("editieren")) {

				BeanProdukt beanEdit = this.daoProdukt.abfragen(produkt);
				RequestDispatcher view = req.getRequestDispatcher("/produktregister.jsp");
				req.setAttribute("produktListe", beanEdit);
				view.forward(req, resp);

				this.daoProdukt.update(beanEdit);
			}

			else if (aktion.equalsIgnoreCase("aufgelisteteprodukte")) {
				RequestDispatcher view = req.getRequestDispatcher("/produktregister.jsp");
				req.setAttribute("produktliste", this.daoProdukt.aufgelisteteProdukte());
				view.forward(req, resp);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Siehe Form in produktregister.jsp method= post */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String aktionAbbrechen = req.getParameter("aktion");

		if (aktionAbbrechen != null && aktionAbbrechen.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher view = req.getRequestDispatcher("/produktregister.jsp");
				req.setAttribute("produktliste", this.daoProdukt.aufgelisteteProdukte());
				view.forward(req, resp);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			String beschreibung = req.getParameter("beschreibung");
			String anzahl = req.getParameter("anzahl");
			String id = req.getParameter("id");
			String preis = req.getParameter("preis");

			BeanProdukt produkt = new BeanProdukt();
			produkt.setBeschreibung(beschreibung);
			produkt.setAnzahl(!anzahl.isEmpty() ? Long.parseLong(anzahl) : null);
			produkt.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			produkt.setPreis(!preis.isEmpty() ? Double.parseDouble(preis) : 0);

			try {

				String fehlerMeldungProdukt = null;
				boolean weiterGehen = true;

				if (id == null || id.isEmpty()) {

					if (beschreibung.isEmpty()) {
						req.setAttribute("fehlerMeldungProdukt", "Produktname darf nicht leer bleiben.");
						weiterGehen = false;

					} else if (preis == null || preis.isEmpty()) { // preis.isEmpty() ||
						req.setAttribute("fehlerMeldungProdukt", "Der Preis darf nicht leer sein.");
						weiterGehen = false;

					} else if (anzahl.isEmpty()) {
						req.setAttribute("fehlerMeldungProdukt", "Produktmenge darf nicht leer bleiben.");
						weiterGehen = false;

					} else {
						this.daoProdukt.produtktSpeichern(produkt);
					}
				}

				else if (id != null || !id.isEmpty()) {

					if (beschreibung.isEmpty()) {
						req.setAttribute("fehlerMeldungProdukt", "Die Beschreibung darf nicht leer bleiben.");
						weiterGehen = false;

					} else if (Long.parseLong(anzahl) == 0 || anzahl.isEmpty()) {
						req.setAttribute("fehlerMeldungProdukt", "Produkt muss neu bestellt werden.");
						weiterGehen = false;

					} else if (preis.isEmpty()) { //Double.parseDouble(preis) == 0 || 
						req.setAttribute("fehlerMeldungProdukt", "Der Preis darf nicht leer sein.");
						weiterGehen = false;

					} else {
						this.daoProdukt.update(produkt);
					}
				}

				if (!weiterGehen) {
					req.setAttribute("produktliste", produkt);
				}

				RequestDispatcher viewProduktregister = req.getRequestDispatcher("/produktregister.jsp");
				req.setAttribute("produktliste", this.daoProdukt.aufgelisteteProdukte());
				viewProduktregister.forward(req, resp);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
