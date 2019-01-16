create table hallinfo(
	hi_no		number primary key,
	hi_name		varchar2(20) not null unique,
	hi_area		varchar2(20) not null,
	hi_num		number(10) not null,
	hi_genre1	number(10) not null,
	hi_genre2	number(10) not null,
	hi_genre3	number(10) not null,
	hi_genre4	number(10) not null
);

create sequence hallinfo_seq
start with 100
increment by 1;