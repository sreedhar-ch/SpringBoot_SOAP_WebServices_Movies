CREATE TABLE movies (
  movie_id int NOT NULL,
  title varchar(45) DEFAULT NULL,
  category varchar(45) DEFAULT NULL,
  spaceship varchar(45) DEFAULT NULL,
  PRIMARY KEY (movie_id)
);

INSERT INTO movies(movie_id, title, category,spaceship) VALUES
(101, 'Mama mia', 'drama',""),
(102, 'Mission Impossible', 'action',""),
(103, 'Coco', 'animation',""),
(104, 'Mama mia', 'drama','testing');
