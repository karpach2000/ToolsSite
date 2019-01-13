CREATE TABLE Roles(
	id INTEGER PRIMARY KEY autoincrement,
	role VARCHAR(64)
);

INSERT INTO Roles(id, role) VALUES(1, "ADMIN");
INSERT INTO Roles(id, role) VALUES(2, "USER");

CREATE TABLE Users_roles(
	user_id INTEGER,
	role VARCHAR (255)
);