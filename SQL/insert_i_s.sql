
USE `数据库名称`;

/*Table structure for table `is_buglogs` */

DROP TABLE IF EXISTS `is_buglogs`;

CREATE TABLE `is_buglogs` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `error` varchar(500) COLLATE utf8_general_ci NOT NULL,
  `baseinfo` varchar(500) COLLATE utf8_general_ci DEFAULT NULL,
  `time` varchar(50) COLLATE utf8_general_ci DEFAULT NULL,
  `totalmemory` varchar(50) COLLATE utf8_general_ci DEFAULT NULL,
  `availmemory` varchar(50) COLLATE utf8_general_ci DEFAULT NULL,
  `cpuinfo` varchar(100) COLLATE utf8_general_ci DEFAULT NULL,
  `otherinfo` varchar(500) COLLATE utf8_general_ci DEFAULT NULL,
  `description` varchar(500) COLLATE utf8_general_ci DEFAULT NULL,
  `note` varchar(500) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `is_buglogs` */

/*Table structure for table `is_feedback` */

DROP TABLE IF EXISTS `is_feedback`;

CREATE TABLE `is_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `feed` varchar(300) COLLATE utf8_general_ci NOT NULL COMMENT '反馈内容',
  `postdate` date DEFAULT NULL COMMENT '反馈时间',
  `user_id` int(11) DEFAULT NULL COMMENT '提交反馈的用户',
  `description` varchar(500) COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `note` varchar(500) COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `is_feedback` */

insert  into `is_feedback`(`id`,`feed`,`postdate`,`user_id`,`description`,`note`) values (1,'','0000-00-00',0,'',''),(2,'123456789','2016-07-22',0,'',''),(3,'作者英雄盖世勇猛无敌','2016-07-22',0,'','');

/*Table structure for table `is_users` */

DROP TABLE IF EXISTS `is_users`;

CREATE TABLE `is_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(16) COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `pswd` varchar(30) COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `age` int(2) DEFAULT NULL COMMENT '年龄',
  `sex` int(1) DEFAULT NULL COMMENT '性别，0女，1男，2妖',
  `alone` tinyint(1) DEFAULT '1' COMMENT '单身',
  `address` varchar(90) COLLATE utf8_general_ci DEFAULT NULL COMMENT '地址',
  `phone` int(11) DEFAULT NULL COMMENT '电话',
  `email` varchar(60) COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `qq` int(11) DEFAULT NULL COMMENT 'QQ号码',
  `weixin` varchar(60) COLLATE utf8_general_ci DEFAULT NULL COMMENT '微信',
  `momo` varchar(60) COLLATE utf8_general_ci DEFAULT NULL COMMENT '陌陌',
  `weibo` varchar(60) COLLATE utf8_general_ci DEFAULT NULL COMMENT '微博',
  `blog` varchar(60) COLLATE utf8_general_ci DEFAULT NULL COMMENT '博客地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `is_users` */

