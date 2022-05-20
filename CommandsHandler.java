public class CommandsHandler {
    public static void parseCommand(String command){
        String[] arguments = command.split(" ");
        if(arguments[0].equals("CreateFile") && arguments.length == 3){

        }
        else if (arguments[0].equals("CreateFolder") && arguments.length == 2){

        }
        else if (arguments[0].equals("DeleteFile") && arguments.length == 2){

        }
        else if (arguments[0].equals("DeleteFolder") && arguments.length == 2){

        }
        else if (arguments[0].equals("DisplayDiskStatus")){

        }
        else if (arguments[0].equals("DisplayDiskStructure")){

        }
        else{
            System.out.println("Unknown Command");
        }
    }
}
