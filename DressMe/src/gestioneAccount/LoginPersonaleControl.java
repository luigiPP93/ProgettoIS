package gestioneAccount;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


import it.unisa.utils.PasswordHasher;


@WebServlet("/LoginPersonaleControl")
public class LoginPersonaleControl extends HttpServlet {

	private static final long serialVersionUID = 5201749135928085764L;

	protected void doPost(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
	    LoginModelDS model = new LoginModelDS(ds);
		PrintWriter out = response.getWriter();
		PersonaleBean bean = new PersonaleBean();
		
		
		HttpSession sessionOld = request.getSession(true);
		
		if(sessionOld != null) {
			sessionOld.invalidate();
		}

		try {
			bean = model.doRetrieveByKeyPersonale(request.getParameter("username"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(bean.getEmail()==null) {

			out.print("Errore di credenziali.Tornare indietro e riprovare!");
		
		}
		if(bean != null) {
			 
			System.out.println("PASSWORD"+bean.getPassword());	
			if(bean.getPassword().equals(PasswordHasher.hash((request.getParameter("password"))))) {
				response.setStatus(200);
					System.out.println("sei entratooo");			
					HttpSession session = request.getSession();
			     
			        session.setMaxInactiveInterval(5*60);
			       
			        session.setAttribute("RUOLO", bean.getRuolo());
			        System.out.println("RUOLO"+bean.getRuolo());
			        
			        if(bean.getRuolo().equals("Gestore prodotti")) {
			        	getServletContext().getRequestDispatcher("/adminProdotti.jsp").forward(request, response);
			        }
			        else if(bean.getRuolo().equals("Gestore ordini"))
			        {
			        	getServletContext().getRequestDispatcher("/adminOrdini.jsp").forward(request, response);
			        }
			        else if(bean.getRuolo().equals("Direttore"))
			        {
			        	getServletContext().getRequestDispatcher("/adminDirettore.jsp").forward(request, response);
			        }
			        else {
			        	System.out.println("nessun Accesso");
			        }
			        
			} else {
				out.print("Errore di credenziali.Tornare indietro e riprovare!");
							
			}
		}
	}
	
}
