create table product_order
(
    id         bigint not null
        constraint product_order__pk
            primary key,
    items      text   not null,
    updated_at timestamp,
    user_id    varchar(255)
        constraint product_order_user__fk
            references "user"
);

create table audit.audit_product_order
(
    id         bigint  not null,
    rev        integer not null
        constraint audit_product_order_rev_info
            references audit.revinfo,
    revtype    smallint,
    items      text,
    updated_at timestamp,
    user_id    varchar(255),
    constraint audit_product_order_pkey
        primary key (id, rev)
);


alter table product_order
    owner to khydnudsjmernm;

create sequence s_product_order owned by product_order.id;

create index user_product_order__idx
    on product_order (user_id);

alter table audit.audit_product_order
    owner to khydnudsjmernm;
