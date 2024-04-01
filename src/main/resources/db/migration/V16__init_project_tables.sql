create table if not exists projects
(
    id          int primary key auto_increment,
    description varchar(100) not null
);
create table if not exists project_steps
(
    id               int primary key auto_increment,
    description      varchar(100) not null,
    days_to_deadline int          not null,
    project_id       int          not null,
    foreign key (project_id) references projects (id)
);
alter table if exists task_groups
    add column project_id int null;
alter table if exists task_groups
    add foreign key (project_id) references projects (id);

