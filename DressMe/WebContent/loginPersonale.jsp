<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


    <!DOCTYPE html>
<html lang="en">
<head>
    <title>Account | DressMe Cloth collection</title>
    <link rel="shortcut icon" type="image/x-icon" href="images/Logo/logo-sito.gif">
    <link rel="stylesheet" href="style.css">
   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
   
   
	
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

    

<!-- ------------------------------------ Accesso ----------------------------------------  -->

    <section class="container-account">
<div class="login-box">
	<h2 id="login-message"></h2>
  	<h2>Login Personale</h2>
  
  <form  action="<%=response.encodeURL("LoginPersonaleControl")%>" method="POST">
    <div class="user-box">
      <input type="text" id="username" name="username" required="">
      <label>Email</label>
    </div>
    <div class="user-box">
      <input type="password" id="password" name="password"  required="">
      <label>Password</label>
    </div>
    <input class="button" type="submit" id="login-submit" value="Accedi">
      <!-- <a class="c" href="registrazione2.jsp">hai dimenticato la password? clicca qui</a>   -->  
                
     <h3> In caso di problemi contattare l'amministratore</h3>
   
  </form>
</div>

    </section>

<!-- ------------------------------------ Footer ----------------------------------------  -->

<%@ include file ="/footer.jsp" %> 

</body>

</html>