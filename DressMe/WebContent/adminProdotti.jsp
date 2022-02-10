<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,gestioneProdotti.*"%>
    
    <%
    Collection<?> products = (Collection<?>)request.getAttribute("products");

	String error = (String)request.getAttribute("error");
	 
	if(products == null && error == null) {
		response.sendRedirect(response.encodeRedirectURL("./AdminProdotti"));
		return;
	}
	
	ShopBean product = (ShopBean)request.getAttribute("product");
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

	function validazioneProdotto() 
	{
		var codiceVestito=document.forms['InserimentoProdotto'].codiceVestito.value;
		 if(!/^[a-zA-Z0-9]{5,5}/.test(codiceVestito)){
			    	alert("Il codice che hai inserito non è valido");
			   	 return false;
			    }
	    var prezzo=document.forms['InserimentoProdotto'].prezzo.value;
	    if(prezzo<=0){
	    	alert("Il prezzo che hai inserito non è valido");
	   	 return false;
	    }	          
	}	
	
	function validazioneModificaProdotto() 
	{
	    var prezzo=document.forms['ModificaProdotto'].prezzo.value;
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
else if( r.equals("Gestore prodotti")){ %>
<!-- CATALOGO -->
<br>
<table class="container">

<a class="Opzioni-effect" href="<%=response.encodeURL("LogoutControl") %>">Logout</a>
<h2>Elenco Prodotti</h2>
	<tr>
		<th>Codice <a href="<%=response.encodeURL("AdminProdotti?sort=codiceVestito") %>"></a></th>
		<th>Nome <a href="<%=response.encodeURL("AdminProdotti?sort=titolo") %>"></a></th>
		<th>Descrizione <a href="<%=response.encodeURL("AdminProdotti?sort=descrizione") %>"></a></th>
		<th>Immagini</th>
		<th>Action</th>
	</tr>
	<%
		if(products != null && products.size() > 0) {
	
			Iterator<?> it = products.iterator();
			while(it.hasNext()) {
				ShopBean bean = (ShopBean)it.next();
	%>
			<tr>
				<td><%=bean.getCodiceVestito() %> </td>
				<td><%=bean.getTitolo() %>	</td>
				<td><%=bean.getDescrizione() %> </td>
				<td><img src="<%= bean.getCopertina()%>" onerror="this.src='./image/noimage.png'" width=50px></td>
				
				<td>
					<a href="<%=response.encodeURL("AdminProdotti?action=details&id="+bean.getCodiceVestito()) %>">Dettagli</a>
					<a href="<%=response.encodeURL("AdminProdotti?action=delete&id="+bean.getCodiceVestito()) %>">Elimina</a>
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
	if(product != null && !product.isEmpty()){
%>
	<!-- DETTAGLI PRODOTTO -->
	<h2>Dettagli Prodotto</h2>
	<table class="container">
		<tr>
			<th>Codice</th>
			<th>Nome</th>
			<th>Descrizione</th>
			<th>Prezzo</th>
			<th>Quantita</th>
		</tr>
		<tr>
			<td><%=product.getCodiceVestito() %></td>
			<td><%=product.getTitolo() %></td>
			<td><%=product.getDescrizione() %></td>
			<td><%=product.getPrezzo() %></td>
			<td><%=product.getQuantitaVestito() %></td>
			<td><img src="<%= product.getCopertina()%>" onerror="this.src='./image/noimage.png'" width=100px></td>
			
		</tr>
	</table>
	
	<br/>
	
	<!-- FORM PER LA MODIFICA DEL PRODOTTO -->
	
	<section class="container">
	
	<form class="container-form" name="ModificaProdotto" onsubmit="return validazioneModificaProdotto()" action="<%=response.encodeURL("AdminProdotti")%>" method="POST">
			<legend><b>Modifica</b></legend>
			
			<input type="hidden" name="action" value="update">
			<input type="hidden" name="id" value="<%=product.getCodiceVestito()%>">
			
			<label for="idcategoria">Categoria:</label><br>
			<!--  <input id="idcategoria" name="idcategoria" type="text" placeholder="enter id" required><br>-->
			 <select name="idcategoria" id="idcategoria">
   			 	<option value="Uomo">Uomo</option>
   				 <option value="Donna">Donna</option>
   				 <option value="Bambino">Bambini</option>
 			 </select>
			<br>
			
			<label for="quantitaVestito">Quantità:</label><br>
			<input id="quantitaVestito" name="quantitaVestito" type="number" min="1" required value="<%=product.getQuantitaVestito()%>"><br>
			
			<label for="titolo">Nome:</label><br>
			<input id="titolo" name="titolo" type="text" maxlength="20" placeholder="enter name" required value="<%=product.getTitolo()%>"><br>
			
			<label for="descrizione">Descrizione:</label><br>
			<textarea id="descrizione" name="descrizione" maxlength="100" rows="3" placeholder="enter description" required><%=product.getDescrizione()%></textarea><br>
			
			<label for="prezzo">Prezzo:</label><br>
			<input id="prezzo" name="prezzo" type="number" min="0" required value="<%=product.getPrezzo()%>"><br>
					
			<label for="copertina">Path img:</label><br>
			<!--  <input id="copertina" name="copertina" type="text" value="<%//=product.getCopertina()%>" ><br> -->  
			<input id="copertina" name="copertina" type="file" ><br>
					
			<input id= "update" type="submit" value="Update">
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

<!-- FORM PER L'INSERIMENTO DI UN PRODOTTO -->
<section class="container">
	<form class="container-form" name="InserimentoProdotto" onsubmit="return validazioneProdotto()" action="<%=response.encodeURL("AdminProdotti")%>" method="POST">
	
			<legend><b>Inserimento</b></legend>
			<input type="hidden" name="action" value="insert">
			
			<label for="codiceVestito">Codice:</label><br>
			<input id="codiceVestito" name="codiceVestito" type="text" maxlength="5" placeholder="enter codice" required><br>
			
			<label for="idcategoria">Idcategoria:</label><br>
			<!--  <input id="idcategoria" name="idcategoria" type="text" placeholder="enter id" required><br>-->
			 <select name="idcategoria" id="idcategoria">
   			 	<option value="Uomo">Uomo</option>
   				 <option value="Donna">Donna</option>
   				 <option value="Bambino">Bambini</option>
 			 </select>
			<br>
			
			<label for="quantitaVestito">Quantita:</label><br>
			<input id="quantitaVestito" name="quantitaVestito" type="number" min="0" required><br>
			
			<label for="titolo">Name:</label><br>
			<input id="titolo" name="titolo" type="text" maxlength="20" placeholder="enter titolo" required><br>
			
			<label for="descrizione">Descrizione:</label><br>
			<textarea id="descrizione" name="descrizione" maxlength="100" rows="3" placeholder="enter descrizione" required></textarea><br>
			
			<label for="prezzo">Prezzo:</label><br>
			<input id="prezzo" name="prezzo" type="number" min="0" required><br>
		
			<label for="copertina">Copertina:</label><br>
			<input id="copertina" name="copertina"  type="file" ><br>
		
			<input type="submit" value="Insert">
			<input type="reset" value="Reset">
		
	</form>
</section>
<%} else{%>
	<script> 
	alert("Pagina riservata al Gestore Prodotti")
	</script>
	
<%} %>

   
<body>

</body>
</html>