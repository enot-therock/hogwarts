CREATE TABLE cars (
    id SERIAL PRIMARY KEY,
    car_brand TEXT NOT NULL,
    car_model TEXT NOT NULL,
    price SERIAL CHECK (price > 0));

CREATE TABLE person (
    id SERIAL UNIQUE,
    name TEXT NOT NULL,
    age INTEGER NOT NULL,
    drivers_license BOOLEAN,
    cars_id SERIAL REFERENCES cars (id));

CREATE TRIGGER check_license
    BEFORE INSERT OR UPDATE ON person
    FOR EACH ROW
    BEGIN
        IF NEW.age >= 18 THEN
            SET NEW.drivers_license = 'yes';
        ELSE
            SET NEW.drivers_license = 'no';
        END IF;
    END;

