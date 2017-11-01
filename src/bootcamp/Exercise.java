package bootcamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {
	
	private int id = 0;
	private String title;
	private String description;

	public Exercise() {
		// TODO Auto-generated constructor stub
	}

	public Exercise(String title, String description) {
		setTitle(title);
		setDescription(description);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public Exercise setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Exercise setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public static Exercise[] loadAll() {

		ArrayList<Exercise> exercises = new ArrayList<>();
		String query = "Select * from exercise;";

		try {
			ResultSet rs = DbClient.getData(query, new String[0]);

			while(rs.next()) {
				Exercise tmpEx = new Exercise(rs.getString(2), rs.getString(3) );
				tmpEx.id = rs.getInt(1);
				exercises.add(tmpEx);
			}

		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally{
			DbClient.closeConnection();
		}

		Exercise[]exercisesList = new Exercise[exercises.size()];
		exercisesList = exercises.toArray(exercisesList);
		return exercisesList;
	}
	
	
	public static Exercise[] loadAllEmptyByUserId(int id) {
		ArrayList<Exercise> exercises = new ArrayList<>();
		String query = "select * from exercise e join solution s "
					+ "on e.id=s.exercise_id "
					+ "where s.users_id = ? and s.description is null;";
		String[]params = {Integer.toString(id)};
		
		try {
			ResultSet rs = DbClient.getData(query, params);
			
			while(rs.next()) {
				Exercise tmpEx = new Exercise(rs.getString(2), rs.getString(3) );
				tmpEx.id = rs.getInt(1);
				exercises.add(tmpEx);
			}
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally{
			DbClient.closeConnection();
		}
		
		Exercise[]exList = new Exercise[exercises.size()];
		exList = exercises.toArray(exList);
		return exList;
	}
	
	
	public static void printAllEmptyByUserId(int id) {
		Exercise[]ex = loadAllEmptyByUserId(id);
		for (Exercise e : ex) {
			System.out.println(e.getId()+" - "+e.getTitle()+" - "+e.getDescription());
		}
	}
	
	
	public void saveToDB() {
		if(this.id == 0) {
			addToDB();
		}else {
			updateInDB();
		}
	}
	
	public void addToDB() {
		
		String query = "Insert into exercise Values (null, ?, ?);";
		String[]params = {getTitle(), getDescription()};
		int newId = DbClient.insertData(query, params);
		if(newId != 0) {
			setId(newId);
		}
				
	}
	
	public void updateInDB() {

		String query = "Update exercise set title = ?, description = ? where id = ?;";
		String[]params = {getTitle(), getDescription(), Integer.toString( getId() )};

		DbClient.updateData(query, params);

	}
	
	public static void deleteById(int id) {

		String query = "Delete from exercise where id = ?;";
		String[]params = {Integer.toString(id)};

		DbClient.deleteData(query, params);

	}
	
	
	
}
