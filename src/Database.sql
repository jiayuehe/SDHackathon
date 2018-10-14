-- COMMENT
/*This is the most dangerous line of sql code
CREATE DATABASE TripProject;*/
USE TripProject;

CREATE TABLE User(
    user_id INT(11) PRIMARY KEY  NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    _password VARCHAR(50) NOT NULL
);

CREATE TABLE Trip(
    trip_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title VARCHAR(20) NOT NULL,
    mainplace VARCHAR(25) NOT NULL
);

CREATE TABLE User_to_Trip(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id INT(11) NOT NULL,
    trip_id iNT(11) NOT NULL,
    FOREIGN KEY fk1(user_id) REFERENCES User(user_id),
    FOREIGN KEY fk2(trip_id) REFERENCES Trip(trip_id)
);

CREATE TABLE Location(
    location_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    trip_id iNT(11) NOT NULL,
    title VARCHAR(20) NOT NULL,
    description VARCHAR(200),
    item_type VARCHAR(20) NOT NULL,
    latitude VARCHAR(130) NOT NULL,
    longitutude VARCHAR(130) NOT NULL,
    from_date VARCHAR(20) NOT NULL,
    to_date VARCHAR(20) NOT NULL,
    FOREIGN KEY fk1(trip_id) REFERENCES Trip(trip_id)
);

CREATE TABLE Flight(
    flight_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    trip_id iNT(11) NOT NULL,
    from_location VARCHAR(3) NOT NULL,
    to_location VARCHAR(3) NOT NULL,
    start_time VARCHAR(50) NOT NULL,
    arrive_time VARCHAR(50) NOT NULL,
    FOREIGN KEY fk1(trip_id) REFERENCES Trip(trip_id)
);

    

