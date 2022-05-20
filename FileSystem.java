import java.io.*;
import java.nio.file.Path;

public class FileSystem implements Serializable {
    private static FileSystem fileSystem; //singleton
    private boolean[] systemBlocks;
    private int diskSize;
    private Directory root;

    private FileSystem(){

    }

    public static FileSystem getFileSystem(){
        if(fileSystem == null){
            fileSystem = new FileSystem();
        }
        return fileSystem;
    }

    public static void initialize(String path) throws IOException, ClassNotFoundException {
        File file = new File(path);
        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        fileSystem = (FileSystem) objectIn.readObject();
        objectIn.close();
        fileIn.close();
    }

    public void setSize(int size){
        systemBlocks = new boolean[size];
        diskSize = size;
    }
}
