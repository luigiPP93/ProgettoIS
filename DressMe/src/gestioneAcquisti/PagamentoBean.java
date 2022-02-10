package gestioneAcquisti;

import java.util.Date;

public class PagamentoBean {
	
	private int codiceAcquisto;
	private String email;
	private String codiceVestito;
	private Date data;
	private int importo;
	
	public PagamentoBean() {
		this.email = "";
		this.codiceVestito = "";
		this.data = null;
		this.importo = 0;
	}

	public int getCodiceAcquisto() {
		return codiceAcquisto;
	}

	public void setCodiceAcquisto(int codiceAcquisto) {
		this.codiceAcquisto = codiceAcquisto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCodiceVestito() {
		return codiceVestito;
	}

	public void setCodiceVestito(String codiceVestito) {
		this.codiceVestito = codiceVestito;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getImporto() {
		return importo;
	}

	public void setImporto(int importo) {
		this.importo = importo;
	}
	
}

