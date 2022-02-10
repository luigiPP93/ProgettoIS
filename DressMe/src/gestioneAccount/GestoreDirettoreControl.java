package gestioneAccount;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.utils.PasswordHasher;
import it.unisa.utils.Utility;

@WebServlet("/AdminDirettore")
public class GestoreDirettoreControl  extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
		GestororeDirettoreModelDS mD = new GestororeDirettoreModelDS(ds);	
		
		String action = request.getParameter("action");	
		
		try {
			if(action != null) {
				if( action.equals("details")) {
					String id = request.getParameter("id");
					request.removeAttribute("product");
					request.setAttribute("product", mD.doRetrieveByKey(id));					
				}
				else if ( action.equals("delete")) {
					String id = request.getParameter("id");
					PersonaleBean bean = mD.doRetrieveByKey(id);
					if(bean!=null) {
						mD.doDelete(bean);
						request.setAttribute("message", "Personale "+bean.getEmail()+" eliminato");
					}
				}	else if ( action.equals("update")) {
					String id = request.getParameter("id");
					String nome = request.getParameter("nome");
					String cognome = request.getParameter("cognome");
					String password = PasswordHasher.hash((request.getParameter("password")));
					String ruolo = request.getParameter("ruolo");
										
					PersonaleBean bean = new PersonaleBean();
					bean.setNome(nome);
					bean.setCognome(cognome);
					bean.setEmail(id);
					bean.setPassword(password);
					bean.setRuolo(ruolo);
					
					
					mD.doUpdate(bean);
					request.setAttribute("message", "Personale " + bean.getEmail() + " aggiornato");
				} else if (action.equals("insert")) {
					String nome = request.getParameter("nome");
					String cognome = request.getParameter("cognome");
					String email = request.getParameter("email");
					String password = PasswordHasher.hash(request.getParameter("password"));
					String ruolo = request.getParameter("ruolo");
				
					
					PersonaleBean bean = new PersonaleBean();
					bean.setNome(nome);
					bean.setCognome(cognome);
					bean.setEmail(email);
					bean.setPassword(password);
					bean.setRuolo(ruolo);
					
					mD.inserisciPersonale(bean);
					request.setAttribute("message", "Personale " + bean.getEmail() + " inserito");
				
				}
			}
			
		} catch (Exception e) {
			Utility.print(e);
			request.setAttribute("error", e.getMessage());
		}
		
		
		try {
			request.setAttribute("products", mD.stampaTuttoIlPersonale());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		
		getServletContext().getRequestDispatcher("/adminDirettore.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
