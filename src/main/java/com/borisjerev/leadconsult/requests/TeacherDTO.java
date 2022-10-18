package com.borisjerev.leadconsult.requests;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeacherDTO {
    @NotNull
    private String name;

    @Nullable
    private Integer age;

    @Nullable
    private Long assignedCourse;

    @Nullable
    private String assignedGroup;

    @Nullable
    private Set<Long> assignedStudents;
}
