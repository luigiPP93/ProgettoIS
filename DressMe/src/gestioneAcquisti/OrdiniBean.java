package gestioneAcquisti;

public class OrdiniBean {
	private String idemail;
	private String codiceVestito;

	@Override
	public String toString() {
		return "SessionCarrelloBean [idemail=" + idemail + ", codiceVestito=" + codiceVestito + ", getIdemail()="
				+ getIdemail() + ", getCodiceVestito()=" + getCodiceVestito() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	public String getIdemail() {
		return idemail;
	}
	public void setIdemail(String idemail) {
		this.idemail = idemail;
	}
	public String getCodiceVestito() {
		return codiceVestito;
	}
	public void setCodiceVestito(String codiceVestito) {
		this.codiceVestito = codiceVestito;
	}
}
