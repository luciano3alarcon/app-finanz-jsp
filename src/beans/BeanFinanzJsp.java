package beans;

public class BeanFinanzJsp {

	private String login;
	private String password;
	private Long id;
	private String name;
	private String rufnummer;
	private String email;

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
