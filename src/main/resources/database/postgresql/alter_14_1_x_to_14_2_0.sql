create table o_gta_task_revision (
   id bigserial,
   creationdate timestamp not null,
   lastmodified timestamp not null,
   g_status varchar(36) not null,
   g_rev_loop int4 not null default 0,
   g_date timestamp,
   g_rev_comment text,
   g_rev_comment_lastmodified timestamp,
   fk_task int8 not null,
   fk_comment_author int8,
   primary key (id)
);

alter table o_gta_task_revision add constraint task_rev_to_task_idx foreign key (fk_task) references o_gta_task (id);
create index idx_task_rev_to_task_idx on o_gta_task_revision (fk_task);
alter table o_gta_task_revision add constraint task_rev_to_ident_idx foreign key (fk_comment_author) references o_bs_identity (id);
create index idx_task_rev_to_ident_idx on o_gta_task_revision (fk_comment_author);

-- livestream
create table o_livestream_launch (
   id bigserial,
   creationdate timestamp not null,
   l_launch_date timestamp not null,
   fk_entry int8 not null,
   l_subident varchar(128) not null,
   fk_identity int8 not null,
   primary key (id)
);
create index idx_livestream_viewers_idx on o_livestream_launch(l_subident, l_launch_date, fk_entry, fk_identity);


-- notifications
alter table o_noti_sub add column subenabled bool default true;


-- index
create index mark_all_idx on o_mark(resname,resid,creator_id);
create index idx_eff_stat_course_ident_idx on o_as_eff_statement (fk_identity,course_repo_key);

-- question pool
alter table o_qp_item add column q_correction_time int8 default null;

