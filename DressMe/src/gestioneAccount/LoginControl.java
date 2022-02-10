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


@WebServlet("/LoginControl")
public class LoginControl extends HttpServlet {

	private static final long serialVersionUID = 5201749135928085764L;

	public void doPost(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
	    LoginModelDS model = new LoginModelDS(ds);
		PrintWriter out = response.getWriter();
		UtenteBean bean = new UtenteBean();
		
		
		
		try {
			bean = model.doRetrieveByKey(request.getParameter("username"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String nome = bean.getNome();
		if(bean != null) {
			 
				
			if(bean.getPassword().equals(PasswordHasher.hash((request.getParameter("password"))))) {
				response.setStatus(200);
					System.out.println("sei entratoo");			
					HttpSession session = request.getSession();
			        String conto = "0";
			        session.setAttribute("conto", conto);
			        session.setMaxInactiveInterval(5*60);
			        session.setAttribute("nome", nome);
			        String token = "ciaooo";
                    session.setAttribute("token", token);
			        session.setAttribute("email", bean.getEmail());
			        session.setAttribute("Cliente", "1");
					response.encodeURL("index.jsp");
				return;
			} else {
				out.print("La password &egrave; errata!");
				response.setStatus(400);
				return;
			}
		}
	}
	
}
