<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="carta.css">
<link rel="stylesheet" href="style.css">


<meta charset="ISO-8859-1">
<title>Pagamento</title>

<meta http-equiv="cache-control" content="max-age=0" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="pragma" content="no-cache" />

<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Cache-Control", "no-store");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>
<script type="text/javascript">
		if (window.performance && window.performance.navigation.type === window.performance.navigation.TYPE_BACK_FORWARD) {
			window.location.replace("http://localhost:8080/DressMe/successoOrdine.jsp");
		}
    </script>


<script>
function tryPagamento(){
		const cvc = /^[0-9]{3,3}/;
		const cod = /^[0-9 \s]{19,19}/;
		const scadenza = /^[0-9 -]{7,7}/;
		
		if($("#cod").val().match(cod)) {
			if($("#scadenza").val().match(scadenza)){ // scadenza qui
				if($("#cvc").val().match(cvc)) {
				$.ajax(
					{
						method: "POST",
						url: "CarrelloFineControl?azione=pagamentoCart&action=clearCart",
						data: {
							cod: $("#cod").val(),
							nome: $("#nome").val(),
							scadenza: $("#scadenza").val(),
							cvc: $("#cvc").val(),
							
							
						},
						success: (data) => {
							window.location.replace("http://localhost:8080/DressMe/successoOrdine.jsp");
						},
						error: (data) => {
							$("#pagamento-message").html("OPS!!");
							$("#pagamento-message").css("color", "red");
						}	
					}
				);
				}else {
					$("#pagamento-message").css("color", "red");
					$("#pagamento-message").html(
						"OPS, Formato cvc non valido.<br/>"
					);
				}
			}else {
				$("#pagamento-message").css("color", "red");
				$("#pagamento-message").html(
					"OPS, Formato data carta non valido.<br/>"
				);
			}
		}else {
			$("#pagamento-message").css("color", "red");
			$("#pagamento-message").html(
				"OPS, Formato codice carta non valido.<br/>"
			);
		}
	}
	
	</script>
</head>


