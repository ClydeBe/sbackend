create table t_haircut_order
(
    id                 bigserial not null
        constraint t_haircut_order_pkey
            primary key,
    client_id          varchar(255)
        constraint haircut_order_client__fk
            references t_user,
    haircut_order_date timestamp,
    description        text      not null,
    price              real,
    updated_at         timestamp,
    haircut_id         bigint
        constraint haircut_order_haircut__fk
            references t_haircut
);

create table audit_haircut_order
(
    id                 bigint  not null,
    rev                integer not null
        constraint audit_haircut_order_rev_info
            references revinfo,
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

alter table t_haircut_order
    owner to khydnudsjmernm;

alter table audit_haircut_order
    owner to khydnudsjmernm;
