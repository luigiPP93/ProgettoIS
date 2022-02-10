<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,gestioneProdotti.*"%>


    <!DOCTYPE html>
<html lang="en">
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

<!-- ------------------------------------ Header ------------------------------------  -->

    <%@ include file ="header.jsp" %>

<!-- ------------------------------------ Accesso ----------------------------------------  -->
 <section class="section product-detail">
    <div class="details container-md">
      <div class="left">
        <div class="main">
        <%String strId = request.getParameter("copertina"); %>
          <img src="./<%=strId %>" alt="./<%=request.getAttribute("copertina") %>">
        </div>
       
      </div>
      <div class="right">
        <span>Home/T-shirt</span>
        <%String strTi = request.getParameter("titolo"); %>
        <h1><%=strTi %></h1>
        <%
        String strPr = request.getParameter("prezzo");
        Integer intero = Integer.valueOf(strPr);
        %>
        <div class="price"><%=intero%> &euro;</div>
        <form>
          <div>
          </div>
        </form>
<%String strCo = request.getParameter("codice"); %>
        <form class="form">
          <a href="<%=response.encodeURL("CarrelloControl?action=addCart&id="+strCo) %>"  class="addCart">Aggiungi al carello</a>
         
        </form>
        <h3>Dettagli Prodotto</h3>
         <%String strDe = request.getParameter("descrizione"); %>
        <p><%=strDe %>!</p>
          <h2>SPEDIZIONE VELOCE IN MENO DI 5 GIORNI IN TUTTA ITALIA</h2>
      </div>
    </div>
  </section>

  <!-- Related -->
    
<!-- ------------------------------------ Footer ----------------------------------------  -->

   <%@ include file ="/footer.jsp" %>

</body>

</html>