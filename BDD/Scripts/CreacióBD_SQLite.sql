CREATE TABLE "ancoratges" (
	"ancoratge_id"	INTEGER NOT NULL,
	"nom"	TEXT(30) NOT NULL,
	PRIMARY KEY("ancoratge_id" AUTOINCREMENT)
);

CREATE TABLE "dificultats" (
	"dificultat_id"	INTEGER NOT NULL,
	"nom"	TEXT(45) NOT NULL,
	PRIMARY KEY("dificultat_id" AUTOINCREMENT)
);

CREATE TABLE "materials" (
	"material_id"	INTEGER NOT NULL,
	"nom"	TEXT(25) NOT NULL,
	PRIMARY KEY("material_id" AUTOINCREMENT)
);

CREATE TABLE "tipus_roques" (
	"roca_id"	INTEGER NOT NULL,
	"nom"	INTEGER(25) NOT NULL,
	PRIMARY KEY("roca_id" AUTOINCREMENT)
);

CREATE TABLE "escaladors" (
	"escaldor_id"	INTEGER NOT NULL,
	"nom"	TEXT(35) NOT NULL,
	"alies"	TEXT(25) NOT NULL,
	"edad"	INTEGER NOT NULL,
	"nivell"	TEXT(4) NOT NULL,
	"via_nom"	TEXT(35) NOT NULL,
	"estil"	TEXT NOT NULL,
	PRIMARY KEY("escaldor_id" AUTOINCREMENT)
);

CREATE TABLE "poblacions" (
	"poblacio_id"	INTEGER NOT NULL,
	"nom"	TEXT(30) NOT NULL,
	PRIMARY KEY("poblacio_id" AUTOINCREMENT)
);

CREATE TABLE "escoles" (
	"escola_id"	INTEGER NOT NULL,
	"poblacio_id"	INTEGER NOT NULL,
	"nom"	TEXT(50) NOT NULL,
	"aproximacio"	TINYTEXT NOT NULL,
	"vies_qt"	INTEGER NOT NULL,
	"popularitat"	TEXT NOT NULL,
	"restriccio"	DATE,
	PRIMARY KEY("escola_id" AUTOINCREMENT),
	CONSTRAINT "fk_poblacio_id" FOREIGN KEY("poblacio_id") REFERENCES ""
);

CREATE TABLE "sectors" (
	"sector_id"	INTEGER NOT NULL,
	"escola_id"	INTEGER NOT NULL,
	"sector_num"	INTEGER NOT NULL,
	"nom"	TEXT(45) NOT NULL,
	"coordenades"	TEXT(45) NOT NULL,
	"aproximacio"	TINYTEXT NOT NULL,
	"vies_qt"	INTEGER NOT NULL,
	"popularitat"	TEXT NOT NULL,
	"restriccio"	DATE,
	PRIMARY KEY("sector_id" AUTOINCREMENT),
	CONSTRAINT "fk_escola_id" FOREIGN KEY("escola_id") REFERENCES "escoles"
);

CREATE TABLE "vies" (
	"via_id"	INTEGER NOT NULL,
	"sector_id"	INTEGER NOT NULL,
	"ancoratge_id"	INTEGER NOT NULL,
	"tipus_roca_id"	INTEGER NOT NULL,
	"escalador_id"	INTEGER NOT NULL,
	"via_num"	INTEGER NOT NULL,
	"nom"	NUMERIC NOT NULL,
	"orientacio"	TEXT NOT NULL,
	"estat"	TEXT NOT NULL,
	"tipus"	TEXT NOT NULL,
	"llargada"	INTEGER NOT NULL,
	PRIMARY KEY("via_id" AUTOINCREMENT),
	CONSTRAINT "fk_ancoratge_id" FOREIGN KEY("ancoratge_id") REFERENCES "ancoratges"("ancoratge_id"),
	CONSTRAINT "fk_escalador_id" FOREIGN KEY("escalador_id") REFERENCES "escaladors"("escaldor_id"),
	CONSTRAINT "fk_sector_id" FOREIGN KEY("sector_id") REFERENCES "sectors"("sector_id"),
	CONSTRAINT "fk_tipus_roca_id" FOREIGN KEY("tipus_roca_id") REFERENCES ""
);

CREATE TABLE "vies_utilitza_materials" (
	"via_id"	INTEGER NOT NULL,
	"material_id"	INTEGER NOT NULL,
	CONSTRAINT "fk_material_id" FOREIGN KEY("material_id") REFERENCES "",
	CONSTRAINT "fk_via_id" FOREIGN KEY("via_id") REFERENCES "vies"("via_id")
);

CREATE TABLE "trams" (
	"tram_id"	INTEGER NOT NULL,
	"via_id"	INTEGER NOT NULL,
	"dificultat_id"	INTEGER NOT NULL,
	"nom"	TEXT(35) NOT NULL,
	"llargada"	INTEGER NOT NULL,
	PRIMARY KEY("tram_id" AUTOINCREMENT),
	CONSTRAINT "fk_dificultat_id" FOREIGN KEY("dificultat_id") REFERENCES "",
	CONSTRAINT "fk_via_id" FOREIGN KEY("via_id") REFERENCES "vies"("via_id")
);