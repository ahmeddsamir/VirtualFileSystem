import java.io.*;
import java.util.Scanner;

public class UsersManager implements Serializable{

    private User loggedInUser = new User("admin", "admin");

    public UsersManager() {
        File users = new File("users.txt");

        //If file didn't exist, create it and add admin
        try {
            if (users.createNewFile()) {
                loggedInUser.setUsername("admin");
                loggedInUser.setPassword("admin");
                register("admin", "admin");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        login("admin", "admin");
    }

    public String getLoggedInUsername(){
        return loggedInUser.getUsername();
    }

    public boolean userExists(String username){
        File usersFile = new File("users.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(usersFile);
            while (scanner.hasNextLine()) {
                if(scanner.nextLine().contains(username)){
                    return true;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void register(String username, String password){
        if(loggedInUser.getUsername().equals("admin") && loggedInUser.getPassword().equals("admin")){
            boolean exists = false;
            File usersFile = new File("users.txt");
            try {
                //if (!usersFile.createNewFile()) {
                    Scanner scanner = new Scanner(usersFile);
                    while (scanner.hasNextLine()) {
                        if(scanner.nextLine().contains(username)){
                            exists = true;
                            break;
                        }
                    }
                    scanner.close();
                //}
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!exists){
                try {
                    FileWriter writer = new FileWriter(usersFile, true);
                    String newUser = username + "," + password + "\n";
                    writer.write(newUser);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(username + " Registered Successfully.");
            }
            else{
                System.out.println(username + " Already Exists!");
            }
        }
        else{
            System.out.println("You are not authorized to do this action!");
        }
    }

    public void login(String username, String password){
        File usersFile = new File("users.txt");
        boolean loggedIn = false;
        try {
            Scanner scanner = new Scanner(usersFile);
            while(scanner.hasNextLine()){
                if(scanner.nextLine().equals(username + "," + password)){
                    loggedInUser.setUsername(username);
                    loggedInUser.setPassword(password);
                    System.out.println(username + " Logged In Successfully.");
                    loggedIn = true;
                    break;
                }
            }
            if(!loggedIn){
                System.out.println("Incorrect Credentials!");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
