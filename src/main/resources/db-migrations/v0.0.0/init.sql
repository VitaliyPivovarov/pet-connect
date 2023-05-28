create table if not exists users
(
    id                 uuid default gen_random_uuid() primary key,
    avatar_uri         varchar,
    email              varchar unique not null,
    password           varchar        not null,
    name               varchar        not null,
    surname            varchar        not null,
    address            varchar,
    post_code          varchar,
    country            varchar        not null,
    city               varchar        not null,
    communication_type varchar,
    created_at         timestamp      not null,
    update_at          timestamp      not null
);

create table if not exists pets
(
    id            uuid default gen_random_uuid() primary key,
    avatar_uri    varchar,
    type          varchar   not null,
    name          varchar   not null,
    breed         varchar   not null,
    sex           varchar   not null,
    date_of_birth timestamp not null,
    coat          varchar,
    coat_variety  varchar,
    created_at    timestamp not null,
    update_at     timestamp not null
);

create table if not exists user_pets
(
    user_id    uuid      not null REFERENCES users ("id") ON DELETE CASCADE,
    pet_id     uuid      not null REFERENCES pets ("id") ON DELETE CASCADE,
    created_at timestamp not null,
    update_at  timestamp not null,
    PRIMARY KEY (pet_id, user_id)
);

create table if not exists identifications
(
    id                uuid default gen_random_uuid() primary key,
    pet_id            uuid      not null REFERENCES pets ("id") ON DELETE CASCADE,
    number            varchar   not null,
    micro_chipped_at  timestamp not null,
    location          varchar   not null,
    tattoo_number     varchar,
    tattooing_at      timestamp,
    distinctive_mark  varchar,
    reproduction_data varchar   not null,
    created_at        timestamp not null,
    update_at         timestamp not null
);

create table if not exists vaccinations
(
    id                          uuid default gen_random_uuid() primary key,
    pet_id                      uuid      not null REFERENCES pets ("id") ON DELETE CASCADE,
    type                        varchar   not null,
    name                        varchar   not null,
    manufacturer                varchar   not null,
    batch_number                varchar   not null,
    manufactured_at             timestamp not null,
    best_before_at              timestamp not null,
    vaccination_at              timestamp not null,
    vaccination_until_before_at timestamp not null,
    authorised_veterinarian     varchar   not null,
    created_at                  timestamp not null,
    update_at                   timestamp not null
);

create table if not exists clinical_examinations
(
    id                      uuid default gen_random_uuid() primary key,
    pet_id                  uuid      not null REFERENCES pets ("id") ON DELETE CASCADE,
    assessment_report       varchar   not null,
    authorised_veterinarian varchar   not null,
    created_at              timestamp not null,
    update_at               timestamp not null
);

create table if not exists notes
(
    id         uuid default gen_random_uuid() primary key,
    pet_id     uuid      not null REFERENCES pets ("id") ON DELETE CASCADE,
    topic      varchar   not null,
    value      varchar   not null,
    created_at timestamp not null,
    update_at  timestamp not null
);

create table if not exists chats
(
    id            uuid default gen_random_uuid() primary key,
    owner_user_id uuid      not null,
    name          varchar   not null,
    created_at    timestamp not null,
    update_at     timestamp not null
);

create table if not exists chat_messages
(
    id            uuid default gen_random_uuid() primary key,
    owner_user_id uuid      not null,
    chat_id       uuid      not null,
    value         varchar   not null,
    created_at    timestamp not null,
    update_at     timestamp not null
);