insert  into `is_users`(`id`,`name`,`pswd`,`age`,`sex`,`alone`,`address`,`phone`,`email`,`qq`,`weixin`,`momo`,`weibo`,`blog`) values (1,'alizee','2af11883902b7b7bb7e4e66f0b7abe',29,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'Eminem','e07ee40904c450ec2cfd6ac2454489',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,'草泥马','62a6acf5c1103570275018ccbe58ed',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,'羊驼','17f6aeb55a8d4fdd18bfe8f36ec40f',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,'吐槽之王','11ce5b2c6e53cc4d066c3bdf109092',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,'admin','67f43efc5701784db1504e4993d7e3',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,'管理员','7a235d3c387627664cd1a70e786d52',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(8,'root','38b04e559910164fed598ed76efddc',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(9,'管理员','dd',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10,'喷神','ff',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(84,'老王',NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(85,'老王夫人',NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(87,'风姿卓越的码子',NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(88,'码子',NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `is_company` */

DROP TABLE IF EXISTS `is_company`;

CREATE TABLE `is_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公司主键',
  `name` varchar(60) COLLATE utf8_general_ci DEFAULT NULL COMMENT '公司名称',
  `address` varchar(90) COLLATE utf8_general_ci DEFAULT NULL COMMENT '地址',
  `description` varchar(500) COLLATE utf8_general_ci DEFAULT NULL COMMENT '公司描述',
  `note` varchar(500) COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `is_company` */

insert  into `is_company`(`id`,`name`,`address`,`description`,`note`) values (1,'0','0','0','0'),(2,'哇嘎嘎','不知道什么鬼地方',NULL,NULL),(3,'绝了矫情','山上的庙里',NULL,NULL),(4,'牛逼的集合','北京郊区的旁边的12环的东南角',NULL,NULL),(5,'量下你妹啊','11路车所在地',NULL,NULL),(6,'专业抱大腿','1省2市3县4镇5村',NULL,NULL),(7,'有门儿','没门儿大院',NULL,NULL),(8,'宇宙无敌掌门狗','美利坚',NULL,NULL),(9,'abc','兔子洞',NULL,NULL),(15,'abcl','','',''),(17,'吧豆吧豆','西二旗','',''),(18,'亚里亚力','xx','',''),(19,'腾讯','','',''),(20,'365','总部','','');
/*Table structure for table `is_label` */

DROP TABLE IF EXISTS `is_label`;

CREATE TABLE `is_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_id` int(11) NOT NULL COMMENT '所属公司id',
  `label` varchar(8) COLLATE utf8_general_ci NOT NULL COMMENT '标签',
  `description` varchar(500) COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `note` varchar(500) COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `is_label_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `is_company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `is_label` */

insert  into `is_label`(`id`,`company_id`,`label`,`description`,`note`) values (1,2,'绝逼',NULL,NULL),(2,2,'靠谱',NULL,NULL),(3,1,'服了',NULL,NULL),(4,1,'神马',NULL,NULL),(5,3,'呵呵',NULL,NULL),(6,3,'靠',NULL,NULL),(7,6,'可以',NULL,NULL),(8,8,'美女多',NULL,NULL),(9,8,'颜值高',NULL,NULL),(10,8,'科技感',NULL,NULL),(11,8,'高大上',NULL,NULL),(12,4,'有鬼',NULL,NULL),(13,5,'有鬼',NULL,NULL),(14,7,'阴森',NULL,NULL),(15,7,'憋屈',NULL,NULL),(16,4,'诈骗',NULL,NULL);

/*Table structure for table `is_station` */

DROP TABLE IF EXISTS `is_station`;

CREATE TABLE `is_station` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(60) COLLATE utf8_general_ci NOT NULL COMMENT '岗位名称',
  `company_id` int(11) NOT NULL COMMENT '所属公司的id',
  `description` varchar(500) COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `note` varchar(500) COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `is_station_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `is_company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `is_station` */

insert  into `is_station`(`id`,`name`,`company_id`,`description`,`note`) values (1,'Android开发',1,NULL,NULL),(2,'Java开发',1,NULL,NULL),(3,'.net',4,NULL,NULL),(4,'java',4,NULL,NULL),(5,'Android开发',2,NULL,NULL),(6,'项目经理',4,NULL,NULL),(8,'部门经理',8,NULL,NULL),(9,'前台',3,NULL,NULL),(10,'翻译',1,NULL,NULL),(11,'保洁',6,NULL,NULL),(12,'保安',6,NULL,NULL),(14,'人事助理',5,NULL,NULL),(15,'销售',5,NULL,NULL),(16,'叉车司机',5,NULL,NULL),(17,'前台',7,NULL,NULL),(18,'人事助理',7,NULL,NULL),(19,'靠妹',15,NULL,NULL),(21,'abc',15,'',''),(22,'竞价',17,'',''),(23,'竞价排名',17,'',''),(24,'首席执行官',18,'',''),(25,'CEO',19,'',''),(26,'沐浴露助理',18,'',''),(27,'反编译',20,'',''),(28,'破解工程师',20,'',''),(29,'内务助理',17,'','');

/*Table structure for table `is_article` */

DROP TABLE IF EXISTS `is_article`;

CREATE TABLE `is_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '作者，user表id',
  `title` varchar(60) COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `article` varchar(1500) COLLATE utf8_general_ci NOT NULL COMMENT '文章正文',
  `date_add` date NOT NULL COMMENT '发布日期。优先级标识',
  `praise_count` int(11) DEFAULT NULL COMMENT '赞',
  `recommend` int(1) NOT NULL DEFAULT '0' COMMENT '推荐状态，1为推荐',
  `enable` int(1) NOT NULL DEFAULT '0' COMMENT '是否审核通过，1为通过',
  `description` varchar(500) COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `note` varchar(500) COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `is_article_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `is_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `is_article` */

insert  into `is_article`(`id`,`user_id`,`title`,`article`,`date_add`,`praise_count`,`recommend`,`enable`,`description`,`note`) values (1,1,'神枪手','咱们都是神枪手，后文省略1000字','2016-06-13',1011,0,1,NULL,NULL),(2,6,'嘎嘎','妈蛋，这天都快热死宝宝了','2016-06-27',32481,1,1,NULL,NULL),(3,6,'再来一篇','这是个口水文，没有什么实际内容是的啦房间爱丽丝的咖啡加拉斯的；附近； 了敬爱了看得见的法律卡时间段法国看了阿拉的世界观法了卡斯的了；爱神的箭个拉屎的看过就发生的了；落实到房间啊；偶家了看电视剧过分了；卡死的了空间法法师的路口；拉大锯刚看了就发啦到家了房间阿萨德凉快的法师法看电视了的萨拉快过节了的撒娇歌卢卡斯的见鬼了防卡死的见喷入噶的接过来偶尔， 是的看了就噶的裸四口感觉到了是两个技能 大房间少打了看过就凉快的世界观电缆附件阿斯兰。','2016-04-11',32469,1,1,NULL,NULL),(4,88,'一个字','11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111','2016-07-26',48,0,1,'',''),(5,6,'飙一个','12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123','2016-07-26',146,0,1,'','');

/*Table structure for table `is_spitslot` */

DROP TABLE IF EXISTS `is_spitslot`;

CREATE TABLE `is_spitslot` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'int主键非空自增长',
  `company_id` int(11) NOT NULL COMMENT '公司ID',
  `station_id` int(11) NOT NULL COMMENT '岗位id',
  `user_id` int(11) DEFAULT NULL COMMENT '面试者，user表id',
  `date_view` date DEFAULT NULL COMMENT '面试时间',
  `description` varchar(160) COLLATE utf8_general_ci NOT NULL COMMENT '口水，面试描述',
  `praise_count` int(11) DEFAULT NULL COMMENT '赞同',
  `record_time` date DEFAULT NULL COMMENT '记录日期',
  `note` varchar(500) COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `company_id` (`company_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `is_spitslot_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `is_company` (`id`),
  CONSTRAINT `is_spitslot_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `is_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

/*Data for the table `is_spitslot` */

insert  into `is_spitslot`(`id`,`company_id`,`station_id`,`user_id`,`date_view`,`description`,`praise_count`,`record_time`,`note`) values (1,5,14,5,'2016-07-13','面试官是大傻叉',111,NULL,NULL),(2,5,16,2,'2016-07-13','前台妹子非常靓',NULL,NULL,NULL),(3,3,9,1,'2016-05-09','人真多啊',NULL,NULL,NULL),(4,8,8,3,'2016-07-05','太远了',NULL,NULL,NULL),(5,6,2,2,'2016-07-07','又入火坑',NULL,NULL,NULL),(6,7,18,4,'2016-07-08','咳咳，没的说',NULL,NULL,NULL),(7,4,6,8,'2016-07-12','什么情况啊，等了半小时',NULL,NULL,NULL),(8,1,11,6,'2016-07-16','周末在面试，你猜什么情况',NULL,NULL,NULL),(9,1,11,7,'2016-07-04','周末确实在面试，哈哈哈，鸟事',NULL,NULL,NULL),(50,17,23,84,'2016-07-20','最不要脸的公司祸害平民',0,'2016-07-20',''),(51,17,29,84,'2016-07-20','是啊，纯鸡毛流氓，不可信',0,'2016-07-20',''),(52,17,23,84,'2016-07-20','就是就是，鸡毛骗子公司',0,'2016-07-20',''),(53,7,18,84,'2016-07-20','不清楚没去过不评论',0,'2016-07-20',''),(54,18,24,84,'2016-07-20','12345678',0,'2016-07-20',''),(55,19,25,85,'2016-07-21','说了你不信，非让我干老总。可我喜欢男的啊！',0,'2016-07-21',''),(56,18,26,85,'2016-07-21','沐浴露是我兄弟，亲兄弟',0,'2016-07-21',''),(57,20,27,85,'2016-07-21','破解能力咱第一，忽悠人却不行',0,'2016-07-21',''),(58,20,28,85,'2016-07-21','12345678搞定',0,'2016-07-21',''),(59,17,29,85,'2016-07-21','再次12345678',0,'2016-07-21',''),(60,17,29,85,'2016-07-21','好，排名2也是真2',0,'2016-07-21',''),(61,17,29,87,'2016-07-23','呵呵了，一起吐槽吧豆吧豆',0,'2016-07-23',''),(62,17,29,87,'2016-07-24','我靠我靠我靠我靠我靠',0,'2016-07-24',''),(63,17,29,87,'2016-07-24','看看犒劳犒劳老K就咯啦咯啦咯啦',0,'2016-07-24',''),(64,17,29,87,'2016-07-24','竞价竞价竞价竞价啦咯啦咯啦咯啦咯',0,'2016-07-24',''),(65,18,26,88,'2016-07-24','尼玛话多尽喷水了',0,'2016-07-24',''),(66,17,29,88,'2016-07-24','流氓不留名，可惜太有名',0,'2016-07-24',''),(67,3,9,88,'2016-07-24','我靠！8888888个字',0,'2016-07-24','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
