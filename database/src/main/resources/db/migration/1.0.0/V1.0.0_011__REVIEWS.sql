create table t_reviews
(
    id         bigserial not null
        constraint t_reviews_pkey
            primary key,
    comment    text      not null,
    rating     integer   not null,
    reviewer   varchar(255)
        constraint review_reviewer__fk
            references t_user,
    self       varchar(255)
        constraint review_user__fk
            references t_user,
    updated_at timestamp
);

alter table t_reviews
    owner to khydnudsjmernm;

create index self_review__idx
    on t_reviews (self);
