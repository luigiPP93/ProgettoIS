package gestioneCarreloTest;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import gestioneAccount.UtenteBean;
import gestioneCarrello.SessionCarrelloBean;
import gestioneProdotti.ShopBean;
import test.*;

public class TestCarrelloDAO {

    private Connection db;
    private GestioneCarrelloModelDS carrelloDAO;
    private ShopModelDS prodottoDAO;
    private RegistrazioneModelDS userDAO;
    private UtenteBean utenteEsistente;
    private SessionCarrelloBean carrello;
    
    private ShopBean prodotto1;
    private ShopBean prodotto2;
    private ShopBean prodotto3;
   
    private static IDatabaseTester tester;
	private DataSource ds;
    
    public TestCarrelloDAO () {}

    @Before
    public void setUp() throws SQLException, Exception {
      
        	tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
                    "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'resources/db/init/schema.sql'",
                    "PROVA",
                    ""
            );
            // Refresh permette di svuotare la cache dopo un modifica con setDataSet
            // DeleteAll ci svuota il DB manteneno lo schema
            tester.setSetUpOperation(DatabaseOperation.REFRESH);
            tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
       
            ds = Mockito.mock(DataSource.class);
            Mockito.when(ds.getConnection())
            .thenReturn(tester.getConnection().getConnection());
            userDAO = new RegistrazioneModelDS(ds);
            prodottoDAO= new ShopModelDS(ds);
            carrelloDAO = new GestioneCarrelloModelDS(ds);
            
            
            String ctegoria="Uomo";
            String numero="";
            prodottoDAO.doSaveCategoria(ctegoria, numero);
            utenteEsistente=new UtenteBean();
            utenteEsistente.setNome("luigi");
            utenteEsistente.setCognome("PP");
            utenteEsistente.setEmail("luigiPP@gmail.com");
            utenteEsistente.setIndirizzo("Via San Francesco 29");   
            utenteEsistente.setPassword("ABCD1234");
        	userDAO.doSave(utenteEsistente);
        	
        	prodotto1 = new ShopBean();
        	prodotto1.setCodiceVestito("MG001");
        	prodotto1.setIdCategoria("Uomo");
        	prodotto1.setQuantitaVestito(10);
        	prodotto1.setTitolo("T-Shirt Colors");
        	prodotto1.setDescrizione("Maglia con triangolo multicolore");
        	prodotto1.setPrezzo(9);
        	prodotto1.setCopertina("uomo_maglia1.png");
        	
        	prodotto2 = new ShopBean();
        	prodotto2.setCodiceVestito("MG002");
        	prodotto2.setIdCategoria("Uomo");
        	prodotto2.setQuantitaVestito(20);
        	prodotto2.setTitolo("T-Shirt Kalvin Klein");
        	prodotto2.setDescrizione("Maglia in cotone");
        	prodotto2.setPrezzo(15);
        	prodotto2.setCopertina("uomo_maglia2.png");

        	prodotto3 = new ShopBean();
        	prodotto3.setCodiceVestito("MG003");
        	prodotto3.setIdCategoria("Uomo");
        	prodotto3.setQuantitaVestito(30);
        	prodotto3.setTitolo("Maglia rosa");
        	prodotto3.setDescrizione("Maglia colore nero-verde");
        	prodotto3.setPrezzo(34);
        	prodotto3.setCopertina("uomo_maglia3.png");

