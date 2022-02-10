<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


    <!DOCTYPE html>
<html lang="en">
<head>
    <title>Account | DressMe Cloth collection</title>
    <link rel="shortcut icon" type="image/x-icon" href="images/Logo/logo-sito.gif">
    <link rel="stylesheet" href="style.css">
   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
   
   
	<script>
	function tryLogin(){
		$.ajax(
			{
				method: "POST",
				url: "LoginControl",
				data: {
					username: $("#login-username").val(),
					password: $("#login-password").val(),
				},
				success: (data) => {
					window.location.replace("http://localhost:8080/DressMe/index.jsp");
				},
				error: (data) => {
					$("#login-message").html("OPS!!È stato commesso un errore credenziali errate o utente non registrato");
					$("#login-message").css("color", "red");
				}	
			}
		);
	}
	</script>
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
	<h2 id="login-message"></h2>
  	<h2>Login</h2>
  
  <form  onsubmit="tryLogin(); return false">
    <div class="user-box">
      <input type="text" id="login-username"  required="">
      <label>Email</label>
    </div>
    <div class="user-box">
      <input type="password" id="login-password"  required="">
      <label>Password</label>
      
    </div>
    <input class="button" type="submit" id="login-submit" value="Accedi">
      <!-- <a class="c" href="registrazione2.jsp">hai dimenticato la password? clicca qui</a>   -->  
                
     <a class="button" href="registrazione.jsp"><b>Non sei ancora registrato?Registrati</b></a>
   
  </form>
</div>

    </section>

<!-- ------------------------------------ Footer ----------------------------------------  -->

   <%@ include file ="/footer.jsp" %>

</body>

</html>