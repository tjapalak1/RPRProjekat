BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `grad` (
	`id`	INTEGER,
	`naziv`	TEXT,
	`broj_stanovnika`	INTEGER,
	`drzava`	INTEGER,
	`olimpijski`	INTEGER,
	FOREIGN KEY(`drzava`) REFERENCES `drzava`,
	PRIMARY KEY(`id`)
);
INSERT INTO `grad` VALUES (1,'Pariz',2206488,1,0);
INSERT INTO `grad` VALUES (2,'London',8825000,2,0);
INSERT INTO `grad` VALUES (3,'Beč',1899055,3,0);
INSERT INTO `grad` VALUES (4,'Manchester',545500,2,0);
INSERT INTO `grad` VALUES (5,'Graz',280200,3,0);
CREATE TABLE IF NOT EXISTS `drzava` (
	`id`	INTEGER,
	`naziv`	TEXT,
	`glavni_grad`	INTEGER,
	`olimpijska`	INTEGER,
	PRIMARY KEY(`id`)
);
INSERT INTO `drzava` VALUES (1,'Francuska',1,0);
INSERT INTO `drzava` VALUES (2,'Velika Britanija',2,0);
INSERT INTO `drzava` VALUES (3,'Austrija',3,0);
COMMIT;
