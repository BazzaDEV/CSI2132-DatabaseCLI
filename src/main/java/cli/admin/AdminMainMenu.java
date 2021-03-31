package cli.admin;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import utils.Helper;
import utils.Vars;

public class AdminMainMenu extends Menu {

	@Override
	public void start() {

		Helper.println("\n" + Vars.DIVIDER_EQUALS +
				"\n" + StringUtils.center("Database Admin - Main Menu", Vars.DIVIDER_EQUALS.length()) +
				"\n" + Vars.DIVIDER_EQUALS);

		System.out.println("\n Welcome Database Administrator.");

		/*
    	boolean FLAG = false;
    	while (!FLAG) {
    		String choice1 = Helper.getInput("\nWhat SQL query would you like to write?" + "\n insert" + "\n delete" + "\n update");

    		if (Helper.multiCheck(choice1,new String[] {"insert","delete","update"})) { // answer is valid input
                FLAG = true;

              switch(choice1) {
              case "insert":
            	  Admin.adminInsert();
            	  break;
              case "delete":
            	  //Helper.adminDelete();
            	  break;
              case "update":
            	  //Helper.adminUpdate();
            	  break;
              }
		 */      
	}
}