<body>
	<!-- ------------------------------------ Header ------------------------------------  -->



	<!-- ------------------------------------ Accesso ----------------------------------------  -->
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

	<%
	int prezzo = (int) request.getSession().getAttribute("prezzo");
	%>
	<%
	String nome = (String) request.getSession().getAttribute("nome");
	%>
	
	<h2 class="carta">Ciao <%=nome%> compila i campi per terminare l'acquito</h2>
	
	
	<script type="text/javascript">$(function(){
	 
	  var cards = [{
	    nome: "mastercard",
	    colore: "#0061A8",
	    src: "https://upload.wikimedia.org/wikipedia/commons/0/04/Mastercard-logo.png"
	  }, {
	    nome: "visa",
	    colore: "#E2CB38",
	    src: "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Visa_Inc._logo.svg/2000px-Visa_Inc._logo.svg.png"
	  }, {
	    nome: "dinersclub",
	    colore: "#888",
	    src: "http://www.worldsultimatetravels.com/wp-content/uploads/2016/07/Diners-Club-Logo-1920x512.png"
	  }, {
	    nome: "americanExpress",
	    colore: "#108168",
	    src: "https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/American_Express_logo.svg/600px-American_Express_logo.svg.png"
	  }, {
	    nome: "discover",
	    colore: "#86B8CF",
	    src: "https://lendedu.com/wp-content/uploads/2016/03/discover-it-for-students-credit-card.jpg"
	  }, {
	    nome: "dankort",
	    colore: "#0061A8",
	    src: "https://upload.wikimedia.org/wikipedia/commons/5/51/Dankort_logo.png"
	  }];
	  
	  var month = 0;
	  var html = document.getElementsByTagName('html')[0];
	  var number = "";
	  
	  var selected_card = -1;
	  
	  //Card number input
	  $(".number").keyup(function(event){
	    $(".card_number").text($(this).val());
	    number = $(this).val();
	    
	    if(parseInt(number.substring(0, 2)) > 50 && parseInt(number.substring(0, 2)) < 56){
	      selected_card = 0;
	    }else if(parseInt(number.substring(0, 1)) == 4){//controlla a quale banca è affiliato
	      selected_card = 1;  
	    }else if(parseInt(number.substring(0, 2)) == 36 || parseInt(number.substring(0, 2)) == 38 || parseInt(number.substring(0, 2)) == 39){
	      selected_card = 2;     
	    }else if(parseInt(number.substring(0, 2)) == 34 || parseInt(number.substring(0, 2)) == 37){
	      selected_card = 3; 
	    }else if(parseInt(number.substring(0, 2)) == 65){
	      selected_card = 4; 
	    }else if(parseInt(number.substring(0, 4)) == 5019){
	      selected_card = 5; 
	    }else{
	      selected_card = -1; 
	    }
	    
	    if(selected_card != -1){
	      html.setAttribute("style", "--card-color: " + cards[selected_card].colore); //setta il colore
	      $(".bankid").attr("src", cards[selected_card].src).show();//mostra il logo
	    }else{
	      html.setAttribute("style", "--card-color: #cecece");//in caso non è registrata la banca
	      $(".bankid").attr("src", "").hide();
	    }

	  }).focus(function(){
	    $(".card_number").css("color", "white");//setta i numeri sulla carta di colore bianco
	  }).on("keydown input", function(){
	    
	    $(".card_number").text($(this).val());
	    
	    if(event.key >= 0 && event.key <= 9){
	      if($(this).val().length === 4 || $(this).val().length === 9 || $(this).val().length === 14){
	        $(this).val($(this).val() +  " ");//permette di mettere gli spazi dopo le prime 4, 9, 14 cifre
	      }
	    }
	  })
	  
	  //Nome
	  $(".inputname").keyup(function(){  
	    $(".fullname").text($(this).val());  
	    if($(".inputname").val().length === 0){
	        $(".fullname").text("FULL NAME");
	    }
	    return event.charCode;
	  }).focus(function(){
	    $(".fullname").css("color", "white");//setta il colore bianco a nome sulla carta
	  });
	  
	  //Sicurezza codice Input
	  $(".ccv").focus(function(){
	    $(".card").css("transform", "rotatey(180deg)");//ruota la carta
	    $(".seccode").css("color", "white");
	  })
	  .keyup(function(){
	    $(".seccode").text($(this).val());//inserisce il codice sulla carta
	    if($(this).val().length === 0){
	      $(".seccode").html("&#x25CF;&#x25CF;&#x25CF;");
	    }
	  }).focusout(function() {
	      $(".card").css("transform", "rotatey(0deg)");//quando perde il focus ritorna allo stato originario
	      $(".seccode").css("color", "var(--text-color)");
	  });
	    
	  
	  //Data scadenza input
	  $(".expire").keypress(function(event){
	  
	      if($(this).val().length === 1){
	          $(this).val($(this).val() + event.key + "-");
	      }else if($(this).val().length === 0){
	        if(event.key == 2 || event.key == 1 || event.key == 0 || event.key == 3){//se iniza con questi numeri va avanti
	          month = event.key;
	          return event.charCode;
	        }else{
	          $(this).val(0 + event.key + "-");//se è 5 allora stampa direttamente 05
	        }
	      }else if($(this).val().length > 2 && $(this).val().length < 9){
	        return event.charCode;//se il codice e giusto lo restituisce
	      }

	    return false;
	  }).keyup(function(event){
	    $(".date_value").html($(this).val());//inserisce l'elemento nella carta
	 
	    if($(this).val().length === 0){
	      $(".date_value").text("MM-YYYY");
	    }
	  }).keydown(function(){
	    $(".date_value").html($(this).val());
	  }).focus(function(){
	    $(".date_value").css("color", "white");//setta il colore a bianco
	  });
	});
	</script>
	<div class="tips">Numero della carta di pagamento: (4) VISA, (51
		-> 55) MasterCard, (36-38-39) DinersClub, (34-37) American Express,
		(65) Discover, (5019) dankort</div>

	<div class="container">
		<div class="col1">
			<div class="card">
				<div class="front">
					<div class="type">
						<img class="bankid" />
					</div>
					<span class="chip"></span> <span class="card_number">&#x25CF;&#x25CF;&#x25CF;&#x25CF;
						&#x25CF;&#x25CF;&#x25CF;&#x25CF; &#x25CF;&#x25CF;&#x25CF;&#x25CF;
						&#x25CF;&#x25CF;&#x25CF;&#x25CF; </span>
					<div class="date">
						<span class="date_value">MM / AAAA</span>
					</div>
					<span class="fullname">NOME COMPLETO</span>
				</div>
				<div class="back">
					<div class="magnetic"></div>
					<div class="bar"></div>
					<span class="seccode">&#x25CF;&#x25CF;&#x25CF;</span> <span
						class="chip"></span><span class="disclaimer">Questa carta
						è di proprietà della Random Bank of Random Corporation.<br>
						Se trovato, si prega di restituire alla Random Bank of Random
						Corporation - 21968 Parigi, Verdi Street, 34
					</span>
				</div>
			</div>
		</div>

  <form  onsubmit="tryPagamento(); return false">
		  	
			<div class="col2">
				<h3 id="pagamento-message"></h3>
				
				<label>Numero carta</label> 
				<input class="number" id="cod" name="cod" type="text" required="" ng-model="ncard" maxlength="19" onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
				<label>Nome e cognome intestatario</label> 
				<input class="inputname"  id="nome" name="nome"type="text" required="" placeholder="" /> 
				<label>Data Scadenza</label> 
				<input class="expire" id="scadenza" name="scadenza" type="text" maxlength="7" required="" placeholder="MM-YYYY" /> 
				<label>Numero sicuro</label> 
				<input class="ccv" type="text" id="cvc" name="cvc" required="" placeholder="CVC" maxlength="3" onkeypress='return event.charCode >= 48 && event.charCode <= 57' />

				<button class="buy"><i class="material-icons"></i> Paga <%=prezzo%>&euro;</button>
				
				
			</div>

		</form>
	</div>

	<%
	}
	%>

	<!-- ------------------------------------ Footer ----------------------------------------  -->

</body>

</html>