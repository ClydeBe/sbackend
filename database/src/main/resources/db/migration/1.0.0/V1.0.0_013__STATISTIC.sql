create table statistic
(
    id         bigint not null
        constraint t_statistics__pk
            primary key,
    product_id bigint,
    haircut_id bigint,
    updated_at timestamp,
    profile    varchar(255),
    username   varchar(255)
);

alter table statistic
    owner to khydnudsjmernm;

create sequence s_statistic owned by statistic.id;

create index statistic_username__idx
    on statistic (username);
