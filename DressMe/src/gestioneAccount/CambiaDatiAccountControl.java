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

@WebServlet("/AccountCambiaDatiControl")
public class CambiaDatiAccountControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void doPost(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		
		 DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
		 RegistrazioneModelDS model = new RegistrazioneModelDS(ds);
		 LoginModelDS model2 = new LoginModelDS(ds);
		PrintWriter out = response.getWriter();
		
		
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		String vecchiaPassword = PasswordHasher.hash(request.getParameter("vecchiaPassword"));
		String nuovaPassword = PasswordHasher.hash(request.getParameter("nuovaPassword"));
		String confermaPassword =PasswordHasher.hash(request.getParameter("confermaPassword"));
		
		UtenteBean bean = new UtenteBean();
		
		if(email!= null){
			if(request.getParameter("nuovaPassword")!=null && request.getParameter("vecchiaPassword")!=null && request.getParameter("confermaPassword")!=null) {
								
				try {
				bean=model2.doRetrieveByKey(email);
				if(bean.getPassword().equals(vecchiaPassword)) {
					if(nuovaPassword.equals(confermaPassword)) {
						bean.setPassword(nuovaPassword);
					model.doUpdate(bean);
					}
					else {
						response.setStatus(400);
						return;
					}
				}
				else {
					response.setStatus(400);
					return;
				}
				
				} catch (SQLException e) {
					response.setStatus(400);
					return;					
				}
				out.print("Utente creato con successo!");
								
				response.setStatus(200);
				return;
			} else {
				out.print("Le password non coincidono!");
				
				response.setStatus(400);
				return;
			}
		} else {
			out.print("L'utente esiste!");
			response.setStatus(400);
			return;
		}
	}

}
