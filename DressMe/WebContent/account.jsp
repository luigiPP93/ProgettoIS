<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,gestioneProdotti.*"%>
    

 
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
	
	ShopBean product = (ShopBean)request.getAttribute("product");
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
		<tr> 
			<th>Nome</th>
			<th>Descrizione</th>
			<th>Prezzo</th>
			<th>Immagine</th>
		</tr>		
		
		<%if(request.getSession().getAttribute("email")!=null) {
			
			Iterator<?> it = products.iterator();
			while(it.hasNext()) {
				ShopBean prod = (ShopBean)it.next();
                   
                %>
                <form  method="get">
       
        <tr>
            <td> <%=prod.getTitolo() %> </td>
            <td> <%=prod.getDescrizione()%></td>
            <td> <%=prod.getPrezzo() %> &euro; </td>
            <input type="text" name="id" value=<%=prod.getCodiceVestito()%> style="display: none;">
           
            <td><img src="<%= prod.getCopertina()%>" onerror="this.src='./image/noimage.png'" width=75px> </td>
           
        </tr>
       

        <%
            }                       
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
<!-- ------------------------------------ Footer ----------------------------------------  -->

<%@ include file ="footer.jsp" %>

</body>
</html>