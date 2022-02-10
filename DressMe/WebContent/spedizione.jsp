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
	function trySpedizione(){
		const cap = /^[0-9]{5,5}/;
		if($("#capS").val().match(cap)) {
		$.ajax(
			{
				method: "POST",
				url: "SpedizioneControl",
				data: {
					nome: $("#nomeS").val(),
					cognome: $("#cognomeS").val(),
					indirizzo: $("#indirizzoS").val(),
					cap: $("#capS").val(),
					comune: $("#comuneS").val(),
					provincia: $("#provinciaS").val(),
					
				},
				success: (data) => {
					window.location.replace("http://localhost:8080/DressMe/pagamento.jsp");
				},
				error: (data) => {
					$("#spedizione-message").html("OPS!!È stato commesso un errore credenziali errate");
					$("#spedizione-message").css("color", "red");
				}	
			}
		);
	} else {
		$("#spedizione-message").css("color", "red");
		$("#spedizione-message").html(
			"OPS, Formato cap non valido.<br/>"
		);
	}
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
<%
	if (session.getAttribute("nome") == null) {
	%>

	<%@ include file="header.jsp"%>
	<h2>Prima di procedere effettuare l'accesso o la registrazione</h2>


	<a class="button" href="login.jsp"><b>Login</b></a>
	<a class="button" href="registrazione.jsp"><b>Non sei ancora
			utente, registrati</b></a>

	<%@ include file="footer.jsp"%>
	<%
	} else {
	%>
<!-- ------------------------------------ Header ------------------------------------  -->

    <%@ include file ="header.jsp" %>

<!-- ------------------------------------ Accesso ----------------------------------------  -->

    <section class="container-account">
<div class="login-box">
	<h2 id="spedizione-message"></h2>
  	<h2>Spedizione</h2>
  
  <form  onsubmit="trySpedizione(); return false">
    <div class="user-box">
      <input type="text" id="nomeS"  required="">
      <label>Nome</label>
    </div>
    <div class="user-box">
      <input type="text" id="cognomeS"  required="">
      <label>Cognome</label>
    </div>
    <div class="user-box">
      <input type="text" id="indirizzoS"  required="">
      <label>Indirizzo</label>
    </div>
    <div class="user-box">
      <input type="text" id="capS"  required="" maxlength="5">
      <label>CAP</label>
    </div>
    <div class="user-box">
      <input type="text" id="comuneS"  required="">
      <label>Comune</label>
    </div>
    <div class="user-box">
      <input type="text" id="provinciaS"  required="">
      <label>Provincia</label>
    </div>
    <input class="button" type="submit" id="login-submit" value="procedi">
      <!-- <a class="c" href="registrazione2.jsp">hai dimenticato la password? clicca qui</a>   -->  
                
     <a class="button" href="registrazione.jsp"><b>Non sei ancora registrato?Registrati</b></a>
   
  </form>
</div>

    </section>

<!-- ------------------------------------ Footer ----------------------------------------  -->

   <%@ include file ="/footer.jsp" %>
	<%
	}
	%>
</body>

</html>