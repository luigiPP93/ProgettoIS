 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*"%>


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

<% String tmp = null;
if(session.getAttribute("nome") != null){
	tmp = (String) session.getAttribute("nome");
}%>

	<%String password =null;
	  String email=null;
	  password = (String) session.getAttribute("nome");
	  email = (String) session.getAttribute("nome");
	  
	  if(password == "AdminAdmin" && email =="admin@gmail.com"){%>
	  <div class="container"> 
                <h1 class ="ute">Account: <%=tmp%> </h1>
               <h2>REGISTAZIONE COMPLETATA</h2>
               <a class="button" href="index.jsp"><b>Scegli degli altri articoli</b></a>
               <a class="button" href="amministrazione.jsp"><b>Vai all'amministrazione</b></a>
            </div>
	  <%} else{ %>
	  
<div class="container"> 
                <h1 class ="ute">Account: <%=tmp%> </h1>
               <h2>ORDINE EFFETTUATO CON SUCCESSO</h2>
               <a class="button" href="index.jsp"><b>Scegli degli altri articoli</b></a>
            </div>
            <%} %>
<!-- ------------------------------------ Footer ----------------------------------------  -->

   <%@ include file ="footer.jsp" %>

</body>

</html>