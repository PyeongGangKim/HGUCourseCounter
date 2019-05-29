package edu.handong.analysis.datamodel;

public class Course {
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	public String getCourseName() {
		return courseName;
	}

	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;
	
	public Course(String[] line) {
		studentId=line[0].trim();
		yearMonthGraduated=line[1].trim();
		firstMajor=line[2].trim();
		secondMajor=line[3].trim();
		courseCode=line[4].trim();
		courseName=line[5].trim();
		courseCredit=line[6].trim();
		yearTaken=Integer.parseInt(line[7].trim());
		semesterCourseTaken=Integer.parseInt(line[8].trim());
		
		
	}
	public int getYearTaken() {
		return yearTaken;
	}

	public void setYearTaken(int yearTaken) {
		this.yearTaken = yearTaken;
	}

	public int getSemesterCourseTaken() {
		return semesterCourseTaken;
	}

	public void setSemesterCourseTaken(int semesterCourseTaken) {
		this.semesterCourseTaken = semesterCourseTaken;
	}
	
	public String getStudentId() {
		return studentId;
	}
	
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	public String getCourseCode() {
		return courseCode;
	}

}
