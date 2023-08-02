create table pessoa
(
    id         uuid default gen_random_uuid() primary key,
    apelido    varchar(255) not null unique,
    nome       varchar(255) not null,
    nascimento date         not null,
    stack      text
);