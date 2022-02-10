<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"   import="java.util.*,gestioneProdotti.*,gestioneCarrello.*"%>
    
 <% 
	Collection<?> products = (Collection<?>)request.getAttribute("products");

	String error = (String)request.getAttribute("error");
	
	if(products == null && error == null) {
		response.sendRedirect(response.encodeRedirectURL("./CarrelloFineControl"));
		return;
	}
	
	Carts<ShopBean> cart = (Carts<ShopBean>) request.getAttribute("cart");
	
	if(cart==null){
		response.sendRedirect(response.encodeRedirectURL("./CarrelloFineControl"));
		return;
	}
	
	ShopBean product = (ShopBean) request.getAttribute("product");
%>

<% String loginNome = (String) request.getAttribute("login");%>
 
<!DOCTYPE html>
<html>
<head>
<title>Carrello | DressMe Cloth collection</title>
    <link rel="shortcut icon" type="image/x-icon" href="images/Logo/logo-sito.gif">
    <link rel="stylesheet" href="style.css">
    
<meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="pragma" content="no-cache" />
	
	<%
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Cache-Control","no-store");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader ("Expires", 0);
	%>
	
		<script type="text/javascript">
		if (window.performance && window.performance.navigation.type === window.performance.navigation.TYPE_BACK_FORWARD) {
			window.location.replace("https://localhost:8443/DressMe/index.jsp");
		}
    </script>
   
	
	
</head>
<body>



<!-- ------------------------------------ Header ------------------------------------  -->



<%@ include file ="header.jsp" %>


<%
	List<ShopBean> prodcart = cart.getItems();
	request.getSession().setAttribute("cute", prodcart);
%>


<!-- ----------------------------------------- CARRELLO -------------------------------- -->
<section class="carrello">
		
	<table class="container">
	<br>
	<%String az = (String) request.getParameter("azione");
	if(az!=null){%>
		<h1 style="color:green;">Ordine effettuato</h1>
	<%} %>
	<% String tmp = (String) request.getSession().getAttribute("nome");%>
	<%if(tmp==null)
		{
	%>
	<div class="container">
		<a class="button" href="index.jsp"><b>Torna All'home</b></a>
		<a class="button" href="login.jsp"><b>Clicca qui per accedere</b></a>
	</div>
		
	<%} else{%>
	
	<h2 class ="ute"> <%=tmp%> questo è il tuo carrello</h2>
	<%} %>
	<h2>Prodotti nel Carrello</h2>
		<tr> 
			<th>Nome</th>
			<th>Descrizione</th>
			<th>Prezzo</th>
			<th></th>
			<th>Action </th>
		</tr>
		
		<%int prezzo = 0; %>
		<% if(prodcart.size()>0) {
				for(ShopBean prod: prodcart){
					
		%>
		
		<form action="<%= request.getContextPath()%>/SpedizioneControl" method="get">
		
		<tr>
			<td> <%=prod.getTitolo() %> </td>
			<td> <%=prod.getDescrizione()%></td>
			<td> <%=prod.getPrezzo() %> </td>
			 <% prezzo = prezzo + prod.getPrezzo(); 
            System.out.println("questo  il prezzo");%>
			<input type="text" name="id" value=<%=prod.getCodiceVestito()%> style="display: none;">
			
			
			<td><img src="<%= prod.getCopertina()%>" onerror="this.src='./image/noimage.png'" width=75px> </td>
			<td> <a href="<%=response.encodeURL("CarrelloControl?action=deleteCart&id="+prod.getCodiceVestito()) %>">Rimuovi</a> </td>
		</tr>
		
		
		
		<% 		}
			} else {			
		%>
		
		<tr>
			<td colspan="5">Non ci sono prodotti nel carrello</td>
		</tr>
		
		<%
			}
		%>
		
	</table>
      
</section>

<!-- -----------------------------------------Acquista -------------------------------- -->
<%
	if(prodcart.size()>0) {
%>	
		<%if(prezzo < 25){%>
		<div >
      <table class="container">
        <tr>
          <td>Prodotti</td>
          <td><%=prezzo%> &euro;</td>
        </tr>
        <tr>
            <td>Spedizione</td>
          <td>5&euro;</td>
        </tr>
        <tr>
          <td>Totale</td>
          <td><%=prezzo+5%></td>
          
        </tr>
      </table>
      <%} else if(prezzo>=25){%>
      <div >
      <table class="container">
        <tr>
          <td>Prodotti</td>
          <td><%=prezzo%> &euro;</td>
        </tr>
        <tr>
            <td>Spedizione</td>
          <td>0&euro;</td>
        </tr>
        <tr>
          <td>Totale</td>
          <td><%=prezzo%></td>
          
        </tr>
      </table>
      <%} %>
    <%request.getSession().setAttribute("prezzo",prezzo); %>
		  <a class="button" href="spedizione.jsp"><b>Procedi all'ordine</b></a>
	

    </div>
	<div class="container">
	 <a class="button" href="<%=response.encodeURL("CarrelloControl?action=clearCart") %>"><b>Svuota Carrello</b></a>
	<a class="button" href="index.jsp"><b>Scegli degli altri articoli</b></a>
	</div>
	</form>
	
    
<%} %>

	<% if(prodcart.size()<=0){%>
		<div class="container">
		<a class="button" href="index.jsp"><b>Scegli degli articoli</b></a>
	</div>
	<%}%>
	
<!-- ------------------------------------ Footer ----------------------------------------  -->

<%@ include file ="footer.jsp" %>

</body>
</html>