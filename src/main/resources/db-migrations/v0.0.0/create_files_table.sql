create table if not exists files
(
    id         uuid default gen_random_uuid() primary key,
    user_id    uuid      not null,
    uri        varchar   not null,
    created_at timestamp not null,
    update_at  timestamp not null
);
