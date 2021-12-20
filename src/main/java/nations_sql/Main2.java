package nations_sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//MILESTONE 3
public class Main2 {
	
		private final static String DB_URL = "jdbc:mysql://localhost:3306/db_nations";
		private final static String DB_USER = "root";
		private final static String DB_PASSWORD = "My5qlpas$";

		public static void main(String[] args) {
			Scanner scan = new Scanner(System.in);
			try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
				System.out.print("Cerca : ");
				String ricerca = "%" + scan.nextLine() + "%";
				
				
				String sql = "SELECT c.name, c.country_id, r.name , c2.name \n" + 
						"FROM countries c\n" + 
						"JOIN regions r ON c.region_id = r.region_id\n" + 
						"JOIN continents c2 ON r.continent_id = c2.continent_id\n" + 
						" WHERE c.name LIKE ? " +
						"ORDER BY c.name ;";
						
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setString(1, ricerca);
					try(ResultSet rs = ps.executeQuery()){
						String format = "%-48s%-5s%-28s%-13s\n";
						System.out.printf(format , "Name","ID","Region","Continent");
						System.out.println();
						while (rs.next()) {
							String c_name = rs.getString(1);
							int c_id = rs.getInt(2);
							String r_name = rs.getString(3);
							String cont_name = rs.getString(4);
							
							/*
							 *  lunghezze massime 
							 *  Name = 44
							 *  Regio = 25
							 *  Continent = 13
							 */
							
							System.out.printf(format, c_name, c_id, r_name, cont_name);
						}
					}
				}
	 			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			scan.close();
			
	}

}
