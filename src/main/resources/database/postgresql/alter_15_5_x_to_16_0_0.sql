-- Reminder 
alter table o_rem_reminder add column r_email_copy varchar(32);
alter table o_rem_reminder add column r_email_custom_copy varchar(1024);
