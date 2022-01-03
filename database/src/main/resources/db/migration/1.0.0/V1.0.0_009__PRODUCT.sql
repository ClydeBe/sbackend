create table product
(
    id              bigint not null
        constraint product__pk
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
        constraint product_vendor__fk
            references "user"
);

create table audit.audit_product
(
    id              bigint  not null,
    rev             integer not null
        constraint audit_product_rev_info
            references audit.revinfo,
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

alter table product
    owner to khydnudsjmernm;

alter table audit.audit_product
    owner to khydnudsjmernm;

create sequence s_product owned by product.id;

create index product_price__idx
    on product (price);

alter table product
    add column text_search tsvector;

create index product_text_search__idx
    on product using gin (text_search);

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
    ON product
    FOR EACH ROW
EXECUTE PROCEDURE product_search_trigger();