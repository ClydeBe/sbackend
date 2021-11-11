create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to khydnudsjmernm;

create table revinfo
(
    rev integer not null
        constraint revinfo_pkey
            primary key,
    revtstmp bigint
);

alter table revinfo owner to khydnudsjmernm;