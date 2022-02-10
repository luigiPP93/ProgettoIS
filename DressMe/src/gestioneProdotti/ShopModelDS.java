package gestioneProdotti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class ShopModelDS implements ShopModel<ShopBean>{
	private DataSource ds = null;

	public ShopModelDS(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public Collection<ShopBean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT * FROM Vestito";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		Collection<ShopBean> products = new LinkedList<ShopBean>();

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			Utility.print("doRetrieveAll: " + preparedStatement.toString());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ShopBean bean = new ShopBean();

				bean.setCodiceVestito(rs.getString("codiceVestito"));
				bean.setIdCategoria(rs.getString("idCategoria"));
				bean.setQuantitaVestito(rs.getInt("quantitaVestito"));
				bean.setTitolo(rs.getString("titolo"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setPrezzo(rs.getInt("prezzo"));
				bean.setCopertina(rs.getString("copertina"));
				
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

	@Override
	public ShopBean doRetrieveByKey(String code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ShopBean bean = new ShopBean();

		String selectSQL = "SELECT * FROM Vestito WHERE codiceVestito= ?";
		
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, code);

			Utility.print("doRetrieveByKey: " + preparedStatement.toString());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setCodiceVestito(rs.getString("codiceVestito"));
				bean.setIdCategoria(rs.getString("idCategoria"));
				bean.setQuantitaVestito(rs.getInt("quantitaVestito"));
				bean.setTitolo(rs.getString("titolo"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setPrezzo(rs.getInt("prezzo"));
				bean.setCopertina(rs.getString("copertina"));
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
	
	@Override
	public void doSave(ShopBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO Vestito " + " (codiceVestito,idcategoria,quantitaVestito,titolo,descrizione,prezzo,copertina) VALUES (?,?,?,?,?,?,?)";
		
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			
			preparedStatement.setString(1, item.getCodiceVestito());
			preparedStatement.setString(2, item.getIdCategoria());
			preparedStatement.setInt(3, item.getQuantitaVestito());
			preparedStatement.setString(4, item.getTitolo());
			preparedStatement.setString(5, item.getDescrizione());
			preparedStatement.setInt(6, item.getPrezzo());
			
			if(item.getIdCategoria().equals("Uomo")) {
				preparedStatement.setString(7, "image/Uomo/"+item.getCopertina());
			}else if(item.getIdCategoria().equals("Donna")) {
				preparedStatement.setString(7, "image/Donna/"+item.getCopertina());
			}else if(item.getIdCategoria().equals("Bambini")) {
				preparedStatement.setString(7, "image/Bambini/"+item.getCopertina());
			}

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

	@Override
	public boolean doUpdate(ShopBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE Vestito SET " + "idcategoria = ?, quantitaVestito = ? , titolo = ? ,descrizione = ?,prezzo = ?,copertina = ? WHERE codiceVestito = ? ";
		
	
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			
			preparedStatement.setString(1, item.getIdCategoria());
			preparedStatement.setInt(2, item.getQuantitaVestito());
			preparedStatement.setString(3, item.getTitolo());
			preparedStatement.setString(4, item.getDescrizione());
			preparedStatement.setInt(5, item.getPrezzo());
						
			if(item.getIdCategoria().equals("Uomo")) {
				preparedStatement.setString(6, "image/Uomo/"+item.getCopertina());
			}else if(item.getIdCategoria().equals("Donna")) {
				preparedStatement.setString(6, "image/Donna/"+item.getCopertina());
			}else if(item.getIdCategoria().equals("Bambini")) {
				preparedStatement.setString(6, "image/Bambini/"+item.getCopertina());
			}
			
			
			preparedStatement.setString(7, item.getCodiceVestito());

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

	@Override
	public boolean doDelete(ShopBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "DELETE FROM Vestito WHERE codiceVestito = ?";
		
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			
			preparedStatement.setString(1, item.getCodiceVestito());

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
	
	
	public Collection<ShopBean> doRetrieveAllBambini(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT * FROM Vestito where idcategoria = 'Bambini' ";

		if (order != null && !order.equals("")) {
			selectSQL += "ORDER BY " + order;
		}

		Collection<ShopBean> products = new LinkedList<ShopBean>();

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL); // questa è gia pronta perche non ha ? da processare

			Utility.print("doRetriveAll: " + preparedStatement.toString());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ShopBean bean = new ShopBean();

				bean.setCodiceVestito(rs.getString("codiceVestito"));
				bean.setIdCategoria(rs.getString("idCategoria"));
				bean.setQuantitaVestito(rs.getInt("quantitaVestito"));
				bean.setTitolo(rs.getString("titolo"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setPrezzo(rs.getInt("prezzo"));
				bean.setCopertina(rs.getString("copertina"));

				products.add(bean);

			}

		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
			if (connection != null)
				connection.close();
		}

		return products;
	}
	
	public Collection<ShopBean> doRetrieveAllDonna(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT * FROM Vestito where idcategoria = 'Donna' ";

		if (order != null && !order.equals("")) {
			selectSQL += "ORDER BY " + order;
		}

		Collection<ShopBean> products = new LinkedList<ShopBean>();

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL); // questa è gia pronta perche non ha ? da processare

			Utility.print("doRetriveAll: " + preparedStatement.toString());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ShopBean bean = new ShopBean();

				bean.setCodiceVestito(rs.getString("codiceVestito"));
				bean.setIdCategoria(rs.getString("idCategoria"));
				bean.setQuantitaVestito(rs.getInt("quantitaVestito"));
				bean.setTitolo(rs.getString("titolo"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setPrezzo(rs.getInt("prezzo"));
				bean.setCopertina(rs.getString("copertina"));

				products.add(bean);

			}

		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
			if (connection != null)
				connection.close();
		}

		return products;
	}
	
	public Collection<ShopBean> doRetrieveAllUomo(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT * FROM Vestito where idcategoria = 'Uomo' ";

		if (order != null && !order.equals("")) {
			selectSQL += "ORDER BY " + order;
		}

		Collection<ShopBean> products = new LinkedList<ShopBean>();

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL); // questa è gia pronta perche non ha ? da processare

			Utility.print("doRetriveAll: " + preparedStatement.toString());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ShopBean bean = new ShopBean();

				bean.setCodiceVestito(rs.getString("codiceVestito"));
				bean.setIdCategoria(rs.getString("idCategoria"));
				bean.setQuantitaVestito(rs.getInt("quantitaVestito"));
				bean.setTitolo(rs.getString("titolo"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setPrezzo(rs.getInt("prezzo"));
				bean.setCopertina(rs.getString("copertina"));

				products.add(bean);

			}

		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
			if (connection != null)
				connection.close();
		}

		return products;
	}

	
}
