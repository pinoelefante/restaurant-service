--liquibase formatted sql
--dbms postgresql
--runInTransaction true
--runOnChange true

--changeset pinoelefante:basic schema creation
create schema if not exists reference_data;

create table if not exists reference_data.country
(
    id   int,
    code varchar,
    translation varchar,
    primary key (id)
);
insert into reference_data.country (id, code, translation) VALUES (1, 'it', 'Italia');

create table if not exists reference_data.company_type
(
    id          int,
    name        varchar,
    translation varchar,
    primary key (id),
    unique (name)
);
insert into reference_data.company_type (id, "name", translation) values (1, 'Manufacturer', 'Produttore') on conflict do nothing;
insert into reference_data.company_type (id, "name", translation) values (2, 'Supplier', 'Fornitore') on conflict do nothing;
insert into reference_data.company_type (id, "name", translation) values (3, 'Client', 'Cliente') on conflict do nothing;

create table if not exists reference_data.sale_type
(
    id int,
    name varchar,
    translation varchar,
    primary key (id)
);
insert into reference_data.sale_type (id, name, translation) VALUES (1, 'piece', 'al pezzo');
insert into reference_data.sale_type (id, name, translation) VALUES (2, 'weight', 'al peso');
insert into reference_data.sale_type (id, name, translation) VALUES (3, 'length', 'al metro');

create table if not exists reference_data.measurement_unit
(
    code varchar(2),
    weight boolean,
    primary key (code)
);
insert into reference_data.measurement_unit (code, weight) values ('PZ', false);
insert into reference_data.measurement_unit (code, weight) values ('KG', true);
insert into reference_data.measurement_unit (code, weight) values ('G', true);
insert into reference_data.measurement_unit (code, weight) values ('L', true);
insert into reference_data.measurement_unit (code, weight) values ('ML', true);
insert into reference_data.measurement_unit (code, weight) values ('M', true);
insert into reference_data.measurement_unit (code, weight) values ('CM', true);

create table if not exists reference_data.conversion_factor
(
    code varchar(2),
    base_code varchar(2),
    factor int,
    primary key (code, base_code),
    foreign key (code) references reference_data.measurement_unit(code) on update cascade on delete cascade,
    foreign key (base_code) references reference_data.measurement_unit(code) on update cascade on delete cascade
);
insert into reference_data.conversion_factor values ('KG', 'KG', 1);
insert into reference_data.conversion_factor values ('G', 'KG', 1000);
insert into reference_data.conversion_factor values ('L', 'L', 1);
insert into reference_data.conversion_factor values ('ML', 'L', 1000);
insert into reference_data.conversion_factor values ('M', 'M', 1);
insert into reference_data.conversion_factor values ('CM', 'M', 100);

create table if not exists reference_data.contact_type
(
    id int,
    name varchar,
    translation varchar,
    visible boolean not null default true,
    primary key (id)
);
insert into reference_data.contact_type (id, name, translation) VALUES (1, 'phone', 'Telefono');
insert into reference_data.contact_type (id, name, translation) VALUES (2, 'email', 'Posta elettronica');
insert into reference_data.contact_type (id, name, translation) VALUES (3, 'pec', 'Posta certificata');
insert into reference_data.contact_type (id, name, translation) VALUES (4, 'fax', 'FAX');
insert into reference_data.contact_type (id, name, translation, visible) VALUES (5, 'whatsapp', 'WhatsApp', false);
insert into reference_data.contact_type (id, name, translation, visible) VALUES (6, 'telegram', 'Telegram', false);

create table if not exists reference_data.attribute_type
(
    id int,
    name varchar,
    translation varchar,
    primary key (id)
);
insert into reference_data.attribute_type (id, name, translation) values (1, 'allergen', 'Allergene');
insert into reference_data.attribute_type (id, name, translation) values (2, 'general-attribute', 'Caratteristica');

create table if not exists reference_data.attribute
(
    id serial,
    name varchar,
    translation varchar,
    "type" int,
    symbol varchar,
    primary key (id),
    foreign key (type) references reference_data.attribute_type(id)
);
insert into reference_data.attribute("name", "type", translation) values ('gluten', 1, 'glutine');
insert into reference_data.attribute("name", "type", translation) values ('lactose', 1, 'lattosio');
insert into reference_data.attribute("name", "type", translation) values ('egg', 1, 'uova');
insert into reference_data.attribute("name", "type", translation) values ('shellfish', 1, 'crostacei');
insert into reference_data.attribute("name", "type", translation) values ('fish', 1, 'pesce');
insert into reference_data.attribute("name", "type", translation) values ('peanuts', 1, 'arachidi');
insert into reference_data.attribute("name", "type", translation) values ('soy', 1, 'soia');
insert into reference_data.attribute("name", "type", translation) values ('milk', 1, 'latte');
insert into reference_data.attribute("name", "type", translation) values ('nuts', 1, 'frutta a guscio');
insert into reference_data.attribute("name", "type", translation) values ('celery', 1, 'sedano');
insert into reference_data.attribute("name", "type", translation) values ('mustard', 1, 'senape');
insert into reference_data.attribute("name", "type", translation) values ('sesame', 1, 'sesamo');
insert into reference_data.attribute("name", "type", translation) values ('sulfur dioxide and sulphites', 1, 'anidride solforosa e solfiti');
insert into reference_data.attribute("name", "type", translation) values ('lupins', 1, 'lupini');
insert into reference_data.attribute("name", "type", translation) values ('clams', 1, 'molluschi');

