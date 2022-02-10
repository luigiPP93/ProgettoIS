
package gestioneAccountTest;

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
import gestioneAccount.PersonaleBean;
import test.*;

public class TestGestorePersonaleDAO {
	private Connection db;
	private GestionePersonaleModelDS personaleDAO;
	private PersonaleBean personaleEsistente;
	private PersonaleBean personaleNonEsistente;
	private static IDatabaseTester tester;
	private DataSource ds;

	public TestGestorePersonaleDAO() {
	}

	@Before
	public void setUp() throws SQLException, Exception {
	tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
	"jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'resources/db/init/schema.sql'", "PROVA", "");
	tester.setSetUpOperation(DatabaseOperation.REFRESH);
	tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
	ds = Mockito.mock(DataSource.class);
	Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());

	personaleDAO = new GestionePersonaleModelDS(ds);

	personaleEsistente = new PersonaleBean();

	personaleEsistente.setNome("Francesco1");
	personaleEsistente.setCognome("Ciccone1");
	personaleEsistente.setEmail("francesco1@gmail.com");
	personaleEsistente.setPassword("ABCD1234");
	personaleEsistente.setRuolo("Gestore Ordini");

	personaleDAO.inserisciPersonale(personaleEsistente);

	personaleNonEsistente = new PersonaleBean();

	personaleNonEsistente.setNome("Francesco2");
	personaleNonEsistente.setCognome("Ciccone2");
	personaleNonEsistente.setEmail("francesco2@gmail.com");
	personaleNonEsistente.setPassword("ABCD1234");
	personaleNonEsistente.setRuolo("Gestore Ordini");
	}



	@Test
	public void TestFindPersonaleByEmailExisting() throws SQLException {
	assertEquals(personaleEsistente, personaleDAO.doRetrieveByKey("francesco1@gmail.com"));
	}
	
	
	@Test
	public void TestFindPersonaleByEmailNull() throws SQLException {
	assertNotEquals(personaleEsistente, personaleDAO.doRetrieveByKey(null));
	}



	@Test
	public void TestFindPersonaleByEmailNotExisting() throws SQLException {
	assertNotEquals(personaleNonEsistente, personaleDAO.doRetrieveByKey("francesco2@gmail.com"));
	}



	@Test
	public void TestDeletePersonale() throws SQLException {
	personaleDAO.doDelete(personaleEsistente);
	assertNotEquals(personaleEsistente, personaleDAO.doRetrieveByKey("francesco2@gmail.com"));
	}
	
	@Test
	public void TestDeletePersonaleNonEsistente() throws SQLException {
	assertEquals(false,personaleDAO.doDelete(personaleNonEsistente));
	}
	
	
	@Test
	public void TestDeletePersonaleNull() throws SQLException {
		assertThrows(NullPointerException.class, () ->{
			personaleDAO.doDelete(null);
        });
	}
	
	
	
	@Test
	public void TestFindTuttoPersonale () throws SQLException {
	Collection<PersonaleBean> expected = new LinkedList<PersonaleBean>();
	expected.add(personaleEsistente);
	assertEquals(expected,personaleDAO.stampaTuttoIlPersonale());
	}

	@Test
	public void TestUpdatePersonale() throws SQLException {
	personaleEsistente.setNome("Francesco3");
	personaleEsistente.setCognome("Ciccone3");
	personaleEsistente.setPassword("ABCD1234");
	personaleEsistente.setRuolo("Gestore prodotti");

	personaleDAO.doUpdate(personaleEsistente);
	personaleEsistente=personaleDAO.doRetrieveByKey("francesco1@gmail.com");
	assertEquals(personaleEsistente.getNome(),personaleDAO.doRetrieveByKey("francesco1@gmail.com").getNome());
	assertEquals(personaleEsistente.getCognome(),personaleDAO.doRetrieveByKey("francesco1@gmail.com").getCognome());
	assertEquals(personaleEsistente.getEmail(),personaleDAO.doRetrieveByKey("francesco1@gmail.com").getEmail());
	assertEquals(personaleEsistente.getPassword(),personaleDAO.doRetrieveByKey("francesco1@gmail.com").getPassword());
	assertEquals(personaleEsistente.getRuolo(),personaleDAO.doRetrieveByKey("francesco1@gmail.com").getRuolo());

	}
	
	@Test
	public void TestUpdatePersonaleNonEsistente() throws SQLException {
	personaleEsistente.setNome("Francesco3");
	personaleEsistente.setCognome("Ciccone3");
	personaleEsistente.setPassword("ABCD1234");
	personaleEsistente.setRuolo("Gestore prodotti");

	assertEquals(false,personaleDAO.doUpdate(personaleNonEsistente));

	}
	
	@Test
	public void TestUpdatePersonaleNull() throws SQLException {

	assertEquals(false,personaleDAO.doUpdate(null));

	}



	@After
	public void tearDown() throws SQLException {
	System.out.println("Sono entrato nella tearDown");
	personaleDAO.doDelete(personaleEsistente);
	personaleDAO.doDelete(personaleNonEsistente);
	}
}