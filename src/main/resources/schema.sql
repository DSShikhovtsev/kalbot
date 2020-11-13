DROP TABLE IF EXISTS main.brands_tobaccos;
DROP TABLE IF EXISTS main.globaltaste_tastes;
DROP TABLE IF EXISTS main.fortress_tobaccos;
DROP TABLE IF EXISTS main.tobaccos_tastes;
DROP TABLE IF EXISTS main.kalians_tobaccos;
DROP TABLE IF EXISTS main.userstate_kalian;
DROP TABLE IF EXISTS main.users_tastes;
DROP TABLE IF EXISTS main.fortress;
DROP TABLE IF EXISTS main.brand;
DROP TABLE IF EXISTS main.taste;
DROP TABLE IF EXISTS main.global_taste;
DROP TABLE IF EXISTS main.tobacco;
DROP TABLE IF EXISTS main.kalian;
DROP TABLE IF EXISTS main.user_state;

CREATE SCHEMA IF NOT EXISTS main;
CREATE TABLE main.tobacco(id BIGSERIAL PRIMARY KEY, description VARCHAR(255), rating DOUBLE PRECISION);
CREATE TABLE main.brand(id BIGSERIAL PRIMARY KEY, firm VARCHAR(255));
CREATE TABLE main.fortress(id BIGSERIAL PRIMARY KEY, score INT, fortress VARCHAR(255));
CREATE TABLE main.taste(id BIGSERIAL PRIMARY KEY, taste VARCHAR(255), description VARCHAR(255));
CREATE TABLE main.global_taste(id BIGSERIAL PRIMARY KEY, taste VARCHAR(255));
CREATE TABLE main.kalian(
    id BIGSERIAL PRIMARY KEY,
    user_chat_id BIGINT,
    fortress_id BIGINT,
    date DATE,
    date_to DATE,
    ice BOOLEAN);
CREATE TABLE main.user_state(
    id BIGSERIAL PRIMARY KEY,
    user_chat_id BIGINT NOT NULL,
    state VARCHAR(255),
    global_taste_id BIGINT);

CREATE TABLE main.globaltaste_tastes(global_taste_id BIGINT NOT NULL, taste_id BIGINT NOT NULL,
    FOREIGN KEY(global_taste_id) REFERENCES main.global_taste(id) ON DELETE CASCADE,
    FOREIGN KEY(taste_id) REFERENCES main.taste(id) ON DELETE CASCADE
);

CREATE TABLE main.tobaccos_tastes(tobacco_id BIGINT NOT NULL, taste_id BIGINT NOT NULL,
    FOREIGN KEY(tobacco_id) REFERENCES main.tobacco(id) ON DELETE CASCADE,
    FOREIGN KEY(taste_id) REFERENCES main.taste(id) ON DELETE CASCADE,
    UNIQUE (tobacco_id, taste_id)
);

CREATE TABLE main.fortress_tobaccos(fortress_id BIGINT NOT NULL, tobacco_id BIGINT NOT NULL,
    FOREIGN KEY(fortress_id) REFERENCES main.fortress(id) ON DELETE CASCADE,
    FOREIGN KEY(tobacco_id) REFERENCES main.tobacco(id) ON DELETE CASCADE
);

CREATE TABLE main.brands_tobaccos(brand_id BIGINT NOT NULL, tobacco_id BIGINT NOT NULL,
    FOREIGN KEY(brand_id) REFERENCES main.brand(id) ON DELETE CASCADE,
    FOREIGN KEY(tobacco_id) REFERENCES main.tobacco(id) ON DELETE CASCADE,
    UNIQUE (tobacco_id, brand_id)
);

CREATE TABLE main.users_tastes(user_state_id BIGINT NOT NULL, tastes_id BIGINT NOT NULL,
    FOREIGN KEY(user_state_id) REFERENCES main.user_state(id) ON DELETE CASCADE,
    FOREIGN KEY(tastes_id) REFERENCES main.taste(id) ON DELETE CASCADE,
    UNIQUE (user_state_id, tastes_id)
);

CREATE TABLE main.userstate_kalian(user_state_id BIGINT NOT NULL, kalian_id BIGINT NOT NULL,
    FOREIGN KEY(user_state_id) REFERENCES main.user_state(id) ON DELETE CASCADE,
    FOREIGN KEY(kalian_id) REFERENCES main.kalian(id) ON DELETE CASCADE,
    UNIQUE (user_state_id, kalian_id)
);

CREATE TABLE main.kalians_tobaccos(kalian_id BIGINT NOT NULL, tobacco_id BIGINT NOT NULL,
    FOREIGN KEY(kalian_id) REFERENCES main.kalian(id) ON DELETE CASCADE,
    FOREIGN KEY(tobacco_id) REFERENCES main.tobacco(id) ON DELETE CASCADE,
    UNIQUE (kalian_id, tobacco_id)
);



