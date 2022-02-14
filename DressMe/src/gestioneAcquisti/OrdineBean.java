package gestioneAcquisti;

public class OrdineBean {
	
 @Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString()+"GestoreOrdini";
	}
 String numeroOrdine;
 String	email ;
 String	nome;
 String	cognome ;
 String	indirizzo ;
 String	cap ;
 String	comune;
 String	provincia;
 String	prezzo;
 String	prodotti ;
 String controllato;
 

public String getControllato() {
	return controllato;
}
public void setControllato(String controllato) {
	this.controllato = controllato;
}
public String getNumeroOrdine() {
	return numeroOrdine;
}
public void setNumeroOrdine(String numeroOrdine) {
	this.numeroOrdine = numeroOrdine;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public String getCognome() {
	return cognome;
}
public void setCognome(String cognome) {
	this.cognome = cognome;
}
public String getIndirizzo() {
	return indirizzo;
}
public void setIndirizzo(String indirizzo) {
	this.indirizzo = indirizzo;
}
public String getCap() {
	return cap;
}
public void setCap(String cap) {
	this.cap = cap;
}

/**
 * @return the comune
 */
public String getComune() {
	return comune;
}
/**
 * @param comune the comune to set
 */
public void setComune(String comune) {
	this.comune = comune;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((cap == null) ? 0 : cap.hashCode());
	result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
	result = prime * result + ((comune == null) ? 0 : comune.hashCode());
	result = prime * result + ((controllato == null) ? 0 : controllato.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((indirizzo == null) ? 0 : indirizzo.hashCode());
	result = prime * result + ((nome == null) ? 0 : nome.hashCode());
	result = prime * result + ((numeroOrdine == null) ? 0 : numeroOrdine.hashCode());
	result = prime * result + ((prezzo == null) ? 0 : prezzo.hashCode());
	result = prime * result + ((prodotti == null) ? 0 : prodotti.hashCode());
	result = prime * result + ((provincia == null) ? 0 : provincia.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj) {
		return true;
	}
	if (!(obj instanceof OrdineBean)) {
		return false;
	}
	OrdineBean other = (OrdineBean) obj;
	if (cap == null) {
		if (other.cap != null) {
			return false;
		}
	} else if (!cap.equals(other.cap)) {
		return false;
	}
	if (cognome == null) {
		if (other.cognome != null) {
			return false;
		}
	} else if (!cognome.equals(other.cognome)) {
		return false;
	}
	if (comune == null) {
		if (other.comune != null) {
			return false;
		}
	} else if (!comune.equals(other.comune)) {
		return false;
	}
	if (controllato == null) {
		if (other.controllato != null) {
			return false;
		}
	} else if (!controllato.equals(other.controllato)) {
		return false;
	}
	if (email == null) {
		if (other.email != null) {
			return false;
		}
	} else if (!email.equals(other.email)) {
		return false;
	}
	if (indirizzo == null) {
		if (other.indirizzo != null) {
			return false;
		}
	} else if (!indirizzo.equals(other.indirizzo)) {
		return false;
	}
	if (nome == null) {
		if (other.nome != null) {
			return false;
		}
	} else if (!nome.equals(other.nome)) {
		return false;
	}
	if (numeroOrdine == null) {
		if (other.numeroOrdine != null) {
			return false;
		}
	} else if (!numeroOrdine.equals(other.numeroOrdine)) {
		return false;
	}
	if (prezzo == null) {
		if (other.prezzo != null) {
			return false;
		}
	} else if (!prezzo.equals(other.prezzo)) {
		return false;
	}
	if (prodotti == null) {
		if (other.prodotti != null) {
			return false;
		}
	} else if (!prodotti.equals(other.prodotti)) {
		return false;
	}
	if (provincia == null) {
		if (other.provincia != null) {
			return false;
		}
	} else if (!provincia.equals(other.provincia)) {
		return false;
	}
	return true;
}
public String getProvincia() {
	return provincia;
}
public void setProvincia(String provincia) {
	this.provincia = provincia;
}
public String getPrezzo() {
	return prezzo;
}
public void setPrezzo(String prezzzo) {
	this.prezzo = prezzzo;
}
public String getProdotti() {
	return prodotti;
}
public void setProdotti(String prodotti) {
	this.prodotti = prodotti;
}

	
}
