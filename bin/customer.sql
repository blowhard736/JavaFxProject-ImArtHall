create table customer(
	cus_type		varchar2(6) not null,
	cus_comname 	varchar2(50) primary key,
	cus_num			varchar2(20),
	cus_ceoname		varchar2(20),
	cus_name		varchar2(20) not null,
	cus_addr		varchar2(100) not null,
	cus_phone		varchar2(20) not null,
	cus_email		varchar2(50) not null,
	cus_genre		varchar2(8) not null,
	cus_title		varchar2(50) not null,
	cus_price		number(10) not null,
	cus_starttime 	varchar2(20) not null,
	cus_endtime		varchar2(20) not null,
	no				number
);

create sequence customer_seq
start with 1
increment by 1;