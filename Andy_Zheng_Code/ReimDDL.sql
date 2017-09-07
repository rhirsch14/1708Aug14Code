
DROP TABLE REIMBURSEMENTS;
DROP TABLE REIMSTATUS;
DROP TABLE USERS;

ALTER SESSION SET NLS_DATE_FORMAT='DD-MON-YYYY HH24:MI:SS';

CREATE TABLE USERS
(
    USERID NUMBER NOT NULL,
    FIRSTNAME VARCHAR2(30) NOT NULL,
    LASTNAME VARCHAR2(30) NOT NULL,
    EMAIL VARCHAR2(50) NOT NULL UNIQUE,
    PASSWORD VARCHAR2(30) NOT NULL,
    ISMANAGER NUMBER(1) NOT NULL,
    CONSTRAINT USERID PRIMARY KEY(USERID)
);


CREATE TABLE REIMSTATUS
(
    STATUSID NUMBER NOT NULL,
    STATUSNAME VARCHAR(30) NOT NULL,
    CONSTRAINT STATUSID PRIMARY KEY(STATUSID)
);

CREATE TABLE REIMBURSEMENTS
(
    REIMID NUMBER NOT NULL,
    SUBMITTERID NUMBER NOT NULL,
    RESOLVERID NUMBER,
    SUBMITDATE TIMESTAMP DEFAULT TRUNC(CURRENT_TIMESTAMP, 'MI'),
    RESOLVEDATE TIMESTAMP(0),
    STATUSID NUMBER DEFAULT 0,
    DESCRIPTION VARCHAR2(100),
    NOTES VARCHAR2(100),
    AMOUNT NUMBER(12,2) ,
    CONSTRAINT REIMID PRIMARY KEY(REIMID),
    CONSTRAINT FK_SUBMITTERID FOREIGN KEY(SUBMITTERID) REFERENCES USERS(USERID),
    CONSTRAINT FK_RESOLVERID FOREIGN KEY(RESOLVERID) REFERENCES USERS(USERID),
    CONSTRAINT FK_STATUSID FOREIGN KEY(STATUSID) REFERENCES REIMSTATUS(STATUSID),
    CONSTRAINT AMOUNT CHECK (AMOUNT > 0)
);


-- Triggers and Sequences

CREATE SEQUENCE USERID_SEQ
INCREMENT BY 1
START WITH 1;
/
CREATE OR REPLACE TRIGGER USERID_TRIGGER
BEFORE INSERT ON USERS
FOR EACH ROW
BEGIN
    IF:NEW.USERID IS NULL THEN
    SELECT USERID_SEQ.NEXTVAL INTO :NEW.USERID FROM DUAL;
    END IF;
END;
/

CREATE SEQUENCE REIMID_SEQ
INCREMENT BY 1
START WITH 1;
/
CREATE OR REPLACE TRIGGER REIMID_SEQ
BEFORE INSERT ON REIMBURSEMENTS
FOR EACH ROW
BEGIN
    IF:NEW.REIMID IS NULL THEN
    SELECT REIMID_SEQ.NEXTVAL INTO :NEW.REIMID FROM DUAL;
    END IF;
END;
/

INSERT INTO REIMSTATUS
VALUES(0,'Pending');
INSERT INTO REIMSTATUS
VALUES(1,'Approved');
INSERT INTO REIMSTATUS
VALUES(2,'Denied');



UPDATE REIMBURSEMENTS SET RESOLVERID = 181, RESOLVEDATE = CURRENT_TIMESTAMP, STATUSID = 2, NOTES = 'updated'
WHERE REIMID = 141;

CREATE OR REPLACE PROCEDURE getUserInfo(inUSERID IN NUMBER, outFN OUT VARCHAR2, outLN OUT VARCHAR2, outEMAIL OUT VARCHAR2, outISM OUT NUMBER)
AS
BEGIN
    SELECT FIRSTNAME, LASTNAME, EMAIL, ISMANAGER
    INTO outFN, outLN, outEMAIL, outISM
    FROM USERS
    WHERE USERID = inUSERID;
END;
/


DELETE FROM REIMBURSEMENTS WHERE SUBMITTERID = 181;
DELETE FROM USERS WHERE FIRSTNAME = 'A';

INSERT INTO USERS(FIRSTNAME, LASTNAME, EMAIL, PASSWORD, ISMANAGER)
VALUES('A','A','A','A',0);

INSERT INTO REIMBURSEMENTS(SUBMITTERID, DESCRIPTION, AMOUNT)
VALUES(181,'TEST','100');

INSERT INTO USERS(FIRSTNAME, LASTNAME, EMAIL, PASSWORD, ISMANAGER)
VALUES('Andy','Zheng','az@email.com','az',0);
INSERT INTO USERS(FIRSTNAME, LASTNAME, EMAIL, PASSWORD, ISMANAGER)
VALUES('John','Lee','jl@email.com','jl',0);
INSERT INTO USERS(FIRSTNAME, LASTNAME, EMAIL, PASSWORD, ISMANAGER)
VALUES('Jackson','Road','jr@email.com','jr',0);
INSERT INTO USERS(FIRSTNAME, LASTNAME, EMAIL, PASSWORD, ISMANAGER)
VALUES('Amy','Lewis','al@email.com','al',0);
INSERT INTO USERS(FIRSTNAME, LASTNAME, EMAIL, PASSWORD, ISMANAGER)
VALUES('Jessica','Yuri','jy@email.com','jy',0);
INSERT INTO USERS(FIRSTNAME, LASTNAME, EMAIL, PASSWORD, ISMANAGER)
VALUES('Don','Don','dd@email.com','dd',0);

INSERT INTO USERS(FIRSTNAME, LASTNAME, EMAIL, PASSWORD, ISMANAGER)
VALUES('Caleb','Rum','cr@email.com','cr',1);



