package cli.admin;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import utils.Helper;
import utils.Vars;
import users.Admin;

public class AdminMainMenu extends Menu {

	static String[]tables = new String[]{"hotel","worksfor","employee","supervises","hotelphonenum",
			"brandchain","hotelroom","hotelroomamenities","customer","hotelbrand",
			"hotelbrandemail","hotelbrandphonenum","booksfor","cancreate","payment",
			"booking","transformsinto","renting"};
	
	@Override
	public void start() {
		Helper.println("\n" + Vars.DIVIDER_EQUALS +
				"\n" + StringUtils.center("Database Admin - Main Menu", Vars.DIVIDER_EQUALS.length()) +
				"\n" + Vars.DIVIDER_EQUALS);
		System.out.println("\n Welcome Database Administrator.");

		//ask what query to write
		boolean FLAG = false;
		while (!FLAG) {
			String choice1 = Helper.getInput("\nWhat SQL query would you like to write?" + "\n insert" + "\n delete" + "\n update" + "\n exit \n");
			//exit admin menu
			if(choice1.equalsIgnoreCase("exit")) {
				FLAG=true;
			}

			if (Helper.multiCheck(choice1,new String[] {"insert","delete","update"})) { // answer is valid input

				switch(choice1) {
				//insert query
				case "insert":
					boolean FLAG2 = true;
					while(FLAG2) { //yes write
						String tableName="";
						do { //get table name
							tableName = Helper.getInput("\nWhat table do you want to insert data into?\n");
						}while(!Helper.multiCheck(tableName, tables));

						String data="";
						boolean flag1=false;
						while(!flag1) { //no incorrect
							data = Helper.getInput("What is " +tableName+ " data to insert:\n"); //table
							System.out.println("Data to insert: "+data); //information
							flag1 = Helper.ask("Is this the correct data to insert into "+tableName+" ?");
						}
						//11111125,'John','Middle','Smith',27,'Streethere',5,'Paris','France','123456','2021-10-05','6131111125'
						//calls function that insert query to db
						//Admin.adminInsert(tableName, data);
						FLAG2 = Helper.ask("Do you want to write another insert query?");
					}		
					break;
					//delete query
				case "delete":
					boolean FLAG3 = true;
					while(FLAG3) {	//yes write
						String tableName="";
						do {
							tableName = Helper.getInput("\nWhat table do you want to delete data from?\n");
						}while(!Helper.multiCheck(tableName, tables));

						boolean flag1=false;
						while(!flag1) { //no incorrect
							String condition1 = Helper.getInput("What is condition: "+"\n WHERE (condition?) \n"); //condition
							System.out.println("DELETE FROM "+tableName+"\n WHERE "+condition1);
							flag1 = Helper.ask("Is this the correct delete query ?");
						}	
						//calls admin function for delete query from db
						//Admin.adminDelete(tableName,condition1);
						FLAG3 = Helper.ask("Do you want to write another delete query?");
					}	
					break;
					//update query
				case "update":
					boolean FLAG4 = true;
					while(FLAG4) { //yes write
						String tableName="";
						do {
							tableName = Helper.getInput("\nWhat table do you want to Update data for?");
						}while(!Helper.multiCheck(tableName, tables));

						String choice="";
						boolean flag1=false;
						while(!flag1) { // no incorrect
							choice = Helper.getInput("\n What Update query would you like to execute?\n "
									+ "1) UPDATE tableName SET Condition1 WHERE condition2;\n"
									+ "2) UPDATE tableName SET condition1;\n");
							if(Helper.multiCheck(choice, new String[] {"1","2"})) {
								flag1 = Helper.ask("Is this the correct update query to use? \n"+"#"+choice);
							}
							//call admin function execute update query based on choice
							//Admin.adminUpdate(tablName,choice);
							FLAG4 = Helper.ask("Do you want to write another update query?");
						}	
						break;
					}
				}
			}
		}
	}
}
