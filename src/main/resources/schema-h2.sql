--DROP SCHEMA IF EXISTS public;

CREATE SCHEMA IF NOT EXISTS public;


CREATE SEQUENCE IF NOT EXISTS public.product_dim_seq 	INCREMENT 1 	MINVALUE 1 	MAXVALUE 9223372036854775807 	START 1 	CACHE 1;

create table public.product_dim
(
	id bigserial not null DEFAULT public.product_dim_seq.nextval,
	name varchar(100) not null,
	price numeric not null,
	constraint product_dim_pkey primary key(id)
)
;



CREATE SEQUENCE IF NOT EXISTS public.customer_dim_seq 	INCREMENT 1 	MINVALUE 1 	MAXVALUE 9223372036854775807 	START 1 	CACHE 1;

create table public.customer_dim
(
	id bigserial not null DEFAULT public.customer_dim_seq.nextval,
	firstname varchar(100) not null,
	lastname varchar(100) not null,
	email varchar(100) not null,
	constraint customer_dim_pkey primary key(id)
)
;

create table public.order_fact
(
	customer_id bigint not null
		constraint fk_order_fact_01
			references customer_dim,
	product_id bigint not null
		constraint fk_order_fact_02
			references product_dim,
	order_time timestamp not null,
	order_number varchar(50) not null,
	order_line_number integer not null,
	order_line_quantity smallint not null,
	total_price_line numeric not null
)
;