        	prodottoDAO.doSave(prodotto1);
        	prodottoDAO.doSave(prodotto2);
        	prodottoDAO.doSave(prodotto3);
        	
    }
    

    @Test
    public void TestAggiungiAlCarrello() throws SQLException {
    	String email="luigiPP@gmail.com";
    	String codiceProdotto="MG003";
    	carrello = new SessionCarrelloBean();
    	carrello.setCodiceVestito(codiceProdotto);
    	carrello.setIdemail(email);
        assertEquals(true,carrelloDAO.doSave(carrello));
    }
    
    @Test
    public void TestAggiungiAlCarrelloUtenteNonEsistente() throws SQLException {
    	String email="PP@gmail.com";
    	String codiceProdotto="MG003";
    	carrello = new SessionCarrelloBean();
    	carrello.setCodiceVestito(codiceProdotto);
    	carrello.setIdemail(email);
        assertEquals(false,carrelloDAO.doSave(carrello));
    }



    @Test
    public void TestEliminaTuttoIlCarrello() throws SQLException {
    	String email="luigiPP@gmail.com";
    	String codiceProdotto="MG003";
    	carrello = new SessionCarrelloBean();
    	carrello.setCodiceVestito(codiceProdotto);
    	carrello.setIdemail(email);
    	carrelloDAO.doSave(carrello);
    	
    	carrello = new SessionCarrelloBean();
    	carrello.setCodiceVestito("MG002");
    	carrello.setIdemail("luigiPP@gmail.com");
    	carrelloDAO.doSave(carrello);
    	
        assertEquals(true,carrelloDAO.deleteAll("luigiPP@gmail.com"));
    }

    

    @Test
    public void TestRimuoviDalCarrello() throws SQLException {
    	String email="luigiPP@gmail.com";
    	String codiceProdotto="MG003";
    	carrello = new SessionCarrelloBean();
    	carrello.setCodiceVestito(codiceProdotto);
    	carrello.setIdemail(email);
        assertEquals(true,carrelloDAO.doDelete(carrello));
    }
    
    
    @Test
    public void TestRitornaCarrello() throws SQLException {
    	ShopBean shop;
		Collection<ShopBean> expected = new LinkedList<ShopBean>();

		shop = new ShopBean();

		shop.setCodiceVestito("MG001");
		shop.setIdCategoria("Uomo");
		shop.setQuantitaVestito(10);
		shop.setTitolo("T-Shirt Colors");
		shop.setDescrizione("Maglia con triangolo multicolore");
		shop.setPrezzo(9);
		shop.setCopertina("image/Uomo/uomo_maglia1.png");

		expected.add(shop);

		shop = new ShopBean();
		
		shop.setCodiceVestito("MG002");
		shop.setIdCategoria("Uomo");
		shop.setQuantitaVestito(20);
		shop.setTitolo("T-Shirt Kalvin Klein");
		shop.setDescrizione("Maglia in cotone");
		shop.setPrezzo(15);
		shop.setCopertina("image/Uomo/uomo_maglia2.png");

		expected.add(shop);
		
		shop = new ShopBean();
		
		shop.setCodiceVestito("MG003");
		shop.setIdCategoria("Uomo");
		shop.setQuantitaVestito(30);
		shop.setTitolo("Maglia rosa");
		shop.setDescrizione("Maglia colore nero-verde");
		shop.setPrezzo(34);
		shop.setCopertina("image/Uomo/uomo_maglia3.png");

		expected.add(shop);
		carrello = new SessionCarrelloBean();
    	carrello.setCodiceVestito("MG001");
    	carrello.setIdemail("luigiPP@gmail.com");
    	carrelloDAO.doSave(carrello);
    	
    	carrello = new SessionCarrelloBean();
    	carrello.setCodiceVestito("MG002");
    	carrello.setIdemail("luigiPP@gmail.com");
    	carrelloDAO.doSave(carrello);
    	
    	carrello = new SessionCarrelloBean();
    	carrello.setCodiceVestito("MG003");
    	carrello.setIdemail("luigiPP@gmail.com");
    	carrelloDAO.doSave(carrello);
    	
    	Collection<ShopBean> actual = new LinkedList<ShopBean>();
		try {

			actual = carrelloDAO.TrovaCarrelloUtente("luigiPP@gmail.com");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		assertEquals(expected, actual);
		userDAO.doDelete(utenteEsistente);
	}
   
    
    @Test
    public void TestRitornaCarrelloUtenteNonEsistente() throws SQLException {
    	ShopBean shop;
		Collection<ShopBean> expected = new LinkedList<ShopBean>();

		shop = new ShopBean();

		shop.setCodiceVestito("MG001");
		shop.setIdCategoria("Uomo");
		shop.setQuantitaVestito(10);
		shop.setTitolo("T-Shirt Colors");
		shop.setDescrizione("Maglia con triangolo multicolore");
		shop.setPrezzo(9);
		shop.setCopertina("image/Uomo/uomo_maglia1.png");

		expected.add(shop);

		shop = new ShopBean();
		
		shop.setCodiceVestito("MG002");
		shop.setIdCategoria("Uomo");
		shop.setQuantitaVestito(20);
		shop.setTitolo("T-Shirt Kalvin Klein");
		shop.setDescrizione("Maglia in cotone");
		shop.setPrezzo(15);
		shop.setCopertina("image/Uomo/uomo_maglia2.png");

		expected.add(shop);
		
		shop = new ShopBean();
		
		shop.setCodiceVestito("MG003");
		shop.setIdCategoria("Uomo");
		shop.setQuantitaVestito(30);
		shop.setTitolo("Maglia rosa");
		shop.setDescrizione("Maglia colore nero-verde");
		shop.setPrezzo(34);
		shop.setCopertina("image/Uomo/uomo_maglia3.png");

		expected.add(shop);
		carrello = new SessionCarrelloBean();
    	carrello.setCodiceVestito("MG001");
    	carrello.setIdemail("luigiPP@gmail.com");
    	carrelloDAO.doSave(carrello);
    	
    	carrello = new SessionCarrelloBean();
    	carrello.setCodiceVestito("MG002");
    	carrello.setIdemail("luigiPP@gmail.com");
    	carrelloDAO.doSave(carrello);
    	
    	carrello = new SessionCarrelloBean();
    	carrello.setCodiceVestito("MG003");
    	carrello.setIdemail("luigiPP@gmail.com");
    	carrelloDAO.doSave(carrello);
    	
    	Collection<ShopBean> actual = new LinkedList<ShopBean>();
		try {

			actual = carrelloDAO.TrovaCarrelloUtente("g@gmail.com");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		assertNotEquals(expected, actual);
		userDAO.doDelete(utenteEsistente);
	}
    
    @Test
    public void TestRitornaCarrelloUtenteNull() throws SQLException {
    	ShopBean shop;
		Collection<ShopBean> expected = new LinkedList<ShopBean>();

		shop = new ShopBean();

		shop.setCodiceVestito("MG001");
		shop.setIdCategoria("Uomo");
		shop.setQuantitaVestito(10);
		shop.setTitolo("T-Shirt Colors");
		shop.setDescrizione("Maglia con triangolo multicolore");
		shop.setPrezzo(9);
		shop.setCopertina("image/Uomo/uomo_maglia1.png");

		expected.add(shop);

		shop = new ShopBean();
		
		shop.setCodiceVestito("MG002");
		shop.setIdCategoria("Uomo");
		shop.setQuantitaVestito(20);
		shop.setTitolo("T-Shirt Kalvin Klein");
		shop.setDescrizione("Maglia in cotone");
		shop.setPrezzo(15);
		shop.setCopertina("image/Uomo/uomo_maglia2.png");

		expected.add(shop);
		
		shop = new ShopBean();
		
		shop.setCodiceVestito("MG003");
		shop.setIdCategoria("Uomo");
		shop.setQuantitaVestito(30);
		shop.setTitolo("Maglia rosa");
		shop.setDescrizione("Maglia colore nero-verde");
		shop.setPrezzo(34);
		shop.setCopertina("image/Uomo/uomo_maglia3.png");

		expected.add(shop);
		carrello = new SessionCarrelloBean();
    	carrello.setCodiceVestito("MG001");
    	carrello.setIdemail("luigiPP@gmail.com");
    	carrelloDAO.doSave(carrello);
    	
    	carrello = new SessionCarrelloBean();
    	carrello.setCodiceVestito("MG002");
    	carrello.setIdemail("luigiPP@gmail.com");
    	carrelloDAO.doSave(carrello);
    	
    	carrello = new SessionCarrelloBean();
    	carrello.setCodiceVestito("MG003");
    	carrello.setIdemail("luigiPP@gmail.com");
    	carrelloDAO.doSave(carrello);
    	
    	Collection<ShopBean> actual = new LinkedList<ShopBean>();
		try {
			actual = carrelloDAO.TrovaCarrelloUtente(null);

		} catch (SQLException e) {
			e.printStackTrace();

		}
		assertNotEquals(expected, actual);
		userDAO.doDelete(utenteEsistente);
	}
  
    
    @After
    public void tearDown () throws SQLException {
        System.out.println("Sono entrato nella tearDown");
        carrelloDAO.doDelete(carrello);
        
        prodottoDAO.doDelete(prodotto1);
    	prodottoDAO.doDelete(prodotto2);
    	prodottoDAO.doDelete(prodotto3);
       
    	prodottoDAO.doDeleteCategoria("Uomo");
    	userDAO.doDelete(utenteEsistente);
    }
    
  



}