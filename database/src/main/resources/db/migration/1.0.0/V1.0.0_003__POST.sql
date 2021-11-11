create table t_post
(
    id         bigserial not null
        constraint t_post_pkey
            primary key,
    author_id  varchar(255)
        constraint post_user__fk
            references t_user,
    body       text,
    cover      varchar(255),
    tags       varchar(255),
    title      varchar(255),
    updated_at timestamp
);

create table audit_post
(
    id         bigint  not null,
    rev        integer not null
        constraint audit_post_rev_info
            references revinfo,
    revtype    smallint,
    author_id  varchar(255),
    body       text,
    cover      varchar(255),
    tags       varchar(255),
    title      varchar(255),
    updated_at timestamp,
    constraint audit_post_pkey
        primary key (id, rev)
);

create index post_author__idx
    on t_post (author_id);

alter table t_post
    owner to khydnudsjmernm;

alter table audit_post
    owner to khydnudsjmernm;
