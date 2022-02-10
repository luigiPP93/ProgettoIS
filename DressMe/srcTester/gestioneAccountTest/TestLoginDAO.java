package gestioneAccountTest;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import gestioneAccount.PersonaleBean;
import gestioneAccount.UtenteBean;
import test.*;
import it.unisa.utils.PasswordHasher;

public class TestLoginDAO {

    private Connection db;
    private LoginModelDS LoginDAO;
    private RegistrazioneModelDS UtenteDAO;
    private GestionePersonaleModelDS PersonaleDAO;
    
    private PersonaleBean PersonaleEsistente;
    private PersonaleBean PersonaleNonEsistente;
    
    private UtenteBean utenteEsistente;
    private UtenteBean utenteNonEsistente;
    
    
    private static IDatabaseTester tester;
	private DataSource ds;
    
    public TestLoginDAO () {}

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
    LoginDAO = new LoginModelDS(ds);
    UtenteDAO = new RegistrazioneModelDS(ds);
    PersonaleDAO = new GestionePersonaleModelDS(ds);

    PersonaleEsistente = new PersonaleBean();
    PersonaleEsistente.setNome("luigi");
    PersonaleEsistente.setCognome("Sella");
    PersonaleEsistente.setEmail("luigiPersonale@gmail.com");
    PersonaleEsistente.setRuolo("Gestore Ordini");
    PersonaleEsistente.setPassword("bbb7b19fe1ed2f32883d32234246d88462df528f1a05d358957aeed805a447e0");
    PersonaleDAO.inserisciPersonale(PersonaleEsistente);

    utenteEsistente = new UtenteBean();
    utenteEsistente.setNome("luigi");
    utenteEsistente.setCognome("Sella");
    utenteEsistente.setEmail("luigi@gmail.com");
    utenteEsistente.setIndirizzo("via Roma 41, Avellino");
    utenteEsistente.setPassword("bbb7b19fe1ed2f32883d32234246d88462df528f1a05d358957aeed805a447e0");
    UtenteDAO.doSave(utenteEsistente);

    utenteNonEsistente = new UtenteBean();
    utenteNonEsistente.setNome("Utente");
    utenteNonEsistente.setCognome("Non esistente");
    utenteNonEsistente.setEmail("UtenteNonEsistente@gmail.com");
    utenteNonEsistente.setIndirizzo("Via San Nessuno 29");
    utenteNonEsistente.setPassword("AdminAdmin");

    PersonaleNonEsistente = new PersonaleBean();
    PersonaleNonEsistente.setNome("Utente");
    PersonaleNonEsistente.setCognome("Non esistente");
    PersonaleNonEsistente.setEmail("PersonaleNonEsistente@gmail.com");
    PersonaleNonEsistente.setPassword("AdminAdmin");
    PersonaleNonEsistente.setRuolo("Gestore Prodotti");
    }


    @Test
    public void TestTrovaUtenteEsistente() throws SQLException {
    assertEquals(utenteEsistente,LoginDAO.doRetrieveByKey(utenteEsistente.getEmail()));
    }
    
    @Test
    public void TestTrovaUtenteNonEsistente() throws SQLException {
    assertNotEquals(utenteNonEsistente,LoginDAO.doRetrieveByKey(utenteNonEsistente.getEmail()));
    }
    
    @Test
    public void TestTrovaUtenteNull() throws SQLException {
    assertNotEquals(utenteEsistente,LoginDAO.doRetrieveByKey(null));
    }

    @Test
    public void TestTrovaPersonaleEsistente() throws SQLException {
    assertEquals(PersonaleEsistente,LoginDAO.doRetrieveByKeyPersonale(PersonaleEsistente.getEmail()));
    }
    
    @Test
    public void TestTrovaPersonaleNonEsistente() throws SQLException {
    assertNotEquals(PersonaleNonEsistente,LoginDAO.doRetrieveByKeyPersonale(PersonaleNonEsistente.getEmail()));
    }
    
    @Test
    public void TestTrovaPersonalenull() throws SQLException {
    assertNotEquals(PersonaleEsistente,LoginDAO.doRetrieveByKeyPersonale(null));
    }



    @Test
    public void TestVerificaLogin () throws SQLException, ClassNotFoundException {
    String email= "luigi@gmail.com";
    String password = PasswordHasher.hash("AdminAdmin");
    assertEquals(LoginDAO.doRetrieveByKey(email),LoginDAO.validazione(email,password));
    }
    
 
    
    @Test
    public void TestVerificaLoginUteneteNull () throws SQLException, ClassNotFoundException {
    assertNotEquals(LoginDAO.doRetrieveByKey("luigi@gmail.com"),LoginDAO.validazione(null,null));
    }

    @Test
    public void TestVerificaLoginPersonale () throws SQLException, ClassNotFoundException {
    String email= "luigiPersonale@gmail.com";
    String password = PasswordHasher.hash("AdminAdmin");
    assertEquals(PersonaleDAO.doRetrieveByKey(email),LoginDAO.validazionePersonale(email,password));
    System.out.println("Accesso consentito al "+PersonaleDAO.doRetrieveByKey(email).getRuolo());
    }
    
    @Test
    public void TestVerificaLoginPersonaleNull () throws SQLException, ClassNotFoundException {

    assertNotEquals(PersonaleDAO.doRetrieveByKey("luigiPersonale@gmail.com"),LoginDAO.validazionePersonale(null,null));
    }
   

    @Test
    public void TestVerificaLoginNonRegistrato () throws SQLException, ClassNotFoundException {
    String email= "UtenteNonEsistente@gmail.com";
    String password = PasswordHasher.hash("AdminAdmin");
    assertNotEquals(utenteNonEsistente,LoginDAO.validazione(email,password));

    }

    @Test
    public void TestVerificaLoginPeronaleNonInserito () throws SQLException, ClassNotFoundException {
    String email= "PersonaleNonEsistente@gmail.com";
    String password = PasswordHasher.hash("AdminAdmin");
    assertNotEquals(PersonaleNonEsistente,LoginDAO.validazionePersonale(email,password));

    }




    @After
    public void tearDown () throws SQLException {
    System.out.println("Sono entrato nella tearDown");
    UtenteDAO.doDelete(utenteEsistente);
    UtenteDAO.doDelete(utenteNonEsistente);
    PersonaleDAO.doDelete(PersonaleEsistente);
    }

}

