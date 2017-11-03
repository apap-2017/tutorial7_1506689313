package com.example.dao;
import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;

import com.example.model.CourseModel;
import com.example.model.StudentModel;

@Mapper
public interface CourseMapper {
	
	//select students that are enrolled on a particular course
    @Select("SELECT student.npm, name " 
    		+ "FROM studentcourse JOIN student " 
    		+ "ON student.npm = studentcourse.npm " 
    		+ "WHERE studentcourse.id_course = #{id_course}")
    List<StudentModel> selectStudents(@Param("id_course") String id_course);
    
    //view course detail
    @Select("SELECT id_course, name, credits FROM course where id_course = #{idCourse}")
    @Results(value= {
        	@Result(property="idCourse", column="id_course"),
        	@Result(property="name", column="name"),
        	@Result(property="credits", column="credits"),
        	@Result(property="students", column="id_course",
        		javaType = List.class,
        		many = @Many(select = "selectStudents"))
        })
    CourseModel selectCourse(@Param("idCourse") String id_course);
    
    @Select("select id_course, name, credits from course")
    @Results(value = {
    	@Result(property="idCourse", column="id_course"),
    	@Result(property="name", column="name"),
    	@Result(property="credits", column="credits"),
    	@Result(property="students", column="id_course",
    		javaType = List.class,
    		many = @Many(select = "selectStudents"))
    })
    List<CourseModel> selectAllCourses ();
}
