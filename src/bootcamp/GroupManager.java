package bootcamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class GroupManager {

public static void main(String[] args) {
		
		String db_name = "Kat_workshop_BIS";
		DbClient.db_name = db_name;	
				
		Scanner scan = new Scanner(System.in);
		String option = "";
		
		while(true){
			
			System.out.println(">>>Lista grup<<<");
			Group[] groups = Group.loadAll();
			for(Group g : groups) {
				System.out.println(g.getId()+" - "+g.getGroupName());
			}
			
			System.out.println("\nWybierz jedną z opcji:\n"
					+ "add - dodanie grupy\n"
					+ "edit - edycja grupy\n"
					+ "delete - usunięcie grupy\n"
					+ "quit - zakończenie programu");
			option = scan.nextLine();
			
			if(option.equals("add")) {
				add();
			}else if(option.equals("edit")) {
				edit();
			}else if(option.equals("delete")) {
				delete();
			}else if (option.equals("quit")){
				System.out.println("Wyszedłeś z programu. Zapraszamy ponownie.");
				System.exit(0);
			}else {
				System.out.println("Nie wybrałeś poprawnej opcji. Spróbuj ponownie.\n");
			}
			
			
		}		

	}
	
	public static void add() {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Podaj nazwę grupy:");
		String groupName = scan.nextLine();
				
		Group group = new Group(groupName);
		group.saveToDB();
		
	
	}
	
	public static void edit() {
		
		Scanner scan = new Scanner(System.in);
		
		int id = User.getInt("Podaj id grupy do edycji:");                                
		
		System.out.println("Podaj nową nazwę grupy:");
		String groupName = scan.nextLine();

		Group group = new Group(groupName);
		group.setId(id);
		group.updateInDB();
		
	}
	
	public static void delete() {
		
		Scanner scan = new Scanner(System.in);
		
		int id = User.getInt("Podaj id grupy, którą chcesz usunąć:");
		
		Group.deleteById(id);
			
	}

	


}
