create table Boards
(
	id INTEGER
		primary key autoincrement,
	devId TEXT
		unique,
	userId INTEGER,
	boardRsName VARCHAR (255),
	boardType INTEGER,
	description VARCHAR (255),
	deleted SMALLINT
);

create table DigitalInPins
(
	id INTEGER
		primary key autoincrement,
	boardId INTEGER,
	pinNumber INTEGER,
	value INTEGER,
	description VARCHAR (255),
	deleted SMALLINT
);

create table DigitalOutPins
(
	id INTEGER
		primary key autoincrement,
	boardId INTEGER,
	pinNumber INTEGER,
	value SMALLINT,
	description VARCHAR(255),
	deleted SMALLINT
);

create table Users
(
	id INTEGER
		primary key autoincrement,
	login VARCHAR(64)
		unique,
	pas VARCHAR (255),
	deleted SMALLINT
);

