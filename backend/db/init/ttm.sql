CREATE TABLE USERS (
	ID integer primary key not null AUTO_INCREMENT,
	USERNAME varchar(255),
	EMAIL varchar(255),
	PASSWORD varchar(255),
	CREATED_AT timestamp,
	UPDATED_AT timestamp
);

CREATE TABLE PROJECTS (
	ID integer primary key not null AUTO_INCREMENT,
	NAME varchar(255),
	DESCRIPTION text,
	OWNER_ID integer,
	CONSTRAINT ownerid_fk FOREIGN KEY (OWNER_ID) REFERENCES USERS(ID),
	CREATED_AT timestamp,
	UPDATED_AT timestamp
);

CREATE TABLE TASKS (
	ID integer primary key not null AUTO_INCREMENT,
	TITLE varchar(255),
	DESCRIPTION text,
	STATUS varchar(50),
	PRIORITY integer,
	DUE_DATE timestamp,
	PROJECT_ID integer,
	CONSTRAINT projectid_fk FOREIGN KEY (PROJECT_ID) REFERENCES PROJECTS(ID),
	CREATED_BY integer,
	CONSTRAINT createdby_fk FOREIGN KEY (CREATED_BY) REFERENCES USERS(ID),
	CREATED_AT timestamp,
	UPDATED_AT timestamp
);

CREATE TABLE TASK_ASSIGNMENT (
	ID integer primary key not null AUTO_INCREMENT,
	TASK_ID integer,
	CONSTRAINT taskid_fk FOREIGN KEY (TASK_ID) REFERENCES TASKS(ID),
	USER_ID integer,
	CONSTRAINT userid_fk FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
);