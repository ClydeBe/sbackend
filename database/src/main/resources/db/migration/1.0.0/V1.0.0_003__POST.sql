create table public.post
(
    id         bigint not null
        constraint post__pk
            primary key,
    author_id  varchar(255)
        constraint post_user__fk
            references "user",
    body       text,
    cover      varchar(255),
    tags       varchar(255),
    title      varchar(255),
    updated_at timestamp
);

create table audit.audit_post
(
    id         bigint  not null,
    rev        integer not null
        constraint audit_post_rev_info
            references audit.revinfo,
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
    on post (author_id);

create sequence s_post owned by post.id;

alter table post
    owner to khydnudsjmernm;

alter table audit.audit_post
    owner to khydnudsjmernm;
