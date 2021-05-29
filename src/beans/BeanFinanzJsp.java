package beans;

public class BeanFinanzJsp {

	private String login;
	private String password;
	private Long id;
	private String name;
	private String rufnummer;
	private String email;
	
//	Date für Adresse
	private String plz;
	private String strasse;
	private String stadtTeil; 
	private String stadt; 
	private String bundesland;
		
	
	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getStadtTeil() {
		return stadtTeil;
	}

	public void setStadtTeil(String stadtTeil) {
		this.stadtTeil = stadtTeil;
	}

	public String getStadt() {
		return stadt;
	}

	public void setStadt(String stadt) {
		this.stadt = stadt;
	}

	public String getBundesland() {
		return bundesland;
	}

	public void setBundesland(String bundesland) {
		this.bundesland = bundesland;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRufnummer(String rufnummer) {
		this.rufnummer = rufnummer;
	}

	public String getRufnummer() {
		return this.rufnummer;
	}
	
	public void setEmail (String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}
	
}
