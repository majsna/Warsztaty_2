package bootcamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group {
	
	private int id = 0;
	private String groupName;

	public Group() {
		
	}

	public Group(String groupName) {
		setGroupName(groupName);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public Group setGroupName(String groupName) {
		this.groupName = groupName;
		return this;
	}
	
	public void saveToDB() {
		if(this.id == 0) {
			addToDB();
		}else {
			updateInDB();
		}
	}
	
	public void addToDB() {
		String query = "Insert into user_group Values (null, ?);";
		String[]params = {getGroupName()};
		int newId = DbClient.insertData(query, params);
		if(newId != 0) {
			setId(newId);
		}
		
	}
	
	public void updateInDB() {
		
		String query = "Update user_group set name = ? where id = ?;";
		String []params = {getGroupName(), Integer.toString(getId())};
		
		DbClient.updateData(query, params);		
	}
	
	public static Group loadById(int id) {
		
		String query = "Select * from user_group where id = ?;";
		String[]params = {Integer.toString(id)};
		
		Group group = null;
		
		try {
			ResultSet rs = DbClient.getData(query, params);
			
			if(rs.next()) {
				group = new Group(rs.getString(2));
				group.id = rs.getInt(1);
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			DbClient.closeConnection();
		}
		
		return group;
	}
	
	public static Group[] loadAll(){
		
		ArrayList<Group> groups = new ArrayList<>();
		String query = "Select * from user_group;";
		
		try {
			ResultSet rs = DbClient.getData(query,new String[0]);
			
			while (rs.next()) {
				Group tmpGroup = new Group(rs.getString(2));
				tmpGroup.id = rs.getInt(1);
				groups.add(tmpGroup);
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			DbClient.closeConnection();
		}
				
		Group[]groupList = new Group[groups.size()];
		groupList = groups.toArray(groupList);
		return groupList;
	}
	
	public static void viewAll() {
		
		Group[] groups = loadAll();
		for ( Group g : groups) {
			System.out.println(g.getId()+" - "+g.getGroupName());
		}
				
								
	}
	
	public static void deleteById(int id) {
		
		String query = "Delete from user_group where id = ?;";
		String[]params = {Integer.toString(id)};
		
		DbClient.deleteData(query, params);
	}
	
	
		
	
}
