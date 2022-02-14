
package gestioneAcquistiTest;

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
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import gestioneAccount.UtenteBean;
import gestioneAcquisti.OrdineBean;
import test.*;

public class TestGestoreOrdiniDAO {
	private Connection db;
	private GestioneOrdiniModelDS ordineDAO;
	private OrdineBean ordineEsistente;
	private OrdineBean ordineEsistente2;
	private OrdineBean ordineNonEsistente;
	private UtenteBean cliente;
	private UtenteBean cliente2;
	private UtenteBean cliente3;
	private RegistrazioneModelDS userDAO;
	private PagamentoModelDS pagaDS;
	private static IDatabaseTester tester;
	private DataSource ds;

	public TestGestoreOrdiniDAO() {
	}

	@Before
	public void setUp() throws SQLException, Exception {
		tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
				"jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'resources/db/init/schema.sql'", "PROVA", "");
		tester.setSetUpOperation(DatabaseOperation.REFRESH);
		tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
		ds = Mockito.mock(DataSource.class);
		Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
		ordineDAO = new GestioneOrdiniModelDS(ds);
		userDAO = new RegistrazioneModelDS(ds);
		pagaDS = new PagamentoModelDS(ds);
		
		cliente = new UtenteBean();
		
		cliente.setNome("Francesco9");
		cliente.setCognome("Ciccone9");
		cliente.setEmail("francesco1@gmail.com");
		cliente.setIndirizzo("Via San Francesco 29");
		cliente.setPassword("ABCD1234");
		
		userDAO.doSave(cliente);
		
		
		cliente2 = new UtenteBean();
		
		cliente2.setNome("Francesco2");
		cliente2.setCognome("Ciccone2");
		cliente2.setEmail("francesco2@gmail.com");
		cliente2.setIndirizzo("Via San Francesco 29");
		cliente2.setPassword("ABCD1234");
		
		userDAO.doSave(cliente2);
		
		cliente3 = new UtenteBean();
		
		cliente3.setNome("Francesco3");
		cliente3.setCognome("Ciccone3");
		cliente3.setEmail("francesco3@gmail.com");
		cliente3.setIndirizzo("Via San Francesco 29");
		cliente3.setPassword("ABCD1234");
		
		userDAO.doSave(cliente3);
		
		ordineEsistente = new OrdineBean();

		ordineEsistente.setEmail("francesco1@gmail.com");
		ordineEsistente.setNome("Francesco");
		ordineEsistente.setCognome("Ciccone");
		ordineEsistente.setIndirizzo("Via San Francesco 30");
		ordineEsistente.setCap("83100");
		ordineEsistente.setComune("Montella");
		ordineEsistente.setProvincia("Avellino");
		ordineEsistente.setPrezzo("230");
		ordineEsistente.setProdotti("5");
		ordineEsistente.setControllato("false");

		pagaDS.SalvaOrdine(ordineEsistente);
		
		ordineEsistente2 = new OrdineBean();
		
		ordineEsistente2.setEmail("francesco2@gmail.com");
		ordineEsistente2.setNome("Francesco2");
		ordineEsistente2.setCognome("Ciccone2");
		ordineEsistente2.setIndirizzo("Via San Francesco 32");
		ordineEsistente2.setCap("83102");
		ordineEsistente2.setComune("Montella2");
		ordineEsistente2.setProvincia("Avellino2");
		ordineEsistente2.setPrezzo("2302");
		ordineEsistente2.setProdotti("52");
		ordineEsistente2.setControllato("false");
		
