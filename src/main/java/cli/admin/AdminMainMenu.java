package cli.admin;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import utils.Helper;
import utils.Vars;
import users.Admin;

public class AdminMainMenu extends Menu {

	//make another array of column names
	//match to tables array
	//get index from array
	
	//create backup of db 
	//test the admin queries
	
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

				//insert query
				if(choice1.equalsIgnoreCase("insert")) {
					boolean FLAG2 = true;
					while(FLAG2) { //yes write
						String tableName="";
						boolean flag2=false;
						while(!flag2) {
							//get table name
							tableName = Helper.getInput("\nWhat table do you want to insert data into?\n"); // try to get column names/ format of column names - print cusomter attribute names
							if(Helper.multiCheck(tableName, Admin.getTables())) {
							System.out.println(Helper.getCols(tableName, Admin.getTables(), Admin.getCols()));		
							flag2 = Helper.ask("Is this the correct table? " + tableName);
							}
						}

						String data="";
						boolean flag1=false;
						while(!flag1) { //no incorrect
							data = Helper.getInput("What is " +tableName+ " data to insert:\n"); //table
							System.out.println("Data to insert: "+data); //information
							flag1 = Helper.ask("Is this the correct data to insert into "+tableName+" ?"); //make message for when data not inserted
							if(!flag1) {
								System.out.println("Data was not inserted.");
							}
						}
						
						//11111125,'John','Middle','Smith',27,'Streethere',5,'Paris','France','123456','2021-10-05','6131111125'
						//calls function that insert query to db
						Admin.adminInsert(tableName, data);
						FLAG2 = Helper.ask("Do you want to write another insert query?");
					}		
				}
				//delete query
				else if(choice1.equalsIgnoreCase("delete")) {
					boolean FLAG3 = true;
					while(FLAG3) {	//yes write
						String tableName="";
						boolean flag4=false;
						while(!flag4) {
							tableName = Helper.getInput("\nWhat table do you want to delete data from?\n");
							if(Helper.multiCheck(tableName, Admin.getTables())) {
							System.out.println(Helper.getCols(tableName, Admin.getTables(), Admin.getCols()));
							flag4 = Helper.ask("Is this the correct table? " + tableName);
							}
						}

						String condition1="";
						boolean flag1=false;
						while(!flag1) { //no incorrect
							condition1 = Helper.getInput("What is condition: "+"\n WHERE (condition?) \n"); //condition
							System.out.println("DELETE FROM "+tableName+"\n WHERE "+condition1 + ";");
							flag1 = Helper.ask("Is this the correct delete query ?");
							if(!flag1) {
								System.out.println("Data was not deleted.");
							}
						}	
						//calls admin function for delete query from db
						Admin.adminDelete(tableName,condition1);
						FLAG3 = Helper.ask("Do you want to write another delete query?");
					}	
				}
				//update query
				else if(choice1.equalsIgnoreCase("update")) {
					boolean FLAG4 = true;
					while(FLAG4) { //yes write
						String tableName="";

						boolean flag3=false;
						while(!flag3) {
							tableName = Helper.getInput("\nWhat table do you want to Update data for?");			
							if(Helper.multiCheck(tableName, Admin.getTables())) {
							System.out.println(Helper.getCols(tableName, Admin.getTables(), Admin.getCols()));
							flag3 = Helper.ask("Is this the correct table? " + tableName);
							}
						}

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
							Admin.adminUpdate(tableName,choice);
							FLAG4 = Helper.ask("Do you want to write another update query?");
						}	
					}
				}
			}
		}
	}
}
