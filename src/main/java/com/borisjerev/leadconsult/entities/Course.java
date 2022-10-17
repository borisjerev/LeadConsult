package com.borisjerev.leadconsult.entities;

import com.borisjerev.leadconsult.enums.CourseTypes;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Courses")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long courseId;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CourseTypes type;
}
