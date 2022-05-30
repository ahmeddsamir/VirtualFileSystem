import java.io.File;
import java.io.Serializable;

public class CommandsHandler implements Serializable {
    public static void parseCommand(String command){
        String[] arguments = command.split(" ");
        if(arguments[0].equals("CreateFile") && arguments.length == 3){
            FileSystem.getFileSystem().createFile(arguments[1], Integer.parseInt(arguments[2]));
        }
        else if (arguments[0].equals("CreateFolder") && arguments.length == 2){
            FileSystem.getFileSystem().createDirectory(arguments[1]);
        }
        else if (arguments[0].equals("DeleteFile") && arguments.length == 2){
            FileSystem.getFileSystem().deleteFile(arguments[1]);
        }
        else if (arguments[0].equals("DeleteFolder") && arguments.length == 2){
            FileSystem.getFileSystem().deleteFolder(arguments[1]);
        }
        else if (arguments[0].equals("DisplayDiskStatus")){
            FileSystem.getFileSystem().displayDiskStatus();
        }
        else if (arguments[0].equals("DisplayDiskStructure")){
            FileSystem.getFileSystem().getRootDirectory().printDirectoryStructure(0);
        }
        else if (arguments[0].equals("systemblocks")){
            FileSystem.getFileSystem().displaySystemBlocks();
        }
        else if (arguments[0].equals("TellUser")){
        }
        else if(arguments[0].equals("CUser") && arguments.length == 3){
            //arguments[1] username
            //arguments[2] password
            //admin only
            //no user with the same name already created
        }
        else if(arguments[0].equals("Grant") && arguments.length == 4){
            //arguments[1] username
            //arguments[2] path
            //arguments[3] capability digits
            //admin only
            //folders only
            //path exists
            //user exists
        }
        else if(arguments[0].equals("Login") && arguments.length == 3){
            //arguments[1] username
            //arguments[2] password
            //user exists
            //password is correct
        }
        else{
            System.out.println("Unknown Command");
        }
    }
}
