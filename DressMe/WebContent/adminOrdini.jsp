<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,gestioneProdotti.*,gestioneAcquisti.*"%>
    
    <%
        Collection<?> products = (Collection<?>)request.getAttribute("products");

        	String error = (String)request.getAttribute("error");
        	 
        	if(products == null && error == null) {
        		response.sendRedirect(response.encodeRedirectURL("./AdminOrdini"));
        		return;
        	}
        	
        	OrdineBean product = (OrdineBean)request.getAttribute("product");
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
	function validazioneModificaOrdine() 
	{
		var cap=document.forms['ModificaOrdine'].cap.value;
		if(!/^[0-9]{5,5}/.test(cap)){
			    	alert("Il CAP che hai inserito non è valido");
			   	 return false;
		}
		
		var prezzo=document.forms['ModificaOrdine'].prezzo.value;
	    if(prezzo<=0){
	    	alert("Il prezzo che hai inserito non è valido");
	   	 return false;
	    }
	  
	}	          
	
	</script>
</head>

<body>

<%
String ad=(String)request.getSession().getAttribute("email");
String r=(String)request.getSession().getAttribute("RUOLO");
if(ad==null)
	ad="noAmm";
if(r==null){
	response.sendRedirect("http://localhost:8080/DressMe/loginPersonale.jsp");
}
else if( r.equals("Gestore ordini")){
%>
<!-- CATALOGO -->
<br>
<table class="container">
<a class="Opzioni-effect" href="<%=response.encodeURL("LogoutControl")%>">Logout</a>
<h2>Elenco Ordini</h2>
	<tr>
		<th>Numero Ordine</th>
		<th>E-mail</th>
		<th>Nome</th>
		<th>Cognome</th>
		<th>Comune</th>
		<th>Controllato</th>
		<th>Action</th>
	</tr>
	<%
	if(products != null && products.size() > 0) {
		
		Iterator<?> it = products.iterator();
		while(it.hasNext()) {
			OrdineBean bean = (OrdineBean)it.next();
	%>
			<tr>
				<td><%=bean.getNumeroOrdine() %> </td>
				<td><%=bean.getEmail() %></td>
				<td><%=bean.getNome() %></td>
				<td><%=bean.getCognome()%> </td>
				<td><%=bean.getComune()%> </td>
				<td><%=bean.getControllato()%> </td>
				
				<td>
					<a id="dettagli" href="<%=response.encodeURL("AdminOrdini?action=details&id="+bean.getNumeroOrdine()) %>">Dettagli</a>
					<a id="elimina" href="<%=response.encodeURL("AdminOrdini?action=delete&id="+bean.getNumeroOrdine()) %>">Elimina</a>
					<a id="conferma" href="<%=response.encodeURL("AdminOrdini?action=conferma&id="+bean.getNumeroOrdine()) %>">Conferma</a>
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
	if(product != null){
%>
	<!-- DETTAGLI Ordini -->
	<h2>Modifica Ordine</h2>
	<table class="container">
		<tr>
			<th>Numero Ordine</th>
			<th>E-mail</th>
			<th>Nome</th>
			<th>Cognome</th>
			<th>Comune</th>
			<th>Controllato</th>
			
		</tr>
		<tr>
			<td><%=product.getNumeroOrdine() %></td>
			<td><%=product.getEmail() %></td>
			<td><%=product.getNome() %></td>
			<td><%=product.getCognome() %></td>
			<td><%=product.getComune() %></td>
			<td><%=product.getControllato()%></td>
			
		</tr>
	</table>
	
	<br/>
	
	<!-- FORM PER LA MODIFICA DELL'ORDINE -->
	
	<section class="container">
	
	<form class="container-form" name="ModificaOrdine" onsubmit="return validazioneModificaOrdine()" action="<%=response.encodeURL("AdminOrdini")%>" method="POST">
			<legend><b>Modifica</b></legend>
			
			<input type="hidden" name="action" value="update">
			<input type="hidden" name="id" value="<%=product.getNumeroOrdine()%>">
			
			<label for="nome">Nome:</label><br>
			<input id="nome" name="nome" type="text" maxlength="10" placeholder="Inserisci nome" required value="<%=product.getNome()%>"><br>
			
			<label for="cognome">Cognome:</label><br>
			<input id="cognome" name="cognome" type="text" maxlength="10" placeholder="Inserisci cognome"required value="<%=product.getCognome()%>"><br>
			
			<label for="indirizzo">Indirizzo:</label><br>
			<input id="indirizzo" name="indirizzo" type="text" maxlength="20" placeholder="Inserisci indirizzo" required value="<%=product.getIndirizzo()%>"><br>
			
			<label for="cap">Cap:</label><br>
			<input id="cap" name="cap" type="text" maxlength="5" placeholder="Inserisci CAP" required value="<%=product.getCap()%>"><br>
			
			<label for="comune">Comune:</label><br>
			<input id="comune" name="comune" type="text" maxlength="100" placeholder="Inserisci città" required value="<%=product.getComune()%>"><br>
			
			<label for="provincia">Provincia:</label><br>
			<input id="provincia" name="provincia" type="text" maxlength="100" placeholder="Inserisci provincia"  required value="<%=product.getProvincia()%>"><br>
			
			<label for="prezzo">Prezzo:</label><br>
			<input id="prezzo" name="prezzo" type="number" maxlength="100" placeholder="Inserisci prezzo"  required value="<%=product.getPrezzo()%>"><br>
			
			<label for="prodotti">Prodotti:</label><br>
			<textarea id="prodotti" name="prodotti" maxlength="900" rows="3" placeholder="Inserisci prodotti" required value=><%=product.getProdotti()%></textarea><br>
				
			<label for="controllato">Controllato:</label><br>
			<!--  <input id="idcategoria" name="idcategoria" type="text" placeholder="Inserisci controllo" required><br>-->
			 <select name="controllato" id="controllato">
   			 	<option value="true">true</option>
   				 <option value="false">false</option>
 			 </select>
			<br>
					
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

<!-- FORM PER L'INSERIMENTO DI UN ORDINE -->

<%} else{%>
	<script> 
	alert("Pagina riservata al gestore ordine")
	</script>
	
<%} %>
   
<body>

</body>
</html>