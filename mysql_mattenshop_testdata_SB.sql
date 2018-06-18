use mattenshop;

-- -----------------------------------------------------
-- Table `mattenshop`.`account`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `mattenshop`.`account` ;
-- 
-- CREATE TABLE IF NOT EXISTS `mattenshop`.`account` (
--   `username` VARCHAR(25) NOT NULL,
--   `wachtwoord` VARCHAR(180) NOT NULL,
--   `account_type` CHAR(1) NOT NULL,
--   PRIMARY KEY (`username`))

DELETE FROM account;
INSERT INTO account (email, wachtwoord, account_type) VALUES ("stephan@borst.nl", "stephan", "M");
INSERT INTO account (email, wachtwoord, account_type) VALUES ("tom@devos.nl", "tom", "M");
INSERT INTO account (email, wachtwoord, account_type) VALUES ("boer@piet.nl", "piet", "A");

-- -----------------------------------------------------
-- Table `mattenshop`.`klant`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `mattenshop`.`klant` ;
-- 
-- CREATE TABLE IF NOT EXISTS `mattenshop`.`klant` (
--   `id` INT NOT NULL AUTO_INCREMENT,
--   `email` VARCHAR(45) NOT NULL,
--   `voornaam` VARCHAR(50) NOT NULL,
--   `achternaam` VARCHAR(50) NOT NULL,
--   `tussenvoegsel` VARCHAR(15) NULL DEFAULT NULL,
--   `sortering` INT NULL,
--   PRIMARY KEY (`id`),
--   UNIQUE INDEX `uq_klant_email` (`email` ASC))

DELETE FROM adres;
DELETE FROM klant;
INSERT INTO klant (email,voornaam,achternaam,tussenvoegsel,sortering) VALUES ("klant1@klant.nl", "Jan", "Klant1", "" , 3);
SELECT LAST_INSERT_ID() INTO @last_klantid;
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("postadres",12,"h","2211XX","Oegstgeest",@last_klantid, 'P' );
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("factuuradres",12,"h","2211XX","Oegstgeest",@last_klantid, 'F' );
INSERT INTO adres (straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) VALUES 
("bezorgadres",12,"h","2211XX","Oegstgeest",@last_klantid, 'B' );

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
-- Table `mattenshop`.`artikel`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `mattenshop`.`artikel` ;

-- CREATE TABLE IF NOT EXISTS `mattenshop`.`artikel` (
--  `id` INT NOT NULL AUTO_INCREMENT,
--  `naam` VARCHAR(45) NOT NULL,
--  `prijs` DECIMAL(6,2) NOT NULL,
--  `voorraad` INT NOT NULL,
--  `gereserveerd` INT NOT NULL,
--  `sortering` INT NULL,
--  PRIMARY KEY (`id`),
--  UNIQUE INDEX `naam_UNIQUE` (`naam` ASC))

DELETE FROM artikel;
INSERT INTO artikel (naam,prijs,voorraad,gereserveerd,sortering) VALUES ("antislipmat", 5, 18, 1 , 3);
INSERT INTO artikel (naam,prijs,voorraad,gereserveerd,sortering) VALUES ("borstelmat", 3.5, 16, 1 , 3);
INSERT INTO artikel (naam,prijs,voorraad,gereserveerd,sortering) VALUES ("grasmat", 7, 35, 1 , 1);
INSERT INTO artikel (naam,prijs,voorraad,gereserveerd,sortering) VALUES ("rubbermat", 2.5, 27, 1 , 2);
INSERT INTO artikel (naam,prijs,voorraad,gereserveerd,sortering) VALUES ("spaghettimat", 8, 20, 1 , 1);