package gestioneAcquisti;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;


import it.unisa.utils.Utility;

public class GestioneOrdiniModelDS {
	
	private DataSource ds = null;
	
	public GestioneOrdiniModelDS(DataSource ds){
		this.ds = ds;
		
	}
	public Collection<GestioneOrdineBean> ritornaTuttiOrdiniDaControllare() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT * FROM GestoriOrdini WHERE controllato= ? ";

		

		Collection<GestioneOrdineBean> products = new LinkedList<GestioneOrdineBean>();

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, "false");

			Utility.print("doRetrieveAll: " + preparedStatement.toString());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				GestioneOrdineBean bean = new GestioneOrdineBean();
				
				bean.setNumeroOrdine(rs.getString("numeroOrdine"));
				bean.setEmail(rs.getString("email"));
				bean.setNome(rs.getString("nome"));
				bean.setCognome(rs.getString("cognome"));
				bean.setIndirizzo(rs.getString("indirizzo"));
				bean.setCap(rs.getString("cap"));
				bean.setComune(rs.getString("comune"));
				bean.setProvincia(rs.getString("provincia"));
				bean.setPrezzo(rs.getString("prezzo"));
				bean.setProdotti(rs.getString("prodotti"));
				bean.setControllato(rs.getString("controllato"));
				
				products.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}

		return products; 
	}
	
	public GestioneOrdineBean doRetrieveByKey(String code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		GestioneOrdineBean bean = new GestioneOrdineBean();

		String selectSQL = "SELECT * FROM GestoriOrdini WHERE numeroOrdine= ?";
		
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, code);

			Utility.print("doRetrieveByKey: " + preparedStatement.toString());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				
				bean.setNumeroOrdine(rs.getString("numeroOrdine"));
				bean.setEmail(rs.getString("email"));
				bean.setNome(rs.getString("nome"));
				bean.setCognome(rs.getString("cognome"));
				bean.setIndirizzo(rs.getString("indirizzo"));
				bean.setCap(rs.getString("cap"));
				bean.setComune(rs.getString("comune"));
				bean.setProvincia(rs.getString("provincia"));
				bean.setPrezzo(rs.getString("prezzo"));
				bean.setProdotti(rs.getString("prodotti"));
				bean.setControllato(rs.getString("controllato"));
				
			}
			
			Utility.print(bean.toString());

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}

		return bean;
	}
	
	public boolean doUpdate(GestioneOrdineBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE GestoriOrdini SET " + "email = ?, nome = ? , cognome = ? ,indirizzo = ?,cap = ?,comune = ?,provincia = ?,prezzo = ?,prodotti = ?,controllato =? WHERE numeroOrdine = ? ";
		
	
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			
			preparedStatement.setString(1, item.getEmail());
			preparedStatement.setString(2, item.getNome());
			preparedStatement.setString(3, item.getCognome());
			preparedStatement.setString(4, item.getIndirizzo());
			preparedStatement.setString(5, item.getCap());
			preparedStatement.setString(6, item.getComune());
			preparedStatement.setString(7, item.getProvincia());
			preparedStatement.setString(8, item.getPrezzo());
			preparedStatement.setString(9, item.getProdotti());
			preparedStatement.setString(10, item.getControllato());
			
			preparedStatement.setString(11, item.getNumeroOrdine());

			Utility.print("doUpdate: " + preparedStatement.toString());

			
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
		return true;
	}
	
	public boolean confermaOrdine(GestioneOrdineBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE GestoriOrdini SET " + "  controllato =? WHERE numeroOrdine = ? ";
		
	
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			
			
			preparedStatement.setString(1, "true");
			
			preparedStatement.setString(2, item.getNumeroOrdine());

			Utility.print("doUpdate: " + preparedStatement.toString());

			
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
		return true;
	}
	public boolean doDelete(GestioneOrdineBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "DELETE FROM GestoriOrdini WHERE numeroOrdine= ?";
		
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			
			preparedStatement.setString(1, item.getNumeroOrdine());

			Utility.print("doDelete: " + preparedStatement.toString());

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
		return false;
	}
}
