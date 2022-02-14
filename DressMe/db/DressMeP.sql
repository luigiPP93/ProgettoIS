DROP DATABASE IF EXISTS DressMeP;

CREATE DATABASE DressMeP;
USE DressMeP;

DROP USER IF EXISTS 'tsw'@'localhost';
CREATE USER 'tsw'@'localhost' IDENTIFIED BY 'adminadmin';
GRANT ALL ON DressMeP.* TO 'tsw'@'localhost';

 create table Categoria
 (
    nome varchar(15) NOT NULL,
	numeroArticolo int NOT NULL,
    primary key(nome)
 );
 
 create table Vestito
 (
	codiceVestito varchar(5) NOT NULL,
    idcategoria varchar(15) NOT NULL,
    quantitaVestito int NOT NULL,
    titolo varchar(25) NOT NULL,
    descrizione varchar(50) NOT NULL,
    prezzo int NOT NULL,
    copertina varchar(100) NOT NULL,
   
    primary key(codiceVestito),
    FOREIGN KEY(idcategoria) references Categoria(nome) on update cascade on delete cascade
 );
 
    
create table Cliente (
nome varchar(15) NOT NULL,
cognome varchar(15) NOT NULL,
email varchar(25),
indirizzo varchar(30),
password varchar(250),

PRIMARY KEY(email)
);


create table Personale (
nome varchar(15) NOT NULL,
cognome varchar(15) NOT NULL,
email varchar(25) not null,
password varchar(250) not null,
ruolo varchar (30) not null,

PRIMARY KEY(email)
);


create table Carrello (
idemail varchar(25) not null,
codiceVestito varchar(5)not null,

FOREIGN KEY(idEmail) references Cliente(email) on update cascade on delete cascade,
FOREIGN KEY(codiceVestito) references Vestito(codiceVestito) on update cascade on delete cascade
);

create table Storico(
	idemail varchar(25) not null,
	codiceVestito varchar(5)not null,
	FOREIGN KEY(idEmail) references Cliente(email) on update cascade on delete cascade,
	FOREIGN KEY(codiceVestito) references Vestito(codiceVestito) on update cascade on delete cascade
);

create table Ordine (
numeroOrdine int auto_increment,
email varchar(30) not null,
nome varchar(15) NOT NULL,
cognome varchar(15) NOT NULL,
indirizzo varchar(30) not null,
cap varchar(5) not null,
comune varchar(30) not null,
provincia varchar(30) not null,
prezzo varchar(30) not null,
prodotti varchar(300) not null,
controllato varchar(5) not null,

PRIMARY KEY(numeroOrdine),
FOREIGN KEY(email) references Cliente(email)
);

DROP trigger if exists gestione_dato_ridondante;
delimiter //
create trigger gestione_dato_ridondante after insert on Vestito for each row
begin
update categoria
set numeroArticolo = numeroArticolo + 1
where nome = new.idcategoria ;
END; //
DELIMITER

DROP trigger if exists gestione_dato_ridondante_2;
delimiter //
create trigger gestione_dato_ridondante_2 after delete on Vestito for each row
begin
update categoria
set numeroArticolo= numeroArticolo - 1
where nome = old.idcategoria ;
END; //
DELIMITER 


DROP trigger if exists ACQUISTO;
delimiter //
create trigger ACQUISTO after insert on Storico for each row
begin
update Vestito
set quantitaVestito = quantitaVestito - 1
where codiceVestito = new.codiceVestito ;
END; //
DELIMITER 

DROP TRIGGER IF EXISTS CHECK_QUANTITA;
DELIMITER //
CREATE TRIGGER CHECK_QUANTITA BEFORE update ON Vestito FOR EACH ROW
BEGIN
IF new.quantitaVestito < 0 THEN SET NEW.codiceVestito= NULL;
END IF;
END; //
DELIMITER ;

 /*---------------------------------------------------------------------  Categoria   ------------------------------------------------------------------------*/

 INSERT INTO Categoria(nome,numeroArticolo)
 VALUES ('Uomo','0');

 INSERT INTO Categoria(nome,numeroArticolo)
 VALUES ('Donna','0');
 
 INSERT INTO Categoria(nome,numeroArticolo)
 VALUES ('Bambini','0');


 /*---------------------------------------------------------------------  Clienti   ------------------------------------------------------------------------*/


 INSERT INTO Cliente(nome, cognome, email ,indirizzo, password)
 VALUES ('Aurora','Sella','nessuno@gmail.com','via Roma 41, Avellino','bbb7b19fe1ed2f32883d32234246d88462df528f1a05d358957aeed805a447e0');
 INSERT INTO Cliente(nome, cognome, email ,indirizzo, password)
 VALUES ('luigi','Sella','luigi@gmail.com','via Roma 41, Avellino','1234bbb7b19fe1ed2f32883d32234246d88462df528f1a05d358957aeed805a447e056');
 INSERT INTO Cliente(nome, cognome, email ,indirizzo, password)
 VALUES ('Mario','Sella','mario@gmail.com','via Roma 41, Avellino','bbb7b19fe1ed2f32883d32234246d88462df528f1a05d358957aeed805a447e0');
 INSERT INTO Cliente(nome, cognome, email ,indirizzo, password)
 VALUES ('Luigi','Sella','luigi.sica@gmail.com','via Roma 41, Avellino','bbb7b19fe1ed2f32883d32234246d88462df528f1a05d358957aeed805a447e0');
 
