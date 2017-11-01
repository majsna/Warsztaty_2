package bootcamp;

import java.sql.ResultSet;
import java.util.Scanner;

public class UserAddSolution {

	public static void main(String[] args) {
		
		String db_name = "Kat_workshop_BIS";
		DbClient.db_name = db_name;
		
		User.printAllUsers();
		int id = User.getInt("Podaj Id użytkownia, którego rozwiązania chcesz dodać: ");
		
		Scanner scan = new Scanner(System.in);
		String option = "";
		
		while(true){
			
			System.out.println("\nWybierz jedną z opcji:\n"
					+ "add - dodaj rozwiązanie\n"
					+ "view - wyświetl rozwiązania danego użutkownika\n"
					+ "quit - zakończenie programu");
			option = scan.nextLine();
			
			if(option.equals("add")) {
				addSolution(id);
			}else if(option.equals("view")) {
				System.out.println("===Rozwiązania===");
				Solution.printAllByUserId(id);
			}else if (option.equals("quit")){
				System.out.println("Wyszedłeś z programu. Zapraszamy ponownie.");
				System.exit(0);
			}else {
				System.out.println("Nie wybrałeś poprawnej opcji. Spróbuj ponownie.\n");
			}
						
		}

	}
	
	public static void addSolution(int id) {
		
		Exercise.printAllEmptyByUserId(id);
		System.out.println("\nPowyższa lista zawiera zadania do których nie dodałeś jeszcze rozwiązań.");
		
		int exId = User.getInt("Podaj Id zadania do, którego chcesz dodać rozwiązanie.");
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Podaj rozwiązanie: ");
		String solution = scan.nextLine();
		
//		if (checkIfSolutionExist(id,exId) == false){
			String query = "Update solution set description = ?, updated = CURRENT_TIMESTAMP where users_id = ? and exercise_id = ?;";
			String[]params = { solution, Integer.toString(id) , Integer.toString(exId)};		
			boolean solutioanAdded = DbClient.updateData(query, params);
			if(solutioanAdded == true) {
				System.out.println("Rozwiązanie zostało dodane.");
			}
//		} else {
//			System.out.println("Nie można dodać rozwiązania, ponieważ zostało już ono dodane wcześniej.");
//		}
		
		
	}
	
	public static boolean checkIfSolutionExist(int userId, int exId) {
		
		String query = "Select description from solution where users_id = ? and exercise_id = ? and description is not null;";
		String[]params = {Integer.toString(userId), Integer.toString(exId)};
		
		
		ResultSet rs = DbClient.getData(query, params);
		
		if(rs == null) {
			return false;
		}else {
			return true;
		}
		
	}
	


}
