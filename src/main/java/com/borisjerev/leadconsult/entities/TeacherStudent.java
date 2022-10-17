package com.borisjerev.leadconsult.entities;

import com.borisjerev.leadconsult.entities.ids.TeacherStudentId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Teacher_Student")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TeacherStudent {
    @EmbeddedId
    private TeacherStudentId teacherStudentId = new TeacherStudentId();

    public Long getCourseId() {
        return teacherStudentId.getCourseId();
    }

    public void setCourseId(Long course) {
        teacherStudentId.setCourseId(course);
    }

    public String getGroupp() {
        return teacherStudentId.getGroupp();
    }

    public void setGroupp(String groupp) {
        teacherStudentId.setGroupp(groupp);
    }

    public Long getTeacherId() {
        return teacherStudentId.getTeacherId();
    }

    public void setTeacherId(Long teacherId) {
        teacherStudentId.setTeacherId(teacherId);
    }

    public Long getStudentId() {
        return teacherStudentId.getStudentId();
    }

    public void setStudentId(Long studentId) {
        teacherStudentId.setStudentId(studentId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherStudent that = (TeacherStudent) o;
        return Objects.equals(teacherStudentId, that.teacherStudentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherStudentId);
    }
}
