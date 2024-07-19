CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, userid VARCHAR(255) UNIQUE NOT NULL, nickname VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL);
CREATE TABLE IF NOT EXISTS posts (id INT AUTO_INCREMENT PRIMARY KEY, userid VARCHAR(255), title VARCHAR(255), contents TEXT, image VARCHAR(255), FOREIGN KEY (userid) REFERENCES users(userid));
CREATE TABLE IF NOT EXISTS comments (id INT AUTO_INCREMENT PRIMARY KEY, userid VARCHAR(255), postid VARCHAR(255), contents TEXT, FOREIGN KEY (postid) REFERENCES posts(id), FOREIGN KEY (userid) REFERENCES users(userid));