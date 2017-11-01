package bootcamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Solution {
	
	private int id = 0;
	private String description;
	private int userId;
	private int exerciseId;
	private String created;
	private String updated;

	public Solution() {
		
	}

	public Solution(int userId, int exerciseId) {
		setUserId(userId);
		setExerciseId(exerciseId);	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public Solution setDescription(String description) {
		this.description = description;
		return this;
	}

	public int getUserId() {
		return userId;
	}

	public Solution setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public Solution setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
		return this;
	}
	
	public String getCreated() {
		return created;
	}

	public Solution setCreated(String created) {
		this.created = created;
		return this;
	}

	public String getUpdated() {
		return updated;
	}

	public Solution setUpdated(String updated) {
		this.updated = updated;
		return this;
	}
	
	public void addToDB() {
		String query = "Insert into solution(users_id, exercise_id, created) Values (?, ?, CURRENT_TIMESTAMP);";
		String[]params = {Integer.toString(getUserId()), Integer.toString(getExerciseId())};
		int newId = DbClient.insertData(query, params);
		if(newId != 0) {
			setId(newId);
		}
		
	}
	

	public static Solution[] loadAllByUserId(int id) {
		
		String query = "Select * from solution where users_id = ?;";
		String[]params = {Integer.toString(id)};
		
		ArrayList<Solution> solutions = new ArrayList<>();
		
		try {
			ResultSet rs = DbClient.getData(query, params);
			
			while(rs.next()) {
				Solution tmpSol = new Solution(Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6)));
				tmpSol.setDescription(rs.getString(4));
				tmpSol.setCreated(rs.getString(2));
				tmpSol.setUpdated(rs.getString(3));
				tmpSol.setId(rs.getInt(1));	
				solutions.add(tmpSol);
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			DbClient.closeConnection();
		}
		
		Solution[]solList = new Solution[solutions.size()];
		solList = solutions.toArray(solList);
		return solList;
		
	}
	
	public static void printAllByUserId(int id) {
		
		Solution[] usersSolutions = loadAllByUserId(id);
		
		for(Solution s : usersSolutions) {
			System.out.println("Id zadania - "+s.getExerciseId()
								+" - rozwiązanie:\n"
								+s.getDescription());
		}
				
	}
	
	public static Solution[] loadAllByExerciseId(int exId) {
		
		String query = "Select * from solution where exercise_id = ? order by updated asc;";
		String[]params = {Integer.toString(exId)};
		
		ArrayList<Solution> solutions = new ArrayList<>();

		try {
			ResultSet rs = DbClient.getData(query, params);

			while(rs.next()) {
				Solution tmpSol = new Solution(Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6)));
				tmpSol.setDescription(rs.getString(4));
				tmpSol.setCreated(rs.getString(2));
				tmpSol.setUpdated(rs.getString(3));
				tmpSol.setId(rs.getInt(1));	
				solutions.add(tmpSol);
			}

		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			DbClient.closeConnection();
		}

		Solution[]solList = new Solution[solutions.size()];
		solList = solutions.toArray(solList);
		return solList;
	}
	
	public static void printAllByExerciseId(int exId) {
		
		Solution[] exerrciseSolutions = loadAllByExerciseId(exId);

		for(Solution s : exerrciseSolutions) {
			System.out.println("Id zadania - "+s.getExerciseId()
			+", Id użytkownika - "+s.getUserId()
			+", dodano - "+s.getCreated()
			+"\n   Rozwiązanie:\n    "
			+s.getDescription()+"\n");
		}
	}
	
}
