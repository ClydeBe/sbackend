create table t_product_order
(
    id         bigserial not null
        constraint t_product_order_pkey
            primary key,
    items      text      not null,
    updated_at timestamp,
    user_id    varchar(255)
        constraint product_order_user__fk
            references t_user
);

create table audit_product_order
(
    id         bigint  not null,
    rev        integer not null
        constraint audit_product_order_rev_info
            references revinfo,
    revtype    smallint,
    items      text,
    updated_at timestamp,
    user_id    varchar(255),
    constraint audit_product_order_pkey
        primary key (id, rev)
);


alter table t_product_order
    owner to khydnudsjmernm;

create index user_product_order__idx
    on t_product_order (user_id);

alter table audit_product_order
    owner to khydnudsjmernm;
