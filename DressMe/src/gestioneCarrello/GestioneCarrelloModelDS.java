package gestioneCarrello;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;


import gestioneProdotti.*;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class GestioneCarrelloModelDS {
	private DataSource ds = null;

	public GestioneCarrelloModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	public void doSave(SessionCarrelloBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO Carrello  " + " (idemail,codiceVestito) VALUES (?,?)";
		
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			
			preparedStatement.setString(1, item.getIdemail());
			preparedStatement.setString(2, item.getCodiceVestito());
			

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

	
	public void doSave2(SessionCarrelloBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO ordini  " + " (idemail,codiceVestito) VALUES (?,?)";
		
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			
			preparedStatement.setString(1, item.getIdemail());
			preparedStatement.setString(2, item.getCodiceVestito());
			

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
	
	public boolean doDelete(SessionCarrelloBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "DELETE FROM carrello WHERE idemail=? and codiceVestito = ? ";
		
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			
			preparedStatement.setString(1, item.getIdemail());
			preparedStatement.setString(2, item.getCodiceVestito());

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


	public boolean doDelete2(SessionCarrelloBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "DELETE FROM ordini WHERE idemail=? and codiceVestito = ? ";
		
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			
			preparedStatement.setString(1, item.getIdemail());
			preparedStatement.setString(2, item.getCodiceVestito());

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
	
	public Carts<ShopBean> TrovaCarrello(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL =  "SELECT vestito.codiceVestito , titolo, descrizione, prezzo , copertina "
                + "FROM carrello , "
                + "vestito , cliente "
                + "where carrello.idemail = ? && carrello.idemail = cliente.email "
                + "&& carrello.codiceVestito=vestito.codiceVestito;";

        Carts<ShopBean> products = new Carts<ShopBean>();

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1,email);
            Utility.print("doRetrieveAll: " + preparedStatement.toString());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ShopBean bean = new ShopBean();

                bean.setCodiceVestito(rs.getString("codiceVestito"));
                String s =rs.getString("codiceVestito");
                System.out.println(s);
                bean.setTitolo(rs.getString("titolo"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setPrezzo(rs.getInt("prezzo"));
                bean.setCopertina(rs.getString("copertina"));
                products.addItem(bean);
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
	
	public Collection<ShopBean> TrovaCarrello2(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

 

        String selectSQL =  "SELECT vestito.codiceVestito , titolo, descrizione, prezzo , copertina "
                + "FROM ordini , "
                + "vestito , cliente "
                + "where ordini.idemail = ? && ordini.idemail = cliente.email "
                + "&& ordini.codiceVestito=vestito.codiceVestito;";

 

        Collection<ShopBean> products = new LinkedList<ShopBean>();

 

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1,email);
            Utility.print("doRetrieveAll: " + preparedStatement.toString());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ShopBean bean = new ShopBean();

                bean.setCodiceVestito(rs.getString("codiceVestito"));
                String s =rs.getString("codiceVestito");
                System.out.println(s);
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
	
	public boolean deleteAll(String item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "DELETE FROM carrello WHERE idemail= ?";
		
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			
			preparedStatement.setString(1, item);

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
