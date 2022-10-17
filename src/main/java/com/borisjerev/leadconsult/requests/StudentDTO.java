package com.borisjerev.leadconsult.requests;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {
    @NotNull
    private String name;

    @Nullable
    private Integer age;

    @Nullable
    private Long assignedCourse;

    @Nullable
    private String assignedGroup;

    @Nullable
    private List<Long> assignedTeachers;
}
