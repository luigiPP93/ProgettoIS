package gestioneAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class LoginModelDS {
	private DataSource ds = null;

	public LoginModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	public PersonaleBean doRetrieveByKeyPersonale(String code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		PersonaleBean bean = new PersonaleBean();

		String selectSQL = "SELECT * FROM Personale WHERE email= ?";
		
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, code);

			Utility.print("doRetrieveByKey: " + preparedStatement.toString());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				bean.setNome(rs.getString("nome"));
				bean.setCognome(rs.getString("cognome"));
				bean.setEmail(rs.getString("email"));
				bean.setPassword(rs.getString("password"));
				System.out.println("PASSWORD DAO "+bean.getPassword());	
				bean.setRuolo(rs.getString("ruolo"));
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
	
	
	public UtenteBean doRetrieveByKey(String code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		UtenteBean bean = new UtenteBean();

		String selectSQL = "SELECT * FROM cliente WHERE email= ?";
		
		
		try {
			connection = ds.getConnection();
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
	
	/*public boolean validate(UtenteBean  item) throws ClassNotFoundException, SQLException {
        boolean status = false;
        Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		

		String selectSQL = "SELECT  *  FROM cliente WHERE email= ? and password = ? ";
		
		
		try{
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, item.getEmail());
			preparedStatement.setString(2, item.getPassword());

	            System.out.println(preparedStatement);
	            ResultSet rs = preparedStatement.executeQuery();
	            status = rs.next();

	        } catch (SQLException e) {
	            printSQLException(e);
	        }
		 return status;
}
	public UtenteBean validazione(String email, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		UtenteBean bean = new UtenteBean();

		String selectSQL = "SELECT * FROM Cliente WHERE email = ? and password = ?";
		
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

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
*/
	private void printSQLException(SQLException e){
		// TODO Auto-generated method stub
	}
}