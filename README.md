### Important Note for TA:
Please **ignore** the install instructions in our report, and instead follow the instructions here.  
Thanks!

# How to Install (*UPDATED!*):

* [macOS](docs/macOSInstall.md)   
* [Windows](docs/WindowsInstall.md)
* [Alternative Install](docs/altInstall.md) _**(outdated - do not use.)**_

# Guide

Using the CLI fairly self-explanatory. Here are some useful features:

### Universal Keys
From any input screen in the CLI, you can...
* Go back to the previous menu by clicking **`**
* Exit the CLI by clicking **```**

![CleanShot 2021-04-05 at 20 50 58](https://user-images.githubusercontent.com/28697372/113643826-3de70600-9651-11eb-8429-c88a3865e943.png)

# Sample Inputs
### Admin:
Admin SIN: 11111125
- Insert into Customer values(11111400,'John','Middle','Smith',27,'Streethere',5,'Paris','France','123456','2021-10-05','6131111125');
- Update customer Set first_name=Matt’ Where sin_number=11111400;
- Delete from Customer Where sin_number=11111400;

### Customer:
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

### Employee:
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
