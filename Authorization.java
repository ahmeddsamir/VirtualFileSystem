import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Authorization implements Serializable {
    private ArrayList<String> capabilities = new ArrayList<>();

    public Authorization() {
        File capabilitiesFile = new File("capabilities.txt");

        //If file didn't exist, create it, else write file into arraylist
        try {
            if (!capabilitiesFile.createNewFile()) {
                Scanner scanner = new Scanner(capabilitiesFile);
                while (scanner.hasNextLine()) {
                    capabilities.add(scanner.nextLine());
                }
                scanner.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void grantCapability(String path, String username, int create, int delete){
        if(FileSystem.getFileSystem().getUsersManager().getLoggedInUsername().equals("admin") && /*user exist &&*/ FileSystem.getFileSystem().searchDirectory(FileSystem.getFileSystem().getRootDirectory(), path) != null ){
            //write capability to file
        }
        //admin only
        //path exists
        //user exists
    }

    public boolean isAuthorized(String path, String username){
        return true;
    }
}
