package gestioneAcquisti;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class OrdineModelDS {
	
	private DataSource ds = null;
	
	public OrdineModelDS(DataSource ds){
		this.ds = ds;
		
	}
	
	public void SalvaOrdine(OrdineBean o) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO Ordine " + " (email,nome,cognome,indirizzo,cap,comune,provincia,prezzo,prodotti,controllato) VALUES (?,?,?,?,?,?,?,?,?,?)";
				
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
					
			preparedStatement.setString(1, o.getEmail());
			preparedStatement.setString(2, o.getNome());
			preparedStatement.setString(3, o.getCognome());
			preparedStatement.setString(4, o.getIndirizzo());
			preparedStatement.setString(5, o.getCap());
			preparedStatement.setString(6, o.getComune());
			preparedStatement.setString(7, o.getProvincia());
			preparedStatement.setString(8, o.getPrezzo());
			preparedStatement.setString(9, o.getProdotti());
			preparedStatement.setString(10, o.getControllato());
			
			Utility.print("doSave: " + preparedStatement.toString());

			preparedStatement.executeUpdate();
			
			connection.commit();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}

	}
	
}
