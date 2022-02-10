package gestioneAcquisti;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.utils.Utility;

@WebServlet("/AdminOrdini")
public class GestoreOrdiniControl  extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
		GestioneOrdiniModelDS mO= new GestioneOrdiniModelDS(ds);
		

	
		String action = request.getParameter("action");
		
		try {
			if(action != null) {
				if( action.equals("details")) {
					String ordine = request.getParameter("id");
					request.removeAttribute("product");
					request.setAttribute("product", mO.doRetrieveByKey(ordine));					
				}
				else if ( action.equals("delete")) {
					String id = request.getParameter("id");
					GestioneOrdineBean bean = mO.doRetrieveByKey(id);
					if(bean!=null) {
						mO.doDelete(bean);
						request.setAttribute("message", "Product "+bean.getNumeroOrdine()+" deleted");
					}
				}	else if ( action.equals("update")) {
					String id = request.getParameter("id");
					String numeroOrdine =request.getParameter("NumeroOrdine");
					GestioneOrdineBean o = mO.doRetrieveByKey(id);
					String email =o.getEmail();
					String nome =request.getParameter("nome");
					String cognome =request.getParameter("cognome");
					String indirizzo =request.getParameter("indirizzo");
					String cap =request.getParameter("cap");
					String comune =request.getParameter("comune");
					String provincia =request.getParameter("provincia");
					String prezzo =request.getParameter("prezzo");
					String prodotti =request.getParameter("prodotti");
					String controllato = request.getParameter("controllato");
					
					
					GestioneOrdineBean bean = new GestioneOrdineBean();
					
					bean.setNumeroOrdine(id);
					bean.setEmail(email);
					bean.setNome(nome);
					bean.setCognome(cognome);
					bean.setIndirizzo(indirizzo);
					bean.setCap(cap);
					bean.setComune(comune);
					bean.setProvincia(provincia);
					bean.setPrezzo(prezzo);
					bean.setProdotti(prodotti);
					bean.setControllato(controllato);
					
					mO.doUpdate(bean);
					request.setAttribute("message", "Product " + bean.getNumeroOrdine()+ " updated");
				} else if (action.equals("conferma")) {
					String id = request.getParameter("id");
					GestioneOrdineBean bean = mO.doRetrieveByKey(id);
					mO.confermaOrdine(bean);
					request.setAttribute("message", "Numero Ordine " + bean.getNumeroOrdine() + " confermato");
				}
			}
			
		} catch (Exception e) {
			Utility.print(e);
			request.setAttribute("error", e.getMessage());
		}
		
		try {
			request.setAttribute("products", mO.ritornaTuttiOrdiniDaControllare());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		getServletContext().getRequestDispatcher("/adminOrdini.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
