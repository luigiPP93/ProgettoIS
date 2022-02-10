package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.sql.DataSource;
import it.unisa.utils.Utility;


import gestioneProdotti.*;

public class CarrelloModelDS implements ShopModel<ShopBean>{
	private DataSource ds = null;

	public CarrelloModelDS(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public ShopBean doRetrieveByKey(String code) throws SQLException {
		return null;
	}

	@Override
	public Collection<ShopBean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT * FROM Vestito";

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

	@Override
	public void doSave(ShopBean item) throws SQLException {

	}

	@Override
	public boolean doUpdate(ShopBean item) throws SQLException {
		return false;
	}

	@Override
	public boolean doDelete(ShopBean item) throws SQLException {

		return false;
	}
	
	

}
