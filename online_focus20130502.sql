-- MySQL dump 10.11
--
-- Host: localhost    Database: online_focus
-- ------------------------------------------------------
-- Server version	5.0.51b-community-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Temporary table structure for view `check_update_user_module_view`
--

DROP TABLE IF EXISTS `check_update_user_module_view`;
/*!50001 DROP VIEW IF EXISTS `check_update_user_module_view`*/;
/*!50001 CREATE TABLE `check_update_user_module_view` (
  `userId` bigint(8),
  `userModuleId` bigint(8),
  `ignoreByUser` int(1),
  `contentId` bigint(8)
) */;

--
-- Temporary table structure for view `check_update_view`
--

DROP TABLE IF EXISTS `check_update_view`;
/*!50001 DROP VIEW IF EXISTS `check_update_view`*/;
/*!50001 CREATE TABLE `check_update_view` (
  `sendId` bigint(12),
  `bufferId` bigint(8),
  `userModuleId` bigint(8),
  `moduleId` bigint(8)
) */;

--
-- Table structure for table `content_buffer`
--

DROP TABLE IF EXISTS `content_buffer`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `content_buffer` (
  `moduleId` bigint(8) NOT NULL,
  `content` mediumtext NOT NULL,
  `bufferId` bigint(8) NOT NULL auto_increment,
  `updateTime` datetime default NULL,
  `updateType` int(1) NOT NULL default '0',
  PRIMARY KEY  (`bufferId`),
  KEY `moduleId` USING BTREE (`moduleId`),
  CONSTRAINT `content_buffer_ibfk_1` FOREIGN KEY (`moduleId`) REFERENCES `module_info` (`moduleId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

/*!50003 SET @SAVE_SQL_MODE=@@SQL_MODE*/;

DELIMITER ;;
/*!50003 SET SESSION SQL_MODE="STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION" */;;
/*!50003 CREATE */ /*!50017 DEFINER=`root`@`localhost` */ /*!50003 TRIGGER `insertReceiverAndStore` AFTER INSERT ON `content_buffer` FOR EACH ROW begin
DECLARE a  bigint(8); 
DECLARE b  int(1); 
DECLARE done INT DEFAULT 0;
  
DECLARE cur1 CURSOR FOR SELECT
receiver_view.userModuleId,receiveType
FROM
receiver_view 
where receiver_view.moduleId=new.moduleId and (receiver_view.monitorType&new.updateType)=new.updateType and receiver_view.monitorType&1=1;

 DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
     OPEN cur1; 
    while done<1 do
    FETCH cur1 into a,b; 
if done <1 then
   insert into content_receiver(bufferId,sendType,userModuleId) values(new.bufferId,b,a);
end if;
   end while;
     CLOSE cur1; 
end */;;

DELIMITER ;
/*!50003 SET SESSION SQL_MODE=@SAVE_SQL_MODE*/;

--
-- Temporary table structure for view `content_need_to_be_send_view`
--

DROP TABLE IF EXISTS `content_need_to_be_send_view`;
/*!50001 DROP VIEW IF EXISTS `content_need_to_be_send_view`*/;
/*!50001 CREATE TABLE `content_need_to_be_send_view` (
  `userModuleName` char(20),
  `moduleId` bigint(8),
  `userModuleId` bigint(8),
  `contentType` int(1),
  `userId` bigint(8),
  `contentId` bigint(8),
  `subgroupId` bigint(8),
  `groupName` char(10),
  `alreadySendMessage` int(1),
  `alreadySendMail` int(1),
  `ignoreByUser` int(1),
  `monitorType` int(1)
) */;

--
-- Table structure for table `content_receiver`
--

DROP TABLE IF EXISTS `content_receiver`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `content_receiver` (
  `sendId` bigint(12) NOT NULL auto_increment,
  `bufferId` bigint(8) NOT NULL,
  `sendType` int(1) NOT NULL,
  `userModuleId` bigint(8) NOT NULL,
  PRIMARY KEY  (`sendId`),
  KEY `bid` (`bufferId`),
  KEY `bidum` (`userModuleId`),
  CONSTRAINT `bidc` FOREIGN KEY (`bufferId`) REFERENCES `content_buffer` (`bufferId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bidum` FOREIGN KEY (`userModuleId`) REFERENCES `user_module_info` (`userModuleId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `content_receiver_view`
--

DROP TABLE IF EXISTS `content_receiver_view`;
/*!50001 DROP VIEW IF EXISTS `content_receiver_view`*/;
/*!50001 CREATE TABLE `content_receiver_view` (
  `sendType` int(1),
  `userId` bigint(8),
  `sendId` bigint(12)
) */;

--
-- Table structure for table `east_money_bar`
--

DROP TABLE IF EXISTS `east_money_bar`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `east_money_bar` (
  `stockId` int(10) NOT NULL,
  `title` varchar(1024) default NULL,
  `time` datetime NOT NULL,
  `autor` varchar(255) default NULL,
  `ip` varchar(255) default NULL,
  `createTime` datetime NOT NULL,
  `topicId` int(10) NOT NULL auto_increment,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY  (`topicId`),
  UNIQUE KEY `url` (`url`),
  KEY `stock_id` (`stockId`),
  CONSTRAINT `stock_id` FOREIGN KEY (`stockId`) REFERENCES `stock_base_info` (`stockId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=900391 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `fail_module_view`
--

DROP TABLE IF EXISTS `fail_module_view`;
/*!50001 DROP VIEW IF EXISTS `fail_module_view`*/;
/*!50001 CREATE TABLE `fail_module_view` (
  `moduleId` bigint(8),
  `failTimes` int(1)
) */;

--
-- Temporary table structure for view `fail_web_info_view`
--

DROP TABLE IF EXISTS `fail_web_info_view`;
/*!50001 DROP VIEW IF EXISTS `fail_web_info_view`*/;
/*!50001 CREATE TABLE `fail_web_info_view` (
  `webId` bigint(8),
  `webAddress` varchar(255),
  `failTimes` int(4),
  `totalMonitorTimes` int(4)
) */;

--
-- Temporary table structure for view `failwebaddresstest`
--

DROP TABLE IF EXISTS `failwebaddresstest`;
/*!50001 DROP VIEW IF EXISTS `failwebaddresstest`*/;
/*!50001 CREATE TABLE `failwebaddresstest` (
  `webAddress` varchar(255),
  `failTimes` int(1)
) */;

--
-- Table structure for table `friends_info`
--

DROP TABLE IF EXISTS `friends_info`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `friends_info` (
  `id` bigint(20) NOT NULL auto_increment,
  `userId` bigint(20) NOT NULL,
  `friendId` bigint(20) NOT NULL,
  `friendState` int(11) NOT NULL,
  `friendInfo` varchar(20) default NULL,
  `subgroupId` bigint(8) default NULL,
  PRIMARY KEY  (`id`),
  KEY `fid` (`friendId`),
  CONSTRAINT `fid` FOREIGN KEY (`friendId`) REFERENCES `user_info` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `mail_need_to_be_send_view`
--

DROP TABLE IF EXISTS `mail_need_to_be_send_view`;
/*!50001 DROP VIEW IF EXISTS `mail_need_to_be_send_view`*/;
/*!50001 CREATE TABLE `mail_need_to_be_send_view` (
  `userId` bigint(8),
  `userName` char(10),
  `mail` char(30),
  `userModuleName` char(20),
  `websiteName` char(20),
  `contentId` bigint(8),
  `updateTime` datetime
) */;

--
-- Table structure for table `manager_info`
--

DROP TABLE IF EXISTS `manager_info`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `manager_info` (
  `managerId` bigint(8) NOT NULL auto_increment,
  `managerName` char(10) default NULL,
  `managerPsw` char(255) default '00000',
  PRIMARY KEY  (`managerId`)
) ENGINE=InnoDB AUTO_INCREMENT=2002 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `module_histoy_num_view`
--

DROP TABLE IF EXISTS `module_histoy_num_view`;
/*!50001 DROP VIEW IF EXISTS `module_histoy_num_view`*/;
/*!50001 CREATE TABLE `module_histoy_num_view` (
  `num` decimal(24,4),
  `userModuleId` bigint(8)
) */;

--
-- Table structure for table `module_info`
--

DROP TABLE IF EXISTS `module_info`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `module_info` (
  `moduleId` bigint(8) NOT NULL auto_increment,
  `modulePath` text NOT NULL,
  `failTimes` int(1) NOT NULL default '0',
  `webId` bigint(8) NOT NULL,
  `enable` int(1) NOT NULL default '1',
  `updateContent` mediumtext NOT NULL,
  `userCount` bigint(8) NOT NULL,
  `rssContent` mediumtext NOT NULL,
  `defineSource` mediumtext NOT NULL,
  PRIMARY KEY  (`moduleId`),
  UNIQUE KEY `moduleId` (`moduleId`),
  KEY `webId` (`webId`),
  CONSTRAINT `webIdConstraint` FOREIGN KEY (`webId`) REFERENCES `monitor_web_info` (`webId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `module_info_show_view`
--

DROP TABLE IF EXISTS `module_info_show_view`;
/*!50001 DROP VIEW IF EXISTS `module_info_show_view`*/;
/*!50001 CREATE TABLE `module_info_show_view` (
  `userId` bigint(8),
  `websiteName` char(20),
  `userModuleName` char(20),
  `updateTime` timestamp,
  `webAddress` varchar(255),
  `userModuleId` bigint(8),
  `accessType` int(1),
  `userModuleStyle` varchar(255),
  `subgroupId` bigint(8),
  `enable` int(1),
  `updateContent` mediumtext,
  `contentId` bigint(8),
  `moduleId` bigint(8),
  `contentType` int(1),
  `headCssAndScript` mediumtext,
  `bodyCssAndScript` mediumtext,
  `unCheckPaths` text,
  `ignoreByUser` int(1)
) */;

--
-- Temporary table structure for view `module_need_to_be_update_view`
--

DROP TABLE IF EXISTS `module_need_to_be_update_view`;
/*!50001 DROP VIEW IF EXISTS `module_need_to_be_update_view`*/;
/*!50001 CREATE TABLE `module_need_to_be_update_view` (
  `webAddress` varchar(255),
  `moduleId` bigint(8),
  `path` text,
  `lastTime` double,
  `updateContent` mediumtext
) */;

--
-- Temporary table structure for view `module_user_modules_view`
--

DROP TABLE IF EXISTS `module_user_modules_view`;
/*!50001 DROP VIEW IF EXISTS `module_user_modules_view`*/;
/*!50001 CREATE TABLE `module_user_modules_view` (
  `moduleId` bigint(8),
  `contentId` bigint(8)
) */;

--
-- Temporary table structure for view `modules_link_view`
--

DROP TABLE IF EXISTS `modules_link_view`;
/*!50001 DROP VIEW IF EXISTS `modules_link_view`*/;
/*!50001 CREATE TABLE `modules_link_view` (
  `userName` char(10),
  `phone` char(12),
  `receiveId` bigint(8),
  `userModuleId` bigint(8)
) */;

--
-- Temporary table structure for view `monitor_by_js_view`
--

DROP TABLE IF EXISTS `monitor_by_js_view`;
/*!50001 DROP VIEW IF EXISTS `monitor_by_js_view`*/;
/*!50001 CREATE TABLE `monitor_by_js_view` (
  `modulePath` text,
  `moduleId` bigint(8),
  `webAddress` varchar(255)
) */;

--
-- Table structure for table `monitor_web_info`
--

DROP TABLE IF EXISTS `monitor_web_info`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `monitor_web_info` (
  `webId` bigint(8) NOT NULL auto_increment,
  `webAddress` varchar(255) NOT NULL,
  `monitorFrequency` time NOT NULL default '00:10:00',
  `lastMonitorTime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `isMonitoring` bit(1) NOT NULL default '\0',
  `failTimes` int(4) NOT NULL default '0',
  `userCount` bigint(8) NOT NULL default '1',
  `enable` int(1) NOT NULL default '1',
  `retryMonitorTime` time NOT NULL default '00:10:00' COMMENT '如果失败，下一次再监测的时间间隔，可能是网页封锁了此ip ,也可能是网络不通',
  `headCssAndScript` mediumtext,
  `bodyCssAndScript` mediumtext,
  `totalMonitorTimes` int(4) NOT NULL default '1',
  PRIMARY KEY  (`webId`),
  UNIQUE KEY `webId` (`webId`),
  UNIQUE KEY `webAddress` (`webAddress`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

/*!50003 SET @SAVE_SQL_MODE=@@SQL_MODE*/;

DELIMITER ;;
/*!50003 SET SESSION SQL_MODE="STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION" */;;
/*!50003 CREATE */ /*!50017 DEFINER=`root`@`localhost` */ /*!50003 TRIGGER `monitor_web_info_tig01` AFTER UPDATE ON `monitor_web_info` FOR EACH ROW begin
   if new.enable=0 then
      update module_info set enable=0 where webId=new.WebId;
  end if;
end */;;

DELIMITER ;
/*!50003 SET SESSION SQL_MODE=@SAVE_SQL_MODE*/;

--
-- Temporary table structure for view `receiver_view`
--

DROP TABLE IF EXISTS `receiver_view`;
/*!50001 DROP VIEW IF EXISTS `receiver_view`*/;
/*!50001 CREATE TABLE `receiver_view` (
  `userModuleId` bigint(8),
  `moduleId` bigint(8),
  `monitorType` int(1),
  `receiveType` int(1)
) */;

--
-- Table structure for table `stock_base_info`
--

DROP TABLE IF EXISTS `stock_base_info`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `stock_base_info` (
  `stockId` int(10) NOT NULL auto_increment,
  `stockCode` varchar(10) NOT NULL,
  `stockName` varchar(50) NOT NULL,
  `createTime` datetime NOT NULL,
  `eastmoneyBarUrl` varchar(200) default NULL,
  `eastmoneyComment` varchar(200) default NULL,
  `earningsRatio` float default NULL,
  `updateDate` datetime NOT NULL,
  PRIMARY KEY  (`stockId`),
  UNIQUE KEY `stock_code_index` (`stockCode`)
) ENGINE=InnoDB AUTO_INCREMENT=2571 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `stock_daily`
--

DROP TABLE IF EXISTS `stock_daily`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `stock_daily` (
  `date` date NOT NULL,
  `open` float NOT NULL,
  `high` float NOT NULL,
  `low` float NOT NULL,
  `close` float NOT NULL,
  `volume` int(15) NOT NULL,
  `adjClose` float NOT NULL,
  `stockId` int(10) NOT NULL,
  `daily_id` int(10) NOT NULL auto_increment,
  `createTime` datetime NOT NULL,
  PRIMARY KEY  (`daily_id`),
  UNIQUE KEY `ss` (`stockId`,`date`),
  KEY `stock_id` (`stockId`),
  CONSTRAINT `stock_fk` FOREIGN KEY (`stockId`) REFERENCES `stock_base_info` (`stockId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3838295 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `system_config`
--

DROP TABLE IF EXISTS `system_config`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `system_config` (
  `configId` int(4) NOT NULL auto_increment,
  `configName` varchar(20) NOT NULL,
  `configValue` text,
  PRIMARY KEY  (`configId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `user_activecode`
--

DROP TABLE IF EXISTS `user_activecode`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `user_activecode` (
  `activeCodeId` bigint(8) NOT NULL auto_increment,
  `activeCode` varchar(21) NOT NULL,
  `userId` bigint(8) NOT NULL,
  `activeCodeSendTime` datetime NOT NULL,
  PRIMARY KEY  (`activeCodeId`),
  KEY `uidfk` (`userId`),
  CONSTRAINT `uidfk` FOREIGN KEY (`userId`) REFERENCES `user_info` (`userId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `user_default_receive_info`
--

DROP TABLE IF EXISTS `user_default_receive_info`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `user_default_receive_info` (
  `startTime` time NOT NULL,
  `stopTime` time NOT NULL,
  `defaultReceiveId` bigint(8) NOT NULL auto_increment,
  `receiveType` int(1) NOT NULL,
  `sendFrequency` datetime NOT NULL default '2000-01-01 00:00:00',
  `maxReceiveNum` int(1) NOT NULL default '2',
  `userId` bigint(8) NOT NULL,
  PRIMARY KEY  (`defaultReceiveId`),
  UNIQUE KEY `did` (`defaultReceiveId`),
  KEY `uid` (`userId`),
  CONSTRAINT `uid` FOREIGN KEY (`userId`) REFERENCES `user_info` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `user_focus_web_module_view`
--

DROP TABLE IF EXISTS `user_focus_web_module_view`;
/*!50001 DROP VIEW IF EXISTS `user_focus_web_module_view`*/;
/*!50001 CREATE TABLE `user_focus_web_module_view` (
  `webAddress` varchar(255),
  `modulePath` text,
  `userModuleId` bigint(8),
  `userId` bigint(8)
) */;

--
-- Table structure for table `user_friend_subgroup_info`
--

DROP TABLE IF EXISTS `user_friend_subgroup_info`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `user_friend_subgroup_info` (
  `userId` bigint(8) NOT NULL,
  `subgroupId` bigint(8) NOT NULL auto_increment,
  `groupName` char(10) NOT NULL,
  `groupType` int(1) NOT NULL default '1',
  PRIMARY KEY  (`subgroupId`),
  KEY `userIdIndex` (`userId`),
  CONSTRAINT `user_friend_subgroup_info_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user_info` (`userId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `user_friends_view`
--

DROP TABLE IF EXISTS `user_friends_view`;
/*!50001 DROP VIEW IF EXISTS `user_friends_view`*/;
/*!50001 CREATE TABLE `user_friends_view` (
  `userId` bigint(8),
  `userName` char(10),
  `friendId` bigint(20),
  `friendInfo` varchar(20),
  `groupName` char(10)
) */;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `user_info` (
  `userId` bigint(8) NOT NULL auto_increment,
  `userName` char(10) NOT NULL default '',
  `gender` char(2) default '男',
  `birthday` date default '2000-01-01',
  `password` char(32) NOT NULL default '',
  `mailbox` char(30) default NULL,
  `phone` char(12) default NULL,
  `registrationTime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `enable` int(1) NOT NULL default '1',
  `userType` int(1) default '0',
  `userPoint` int(11) default '0',
  `portrait` char(5) default NULL,
  PRIMARY KEY  (`userId`),
  UNIQUE KEY `userId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=10013 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

/*!50003 SET @SAVE_SQL_MODE=@@SQL_MODE*/;

DELIMITER ;;
/*!50003 SET SESSION SQL_MODE="STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION" */;;
/*!50003 CREATE */ /*!50017 DEFINER=`root`@`localhost` */ /*!50003 TRIGGER `insertTrigger` AFTER INSERT ON `user_info` FOR EACH ROW begin
DECLARE v_subgroupId  bigint(8); 
DECLARE done INT DEFAULT 0;
/*和游标对应的变量*/
DECLARE v_userModuleName varchar(20);
DECLARE v_moduleId bigint(8);
DECLARE v_websiteName varchar(20);
DECLARE v_enable int(1);
DECLARE v_monitorFrequency time;
DECLARE v_userModuleStyle varchar(255);
DECLARE v_accessType int(1);
DECLARE v_monitorType int(1);
DECLARE v_path varchar(255);

/**取出业务员订阅的模块**/
DECLARE cur1 CURSOR FOR 
SELECT
user_module_info.userModuleName,
user_module_info.moduleId,
user_module_info.websiteName,
user_module_info.`enable`,
user_module_info.monitorFrequency,
user_module_info.userModuleStyle,
user_module_info.accessType,
user_module_info.monitorType,
user_module_info.unCheckPaths
FROM
user_module_info
WHERE
user_module_info.userId = 1;
/**end**/
DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
insert into user_module_subgroup_info(userId,groupName,groupType) values(new.userId,'默认',3);/*插入用户默认分组*/
SELECT  user_module_subgroup_info.subgroupId into  v_subgroupId  FROM user_module_subgroup_info WHERE user_module_subgroup_info.groupType = 3 and userId=new.userId; /*获取用户默认分组*/
insert into user_subscribe(userId,subscribeUserId) values(new.userID,1);/*用户默认订阅平台业务员*/

OPEN cur1; 
    while done<1 do
    FETCH cur1 into   v_userModuleName,v_moduleId,v_websiteName,v_enable,v_monitorFrequency,v_userModuleStyle, v_accessType,v_monitorType, v_path ;
    if done <1 then
         insert into user_module_info(userId,subgroupId,userModuleName, moduleId, websiteName, enable, monitorFrequency, userModuleStyle,  accessType, monitorType,  unCheckPaths) 
        values(new.userId,v_subgroupId,v_userModuleName,v_moduleId,v_websiteName,1,v_monitorFrequency,v_userModuleStyle, v_accessType,v_monitorType, v_path );
    end if;
   end while;
CLOSE cur1; 
end */;;

DELIMITER ;
/*!50003 SET SESSION SQL_MODE=@SAVE_SQL_MODE*/;

--
-- Table structure for table `user_module_contents`
--

DROP TABLE IF EXISTS `user_module_contents`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `user_module_contents` (
  `userModuleContent` mediumtext NOT NULL,
  `contentId` bigint(8) NOT NULL auto_increment,
  `userModuleId` bigint(8) NOT NULL,
  `updateTime` datetime NOT NULL,
  `contentType` int(1) NOT NULL,
  `alreadySendMessage` int(1) NOT NULL default '0',
  `alreadySendMail` int(1) NOT NULL default '0',
  `ignoreByUser` int(1) NOT NULL default '0',
  PRIMARY KEY  (`contentId`),
  KEY `userModuleIdFK` (`userModuleId`),
  CONSTRAINT `userModuleIdFK` FOREIGN KEY (`userModuleId`) REFERENCES `user_module_info` (`userModuleId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `user_module_disingore_view`
--

DROP TABLE IF EXISTS `user_module_disingore_view`;
/*!50001 DROP VIEW IF EXISTS `user_module_disingore_view`*/;
/*!50001 CREATE TABLE `user_module_disingore_view` (
  `userId` bigint(8),
  `contentId` bigint(8)
) */;

--
-- Temporary table structure for view `user_module_history_list_view`
--

DROP TABLE IF EXISTS `user_module_history_list_view`;
/*!50001 DROP VIEW IF EXISTS `user_module_history_list_view`*/;
/*!50001 CREATE TABLE `user_module_history_list_view` (
  `userModuleId` bigint(8),
  `moduleId` bigint(8),
  `bufferId` bigint(8)
) */;

--
-- Temporary table structure for view `user_module_history_view`
--

DROP TABLE IF EXISTS `user_module_history_view`;
/*!50001 DROP VIEW IF EXISTS `user_module_history_view`*/;
/*!50001 CREATE TABLE `user_module_history_view` (
  `userModuleStyle` varchar(255),
  `headCssAndScript` mediumtext,
  `bodyCssAndScript` mediumtext,
  `updateTime` datetime,
  `content` mediumtext,
  `bufferId` bigint(8),
  `userModuleId` bigint(8)
) */;

--
-- Table structure for table `user_module_info`
--

DROP TABLE IF EXISTS `user_module_info`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `user_module_info` (
  `userId` bigint(8) NOT NULL default '1',
  `userModuleName` char(20) NOT NULL default '新模块',
  `moduleId` bigint(8) NOT NULL,
  `userModuleId` bigint(8) NOT NULL auto_increment,
  `websiteName` char(20) NOT NULL,
  `enable` int(1) NOT NULL default '1',
  `monitorFrequency` time NOT NULL default '00:03:00',
  `userModuleStyle` varchar(255) default 'position: absolute; left: -1000px; top: -1000px; height: 200px; width: 500px; ',
  `accessType` int(1) default '1',
  `monitorType` int(1) default '0',
  `subgroupId` bigint(8) default NULL,
  `unCheckPaths` text,
  PRIMARY KEY  (`userModuleId`),
  UNIQUE KEY `um` (`userId`,`moduleId`),
  UNIQUE KEY `umid` (`userModuleId`),
  KEY `monitor` (`moduleId`),
  KEY `uid3` (`userId`),
  KEY `subgroupIdIndex` (`subgroupId`),
  CONSTRAINT `subgroupIdFK` FOREIGN KEY (`subgroupId`) REFERENCES `user_module_subgroup_info` (`subgroupId`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `user_module_info_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user_info` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_module_info_ibfk_3` FOREIGN KEY (`moduleId`) REFERENCES `module_info` (`moduleId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

/*!50003 SET @SAVE_SQL_MODE=@@SQL_MODE*/;

DELIMITER ;;
/*!50003 SET SESSION SQL_MODE="STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION" */;;
/*!50003 CREATE */ /*!50017 DEFINER=`root`@`localhost` */ /*!50003 TRIGGER `insertInto_user_receive_info` AFTER INSERT ON `user_module_info` FOR EACH ROW begin
DECLARE text  mediumtext;
insert into user_receive_info(user_receive_info.startTime,
user_receive_info.stopTime,
user_receive_info.receiveType,
user_receive_info.lastSendTime,
user_receive_info.sendFrequency,
user_receive_info.maxReceiveNum,
user_receive_info.userModuleId) values('01:00:00','23:59:59',9,'2000-01-01 00:00:00',	'2000-01-01 00:01:00',2,new.userModuleId
);
select  updateContent into text  from module_info where module_info.moduleId=new.moduleId;
insert into user_module_contents(userModuleContent,userModuleId,updateTime,contentType,alreadySendMessage,alreadySendMail,ignoreByUser) values(text,new.userModuleId,now(),0,1,1,1);
end */;;

DELIMITER ;
/*!50003 SET SESSION SQL_MODE=@SAVE_SQL_MODE*/;

--
-- Table structure for table `user_module_subgroup_info`
--

DROP TABLE IF EXISTS `user_module_subgroup_info`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `user_module_subgroup_info` (
  `userId` bigint(8) NOT NULL,
  `subgroupId` bigint(8) NOT NULL auto_increment,
  `groupName` char(10) NOT NULL,
  `groupType` int(1) NOT NULL default '1',
  PRIMARY KEY  (`subgroupId`),
  KEY `userIdIndex` (`userId`),
  CONSTRAINT `userIdFK2` FOREIGN KEY (`userId`) REFERENCES `user_info` (`userId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `user_module_updatepaths_view`
--

DROP TABLE IF EXISTS `user_module_updatepaths_view`;
/*!50001 DROP VIEW IF EXISTS `user_module_updatepaths_view`*/;
/*!50001 CREATE TABLE `user_module_updatepaths_view` (
  `userId` bigint(8),
  `webAddress` varchar(255),
  `modulePath` text,
  `contentId` bigint(8),
  `userModuleId` bigint(8),
  `userModuleContent` mediumtext,
  `contentType` int(1),
  `unCheckPaths` text
) */;

--
-- Table structure for table `user_receive_info`
--

DROP TABLE IF EXISTS `user_receive_info`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `user_receive_info` (
  `startTime` time NOT NULL,
  `stopTime` time NOT NULL,
  `receiveId` bigint(8) NOT NULL auto_increment,
  `receiveType` int(1) NOT NULL,
  `lastSendTime` datetime NOT NULL default '0000-00-00 00:00:00',
  `sendFrequency` datetime NOT NULL default '2000-01-01 00:00:00',
  `maxReceiveNum` int(1) NOT NULL default '2',
  `userModuleId` bigint(8) NOT NULL,
  PRIMARY KEY  (`receiveId`),
  KEY `um` (`userModuleId`),
  CONSTRAINT `umk` FOREIGN KEY (`userModuleId`) REFERENCES `user_module_info` (`userModuleId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

/*!50003 SET @SAVE_SQL_MODE=@@SQL_MODE*/;

DELIMITER ;;
/*!50003 SET SESSION SQL_MODE="STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION" */;;
/*!50003 CREATE */ /*!50017 DEFINER=`root`@`localhost` */ /*!50003 TRIGGER `updateTrigger` AFTER UPDATE ON `user_receive_info` FOR EACH ROW begin
update content_receiver set sendType=new.receiveType   where   sendType=old.receiveType and userModuleId=old.userModuleId;
end */;;

/*!50003 SET SESSION SQL_MODE="STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION" */;;
/*!50003 CREATE */ /*!50017 DEFINER=`root`@`localhost` */ /*!50003 TRIGGER `deleteTrigger` AFTER DELETE ON `user_receive_info` FOR EACH ROW begin
delete from content_receiver where sendType=old.receiveType and userModuleId=old.userModuleId;
end */;;

DELIMITER ;
/*!50003 SET SESSION SQL_MODE=@SAVE_SQL_MODE*/;

--
-- Table structure for table `user_space_info`
--

DROP TABLE IF EXISTS `user_space_info`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `user_space_info` (
  `accessType` int(1) NOT NULL,
  `question` varchar(255) default NULL,
  `answer` char(20) default NULL,
  `userSpaceName` char(20) default NULL,
  `userSpaceId` bigint(8) NOT NULL auto_increment,
  `userId` bigint(8) NOT NULL,
  `totalAccesses` bigint(8) default NULL,
  `userImage` char(64) default NULL,
  `userSpaceStyle` varchar(255) NOT NULL,
  PRIMARY KEY  (`userSpaceId`),
  KEY `userIdFK` (`userId`),
  CONSTRAINT `userIdFK` FOREIGN KEY (`userId`) REFERENCES `user_info` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `user_space_module_info_show_view`
--

DROP TABLE IF EXISTS `user_space_module_info_show_view`;
/*!50001 DROP VIEW IF EXISTS `user_space_module_info_show_view`*/;
/*!50001 CREATE TABLE `user_space_module_info_show_view` (
  `userId` bigint(8),
  `websiteName` char(20),
  `userModuleName` char(20),
  `webAddress` varchar(255),
  `userModuleId` bigint(8),
  `accessType` int(1),
  `userModuleStyle` varchar(255),
  `subgroupId` bigint(8),
  `enable` int(1),
  `moduleId` bigint(8),
  `headCssAndScript` mediumtext,
  `bodyCssAndScript` mediumtext,
  `unCheckPaths` text,
  `updateContent` mediumtext
) */;

--
-- Table structure for table `user_subscribe`
--

DROP TABLE IF EXISTS `user_subscribe`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `user_subscribe` (
  `userId` bigint(8) NOT NULL,
  `subscribeUserId` bigint(8) NOT NULL,
  `subscribeId` bigint(8) NOT NULL auto_increment,
  PRIMARY KEY  (`subscribeId`),
  UNIQUE KEY `udiAndsid` (`userId`,`subscribeUserId`),
  KEY `uidI` (`userId`),
  KEY `suidI` (`subscribeUserId`),
  CONSTRAINT `suid2` FOREIGN KEY (`subscribeUserId`) REFERENCES `user_info` (`userId`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `uidfk2` FOREIGN KEY (`userId`) REFERENCES `user_info` (`userId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `usermodule_view`
--

DROP TABLE IF EXISTS `usermodule_view`;
/*!50001 DROP VIEW IF EXISTS `usermodule_view`*/;
/*!50001 CREATE TABLE `usermodule_view` (
  `userId` bigint(8),
  `userModuleName` char(20),
  `moduleId` bigint(8),
  `websiteName` char(20),
  `webAddress` varchar(255)
) */;

--
-- Temporary table structure for view `web_user_modules_view`
--

DROP TABLE IF EXISTS `web_user_modules_view`;
/*!50001 DROP VIEW IF EXISTS `web_user_modules_view`*/;
/*!50001 CREATE TABLE `web_user_modules_view` (
  `webId` bigint(8),
  `contentId` bigint(8)
) */;

--
-- Final view structure for view `check_update_user_module_view`
--

/*!50001 DROP TABLE `check_update_user_module_view`*/;
/*!50001 DROP VIEW IF EXISTS `check_update_user_module_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `check_update_user_module_view` AS select `user_module_info`.`userId` AS `userId`,`user_module_contents`.`userModuleId` AS `userModuleId`,`user_module_contents`.`ignoreByUser` AS `ignoreByUser`,`user_module_contents`.`contentId` AS `contentId` from ((`user_module_info` join `user_module_contents`) join `module_info`) where ((`user_module_info`.`userModuleId` = `user_module_contents`.`userModuleId`) and (`user_module_contents`.`contentType` = 1) and (`user_module_info`.`moduleId` = `module_info`.`moduleId`) and (`module_info`.`enable` <> 0)) */;

--
-- Final view structure for view `check_update_view`
--

/*!50001 DROP TABLE `check_update_view`*/;
/*!50001 DROP VIEW IF EXISTS `check_update_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `check_update_view` AS select `content_receiver`.`sendId` AS `sendId`,`content_buffer`.`bufferId` AS `bufferId`,`user_module_info`.`userModuleId` AS `userModuleId`,`user_module_info`.`moduleId` AS `moduleId` from ((`content_receiver` join `user_module_info`) join `content_buffer`) where ((`content_receiver`.`userModuleId` = `user_module_info`.`userModuleId`) and (`content_receiver`.`bufferId` = `content_buffer`.`bufferId`) and (`content_receiver`.`sendType` = 9)) order by `content_buffer`.`bufferId` */;

--
-- Final view structure for view `content_need_to_be_send_view`
--

/*!50001 DROP TABLE `content_need_to_be_send_view`*/;
/*!50001 DROP VIEW IF EXISTS `content_need_to_be_send_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `content_need_to_be_send_view` AS select `user_module_info`.`userModuleName` AS `userModuleName`,`module_info`.`moduleId` AS `moduleId`,`user_module_info`.`userModuleId` AS `userModuleId`,`user_module_contents`.`contentType` AS `contentType`,`user_module_info`.`userId` AS `userId`,`user_module_contents`.`contentId` AS `contentId`,`user_module_info`.`subgroupId` AS `subgroupId`,`user_module_subgroup_info`.`groupName` AS `groupName`,`user_module_contents`.`alreadySendMessage` AS `alreadySendMessage`,`user_module_contents`.`alreadySendMail` AS `alreadySendMail`,`user_module_contents`.`ignoreByUser` AS `ignoreByUser`,`user_module_info`.`monitorType` AS `monitorType` from (((`user_module_info` join `module_info`) join `user_module_contents`) join `user_module_subgroup_info`) where ((`user_module_info`.`moduleId` = `module_info`.`moduleId`) and (`user_module_contents`.`userModuleId` = `user_module_info`.`userModuleId`) and (`user_module_info`.`subgroupId` = `user_module_subgroup_info`.`subgroupId`)) */;

--
-- Final view structure for view `content_receiver_view`
--

/*!50001 DROP TABLE `content_receiver_view`*/;
/*!50001 DROP VIEW IF EXISTS `content_receiver_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `content_receiver_view` AS select `content_receiver`.`sendType` AS `sendType`,`user_module_info`.`userId` AS `userId`,`content_receiver`.`sendId` AS `sendId` from (`content_receiver` join `user_module_info`) where (`content_receiver`.`userModuleId` = `user_module_info`.`userModuleId`) */;

--
-- Final view structure for view `fail_module_view`
--

/*!50001 DROP TABLE `fail_module_view`*/;
/*!50001 DROP VIEW IF EXISTS `fail_module_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `fail_module_view` AS select `module_info`.`moduleId` AS `moduleId`,`module_info`.`failTimes` AS `failTimes` from `module_info` where ((`module_info`.`failTimes` > 2) and (`module_info`.`enable` <> 0)) */;

--
-- Final view structure for view `fail_web_info_view`
--

/*!50001 DROP TABLE `fail_web_info_view`*/;
/*!50001 DROP VIEW IF EXISTS `fail_web_info_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `fail_web_info_view` AS select `monitor_web_info`.`webId` AS `webId`,`monitor_web_info`.`webAddress` AS `webAddress`,`monitor_web_info`.`failTimes` AS `failTimes`,`monitor_web_info`.`totalMonitorTimes` AS `totalMonitorTimes` from `monitor_web_info` where ((`monitor_web_info`.`failTimes` > 3) and (`monitor_web_info`.`enable` <> 0)) */;

--
-- Final view structure for view `failwebaddresstest`
--

/*!50001 DROP TABLE `failwebaddresstest`*/;
/*!50001 DROP VIEW IF EXISTS `failwebaddresstest`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `failwebaddresstest` AS select `monitor_web_info`.`webAddress` AS `webAddress`,`module_info`.`failTimes` AS `failTimes` from (`module_info` join `monitor_web_info`) where ((`module_info`.`enable` = 0) and (`module_info`.`webId` = `monitor_web_info`.`webId`)) */;

--
-- Final view structure for view `mail_need_to_be_send_view`
--

/*!50001 DROP TABLE `mail_need_to_be_send_view`*/;
/*!50001 DROP VIEW IF EXISTS `mail_need_to_be_send_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `mail_need_to_be_send_view` AS select `user_info`.`userId` AS `userId`,`user_info`.`userName` AS `userName`,`user_info`.`mailbox` AS `mail`,`user_module_info`.`userModuleName` AS `userModuleName`,`user_module_info`.`websiteName` AS `websiteName`,`user_module_contents`.`contentId` AS `contentId`,`user_module_contents`.`updateTime` AS `updateTime` from (((`user_info` join `user_module_info`) join `user_receive_info`) join `user_module_contents`) where ((`user_info`.`userId` = `user_module_info`.`userId`) and (`user_receive_info`.`userModuleId` = `user_module_info`.`userModuleId`) and (`user_receive_info`.`receiveType` = 7) and (`user_module_contents`.`userModuleId` = `user_receive_info`.`userModuleId`) and (`user_module_contents`.`alreadySendMail` = 0) and ((`user_receive_info`.`lastSendTime` + `user_receive_info`.`sendFrequency`) < (((now() + interval 2000 year) + interval 1 month) + interval 1 day)) and (curtime() between `user_receive_info`.`startTime` and `user_receive_info`.`stopTime`)) order by `user_info`.`mailbox` */;

--
-- Final view structure for view `module_histoy_num_view`
--

/*!50001 DROP TABLE `module_histoy_num_view`*/;
/*!50001 DROP VIEW IF EXISTS `module_histoy_num_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `module_histoy_num_view` AS select (count(0) / 2) AS `num`,`user_module_info`.`userModuleId` AS `userModuleId` from (`content_buffer` join `user_module_info`) where (`content_buffer`.`moduleId` = `user_module_info`.`moduleId`) group by `user_module_info`.`userModuleId` */;

--
-- Final view structure for view `module_info_show_view`
--

/*!50001 DROP TABLE `module_info_show_view`*/;
/*!50001 DROP VIEW IF EXISTS `module_info_show_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `module_info_show_view` AS select `user_info`.`userId` AS `userId`,`user_module_info`.`websiteName` AS `websiteName`,`user_module_info`.`userModuleName` AS `userModuleName`,`monitor_web_info`.`lastMonitorTime` AS `updateTime`,`monitor_web_info`.`webAddress` AS `webAddress`,`user_module_info`.`userModuleId` AS `userModuleId`,`user_module_info`.`accessType` AS `accessType`,`user_module_info`.`userModuleStyle` AS `userModuleStyle`,`user_module_info`.`subgroupId` AS `subgroupId`,`user_module_info`.`enable` AS `enable`,`user_module_contents`.`userModuleContent` AS `updateContent`,`user_module_contents`.`contentId` AS `contentId`,`user_module_info`.`moduleId` AS `moduleId`,`user_module_contents`.`contentType` AS `contentType`,`monitor_web_info`.`headCssAndScript` AS `headCssAndScript`,`monitor_web_info`.`bodyCssAndScript` AS `bodyCssAndScript`,`user_module_info`.`unCheckPaths` AS `unCheckPaths`,`user_module_contents`.`ignoreByUser` AS `ignoreByUser` from ((((`user_info` join `user_module_info`) join `monitor_web_info`) join `user_module_contents`) join `module_info`) where ((`user_info`.`userId` = `user_module_info`.`userId`) and (`user_module_info`.`enable` <> 0) and (`user_module_info`.`accessType` <> 2) and (`user_module_contents`.`userModuleId` = `user_module_info`.`userModuleId`) and (`user_module_contents`.`contentType` < 2) and (`module_info`.`webId` = `monitor_web_info`.`webId`) and (`user_module_info`.`moduleId` = `module_info`.`moduleId`)) order by `monitor_web_info`.`webId` */;

--
-- Final view structure for view `module_need_to_be_update_view`
--

/*!50001 DROP TABLE `module_need_to_be_update_view`*/;
/*!50001 DROP VIEW IF EXISTS `module_need_to_be_update_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `module_need_to_be_update_view` AS select `monitor_web_info`.`webAddress` AS `webAddress`,`module_info`.`moduleId` AS `moduleId`,`module_info`.`modulePath` AS `path`,(`monitor_web_info`.`lastMonitorTime` + `monitor_web_info`.`monitorFrequency`) AS `lastTime`,`module_info`.`updateContent` AS `updateContent` from (`module_info` join `monitor_web_info`) where ((`monitor_web_info`.`webId` = `module_info`.`webId`) and (`monitor_web_info`.`enable` = 1) and (`module_info`.`enable` = 1) and ((`monitor_web_info`.`lastMonitorTime` + `monitor_web_info`.`monitorFrequency`) < now())) order by (`monitor_web_info`.`lastMonitorTime` + `monitor_web_info`.`monitorFrequency`) */;

--
-- Final view structure for view `module_user_modules_view`
--

/*!50001 DROP TABLE `module_user_modules_view`*/;
/*!50001 DROP VIEW IF EXISTS `module_user_modules_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `module_user_modules_view` AS select `module_info`.`moduleId` AS `moduleId`,`user_module_contents`.`contentId` AS `contentId` from ((`module_info` join `user_module_info`) join `user_module_contents`) where ((`module_info`.`moduleId` = `user_module_info`.`moduleId`) and (`user_module_info`.`userModuleId` = `user_module_contents`.`userModuleId`)) */;

--
-- Final view structure for view `modules_link_view`
--

/*!50001 DROP TABLE `modules_link_view`*/;
/*!50001 DROP VIEW IF EXISTS `modules_link_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `modules_link_view` AS select `user_info`.`userName` AS `userName`,`user_info`.`phone` AS `phone`,`user_receive_info`.`receiveId` AS `receiveId`,`user_module_info`.`userModuleId` AS `userModuleId` from (((`user_info` join `user_module_info`) join `user_receive_info`) join `user_module_contents`) where ((`user_info`.`userId` = `user_module_info`.`userId`) and (`user_receive_info`.`userModuleId` = `user_module_info`.`userModuleId`) and (`user_module_contents`.`userModuleId` = `user_receive_info`.`userModuleId`) and (`user_module_contents`.`alreadySendMessage` = 0) and (`user_receive_info`.`receiveType` = 4) and ((`user_receive_info`.`lastSendTime` + `user_receive_info`.`sendFrequency`) < (((now() + interval 2000 year) + interval 1 month) + interval 1 day)) and (curtime() between `user_receive_info`.`startTime` and `user_receive_info`.`stopTime`)) order by `user_info`.`phone` */;

--
-- Final view structure for view `monitor_by_js_view`
--

/*!50001 DROP TABLE `monitor_by_js_view`*/;
/*!50001 DROP VIEW IF EXISTS `monitor_by_js_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `monitor_by_js_view` AS select `module_info`.`modulePath` AS `modulePath`,`module_info`.`moduleId` AS `moduleId`,`monitor_web_info`.`webAddress` AS `webAddress` from (`module_info` join `monitor_web_info`) where ((`module_info`.`enable` = 0) and (`module_info`.`webId` = `monitor_web_info`.`webId`) and ((`monitor_web_info`.`lastMonitorTime` + `monitor_web_info`.`monitorFrequency`) < now()) and `monitor_web_info`.`webId` in (select min(`monitor_web_info`.`webId`) AS `min(``monitor_web_info``.webId)` from `monitor_web_info` where `monitor_web_info`.`lastMonitorTime` in (select min(`monitor_web_info`.`lastMonitorTime`) AS `min(monitor_web_info.lastMonitorTime)` from (`monitor_web_info` join `module_info`) where (((`monitor_web_info`.`lastMonitorTime` + `monitor_web_info`.`monitorFrequency`) < now()) and (`monitor_web_info`.`webId` = `module_info`.`webId`) and (`module_info`.`enable` = 0))))) */;

--
-- Final view structure for view `receiver_view`
--

/*!50001 DROP TABLE `receiver_view`*/;
/*!50001 DROP VIEW IF EXISTS `receiver_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `receiver_view` AS select `user_module_info`.`userModuleId` AS `userModuleId`,`user_module_info`.`moduleId` AS `moduleId`,`user_module_info`.`monitorType` AS `monitorType`,`user_receive_info`.`receiveType` AS `receiveType` from (`user_module_info` join `user_receive_info`) where ((`user_module_info`.`userModuleId` = `user_receive_info`.`userModuleId`) and (`user_receive_info`.`receiveType` = 9)) */;

--
-- Final view structure for view `user_focus_web_module_view`
--

/*!50001 DROP TABLE `user_focus_web_module_view`*/;
/*!50001 DROP VIEW IF EXISTS `user_focus_web_module_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `user_focus_web_module_view` AS select `monitor_web_info`.`webAddress` AS `webAddress`,`module_info`.`modulePath` AS `modulePath`,`user_module_info`.`userModuleId` AS `userModuleId`,`user_module_info`.`userId` AS `userId` from ((`module_info` join `monitor_web_info`) join `user_module_info`) where ((`user_module_info`.`moduleId` = `module_info`.`moduleId`) and (`module_info`.`webId` = `monitor_web_info`.`webId`)) */;

--
-- Final view structure for view `user_friends_view`
--

/*!50001 DROP TABLE `user_friends_view`*/;
/*!50001 DROP VIEW IF EXISTS `user_friends_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `user_friends_view` AS select `user_info`.`userId` AS `userId`,`user_info`.`userName` AS `userName`,`friends_info`.`friendId` AS `friendId`,`friends_info`.`friendInfo` AS `friendInfo`,`user_friend_subgroup_info`.`groupName` AS `groupName` from ((`user_info` join `friends_info`) join `user_friend_subgroup_info`) where ((`user_info`.`userId` = `friends_info`.`userId`) and (`friends_info`.`subgroupId` = `user_friend_subgroup_info`.`subgroupId`)) */;

--
-- Final view structure for view `user_module_disingore_view`
--

/*!50001 DROP TABLE `user_module_disingore_view`*/;
/*!50001 DROP VIEW IF EXISTS `user_module_disingore_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `user_module_disingore_view` AS select `user_module_info`.`userId` AS `userId`,`user_module_contents`.`contentId` AS `contentId` from (`user_module_contents` join `user_module_info`) where ((`user_module_contents`.`ignoreByUser` = 0) and (`user_module_contents`.`userModuleId` = `user_module_info`.`userModuleId`)) */;

--
-- Final view structure for view `user_module_history_list_view`
--

/*!50001 DROP TABLE `user_module_history_list_view`*/;
/*!50001 DROP VIEW IF EXISTS `user_module_history_list_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `user_module_history_list_view` AS select `user_module_info`.`userModuleId` AS `userModuleId`,`content_buffer`.`moduleId` AS `moduleId`,`content_buffer`.`bufferId` AS `bufferId` from (`user_module_info` join `content_buffer`) where ((`user_module_info`.`moduleId` = `content_buffer`.`moduleId`) and ((`user_module_info`.`monitorType` & `content_buffer`.`updateType`) = `content_buffer`.`updateType`)) */;

--
-- Final view structure for view `user_module_history_view`
--

/*!50001 DROP TABLE `user_module_history_view`*/;
/*!50001 DROP VIEW IF EXISTS `user_module_history_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `user_module_history_view` AS select `user_module_info`.`userModuleStyle` AS `userModuleStyle`,`monitor_web_info`.`headCssAndScript` AS `headCssAndScript`,`monitor_web_info`.`bodyCssAndScript` AS `bodyCssAndScript`,`content_buffer`.`updateTime` AS `updateTime`,`content_buffer`.`content` AS `content`,`content_buffer`.`bufferId` AS `bufferId`,`user_module_info`.`userModuleId` AS `userModuleId` from (((`user_module_info` join `monitor_web_info`) join `module_info`) join `content_buffer`) where ((`module_info`.`webId` = `monitor_web_info`.`webId`) and (`user_module_info`.`moduleId` = `module_info`.`moduleId`) and (`content_buffer`.`moduleId` = `module_info`.`moduleId`)) */;

--
-- Final view structure for view `user_module_updatepaths_view`
--

/*!50001 DROP TABLE `user_module_updatepaths_view`*/;
/*!50001 DROP VIEW IF EXISTS `user_module_updatepaths_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `user_module_updatepaths_view` AS select `user_info`.`userId` AS `userId`,`monitor_web_info`.`webAddress` AS `webAddress`,`module_info`.`modulePath` AS `modulePath`,`user_module_contents`.`contentId` AS `contentId`,`user_module_contents`.`userModuleId` AS `userModuleId`,`user_module_contents`.`userModuleContent` AS `userModuleContent`,`user_module_contents`.`contentType` AS `contentType`,`user_module_info`.`unCheckPaths` AS `unCheckPaths` from ((((`user_info` join `module_info`) join `monitor_web_info`) join `user_module_info`) join `user_module_contents`) where ((`user_info`.`userId` = `user_module_info`.`userId`) and (`user_module_info`.`moduleId` = `module_info`.`moduleId`) and (`module_info`.`webId` = `monitor_web_info`.`webId`) and (`user_module_info`.`userModuleId` = `user_module_contents`.`userModuleId`)) */;

--
-- Final view structure for view `user_space_module_info_show_view`
--

/*!50001 DROP TABLE `user_space_module_info_show_view`*/;
/*!50001 DROP VIEW IF EXISTS `user_space_module_info_show_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `user_space_module_info_show_view` AS select `user_info`.`userId` AS `userId`,`user_module_info`.`websiteName` AS `websiteName`,`user_module_info`.`userModuleName` AS `userModuleName`,`monitor_web_info`.`webAddress` AS `webAddress`,`user_module_info`.`userModuleId` AS `userModuleId`,`user_module_info`.`accessType` AS `accessType`,`user_module_info`.`userModuleStyle` AS `userModuleStyle`,`user_module_info`.`subgroupId` AS `subgroupId`,`user_module_info`.`enable` AS `enable`,`user_module_info`.`moduleId` AS `moduleId`,`monitor_web_info`.`headCssAndScript` AS `headCssAndScript`,`monitor_web_info`.`bodyCssAndScript` AS `bodyCssAndScript`,`user_module_info`.`unCheckPaths` AS `unCheckPaths`,`module_info`.`updateContent` AS `updateContent` from (((`user_info` join `user_module_info`) join `monitor_web_info`) join `module_info`) where ((`user_info`.`userId` = `user_module_info`.`userId`) and (`user_module_info`.`enable` <> 0) and (`user_module_info`.`accessType` <> 2) and (`module_info`.`webId` = `monitor_web_info`.`webId`) and (`user_module_info`.`moduleId` = `module_info`.`moduleId`)) order by `monitor_web_info`.`webId` */;

--
-- Final view structure for view `usermodule_view`
--

/*!50001 DROP TABLE `usermodule_view`*/;
/*!50001 DROP VIEW IF EXISTS `usermodule_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `usermodule_view` AS select `user_module_info`.`userId` AS `userId`,`user_module_info`.`userModuleName` AS `userModuleName`,`user_module_info`.`moduleId` AS `moduleId`,`user_module_info`.`websiteName` AS `websiteName`,`monitor_web_info`.`webAddress` AS `webAddress` from ((`user_module_info` join `monitor_web_info`) join `module_info`) where ((`user_module_info`.`moduleId` = `module_info`.`moduleId`) and (`module_info`.`webId` = `monitor_web_info`.`webId`)) order by `monitor_web_info`.`webId` */;

--
-- Final view structure for view `web_user_modules_view`
--

/*!50001 DROP TABLE `web_user_modules_view`*/;
/*!50001 DROP VIEW IF EXISTS `web_user_modules_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `web_user_modules_view` AS select `monitor_web_info`.`webId` AS `webId`,`user_module_contents`.`contentId` AS `contentId` from (((`monitor_web_info` join `module_info`) join `user_module_contents`) join `user_module_info`) where ((`monitor_web_info`.`webId` = `module_info`.`webId`) and (`module_info`.`moduleId` = `user_module_info`.`moduleId`) and (`user_module_info`.`userModuleId` = `user_module_contents`.`userModuleId`)) */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-05-02  8:10:33
