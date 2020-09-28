SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `path` varchar(64) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `name_zh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `icon_cls` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `component` varchar(64) DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Table structure for admin_permission
-- ----------------------------
DROP TABLE IF EXISTS `admin_permission`;
CREATE TABLE `admin_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `desc_` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name_zh` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for admin_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_menu`;
CREATE TABLE `admin_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `rid` bigint DEFAULT NULL,
  `mid` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Table structure for admin_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_permission`;
CREATE TABLE `admin_role_permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `rid` int(20) DEFAULT NULL,
  `pid` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_role_permission_role_1` (`rid`),
  KEY `fk_role_permission_permission_1` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for admin_user_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_role`;
CREATE TABLE `admin_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uid` bigint DEFAULT NULL,
  `rid` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_operator_role_operator_1` (`uid`),
  KEY `fk_operator_role_role_1` (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cover` varchar(255) DEFAULT '',
  `title` varchar(255) NOT NULL DEFAULT '',
  `author` varchar(255) DEFAULT '',
  `date` varchar(20) DEFAULT '',
  `press` varchar(255) DEFAULT '',
  `abs` varchar(255) DEFAULT NULL,
  `cid` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_book_category_on_cid` (`cid`),
  CONSTRAINT `fk_book_category_on_cid` FOREIGN KEY (`cid`) REFERENCES `category` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for jotter_article
-- ----------------------------
DROP TABLE IF EXISTS `jotter_article`;
CREATE TABLE `jotter_article` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `article_title` varchar(255) DEFAULT NULL,
  `article_content_html` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `article_content_md` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `article_abstract` varchar(255) DEFAULT NULL,
  `article_cover` varchar(255) DEFAULT NULL,
  `article_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8;
alter table user modify username char(255) not null comment '登录名';

alter table user modify password varchar(255) null comment '密码';

alter table user modify salt varchar(255) null comment '盐';

alter table user modify name varchar(255) null comment '昵称';

alter table user modify phone varchar(255) null comment '手机号';

alter table user modify email varchar(255) null comment '邮箱';

alter table user modify enabled tinyint(1) default 1 null comment '是否启用 1：启用 0：禁用';
alter table user comment '用户表';

alter table category modify name varchar(255) not null comment '分类名称';

alter table category comment '图书类别';

alter table book modify cover varchar(255) default '' null comment '封面链接';

alter table book modify title varchar(255) default '' not null comment '书名';

alter table book modify author varchar(255) default '' null comment '作者';

alter table book modify date varchar(20) default '' null comment '出版日期';

alter table book modify press varchar(255) default '' null comment '出版社';

alter table book modify abs varchar(255) null comment '简介';

alter table book modify cid bigint null comment '类别id';

alter table book comment '图书';

alter table admin_menu modify path varchar(64) null comment '路径';

alter table admin_menu modify name varchar(64) null comment '菜单名en';

alter table admin_menu modify name_zh varchar(64) null comment '菜单名zn';

alter table admin_menu modify icon_cls varchar(64) null comment '图标';

alter table admin_menu modify component varchar(64) null comment '组件路径';

alter table admin_menu modify parent_id bigint null comment '父节点id';

alter table admin_menu comment '菜单';

alter table admin_role modify name varchar(100) null comment '角色en';

alter table admin_role modify name_zh varchar(100) null comment '角色zh';

alter table admin_role modify enabled tinyint(1) null comment '是否可用 1：可用 0：禁止';

alter table admin_role comment '后台角色';
alter table admin_permission modify id bigint auto_increment comment '权限/菜单id';

alter table admin_permission modify name varchar(100) null comment '权限/菜单名';

alter table admin_permission modify desc_ varchar(100) null comment '描述';

alter table admin_permission modify url varchar(100) null comment '地址';

alter table admin_permission comment '权限/菜单';

alter table admin_menu modify path varchar(64) null comment '与Vue路由中的path对应，及地址路径';

alter table admin_menu modify name varchar(64) null comment '与Vue路由中的name属性对应';

alter table admin_menu modify name_zh varchar(64) null comment '中文名称，用于渲染导航栏（菜单名）界面';

alter table admin_menu modify component varchar(64) null comment '组件名，用于解析路由对应组件';

alter table admin_user_role modify uid bigint null comment '用户id';

alter table admin_user_role modify rid bigint null comment '角色id';

alter table admin_user_role comment '后台用户角色关联表';

alter table admin_role_menu modify rid bigint null comment '角色id';

alter table admin_role_menu modify mid bigint null comment '菜单id';

alter table admin_role_menu comment '角色菜单关联关系';

alter table admin_menu
	add sequence int not null comment '排序';

alter table admin_menu
	add create_time timestamp default current_timestamp() not null comment '创建时间';

alter table admin_menu
	add update_time timestamp default current_timestamp() not null comment '更新时间';

alter table admin_permission
	add create_time timestamp default current_timestamp() not null comment '创建时间';

alter table admin_permission
	add update_time timestamp default current_timestamp() not null comment '更新时间';

alter table admin_role
	add create_time timestamp default current_timestamp() not null comment '创建时间';

alter table admin_role
	add update_time timestamp default current_timestamp() not null comment '更新时间';

alter table admin_role_menu
	add create_time timestamp default current_timestamp() not null comment '创建时间';

alter table admin_role_menu
	add update_time timestamp default current_timestamp() not null comment '更新时间';

alter table admin_role_permission
	add create_time timestamp default current_timestamp() not null comment '创建时间';

alter table admin_role_permission
	add update_time timestamp default current_timestamp() not null comment '更新时间';

alter table admin_user_role
	add create_time timestamp default current_timestamp() not null comment '创建时间';

alter table admin_user_role
	add update_time timestamp default current_timestamp() not null comment '更新时间';
alter table book
	add create_time timestamp default current_timestamp() not null comment '创建时间';

alter table book
	add update_time timestamp default current_timestamp() not null comment '更新时间';
alter table category
	add create_time timestamp default current_timestamp() not null comment '创建时间';

alter table category
	add update_time timestamp default current_timestamp() not null comment '更新时间';
alter table jotter_article
	add create_time timestamp default current_timestamp() not null comment '创建时间';

alter table jotter_article
	add update_time timestamp default current_timestamp() not null comment '更新时间';
alter table user
	add create_time timestamp default current_timestamp() not null comment '创建时间';

alter table user
	add update_time timestamp default current_timestamp() not null comment '更新时间';
