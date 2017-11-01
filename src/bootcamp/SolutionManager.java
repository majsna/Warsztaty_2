package bootcamp;

import java.util.Scanner;

public class SolutionManager {

	public static void main(String[] args) {
		
		String db_name = "Kat_workshop_BIS";
		DbClient.db_name = db_name;
		
		Scanner scan = new Scanner(System.in);
		String option = "";
		
		while(true){
			
			System.out.println("\nWybierz jedną z opcji:\n"
					+ "add - przypisz zadanie do użytkownika\n"
					+ "view - wyświetl rozwiązania danego użutkownika\n"
					+ "viewEx - wyświetl kolejno wszystkie rozwiązania danego zadania\n"
					+ "quit - zakończenie programu");
			option = scan.nextLine();
			
			if(option.equals("add")) {
				add();
			}else if(option.equals("view")) {
				view();
			}else if(option.equals("viewEx")) {
				viewByExerciseId();
			}else if (option.equals("quit")){
				System.out.println("Wyszedłeś z programu. Zapraszamy ponownie.");
				System.exit(0);
			}else {
				System.out.println("Nie wybrałeś poprawnej opcji. Spróbuj ponownie.\n");
			}
						
		}

	}
	
	public static void add() {
		
		System.out.println(">>>Lista użytkowników<<<");
		User[] users = User.loadAll();
		for(User u : users) {
			System.out.println(u.getId()+" "+u.getUsername());
		}
		
		int id = User.getInt("Podaj id użytkownika, któremu chcesz przypisać zadanie:");
		
		System.out.println(">>>Lista ćwiczeń<<<");
		Exercise[] exercises = Exercise.loadAll();
		for(Exercise e : exercises) {
			System.out.println(e.getId()+" - "+e.getTitle()+" - "+e.getDescription());
		}
		
		int exId = User.getInt("Podaj id zadania, które chcesz przypisać:");
		
		Solution sol = new Solution(id, exId);
		sol.addToDB();
		
	}
	
	public static void view() {
		
		System.out.println(">>>Lista użytkowników<<<");
		User[] users = User.loadAll();
		for(User u : users) {
			System.out.println(u.getId()+" "+u.getUsername());
		}
		
		int id = User.getInt("Podaj id użytkownika, którego rozwiązania chcesz zobaczyć:");
		
		Solution.printAllByUserId(id);
		
	}
	
	public static void viewByExerciseId() {
		
		int exId = User.getInt("Podaj Id zadania, którego rozwiązania chcesz wyświetlić: ");
		Solution.printAllByExerciseId(exId);
		
	}

}
