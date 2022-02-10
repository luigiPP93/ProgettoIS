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

@WebServlet("/AdminProdotti")
public class GestoreProdottiControl  extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
		ShopModelDS model = new ShopModelDS(ds);
		
		
		
		String sort = request.getParameter("sort");
		String action = request.getParameter("action");
		
		
		try {
			if(action != null) {
				if( action.equals("details")) {
					String id = request.getParameter("id");
					request.removeAttribute("product");
					request.setAttribute("product", model.doRetrieveByKey(id));					
				}
				else if ( action.equals("delete")) {
					String id = request.getParameter("id");
					ShopBean bean = model.doRetrieveByKey(id);
					if(bean!=null && !bean.isEmpty()) {
						model.doDelete(bean);
						request.setAttribute("message", "Product "+bean.getTitolo()+" deleted");
					}
				}	else if ( action.equals("update")) {

					String id = request.getParameter("id");
					String idCategoria = request.getParameter("idcategoria");
					String titolo = request.getParameter("titolo");
					String descrizione = request.getParameter("descrizione");
					String copertina = request.getParameter("copertina");
					int prezzo = Integer.parseInt(request.getParameter("prezzo"));
					int quantitaVestito = Integer.parseInt(request.getParameter("quantitaVestito"));
					
					ShopBean bean = new ShopBean();
					bean.setCodiceVestito(id);
					bean.setIdCategoria(idCategoria);
					bean.setQuantitaVestito(quantitaVestito);
					bean.setTitolo(titolo);
					bean.setDescrizione(descrizione);
					bean.setPrezzo(prezzo);
					bean.setCopertina(copertina);
					
					model.doUpdate(bean);
					request.setAttribute("message", "Product " + bean.getTitolo() + " updated");
				} else if (action.equals("insert")) {
					String codiceVestito = request.getParameter("codiceVestito");
					String idCategoria = request.getParameter("idcategoria");
					String titolo = request.getParameter("titolo");
					String descrizione = request.getParameter("descrizione");
					String copertina = request.getParameter("copertina");
					int prezzo = Integer.parseInt(request.getParameter("prezzo"));
					int quantitaVestito = Integer.parseInt(request.getParameter("quantitaVestito"));
					
					ShopBean bean = new ShopBean();
					bean.setCodiceVestito(codiceVestito);
					bean.setIdCategoria(idCategoria);
					bean.setQuantitaVestito(quantitaVestito);
					bean.setTitolo(titolo);
					bean.setDescrizione(descrizione);
					bean.setPrezzo(prezzo);
					bean.setCopertina(copertina);
					
					model.doSave(bean);
					request.setAttribute("message", "Product " + bean.getTitolo() + " added");
				
				}
			}
			
		} catch (Exception e) {
			Utility.print(e);
			request.setAttribute("error", e.getMessage());
		}
		
		
		
		try {
			request.removeAttribute("products");
			request.setAttribute("products", model.doRetrieveAll(sort));
			
		} catch(SQLException e) {
			Utility.print(e);
			
			request.setAttribute("error", e.getMessage());
		}
		
		getServletContext().getRequestDispatcher("/adminProdotti.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
