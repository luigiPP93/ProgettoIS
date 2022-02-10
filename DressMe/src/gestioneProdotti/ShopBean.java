package gestioneProdotti;

import java.io.Serializable;

public class ShopBean implements Serializable {

	private static final long serialVersionUID = 1L;

	String codiceVestito;
	String idCategoria;
	int quantitaVestito;
	String titolo;
	String descrizione;
	int prezzo;
	String copertina;
	
	public ShopBean() {
		codiceVestito ="";
		idCategoria="";
		quantitaVestito=0;
		titolo = "";
		descrizione = "";
		prezzo = 0;
		copertina = "";
	}
	
	public String getCodiceVestito() {
		return codiceVestito;
	}
	public void setCodiceVestito(String codiceVestito) {
		this.codiceVestito = codiceVestito;
	}
	
	public int getQuantitaVestito() {
		return quantitaVestito;
	}
	public void setQuantitaVestito(int quantitaVestito) {
		this.quantitaVestito = quantitaVestito;
	}
	
	public String getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(String idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public int getPrezzo() {
		return prezzo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codiceVestito == null) ? 0 : codiceVestito.hashCode());
		result = prime * result + ((copertina == null) ? 0 : copertina.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((idCategoria == null) ? 0 : idCategoria.hashCode());
		result = prime * result + prezzo;
		result = prime * result + quantitaVestito;
		result = prime * result + ((titolo == null) ? 0 : titolo.hashCode());
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
		ShopBean other = (ShopBean) obj;
		if (codiceVestito == null) {
			if (other.codiceVestito != null)
				return false;
		} else if (!codiceVestito.equals(other.codiceVestito))
			return false;
		if (copertina == null) {
			if (other.copertina != null)
				return false;
		} else if (!copertina.equals(other.copertina))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (idCategoria == null) {
			if (other.idCategoria != null)
				return false;
		} else if (!idCategoria.equals(other.idCategoria))
			return false;
		if (prezzo != other.prezzo)
			return false;
		if (quantitaVestito != other.quantitaVestito)
			return false;
		if (titolo == null) {
			if (other.titolo != null)
				return false;
		} else if (!titolo.equals(other.titolo))
			return false;
		return true;
	}

	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}
	
	//da provare
	public boolean isEmpty() {
		return codiceVestito == "";
	}
	
	public String getCopertina() {
		return copertina;
	}
	public void setCopertina(String copertina) {
		this.copertina = copertina;
	}
}
