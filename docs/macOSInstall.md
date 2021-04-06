# macOS Install Instructions

1. Navigate to **Releases** and find the latest release.

![image](https://user-images.githubusercontent.com/28697372/113635919-8269a600-963f-11eb-827e-30b9dfe018f8.png)

2. Looking at the **Assets** dropdown, click **DatabaseCLI-vX.X.X.jar** to download the executable JAR.

![image](https://user-images.githubusercontent.com/28697372/113635808-4a626300-963f-11eb-8c39-c1ab1d7465fa.png)

3. Locate the JAR on your computer.

![CleanShot 2021-04-05 at 19 03 16](https://user-images.githubusercontent.com/28697372/113637114-dbd2d480-9641-11eb-8f4e-0aa7ebf8084d.gif)

4. Rename the JAR to remove the version number for simplicity - should look like **DatabaseCLI.jar**.

![CleanShot 2021-04-05 at 18 52 15](https://user-images.githubusercontent.com/28697372/113636241-29e6d880-9640-11eb-8f56-0d812b3b1663.gif)

5. Copy the location (file path) of the JAR. 

      An easy way to do this:
      - **control + click** the file, then hold the **Option** key.
      - Click _Copy "DatabaseCLI.jar as Pathname"_ to copy the file path to the clipboard.
      
![CleanShot 2021-04-05 at 19 26 50](https://user-images.githubusercontent.com/28697372/113638502-feb2b800-9644-11eb-804d-ea635a710ad8.gif)

6. Open up a new Terminal window.
   You can do this quickly by pressing **Command + Space** to open a Spotlight window, typing _Terminal_ and hitting **Enter**.

![CleanShot 2021-04-05 at 19 19 09](https://user-images.githubusercontent.com/28697372/113638030-f148fe00-9643-11eb-9ddd-0584fee0a942.gif)

7. Using Terminal, navigate to the JAR location using `cd path/To/Jar`. 
   If the file path is saved to your clipboard, hit **Comamnd + V** to paste it, then remove the JAR file from the path.
   
![CleanShot 2021-04-05 at 19 29 16](https://user-images.githubusercontent.com/28697372/113638635-4cc7bb80-9645-11eb-93b2-fbf8e053fd15.gif)

8. To run the CLI, enter the following into Terminal:  
```
java -jar DatabaseCLI.jar YYYY-MM-DD
```
Replace `YYYY-MM-DD` with a date of your choosing - this will be interpreted as today's date.

9. Enter your PostgreSQL credentials to start the application.

![CleanShot 2021-04-05 at 19 30 35](https://user-images.githubusercontent.com/28697372/113638831-c495e600-9645-11eb-8eb7-2036e5ae15ef.gif)
