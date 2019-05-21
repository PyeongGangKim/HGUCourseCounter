package edu.handong.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String dataPath = args[0]; // csv file to be analyzed
		String resultPath = args[1]; // the file path where the results are saved.
		ArrayList<String> lines = Utils.getLines(dataPath, true);
		
		students = loadStudentCourseRecords(lines);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
		
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath);
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		HashMap<String,Student> studentData= new HashMap<String,Student>();
		ArrayList<Course> courseData= new ArrayList<Course>();
		ArrayList<Course> sameStudentId = new ArrayList<Course>(); 
		for(String data:lines) {
			int i=0;
			Course course = new Course(data);
			for(Course courses: sameStudentId) {
				if((courses.getStudentId().equals(course.getStudentId()))) {
					i++;
				}
			}
			if(i==0){
				sameStudentId.add(course);
			}
			courseData.add(course);
		}
		for(Course course: sameStudentId) {
			Student student = new Student(course.getStudentId());
		for(Course data: courseData) {
			student.addCourse(data);
		}
		studentData.put(student.getStudentId(),student);
		}
		return studentData;

	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		ArrayList<String> resultLines= new ArrayList<String>();
		Iterator<String> iteratorKey = ((TreeMap<String, Student>) sortedStudents).keySet().iterator();
		while(iteratorKey.hasNext()){
			   String key = iteratorKey.next();
			   int size=sortedStudents.get(key).getSemestersByYearAndSemester().size();
			   for(int i=1;i<size+1;i++) {
				   String lines=key+","+size+","+i+","+sortedStudents.get(key).getNumCourseInNthSementer(i);
				   resultLines.add(lines);
			   }
		}		   
		
		Collections.sort(resultLines);
		return resultLines; // do not forget to return a proper variable.
	}
}