		pagaDS.SalvaOrdine(ordineEsistente2);
	
	}

	@Test
	public void TestFindOrdineByNumeroOrdine() throws SQLException {
		ordineEsistente = ordineDAO.doRetrieveByKey(1);
		System.out.println("OrdineEsistente" + ordineEsistente.getNome() + ordineEsistente.getNumeroOrdine());
		assertEquals(ordineEsistente, ordineDAO.doRetrieveByKey(1));
	}
	
	@Test
	public void TestFindOrdineByNumeroNonOrdine() throws SQLException {
		assertEquals(null,ordineDAO.doRetrieveByKey(100).getNumeroOrdine());
	}
	
	@Test
	public void TestFindOrdineByNumeroNonOrdineNull() throws SQLException {
		assertThrows(NumberFormatException.class, () ->{
			ordineDAO.doRetrieveByKey(Integer.parseInt(null));
        });
	}

	@Test
	public void TestFindTuttiOrdini() throws SQLException {
		
		Collection<OrdineBean> actual = new LinkedList<OrdineBean>();
		actual=ordineDAO.ritornaTuttiOrdiniDaControllare();
		
		Collection<OrdineBean> expected = new LinkedList<OrdineBean>();
		
		OrdineBean ordineEsistenteCollectio= new OrdineBean();
		ordineEsistenteCollectio.setNumeroOrdine("1");
		ordineEsistenteCollectio.setEmail("francesco1@gmail.com");
		ordineEsistenteCollectio.setNome("Francesco");
		ordineEsistenteCollectio.setCognome("Ciccone");
		ordineEsistenteCollectio.setIndirizzo("Via San Francesco 30");
		ordineEsistenteCollectio.setCap("83100");
		ordineEsistenteCollectio.setComune("Montella");
		ordineEsistenteCollectio.setProvincia("Avellino");
		ordineEsistenteCollectio.setPrezzo("230");
		ordineEsistenteCollectio.setProdotti("5");
		ordineEsistenteCollectio.setControllato("false");
		
		expected.add(ordineEsistenteCollectio);
		
		OrdineBean ordineEsistenteCollectio2= new OrdineBean();
		ordineEsistenteCollectio2.setNumeroOrdine("2");
		ordineEsistenteCollectio2.setEmail("francesco2@gmail.com");
		ordineEsistenteCollectio2.setNome("Francesco2");
		ordineEsistenteCollectio2.setCognome("Ciccone2");
		ordineEsistenteCollectio2.setIndirizzo("Via San Francesco 32");
		ordineEsistenteCollectio2.setCap("83102");
		ordineEsistenteCollectio2.setComune("Montella2");
		ordineEsistenteCollectio2.setProvincia("Avellino2");
		ordineEsistenteCollectio2.setPrezzo("2302");
		ordineEsistenteCollectio2.setProdotti("52");
		ordineEsistenteCollectio2.setControllato("false");
		
		expected.add(ordineEsistenteCollectio2);
		
		
		
		for(OrdineBean prod: expected) {								
            System.out.println("Ordini inseriti"+prod.getNumeroOrdine());
				}
		
		for(OrdineBean prod: actual) {								
            System.out.println("Ordini Presenti"+prod.getNumeroOrdine());
            System.out.println("Ordini Presenti"+prod.getEmail());
				}
		
		assertEquals(expected.size(), ordineDAO.ritornaTuttiOrdiniDaControllare().size());
		
		
	}

	@Test
	public void TestUpdateOrdine() throws SQLException {
		OrdineBean ordineEsistenteDaModificare= new OrdineBean();
		System.out.println("Ordini da modificare"+ordineEsistente2.getNumeroOrdine());
		ordineEsistenteDaModificare.setNumeroOrdine("1");
		ordineEsistenteDaModificare.setEmail("francesco2@gmail.com");
		ordineEsistenteDaModificare.setNome("Francesco3");
		ordineEsistenteDaModificare.setCognome("Ciccone3");
		ordineEsistenteDaModificare.setIndirizzo("Via San Francesco 330");
		ordineEsistenteDaModificare.setCap("83100");
		ordineEsistenteDaModificare.setComune("Montella");
		ordineEsistenteDaModificare.setProvincia("Avellino");
		ordineEsistenteDaModificare.setPrezzo("230");
		ordineEsistenteDaModificare.setProdotti("5");
		ordineEsistenteDaModificare.setControllato("false");
		
		assertEquals(true,ordineDAO.doUpdate(ordineEsistenteDaModificare));
		
	}
	
	@Test
	public void TestUpdateOrdineNonEsistente() throws SQLException {
		OrdineBean ordineEsistenteDaModificare= new OrdineBean();
		System.out.println("Ordini da modificare"+ordineEsistente2.getNumeroOrdine());
		ordineEsistenteDaModificare.setNumeroOrdine("0");
		ordineEsistenteDaModificare.setEmail("");
		ordineEsistenteDaModificare.setNome("Francesco3");
		ordineEsistenteDaModificare.setCognome("Ciccone3");
		ordineEsistenteDaModificare.setIndirizzo("Via San Francesco 330");
		ordineEsistenteDaModificare.setCap("83100");
		ordineEsistenteDaModificare.setComune("Montella");
		ordineEsistenteDaModificare.setProvincia("Avellino");
		ordineEsistenteDaModificare.setPrezzo("230");
		ordineEsistenteDaModificare.setProdotti("5");
		ordineEsistenteDaModificare.setControllato("false");
		
		assertEquals(false,ordineDAO.doUpdate(ordineEsistenteDaModificare));
	}
	

	@Test
	public void TestUpdateOrdineNull() throws SQLException {
		assertThrows(NullPointerException.class, () ->{
			ordineDAO.doUpdate(null);
        });
		
	}
	
	@Test
	public void TestDelateOrdineNull() throws SQLException {
		assertThrows(NullPointerException.class, () ->{
			ordineDAO.doDelete(null);
        });
		
	}
	
	@Test
	public void TestDelateOrdineEsistente() throws SQLException {
		assertEquals(true,ordineDAO.doDelete(ordineEsistente));
		assertNotEquals(ordineEsistente,ordineDAO.doRetrieveByKey(1));
	}
	
	@Test
	public void TestDelateOrdineNonEsistente() throws SQLException {
		assertNotEquals(ordineNonEsistente,ordineDAO.doRetrieveByKey(200));
	}
	
	@Test
	public void TestConfermaOrdineNonEsistente() throws SQLException {
		assertThrows(NullPointerException.class, () ->{
			ordineDAO.confermaOrdine(ordineNonEsistente);
        });
		
		
	}
	
	@Test
	public void TestConfermaOrdineEsistente() throws SQLException {
			assertEquals(true,ordineDAO.confermaOrdine(ordineEsistente));
        
	}

	
	@Test
	public void TestConfermaOrdineNull() throws SQLException {
		assertThrows(NullPointerException.class, () ->{
			ordineDAO.confermaOrdine(null);
        });
		
	}
	

	@After
	public void tearDown() throws SQLException {
		System.out.println("Sono entrato nella tearDown");
		userDAO.doDelete(cliente);
		userDAO.doDelete(cliente2);
		userDAO.doDelete(cliente3);
		ordineDAO.doDelete(ordineEsistente2);
		ordineDAO.doDelete(ordineEsistente);
	}
}
