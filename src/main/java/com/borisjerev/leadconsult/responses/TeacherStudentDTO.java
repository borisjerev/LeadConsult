package com.borisjerev.leadconsult.responses;

import com.borisjerev.leadconsult.entities.Student;
import com.borisjerev.leadconsult.entities.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeacherStudentDTO {
    private List<Teacher> teachers;
    private List<Student> students;
}
