create table t_haircut
(
    id         bigserial not null
        constraint t_haircut_pkey
            primary key,
    duration   bigint,
    label      varchar(255),
    photo      text      not null,
    price      real,
    updated_at timestamp,
    vat_ratio  real,
    vendor_id  varchar(255)
        constraint haircut_user__fk
            references t_user
);

create table audit_haircut
(
    id         bigint  not null,
    rev        integer not null
        constraint audit_haircut_rev_info
            references revinfo,
    revtype    smallint,
    duration   bigint,
    label      varchar(255),
    photo      text,
    price      real,
    updated_at timestamp,
    vat_ratio  real,
    vendor_id  varchar(255),
    constraint audit_haircut_pkey
        primary key (id, rev)
);

alter table t_haircut
    owner to khydnudsjmernm;

alter table audit_haircut
    owner to khydnudsjmernm;
