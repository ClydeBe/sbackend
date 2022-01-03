create table reviews
(
    id         bigserial not null
        constraint reviews__pk
            primary key,
    comment    text      not null,
    rating     integer   not null,
    reviewer   varchar(255)
        constraint review_reviewer__fk
            references "user",
    self       varchar(255)
        constraint review_user__fk
            references "user",
    updated_at timestamp,
    constraint reviews_unique_self_reviewer_constraint
        unique (self, reviewer)
);

alter table reviews
    owner to khydnudsjmernm;

create sequence s_reviews owned by reviews.id;

create index self_review__idx
    on reviews (self);
