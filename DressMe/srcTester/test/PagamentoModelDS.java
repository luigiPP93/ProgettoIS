package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import gestioneAcquisti.GestioneOrdineBean;
import gestioneAcquisti.PagamentoBean;
import it.unisa.utils.Utility;

public class PagamentoModelDS {
	
	private DataSource ds = null;
	
	public PagamentoModelDS(DataSource ds){
		this.ds = ds;
		
	}
	
	public void SalvaOrdine(GestioneOrdineBean o) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO GestoriOrdini " + " (email,nome,cognome,indirizzo,cap,comune,provincia,prezzo,prodotti,controllato) VALUES (?,?,?,?,?,?,?,?,?,?)";
		
		
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
		
				if (preparedStatement != null)
					preparedStatement.close();
			} 

	}
	
	
	public void doSavePagamento(PagamentoBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO Acquistato " + " (email,codiceVestito,data,importo) VALUES (?,?,?,?)";
		
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			
			preparedStatement.setString(1, item.getEmail());
			preparedStatement.setString(2, item.getCodiceVestito());
			preparedStatement.setDate(3, (Date) item.getData());
			preparedStatement.setInt(4, item.getImporto());


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
