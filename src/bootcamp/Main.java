package bootcamp;

import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		String db_name = "Kat_workshop_BIS";
		
			Init.createDB(db_name);
					
		
		DbClient.db_name = db_name;		
		try {
			Init.createTables();
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

		
		
	}

}
