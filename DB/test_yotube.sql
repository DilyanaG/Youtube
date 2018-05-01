-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema test_youtube
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema test_youtube
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `test_youtube` DEFAULT CHARACTER SET utf8 ;
USE `test_youtube` ;

-- -----------------------------------------------------
-- Table `test_youtube`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test_youtube`.`users` ;

CREATE TABLE IF NOT EXISTS `test_youtube`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(30) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `photoUrl` VARCHAR(100)  NULL,
  `isDeleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `test_youtube`.`channels`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test_youtube`.`channels` ;

CREATE TABLE IF NOT EXISTS `test_youtube`.`channels` (
  `channel_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `isDeleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`channel_id`),
  INDEX `fk_channels_users_idx` (`user_id` ASC),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  CONSTRAINT `fk_channels_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `test_youtube`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `test_youtube`.`videos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test_youtube`.`videos` ;

CREATE TABLE IF NOT EXISTS `test_youtube`.`videos` (
  `video_id` INT NOT NULL AUTO_INCREMENT,
  `channel_id` INT NOT NULL,
  `video_url` VARCHAR(45) NOT NULL,
  `photo_url` VARCHAR(45) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `date` timestamp NOT NULL,
  `description` VARCHAR(200) NULL,
  `views` INT NOT NULL DEFAULT 0,
  `isDeleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`video_id`),
  UNIQUE INDEX `url_UNIQUE` (`video_url` ASC),
  INDEX `fk_videos_channels1_idx` (`channel_id` ASC),
  UNIQUE INDEX `photo_url_UNIQUE` (`photo_url` ASC),
  CONSTRAINT `fk_videos_channels1`
    FOREIGN KEY (`channel_id`)
    REFERENCES `test_youtube`.`channels` (`channel_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `test_youtube`.`playlists`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test_youtube`.`playlists` ;

CREATE TABLE IF NOT EXISTS `test_youtube`.`playlists` (
  `playlist_id` INT NOT NULL AUTO_INCREMENT,
  `channel_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `create_date` timestamp NOT NULL,
  `last_video_add_date` timestamp NULL,
  PRIMARY KEY (`playlist_id`),
  INDEX `fk_playlists_channels1_idx` (`channel_id` ASC),
  CONSTRAINT `fk_playlists_channels1`
    FOREIGN KEY (`channel_id`)
    REFERENCES `test_youtube`.`channels` (`channel_id`)
    ON DELETE CASCADE 
    ON UPDATE CASCADE )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `test_youtube`.`comments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test_youtube`.`comments` ;

