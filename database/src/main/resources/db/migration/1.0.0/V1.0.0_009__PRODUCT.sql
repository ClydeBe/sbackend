create table t_product
(
    id              bigserial not null
        constraint t_product_pkey
            primary key,
    characteristics text,
    description     text,
    image           varchar(255),
    label           varchar(255),
    price           real,
    quantity        int4      not null,
    updated_at      timestamp,
    usage           varchar(255),
    vendor_id       varchar(255)
        constraint fk47nrt3pcg8o3j5ks01o6ek6ai
            references t_user
);

create table audit_product
(
    id              bigint  not null,
    rev             integer not null
        constraint audit_product_rev_info
            references revinfo,
    revtype         smallint,
    characteristics text,
    description     text,
    image           varchar(255),
    label           varchar(255),
    price           real,
    quantity        int4,
    updated_at      timestamp,
    usage           varchar(255),
    vendor_id       varchar(255),
    constraint audit_product_pkey
        primary key (id, rev)
);

alter table t_product
    owner to khydnudsjmernm;

alter table audit_product
    owner to khydnudsjmernm;

create index product_price__idx
    on t_product (price);

alter table t_product
    add column text_search tsvector;

create index product_text_search__idx
    on t_product using gin (text_search);

CREATE OR REPLACE FUNCTION product_search_trigger()
    RETURNS trigger
    LANGUAGE plpgsql AS
$$
begin
    new.text_search := to_tsvector('french', coalesce(new.label, '')
                                                 || ' ' || coalesce(new.description, '')
                                                 || ' ' || coalesce(new.usage, '') || ' '
                                                 || coalesce(new.characteristics, '') || ' '
        || coalesce(new.vendor_id, ''));
    return new;
end
$$;

CREATE TRIGGER search_text_trigger
    BEFORE INSERT OR UPDATE
    ON t_product
    FOR EACH ROW
EXECUTE PROCEDURE product_search_trigger();