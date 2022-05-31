import java.io.File;
import java.io.FileWriter;
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
            if (capabilitiesFile.createNewFile()) {
                capabilities.add("root,admin,11");
            }
            else{
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

    public void grantCapability(String path, String username, String create, String delete){
        String toBeAdded = "";
        String targetEntry = "";
        int pathEnd = 0;
        //Check if the admin is logged on
        if(FileSystem.getFileSystem().getUsersManager().getLoggedInUsername().equals("admin")){
            //Check if the path exists
            if(FileSystem.getFileSystem().searchDirectory(FileSystem.getFileSystem().getRootDirectory(), path) != null){
                for(String capability : capabilities){
                    pathEnd = capability.indexOf(",");
                    if(capability.equals(path) || (pathEnd > 0 && capability.substring(0, pathEnd).equals(path))){
                        targetEntry = capability;
                        toBeAdded = capability + "," + username + "," + create + delete;
                        break;
                    }
                }
                capabilities.remove(targetEntry);
                capabilities.add(toBeAdded);
            }
            else{
                System.out.println("Path doesn't exist!");
            }
        }
        else{
            System.out.println("You are not authorized to do this action!");
        }
    }

    public boolean isAuthorized(String path, int action){
        int pathEnd = 0;
        boolean authorized = false;
        while(true){
            for(String capability : capabilities){
                pathEnd = capability.indexOf(",");
                if(pathEnd > 0 && capability.substring(0, pathEnd).equals(path)){
                    if(action == 0){
                        authorized = createAuthorization(capability);
                    }
                    else{
                        authorized = deleteAuthorization(capability);
                    }
                }
            }
            if(authorized){
                return true;
            }
            if(path.equals("root")){
                return false;
            }
            path = path.substring(0, path.lastIndexOf("/"));
        }
    }

    public boolean createAuthorization(String entry){
        if(entry.contains(FileSystem.getFileSystem().getUsersManager().getLoggedInUsername() + ",1")){
            return true;
        }
        return false;
    }

    public boolean deleteAuthorization(String entry){
        if(entry.contains(FileSystem.getFileSystem().getUsersManager().getLoggedInUsername() + ",11") || entry.contains(FileSystem.getFileSystem().getUsersManager().getLoggedInUsername() + ",01")){
            return true;
        }
        return false;
    }

    public void addEntry(String path){
        capabilities.add(path);
    }

    public void removeEntry(String path){
        String toBeRemoved = "";
        for(String capability : capabilities){
            if(capability.startsWith(path)){
                toBeRemoved = capability;
                break;
            }
        }
        capabilities.remove(toBeRemoved);
    }

    public void persistCapabilities(){
        try {
            FileWriter writer = new FileWriter("capabilities.txt");
            String toBeWritten = "";
            for(String capability : capabilities){
                toBeWritten = toBeWritten + capability + "\n";
            }
            writer.write(toBeWritten);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
