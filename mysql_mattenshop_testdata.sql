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
INSERT INTO account (email, wachtwoord, salt, account_type) VALUES ("boer@piet.nl", "[B@224aed64", "[B@29453f44", "Admin");
INSERT INTO account (email, wachtwoord, salt, account_type) VALUES ("stephan@borst.nl", "[B@71e7a66b", "[B@c39f790", "Medewerker");
INSERT INTO account (email, wachtwoord, salt, account_type,klant_id) VALUES ("klant1@klant.nl", "[B@5f150435", "[B@2ac1fdc4", "Klant", 1);


-- -----------------------------------------------------
-- Table `mattenshop`.`artikel`
-- -----------------------------------------------------
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (1,"antislipmat", 5, 100, 10 , 3);
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (2,"borstelmat", 3.5, 100, 11 , 3);
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (3,"grasmat", 7, 100, 12 , 1);
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (4,"rubbermat", 2.5, 100, 13 , 2);
INSERT INTO artikel (id,naam,prijs,voorraad,gereserveerd,sortering) VALUES (5,"spaghettimat", 8, 100, 0 , 1);


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