INSERT INTO Cliente(nome, cognome, email ,indirizzo, password)
 VALUES ('Luigi','Sica','luigi.sica1@gmail.com','via Roma 41, Avellino','bbb7b19fe1ed2f32883d32234246d88462df528f1a05d358957aeed805a447e0');
 
 INSERT INTO Cliente(nome, cognome, email ,indirizzo, password)
 VALUES ('Giovanni','Festa','abcd@gmail.com','via Roma 41, Avellino','bbb7b19fe1ed2f32883d32234246d88462df528f1a05d358957aeed805a447e0');
 

 /*--------------------------------------------------------------------- Personale  ------------------------------------------------------------------------*/
 
 INSERT INTO Personale(nome, cognome, email, password,ruolo)
 VALUES ('Aurora','Sella','ordini@gmail.com','bbb7b19fe1ed2f32883d32234246d88462df528f1a05d358957aeed805a447e0','Gestore ordini');
 
  INSERT INTO Personale(nome, cognome, email ,password,ruolo)
 VALUES ('Luigi','Sella','prodotti@gmail.com','bbb7b19fe1ed2f32883d32234246d88462df528f1a05d358957aeed805a447e0','Gestore prodotti');
 
 INSERT INTO Personale(nome, cognome, email , password,ruolo)
 VALUES ('Amministratore','Admin','admin@gmail.com','bbb7b19fe1ed2f32883d32234246d88462df528f1a05d358957aeed805a447e0','Direttore');


/*------------------------------------------------------------------------	 GestoriOdini    ------------------------------------------------------------------------*/



INSERT INTO Ordine(email,nome,cognome,indirizzo ,cap,comune,provincia,prezzo,prodotti,controllato)
 VALUES ('luigi@gmail.com','Luigi','Sella','via Roma 41','83100','Avellino','Avellino','30$',"Prodotto 1 maglia, prodotto 2 giubbotto",'false');
 INSERT INTO Ordine(email,nome,cognome,indirizzo ,cap,comune,provincia,prezzo,prodotti,controllato)
 VALUES ('nessuno@gmail.com','Marta','Sella','via Roma 41','83100','Avellino','Avellino','30$',"Prodotto 1 maglia, prodotto 2 giubbotto",'false');
 INSERT INTO Ordine(email,nome,cognome,indirizzo ,cap,comune,provincia,prezzo,prodotti,controllato)
 VALUES ('mario@gmail.com','Emanuele','Sella','via Roma 41','83100','Avellino','Avellino','30$',"Prodotto 1 maglia, prodotto 2 giubbotto",'false');
 
 INSERT INTO Ordine(email,nome,cognome,indirizzo ,cap,comune,provincia,prezzo,prodotti,controllato)
 VALUES ('mario@gmail.com','Emanuele','Sella','via Roma 41','83100','Avellino','Avellino','30$',"Prodotto 1 maglia, prodotto 2 giubbotto",'false');
INSERT INTO Ordine(email,nome,cognome,indirizzo ,cap,comune,provincia,prezzo,prodotti,controllato)
 VALUES ('mario@gmail.com','Emanuele','Sella','via Roma 41','83100','Avellino','Avellino','30$',"Prodotto 1 maglia, prodotto 2 giubbotto",'false');
INSERT INTO Ordine(email,nome,cognome,indirizzo ,cap,comune,provincia,prezzo,prodotti,controllato)
 VALUES ('mario@gmail.com','Emanuele','Sella','via Roma 41','83100','Avellino','Avellino','30$',"Prodotto 1 maglia, prodotto 2 giubbotto",'false');

/*------------------------------------------------------------------------Maglie Uomo------------------------------------------------------------------------*/

INSERT INTO Vestito(codiceVestito, idcategoria, quantitaVestito, titolo,descrizione, prezzo, copertina)
VALUES ('MG001','Uomo','10','T-Shirt Colors','Maglia con triangolo multicolore','9',"image/Uomo/uomo_maglia1.png");

