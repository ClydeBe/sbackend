create table appointment
(
    id                bigint not null
        constraint appointment__pk
            primary key,
    client_id         varchar(255)
        constraint appointment_client__fk
            references "user",
    date_time         timestamp,
    is_vendor_address boolean,
    updated_at        timestamp,
    vendor_id         varchar(255)
        constraint appointment_vendor__fk
            references "user",
    haircut_id        bigint
        constraint appointment_haircut__fk
            references haircut,
    haircut_order_id  bigint
        constraint appointment_haircut_order__fk
            references haircut_order
);

create table audit.audit_appointment
(
    id bigint not null,
    rev integer not null
        constraint audit_appointment_rev_info
            references audit.revinfo,
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

alter table appointment
    owner to khydnudsjmernm;

create sequence s_appointment owned by appointment.id;

create index client_appointment__idx
    on appointment (client_id);

create index vendor_appointment__idx
    on appointment (vendor_id);

create index client_haircut_order_id__idx
    on haircut_order (client_id);

alter table audit.audit_appointment
    owner to khydnudsjmernm;
