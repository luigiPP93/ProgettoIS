package gestioneCarrello;

import java.io.IOException;

import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import gestioneProdotti.*;
import gestioneAcquisti.*;
import it.unisa.utils.Utility;

@WebServlet("/CarrelloControl")
public class CarrelloControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
		ShopModelDS model=new ShopModelDS(ds);
		GestioneCarrelloModelDS model2=new GestioneCarrelloModelDS (ds);
		OrdineModelDS modelPagamento = new OrdineModelDS(ds);
		
		HttpSession session = request.getSession();
		String s= (String) session.getAttribute("nome");
		session.setAttribute("nome", s);
		
		String email = (String) session.getAttribute("email");
		
		@SuppressWarnings("unchecked")
		Carts<ShopBean> cart = (Carts<ShopBean>) request.getSession().getAttribute("carrello");
		if(cart == null) {
			cart = new Carts<ShopBean>();
			request.getSession().setAttribute("carrello", cart);
		}
		
		String sort = request.getParameter("sort");
		String action = request.getParameter("action");
		String azione= request.getParameter("azione");
		
	
		if(request.getSession().getAttribute("email")!=null) {
			try {
	
				if(session.getAttribute("Cliente").equals("1")) {
					session.setAttribute("Cliente", "0");
				
				List<ShopBean> prodcart2 = cart.getItems();
				 SessionCarrelloBean addCart2 = new SessionCarrelloBean();
					if(prodcart2.size()>0) {
						for(ShopBean prod: prodcart2) {								
                  addCart2.setIdemail(email);
                  addCart2.setCodiceVestito(prod.getCodiceVestito());
                  model2.doSave(addCart2);
						}
					}
				}
				
				cart = model2.TrovaCarrello(email);
				System.out.println("trova carrello eseguita");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		
		
		try {
			if(azione !=null) {
				if ( azione.equals("pagamentoCart")) {
				
					List<ShopBean> prodcart = cart.getItems();
					request.getSession().setAttribute("pagamento", azione);
						try {
							 SessionCarrelloBean addCart = new SessionCarrelloBean();
							if(prodcart.size()>0) {
								for(ShopBean prod: prodcart) {								
		                     addCart.setIdemail(email);
		                     addCart.setCodiceVestito(prod.getCodiceVestito());
	                         model2.doSave2(addCart);
								}
							OrdineBean b = new OrdineBean();
							int prezzo = (int) request.getSession().getAttribute("prezzo");
							 String nomeS = (String) session.getAttribute("nomeS");
							 String cognomeS=(String) session.getAttribute("cognomeS");
							 String indirizzoS=(String)  session.getAttribute("indirizzoS");
							 String capS=(String)  session.getAttribute("capS");
							 String comuneS=(String)  session.getAttribute("comuneS");
							 String provinciaS=(String)  session.getAttribute("provinciaS");
							
							 System.out.println(" param "+ prezzo);
							 System.out.println(" param "+ nomeS);
							 System.out.println(" param "+ cognomeS);
							 System.out.println(" param "+ indirizzoS);
							 System.out.println(" param "+ capS);
							 System.out.println(" param "+ comuneS);
							 System.out.println(" param "+ provinciaS);
							b.setNome(nomeS);
							b.setCap(capS);
							b.setComune(comuneS);
							b.setCognome(cognomeS);
							b.setIndirizzo(indirizzoS);
							b.setPrezzo(prezzo+"");
							b.setProvincia(provinciaS);
							b.setEmail(email);
							b.setControllato("false");
							String prodotti="";
							for(ShopBean prod: prodcart) {
								prodotti=prodotti+prod.getCodiceVestito();
								}
								System.out.println("Tutti i codici dei prodotti"+prodotti);
							b.setProdotti(prodotti);
							modelPagamento.SalvaOrdine(b);
							b.toString();
							}

	                     } catch (SQLException e) {
	                         // TODO Auto-generated catch block
	                         
	                         e.printStackTrace();
	                     }		
	             
					}
			}
			
			if(action != null) {
				if ( action.equals("clearCart")) {
					cart.deleteItems();
					request.setAttribute("message", "Cart cleaned");
					
					try {
						
						model2.deleteAll(email);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						getServletContext().getRequestDispatcher("/logout.jsp").forward(request, response);
						e.printStackTrace();
					}
					
					
					
					
				}
				else if ( action.equals("UpdateCart")) {
					cart.deleteItems();
					request.setAttribute("message", "Cart cleaned");
					
					try {
						
						model2.deleteAll(email);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						getServletContext().getRequestDispatcher("/logout.jsp").forward(request, response);
						e.printStackTrace();
					}
				}
				
				
				else if ( action.equals("addCart")) {
					
					request.getSession().setAttribute("acc", null);
					String id = request.getParameter("id");
					
					ShopBean bean = model.doRetrieveByKey(id);
					session.setAttribute("id", id);
					int c3= bean.getQuantitaVestito();
					if(bean!=null && !bean.isEmpty()) {
						if(c3==0) {
							System.out.println("finito2");
						}
						
						cart.addItem(bean);
						String cont = (String) session.getAttribute("conto");
						Integer c = Integer.valueOf(cont);
						c=c+1;
						String str = String.valueOf(c);
						session.setAttribute("conto", str);
						request.setAttribute("message", "Product "+bean.getTitolo()+" added to cart");
						
						String idVestito = (String) session.getAttribute("id");
                        
                        SessionCarrelloBean addCart = new SessionCarrelloBean();
                        addCart.setIdemail(email);
                        addCart.setCodiceVestito(idVestito);

                        try {
                            model2.doSave(addCart);
                            
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            
                            e.printStackTrace();
                        }
						
					}
				} 
				
				
				
				else if ( action.equals("deleteCart")) {
					String id = request.getParameter("id");
					ShopBean bean = model.doRetrieveByKey(id);
					if(bean!=null && !bean.isEmpty()) {
						cart.deleteItem(bean);
						
						String cont = (String) session.getAttribute("conto");
						Integer c = Integer.valueOf(cont);
						c=c-1;
						String str = String.valueOf(c);
						session.setAttribute("conto", str);
								
						SessionCarrelloBean addCart = new SessionCarrelloBean();
						addCart.setIdemail(email);
						addCart.setCodiceVestito(id);

						try {
							model2.doDelete(addCart);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							getServletContext().getRequestDispatcher("/logout.jsp").forward(request, response);
							e.printStackTrace();
						}
						
						request.setAttribute("message", "Product "+bean.getTitolo()+" Deleted from cart");
					}	
				}
				
			
			}
			
		} catch (Exception e) {
			Utility.print(e);
			request.setAttribute("error", e.getMessage());
		}
		
		request.setAttribute("cart", cart);
		
		try {
			request.removeAttribute("products");
			request.setAttribute("products", model.doRetrieveAll(sort));
			
		} catch(SQLException e) {
			Utility.print(e);
			
			request.setAttribute("error", e.getMessage());
		}
		
		
		 response.setContentType("text/html");
		
		 
		 
		 getServletContext().getRequestDispatcher("/carrello.jsp").forward(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
