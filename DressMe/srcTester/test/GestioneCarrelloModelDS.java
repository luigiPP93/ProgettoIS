package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;


import gestioneProdotti.*;

import javax.sql.DataSource;

import gestioneCarrello.Carts;
import gestioneCarrello.SessionCarrelloBean;
import it.unisa.utils.Utility;

public class GestioneCarrelloModelDS {
	private DataSource ds = null;

	public GestioneCarrelloModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	public boolean doSave(SessionCarrelloBean item) throws SQLException {
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
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
return false;
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
			
				if (preparedStatement != null)
					preparedStatement.close();
			}

	}
	
	public SessionCarrelloBean trova(String code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		SessionCarrelloBean bean = new SessionCarrelloBean();

		String selectSQL = "SELECT * FROM Carrello WHERE idEmail= ?";
		
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, code);

			Utility.print("doRetrieveByKey: " + preparedStatement.toString());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setIdemail(rs.getString("idemail"));
				bean.setCodiceVestito(rs.getString("codiceVestito"));
			}
			
			Utility.print(bean.toString());

		} finally {
			
				if (preparedStatement != null)
					preparedStatement.close();
			
		}

		return bean;
	}
	
	public boolean doDelete(SessionCarrelloBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "DELETE FROM carrello WHERE idemail=? AND codiceVestito = ? ";
		
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			
			preparedStatement.setString(1, item.getIdemail());
			preparedStatement.setString(2, item.getCodiceVestito());

			Utility.print("doDelete: " + preparedStatement.toString());

			preparedStatement.executeUpdate();
			connection.commit();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
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
			
				if (preparedStatement != null)
					preparedStatement.close();
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
           
                if (preparedStatement != null)
                    preparedStatement.close();
            }

 

        return products; 
    }
	public Carts<ShopBean> TrovaCarrello2(String email) throws SQLException {
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
           
                if (preparedStatement != null)
                    preparedStatement.close();
            }

 

        return products; 
    }
	public Collection<ShopBean> TrovaCarrelloUtente(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

 

        String selectSQL =  "SELECT vestito.codiceVestito , vestito.idcategoria, vestito.quantitaVestito,vestito.titolo, vestito.descrizione, vestito.prezzo , vestito.copertina FROM carrello, vestito where carrello.idemail = ? AND carrello.codiceVestito=vestito.codiceVestito";

 

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
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}
	
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

				if (preparedStatement != null)
					preparedStatement.close();
			} 
		return false;
	}
	
}
