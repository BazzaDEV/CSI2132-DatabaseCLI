package cli.admin;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import structs.Pair;
import users.Admin;
import users.User;
import utils.Helper;
import utils.Messages;
import utils.Vars;

import java.util.ArrayList;
import java.util.List;

public class AdminMainMenu extends Menu {

    private Admin a;

    private ArrayList<Pair<String, String>> relationalSchema;

    @Override
	public void start() {

    	relationalSchema = (ArrayList<Pair<String, String>>) Vars.getSchema();

        User u = cliManager.getUser();

        if (u instanceof Admin) {
            a = (Admin) u;
        }

        Helper.println("\n" + Vars.DIVIDER_EQUALS +
				"\n" + StringUtils.center("Database Admin - Main Menu", Vars.DIVIDER_EQUALS.length()) +
				"\n" + Vars.DIVIDER_EQUALS);
        Helper.println("\nWelcome back, " + a.getName().getFirstName() + "!");


        //ask what query to write
		boolean FLAG = false;
		while (!FLAG) {
			String choice1 = Helper.getInput("\nWhat SQL query would you like to write?" +
					"\n(1) Insert" +
					"\n(2) Delete" +
					"\n(3) Update" +
					"\n(4) Sign Out" +
					"\n>> ");
			//exit admin menu
			if (Helper.isValid(choice1, 4)) { // answer is valid input

				if (choice1.equalsIgnoreCase("1")) { // insert query
					insertQuery();

				} else if (choice1.equalsIgnoreCase("2")) { // delete query
					deleteQuery();

				} else if (choice1.equalsIgnoreCase("3")) { // update query
					updateQuery();

				} else if (choice1.equalsIgnoreCase("4")) {
					FLAG = true;
					cliManager.prevMenu();
				}

			} else { // Invalid entry
				Helper.println(Messages.INVALID_ENTRY);

			}

		}
    }

	private void insertQuery() {

    	String tableName = null, data = null;
    	Pair<String, String> table = null;

		Helper.println("\n" + Vars.DIVIDER_EQUALS +
				"\n" + StringUtils.center("Admin >> Insert Query", Vars.DIVIDER_EQUALS.length()) +
				"\n" + Vars.DIVIDER_EQUALS);

		boolean FLAG2 = true;
		while (FLAG2) { //yes write

			Helper.println("\nWhat table do you want to insert data into?");

			boolean flag2 = false;
			while (!flag2) {
				//get table name
				tableName = Helper.getInput(">> ");

				String finalTableName = tableName;
				table = relationalSchema.stream()
						.filter(p -> p.getX().equalsIgnoreCase(finalTableName))
						.findFirst()
						.orElse(null);

				if (table != null) {
					tableName = table.getX();
					System.out.println("\n" + tableName + "(" + table.getY() + ")");
					flag2 = Helper.ask("\nIs this the correct table?");
				}
			}

			boolean flag1 = false;
			while (!flag1) { //no incorrect
				data = Helper.getInput("\nWhat is " + tableName + " data to insert?" +
						"\n>> "); //table
				System.out.println("\nData to insert: " + data); //information
				flag1 = Helper.ask("\nIs this the correct data to insert into "+ tableName +"?");
				if(!flag1) {
					System.out.println("\nData was not inserted.");
				}
			}

			//11111125,'John','Middle','Smith',27,'Streethere',5,'Paris','France','123456','2021-10-05','6131111125'
			//calls function that insert query to db
			Admin.adminInsert(tableName, data);
			FLAG2 = Helper.ask("\nDo you want to write another insert query?");


	}
}

	private void deleteQuery() {

		Helper.println("\n" + Vars.DIVIDER_EQUALS +
				"\n" + StringUtils.center("Admin >> Delete Query", Vars.DIVIDER_EQUALS.length()) +
				"\n" + Vars.DIVIDER_EQUALS);

		String tableName = null, condition = null;
		Pair<String, String> table = null;

		boolean FLAG3 = true;
		while (FLAG3) {    //yes write

			Helper.println("\nWhat table do you want to delete data from?");

			boolean flag4 = false;
			while (!flag4) {
				tableName = Helper.getInput(">> ");

				String finalTableName = tableName;
				table = relationalSchema.stream()
						.filter(p -> p.getX().equalsIgnoreCase(finalTableName))
						.findFirst()
						.orElse(null);

				if (table != null) {
					tableName = table.getX();
					System.out.println("\n" + tableName + "(" + table.getY() + ")");
					flag4 = Helper.ask("\nIs this the correct table?");
				}
			}

			boolean flag1 = false;
			while (!flag1) { //no incorrect
				condition = Helper.getInput("\nWhat is condition for the query?"
						+ "\n	DELETE FROM " + tableName
						+ "\n	WHERE condition ;" +
						"\n>> "); //condition
				System.out.println("\nDELETE FROM " + tableName + "\n WHERE " + condition + ";");
				flag1 = Helper.ask("\nIs this the correct delete query?");
				if (!flag1) {
					System.out.println("\nData was not deleted.");
				}
			}
			//calls admin function for delete query from db
			Admin.adminDelete(tableName, condition);
			FLAG3 = Helper.ask("\nDo you want to write another delete query?");
		}
	}

	private void updateQuery() {

		Helper.println("\n" + Vars.DIVIDER_EQUALS +
				"\n" + StringUtils.center("Admin >> Update Query", Vars.DIVIDER_EQUALS.length()) +
				"\n" + Vars.DIVIDER_EQUALS);

		String tableName = null, choice = null;
		Pair<String, String> table = null;

		boolean FLAG4 = true;
		while (FLAG4) { //yes write

			Helper.println("\nWhat table do you want to update data for?");

			boolean flag3 = false;
			while (!flag3) {
				tableName = Helper.getInput(">> ");

				String finalTableName = tableName;
				table = relationalSchema.stream()
						.filter(p -> p.getX().equalsIgnoreCase(finalTableName))
						.findFirst()
						.orElse(null);

				if (table != null) {
					tableName = table.getX();
					System.out.println("\n" + tableName + "(" + table.getY() + ")");
					flag3 = Helper.ask("\nIs this the correct table?");
				}
			}

			boolean flag1 = false;
			while (!flag1) { // no incorrect
				choice = Helper.getInput("\nWhich update query would you like to execute?" +
						"\n(1) UPDATE tableName SET condition1 WHERE condition2;" +
						"\n(2) UPDATE tableName SET condition1;" +
						"\n>> ");
				if (Helper.isValid(choice, 2)) {
					flag1 = Helper.ask("\nIs #" + choice + " the correct update query to use?");
				}
			}
			//call admin function execute update query based on choice
			Admin.adminUpdate(tableName, choice);
			FLAG4 = Helper.ask("\nDo you want to write another update query?");
		}
	}
}
