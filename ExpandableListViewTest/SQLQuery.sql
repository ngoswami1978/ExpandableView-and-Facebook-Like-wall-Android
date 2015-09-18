CREATE TABLE T_MS_POST_TITLE (id INTEGER PRIMARY KEY IDENTITY (1,1),title VARCHAR(2000))

CREATE TABLE T_DTLS_POST_COMMENTS (id INTEGER PRIMARY KEY IDENTITY (1,1),_Titleid INTEGER ,Username VARCHAR(50),Postcomment TEXT)

--INSERT INTO T_MS_POST_TITLE(title) VALUES ('Maintainance?')
--INSERT INTO T_MS_POST_TITLE(title) VALUES ('Parking?')

SELECT * FROM T_MS_POST_TITLE

INSERT INTO T_DTLS_POST_COMMENTS (_Titleid,Username,Postcomment) VALUES (1,'Neeraj Goswami','hey guys ! pls let me know what is the maintainance cost per month.')
INSERT INTO T_DTLS_POST_COMMENTS (_Titleid,Username,Postcomment) VALUES (1,'Neeraj Goswami','1K for 1 BHK')
INSERT INTO T_DTLS_POST_COMMENTS (_Titleid,Username,Postcomment) VALUES (1,'Neeraj Goswami','1.5K for 2 BHK')

INSERT INTO T_DTLS_POST_COMMENTS (_Titleid,Username,Postcomment) VALUES (2,'Neeraj Goswami','hi! please clear the bikes from ground floor')
INSERT INTO T_DTLS_POST_COMMENTS (_Titleid,Username,Postcomment) VALUES (2,'Neeraj Goswami','hi ! can anyone let me know who is th owner of white alto?')

SELECT * FROM T_DTLS_POST_COMMENTS

SELECT title,Postcomment FROM T_DTLS_POST_COMMENTS INNER JOIN T_MS_POST_TITLE ON T_DTLS_POST_COMMENTS._Titleid = T_MS_POST_TITLE.id order by _Titleid 
