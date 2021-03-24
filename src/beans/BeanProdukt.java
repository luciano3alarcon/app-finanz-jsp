package beans;

import java.math.BigDecimal;

public class BeanProdukt {

	private String beschreibung;
	private Long anzahl;
	private Long id;
	private Double preis;

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Long getAnzahl() {
		return this.anzahl;
	}

	public void setAnzahl(Long anzahl) {
		this.anzahl = anzahl;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPreis() {
		return this.preis;
	}

	public void setPreis(Double preis) {
		this.preis = preis;
	}

}
