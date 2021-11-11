create table t_user
(
    username   varchar(255)          not null
        constraint t_user_pkey
            primary key,
    city       varchar(255),
    country    varchar(255),
    number     varchar(255),
    street     varchar(255),
    zipcode    integer,
    email      varchar(255)          not null
        constraint unique_email__constraint
            unique,
    firstname  varchar(255),
    is_active  boolean default false not null,
    lastname   varchar(255),
    password   varchar(255)          not null,
    photo      varchar(255),
    role       varchar(255),
    status     varchar(255),
    updated_at timestamp
);

create table audit_user
(
    username varchar(255) not null,
    rev      integer      not null
        constraint audit_user_rev_info
            references revinfo,
    revtype  smallint,
    role     varchar(255),
    constraint audit_user_pkey
        primary key (username, rev)
);

alter table t_user
    owner to khydnudsjmernm;

alter table audit_user
    owner to khydnudsjmernm;