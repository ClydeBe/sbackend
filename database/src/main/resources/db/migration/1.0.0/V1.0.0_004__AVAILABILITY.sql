create table t_avaibility
(
    id             bigserial not null
        constraint t_avaibility_pkey
            primary key,
    availabilities text,
    updated_at     timestamp,
    user_id        varchar(255)
        constraint availability_user__fk
            references t_user
);

create table audit_availability
(
    id             bigint  not null,
    rev            integer not null
        constraint audit_availability_rev_info
            references revinfo,
    revtype        smallint,
    availabilities text,
    updated_at     timestamp,
    user_id        varchar(255),
    constraint audit_availability_pkey
        primary key (id, rev)
);


create index user_availability__idx
    on t_avaibility (user_id);

alter table t_avaibility
    owner to khydnudsjmernm;

alter table audit_availability
    owner to khydnudsjmernm;
