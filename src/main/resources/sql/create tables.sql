CREATE TABLE IF NOT EXISTS Author
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(45) NOT NULL,
    `birth_date` DATE        NOT NULL,
    `country`    VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS Book
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS Category
(
    `id`   INT         NOT NULL,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS Book_has_Category
(
    `Book_id`     INT NOT NULL,
    `Category_id` INT NOT NULL,
    PRIMARY KEY (`Book_id`, `Category_id`),
    INDEX `fk_Book_has_Category_Category1_idx` (`Category_id` ASC) VISIBLE,
    INDEX `fk_Book_has_Category_Book1_idx` (`Book_id` ASC) VISIBLE,
    CONSTRAINT `fk_Book_has_Category_Book1`
        FOREIGN KEY (`Book_id`)
            REFERENCES Book (`id`),
    CONSTRAINT `fk_Book_has_Category_Category1`
        FOREIGN KEY (`Category_id`)
            REFERENCES Category (`id`)
);

CREATE TABLE IF NOT EXISTS Author_has_Book
(
    `Author_id` INT NOT NULL,
    `Book_id`   INT NOT NULL,
    PRIMARY KEY (`Author_id`, `Book_id`),
    INDEX `fk_Author_has_Book_Book1_idx` (`Book_id` ASC) VISIBLE,
    INDEX `fk_Author_has_Book_Author1_idx` (`Author_id` ASC) VISIBLE,
    CONSTRAINT `fk_Author_has_Book_Author1`
        FOREIGN KEY (`Author_id`)
            REFERENCES Author (`id`),
    CONSTRAINT `fk_Author_has_Book_Book1`
        FOREIGN KEY (`Book_id`)
            REFERENCES Book (`id`)
);