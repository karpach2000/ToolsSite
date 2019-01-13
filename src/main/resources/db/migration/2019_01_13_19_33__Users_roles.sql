CREATE TABLE Roles(
	id INTEGER PRIMARY KEY autoincrement,
	role VARCHAR(64)
);

INSERT INTO Roles(id, role) VALUES(1, "ROLE_ADMIN");
INSERT INTO Roles(id, role) VALUES(2, "ROLE_USER");

CREATE TABLE Users_roles(
	user_id INTEGER,
	role_id VARCHAR (255)
);