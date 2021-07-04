-- ----------------------------
-- Records of admin_menu
-- ----------------------------
INSERT INTO `admin_menu` VALUES (1, '/nested', 'Nested', 'Nested', 'nested', 'Layout', 0, 1, '2020-10-10 22:24:50', '2020-10-10 22:24:50', 0, '/nested/menu1');
INSERT INTO `admin_menu` VALUES (2, 'menu1', 'Menu1', 'Menu1', 'nested', 'nested/menu1/index', 1, 3, '2020-10-10 22:25:43', '2020-10-10 22:25:43', 0, NULL);
INSERT INTO `admin_menu` VALUES (3, '/form', 'Form', 'Form', 'form', 'Layout', 0, 4, '2020-10-11 12:20:26', '2020-10-11 12:20:26', 0, NULL);
INSERT INTO `admin_menu` VALUES (5, 'menu1-1', 'Menu1-1', 'Menu1-1', NULL, 'nested/menu1/menu1-1', 2, 6, '2020-10-11 12:24:13', '2020-10-11 12:24:13', 0, NULL);
INSERT INTO `admin_menu` VALUES (6, '/system', 'System', '系统管理', 'table', 'Layout', 0, 7, '2020-10-11 13:12:26', '2020-10-11 13:12:26', 0, NULL);
INSERT INTO `admin_menu` VALUES (7, 'user', 'User', '用户管理', 'nested', 'system/user/index', 6, 8, '2020-10-11 13:13:13', '2020-10-11 13:13:13', 0, NULL);


-- ----------------------------
-- Records of admin_permission
-- ----------------------------
INSERT INTO `admin_permission` VALUES (1, 'users_management', '用户管理', '/api/admin/user', '2020-10-09 20:41:01', '2020-10-09 20:41:01');
INSERT INTO `admin_permission` VALUES (2, 'roles_management', '角色管理', '/api/admin/role', '2020-10-09 20:41:01', '2020-10-09 20:41:01');
INSERT INTO `admin_permission` VALUES (3, 'content_management', '内容管理', '/api/admin/content', '2020-10-09 20:41:01', '2020-10-09 20:41:01');

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES (1, 'sysAdmin', '系统管理员', 1, '2020-10-09 20:41:13', '2020-10-09 20:41:13');
INSERT INTO `admin_role` VALUES (2, 'contentManager', '内容管理员', 1, '2020-10-09 20:41:13', '2020-10-09 20:41:13');
INSERT INTO `admin_role` VALUES (3, 'visitor', '访客', 1, '2020-10-09 20:41:13', '2020-10-09 20:41:13');
INSERT INTO `admin_role` VALUES (9, 'test', '测试角色', 1, '2020-10-09 20:41:13', '2020-10-09 20:41:13');

-- ----------------------------
-- Records of admin_role_menu
-- ----------------------------
INSERT INTO `admin_role_menu` VALUES (19, 4, 1, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (20, 4, 2, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (21, 3, 1, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (22, 3, 2, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (23, 9, 1, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (24, 9, 2, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (77, 2, 1, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (78, 2, 2, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (79, 2, 4, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (80, 2, 8, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (81, 2, 9, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (82, 2, 10, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (121, 1, 1, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (122, 1, 2, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (123, 1, 3, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (124, 1, 6, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (125, 1, 7, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (126, 1, 4, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (127, 1, 8, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (128, 1, 9, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (129, 1, 10, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_menu` VALUES (130, 1, 5, '2020-10-09 20:53:51', '2020-10-09 20:53:51');

-- ----------------------------
-- Records of admin_role_permission
-- ----------------------------
INSERT INTO `admin_role_permission` VALUES (1, 1, 1, '2020-10-09 20:52:07', '2020-10-09 20:52:07');
INSERT INTO `admin_role_permission` VALUES (83, 5, 3, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_permission` VALUES (97, 2, 3, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_permission` VALUES (108, 1, 1, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_permission` VALUES (109, 1, 2, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_role_permission` VALUES (110, 1, 3, '2020-10-09 20:53:51', '2020-10-09 20:53:51');

-- ----------------------------
-- Records of admin_user_role
-- ----------------------------
INSERT INTO `admin_user_role` VALUES (1, 1, 1, '2020-10-09 20:51:17', '2020-10-09 20:51:17');
INSERT INTO `admin_user_role` VALUES (2, 2, 1, '2020-10-09 20:51:23', '2020-10-09 20:51:23');
INSERT INTO `admin_user_role` VALUES (3, 3, 1, '2020-10-09 20:51:32', '2020-10-09 20:51:32');
INSERT INTO `admin_user_role` VALUES (40, 24, 2, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_user_role` VALUES (63, 3, 2, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_user_role` VALUES (64, 1, 1, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_user_role` VALUES (65, 2, 3, '2020-10-09 20:53:51', '2020-10-09 20:53:51');
INSERT INTO `admin_user_role` VALUES (66, 2, 9, '2020-10-09 20:53:51', '2020-10-09 20:53:51');

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '文学', '2020-10-09 20:54:03', '2020-10-09 20:54:03');
INSERT INTO `category` VALUES (2, '流行', '2020-10-09 20:54:03', '2020-10-09 20:54:03');
INSERT INTO `category` VALUES (3, '文化', '2020-10-09 20:54:03', '2020-10-09 20:54:03');
INSERT INTO `category` VALUES (4, '生活', '2020-10-09 20:54:03', '2020-10-09 20:54:03');
INSERT INTO `category` VALUES (5, '经管', '2020-10-09 20:54:03', '2020-10-09 20:54:03');
INSERT INTO `category` VALUES (6, '科技', '2020-10-09 20:54:03', '2020-10-09 20:54:03');

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '35b9529f89cfb9b848060ca576237e17', '8O+vDNr2sI3N82BI31fu1A==', '管理员', '12312312312', 'evan_nightly@163.com', 1, '2020-10-09 20:39:17', '2020-10-09 20:39:17');
INSERT INTO `user` VALUES (2, 'test', '85087738b6c1e1d212683bfafc163853', 'JBba3j5qRykIPJQYTNNH9A==', '测试', '12312312312', '123@123.com', 1, '2020-10-09 20:39:44', '2020-10-09 20:39:44');
INSERT INTO `user` VALUES (3, 'editor', '8583a2d965d6159edbf65c82d871fa3e', 'MZTe7Qwf9QgXBXrZzTIqJQ==', '编辑', '12345678933', '12@qq.com', 0, '2020-10-09 20:39:44', '2020-12-16 02:46:00');

