create table haircut
(
    id         bigint not null
        constraint haircut__pk
            primary key,
    duration   int2,
    label      varchar(255),
    photo      text      not null,
    price      real,
    updated_at timestamp,
    vat_ratio  real,
    vendor_id  varchar(255)
        constraint haircut_user__fk
            references "user"
);

create table audit.audit_haircut
(
    id         bigint  not null,
    rev        integer not null
        constraint audit_haircut_rev_info
            references audit.revinfo,
    revtype    smallint,
    duration   int2,
    label      varchar(255),
    photo      text,
    price      real,
    updated_at timestamp,
    vat_ratio  real,
    vendor_id  varchar(255),
    constraint audit_haircut_pkey
        primary key (id, rev)
);

create sequence s_haircut owned by haircut.id;

alter table haircut
    owner to khydnudsjmernm;

alter table audit.audit_haircut
    owner to khydnudsjmernm;
