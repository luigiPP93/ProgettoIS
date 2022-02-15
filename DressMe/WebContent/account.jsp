<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,gestioneProdotti.*,gestioneAcquisti.*,java.sql.*,javax.sql.DataSource"%>
    

 
<!DOCTYPE html>
<html>
<head>
<title>Account | DressMe Cloth collection</title>
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
</head>
<body>

<%
    Collection<?> products = (Collection<?>)request.getAttribute("products");

	String error = (String)request.getAttribute("error");
	 
	if(products == null && error == null) {
		response.sendRedirect(response.encodeRedirectURL("./AccountControl"));
		return;
	}
	
	OrdineBean product = (OrdineBean)request.getAttribute("product");
%>

<!-- ------------------------------------ Header ------------------------------------  -->



<%@ include file ="header.jsp" %>


<%

%>


<!-- ----------------------------------------- Account -------------------------------- -->
<section class="carrello">
		
	<table class="container">
	<br>
	<%
	String tmp = (String) request.getSession().getAttribute("nome");%>
	<%if(tmp==null){%>
	<div class="container">
		<a class="button" href="index.jsp"><b>Scegli degli articoli</b></a>
		<a class="button" href="login.jsp"><b>Clicca qui per accedere</b></a>
	</div>
		
	<%} else{%>
	 
	<h2 class ="ute"> <%=tmp%> questo è il tuo account</h2>
	<a class="button" href="cambiaPassword.jsp"><b>Vuoi cambiare la password? Clicca qua</b></a> 
	<%} %>
	<h2>Qui potrai trovare i prodotti che hai acquistato</h2>
		
		
		<%if(request.getSession().getAttribute("email")!=null) {
			
			Iterator<?> it = products.iterator();
			while(it.hasNext()) {
				OrdineBean prod = (OrdineBean)it.next();
                   
                %>
                <form  method="get">
       
        
        	<tr>
				<th class="colonne2">Numero Ordine</th>
				<th class="colonne2">Indirizzo</th>
				<th class="colonne2">Prezzo totale</th>
			</tr>	
			
        <tr>
         	<td> <%=prod.getNumeroOrdine() %> </td>
			<td> <%=prod.getIndirizzo() %></td>
			<td> <%=prod.getPrezzo() %></td>
        </tr>
       
       <tr >
<th >Codice Prodotto</th>
<th >Nome Prodotto</th>
<th >Descrizione</th>
<th >Copertina</th>
</tr>
<%String codiciProdotti=prod.getProdotti();
DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
ShopModelDS model = new ShopModelDS(ds);
for(int i=0; i<codiciProdotti.length();i=i+5 ){
String codice=codiciProdotti.substring(i,i+5);
ShopBean p =model.doRetrieveByKey(codice);
%>
<tr class="colonne">
<th class="colonne"></th>
<th class="colonne"></th>
<th class="colonne"></th>
<th class="colonne"></th>
</tr>
<td> <%=p.getCodiceVestito() %></td>
<td> <%=p.getTitolo() %> </td>
<td> <%=p.getDescrizione() %> </td>
<td><img src="<%= p.getCopertina()%>" onerror="this.src='./image/noimage.png'" width=75px> </td>
<% }
%>

</tr>

        <%
            }                       
			} else {			
		%>
		
		<tr>
			<td colspan="5">Non hai ancora effetuato un ordine</td>
		</tr>
		
		<%
			}
		%>
		
	</table>
      
</section>
<!-- ------------------------------------ Footer ----------------------------------------  -->

<%@ include file ="footer.jsp" %>

</body>
</html>