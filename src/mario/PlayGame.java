package mario;

import mario.Frame.MarioClient;
import mario.Frame.MarioLogin;
import mario.Main.MarioClient_old;

public class PlayGame {

	public static void main(String[] args) {
		new ImageBox();
//		new MarioClient_old();
//		new MarioClient();
		new MarioLogin();
	}
}

/*


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

*/
