create table availability
(
    id             bigint not null,
    availabilities text,
    updated_at     timestamp,
    user_id        varchar(255)
        constraint availability_user__fk
            references "user",
    constraint availability__pk
        primary key (user_id, id)
);

create table audit.audit_availability
(
    id             bigint  not null,
    rev            integer not null
        constraint audit_availability_rev_info
            references audit.revinfo,
    revtype        smallint,
    availabilities text,
    updated_at     timestamp,
    user_id        varchar(255),
    constraint audit_availability_pkey
        primary key (id, rev)
);

create sequence s_availability owned by availability.id;

create index user_availability__idx
    on availability (user_id);

alter table availability
    owner to khydnudsjmernm;

alter table audit.audit_availability
    owner to khydnudsjmernm;
