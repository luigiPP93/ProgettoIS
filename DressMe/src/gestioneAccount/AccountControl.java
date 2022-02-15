package gestioneAccount;

import java.io.IOException;
import java.sql.SQLException;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import gestioneAcquisti.GestioneOrdiniModelDS;
import gestioneAcquisti.OrdineModelDS;
import gestioneCarrello.*;

@WebServlet("/AccountControl")
public class AccountControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    // doGet
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        GestioneOrdiniModelDS model2 = new GestioneOrdiniModelDS(ds);
        
		String email =(String) request.getSession().getAttribute("email");
		
		
		if(email != null) {
			try {
				request.removeAttribute("products");
				request.setAttribute("products", model2.ritornaTuttiOrdiniUtente(email));
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("ci sei quasi");
       getServletContext().getRequestDispatcher("/account.jsp").forward(request, response);
       
    }

    //doPost
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

 

        doGet(request, response);
    }

 

}