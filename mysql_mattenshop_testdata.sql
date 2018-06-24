use mattenshop;



-- -----------------------------------------------------
-- Table `mattenshop`.`klant`
-- Table `mattenshop`.`adres`
-- -----------------------------------------------------
DELETE FROM account;
DELETE FROM adres;
DELETE FROM klant;
INSERT INTO klant (id,email,voornaam,achternaam,tussenvoegsel,sortering) VALUES (1,"klant1@klant.nl", "Jan", "Klant1", "" , 3);
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("postadres",12,"h","2211XX","Oegstgeest",1, 'P' );
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("factuuradres",12,"h","2211XX","Oegstgeest",1, 'F' );
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("bezorgadres",12,"h","2211XX","Oegstgeest",1, 'B' );

INSERT INTO klant (email,voornaam,achternaam,tussenvoegsel,sortering) VALUES ("klant2@klant.nl", "Kees", "Klant2", "" , 3);
SELECT LAST_INSERT_ID() INTO @last_klantid;
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("postadres",12,"h","2211XX","Oegstgeest",@last_klantid, 'P' );

INSERT INTO klant (email,voornaam,achternaam,tussenvoegsel,sortering) VALUES ("klant3@klant.nl", "Marije", "Klant3", "" , 3);
SELECT LAST_INSERT_ID() INTO @last_klantid;
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("postadres",12,"h","2211XX","Oegstgeest",@last_klantid, 'P' );

INSERT INTO klant (email,voornaam,achternaam,tussenvoegsel,sortering) VALUES ("klant4@klant.nl", "Jolien", "Klant4", "" , 3);
SELECT LAST_INSERT_ID() INTO @last_klantid;
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("postadres",12,"h","2211XX","Oegstgeest",@last_klantid, 'P' );

INSERT INTO klant (email,voornaam,achternaam,tussenvoegsel,sortering) VALUES ("boer@piet.nl", "piet", "boer", "de" , 1);
SELECT LAST_INSERT_ID() INTO @last_klantid;
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("postadres",12,"h","2211XX","Oegstgeest",@last_klantid, 'P' );

INSERT INTO klant (email,voornaam,achternaam,tussenvoegsel,sortering) VALUES ("stephan@borst.nl", "Stephan", "Borst", "" , 2);
SELECT LAST_INSERT_ID() INTO @last_klantid;
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("postadres",12,"h","2211XX","Oegstgeest",@last_klantid, 'P' );

INSERT INTO klant (email,voornaam,achternaam,tussenvoegsel,sortering) VALUES ("tom@devos.nl", "Tom", "Vos", "de" , 2);
SELECT LAST_INSERT_ID() INTO @last_klantid;
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("postadres",12,"h","2211XX","Oegstgeest",@last_klantid, 'P' );

-- -----------------------------------------------------
-- Table `mattenshop`.`account`
-- -----------------------------------------------------
INSERT INTO account (email, wachtwoord, account_type) VALUES ("boer@piet.nl", "piet", "A");
INSERT INTO account (email, wachtwoord, account_type) VALUES ("stephan@borst.nl", "stephan", "M");
INSERT INTO account (email, wachtwoord, account_type,klant_id) VALUES ("klant@klant.nl", "klant", "K", 1);


-- -----------------------------------------------------
-- Table `mattenshop`.`artikel`
-- -----------------------------------------------------
DELETE FROM artikel;
INSERT INTO artikel (naam,prijs,voorraad,gereserveerd,sortering) VALUES ("antislipmat", 5, 18, 1 , 3);
INSERT INTO artikel (naam,prijs,voorraad,gereserveerd,sortering) VALUES ("borstelmat", 3.5, 16, 1 , 3);
INSERT INTO artikel (naam,prijs,voorraad,gereserveerd,sortering) VALUES ("grasmat", 7, 35, 1 , 1);
INSERT INTO artikel (naam,prijs,voorraad,gereserveerd,sortering) VALUES ("rubbermat", 2.5, 27, 1 , 2);
INSERT INTO artikel (naam,prijs,voorraad,gereserveerd,sortering) VALUES ("spaghettimat", 8, 20, 1 , 1);

