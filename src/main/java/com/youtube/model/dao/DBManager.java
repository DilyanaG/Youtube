package com.youtube.model.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;


@Component
public class DBManager {

	private Connection connection;
	
	private static final String DB_IP = "127.0.0.1";
	private static final String DB_PORT = "3306";
	private static  String DB_NAME ;
	private static  String DB_USERNAME ;
	private static  String DB_PASSWORD ;
	private static final String URL = "jdbc:mysql://"+DB_IP+":"+DB_PORT+"/"+DB_NAME+ "?useSSL=false";
	static{
		  getDBConnectionParameters();
		}

	private DBManager(){
		
		try {
			//load driver
			Class.forName("com.mysql.jdbc.Driver");
		
			//create connection 
			connection = DriverManager.getConnection(URL, DB_USERNAME, DB_PASSWORD);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Sorry,connection failed! Maybe wrong credentials!");
			e.getMessage();
			
		} 
	}
	
	
	
	public Connection getConnection() {
		return connection;
	}
	
	private static void getDBConnectionParameters() {
		File file = new File(".//DB.txt");
	        
			try (BufferedReader in = new BufferedReader(new FileReader(file));){
				String st;
	             while((st=in.readLine()) != null){
				    if(st.lastIndexOf("DB_NAME:")>=0){
				      DB_NAME =st.substring(st.lastIndexOf(":")+1).trim();
				    }
				    if(st.lastIndexOf("DB_USERNAME:")>=0){
				     DB_USERNAME =st.substring(st.lastIndexOf(":")+1).trim();
				    }
				    if(st.lastIndexOf("DB_PASSWORD:")>=0){
				     DB_PASSWORD =st.substring(st.lastIndexOf(":")+1).trim();
				    }
			
				
	             }
	          
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}