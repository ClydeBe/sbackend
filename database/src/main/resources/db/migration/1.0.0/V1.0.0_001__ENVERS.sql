-- create sequence hibernate_sequence;
--
-- alter sequence hibernate_sequence owner to khydnudsjmernm;

create table audit.revinfo
(
    rev integer not null
        constraint revinfo_pkey
            primary key,
    revtstmp bigint
);

alter table audit.revinfo owner to khydnudsjmernm;