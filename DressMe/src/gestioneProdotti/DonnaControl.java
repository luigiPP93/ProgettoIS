package gestioneProdotti;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.utils.Utility;

@WebServlet("/DonnaControl")
public class DonnaControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// doGet
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
		ShopModelDS model = new ShopModelDS(ds);
		
		try {
			request.setAttribute("Donna", model.doRetrieveAllDonna(""));
		
		}catch(SQLException e ){
			Utility.print(e);
			
			request.setAttribute("error", e.getMessage());
		}
		
		getServletContext().getRequestDispatcher("/donna.jsp").forward(request, response);
	}

	//doPost
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}