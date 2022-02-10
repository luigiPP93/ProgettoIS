package gestioneAcquisti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/SpedizioneControl")
public class SpedizioneControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
           	

    //doPost
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	HttpSession session = request.getSession();
        
    	String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String indirizzo = request.getParameter("indirizzo");
		String cap = request.getParameter("cap");
    	String comune =  request.getParameter("comune");
    	String provincia =  request.getParameter("provincia");
    	
    	
        session.setAttribute("nomeS", nome);
        session.setAttribute("cognomeS", cognome);
        session.setAttribute("indirizzoS", indirizzo);
        session.setAttribute("capS",cap);
        session.setAttribute("comuneS", comune);
        session.setAttribute("provinciaS", provincia);
        
        System.out.println("DATII SPEDIZIONE"+nome+","+cognome+","+indirizzo+","+cap+","+comune+","+provincia);
        if(nome.length()>0 && cognome.length()>0 && indirizzo.length()>0 && cap.length()>0 && comune.length()>0 && provincia.length()>0) {
     
        response.setStatus(200);
       return;
        }
        else {
       	response.setStatus(400);
		return;
        }
        
    }
    }
