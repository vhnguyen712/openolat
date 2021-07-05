-- Assessment
alter table o_as_entry add a_date_done date;
alter table o_as_entry add a_date_fully_assessed date;
alter table o_as_entry add a_date_start date;
alter table o_as_entry add a_duration number(20);
alter table o_as_entry add a_obligation varchar(50);
alter table o_as_entry add a_first_visit date;
alter table o_as_entry add a_last_visit date;
alter table o_as_entry add a_num_visits number(20);
alter table o_as_entry add a_entry_root number;

create index idx_as_entry_start_idx on o_as_entry (a_date_start);

-- Curriculum
alter table o_cur_element_type add c_learning_progress varchar(16);
alter table o_cur_curriculum_element add c_learning_progress varchar(16);


-- Forum
alter table o_forum add f_refresname varchar(50);
alter table o_forum add f_refresid number(20);
create index idx_forum_ref_idx on o_forum (f_refresid, f_refresname);

