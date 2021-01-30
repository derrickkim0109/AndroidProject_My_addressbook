CREATE DATABASE  IF NOT EXISTS `mypeople` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mypeople`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: mypeople
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `friendslist`
--

DROP TABLE IF EXISTS `friendslist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friendslist` (
  `fSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `fName` varchar(45) DEFAULT NULL,
  `fTel` varchar(45) DEFAULT NULL,
  `fRelation` varchar(45) DEFAULT NULL,
  `fImage` varchar(200) DEFAULT NULL,
  `fImageReal` varchar(2000) DEFAULT NULL,
  `fTag1` int(11) DEFAULT '0',
  `fTag2` int(11) DEFAULT '0',
  `fTag3` int(11) DEFAULT '0',
  `fTag4` int(11) DEFAULT '0',
  `fTag5` int(11) DEFAULT '0',
  `fComment` varchar(200) DEFAULT NULL,
  `fAddress` varchar(100) DEFAULT NULL,
  `user_uSeqno` int(11) NOT NULL,
  `fEmail` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fSeqno`,`user_uSeqno`),
  UNIQUE KEY `fSepno_UNIQUE` (`fSeqno`),
  KEY `fk_friendslist_user` (`user_uSeqno`),
  CONSTRAINT `fk_friendslist_user` FOREIGN KEY (`user_uSeqno`) REFERENCES `user` (`uSeqno`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friendslist`
--

