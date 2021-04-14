create table mario(
seq number primary key,
clientaccount varchar2(30),
password varchar2(15),
realname varchar2(15),
age number,
nickname varchar2(30),
gender number,
infoagree number,
score number,
goaltime varchar2(24),
playerrank number);

create sequence seq_mario nocycle nocache;

select seq_mario.nextval from dual;

select * from mario;

drop table mario;

drop sequence seq_mario;

select * from tab;

-- Çà Ãß°¡, »èÁ¦, º¯°æ ¸í·É¾î
delete mario where seq = 6;

update mario set clientaccount = ?;

insert into mario values(?,?,?,?,?,?,?,?,?,?,?);

commit;
