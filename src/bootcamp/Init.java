package bootcamp;

import java.sql.SQLException;

public class Init {
	
	public static void createDB(String db_name) {
		
		String query = "Create Database "+db_name
				      +" Default Charset utf8 "
				      +"Default Collate utf8_unicode_ci;";
		

			DbClient.execute(query,new String[0]);

		
	}
	
	public static void createTables() throws Exception {
		
		if(DbClient.db_name.length() == 0) {
			throw new Exception("Not configure database name");
		}
		
		String[] query = new String[4];
		
		query[0]=
				"CREATE TABLE IF NOT EXISTS `Kat_workshop_BIS`.`user_group` (\n" + 
				"  `id` INT NOT NULL AUTO_INCREMENT,\n" + 
				"  `name` VARCHAR(45) NULL,\n" + 
				"  PRIMARY KEY (`id`));\n"; 

		query[1]=
				"CREATE TABLE IF NOT EXISTS `Kat_workshop_BIS`.`users` (\n" + 
				"  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" + 
				"  `email` VARCHAR(255) UNIQUE NULL,\n" + 
				"  `username` VARCHAR(255) NULL,\n" + 
				"  `password` VARCHAR(255) NULL,\n" + 
				"  `person_group_id` INT NOT NULL,\n" + 
				"  PRIMARY KEY (`id`),\n" + 

				"    FOREIGN KEY (`person_group_id`)\n" + 
				"    REFERENCES `Kat_workshop_BIS`.`user_group` (`id`));\n" ; 

		query[2]= 
				"CREATE TABLE IF NOT EXISTS `Kat_workshop_BIS`.`exercise` (\n" + 
				"  `id` INT NOT NULL AUTO_INCREMENT,\n" + 
				"  `title` VARCHAR(255) NULL,\n" + 
				"  `description` VARCHAR(45) NULL,\n" + 
				"  PRIMARY KEY (`id`));\n";
 
			
		query[3]=
				"CREATE TABLE IF NOT EXISTS `Kat_workshop_BIS`.`solution` (\n" + 
				"  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" + 
				"  `created` DATETIME NULL,\n" + 
				"  `updated` DATETIME NULL,\n" + 
				"  `description` TEXT NULL,\n" + 
				"  `users_id` BIGINT NOT NULL,\n" + 
				"  `exercise_id` INT NOT NULL,\n" + 
				"  PRIMARY KEY (`id`),\n" + 
				"    FOREIGN KEY (`users_id`)\n" + 
				"    REFERENCES `Kat_workshop_BIS`.`users` (`id`),\n" + 
 
				"    FOREIGN KEY (`exercise_id`)\n" + 
				"    REFERENCES `Kat_workshop_BIS`.`exercise` (`id`));\n"; 

	

			for(String q : query) {
			DbClient.execute(q,new String[0]);
			}
			
	}

}
