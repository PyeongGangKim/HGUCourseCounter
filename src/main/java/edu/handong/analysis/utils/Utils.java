package edu.handong.analysis.utils;

import java.util.*;


import com.opencsv.CSVReader;

import java.io.*;

public class Utils {
	public static ArrayList<String[]> getLines(String file,boolean removeHeader){
		ArrayList<String[]> student= new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new FileReader(file));
			String[] s;
			if(removeHeader) {
				s = reader.readNext();
			}
			while ((s = reader.readNext()) != null) {
				student.add(s);
            }
		}catch (FileNotFoundException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}catch (IOException e) {
            e.printStackTrace();
        }
    
		return student;
	}
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		
		String fPath=targetFileName.replace("/",File.separator);
		int num=fPath.lastIndexOf(File.separator);
		String filePath=fPath.substring(0,num);
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
		for(String line:lines) {
			outputStream.println(line);
			outputStream.flush();
		}
	}

}
