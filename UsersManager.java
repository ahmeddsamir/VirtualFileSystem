import java.io.File;
import java.io.IOException;

public class UsersManager {

    private User loggedInUser;

    public UsersManager() {
        File users = new File("users.txt");

        //If file didn't exist, create it and add admin
        try {
            if (users.createNewFile()) {
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

    public void register(String username, String password){

    }

    public void login(String username, String password){

    }
}
