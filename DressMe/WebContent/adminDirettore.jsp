<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,gestioneAccount.*,gestioneProdotti.*,it.unisa.utils.PasswordHasher"%>
    
    <%
    Collection<?> products = (Collection<?>)request.getAttribute("products");

	String error = (String)request.getAttribute("error");
	 
	if(products == null && error == null) {
		response.sendRedirect(response.encodeRedirectURL("./AdminDirettore"));
		return;
	}
	
	PersonaleBean product = (PersonaleBean)request.getAttribute("product");
%>


<!DOCTYPE html>
<html>
<head>
<!-- Box icons -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css" />

  <!-- Custom StyleSheet -->
  <link rel="stylesheet" href="styleNavBar.css" />
 	<link href="style.css" rel="stylesheet" type="text/css">
 	<link rel="shortcut icon" type="image/x-icon" href="image/Logo/logo-sito.gif">
    <title>Admin | DressMe Cloth collection</title>
		
		<script>
	function validazioneEmail(email,password) 
{
    // recupero il valore della email indicata nel form
    var email=document.forms['InserimentoPersonale'].email.value;
    var password=document.forms['InserimentoPersonale'].password.value;
    
    if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)){
    	 alert("L'indirizzo email che hai inserito non è valido");
    	 return false;
    }
    
    if(!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{7,}$/.test(password)){
    	alert("La password che hai inserito non è valida");
   	 return false;
    }
       
}	
	function validazioneModificaEmail(password) 
	{
	    // recupero il valore della email indicata nel form
	    var password=document.forms['ModificaPersonale'].password.value;
	    
	    if(!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{7,}$/.test(password)){
	    	alert("La password che hai inserito non è valida");
	   	 return false;
	    }
	       
	}	
	</script>
</head>

<body>

<%

String r=(String)request.getSession().getAttribute("RUOLO");

System.out.println("Ruolo scritto"+r);
if(r==null){
	response.sendRedirect("http://localhost:8080/DressMe/loginPersonale.jsp");
}
else if(r.equals("Direttore")){ %>
<!-- CATALOGO -->
<br>
<table class="container">
<a class="Opzioni-effect" href="<%=response.encodeURL("LogoutControl") %>">Logout</a>
<h2>Elenco Personale</h2>
	<tr>
		<th>Nome</th>
		<th>cognome</th>
		<th>Email</th>
		<th>Ruolo</th>
		<th>Action</th>
	</tr>
	<%
		if(products != null && products.size() > 0) {
	
			Iterator<?> it = products.iterator();
			while(it.hasNext()) {
				PersonaleBean bean = (PersonaleBean)it.next();
	%>
			<tr>
				<td><%=bean.getNome() %> </td>
				<td><%=bean.getCognome() %>	</td>
				<td><%=bean.getEmail() %> </td>
				<td><%=bean.getRuolo() %></td>
				
				<td>
					<a href="<%=response.encodeURL("AdminDirettore?action=details&id="+bean.getEmail()) %>">Dettagli</a>
					<a href="<%=response.encodeURL("AdminDirettore?action=delete&id="+bean.getEmail()) %>">Elimina</a>
				</td>
			</tr>
	<% 
			}
		} else {
	%>
			<tr>
				<td colspan="4">No product available</td>
			</tr>
	<% } %>
</table>
<br/>

<%
	if(product != null ){
%>
	<!-- DETTAGLI Personale -->
	<h2>Dettagli Personale</h2>
	<table class="container">
		<tr>
		<th>Nome</th>
		<th>Cognome</th>
		<th>Email</th>
		<th>Ruolo</th>
		<%String pa=PasswordHasher.hash(product.getPassword());%>
		</tr>
		<tr>
		<td><%=product.getNome() %> </td>
		<td><%=product.getCognome() %>	</td>
		<td><%=product.getEmail() %> </td>
		<td><%=product.getRuolo() %></td>
			
		</tr>
	</table>
	
	<br/>
	
	<!-- FORM PER LA MODIFICA DEL PERSONALE -->
	
	<section class="container">
	
	<form class="container-form" name="ModificaPersonale" onsubmit="return validazioneModificaEmail()" action="<%=response.encodeURL("AdminDirettore")%>" method="POST">
			<legend><b>Modifica</b></legend>
			
			<input type="hidden" name="action" value="update">
			<input type="hidden" name="id" value="<%=product.getEmail()%>">
			
			<label for="nome">Nome:</label><br>
			<input id="nome" name="nome" type="text" maxlength="40" placeholder="inserisci nome" required value="<%=product.getNome()%>"><br>
			
			<label for="cognome">Cognome:</label><br>
			<input id="cognome" name="cognome" type="text" maxlength="40" placeholder="inserisci cognome" required value="<%=product.getCognome()%>"><br>
			
			<label for="password">Password:</label><br>
			<input id="password" name="password" type="text" maxlength="40" placeholder="inserisci password"  required><br>
			
			<label for="ruolo">Ruolo:</label><br>
			 <select name="ruolo" id="ruolo" type="text" maxlength="40" placeholder="inserisci ruolo" required value="<%=product.getRuolo()%>">
   			 	<option value="Gestore ordini">Gestore ordini</option>
   				 <option value="Gestore prodotti">Gestore prodotti</option>
   				 <option value="Direttore">Direttore</option>
 			 </select><br>
			
			<input type="submit" value="Update">
			<input type="reset" value="Reset">
	</form>
	</section>

<% } %>

<%
	String message = (String) request.getAttribute("message");
	if(message!=null && !message.equals("")) {
%>
	<p style="color: green;"><%=message %></p>
<%
	}
	
	if(error!=null && !error.equals("")){
			
%>
	<p style="color: red;">Error: <%=error %></p>
	
<% } %>
<br/>

<!-- FORM PER L'INSERIMENTO DEL PERSONALE -->
<section class="container">
	<form class="container-form" name="InserimentoPersonale" onsubmit="return validazioneEmail()"  action="<%=response.encodeURL("AdminDirettore")%>" method="POST">
	
			<legend><b>Inserimento</b></legend>
			<input type="hidden" name="action" value="insert">
			
			<label for="nome">Nome:</label><br>
			<input id="nome" name="nome" type="text" maxlength="40" placeholder="inserisci nome" required><br>
			
			<label for="cognome">Cognome:</label><br>
			<input id="cognome" name="cognome" type="text" maxlength="40" placeholder="inserisci cognome" required><br>
			
			<label for="email">Email:</label><br>
			<input id="email" name="email" type="text" maxlength="40" placeholder="inserisci email" required ><br>
			
			<label for="password">Password:</label><br>
			<input id="password" name="password" type="text" maxlength="40" placeholder="inserisci password" required><br>
			
			<label for="ruolo">Ruolo:</label><br>
			 <select name="ruolo" id="ruolo" type="text" maxlength="40" placeholder="inserisci ruolo" required>
   			 	<option value="Gestore ordini">Gestore ordini</option>
   				 <option value="Gestore prodotti">Gestore prodotti</option>
   				 <option value="Direttore">Direttore</option>
 			 </select><br>
		
			<input type="submit" value="Insert">
			<input type="reset" value="Reset">
		
	</form>
</section>
<%} else{%>
	<script> 
	alert("Pagina riservata al Direttore")
	</script>
	
<%} %>

<body>

</body>
</html>