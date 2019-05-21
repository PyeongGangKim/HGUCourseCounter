package edu.handong.analysis.datamodel;

import java.util.*;

public class Student {
	private String studentId;
	private ArrayList<Course> coursesTaken; 
	private HashMap<String,Integer> semestersByYearAndSemester; 
	public Student(String studentId) {
	this.studentId=studentId;
	coursesTaken=new ArrayList<Course>(); 
	} // constructor
	public void addCourse(Course newRecord) {
		if(studentId.equals(newRecord.getStudentId())) {
		coursesTaken.add(newRecord);
		}
	}
	
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		semestersByYearAndSemester=new HashMap<String,Integer>();
		ArrayList<String> semesterCheck = new ArrayList<String>();
		int i=1;
		for(Course data:coursesTaken) {
			int j=0;
			String semester=data.getYearTaken()+"-"+data.getSemesterCourseTaken();
			for(String line:semesterCheck) {
				if(line.equals(semester)) {
					j++;
				}
			}
			if(j==0) {
				semesterCheck.add(semester);
				
			}
	}
		Collections.sort(semesterCheck);
		for(String check:semesterCheck) {
			semestersByYearAndSemester.put(check,i);
			i++;
		}
		return semestersByYearAndSemester;
	}
	public int getNumCourseInNthSementer(int semester) {
		HashMap<String,Integer> semesters = getSemestersByYearAndSemester();
		int count=0;
		for(Course data:coursesTaken) {
			String sem=data.getYearTaken()+"-"+data.getSemesterCourseTaken();
		if(semesters.get(sem)==semester) {
			count++;		
		}
	}
		return count;
	}

	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
}
	
