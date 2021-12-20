USE `db_nations`;

SELECT c.name, c.country_id, r.name , c2.name 
FROM countries c
JOIN regions r ON c.region_id = r.region_id
JOIN continents c2 ON r.continent_id = c2.continent_id
ORDER BY c.name ;

SELECT c.name, LENGTH(c.name) as lunghezza
FROM countries c
ORDER BY lunghezza DESC; 


SELECT r.name , LENGTH(r.name) as lunghezza
FROM regions r 
ORDER BY lunghezza DESC 
LIMIT 1;

SELECT c.name ,LENGTH(c.name) as lunghezza
FROM continents c 
ORDER BY lunghezza DESC 
LIMIT 1;
