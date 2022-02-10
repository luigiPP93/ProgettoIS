package gestioneProdotto;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.sql.DataSource;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.mockito.Mockito;

import gestioneAccount.UtenteBean;
import gestioneProdotti.ShopBean;
import test.*;

public class TestProdottoDAO {

	private Connection db;
	private static IDatabaseTester tester;
	private DataSource ds;

	private ShopModelDS productDAO;

	private ShopBean prodotto1;
	private ShopBean prodotto2;
	private ShopBean prodotto3;
	private ShopBean prodottoNonInserito;

	private RegistrazioneModelDS userDAO;
	private UtenteBean utenteEsistente;

	@Before
	public void setUp() throws SQLException, Exception {

		tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
				"jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'resources/db/init/schema.sql'", "PROVA", "");
// Refresh permette di svuotare la cache dopo un modifica con setDataSet
// DeleteAll ci svuota il DB manteneno lo schema
		tester.setSetUpOperation(DatabaseOperation.REFRESH);
		tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);

		ds = Mockito.mock(DataSource.class);
		Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
		userDAO = new RegistrazioneModelDS(ds);
		productDAO = new ShopModelDS(ds);

		String ctegoria = "Uomo";
		String numero = "";
		productDAO.doSaveCategoria(ctegoria, numero);
		
		String ctegoria2 = "Donna";
		String numero2 = "";
		productDAO.doSaveCategoria(ctegoria2, numero2);
		
		String ctegoria3 = "Bambini";
		String numero3 = "";
		productDAO.doSaveCategoria(ctegoria3, numero3);
		
		utenteEsistente = new UtenteBean();
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
		prodotto2.setIdCategoria("Donna");
		prodotto2.setQuantitaVestito(20);
		prodotto2.setTitolo("T-Shirt Kalvin Klein");
		prodotto2.setDescrizione("Maglia in cotone");
		prodotto2.setPrezzo(15);
		prodotto2.setCopertina("donna_maglia2.png");

		prodotto3 = new ShopBean();
		prodotto3.setCodiceVestito("MG003");
		prodotto3.setIdCategoria("Bambini");
		prodotto3.setQuantitaVestito(30);
		prodotto3.setTitolo("Maglia rosa");
		prodotto3.setDescrizione("Maglia colore nero-verde");
		prodotto3.setPrezzo(34);
		prodotto3.setCopertina("bimba.maglia3.png");

		prodottoNonInserito = new ShopBean();
		prodottoNonInserito.setCodiceVestito("MG004");
		prodottoNonInserito.setIdCategoria("Uomo");
		prodottoNonInserito.setQuantitaVestito(10);
		prodottoNonInserito.setTitolo("T-Shirt Colors");
		prodottoNonInserito.setDescrizione("Maglia con triangolo multicolore");
		prodottoNonInserito.setPrezzo(9);
		prodottoNonInserito.setCopertina("uomo_maglia1.png");

		productDAO.doSave(prodotto1);
		productDAO.doSave(prodotto2);
		productDAO.doSave(prodotto3);

	}

	@After
	public void tearDown() throws Exception {
		productDAO.doDelete(prodottoNonInserito);
		System.out.println("Sono entrato nella tearDown");
		productDAO.doDeleteCategoria("Uomo");
		productDAO.doDelete(prodotto1);
		productDAO.doDeleteCategoria("Donna");
		productDAO.doDelete(prodotto2);
		productDAO.doDeleteCategoria("Bambini");
		productDAO.doDelete(prodotto3);
		
		
		userDAO.doDelete(utenteEsistente);

	}

	@Test
	public void TestdoRetrieveByKeyPresente() throws SQLException {
		ShopBean expected = new ShopBean();

		expected.setCodiceVestito("MG001");
		expected.setIdCategoria("Uomo");
		expected.setQuantitaVestito(10);
		expected.setTitolo("T-Shirt Colors");
		expected.setDescrizione("Maglia con triangolo multicolore");
		expected.setPrezzo(9);
		expected.setCopertina("image/Uomo/uomo_maglia1.png");

		ShopBean actual = null;
		try {
			actual = productDAO.doRetrieveByKey("MG001");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		assertEquals(expected, actual);
	}

	@Test
	public void TestdoRetrieveByKeyNonPresente() throws SQLException {
		ShopBean expected = new ShopBean();

		ShopBean actual = null;
		try {
			actual = productDAO.doRetrieveByKey("MT000");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(expected, actual);
	}
	
	@Test
	public void TestdoRetrieveByKeyNull() throws SQLException {
			 assertNotEquals(prodotto1,productDAO.doRetrieveByKey(null));
			 assertNotEquals(prodotto2,productDAO.doRetrieveByKey(null));
			 assertNotEquals(prodotto3,productDAO.doRetrieveByKey(null));
	}

	@Test
	public void TestFindAllProduct() throws SQLException {

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
		shop.setIdCategoria("Donna");
		shop.setQuantitaVestito(20);
		shop.setTitolo("T-Shirt Kalvin Klein");
		shop.setDescrizione("Maglia in cotone");
		shop.setPrezzo(15);
		shop.setCopertina("image/Donna/donna_maglia2.png");

		expected.add(shop);

		shop = new ShopBean();

		shop.setCodiceVestito("MG003");
		shop.setIdCategoria("Bambini");
		shop.setQuantitaVestito(30);
		shop.setTitolo("Maglia rosa");
		shop.setDescrizione("Maglia colore nero-verde");
		shop.setPrezzo(34);
		shop.setCopertina("image/Bambini/bimba.maglia3.png");

		expected.add(shop);

		Collection<ShopBean> actual = new LinkedList<ShopBean>();
		try {

			actual = productDAO.doRetrieveAll("");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		assertEquals(expected, actual);
	}
	
	@Test
	public void TestFindAllProductUomo() throws SQLException {

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

		

		Collection<ShopBean> actual = new LinkedList<ShopBean>();
		try {

			actual = productDAO.doRetrieveAllUomo("");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		assertEquals(expected, actual);
	}
	
	@Test
	public void TestFindAllProductBambini() throws SQLException {

		ShopBean shop;
		Collection<ShopBean> expected = new LinkedList<ShopBean>();

		

		shop = new ShopBean();

		shop.setCodiceVestito("MG003");
		shop.setIdCategoria("Bambini");
		shop.setQuantitaVestito(30);
		shop.setTitolo("Maglia rosa");
		shop.setDescrizione("Maglia colore nero-verde");
		shop.setPrezzo(34);
		shop.setCopertina("image/Bambini/bimba.maglia3.png");

		expected.add(shop);

		

		Collection<ShopBean> actual = new LinkedList<ShopBean>();
		try {

			actual = productDAO.doRetrieveAllBambini("");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		assertEquals(expected, actual);
	}
	
	@Test
	public void TestFindAllProductDonna() throws SQLException {

		ShopBean shop;
		Collection<ShopBean> expected = new LinkedList<ShopBean>();

		shop = new ShopBean();

		shop.setCodiceVestito("MG002");
		shop.setIdCategoria("Donna");
		shop.setQuantitaVestito(20);
		shop.setTitolo("T-Shirt Kalvin Klein");
		shop.setDescrizione("Maglia in cotone");
		shop.setPrezzo(15);
		shop.setCopertina("image/Donna/donna_maglia2.png");

		expected.add(shop);

		

		Collection<ShopBean> actual = new LinkedList<ShopBean>();
		try {

			actual = productDAO.doRetrieveAllDonna("");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		assertEquals(expected, actual);
	}
	
	@Test
	public void TestFindAllProductNull() throws SQLException {

		ShopBean shop;
		Collection<ShopBean> expected = new LinkedList<ShopBean>();

		shop = new ShopBean();

		shop.setCodiceVestito("MG002");
		shop.setIdCategoria("Donna");
		shop.setQuantitaVestito(20);
		shop.setTitolo("T-Shirt Kalvin Klein");
		shop.setDescrizione("Maglia in cotone");
		shop.setPrezzo(15);
		shop.setCopertina("image/Donna/donna_maglia2.png");

		expected.add(shop);

		

		Collection<ShopBean> actual = new LinkedList<ShopBean>();
		try {

			actual = productDAO.doRetrieveAllDonna(null);

		} catch (SQLException e) {
			e.printStackTrace();

		}
		assertNotEquals(expected, actual);
	}

	@Test
	public void TestDoUpdate() throws SQLException {
		ShopBean expected = new ShopBean();
		ShopBean actual = new ShopBean();
		expected.setCodiceVestito("MG001");
		expected.setIdCategoria("Uomo");
		expected.setQuantitaVestito(50);
		expected.setTitolo("T-Shirt Colors");
		expected.setDescrizione("Maglia multicolore");
		expected.setPrezzo(9);
		expected.setCopertina("image/Uomo/uomo_maglia1.png");
		boolean prova = false;
		try {
			prova = productDAO.doUpdate(expected);
			actual = productDAO.doRetrieveByKey("MG001");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(true, prova);
		
	}
	
	@Test
	public void TestDoUpdateNonPresente() throws SQLException {
		ShopBean expected = new ShopBean();
		ShopBean actual = new ShopBean();
		expected.setCodiceVestito("MG004");
		expected.setIdCategoria("Uomo");
		expected.setQuantitaVestito(50);
		expected.setTitolo("T-Shirt Colors");
		expected.setDescrizione("Maglia con triangolo multicolore");
		expected.setPrezzo(9);
		expected.setCopertina("image/Uomo/uomo_maglia1.png");
		boolean prova = false;
		try {
			prova = productDAO.doUpdate(expected);
			actual = productDAO.doRetrieveByKey("MG004");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(true, prova);
		assertNotEquals(expected, actual);
	}
	
	@Test
	public void TestDoUpdateNull() throws SQLException {
		ShopBean expected = new ShopBean();
		ShopBean actual = new ShopBean();
		expected.setCodiceVestito("MG001");
		expected.setIdCategoria("Uomo");
		expected.setQuantitaVestito(50);
		expected.setTitolo("T-Shirt Colors");
		expected.setDescrizione("Maglia con triangolo multicolore");
		expected.setPrezzo(9);
		expected.setCopertina("image/Uomo/uomo_maglia1.png");
		boolean prova = false;
		try {
			prova = productDAO.doUpdate(expected);
			actual = productDAO.doRetrieveByKey(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(true, prova);
		assertNotEquals(expected, actual);
	}

	@Test
	public void TestDoSave() throws SQLException {
		try {
			 productDAO.doSave(prodottoNonInserito);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(prodottoNonInserito.getCodiceVestito(), productDAO.doRetrieveByKey("MG004").getCodiceVestito());
	}
	
	@Test
	public void TestDoSaveNonEsistente() throws SQLException {
		assertThrows(SQLException.class, () ->{
			productDAO.doSave(prodotto1);
        });

	}
	
	@Test
	public void TestDoSaveNull() throws SQLException {
		assertThrows(NullPointerException.class, () ->{
			productDAO.doSave(null);
        });

	}


	@Test
	public void TestDoDeleteEsistente() throws SQLException {

		boolean actual = false;

		try {
			actual = productDAO.doDelete(prodotto1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(true, actual);

	}
	
	@Test
	public void TestDoDeleteNull() throws SQLException {
		assertThrows(NullPointerException.class, () ->{
			productDAO.doDelete(null);
        });
	}
	@Test
	public void TestDoDeleteEsistenteNonEsistente() throws SQLException {
		assertNotEquals(prodottoNonInserito, productDAO.doRetrieveByKey("MG004"));
			productDAO.doDelete(prodottoNonInserito);
			assertNotEquals(prodottoNonInserito, productDAO.doRetrieveByKey("MG004"));
      
	}


}