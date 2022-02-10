package gestioneCarrello;

public class CarrelloBean {
	
	private String idemail;
	private String password;
	private String codiceVestito;
	
	public String getIdemail() {
		return idemail;
	}
	public void setIdemail(String idemail) {
		this.idemail = idemail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCodiceVestito() {
		return codiceVestito;
	}
	public void setCodiceVestito(String codiceVestito) {
		this.codiceVestito = codiceVestito;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codiceVestito == null) ? 0 : codiceVestito.hashCode());
		result = prime * result + ((idemail == null) ? 0 : idemail.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarrelloBean other = (CarrelloBean) obj;
		if (codiceVestito == null) {
			if (other.codiceVestito != null)
				return false;
		} else if (!codiceVestito.equals(other.codiceVestito))
			return false;
		if (idemail == null) {
			if (other.idemail != null)
				return false;
		} else if (!idemail.equals(other.idemail))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
