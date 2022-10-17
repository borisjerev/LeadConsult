package com.borisjerev.leadconsult.repositories;

import com.borisjerev.leadconsult.entities.TeacherStudent;
import com.borisjerev.leadconsult.entities.ids.TeacherStudentId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TeacherStudentRepository extends PagingAndSortingRepository<TeacherStudent, TeacherStudentId> {

    @Transactional
    @Modifying
    @Query("DELETE FROM TeacherStudent ts WHERE ts.teacherStudentId.teacherId = :teacherId")
    void deleteByTeacherId(@Param("teacherId") Long teacherId);

    @Transactional
    @Modifying
    @Query("DELETE FROM TeacherStudent ts WHERE ts.teacherStudentId.studentId = :studentId")
    void deleteByStudentId(@Param("studentId") Long studentId);
}
