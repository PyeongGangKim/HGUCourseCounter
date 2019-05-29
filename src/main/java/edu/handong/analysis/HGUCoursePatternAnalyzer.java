package edu.handong.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	private ArrayList<String> result;
	private String dataPath;
	private String resultPath;
	private boolean help;
	private int analysis;
	private int startYear;
	private int endYear;
	private String courseCode=null;
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		Options options = new Options();
		createOptions(options);
		if(parseOptions(options, args)) {
			if (help){
				printHelp(options);
				return;
			}
			
		ArrayList<String[]> lines = Utils.getLines(dataPath, true);
		
		students = loadStudentCourseRecords(lines);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 

		if(analysis==1) {
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath);
		}
		else {
			if(courseCode==null) {
				System.out.println("put the '-c' options");
			}else {
			ArrayList<String> linesToBeSaved = countNumberOfParticularCourses(sortedStudents);
			
			Utils.writeAFile(linesToBeSaved, resultPath);
			}
		}
	  }
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			dataPath = cmd.getOptionValue("i");
			resultPath = cmd.getOptionValue("o");
			analysis = Integer.parseInt(cmd.getOptionValue("a"));
			startYear = Integer.parseInt(cmd.getOptionValue("s"));
			endYear = Integer.parseInt(cmd.getOptionValue("e"));
			courseCode =cmd.getOptionValue("c");
			help = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
		
	}

	private void printHelp(Options options) {
		// automatically generate the help statement
				HelpFormatter formatter = new HelpFormatter();
				String header = "HGU Course Analyzer";
				String footer ="";
				formatter.printHelp("HGUCourseCounter", header, options, footer, true);
		
	}

	private void createOptions(Options options) {

		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Input path")
				.required()
				.build());
		
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("Output path")
				.required()
				.build());
		
		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg()
				.argName("Analysis option")
				.required()
				.build());
		
		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for '-a 2' option")
				.hasArg()
				.argName("course code")
				//.required()
				.build());
		
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()
				.argName("Start year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()
				.argName("End year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				//.hasArg()
				.argName("Help")
				//.required()
				.build());
	}

	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String[]> lines) {
		HashMap<String,Student> studentData= new HashMap<String,Student>();
		ArrayList<Course> courseData= new ArrayList<Course>();
		ArrayList<Course> sameStudentId = new ArrayList<Course>(); 
		for(String[] data:lines) {
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
		String result="StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester";
		resultLines.add(result);
		while(iteratorKey.hasNext()){
			   String key = iteratorKey.next();
			   int size=sortedStudents.get(key).getSemestersByYearAndSemester().size();
			   for(int i=1;i<size+1;i++) {
				   String lines=key+","+size+","+i+","+sortedStudents.get(key).getNumCourseInNthSementer(i);
				    for(int year=startYear;year<=endYear;year++) {
				   if(Integer.parseInt(sortedStudents.get(key).getYearAndSemester()) ==year ) {
					   resultLines.add(lines);
				   break;
				   }
				   }
				   }
			}
		return resultLines; // do not forget to return a proper variable.	
	}		   
		
	private ArrayList<String> countNumberOfParticularCourses(Map<String, Student> sortedStudents){
		ArrayList<String> resultLines= new ArrayList<String>();
		double divide;
		String lines="Year,Semester,CouseCode, CourseName,TotalStudents,StudentsTaken,Rate";
		String courseName = null;
		resultLines.add(lines);
		
		for(int year=startYear;year<=endYear;year++) {
			for(int semester=1;semester<5;semester++) {
				int totalStudents=0;
				int studentsTaken=0;
				for(String key:sortedStudents.keySet()) {
					int i=0;
					for(Course data:sortedStudents.get(key).getCoursesTaken()) {
						if(data.getYearTaken()==year && data.getSemesterCourseTaken()==semester) {
							i++;
							if(data.getCourseCode().equals(courseCode)) {
								courseName=data.getCourseName();
								studentsTaken++;
							}
						}
					}
					
					if(i != 0
							) {
						totalStudents++;
					}
				}
	
					divide=(double)studentsTaken/totalStudents*100;
				String result=year+","+semester+","+courseCode+","+courseName+","+totalStudents+","+studentsTaken+","+String.format("%.1f",divide)+"%";
				
				resultLines.add(result);
	
			}
			
			
		}
		
		return resultLines;
	}
}
