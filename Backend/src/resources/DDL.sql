CREATE TABLE `hotel` (
  `hotelid` int(11) NOT NULL,
  `hotel_name` varchar(500) NOT NULL,
  `lat` float NOT NULL,
  `lng` float NOT NULL,
  `city` varchar(500) NOT NULL,
  `address` varchar(500) NOT NULL,
  `country` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `expedia_link` varchar(500) NOT NULL,
  `overall_rating` varchar(100) NOT NULL,
  `image_path` varchar(300) NOT NULL,
  PRIMARY KEY (`hotelid`)
);
CREATE TABLE `hotel_brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hotelid` int(11) NOT NULL,
  `brand_name` varchar(45) NOT NULL,
  `sub_brand_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `hotel_review` (
  `reviewid` varchar(100) DEFAULT 'XXXXXXXXX',
  `hotelid` int(11) NOT NULL,
  `rating` decimal(10,0) NOT NULL,
  `review_text` varchar(2000) NOT NULL,
  `review_title` varchar(500) NOT NULL,
  `userid` int(11) NOT NULL DEFAULT '0',
  `review_post_date` varchar(500) NOT NULL,
  `is_recommended` tinyint(4) NOT NULL DEFAULT '0',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(500) NOT NULL DEFAULT 'Anonymous',
  PRIMARY KEY (`id`)
);

CREATE TABLE `review_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reviewid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `reviewLiked` (`reviewid`,`userid`)
);

CREATE TABLE `saved_hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `hotelid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
);
CREATE TABLE `user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(5000) NOT NULL,
  `address` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `zip_code` varchar(45) NOT NULL,
  `email_address` varchar(45) NOT NULL,
  `last_login` timestamp NULL DEFAULT NULL,
  `creation_time` varchar(45) NOT NULL,
  `is_admin` tinyint(4) NOT NULL DEFAULT '0',
  `salt` varchar(45) NOT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `salt_UNIQUE` (`salt`)
) ;
CREATE TABLE `user_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `hotelid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_hotel` (`userid`,`hotelid`)
) ;

CREATE TABLE `user_hotels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(45) NOT NULL,
  `hotelid` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_hotel` (`userid`,`hotelid`)
) ;

CREATE TABLE `user_session` (
  `userid` int(11) NOT NULL,
  `session_id` varchar(45) NOT NULL,
  `session_exp` timestamp NOT NULL,
  PRIMARY KEY (`session_id`)
) ;
