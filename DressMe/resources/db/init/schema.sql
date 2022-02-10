CREATE TABLE IF NOT EXISTS Categoria
(
`nome` varchar(15) NOT NULL,
`numeroArticolo` varchar(15) NOT NULL,
primary key(`nome`)
);

CREATE TABLE IF NOT EXISTS Cliente
(
`nome` varchar(100) NOT NULL,
`cognome` varchar(100) NOT NULL,
`email` varchar(40) NOT NULL,
`indirizzo` varchar(25) NOT NULL,
`password` varchar(93) NOT NULL,
PRIMARY KEY (`email`)
);



CREATE TABLE IF NOT EXISTS Personale (
`nome` varchar(40) NOT NULL,
`cognome` varchar(15) NOT NULL,
`email` varchar(40) not null,
`password` varchar(250) not null,
`ruolo` varchar (30) not null,



PRIMARY KEY(`email`)
);





CREATE TABLE IF NOT EXISTS Vestito
(
`codiceVestito` varchar(5) NOT NULL,
`idcategoria` varchar(15) NOT NULL,
`quantitaVestito` int NOT NULL,
`titolo` varchar(25) NOT NULL,
`descrizione` varchar(50) NOT NULL,
`prezzo` int NOT NULL,
`copertina` varchar(100) NOT NULL,

primary key(`codiceVestito`),
FOREIGN KEY(`idcategoria`) references Categoria(`nome`)on delete cascade
);

CREATE TABLE IF NOT EXISTS Carrello (
`idEmail` varchar(40) not null,
`codiceVestito` varchar(5)not null,



FOREIGN KEY(`idEmail`) references Cliente(`email`) on delete cascade
);



CREATE TABLE IF NOT EXISTS GestoriOrdini (
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
FOREIGN KEY(email) references Cliente(email) on update cascade on delete cascade
); 