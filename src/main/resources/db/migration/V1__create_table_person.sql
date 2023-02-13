CREATE TABLE IF NOT EXISTS `people` (
    `id` integer(11) NOT NULL AUTO_INCREMENT,
    `first_name` varchar(10) NOT NULL,
    `last_name` varchar(10) NOT NULL,
    `address` varchar(30) NOT NULL,
    `gender` varchar(6) NOT NULL,
    PRIMARY KEY (`id`)
)