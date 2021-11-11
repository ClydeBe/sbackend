create table t_comment
(
    id         bigserial not null
        constraint t_comment_pkey
            primary key,
    author_id  varchar(255)
        constraint comment_user__fk
            references t_user,
    body       text,
    post_id    bigint
        constraint comment_post__fk
            references t_post,
    reply_id   bigint
        constraint comment_reply__fk
            references t_comment,
    updated_at timestamp
);

alter table t_comment
    owner to khydnudsjmernm;

create index post__idx
    on t_comment (post_id);