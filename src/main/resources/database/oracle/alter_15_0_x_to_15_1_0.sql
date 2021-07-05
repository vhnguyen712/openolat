-- Inactive user
alter table o_bs_identity add inactivationdate date;
alter table o_bs_identity add inactivationemaildate date;
alter table o_bs_identity add deletionemaildate date;


-- BigBlueButton
alter table o_bbb_meeting add b_layout varchar2(16) default 'standard';
alter table o_bbb_meeting add b_guest number default 0 not null;
alter table o_bbb_meeting add b_identifier varchar2(64);
alter table o_bbb_meeting add b_read_identifier varchar2(64);

alter table o_bbb_template add b_external_users number default 1 not null;


-- Lock
alter table oc_lock add windowid varchar2(32) default null;


-- Appointments
create table o_ap_topic (
   id number(20) generated always as identity,
   creationdate date not null,
   lastmodified date not null,
   a_title varchar2(256),
   a_description varchar2(4000),
   a_type varchar2(64) not null,
   a_multi_participation number default 1 not null,
   a_auto_confirmation number default 0 not null,
   fk_group_id number(20),
   fk_entry_id number(20) not null,
   a_sub_ident varchar2(64) not null,
   primary key (id)
);

create table o_ap_organizer (
   id number(20) generated always as identity,
   creationdate date not null,
   lastmodified date not null,
   fk_topic_id number(20) not null,
   fk_identity_id number(20) not null,
   primary key (id)
);

create table o_ap_topic_to_group (
   id number(20) generated always as identity,
   creationdate date not null,
   fk_topic_id number(20) not null,
   fk_group_id number(20),
   primary key (id)
);

create table o_ap_appointment (
   id number(20) generated always as identity,
   creationdate date not null,
   lastmodified date not null,
   a_status varchar2(64) not null,
   a_status_mod_date date,
   a_start date,
   a_end date,
   a_location varchar2(256),
   a_details varchar2(4000),
   a_max_participations number(20),
   fk_topic_id number(20) not null,
   primary key (id)
);

create table o_ap_participation (
   id number(20) generated always as identity,
   creationdate date not null,
   lastmodified date not null,
   fk_appointment_id number(20) not null,
   fk_identity_id number(20) not null,
   fk_identity_created_by number(20) not null,
   primary key (id)
);

alter table o_ap_topic add constraint ap_topic_entry_idx foreign key (fk_entry_id) references o_repositoryentry (repositoryentry_id);
create index idx_ap_topic_entry_idx on o_ap_topic(fk_entry_id);
alter table o_ap_organizer add constraint ap_organizer_topic_idx foreign key (fk_topic_id) references o_ap_topic (id);
create index idx_ap_organizer_topic_idx on o_ap_organizer(fk_topic_id);
alter table o_ap_organizer add constraint ap_organizer_identity_idx foreign key (fk_identity_id) references o_bs_identity (id);
create index idx_ap_organizer_identitiy_idx on o_ap_organizer(fk_identity_id);
alter table o_ap_topic_to_group add constraint ap_tg_topic_idx foreign key (fk_topic_id) references o_ap_topic (id);
create index idx_ap_tg_topic_idx on o_ap_topic_to_group(fk_topic_id);
create index idx_ap_tg_group_idx on o_ap_topic_to_group(fk_group_id);
alter table o_ap_appointment add constraint ap_appointment_topic_idx foreign key (fk_topic_id) references o_ap_topic (id);
create index idx_ap_appointment_topic_idx on o_ap_appointment(fk_topic_id);
alter table o_ap_participation add constraint ap_part_appointment_idx foreign key (fk_appointment_id) references o_ap_appointment (id);
create index idx_ap_part_appointment_idx on o_ap_participation(fk_appointment_id);
alter table o_ap_participation add constraint ap_part_identity_idx foreign key (fk_identity_id) references o_bs_identity (id);
create index idx_ap_part_identitiy_idx on o_ap_participation(fk_identity_id);

-- Quality management
create index idx_eva_part_survey_idx on o_eva_form_participation (fk_survey);

