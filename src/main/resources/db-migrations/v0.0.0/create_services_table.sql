create table if not exists user_services
(
    id           uuid      not null default gen_random_uuid() primary key,
    user_id      uuid      not null references users ("id"),
    pet_type     varchar   not null,
    service_type varchar   not null,
    departure    bool      not null,
    message      varchar,
    created_at   timestamp not null,
    update_at    timestamp not null
);

create table if not exists user_service_requests
(
    id              uuid      not null default gen_random_uuid() primary key,
    user_service_id uuid      not null references user_services ("id"),
    user_id         uuid      not null references users ("id"),
    created_at      timestamp not null,
    update_at       timestamp not null
);

