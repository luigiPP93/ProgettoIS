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
	
   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
   
	<script>
	
	function trySignIn(){
		const password_regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{7,}$/;

		if($("#vecchiaPassword").val().match(password_regex)) {
			if($("#nuovaPassword").val().match(password_regex)){
				if($("#confermaPassword").val().match(password_regex)){
			$.ajax(
				{
					method: "POST",
					url: "AccountCambiaDatiControl",
					data: {
						vecchiaPassword: $("#vecchiaPassword").val(),
						nuovaPassword: $("#nuovaPassword").val(),
						confermaPassword: $("#confermaPassword").val(),
						
					},
					success: (data) => {
						window.location.replace("http://localhost:8080/DressMe/successoCambioPassword.jsp");
					},
					error: (data) => {
						$("#signin-message").html("OPS!! vecchia password non corretta oppure nuova password e conferma password non coincidono");
						$("#signin-message").css("color", "red");
					}
				}
			);
		} else {
			$("#signin-message").css("color", "red");
			$("#signin-message").html(
				"OPS, La password di conferma non coincide.<br/>"
			);
			
		}
	}else {
		$("#signin-message").css("color", "red");
		$("#signin-message").html(
				"OPS, La password non rispetta i criteri.<br/>" +
				"Sono necessari almeno sette caratteri,<br/>" +
				"e almeno una lettera e un numero."
		);
		
	}
	}else {
		$("#signin-message").css("color", "red");
		$("#signin-message").html(
			"OPS, Formato vecchia password errato.<br/>"
		);
		
	}
}	
	
	</script>
	
</head>

<body>

<!-- ------------------------------------ Header ------------------------------------  -->

    <%@ include file ="header.jsp" %>

<!-- ------------------------------------ Accesso ----------------------------------------  -->

    <section class="container-account">
<div class="login-box">
	
  	<h2>Cambia Password</h2>
  <form name='registrazione' onsubmit="trySignIn(); return false" >
    <div class="user-box">
      <input type="password" id="vecchiaPassword" required="">
      <label>Vecchia Password</label>
    </div>
    <div class="user-box">
      <input type="password" id="nuovaPassword" required="">
      <label>Nuova Password</label>
    </div>
    <div class="user-box">
      <input type="password" id="confermaPassword" required="">
      <label>Conferma Password</label>
    </div>
    <div id="signin-message"></div>
    <input class="button" type="submit"  id="Signin-submit" value="Conferma">
  </form>
  
</div>

    </section>

<!-- ------------------------------------ Footer ----------------------------------------  -->

   <%@ include file ="/footer.jsp" %>

</body>

</html>