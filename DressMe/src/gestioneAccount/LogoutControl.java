package gestioneAccount;

import java.io.IOException;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/LogoutControl")
public class LogoutControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// doGet
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Ciao2");
		//DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
		//GestioneCarrelloModelDS model=new GestioneCarrelloModelDS (ds);


		HttpSession sessionOld = request.getSession(true);
		
		if(sessionOld != null) {
			sessionOld.invalidate();
		}

		 getServletContext().getRequestDispatcher("/logout.jsp").forward(request, response);
		
        
	}

	//doPost
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}



