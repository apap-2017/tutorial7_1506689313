package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.model.CourseModel;
import com.example.service.CourseService;

@Controller
public class CourseController {
	@Autowired 
	CourseService courseDAO;
	
	@RequestMapping(value="/course/view/{idCourse}")
	public String view(Model model, @PathVariable(value="idCourse") String idCourse) {
	  	CourseModel course = courseDAO.selectCourse(idCourse);
	  	model.addAttribute("course", course);
	  	model.addAttribute("page_title", "View Course Detail");
	  	
	  	return "course";
	}
	
	@RequestMapping("/course/viewall")
	public String viewAll(Model model) {
		List<CourseModel> courses = courseDAO.selectAllCourses();
		model.addAttribute("courses", courses);
		model.addAttribute("page_title", "View All Courses");
		
		return "viewall-courses";
	}
}
