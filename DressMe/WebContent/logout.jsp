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
		
		if(request.getSession().getAttribute("token")==null){
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}
	%>

</head>

<body>

<!-- ------------------------------------ Header ------------------------------------  -->

    <%@ include file ="header.jsp" %>

<!-- ------------------------------------ Accesso ----------------------------------------  -->
<% String tmp = null;
tmp= (String) session.getAttribute("nome");
if(session.getAttribute("nome") != null){
	session.invalidate();
}%>


	  <div class="container"> 
                <h1 class ="ute">Account: <%=tmp%> </h1>
               
               <h2>ACCOUNT DISCONNESSO</h2>
               <a class="button" href="index.jsp"><b>Scegli degli altri articoli</b></a>
               <a class="button" href="login2.jsp"><b>Accedi</b></a>
            </div>
    
<!-- ------------------------------------ Footer ----------------------------------------  -->

   <%@ include file ="footer.jsp" %>

</body>

</html>