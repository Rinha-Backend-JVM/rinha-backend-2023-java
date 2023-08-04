CREATE TABLE people (
    nickname VARCHAR(32) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    birthdate DATE NOT NULL,
    skills_stack TEXT []
);