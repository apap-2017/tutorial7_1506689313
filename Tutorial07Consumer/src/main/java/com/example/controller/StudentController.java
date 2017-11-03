package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.CourseModel;
import com.example.model.StudentModel;
import com.example.service.StudentService;

@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;


    @RequestMapping("/")
    public String index (Model model)
    {
    	model.addAttribute("page_title", "Home");
        return "index";
    }


    @RequestMapping("/student/add")
    public String add (Model model)
    {
    	model.addAttribute("page_title", "Add Student");
        return "form-add";
    }


    @RequestMapping("/student/add/submit")
    public String addSubmit (
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa,
    		Model model)
    {
        StudentModel student = new StudentModel (npm, name, gpa, null);
        studentDAO.addStudent (student);
        
        model.addAttribute("page_title", "Add Student Successful");

        return "success-add";
    }


    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        model.addAttribute("page_title", "View Student");
        
        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/view/{npm}")
    public String viewPath (Model model,
            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);
        model.addAttribute("page_title", "View Student");

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/viewall")
    public String view (Model model)
    {
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);
        
        model.addAttribute("page_title", "View All Students");

        return "viewall";
    }


    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent(npm);
        model.addAttribute("page_title", "Delete Student");
        
         if(student == null) {
    		return "not-found";
    	}
    	else {
    		studentDAO.deleteStudent (npm);
    		return "delete";
    	}
    }

    @RequestMapping("student/update/{npm}")
    public String update (Model model, @PathVariable(value="npm") String npm) {
    	StudentModel student = studentDAO.selectStudent(npm);
    	model.addAttribute("page_title", "Update Student");
    	
    	if (student == null || npm == null) {
    		model.addAttribute("npm", npm);
    		return "not-found";
    	}
    	else {
    		model.addAttribute("name", student.getName());
    		model.addAttribute("npm", student.getNpm());
    		model.addAttribute("gpa", student.getGpa());
    		model.addAttribute("student", student);
    		
    		return "form-update";
    	}
    }
  
    @RequestMapping(value = "/student/update/submit", method = RequestMethod.POST)
    public String updateSubmit (@ModelAttribute("student") StudentModel student, Model model) {
    	
    	studentDAO.updateStudent(student);
    	model.addAttribute("page_title", "Update Student Successful");
    	
    	return "success-update";
    }
    
    //view course detail
    @RequestMapping(value="/course/view/{idCourse}")
    public String viewCourse (Model model, @PathVariable(value="idCourse") String id_course) {
    	CourseModel course = studentDAO.viewCourse(id_course);
    	model.addAttribute("course", course);
    	model.addAttribute("page_title", "View Course Detail");
    	
    	return "course";
    }
}
