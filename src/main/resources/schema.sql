create table if not exists professor(
    id INT primary key autoincrement,
    name TEXT,
    department TEXT
);

create table if not exists course(
    id INT primary key autoincrement,
    name TEXT,
    credits INT,
    professorid INT,
    foreign key(professorid) references professor(id)
);

create table if not exists student(
    id INT primary key autoincrement,
    name TEXT,
    email TEXT
);

create table if not exists course_student(
    courseid INT,
    studentid INT,
    primary key (courseid, studentid),
    foreign key (courseid) references course(id),
    foreign key (studentid) references student(id)
);