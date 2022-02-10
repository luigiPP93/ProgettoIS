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
import org.mockito.Mockito;

import gestioneAccount.UtenteBean;
import test.*;

public class TestRegistrazioneDAO {

    private Connection db;
    private RegistrazioneModelDS userDAO;
    private UtenteBean utenteEsistente;
    private UtenteBean utenteNonEsistente;
    private static IDatabaseTester tester;
	private DataSource ds;
    
    public TestRegistrazioneDAO () {}

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
    utenteEsistente=new UtenteBean();
    utenteEsistente.setNome("PPEsistente");
    utenteEsistente.setCognome("esistente");
    utenteEsistente.setEmail("esistente@gmail.com");
    utenteEsistente.setIndirizzo("Via San Francesco 29");
    utenteEsistente.setPassword("ABCD1234");
    userDAO.doSave(utenteEsistente);

    utenteNonEsistente = new UtenteBean();
    utenteNonEsistente.setNome("Utente");
    utenteNonEsistente.setCognome("Non esistente");
    utenteNonEsistente.setEmail("UtenteNonEsistente@gmail.com");
    utenteNonEsistente.setIndirizzo("Via San Nessuno 29");
    utenteNonEsistente.setPassword("ABCD1234");
    }

    @Test
    public void TestVerificaPresenzaUtenteNonEsistente () throws SQLException {
    assertNotEquals(utenteNonEsistente,userDAO.doRetrieveByKey(utenteNonEsistente.getEmail()));
    }

    @Test
    public void TestTrovaUtenteEsistente() throws SQLException {
    assertEquals(utenteEsistente,userDAO.doRetrieveByKey(utenteEsistente.getEmail()));
    }
    
    @Test
    public void TestTrovaUtenteNull() throws SQLException {
    assertNotEquals(utenteEsistente,userDAO.doRetrieveByKey(null));
    }


    @Test
    public void TestIserisciNuovoUtente () throws SQLException {
    userDAO.doSave(utenteNonEsistente);
    assertEquals(utenteNonEsistente,userDAO.doRetrieveByKey(utenteNonEsistente.getEmail()));
    }
    
    @Test
    public void TestIserisciNuovoUtenteNull () throws SQLException {
    	assertThrows(NullPointerException.class, () ->{
    		userDAO.doSave(null);
        });

    }
    
    @Test
    public void TestIserisciNuovoUtenteEsistente () throws SQLException {
    
    assertThrows(SQLException.class, () ->{
    	userDAO.doSave(utenteEsistente);
    });
    }
    

    @Test
    public void TestEliminaUtenteEsistente () throws SQLException {
    assertEquals(true,userDAO.doDelete(utenteEsistente));
    System.out.println("Utente Eliminato");
    assertNotEquals(utenteEsistente,userDAO.doRetrieveByKey(utenteEsistente.getEmail()));
    }

    @Test
    public void TestModificaPasswordUtenteEsistente() throws SQLException {
    utenteEsistente = userDAO.doRetrieveByKey(utenteEsistente.getEmail());
    utenteEsistente.setPassword("ABCDEF9339");
    assertEquals(true,userDAO.doUpdatePassword(utenteEsistente));
    assertEquals(utenteEsistente,userDAO.doRetrieveByKey(utenteEsistente.getEmail()));
    System.out.println("Utente Password Aggiornata "+utenteEsistente.getPassword());
    }
    
    @Test
    public void TestModificaPasswordUtenteNull() throws SQLException {
    	assertThrows(NullPointerException.class, () ->{
    		userDAO.doUpdatePassword(null);
        });
    }
    
    @Test
    public void TestModificaPasswordUtenteNonEsistente() throws SQLException {
    utenteNonEsistente = userDAO.doRetrieveByKey(utenteNonEsistente.getEmail());
    utenteNonEsistente.setPassword("ABCDEF9339");
    assertEquals(true,userDAO.doUpdatePassword(utenteNonEsistente));
    assertNotEquals(utenteNonEsistente,userDAO.doRetrieveByKey(utenteNonEsistente.getEmail()));
    }
  

    @Test
    public void TestModificaDatiUtenteEsistente() throws SQLException {
    utenteEsistente = userDAO.doRetrieveByKey(utenteEsistente.getEmail());
    utenteEsistente.setNome("PPEsistente2");
    utenteEsistente.setCognome("esistente2");
    utenteEsistente.setEmail("esistente@gmail.com");
    utenteEsistente.setIndirizzo("Via San Francesco 29");
    utenteEsistente.setPassword("ABCD12345");

    assertEquals(true,userDAO.doUpdate(utenteEsistente));
    assertEquals(utenteEsistente,userDAO.doRetrieveByKey(utenteEsistente.getEmail()));
    System.out.println("Utente Password Aggiornata "+utenteEsistente.getPassword());
    }
    
    @Test
    public void TestModificaDatiUtenteNonEsistente() throws SQLException {
    utenteNonEsistente = userDAO.doRetrieveByKey(utenteNonEsistente.getEmail());
    utenteNonEsistente.setNome("PPEsistente2");
    utenteNonEsistente.setCognome("esistente2");
    utenteNonEsistente.setEmail("esistente@gmail.com");
    utenteNonEsistente.setIndirizzo("Via San Francesco 29");
    utenteNonEsistente.setPassword("ABCD12345");

    assertNotEquals(false,userDAO.doUpdate(utenteNonEsistente));
    }
    
    @Test
    public void TestModificaDatiUtenteNull() throws SQLException {
    	assertThrows(NullPointerException.class, () ->{
    		userDAO.doUpdate(null);
        });
    }
    @Test
    public void TestTrovaTuttiUtenti() throws SQLException {

    Collection<UtenteBean> expected = new LinkedList<UtenteBean>();
    expected.add(utenteEsistente);
    assertEquals(expected,userDAO.doRetrieveAll(null));
    }



    @After
    public void tearDown () throws SQLException {
    System.out.println("Sono entrato nella tearDown");
    userDAO.doDelete(utenteEsistente);
    userDAO.doDelete(utenteNonEsistente);
    }


    }