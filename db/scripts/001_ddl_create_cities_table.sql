create table cities
(
    id   serial primary key,
    name varchar not null unique
);

insert into cities(name) values('Москва');
insert into cities(name) values('Санкт-Петербург');
insert into cities(name) values('Екатеринбург');

create table files
(
    id   serial primary key,
    name varchar not null,
    path varchar not null unique
);

create table vacancies
(
    id            serial primary key,
    title         varchar not null,
    description   varchar not null,
    creation_date timestamp,
    visible       boolean not null,
    city_id       int references cities(id),
    file_id       int references files(id)
);