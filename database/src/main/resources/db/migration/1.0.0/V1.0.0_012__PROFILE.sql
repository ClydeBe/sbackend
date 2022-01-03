create table profile
(
    followers_count integer,
    haircut_count   integer,
    rate            integer,
    updated_at      timestamp,
    user_id         varchar(255)
        constraint statistics_user__fk
            references "user"
);

alter table profile
    owner to khydnudsjmernm;

create index user_statistics__idx
    on profile(user_id);
