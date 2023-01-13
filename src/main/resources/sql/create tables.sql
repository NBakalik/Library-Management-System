CREATE SCHEMA IF NOT EXISTS `library` DEFAULT CHARACTER SET utf8;
USE `library`;

CREATE TABLE IF NOT EXISTS `library`.`Author`
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(45) NOT NULL,
    `birth_date` DATE        NOT NULL,
    `country`    VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `library`.`Category`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `library`.`Book`
(
    `id`          INT         NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(45) NOT NULL,
    `category_id` INT         NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_Book_Category1_idx` (`category_id` ASC) VISIBLE,
    CONSTRAINT `fk_Book_Category1`
        FOREIGN KEY (`category_id`)
            REFERENCES `library`.`Category` (`id`)
);

CREATE TABLE IF NOT EXISTS `library`.`Author_has_Book`
(
    `author_id` INT NOT NULL,
    `book_id`   INT NOT NULL,
    PRIMARY KEY (`author_id`, `book_id`),
    INDEX `fk_Author_has_Book_Book1_idx` (`book_id` ASC) VISIBLE,
    INDEX `fk_Author_has_Book_Author1_idx` (`author_id` ASC) VISIBLE,
    CONSTRAINT `fk_Author_has_Book_Author1`
        FOREIGN KEY (`author_id`)
            REFERENCES `library`.`Author` (`id`),
    CONSTRAINT `fk_Author_has_Book_Book1`
        FOREIGN KEY (`book_id`)
            REFERENCES `library`.`Book` (`id`)
);