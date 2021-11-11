create table t_statistics
(
    id              bigserial not null
        constraint t_statistics_pkey
            primary key,
    followers_count integer,
    haircut_count   integer,
    rate            integer,
    updated_at      timestamp,
    user_id         varchar(255)
        constraint statistics_user__fk
            references t_user
);

alter table t_statistics
    owner to khydnudsjmernm;

create index user_statistics__idx
    on t_statistics (user_id);
