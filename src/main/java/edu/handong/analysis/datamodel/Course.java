package edu.handong.analysis.datamodel;

public class Course {
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;
	
	public Course(String line) {
		String[] converter=line.split(",");
		studentId=converter[0].trim();
		yearMonthGraduated=converter[1].trim();
		firstMajor=converter[2].trim();
		secondMajor=converter[3].trim();
		courseCode=converter[4].trim();
		courseName=converter[5].trim();
		courseCredit=converter[6].trim();
		yearTaken=Integer.parseInt(converter[7].trim());
		semesterCourseTaken=Integer.parseInt(converter[8].trim());
		
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

}
