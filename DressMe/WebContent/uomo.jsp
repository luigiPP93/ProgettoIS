<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,gestioneProdotti.*" %>
    
<%
	Collection<?> uomoArticolo = (Collection<?>)request.getAttribute("Uomo");

	String error = (String)request.getAttribute("error");
	
	if(uomoArticolo == null && error == null){
		response.sendRedirect(response.encodeRedirectURL("./UomoControl"));
		return;
	}
	
	
%> 
  
<!DOCTYPE html>
<html>

<head>
	<link href="style.css" rel="stylesheet" type="text/css">
	<link href="styleNavBar.css" rel="stylesheet" type="text/css">
	<link rel="shortcut icon" type="image/x-icon" href="image/Logo/logo-sito.gif">
	<title>uomo| DressMe Cloth collection</title>
	
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

<%@ include file ="/header.jsp" %>
    
    <section class="banner-uomo">
	    <div class="text-banner">
	        <a class="button" href="#t-shirt"><b>Esplora ora UOMO</b></a>
	    </div> 
	</section>

	<section class="section-nav"> 
    <h1>Collezione Uomo</h1>
	    <ul class="section-nav-item">
	        <li><a class="nav-item" href="#t-shirt">T-shirt</a></li>
	        <li><a class="nav-item" href="#felpe">Felpe</a></li>
	        <li><a class="nav-item" href="#maglione">Maglioni</a></li>
	        <li><a class="nav-item" href="#pantalone">Pantaloni</a></li>
	    </ul>
	</section>
<h2 id="t-shirt">T-Shirt</h2>
	<section class="vetrina">
	
	<%
		if(uomoArticolo != null && uomoArticolo.size() > 0){
		Iterator<?> it = uomoArticolo.iterator();
		%>
		
		<%	
			while(it.hasNext()){
	%>
		
	<%
				//for(int i=0;it.hasNext();i++){
				ShopBean bean = (ShopBean)it.next();
				
	%>
			<%if(bean.getQuantitaVestito()>0){ %>
		   	<div class="vetrina-item">
		   		<a href="prodotto.jsp?copertina=<%=bean.getCopertina()%>&titolo=<%= bean.getTitolo()%>&codice=<%=bean.getCodiceVestito()%>&descrizione=<%=bean.getDescrizione()%>&prezzo=<%=bean.getPrezzo()%>" > 
		    	<img src="<%= bean.getCopertina()%>" onerror="this.src='./image/noimage.png'" alt="user-1"></a>
		        <h4><%= bean.getTitolo()%></h4>
		        <p><%= bean.getDescrizione()%></p>
		        <p><b>&euro;<%= bean.getPrezzo()%></b></p>
		         <a class="button" href="<%=response.encodeURL("CarrelloControl?action=addCart&id="+bean.getCodiceVestito()) %>"  ><b>Aggiungi al carrello</b></a>
		      		
          	
		    </div>
		    <%}else if(bean.getQuantitaVestito()==0){ %>
		    <div class="vetrina-item">
		   		
		    	<img src="<%= bean.getCopertina()%>" onerror="this.src='./image/noimage.png'" alt="user-1">
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
	
	   <%@ include file ="footer.jsp" %>
	   

</body>

</html>