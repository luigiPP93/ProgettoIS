
package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.sql.DataSource;

import gestioneAccount.PersonaleBean;
import it.unisa.utils.Utility;

public class GestionePersonaleModelDS {
	private DataSource ds = null;

	public GestionePersonaleModelDS(DataSource ds) {
this.ds = ds;
}

	public Collection<PersonaleBean> stampaTuttoIlPersonale() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT * FROM Personale";
		Collection<PersonaleBean> products = new LinkedList<PersonaleBean>();
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			Utility.print("doRetrieveAll: " + preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				PersonaleBean bean = new PersonaleBean();
				bean.setNome(rs.getString("nome"));
				bean.setCognome(rs.getString("cognome"));
				bean.setEmail(rs.getString("email"));
				bean.setPassword(rs.getString("password"));
				bean.setRuolo(rs.getString("ruolo"));
				products.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return products;
	}

	public PersonaleBean doRetrieveByKey(String code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PersonaleBean bean = new PersonaleBean();
		String selectSQL = "SELECT * FROM Personale WHERE email = ?";
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
				bean.setRuolo(rs.getString("ruolo"));
			}
			Utility.print(bean.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return bean;
	}

	public void inserisciPersonale(PersonaleBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String insertSQL = "INSERT INTO Personale " + " (nome,cognome,email,password,ruolo) VALUES (?,?,?,?,?)";
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, item.getNome());
			preparedStatement.setString(2, item.getCognome());
			preparedStatement.setString(3, item.getEmail());
			preparedStatement.setString(4, item.getPassword());
			preparedStatement.setString(5, item.getRuolo());
			Utility.print("doSave: " + preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
	}

	public boolean doUpdate(PersonaleBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String updateSQL = "UPDATE Personale SET "
				+ "nome = ?, cognome = ? , email = ? ,password = ? ruolo = ? WHERE email = ? ";
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			if(connection.prepareStatement(updateSQL)==null) {
				return false;
			}
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setString(1, item.getNome());
			preparedStatement.setString(2, item.getCognome());
			preparedStatement.setString(3, item.getEmail());
			preparedStatement.setString(4, item.getPassword());
			preparedStatement.setString(5, item.getRuolo());
			preparedStatement.setString(6, item.getEmail());
			Utility.print("doUpdate: " + preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return false;
	}

	public boolean doDelete(PersonaleBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String updateSQL = "DELETE FROM Personale WHERE email = ?";
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setString(1, item.getEmail());
			Utility.print("doDelete: " + preparedStatement.toString());
			preparedStatement.executeUpdate();
			connection.commit();
		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return false;
	}
}
