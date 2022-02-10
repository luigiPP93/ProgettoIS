package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import gestioneAccount.UtenteBean;
import gestioneProdotti.*;

import it.unisa.utils.Utility;

public class RegistrazioneModelDS implements ShopModel<UtenteBean>{
	private DataSource ds = null;

	public RegistrazioneModelDS(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public Collection<UtenteBean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT * FROM Cliente";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		Collection<UtenteBean> products = new LinkedList<UtenteBean>();

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			Utility.print("doRetrieveAll: " + preparedStatement.toString());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				UtenteBean bean = new UtenteBean();
					
				bean.setNome(rs.getString("nome"));
				bean.setCognome(rs.getString("cognome"));
				bean.setEmail(rs.getString("email"));
				bean.setIndirizzo(rs.getString("indirizzo"));
				bean.setPassword(rs.getString("password"));
				
				
				products.add(bean);
			}

		} finally {
			
				if (preparedStatement != null)
					preparedStatement.close();
			
		}

		return products;
	}
	
	@Override
	public UtenteBean doRetrieveByKey(String code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		UtenteBean bean = new UtenteBean();

		String selectSQL = "SELECT * FROM Cliente WHERE email = ?";
				
		try {
			connection = ds.getConnection();
			System.out.println("connection"+ connection.toString());
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, code);

			Utility.print("doRetrieveByKey: " + preparedStatement.toString());

			ResultSet rs = preparedStatement.executeQuery();
			
		
			
			while (rs.next()) {
				bean.setNome(rs.getString("nome"));
				bean.setCognome(rs.getString("cognome"));
				bean.setEmail(rs.getString("email"));
				bean.setIndirizzo(rs.getString("indirizzo"));
				bean.setPassword(rs.getString("password"));
			}
			
			Utility.print(bean.toString());

			
			return bean;
			} catch(SQLException e) {
			e.printStackTrace();
			}finally {
			if (preparedStatement != null)
			preparedStatement.close();
			}
			return null;

	}
	
	
	public boolean doRetrieveByKeyU(String code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		UtenteBean bean = new UtenteBean();
		
		String selectSQL = "SELECT * FROM Cliente WHERE email = ?";
				
		try {
			connection = ds.getConnection();
			System.out.println("connection"+ connection.toString());
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, code);

			Utility.print("doRetrieveByKey: " + preparedStatement.toString());

			ResultSet rs = preparedStatement.executeQuery();
			System.out.println("Sono RS"+rs);
		
			while (rs.next()) {
				bean.setNome(rs.getString("nome"));
				System.out.println("Sono Nome del ciclo"+rs.getString("nome"));
				bean.setCognome(rs.getString("cognome"));
				bean.setEmail(rs.getString("email"));
				bean.setIndirizzo(rs.getString("indirizzo"));
				bean.setPassword(rs.getString("password"));
				return true;
			}
			

			
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public UtenteBean doRetrieveByKey(String email, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		UtenteBean bean = new UtenteBean();

		String selectSQL = "SELECT * FROM Cliente WHERE email = ? and password = ?";
		
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);
			preparedStatement.setString(1, password);

			Utility.print("doRetrieveByKey: " + preparedStatement.toString());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setNome(rs.getString("nome"));
				bean.setCognome(rs.getString("cognome"));
				bean.setEmail(rs.getString("email"));
				bean.setIndirizzo(rs.getString("indirizzo"));
				bean.setPassword(rs.getString("password"));
			}
			
			Utility.print(bean.toString());

		} finally {
			
				if (preparedStatement != null)
					preparedStatement.close();
		
		}

		return bean;
	}
	
	public void doSave(UtenteBean  item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO Cliente " + " (nome,cognome,email,indirizzo,password) VALUES (?,?,?,?,?)";
		
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			
			preparedStatement.setString(1, item.getNome());
			preparedStatement.setString(2, item.getCognome());
			preparedStatement.setString(3, item.getEmail());
			preparedStatement.setString(4, item.getIndirizzo());
			preparedStatement.setString(5, item.getPassword());

			Utility.print("doSave: " + preparedStatement.toString());

			preparedStatement.executeUpdate();
			
			connection.commit();

    		System.out.println("ds"+ ds.toString());

		} finally {
			
				if (preparedStatement != null)
					preparedStatement.close();
		
		}

	}
	
	public boolean doSaveUtete(UtenteBean  item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO Cliente " + " (nome,cognome,email,indirizzo,password) VALUES (?,?,?,?,?)";
		
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			if(preparedStatement==null) {
				return false;
			}
			preparedStatement.setString(1, item.getNome());
			preparedStatement.setString(2, item.getCognome());
			preparedStatement.setString(3, item.getEmail());
			preparedStatement.setString(4, item.getIndirizzo());
			preparedStatement.setString(5, item.getPassword());

			Utility.print("doSave: " + preparedStatement.toString());

			preparedStatement.executeUpdate();
			
			connection.commit();

    		System.out.println("ds"+ ds.toString());

		} finally {
			
				if (preparedStatement != null)
					preparedStatement.close();
		
		}
		return true;

	}
	@Override
	public boolean doUpdate(UtenteBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE Cliente SET " + "nome = ?, cognome = ? , email = ? , indirizzo = ?, password = ? WHERE email = ? ";
		
	
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			
			preparedStatement.setString(1, item.getNome());
			preparedStatement.setString(2, item.getCognome());
			preparedStatement.setString(3, item.getEmail());
			preparedStatement.setString(4, item.getIndirizzo());
			preparedStatement.setString(5, item.getPassword());
			
						
			
			
			preparedStatement.setString(6, item.getEmail());

			Utility.print("doUpdate: " + preparedStatement.toString());

			
			preparedStatement.executeUpdate();
			
			connection.commit();
			return true;

		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			
				if (preparedStatement != null)
					preparedStatement.close();
			
		} 
		return false;
	}

	@Override
	public boolean doDelete(UtenteBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "DELETE FROM Cliente WHERE email = ?";
		
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			
			preparedStatement.setString(1, item.getEmail());

			Utility.print("doDelete: " + preparedStatement.toString());

			preparedStatement.executeUpdate();
			
			connection.commit();

			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean doUpdatePassword(UtenteBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE Cliente SET " + " password = ? WHERE email = ? ";
	
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			
			preparedStatement.setString(1, item.getPassword());			
			preparedStatement.setString(2, item.getEmail());

			Utility.print("doUpdate: " + preparedStatement.toString());

			
			preparedStatement.executeUpdate();
			
			connection.commit();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			
				if (preparedStatement != null)
					preparedStatement.close();
			
		}
		
		return false;
	}

}
