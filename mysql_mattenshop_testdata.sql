-- -----------------------------------------------------
-- Create user (specified in mysql.xml)
-- -----------------------------------------------------
DROP USER IF EXISTS 'kaas'@'localhost';
CREATE USER 'kaas'@'localhost' IDENTIFIED BY '123Kaas_kaas';
GRANT ALL PRIVILEGES ON mattenshop.* TO 'kaas'@'localhost' WITH GRANT OPTION;

-- -----------------------------------------------------
-- Now create the database for the application
-- -----------------------------------------------------
use mattenshop;

DELETE FROM account;
DELETE FROM adres;
DELETE FROM bestel_regel;
DELETE FROM bestelling;
DELETE FROM artikel;
DELETE FROM klant;

-- -----------------------------------------------------
-- Table `mattenshop`.`klant`
-- Table `mattenshop`.`adres`
-- -----------------------------------------------------
INSERT INTO klant (id,email,voornaam,achternaam,tussenvoegsel,sortering) VALUES (1,"klant1@klant.nl", "Jan", "Klant1", "" , 3);
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("postadres",12,"h","2211XX","Oegstgeest",1, 'Postadres' );
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("factuuradres",12,"h","2211XX","Oegstgeest",1, 'Factuuradres' );
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("bezorgadres",12,"h","2211XX","Oegstgeest",1, 'Bezorgadres' );

INSERT INTO klant (email,voornaam,achternaam,tussenvoegsel,sortering) VALUES ("klant2@klant.nl", "Kees", "Klant2", "" , 3);
SELECT LAST_INSERT_ID() INTO @last_klantid;
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("postadres",12,"h","2211XX","Oegstgeest",@last_klantid, 'Postadres' );

INSERT INTO klant (email,voornaam,achternaam,tussenvoegsel,sortering) VALUES ("klant3@klant.nl", "Marije", "Klant3", "" , 3);
SELECT LAST_INSERT_ID() INTO @last_klantid;
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("postadres",12,"h","2211XX","Oegstgeest",@last_klantid, 'Postadres' );

INSERT INTO klant (email,voornaam,achternaam,tussenvoegsel,sortering) VALUES ("klant4@klant.nl", "Jolien", "Klant4", "" , 3);
SELECT LAST_INSERT_ID() INTO @last_klantid;
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("postadres",12,"h","2211XX","Oegstgeest",@last_klantid, 'Postadres' );

INSERT INTO klant (email,voornaam,achternaam,tussenvoegsel,sortering) VALUES ("boer@piet.nl", "piet", "boer", "de" , 1);
SELECT LAST_INSERT_ID() INTO @last_klantid;
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("postadres",12,"h","2211XX","Oegstgeest",@last_klantid, 'Postadres' );

INSERT INTO klant (email,voornaam,achternaam,tussenvoegsel,sortering) VALUES ("stephan@borst.nl", "Stephan", "Borst", "" , 2);
SELECT LAST_INSERT_ID() INTO @last_klantid;
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("postadres",12,"h","2211XX","Oegstgeest",@last_klantid, 'Postadres' );

INSERT INTO klant (email,voornaam,achternaam,tussenvoegsel,sortering) VALUES ("tom@devos.nl", "Tom", "Vos", "de" , 2);
SELECT LAST_INSERT_ID() INTO @last_klantid;
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("postadres",12,"h","2211XX","Oegstgeest",@last_klantid, 'Postadres' );

-- -----------------------------------------------------
-- Table `mattenshop`.`account`
-- -----------------------------------------------------
INSERT INTO account (email, wachtwoord, account_type) VALUES ("boer@piet.nl", "bbce59f71108498cbaa06dbc43dc2607db42f3922ff7b971cf40d8c7292bcd3b03ba8b872a324b4e98cd7d1f4cfc541f", "Admin");
INSERT INTO account (email, wachtwoord, account_type) VALUES ("stephan@borst.nl", "df05eac5237c6d42ef730f2e33d8f27449d0411b872ab3fefa874104f05bacf0d9eef8345747e2c16754de318b30a0d3", "Medewerker");
INSERT INTO account (email, wachtwoord, account_type,klant_id) VALUES ("klant1@klant.nl", "fb885ac13f7d80f57e29b88fcfe5d3c31265b69bb0bb278c051aaace540c59c50866a14c2ac42ceed06f0d9970363617", "Klant", 1);


-- -----------------------------------------------------
-- Table `mattenshop`.`artikel`
-- -----------------------------------------------------
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (1,"Deurmat - Rubber Brush 60*80", 38.20, 100, 0 , 3);
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (2,"Deurmat - Rubber Brush 80*100", 64.60, 100, 0 , 3);
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (3,"Deurmat - Rubber Brush 90*150", 114.40, 100, 0 , 1);
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (4,"Deurmat - Rubber Brush 90*100", 128.80, 100, 0 , 2);
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (5,"Ringmat - Oct-O-Mat 60*80", 26.10, 100, 0 , 1);
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (6,"Ringmat - Oct-O-Mat 75*100", 41.80, 100, 0 , 1);
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (7,"Ringmat - Oct-O-Mat 80*120", 51.20, 100, 0 , 1);
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (8,"Ringmat - Oct-O-Mat 100*150", 81.80, 100, 0 , 1); 
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (9,"Schoonloopmat Prelude 150*90", 112.70, 100, 0 , 1);
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (10,"Schoonloopmat Prelude 180*120", 180.40, 100, 0 , 1);
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (11,"Schoonloopmat Prelude 240*120", 240.50, 100, 0 , 1);

-- -----------------------------------------------------
-- Table `mattenshop`.`bestelling`
-- Table `mattenshop`.`bestel_regel`
-- -----------------------------------------------------
INSERT INTO bestelling (id,klant_id,totaalprijs,besteldatum,bestelstatus) VALUES (1,1,280, now(), "Onderhanden");
INSERT INTO bestel_regel (id,bestelling_id,artikel_id,aantal,prijs) VALUES (1,1,1,5, 100);
INSERT INTO bestel_regel (id,bestelling_id,artikel_id,aantal,prijs) VALUES (2,1,2,6, 80);
INSERT INTO bestel_regel (id,bestelling_id,artikel_id,aantal,prijs) VALUES (3,1,3,7, 60);
INSERT INTO bestel_regel (id,bestelling_id,artikel_id,aantal,prijs) VALUES (4,1,4,8, 40);

INSERT INTO bestelling (id,klant_id,totaalprijs,besteldatum,bestelstatus) VALUES (2,1,28, "2018-06-28 12:12:12", "Onderhanden");
INSERT INTO bestel_regel (id,bestelling_id,artikel_id,aantal,prijs) VALUES (5,2,1,5, 10);
INSERT INTO bestel_regel (id,bestelling_id,artikel_id,aantal,prijs) VALUES (6,2,2,5, 8);
INSERT INTO bestel_regel (id,bestelling_id,artikel_id,aantal,prijs) VALUES (7,2,3,5, 6);
INSERT INTO bestel_regel (id,bestelling_id,artikel_id,aantal,prijs) VALUES (8,2,4,5, 4);

INSERT INTO bestelling (id,klant_id,totaalprijs,besteldatum,bestelstatus) VALUES (3,1,25, "2018-06-26 08:12:12", "Onderhanden");
INSERT INTO bestel_regel (id,bestelling_id,artikel_id,aantal,prijs) VALUES (9,3,1,5, 12);
INSERT INTO bestel_regel (id,bestelling_id,artikel_id,aantal,prijs) VALUES (10,3,2,5, 13);


