MERGE INTO genres (genre_id, genre_name) KEY (genre_id) VALUES (1, 'Комедия');
MERGE INTO genres (genre_id, genre_name) KEY (genre_id) VALUES (2, 'Драма');
MERGE INTO genres (genre_id, genre_name) KEY (genre_id) VALUES (3, 'Мультфильм');
MERGE INTO genres (genre_id, genre_name) KEY (genre_id) VALUES (4, 'Триллер');
MERGE INTO genres (genre_id, genre_name) KEY (genre_id) VALUES (5, 'Документальный');
MERGE INTO genres (genre_id, genre_name) KEY (genre_id) VALUES (6, 'Боевик');

MERGE INTO mpa_rating (mpa_rating_id, mpa_rating_code) KEY (mpa_rating_id) VALUES (1, 'G');
MERGE INTO mpa_rating (mpa_rating_id, mpa_rating_code) KEY (mpa_rating_id) VALUES (2, 'PG');
MERGE INTO mpa_rating (mpa_rating_id, mpa_rating_code) KEY (mpa_rating_id) VALUES (3, 'PG-13');
MERGE INTO mpa_rating (mpa_rating_id, mpa_rating_code) KEY (mpa_rating_id) VALUES (4, 'R');
MERGE INTO mpa_rating (mpa_rating_id, mpa_rating_code) KEY (mpa_rating_id) VALUES (5, 'NC-17');

--MERGE INTO users (user_id, email, login, user_name, birthday) KEY (user_id) VALUES (1, 'vasya@yandex.ru', 'vasya', 'vasya', '2000-01-23');
--MERGE INTO users (user_id, email, login, user_name, birthday) KEY (user_id) VALUES (2, 'kolya@yandex.ru', 'kolya', 'kolya', '1980-03-13');
--MERGE INTO users (user_id, email, login, user_name, birthday) KEY (user_id) VALUES (3, 'julia@yandex.ru', 'julia', null, '1984-11-08');
--MERGE INTO users (user_id, email, login, user_name, birthday) KEY (user_id) VALUES (4, 'misha@yandex.ru', 'misha', null, '1990-02-05');
--MERGE INTO users (user_id, email, login, user_name, birthday) KEY (user_id) VALUES (5, 'olga@yandex.ru', 'olga', 'olga', '1995-07-04');
--MERGE INTO users (user_id, email, login, user_name, birthday) KEY (user_id) VALUES (6, 'andrey@yandex.ru', 'andrey', 'andrey', '1965-10-14');

--MERGE INTO films (film_id, film_name, description, mpa_rating_id, release_date,
--                    duration) KEY (film_id) VALUES (1, 'Курочка ряба', 'детская сказка', 1, '1950-03-25', 20);
--MERGE INTO films (film_id, film_name, description, mpa_rating_id, release_date,
--                    duration) KEY (film_id) VALUES (2, 'Дом в огне', 'непонятно', 4, '1999-10-01', 90);
--MERGE INTO films (film_id, film_name, description, mpa_rating_id, release_date,
--                    duration) KEY (film_id) VALUES (3, 'Оно', 'не смотрел', 5, '2001-04-15', 120);
--MERGE INTO films (film_id, film_name, description, mpa_rating_id, release_date,
--                    duration) KEY (film_id) VALUES (4, 'Очень крутое кино', 'больше смотреть не буду', 2,
--                                                        '2022-05-09', 180);

--MERGE INTO film_genre (film_genre_id, film_id, genre_id) KEY (film_genre_id) VALUES (1, 1, 3);
--MERGE INTO film_genre (film_genre_id, film_id, genre_id) KEY (film_genre_id) VALUES (2, 2, 4);
--MERGE INTO film_genre (film_genre_id, film_id, genre_id) KEY (film_genre_id) VALUES (3, 2, 6);
--MERGE INTO film_genre (film_genre_id, film_id, genre_id) KEY (film_genre_id) VALUES (4, 2, 1);
--MERGE INTO film_genre (film_genre_id, film_id, genre_id) KEY (film_genre_id) VALUES (5, 4, 2);

--MERGE INTO friendships (friendship_id, friend_from_id, friend_to_id, is_accept) KEY (friendship_id) VALUES (1, 1, 3, true);
--MERGE INTO friendships (friendship_id, friend_from_id, friend_to_id, is_accept) KEY (friendship_id) VALUES (2, 3, 1, false);
--MERGE INTO friendships (friendship_id, friend_from_id, friend_to_id, is_accept) KEY (friendship_id) VALUES (3, 3, 5, true);
--MERGE INTO friendships (friendship_id, friend_from_id, friend_to_id, is_accept) KEY (friendship_id) VALUES (4, 5, 3, true);

--MERGE INTO likes (like_id, film_id, user_id) KEY (like_id) VALUES (1, 2, 6);
--MERGE INTO likes (like_id, film_id, user_id) KEY (like_id) VALUES (2, 2, 4);
--MERGE INTO likes (like_id, film_id, user_id) KEY (like_id) VALUES (3, 3, 5);
