<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

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


    <section class="container-account">
<div class="login-box">
 <h2>OPS!!È stato commesso un errore</h2>
     <h2>Email o Password errata</h2>
  <h2>Login</h2>
  <form action="<%=request.getContextPath()%>/LoginControl?action=bentornato" method="post">
    <div class="user-box">
      <input type="text" name="email" required="">
      <label>Email</label>
    </div>
    <div class="user-box">
      <input type="password" name="password" required="">
      <label>Password</label>
    </div>
    <input class="button" type="submit"  value="Accedi">
                
     <a class="button" href="registrazione.jsp"><b>Non sei ancora registrato?Registrati</b></a>  
  </form>
</div>

    </section>
<!-- ------------------------------------ Footer ----------------------------------------  -->

   <%@ include file ="footer.jsp" %>

</body>

</html>