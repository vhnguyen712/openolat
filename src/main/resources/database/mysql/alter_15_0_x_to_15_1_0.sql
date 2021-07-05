-- Inactive user
alter table o_bs_identity add column inactivationdate datetime;
alter table o_bs_identity add column inactivationemaildate datetime;
alter table o_bs_identity add column deletionemaildate datetime;


-- BigBlueButton
alter table o_bbb_meeting add column b_layout varchar(16) default 'standard';
alter table o_bbb_meeting add column b_guest bool default false not null;
alter table o_bbb_meeting add column b_identifier varchar(64);
alter table o_bbb_meeting add column b_read_identifier varchar(64);

alter table o_bbb_template add column b_external_users bool default true not null;


-- Lock
alter table oc_lock add column windowid varchar(32) default null;


-- Appointments
create table o_ap_topic (
   id bigint not null auto_increment,
   creationdate datetime not null,
   lastmodified datetime not null,
   a_title varchar(256),
   a_description varchar(4000),
   a_type varchar(64) not null,
   a_multi_participation bool default true not null,
   a_auto_confirmation bool default false not null,
   fk_group_id bigint,
   fk_entry_id bigint not null,
   a_sub_ident varchar(64) not null,
   primary key (id)
);

create table o_ap_organizer (
   id bigint not null auto_increment,
   creationdate datetime not null,
   lastmodified datetime not null,
   fk_topic_id bigint not null,
   fk_identity_id bigint not null,
   primary key (id)
);

create table o_ap_topic_to_group (
   id bigint not null auto_increment,
   creationdate datetime not null,
   fk_topic_id bigint not null,
   fk_group_id bigint,
   primary key (id)
);

create table o_ap_appointment (
   id bigint not null auto_increment,
   creationdate datetime not null,
   lastmodified datetime not null,
   a_status varchar(64) not null,
   a_status_mod_date datetime,
   a_start datetime,
   a_end datetime,
   a_location varchar(256),
   a_details varchar(4000),
   a_max_participations integer,
   fk_topic_id bigint not null,
   primary key (id)
);

create table o_ap_participation (
   id bigint not null auto_increment,
   creationdate datetime not null,
   lastmodified datetime not null,
   fk_appointment_id bigint not null,
   fk_identity_id bigint not null,
   fk_identity_created_by bigint not null,
   primary key (id)
);

alter table o_ap_topic ENGINE = InnoDB;
alter table o_ap_organizer ENGINE = InnoDB;
alter table o_ap_topic_to_group ENGINE = InnoDB;
alter table o_ap_appointment ENGINE = InnoDB;
alter table o_ap_participation ENGINE = InnoDB;

alter table o_ap_topic add constraint ap_topic_entry_idx foreign key (fk_entry_id) references o_repositoryentry (repositoryentry_id);
alter table o_ap_organizer add constraint ap_organizer_topic_idx foreign key (fk_topic_id) references o_ap_topic (id);
alter table o_ap_organizer add constraint ap_organizer_identity_idx foreign key (fk_identity_id) references o_bs_identity (id);
alter table o_ap_topic_to_group add constraint ap_tg_topic_idx foreign key (fk_topic_id) references o_ap_topic (id);
create index idx_ap_tg_group_idx on o_ap_topic_to_group(fk_group_id);
alter table o_ap_appointment add constraint ap_appointment_topic_idx foreign key (fk_topic_id) references o_ap_topic (id);
alter table o_ap_participation add constraint ap_part_appointment_idx foreign key (fk_appointment_id) references o_ap_appointment (id);
alter table o_ap_participation add constraint ap_part_identity_idx foreign key (fk_identity_id) references o_bs_identity (id);
