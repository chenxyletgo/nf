DROP DATABASE nf;
CREATE DATABASE IF NOT EXISTS nf DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
USE nf;

-- 管理员账号 admin@no.com 密码 1234567a

-- 用户表
CREATE TABLE `t_user` (
  `id` char(19) NOT NULL COMMENT '用户id',
  `name` char(16) NOT NULL COMMENT '用户昵称',
  `salt` char(6) NOT NULL COMMENT '密码加盐',
  `password` char(32) NOT NULL COMMENT '密码',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `del_unique_key` varchar(19) NOT NULL DEFAULT '0' COMMENT '解决逻辑删除和唯一约束冲突',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `message` varchar(255) DEFAULT NULL COMMENT '留言',
  `sex` int(11) NOT NULL DEFAULT '2' COMMENT '性别，0女，1男，2保密',
  `habitual_residence` varchar(255) DEFAULT NULL COMMENT '居住地',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '账号状态，0正常，1禁言，2封禁，3管理员',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `modify_time` datetime NOT NULL COMMENT '修改日期',
  `is_deleted` int(11) DEFAULT '0' COMMENT '逻辑删除，0存在，1不存在',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_ema_del` (`email`,`del_unique_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `t_user` VALUE
('0000000000000000000','admin','hM6TDb','71be462b6f92adc1b5d571f2c7a2f568','admin@no.com','0',NULL,NULL,2,NULL,NULL,3,'2023-04-14 15:24:37','2023-04-14 15:24:37',0);
-- 密码 1234567a
-- 关系表 用户(发布者)-用户(关注者)
CREATE TABLE `t_fan` (
  `id` char(19) NOT NULL COMMENT '关系id',
  `b_user_id` char(19) NOT NULL COMMENT '发布者id',
  `f_user_id` char(19) NOT NULL COMMENT '关注者id',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `modify_time` datetime NOT NULL COMMENT '修改日期',
  `is_deleted` int(11) DEFAULT '0' COMMENT '逻辑删除，0存在，1不存在',
  PRIMARY KEY (`id`),
  KEY `fk_fan_user_b` (`b_user_id`),
  KEY `fk_fan_user_f` (`f_user_id`),
  CONSTRAINT `fk_fan_user_b` FOREIGN KEY (`b_user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `fk_fan_user_f` FOREIGN KEY (`f_user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 分类表（有一级，二级之分）
CREATE TABLE `t_category` (
  `id` char(19) NOT NULL COMMENT '分类id',
  `name` varchar(8) NOT NULL COMMENT '分类名',
  `path_name` varchar(16) DEFAULT NULL COMMENT '路径名',
  `father_id` varchar(19) NOT NULL DEFAULT '0' COMMENT '0表示一级分类，非0表示二级分类',
  `del_unique_key` varchar(19) NOT NULL DEFAULT '0' COMMENT '解决逻辑删除和唯一约束冲突',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `modify_time` datetime NOT NULL COMMENT '修改日期',
  `is_deleted` int(11) DEFAULT '0' COMMENT '逻辑删除，0存在，1不存在',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_pat` (`path_name`, `del_unique_key`),
  UNIQUE KEY `uni_nam_fat_del` (`name`,`father_id`,`del_unique_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 文章表
CREATE TABLE `t_article` (
  `id` char(19) NOT NULL COMMENT '文章id',
  `title` varchar(30) NOT NULL COMMENT '标题',
  `text` text NOT NULL COMMENT '正文',
  `cover` varchar(255) DEFAULT NULL COMMENT '封面',
  `user_id` char(19) NOT NULL COMMENT '发布者',
  `watch` int(11) NOT NULL DEFAULT '0' COMMENT '观看次数',
  `first_category_id` char(19) DEFAULT NULL COMMENT '一级分类',
  `second_category_id` char(19) DEFAULT NULL COMMENT '二级分类',
  `level` int(11) NOT NULL DEFAULT '0' COMMENT '文章级别，0默认，1推荐，2置顶（轮播图）',
  `state` int(11) NOT NULL COMMENT '文章状态，0草稿，1审核中，2审核通过，3审核未通过',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `modify_time` datetime NOT NULL COMMENT '修改日期',
  `is_deleted` int(11) DEFAULT '0' COMMENT '逻辑删除，0存在，1不存在',
  PRIMARY KEY (`id`),
  KEY `fk_aritcle_user` (`user_id`),
  KEY `fk_aritcle_fcategory` (`first_category_id`),
  KEY `fk_aritcle_scategory` (`second_category_id`),
  CONSTRAINT `fk_aritcle_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `fk_aritcle_fcategory` FOREIGN KEY (`first_category_id`) REFERENCES `t_category` (`id`),
  CONSTRAINT `fk_aritcle_scategory` FOREIGN KEY (`second_category_id`) REFERENCES `t_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 关系表 文章-用户(喜欢者)
CREATE TABLE `t_like_a` (
  `id` char(19) NOT NULL COMMENT '关系id',
  `article_id` char(19) NOT NULL COMMENT '文章id',
  `user_id` char(19) NOT NULL COMMENT '喜欢者id',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `modify_time` datetime NOT NULL COMMENT '修改日期',
  `is_deleted` int(11) DEFAULT '0' COMMENT '逻辑删除，0存在，1不存在',
  PRIMARY KEY (`id`),
  KEY `fk_likea_article` (`article_id`),
  KEY `fk_likea_user` (`user_id`),
  CONSTRAINT `fk_likea_article` FOREIGN KEY (`article_id`) REFERENCES `t_article` (`id`),
  CONSTRAINT `fk_likea_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 评论表
CREATE TABLE `t_comment` (
  `id` char(19) NOT NULL COMMENT '评论id',
  `words` varchar(255) NOT NULL COMMENT '评论内容',
  `reply_type` int(11) NOT NULL COMMENT '回复对象类型，0文章，1评论',
  `reply_id` char(19) NOT NULL COMMENT '该字段根据reply_type字段值决定意义',
  `s_user_id` char(19) NOT NULL COMMENT '发起者',
  `r_user_id` char(19) DEFAULT NULL COMMENT '接收者',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `modify_time` datetime NOT NULL COMMENT '修改日期',
  `is_deleted` int(11) DEFAULT '0' COMMENT '逻辑删除，0存在，1不存在',
  PRIMARY KEY (`id`),
  KEY `fk_comment_suser` (`s_user_id`),
  KEY `fk_comment_ruser` (`r_user_id`),
  CONSTRAINT `fk_comment_suser` FOREIGN KEY (`s_user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `fk_comment_ruser` FOREIGN KEY (`r_user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 关系表 评论-用户(对评论持有态度者)
CREATE TABLE `t_like_c` (
  `id` char(19) NOT NULL COMMENT '关系id',
  `comment_id` char(19) NOT NULL COMMENT '评论id',
  `user_id` char(19) NOT NULL COMMENT '持有态度者id',
  `attitude` int(11) NOT NULL COMMENT '0踩，1赞',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `modify_time` datetime NOT NULL COMMENT '修改日期',
  `is_deleted` int(11) DEFAULT '0' COMMENT '逻辑删除，0存在，1不存在',
  PRIMARY KEY (`id`),
  KEY `fk_likec_comment` (`comment_id`),
  KEY `fk_likec_user` (`user_id`),
  CONSTRAINT `fk_likec_comment` FOREIGN KEY (`comment_id`) REFERENCES `t_comment` (`id`),
  CONSTRAINT `fk_likec_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 系统消息表
CREATE TABLE `t_message` (
  `id` char(19) NOT NULL COMMENT '消息id',
  `user_id` char(19) NOT NULL COMMENT '所属用户的id',
  `text` char(255) NOT NULL COMMENT '正文',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '0未读，1已读',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `modify_time` datetime NOT NULL COMMENT '修改日期',
  `is_deleted` int(11) DEFAULT '0' COMMENT '逻辑删除，0存在，1不存在',
  PRIMARY KEY (`id`),
  KEY `fk_message_user` (`user_id`),
  CONSTRAINT `fk_message_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;