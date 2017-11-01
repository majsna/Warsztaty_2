package bootcamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


public class User {
	
	private int id = 0;
	private String username;
	private String email;
	private String password;
	private int groupId;

	public User() {
		
	}

	public User(String username, String email, String password, int groupId) {
		setUsername(username);
		setEmail(email);
		setPassword(password);
		setGroupId(groupId);
	}
	
	public int getGroupId() {
		return groupId;
	}

	public User setGroupId(int groupId) {
		this.groupId = groupId;
		return this;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = BCrypt.hashpw(password,	BCrypt.gensalt());//TODO dodać szyforwanie
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
		String query = "Insert into users Values (null, ?, ?, ?, ?);";
		String[]params = {getEmail(), getUsername(), getPassword(), Integer.toString(getGroupId())};
		int newId = DbClient.insertData(query, params);
		if(newId != 0) {
			setId(newId);
		}
				
	}
	
	public void updateInDB() {
		
		String query = "Update users set username = ?, email = ?, password = ?, person_group_id = ? where id = ?;";
		String[]params = {getUsername(), getEmail(), getPassword(),Integer.toString(getGroupId()),Integer.toString( getId() )};
		
		DbClient.updateData(query, params);
		
	}
	
	public static User loadUserById(int id) {
		
		String query = "Select * from users where id = ?;";
		String[]params = {Integer.toString(id)};
		
		User user = null;
		
		try {
			ResultSet rs = DbClient.getData(query, params);
			
			if(rs.next()) {
				user = new User(rs.getString(3), rs.getString(2), rs.getString(4), rs.getInt(5) );
				user.id = rs.getInt(1);
				user.password = rs.getString(4);
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			DbClient.closeConnection();
		}
		
		return user;
		
	}
	
	public static User[] loadAll() {
		
		ArrayList<User> users = new ArrayList<>();
		String query = "Select * from users;";
		
		try {
			ResultSet rs = DbClient.getData(query, new String[0]);
			
			while(rs.next()) {
				User tmpUser = new User(rs.getString(3), rs.getString(2), rs.getString(4), rs.getInt(5) );
				tmpUser.id = rs.getInt(1);
				tmpUser.password = rs.getString(4);

				users.add(tmpUser);
			}
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally{
			DbClient.closeConnection();
		}
		
		User[]usersList = new User[users.size()];
		usersList = users.toArray(usersList);
		return usersList;
	}
	
	
	public static void printAllUsers() {
		
		System.out.println(">>>Lista użytkowników<<<");
		User[]users = loadAll();
		for(User u : users) {
			System.out.println(u.getId()+" - "+u.getUsername());
		}
	}
	
	public static User[] loadAllByGroupId(int groupId) {

		ArrayList<User> users = new ArrayList<>();
		String query = "Select * from users where person_group_id = ?;";
		String[]params = {Integer.toString(groupId)};

		try {
			ResultSet rs = DbClient.getData(query, params);

			while(rs.next()) {
				User tmpUser = new User(rs.getString(3), rs.getString(2), rs.getString(4), rs.getInt(5) );
				tmpUser.id = rs.getInt(1);
				tmpUser.password = rs.getString(4);

				users.add(tmpUser);
			}

		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally{
			DbClient.closeConnection();
		}

		User[]usersList = new User[users.size()];
		usersList = users.toArray(usersList);
		return usersList;
	}
	
	public static void printAllUsersByGroupId(int groupId) {

		User[]users = loadAllByGroupId(groupId);
		for(User u : users) {
			System.out.println(u.getId()+" - "+u.getUsername()+", Group Id - "+u.getGroupId());
		}
	}
	
	public static void deleteById(int id) {
		
		String query = "Delete from users where id = ?;";
		String[]params = {Integer.toString(id)};
		
		DbClient.deleteData(query, params);
		
	}
	
	public static int getInt(String message) {
    	Scanner scan = new Scanner(System.in);
    	System.out.println(message);
    	while(!scan.hasNextInt()) {
    		System.out.println("Podane Id nie jest liczbą!\n"+message);
    		scan.next();
    	}
    	return scan.nextInt();
    	
    }

	
}
