DROP TABLE likes;
DROP TABLE friendships;
DROP TABLE film_genre;
DROP TABLE films;
DROP TABLE users;

CREATE TABLE IF NOT EXISTS "users" (
  "user_id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "email" varchar NOT NULL,
  "login" varchar,
  "user_name" varchar,
  "birthday" timestamp
);

CREATE TABLE IF NOT EXISTS "films" (
  "film_id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "film_name" varchar NOT NULL,
  "description" varchar,
  "mpa_rating_id" integer,
  "release_date" timestamp,
  "duration" integer
);

CREATE TABLE IF NOT EXISTS "likes" (
  "like_id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "film_id" integer,
  "user_id" integer
);

CREATE TABLE IF NOT EXISTS "friendships" (
  "friendship_id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "friend_from_id" integer,
  "friend_to_id" integer,
  "is_accept" boolean DEFAULT false
);

CREATE TABLE IF NOT EXISTS "film_genre" (
  "film_genre_id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "film_id" integer,
  "genre_id" integer
);

CREATE TABLE IF NOT EXISTS "genres" (
  "genre_id" integer PRIMARY KEY,
  "genre_name" varchar
);

CREATE TABLE IF NOT EXISTS "mpa_rating" (
  "mpa_rating_id" integer PRIMARY KEY,
  "mpa_rating_code" varchar
);

ALTER TABLE "films" ADD FOREIGN KEY ("mpa_rating_id") REFERENCES "mpa_rating" ("mpa_rating_id");

ALTER TABLE "likes" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("film_id") ON DELETE CASCADE;

ALTER TABLE "likes" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("user_id") ON DELETE CASCADE;

ALTER TABLE "friendships" ADD FOREIGN KEY ("friend_from_id") REFERENCES "users" ("user_id") ON DELETE CASCADE;

ALTER TABLE "friendships" ADD FOREIGN KEY ("friend_to_id") REFERENCES "users" ("user_id") ON DELETE CASCADE;

ALTER TABLE "film_genre" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("film_id") ON DELETE CASCADE;

ALTER TABLE "film_genre" ADD FOREIGN KEY ("genre_id") REFERENCES "genres" ("genre_id");