LOCK TABLES `friendslist` WRITE;
/*!40000 ALTER TABLE `friendslist` DISABLE KEYS */;
INSERT INTO `friendslist` VALUES (17,'민영이','010-2648-0938','기타모임 ','202012301819110.jpg',NULL,0,0,1,1,1,'아르페지오','경기도 인천시',51,NULL),(18,'건강한애','010-3738-5627','기타모임','202012301819110.jpg',NULL,0,0,1,1,1,'띵땅띵땅','경기도 의정부시',51,NULL),(19,'김쌍','010-0060-7232','스터디그룹','202012301819110.jpg',NULL,0,0,0,1,1,'토익 좀 침','전라북도 군산시',51,NULL),(20,'모이수','010-3452-9238','스터디그룹','202012301819110.jpg',NULL,1,1,1,0,0,'코딩 좀 함','경기도 성남시',51,NULL),(21,'김태현 님','010-8581-6379','야간반','202012301819110.jpg',NULL,1,0,0,0,0,'잘 드심','서울시 은평구',51,NULL),(22,'삼성 김부장님','010-0021-6662','경영 지원과','202012301819110.jpg',NULL,0,1,0,0,0,'12/12까지 결재서류','서울시 강북구',52,NULL),(23,'현대 김대리님','010-4343-9898','인사과','202012301819110.jpg',NULL,0,0,1,0,0,'14일 점심약속','서울시 노원구',52,NULL),(24,'카카오 선차장님','010-4562-2831','모바일관련','202012301819110.jpg',NULL,1,0,0,0,0,'앱관련 문의전화','경기도 일산시',52,NULL),(25,'세민이형','010-3761-3831','친한 형','202012301819110.jpg',NULL,1,0,0,0,0,'풋살화 받기로함','서울시 광진구',53,NULL),(26,'명진이형','010-2822-2372','두번째로 친한형','202012301819110.jpg',NULL,1,0,0,0,0,'10/9 술약속 ','용인시 기흥구',53,NULL),(27,'James','010-9090-1829','디자인','202012301819110.jpg',NULL,0,0,0,1,0,'사와디캅','강원도 삼척시',53,NULL),(28,'Jack','010-2389-0001','천재','202012301819110.jpg',NULL,0,0,1,0,0,'동무','함경북도 ',53,NULL),(32,'스티브잡스','014-5117-9999','친구','202012311537168.peg',NULL,1,0,0,1,0,'가끔 연락 받아줌','미국',50,'steve_apple@apple.naggyda.com'),(33,'오브레임','010-8748-1111','동생','202012311553107.peg',NULL,0,0,1,0,0,'방배동 행동대장','서울시 방배동',50,'50'),(34,'빌게이츠','011-2546-9888','아버지','202012311542542.peg',NULL,0,0,1,0,0,'코딩아빠','USA',50,'Imbillionaire@microsoft.com'),(35,'수지','010-5446-8879','girlfriend','20201231154519.peg',NULL,1,1,0,1,0,'가끔 만남','서울시 강남구',50,'suji@naver.com'),(38,'이츠형','010-2837-1823','외삼촌','bil.jpeg',NULL,1,0,1,0,0,'5000원 빌림','서울시 성북구',49,'qlfrpdlcm@naver.com'),(40,'잡스형','010-8451-8745','아빠친구','stststst.jpeg',NULL,1,1,0,0,0,'천재적이고 사업 수완 좋은 부자','캘리포니아',49,'wkqtm@apple.com'),(43,'성룡','010-8282-8282','집주인','sss.jpeg',NULL,0,1,1,1,0,'착하고 의리 있음','중국 상해',49,'sungttt@china.co.ck'),(44,'패리스힐튼','010-4474-1515','옆집누나','woman.jpeg',NULL,0,1,0,1,1,'용돈 많이','라스베거스',49,'hilton@hhh.co.kkr'),(45,'이민우','010-8784-8271','친구','minu.jpg',NULL,0,1,1,0,1,'화이팅','경기도 성남시',49,'dlalsen@naver.com'),(47,'수지','010-8478-8680','친구','suzzzz.jpeg',NULL,0,0,1,0,0,'너무 이뻐서 보기만 해도 행복','서울시 강남구',49,'49'),(48,'오브레임','010-5454-1112','동네 후배','ov.jpeg',NULL,0,0,0,1,0,'돈많은 형','서울시 강남구',49,'49'),(49,'진수','010-5451-1441','아는 후배','taatt.jpg',NULL,0,0,0,0,1,'조금 까부는 경향이 있음','서울시 강남구 논현동',49,'49');
/*!40000 ALTER TABLE `friendslist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `tag1` varchar(45) DEFAULT 'TAG1',
  `tag2` varchar(45) DEFAULT 'TAG2',
  `tag3` varchar(45) DEFAULT 'TAG3',
  `tag4` varchar(45) DEFAULT 'TAG4',
  `tag5` varchar(45) DEFAULT 'TAG5',
  `user_uSeqno` int(11) NOT NULL,
  PRIMARY KEY (`user_uSeqno`),
  CONSTRAINT `fk_tag_user1` FOREIGN KEY (`user_uSeqno`) REFERENCES `user` (`uSeqno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES ('가족','초등친구','중등친구','고등친구','대학친구',49),('조명팀','음향팀','영상팀','특효팀','연출팀',50),('가족같은 사람','만나면 좋은사람','함께하면 좋은사람','소식 알고싶은 사람','알아둘 번호',51),('거래처','회사','협력사','디자인업체','전력업체',52),('풋살동호회','학원4조','학원3조','학원2조','학원1조',53);
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `uSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `uId` varchar(45) DEFAULT NULL,
  `uPw` varchar(45) DEFAULT NULL,
  `uName` varchar(45) DEFAULT NULL,
  `uTel` varchar(45) DEFAULT NULL,
  `uDeleteDate` date DEFAULT '1111-11-11',
  `uInsertDate` date DEFAULT NULL,
  PRIMARY KEY (`uSeqno`),
  UNIQUE KEY `uSeqno_UNIQUE` (`uSeqno`),
  UNIQUE KEY `uId_UNIQUE` (`uId`),
  UNIQUE KEY `uTel_UNIQUE` (`uTel`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (49,'wntnftk2001@naver.com','Testtest1!','정영재','010-9196-8996','1111-11-11','2020-12-30'),(50,'thlbyl0109@naver.com','Testtest1!','김태현','010-7426-0214','1111-11-11','2020-11-01'),(51,'hyeona_c@naver.com','Testtest1!','최현아','010-5446-2753','1111-11-11','2020-10-05'),(52,'wltjr318@naver.com','Testtest1!','최지석','010-1111-1111','1111-11-11','2020-09-15'),(53,'tnctis21@naver.com','Testtest1!','이민우','010-3046-3035','1111-11-11','2020-08-07');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-28 21:57:59