CREATE TABLE IF NOT EXISTS `test_youtube`.`comments` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `video_id` INT NOT NULL,
  `channel_id` INT NOT NULL,
  `response_to_comment_id` INT NULL,
  `content` VARCHAR(45) NOT NULL,
  `date` timestamp NOT NULL,
  PRIMARY KEY (`comment_id`),
  INDEX `fk_comments_videos1_idx` (`video_id` ASC),
  INDEX `fk_comments_channels1_idx` (`channel_id` ASC),
  INDEX `fk_comments_comments1_idx` (`response_to_comment_id` ASC),
  CONSTRAINT `fk_comments_videos1`
    FOREIGN KEY (`video_id`)
    REFERENCES `test_youtube`.`videos` (`video_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_comments_channels1`
    FOREIGN KEY (`channel_id`)
    REFERENCES `test_youtube`.`channels` (`channel_id`)
     ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_comments_comments1`
    FOREIGN KEY (`response_to_comment_id`)
    REFERENCES `test_youtube`.`comments` (`comment_id`)
	ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `test_youtube`.`playlists_has_videos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test_youtube`.`playlists_has_videos` ;

CREATE TABLE IF NOT EXISTS `test_youtube`.`playlists_has_videos` (
  `video_id` INT NOT NULL,
  `playlist_id` INT NOT NULL,
  PRIMARY KEY (`video_id`, `playlist_id`),
  INDEX `fk_videos_has_playlists_playlists1_idx` (`playlist_id` ASC),
  INDEX `fk_videos_has_playlists_videos1_idx` (`video_id` ASC),
  CONSTRAINT `fk_videos_has_playlists_videos1`
    FOREIGN KEY (`video_id`)
    REFERENCES `test_youtube`.`videos` (`video_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_videos_has_playlists_playlists1`
    FOREIGN KEY (`playlist_id`)
    REFERENCES `test_youtube`.`playlists` (`playlist_id`)
	ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `test_youtube`.`channels_followed_channels`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test_youtube`.`channels_followed_channels` ;

CREATE TABLE IF NOT EXISTS `test_youtube`.`channels_followed_channels` (
  `follower_channel_id` INT NOT NULL,
  `followed_channel_id` INT NOT NULL,
  PRIMARY KEY (`follower_channel_id`, `followed_channel_id`),
  INDEX `fk_channels_has_channels_channels2_idx` (`followed_channel_id` ASC),
  INDEX `fk_channels_has_channels_channels1_idx` (`follower_channel_id` ASC),
  CONSTRAINT `fk_channels_has_channels_channels1`
    FOREIGN KEY (`follower_channel_id`)
    REFERENCES `test_youtube`.`channels` (`channel_id`)
	ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_channels_has_channels_channels2`
    FOREIGN KEY (`followed_channel_id`)
    REFERENCES `test_youtube`.`channels` (`channel_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `test_youtube`.`tags`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test_youtube`.`tags` ;

CREATE TABLE IF NOT EXISTS `test_youtube`.`tags` (
  `tag_id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`tag_id`),
  UNIQUE INDEX `content_UNIQUE` (`content` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `test_youtube`.`videos_has_tags`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test_youtube`.`videos_has_tags` ;

CREATE TABLE IF NOT EXISTS `test_youtube`.`videos_has_tags` (
  `video_id` INT NOT NULL,
  `tag_id` INT NOT NULL,
  PRIMARY KEY (`video_id`, `tag_id`),
  INDEX `fk_videos_has_tags_tags1_idx` (`tag_id` ASC),
  INDEX `fk_videos_has_tags_videos1_idx` (`video_id` ASC),
  CONSTRAINT `fk_videos_has_tags_videos1`
    FOREIGN KEY (`video_id`)
    REFERENCES `test_youtube`.`videos` (`video_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_videos_has_tags_tags1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `test_youtube`.`tags` (`tag_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `test_youtube`.`videos_has_likes_dislikes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test_youtube`.`videos_has_likes_dislikes` ;

CREATE TABLE IF NOT EXISTS `test_youtube`.`videos_has_likes_dislikes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `isLike` TINYINT NOT NULL,
  `video_id` INT NOT NULL,
  `channel_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_videos_has_likes_dislikes_videos1_idx` (`video_id` ASC),
  INDEX `fk_videos_has_likes_dislikes_channels1_idx` (`channel_id` ASC),
  CONSTRAINT `fk_videos_has_likes_dislikes_videos1`
    FOREIGN KEY (`video_id`)
    REFERENCES `test_youtube`.`videos` (`video_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_videos_has_likes_dislikes_channels1`
    FOREIGN KEY (`channel_id`)
    REFERENCES `test_youtube`.`channels` (`channel_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `test_youtube`.`comment_has_likes_dislikes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `test_youtube`.`comment_has_likes_dislikes` ;

CREATE TABLE IF NOT EXISTS `test_youtube`.`comment_has_likes_dislikes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `isLike` TINYINT NOT NULL,
  `comment_id` INT NOT NULL,
  `channel_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comments_has_likes_dislikes_comments1_idx` (`comment_id` ASC),
  INDEX `fk_comments_has_likes_dislikes_channels1_idx` (`channel_id` ASC),
  CONSTRAINT `fk_comments_has_likes_dislikes_comments1`
    FOREIGN KEY (`comment_id`)
    REFERENCES `test_youtube`.`comments` (`comment_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_comments_has_likes_dislikes_channels1`
    FOREIGN KEY (`channel_id`)
    REFERENCES `test_youtube`.`channels` (`channel_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
