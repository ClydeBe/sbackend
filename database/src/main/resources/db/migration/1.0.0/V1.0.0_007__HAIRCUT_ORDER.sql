create table haircut_order
(
    id                 bigint not null
        constraint haircut_order__pk
            primary key,
    client_id          varchar(255)
        constraint haircut_order_client__fk
            references "user",
    haircut_order_date timestamp,
    description        text      not null,
    price              real,
    updated_at         timestamp,
    haircut_id         bigint
        constraint haircut_order_haircut__fk
            references haircut
);

create table audit.audit_haircut_order
(
    id                 bigint  not null,
    rev                integer not null
        constraint audit_haircut_order_rev_info
            references audit.revinfo,
    revtype            smallint,
    client_id          varchar(255),
    haircut_order_date timestamp,
    description        text,
    price              real,
    updated_at         timestamp,
    haircut_id         bigint,
    constraint audit_haircut_order_pkey
        primary key (id, rev)
);

create sequence s_haircut_order owned by haircut_order.id;

alter table haircut_order
    owner to khydnudsjmernm;

alter table audit.audit_haircut_order
    owner to khydnudsjmernm;