insert into reference_data.attribute("name", "type", translation) values ('bio', 2, 'bio');
insert into reference_data.attribute("name", "type", translation) values ('vegan', 2, 'vegano');
insert into reference_data.attribute("name", "type", translation) values ('vegetarian', 2, 'vegetariano');
insert into reference_data.attribute("name", "type", translation) values ('gluten-free', 2, 'senza glutine');
insert into reference_data.attribute("name", "type", translation) values ('lactose-free', 2, 'senza lattosio');

create schema company;

create table if not exists company.company
(
    id         serial,
    name       varchar,
    vat_number varchar,
    address    varchar,
    country    int,
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (id),
    unique (vat_number),
    foreign key (country) references reference_data.country(id) on update cascade on delete set null
);
insert into company.company (id, name, vat_number) values (0, '', ''); /* Dummy company */

create table if not exists company.type
(
    company_id int,
    "type"     int,
    start_date date not null default current_date,
    enabled    boolean not null default true,
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (company_id, "type", start_date),
    foreign key (company_id) references company.company(id) on update cascade on delete cascade,
    foreign key ("type") references reference_data.company_type(id) on delete cascade on update cascade
);
insert into company.type (company_id, "type", start_date, enabled) values (0, 1, current_date, true);
insert into company.type (company_id, "type", start_date, enabled) values (0, 2, current_date, true);
insert into company.type (company_id, "type", start_date, enabled) values (0, 3, current_date, true);

create table if not exists company.contact
(
    company_id int,
    contact_type int,
    contact varchar,
    additional_note varchar,
    order_contact boolean default false,
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (company_id, contact_type, contact),
    foreign key (company_id) references company.company(id) on update cascade on delete cascade,
    foreign key (contact_type) references reference_data.contact_type(id) on delete cascade on update cascade
);

create schema if not exists article;

create table if not exists article.anagraphic
(
    id           serial,
    name         varchar,
    enabled      boolean      default true,
    sale_type    int,
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (id),
    foreign key (sale_type) references reference_data.sale_type(id) on update cascade on delete set null
);

create table if not exists article.ingredient
(
    id             serial,
    name           varchar,
    origin_country int,
    manufacturer   int not null default 0,
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (id),
    foreign key (origin_country) references reference_data.country(id) on update cascade on delete set null,
    foreign key (manufacturer) references company.company(id) on update cascade on delete set default
);

create table if not exists article.article_attribute
(
    article_id int,
    attribute_id int,
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (article_id, attribute_id),
    foreign key (article_id) references article.anagraphic(id) on update cascade on delete cascade,
    foreign key (attribute_id) references reference_data.attribute on update cascade on delete cascade
);

create table if not exists article.ingredient_attribute
(
    ingredient_id int,
    attribute_id int,
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (ingredient_id, attribute_id),
    foreign key (ingredient_id) references article.ingredient(id) on update cascade on delete cascade,
    foreign key (attribute_id) references reference_data.attribute on update cascade on delete cascade
);

create table if not exists article.article_ingredient
(
    article_id int,
    ingredient_id int,
    "order" int not null default 0,
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (article_id, ingredient_id),
    foreign key (article_id) references article.anagraphic(id) on update cascade on delete cascade,
    foreign key (ingredient_id) references article.ingredient(id) on update cascade on delete cascade
);

create table if not exists article.variant
(
    id serial,
    unit_code varchar(2),
    weight int,
    name varchar,
    translation varchar,
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (id),
    foreign key (unit_code) references reference_data.measurement_unit(code) on delete set null on update cascade
);

create table if not exists article.article_variant
(
    article_id int,
    variant_id int,
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (article_id, variant_id),
    foreign key (article_id) references article.anagraphic(id) on update cascade on delete cascade,
    foreign key (variant_id) references article.variant(id) on update cascade on delete cascade
);

create table if not exists article.article_price
(
    article_id int,
    start_date date,
    price      numeric(11, 2),
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (article_id, start_date),
    foreign key (article_id) references article.anagraphic(id) on update cascade on delete cascade
);

create table if not exists article.article_variant_price
(
    article_id int,
    variant_id int,
    start_date date,
    price      numeric(11, 2),
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (article_id, variant_id, start_date),
    foreign key (article_id) references article.anagraphic(id) on update cascade on delete cascade,
    foreign key (variant_id) references article.variant(id) on update cascade on delete cascade
);

create table if not exists article.ingredient_price
(
    ingredient_id int,
    start_date date,
    price numeric(11, 2),
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (ingredient_id, start_date),
    foreign key (ingredient_id) references article.ingredient(id) on update cascade on delete cascade
);

create schema if not exists menu;

create table if not exists menu.menu
(
    id         serial,
    name       varchar,
    enabled    boolean default false,
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (id)
);

create table if not exists menu.category
(
    id         serial,
    title      varchar not null,
    subtitle   varchar,
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (id)
);

create table if not exists menu.composition
(
    menu_id int,
    category_id int,
    "order" int not null default 0,
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (menu_id, category_id),
    foreign key (menu_id) references menu.menu(id) on update cascade on delete cascade,
    foreign key (category_id) references menu.category(id) on update cascade on delete cascade
);

create table if not exists menu.category_composition
(
    menu_id int,
    category_id int,
    article_id int,
    "order" int not null default 0,
    created_at timestamp not null default now(),
    created_by varchar,
    updated_at timestamp,
    updated_by varchar,
    primary key (menu_id, category_id, article_id),
    foreign key (menu_id) references menu.menu(id) on update cascade on delete cascade,
    foreign key (category_id) references menu.category(id) on update cascade on delete cascade,
    foreign key (article_id) references article.anagraphic(id) on update cascade on delete cascade
);
