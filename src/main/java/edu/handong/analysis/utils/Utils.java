package edu.handong.analysis.utils;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


public class Utils {
	public static ArrayList<String> getLines(String file,boolean removeHeader){
		ArrayList<String> student= new ArrayList<String>();
		String lines;
		try {
			Scanner inputStream=new Scanner(new File(file));
			if(removeHeader) {
				lines=inputStream.nextLine();
			}
			while(inputStream.hasNextLine()) {
				lines=inputStream.nextLine();
				student.add(lines);
			}
		}catch(FileNotFoundException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}
		return student;
	}
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		
		int num=targetFileName.lastIndexOf(File.separator);
		String filePath=targetFileName.substring(0,num);
		File path = new File(filePath);
		File resultFile = new File(targetFileName); 
		if (!path.exists()) {
			path.mkdirs();
		}
		PrintWriter outputStream = null;
		try {
			outputStream=new PrintWriter(resultFile);
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		outputStream.println("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester");
		outputStream.flush();
		for(String line:lines) {
			outputStream.println(line);
			outputStream.flush();
		}
	}

}
