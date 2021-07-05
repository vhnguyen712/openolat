-- Document editor
drop table o_wopi_access;
create table o_de_access (
   id bigint not null auto_increment,
   creationdate datetime not null,
   lastmodified datetime not null,
   o_editor_type varchar(64) not null,
   o_expires_at datetime not null,
   o_mode varchar(64) not null,
   o_version_controlled bool not null,
   fk_metadata bigint not null,
   fk_identity bigint not null,
   primary key (id)
);
create table o_de_user_info (
   id bigint not null auto_increment,
   creationdate datetime not null,
   lastmodified datetime not null,
   o_info varchar(2048) not null,
   fk_identity bigint not null,
   primary key (id)
);

alter table o_de_access ENGINE = InnoDB;
alter table o_de_user_info ENGINE = InnoDB;

create unique index idx_de_userinfo_ident_idx on o_de_user_info(fk_identity);


-- Assessment
alter table o_as_entry add column a_current_run_start datetime;

alter table o_as_mode_course add column a_end_status varchar(32);

alter table o_qti_assessmenttest_session add column q_max_score float(65,30);

-- Disadvantage compensation
alter table o_qti_assessmenttest_session add column q_compensation_extra_time bigint;

create table o_as_compensation (
   id bigint not null auto_increment,
   creationdate datetime not null,
   lastmodified datetime not null,
   a_subident varchar(512),
   a_subident_name varchar(512),
   a_extra_time bigint not null,
   a_approved_by varchar(2000),
   a_approval timestamp,
   a_status varchar(32),
   fk_identity bigint not null,
   fk_creator bigint not null,
   fk_entry bigint not null,
   primary key (id)
);
alter table o_as_compensation ENGINE = InnoDB;

alter table o_as_compensation add constraint compensation_ident_idx foreign key (fk_identity) references o_bs_identity (id);
alter table o_as_compensation add constraint compensation_crea_idx foreign key (fk_creator) references o_bs_identity (id);
alter table o_as_compensation add constraint compensation_entry_idx foreign key (fk_entry) references o_repositoryentry (repositoryentry_id);

create table o_as_compensation_log (
   id bigint not null auto_increment,
   creationdate datetime not null,
   a_action varchar(32) not null,
   a_val_before mediumtext,
   a_val_after mediumtext,
   a_subident varchar(512),
   fk_entry_id bigint not null,
   fk_identity_id bigint not null,
   fk_compensation_id bigint not null,
   fk_author_id bigint,
   primary key (id)
);
alter table o_as_compensation_log ENGINE = InnoDB;

create index comp_log_entry_idx on o_as_compensation_log (fk_entry_id);
create index comp_log_ident_idx on o_as_compensation_log (fk_identity_id);


-- Appointments
alter table o_ap_appointment add column fk_meeting_id bigint;
alter table o_ap_appointment add constraint ap_appointment_meeting_idx foreign key (fk_meeting_id) references o_bbb_meeting (id);


-- Organiation role rights
create table o_org_role_to_right (
   id bigint not null auto_increment,
   creationdate datetime not null,
   o_role varchar(255) not null,
   o_right varchar(255) not null,
   fk_organisation bigint not null,
   primary key (id)
);
alter table o_org_role_to_right ENGINE = InnoDB;

alter table o_org_role_to_right add constraint org_role_to_right_to_organisation_idx foreign key (fk_organisation) references o_org_organisation (id);
create index idx_org_role_to_right_to_organisation_idx on o_org_role_to_right (fk_organisation);


-- Lectures
alter table o_lecture_reason add column l_enabled bool default true not null;

-- Absences
alter table o_lecture_absence_category add column l_enabled bool default true not null;

-- Contact tracing
create table o_ct_location (
   id bigint not null auto_increment,
   creationdate datetime not null,
   lastmodified datetime not null,
   l_reference varchar(255),
   l_titel varchar(255),
   l_building varchar(255),
   l_room varchar(255),
   l_sector varchar(255),
   l_table varchar(255),
   l_qr_id varchar(255) not null,
   l_qr_text varchar(4000),
   l_guests boolean default true not null,
   l_printed boolean default false not null,
   unique(l_qr_id),
   primary key (id)
);
alter table o_ct_location ENGINE = InnoDB;

create table o_ct_registration (
   id bigint not null auto_increment,
   creationdate datetime not null,
   l_deletion_date datetime not null,
   l_start_date datetime not null,
   l_end_date datetime,
   l_nick_name varchar(255),
   l_first_name varchar(255),
   l_last_name varchar(255),
   l_street varchar(255),
   l_extra_line varchar(255),
   l_zip_code varchar(255),
   l_city varchar(255),
   l_email varchar(255),
   l_institutional_email varchar(255),
   l_generic_email varchar(255),
   l_private_phone varchar(255),
   l_mobile_phone varchar(255),
   l_office_phone varchar(255),
   fk_location bigint not null,
   primary key (id)
);
alter table o_ct_registration ENGINE = InnoDB;

alter table o_ct_registration add constraint reg_to_loc_idx foreign key (fk_location) references o_ct_location (id);
create index idx_reg_to_loc_idx on o_ct_registration (fk_location);
create index idx_qr_id_idx on o_ct_location (l_qr_id);
