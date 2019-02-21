/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/7/13 10:19:24                           */
/*==============================================================*/


/*==============================================================*/
/* Table: work_organ                                                 */
/*==============================================================*/
create table work_organ
(
   id                   char(36) not null,
   parentId             char(36),
   name                 varchar(60) not null,
   status               varchar(3) not null comment '0.停用 1.启用',
   ordinal              int not null,
   createUserId         char(36) not null,
   createTime           datetime not null,
   updateUserId         char(36) not null,
   updateTime           datetime not null,
   primary key (id),
   constraint FK_Organ_parentId foreign key (parentId)
      references work_organ (id) on delete cascade on update restrict
);

/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table work_worker
(
   id                   char(36) not null,
   name                 varchar(20) not null,
   username             varchar(20) not null,
   password             varchar(120) not null,
   status               varchar(3) not null comment '0.停用 1.启用',
   ordinal              int not null,
   defaultActorId       char(36),
   createUserId         char(36) not null,
   createTime           datetime not null,
   updateUserId         char(36) not null,
   updateTime           datetime not null,
   primary key (id)
);

/*==============================================================*/
/* Table: work_role                                                  */
/*==============================================================*/
create table work_role
(
   id                   char(36) not null,
   name                 varchar(60) not null,
   privilegs            varchar(2000),
   createUserId         char(36) not null,
   createTime           datetime not null,
   updateUserId         char(36) not null,
   updateTime           datetime not null,
   primary key (id)
);

/*==============================================================*/
/* Table: Actor                                                 */
/*==============================================================*/
create table work_actor
(
   id                   char(36) not null,
   organId              char(36) not null,
   userId               char(36) not null,
   roleId               char(36) not null,
   name                 varchar(60) not null,
   primary key (id),
   constraint FK_Actor_organId foreign key (organId)
      references work_organ (id) on delete cascade on update restrict,
   constraint FK_Actor_userId foreign key (userId)
      references work_worker (id) on delete cascade on update restrict,
   constraint FK_Actor_roleId foreign key (roleId)
      references work_role (id) on delete cascade on update restrict
);

/*==============================================================*/
/* Table: BizLog                                                */
/*==============================================================*/
create table BizLog
(
   id                   char(36) not null,
   operator             varchar(20) not null,
   operateTime          datetime not null,
   message              varchar(800) not null,
   entityId             char(36),
   origData             varchar(2000),
   newData              varchar(2000),
   primary key (id)
);

/*==============================================================*/
/* Table: visit_visitor                                         */
/*==============================================================*/
create table visit_worker
(
   id                   char(36) not null,
   identityId             char(36) not null,
   nickName             varchar(20) not null,
   phone             varchar(20) not null,
   password             varchar(120) not null,
   status             varchar(3) not null,
   createTime          datetime not null,
   updateTime          datetime not null,
   primary key (id)
);

/*==============================================================*/
/* Table: visit_identity                                        */
/*==============================================================*/
create table visit_identity
(
   id                   char(36) not null,
   name             varchar(20) not null,
   code             varchar(20) not null,
   type             varchar(3) not null,
   createUserId         char(36) not null,
   createTime           datetime not null,
   updateUserId         char(36) not null,
   updateTime           datetime not null,
   primary key (id)
);

/*==============================================================*/
/* Index: IDX_User_username                                     */
/*==============================================================*/
create unique index IDX_User_username on work_worker
(
   username
);


/*==============================================================*/
/* Index: IDX_Worker_phone                                      */
/*==============================================================*/
create unique index IDX_Worker_phone on visit_worker
(
   phone
);


/*==============================================================*/
/* Index: IDX_Identity_code                                     */
/*==============================================================*/
create unique index IDX_Identity_code on visit_identity(
   code
);

