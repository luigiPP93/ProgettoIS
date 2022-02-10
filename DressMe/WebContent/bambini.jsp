<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,gestioneProdotti.*,gestioneCarrello.*" %>
    
<%
	Collection<?> bambiniArticolo = (Collection<?>)request.getAttribute("Bambini");

	String error = (String)request.getAttribute("error");
	
	if(bambiniArticolo == null && error == null){
		response.sendRedirect(response.encodeRedirectURL("./BambiniControl"));
		return;
	}
%> 
  
<!DOCTYPE html>
<html>

<head>
	<link href="style.css" rel="stylesheet" type="text/css">
	<link rel="shortcut icon" type="image/x-icon" href="image/Logo/logo-sito.gif">
	<title>Bambini| DressMe Cloth collection</title>
</head>

<body>

 <%@ include file ="/header.jsp" %>
    
   <section class="banner-bambino">
	    <div class="text-banner">
	      
	        <a class="button" href="#t-shirt"><b>Esplora ora BAMBINI</b></a>
	    </div> 
	</section>

	<section class="section-nav"> 
	    <h1>Collezione Bambino</h1>
	    <ul class="section-nav-item">
	        <li><a class="nav-item" href="#T-shirt">t-shirt</a></li>
	        <li><a class="nav-item" href="#Felpe">felpe</a></li>
	        <li><a class="nav-item" href="#Maglione">Maglioni</a></li>
	        <li><a class="nav-item" href="#Pantalone">Pantaloni</a></li>
	    </ul>
	</section>
<h2 id="t-shirt">T-Shirt</h2>
	<section class="vetrina">
	<%
		if(bambiniArticolo != null && bambiniArticolo.size() > 0){
		Iterator<?> it = bambiniArticolo.iterator();
		%>
		
		<%	
			while(it.hasNext()){
	%>
		
	<%
				
				ShopBean bean = (ShopBean)it.next();
	%>
		  <%if(bean.getQuantitaVestito()>0){ %>
		   	<div class="vetrina-item">
		   		<a href="prodotto.jsp?copertina=<%=bean.getCopertina()%>&titolo=<%= bean.getTitolo()%>&codice=<%=bean.getCodiceVestito()%>&descrizione=<%=bean.getDescrizione()%>&prezzo=<%=bean.getPrezzo() %>" > 
		    	<img src="<%= bean.getCopertina()%>" onerror="this.src='./image/noimage.png'" alt="user-1"></a>
		        <h4><%= bean.getTitolo()%></h4>
		        <p><%= bean.getDescrizione()%></p>
		        <p><b>&euro;<%= bean.getPrezzo()%></b></p>
		         <a class="button" href="<%=response.encodeURL("CarrelloControl?action=addCart&id="+bean.getCodiceVestito()) %>"  ><b>Aggiungi al carrello</b></a>
		      		
          	
		    </div>
		    <%}else if(bean.getQuantitaVestito()==0){ %>
		    <div class="vetrina-item">
		   		
		    	<img src="<%= bean.getCopertina()%>" onerror="this.src='./image/noimage.png'" alt="user-1"></a>
		        <h4><%= bean.getTitolo()%></h4>
		        <p><%= bean.getDescrizione()%></p>
		        <p><b>&euro;<%= bean.getPrezzo()%></b></p>
		         <p style="color:red;">Prodotto Esaurito</p>
		    </div>
		    
		    <%} %>
				<%
			}
				%>
		
	 	<%
		}
		%>
	</section>	
	
	    <%@ include file ="/footer.jsp" %>
	   

</body>

</html>