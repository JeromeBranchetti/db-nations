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

			String sql = "SELECT c.name, c.country_id, r.name , c2.name \n" + "FROM countries c\n"
					+ "JOIN regions r ON c.region_id = r.region_id\n"
					+ "JOIN continents c2 ON r.continent_id = c2.continent_id\n" + " WHERE c.name LIKE ? "
					+ "ORDER BY c.name ;";

			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setString(1, ricerca);
				try (ResultSet rs = ps.executeQuery()) {
					/*
					 * lunghezze massime Name = 44 Region = 25 Continent = 13
					 */
					String format = "%-48s%-6s%-30s%-13s\n";
					System.out.printf(format, "Name", "ID", "Region", "Continent");
					System.out.println();
					while (rs.next()) {
						String c_name = rs.getString(1);
						int c_id = rs.getInt(2);
						String r_name = rs.getString(3);
						String cont_name = rs.getString(4);
						System.out.printf(format, c_name, c_id, r_name, cont_name);
					}

				}
			}
			// Stampare a video lingue del paese scelto
			System.out.print("\nInserisci ID per dettagli: ");
			int id = Integer.parseInt(scan.nextLine());
			String sql2 = "SELECT c.name \n" + "FROM countries c \n" + "WHERE c.country_id = ?;";

			try (PreparedStatement ps2 = con.prepareStatement(sql2)) {
				ps2.setInt(1, id);
				try (ResultSet rs2 = ps2.executeQuery()) {
					rs2.next();
					String c_name = rs2.getString(1);
					System.out.println("Details for country: " + c_name);

				}
			}

			// stampa lingue della nazione
			String sql3 = "SELECT c.name, l.`language` \n" + "FROM country_languages cl \n"
					+ "JOIN countries c ON c.country_id = cl.country_id \n"
					+ "JOIN languages l ON cl.language_id = l.language_id \n" + "WHERE c.country_id = ?;";

			try (PreparedStatement ps3 = con.prepareStatement(sql3)) {
				ps3.setInt(1, id);
				try (ResultSet rs3 = ps3.executeQuery()) {
					System.out.print("Languages: ");
					rs3.next();
					String lista_nomi = rs3.getString(2);
					while (rs3.next()) {
						lista_nomi += ", " + rs3.getString(2);

					}
					System.out.println(lista_nomi);

				}
			}

			// stampa dettagli ultimi della nazione
			String sql4 = "\n" + "SELECT c.name, cs.`year` ,cs.population , cs.gdp \n" + "FROM countries c\n"
					+ "JOIN country_stats cs ON c.country_id = cs.country_id \n" + "WHERE c.country_id = ?\n"
					+ "ORDER BY cs.`year`\n" + "LIMIT 1;";

			try (PreparedStatement ps4 = con.prepareStatement(sql4)) {
				ps4.setInt(1, id);
				try (ResultSet rs4 = ps4.executeQuery()) {
					rs4.next();
					String year = rs4.getString(1);
					int population = rs4.getInt(2);
					int gdp = rs4.getInt(3);
					System.out.println("Most recent stats ");
					System.out.println("YEAR: " + year);
					System.out.println("POPULATION: " + population);
					System.out.println("GDP: " + gdp);

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		scan.close();

	}

}
