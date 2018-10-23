import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Application {

	private final String DB_NAME = "database-2018.db";

	public static void main(String[] args) throws Exception {
		Application app = new Application();
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + app.DB_NAME);
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened SQLite database successfully.");
		//
		Connection connMsq = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://127.0.0.1:3306/azan?useUnicode=true&characterEncoding=utf8";
		    connMsq = DriverManager.getConnection(url, "admin", "x123456");	  
		    connMsq.setAutoCommit(false);
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    System.exit(0);
		}
		System.out.println("Opened MySQL database successfully.");	
		
		String[] aCountries = app.getCountries();
		// app.insertIntoCountries();
		//ArrayList<String> aTimesColumns = app.getTimesColumns();
		for (int i = 0; i < aCountries.length; i++) {		
			String sCountry = aCountries[i];
			System.out.print("\n(" + (i+1) + "/" + aCountries.length + "): " + sCountry + " ==> ");
			ArrayList<ArrayList<String>> rs = app.readFromDB(app.DB_NAME, "Cities", app.getCitiesColumns(), "Country = \"" + sCountry + "\"");
			int p = 0, s = rs.size();
			for(int j=0; j<s; j++){
				int q = (j+1)*100 / s;
				/*if(q>p){
					System.out.print("\t" + q + "%");
					p = q;
				}*/
				String sCity = rs.get(j).get(0);
				ArrayList<ArrayList<String>> times = app.readFromDB(app.DB_NAME, " `Times` ", app.getTimesColumns(), " `Country` = \"" + sCountry + "\" AND `City` = \"" + sCity +"\"");
				//System.out.println(times);
				if(times.size() >= 365 ){
					app.insetrToDB(connMsq, "Times", app.getTimesColumns(), times, sCountry, sCity);
				} else{
					System.err.println("Missing Data: " + sCountry + " : " + sCity + " : "+times.size());
				}
			}
		}
	    connMsq.close();	
	}
	public String[] getCountries() {
		String[] aCountries = { 
				"Germany", "Albania", "Austria", "Belarus", "Belgium", "Bosnia and Herzegovina",
				"Bulgaria", "Croatia", "Czech Republic", "Denmark", "Estonia", "Finland", "France","Greece", "Hungary",
				"Ireland", "Italy", "Latvia", "Liechtenstein", "Lithuania", "Luxembourg",
				"Macedonia (Former Yugoslav Republic of Macedonia)", "Malta", "Montenegro", "Netherlands", "Norway",
				"Poland", "Romania", "Russia", "Serbia", "Slovakia", "Slovenia", "Spain", "Sweden", "Switzerland",
				"Ukraine" };
		return aCountries;
	}
	

	public ArrayList<String> getTimesColumns() {
		String[] c = { "Country", "City", "yyyy", "mm", "dd", "Day", "Imsak", "Sobh", "Tolo", "Zohr", "Asr", "Ghorob",
				"Maghreb", "Esha", "Midnight" };
		ArrayList<String> aColumns = new ArrayList<String>();
		for (int i = 0; i < c.length; i++) {
			aColumns.add(c[i]);
		}
		return aColumns;
	}
	public ArrayList<String> getCitiesColumns() {
		String[] c = { "City" };
		ArrayList<String> aColumns = new ArrayList<String>();
		for (int i = 0; i < c.length; i++) {
			aColumns.add(c[i]);
		}
		return aColumns;
	}
	

	public void insetrToDB(Connection c, String tableName, ArrayList<String> columns, ArrayList<ArrayList<String>> data, String sCountry, String sCity) {
		
		Statement stmt = null;
		String query = "";
		try {			
			
			//
			query = "REPLACE INTO `" + tableName + "` ";
			//
			String columnsName = "(";
			for (int j = 0; j < columns.size(); j++) {
				if (j > 0) {
					columnsName += ", ";
				}
				columnsName += "`" + columns.get(j) + "`";
			}
			columnsName += ") ";
			query += columnsName;
			//
			String values = "";
			for (int i = 0; i < data.size(); i++) {
				String value = "\n(";
				ArrayList<String> row = data.get(i);
				for (int j = 0; j < row.size(); j++) {
					value += "\"" + row.get(j) + "\",";
				}
				value = value.substring(0, value.length() - 1);
				if (i < data.size() - 1) {
					value += "),";
				} else {
					value += ")";
				}
				values += value;
			}
			query += " VALUES " + values + ";";
			//
			//System.out.println(query);
			stmt = c.createStatement();
			int r = stmt.executeUpdate(query);			
			
			if(r<365){
				System.out.println("\n" + sCountry + " : " + sCity + " : " + r );
			}
			stmt.close();
			c.commit();
			
		} catch (Exception e) {
			System.err.println("\n" +e.getClass().getName() + ": " + e.getMessage());
			System.err.println(sCountry + " : " + sCity);
			System.out.println(query);
		}
		
	}
	
	
	
	public ArrayList<ArrayList<String>> readFromDB(String dbName, String tableName, ArrayList<String> columns, String sWhereClaus) {
		Connection c = null;
		Statement stmt = null;
		ArrayList<ArrayList<String>> resultList = new ArrayList<ArrayList<String>>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + dbName);
			c.setAutoCommit(false);
			String query = "SELECT " ;
			int i = 0;
			for (i = 0; i < columns.size()-1; i++)
			{
				query += columns.get(i) + ", ";
			}
			query += columns.get(i) + " FROM " + tableName;
			if(sWhereClaus != null && sWhereClaus.length()>0){
				query += " WHERE " + sWhereClaus;
			}
			
			query += ";";
			//System.out.println(query);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// loop through the result set
            while (rs.next()) {
            	ArrayList<String> row = new ArrayList<String>();
            	for (i = 0; i < columns.size(); i++)
    			{
            		String column = columns.get(i);
            		row.add(rs.getString(column));
            		
    			}
                resultList.add(row);
            }
			
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println("\n" + e.getClass().getName() + ": " + e.getMessage());
			System.err.println("Read Error: " + sWhereClaus);
		}
		return resultList;
	}

}
