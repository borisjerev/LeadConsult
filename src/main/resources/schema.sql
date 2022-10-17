DROP TABLE IF EXISTS Courses;
CREATE TABLE Courses (
    course_id  BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    type VARCHAR(128) NOT NULL,
    PRIMARY KEY (course_id)
);

DROP TABLE IF EXISTS Students;
CREATE TABLE Students (
    student_id  BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    age BIGINT NULL,
    PRIMARY KEY (student_id)
);

DROP TABLE IF EXISTS Teachers;
CREATE TABLE Teachers (
    teacher_id  BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    age BIGINT NULL,
    PRIMARY KEY (teacher_id)
);

DROP TABLE IF EXISTS Teacher_Student;
CREATE TABLE Teacher_Student (
    teacher_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    groupp VARCHAR(128) NULL,
    FOREIGN KEY(teacher_id) REFERENCES Teachers(teacher_id),
    FOREIGN KEY(student_id) REFERENCES Students(student_id),
    FOREIGN KEY(course_id) REFERENCES Courses(course_id),
    PRIMARY KEY (teacher_id, student_id, course_id, groupp)
);