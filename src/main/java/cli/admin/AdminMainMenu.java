package cli.admin;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import utils.Helper;
import utils.Vars;
import users.Admin;

public class AdminMainMenu extends Menu {

	@Override
	public void start() {

		Helper.println("\n" + Vars.DIVIDER_EQUALS +
				"\n" + StringUtils.center("Database Admin - Main Menu", Vars.DIVIDER_EQUALS.length()) +
				"\n" + Vars.DIVIDER_EQUALS);

		System.out.println("\n Welcome Database Administrator.");

		
		boolean FLAG = false;
		while (!FLAG) {
			String choice1 = Helper.getInput("\nWhat SQL query would you like to write?" + "\n insert" + "\n delete" + "\n update" + "\n exit \n");

			if(choice1.equalsIgnoreCase("exit")) {
				FLAG=true;
			}
			
			if (Helper.multiCheck(choice1,new String[] {"insert","delete","update"})) { // answer is valid input
				switch(choice1) {
				case "insert":
					boolean FLAG2 = false;
					while(!FLAG2) {
						//Admin.adminInsert();
						FLAG2 = Helper.ask("Do you want to write another insert query?");
					}		
					break;
				case "delete":
					boolean FLAG3 = false;
					while(!FLAG3) {
						//Admin.adminDelete();
						FLAG3 = Helper.ask("Do you want to write another delete query?");
					}	
					break;
				case "update":
					boolean FLAG4 = false;
					while(!FLAG4) {
						//Admin.adminUpdate();
						FLAG4 = Helper.ask("Do you want to write another update query?");
					}	
					break;
				}
			}
		}
	}
}
