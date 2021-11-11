create table t_appointment
(
    id                bigserial not null
        constraint t_appointment_pkey
            primary key,
    client_id         varchar(255)
        constraint appointment_client__fk
            references t_user,
    date_time         timestamp,
    is_vendor_address boolean,
    updated_at        timestamp,
    vendor_id         varchar(255)
        constraint appointment_vendor__fk
            references t_user,
    haircut_id        bigint
        constraint appointment_haircut__fk
            references t_haircut,
    haircut_order_id  bigint
        constraint appointment_haircut_order__fk
            references t_haircut_order
);

create table audit_appointment
(
    id bigint not null,
    rev integer not null
        constraint audit_appointment_rev_info
            references revinfo,
    revtype smallint,
    client_id varchar(255),
    date_time timestamp,
    updated_at timestamp,
    vendor_id varchar(255),
    haircut_id bigint,
    haircut_order_id bigint,
    constraint audit_appointment_pkey
        primary key (id, rev)
);

alter table t_appointment
    owner to khydnudsjmernm;

create index client_appointment__idx
    on t_appointment (client_id);

create index vendor_appointment__idx
    on t_appointment (vendor_id);

create index client_haircut_order_id__idx
    on t_haircut_order (client_id);

alter table audit_appointment
    owner to khydnudsjmernm;
