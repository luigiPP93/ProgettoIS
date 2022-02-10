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

@WebServlet("/CambiaPasswordControl")
public class CambiaPasswordControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void doPost(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		
		System.out.print("Inizio Control");
		DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
		RegistrazioneModelDS model = new RegistrazioneModelDS(ds);
		LoginModelDS model1= new LoginModelDS(ds);
		PrintWriter out = response.getWriter();
		
		String email = request.getParameter("email");
		String vecchiaPassword =PasswordHasher.hash (request.getParameter("vecchiaPassword"));
		String nuovaPassword = PasswordHasher.hash(request.getParameter("nuovaPassword"));
		String confermaPassword = PasswordHasher.hash(request.getParameter("confermaPassword"));
		
		
		if(email !=null){
			if(request.getParameter("vecchiaPassword")!=null&&request.getParameter("nuovaPassword")!=null&&request.getParameter("confermaPassword")!=null) {
				
				try {
					UtenteBean bean = new UtenteBean();
					bean=model1.doRetrieveByKey(email);	
					if(bean.getPassword().equals(vecchiaPassword)) {
					
						if(nuovaPassword.equals(confermaPassword)) {
							bean.setCognome(nuovaPassword);
							model.doUpdatePassword(bean);
						}
					} 
					else {
						out.print("Le password non coincidono!");
						
						response.setStatus(200);
						return;
					}
				}
					catch (SQLException e) {
					response.setStatus(400);
					return;
					
				}
				out.print("Utente creato con successo!");
				
				response.setStatus(200);
				return;
				} 
			
		} else {
			out.print("L'utente esiste!");
			
			response.setStatus(200);
			return;
		}
	}
}
