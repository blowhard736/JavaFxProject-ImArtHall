create table equipment(
	eq_no		number primary key,
	eq_type		varchar2(10) not null,
	eq_name		varchar2(50) unique,
	eq_num		number(20) not null,
	eq_price	number(10) not null,
	eq_rentnum  number(10) not null
);

create sequence equipment_seq
start with 1
increment by 1;