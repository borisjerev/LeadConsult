package com.borisjerev.leadconsult.entities.ids;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TeacherStudentId implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "teacher_id")
    private Long teacherId;

    @NotNull
    @Column(name = "student_id")
    private Long studentId;

    @NotNull
    private Long courseId;

    @NotNull
    private String groupp;
}