INSERT INTO Vestito(codiceVestito, idcategoria, quantitaVestito, titolo,descrizione, prezzo, copertina)
 VALUES ('MG002','Uomo','20','T-Shirt Kalvin Klein','Maglia in cotone','15',"image/Uomo/uomo_maglia2.png");

 INSERT INTO Vestito(codiceVestito, idcategoria, quantitaVestito, titolo,descrizione, prezzo, copertina)
 VALUES ('MG003','Uomo','30','Maglia rosa','Maglia colore nero-verde','34',"image/Uomo/uomo_maglia3.png");
 
 INSERT INTO Vestito(codiceVestito, idcategoria, quantitaVestito, titolo,descrizione, prezzo, copertina)
 VALUES ('MG004','Uomo','14','Tommy Hilfiger','Maglia bianca ultimi arrivi','12',"image/Uomo/uomo_maglia4.png");

 INSERT INTO Vestito(codiceVestito, idcategoria, quantitaVestito, titolo,descrizione, prezzo, copertina)
 VALUES ('MG005','Uomo','7','Maglia T-Style','Maglia bicolore con taschino','10',"image/Uomo/uomo_maglia5.png");
 
 /*------------------------------------------------------------------------ Felpe Uomo------------------------------------------------------------------------*/
 
 
 
 /*------------------------------------------------------------------------	  Bambini   ------------------------------------------------------------------------*/
 /*------------------------------------------------------------------------Maglie Donna------------------------------------------------------------------------*/
 
 INSERT INTO Vestito(codiceVestito, idcategoria, quantitaVestito, titolo,descrizione, prezzo, copertina)
 VALUES ('MG006','Donna','13','Shirt Love-Love','Maglia rosa con scritta love','18',"image/Donna/donna_maglia1.png");
 
  INSERT INTO Vestito(codiceVestito, idcategoria, quantitaVestito, titolo,descrizione, prezzo, copertina)
 VALUES ('MG407','Donna','18','T-Shirt Tommy Hilfiger','Maglia rosa Shinny-floe','32',"image/Donna/donna_maglia2.png");
 
  INSERT INTO Vestito(codiceVestito, idcategoria, quantitaVestito, titolo,descrizione, prezzo, copertina)
 VALUES ('MG408','Donna','18','Levis','T-shirt ragazza casual','20',"image/Donna/donna_maglia3.png");
 
  INSERT INTO Vestito(codiceVestito, idcategoria, quantitaVestito, titolo,descrizione, prezzo, copertina)
 VALUES ('MG409','Donna','14','EvenNight','Maglia verde one-size','17',"image/Donna/donna_maglia4.png");

/*------------------------------------------------------------------------	  Bambini   ------------------------------------------------------------------------*/
/*------------------------------------------------------------------------Maglie Bambino ------------------------------------------------------------------------*/
 
 INSERT INTO Vestito(codiceVestito, idcategoria, quantitaVestito, titolo,descrizione, prezzo, copertina)
 VALUES ('MG410','Bambini','24','T-shirt Converse','Maglia bambino blue ','16',"image/Bambini/bimbo.maglia1.png");
 
  INSERT INTO Vestito(codiceVestito, idcategoria, quantitaVestito, titolo,descrizione, prezzo, copertina)
 VALUES ('MG411','Bambini','12','T-shirt Converse','Maglia bambina verde converse','13',"image/Bambini/bimba.maglia1.png");
 
  INSERT INTO Vestito(codiceVestito, idcategoria, quantitaVestito, titolo,descrizione, prezzo, copertina)
 VALUES ('MG412','Bambini','13','Pink-Love','Maglia Rosa Love','12',"image/Bambini/bimba.maglia2.png");
 
  INSERT INTO Vestito(codiceVestito, idcategoria, quantitaVestito, titolo,descrizione, prezzo, copertina)
 VALUES ('MG413','Bambini','18','T-shirt Guest Style','Maglia bambino blue','9',"image/Bambini/bimbo.maglia2.png");
 
  INSERT INTO Vestito(codiceVestito, idcategoria, quantitaVestito, titolo,descrizione, prezzo, copertina)
 VALUES ('MG414','Bambini','4','Maglia T-REX','Maglia REX RoaaaaR','15',"image/Bambini/bimbo.maglia3.png");
 
 /*--------------------------------------------------------------------- Carrello  ------------------------------------------------------------------------*/
 INSERT INTO Carrello(idemail ,codiceVestito )
 VALUES ('nessuno@gmail.com','MG006');
 
 