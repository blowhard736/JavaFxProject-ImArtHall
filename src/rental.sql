create table rental (
	r_no 			number 			primary key,
	hi_no 			number 			references hallinfo(hi_no),
    cus_no          number          not null,
	cus_comname 	varchar2(50) 	references customer(cus_comname),
	r_date 			date 			not null,
	r_type 			varchar2(8) 	not null,
	r_genre			varchar2(30) 	not null,
	r_morning 		varchar2(4) 	not null,
	r_noon			varchar2(4) 	not null,
	r_evening 		varchar2(4) 	not null,
	r_count 		number(2) 		not null,
	r_equipprice 	number(10),
	r_rentprice 	number(10),
	r_contractp 	number(10),
	r_default 		varchar2(8),
	r_extratime 	number(4),
	r_totalprice 	number(10),
	r_regitdate 	date,
	r_editdate 		date
);

create sequence rental_seq
start with 1
increment by 1;