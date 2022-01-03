create table comment
(
    id         bigint not null
        constraint comment_pkey
            primary key,
    author_id  varchar(255)
        constraint comment_user__fk
            references "user",
    body       text,
    post_id    bigint
        constraint comment_post__fk
            references post,
    reply_id   bigint
        constraint comment_reply__fk
            references comment,
    updated_at timestamp
);

create sequence s_comment owned by comment.id;

alter table comment
    owner to khydnudsjmernm;

create index post__idx
    on comment (post_id);