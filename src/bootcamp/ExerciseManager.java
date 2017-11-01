package bootcamp;

import java.util.Scanner;

public class ExerciseManager {

	public static void main(String[] args) {
		
		String db_name = "Kat_workshop_BIS";
		DbClient.db_name = db_name;	
				
		Scanner scan = new Scanner(System.in);
		String option = "";
		
		while(true){
			
			System.out.println(">>>Lista ćwiczeń<<<");
			Exercise[] exercises = Exercise.loadAll();
			for(Exercise e : exercises) {
				System.out.println(e.getId()+" - "+e.getTitle()+" - "+e.getDescription());
			}
			
			System.out.println("\nWybierz jedną z opcji:\n"
					+ "add - dodanie zadania\n"
					+ "edit - edycja zadania\n"
					+ "delete - usunięcie zadania\n"
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
				System.out.println("Nie wybrałeś poprawnej opcji. Spróbuj ponownie.");
			}
			
			
		}

	}
	
	public static void add() {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Podaj tytuł zadania:");
		String title = scan.nextLine();
		System.out.println("Podaj opis zadania:");
		String description = scan.nextLine();
		
		Exercise ex = new Exercise(title, description);
		ex.saveToDB();
		
	
	}
	
	public static void edit() {
		
		Scanner scan = new Scanner(System.in);
	
		int id = User.getInt("Podaj id ćwiczenia do edycji:");
		
		System.out.println("Podaj nowy tytuł ćwiczenia:");
		String title = scan.nextLine();
		
		System.out.println("Podaj nowy opis ćwiczenia:");
		String description = scan.nextLine();

		Exercise ex = new Exercise(title, description);
		ex.setId(id);
		ex.updateInDB();
		
	}
	
	public static void delete() {
		
		Scanner scan = new Scanner(System.in);
		
		int id = User.getInt("Podaj id ćwiczenia, które chcesz usunąć:");
		
		Exercise.deleteById(id);
			
	}

}
