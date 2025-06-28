CREATE TABLE IF NOT EXISTS users (id serial primary key not null);
CREATE TABLE IF NOT EXISTS user_preferences (
    id serial primary key not null,
    user int references users(id)
);
CREATE TABLE IF NOT EXISTS questions (id serial primary key not null);
CREATE TABLE IF NOT EXISTS answers (id serial primary key not null);
CREATE TABLE IF NOT EXISTS lesson_plans (id serial primary key not null);
CREATE TABLE IF NOT EXISTS lessons (id serial primary key not null);
CREATE TABLE IF NOT EXISTS events (id serial primary key not null);