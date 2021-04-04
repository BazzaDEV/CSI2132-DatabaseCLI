# CSI2132-DatabaseCLI
## Instructions:
1. Go to the file dbCLIlogin-TEMP.txt and input your sql database username and password and rename the file to remove -TEMP
2. Please enter today's date in YYYY-MM-DD format as a command line argument when running the Main class to start the application.
      - In Eclipse this is done but modifying the run configurations (run as, run configurations, arguments, program arguments, apply, run)
       ![image](https://user-images.githubusercontent.com/60792590/113496898-3d713280-94cc-11eb-86b4-d8cace4e4dcf.png)


### Sample input for Admin:
Admin SIN: 11111125
- Insert into Customer values(11111400,'John','Middle','Smith',27,'Streethere',5,'Paris','France','123456','2021-10-05','6131111125');
- Update customer Set first_name=Matt’ Where sin_number=11111400;
- Delete from Customer Where sin_number=11111400;



### Sample input for Customer:
Customer SIN: 11111111 -> 11111115, 11111201 -> 11111203
- SIN: 11111111
- What day would you like to check in: 2021-04-01
- What day would you like to check out: 2021-04-02
- City: Philadelphia 
- State: Pennsylvania
- Minimum price: 1 
- Maximum price:1000
- Would you like a (sea) or (mountain) view: mountain
- Would you like a (single) or (double) room: single
- Would you like an extendable room: y
- Are you looking for air conditioning(AC), television(TV) or a (fridge): AC

### Sample input for Employee:
Employee SIN:11111116 -> 11111120, 11111301
- SIN: 11111116
- Start date (YYYY-MM-DD): 2030-06-03
- End date (YYYY-MM-DD): 2030-06-04
- Minimum price: 1
- Maximum price: 1000
- Which view would you like: 1
- Which room size would you like: 1
- Would you like an extendable room: N
- Which hotel room amenity would you like: 1
