import java.io.*;
import java.nio.file.Path;

public class FileSystem implements Serializable {
    private SpaceManager spaceManager = new SpaceManager();
    private static FileSystem fileSystem; //singleton
    private boolean[] systemBlocks;
    private int diskSize;
    private Directory root = new Directory("root");

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

    public Directory getRootDirectory(){
        return root;
    }

    public SpaceManager getSpaceManager(){
        return spaceManager;
    }

    public void createFile(String parentDirectory, int size, String filePath){
        VirtualFile newFile = new VirtualFile(size, filePath);
        spaceManager.allocate(newFile);
        searchDirectory(root, parentDirectory).addFile(newFile);
    }

    //To Be Deleted
    public void displaySystemBlocks(){
        for(int i = 0; i < diskSize; i++){
            System.out.println(systemBlocks[i]);
        }
    }

    public void deleteFile(String path){
        spaceManager.deallocate(searchFile(root, path));
    }

    boolean[] getSystemBlocks(){
        return systemBlocks;
    }

    public void setTrue(int index){
        systemBlocks[index] = true;
    }

    public void setFalse(int index){
        systemBlocks[index] = false;
    }

    public void setSize(int size){
        systemBlocks = new boolean[size];
        diskSize = size;
    }

    public Directory searchDirectory(Directory root, String path){
        if(root.getDirectoryPath().equals(path)){
            return root;
        }
        for (Directory directory : root.getSubDirectories()) {
            searchDirectory(directory, path);
        }
        return null;
    }

    public VirtualFile searchFile(Directory root, String path){
        for (VirtualFile file : root.getFiles()) {
            if(file.getFilePath().equals(path)){
                return file;
            }
        }
        for (Directory directory : root.getSubDirectories()) {
            searchDirectory(directory, path);
        }
        return null;
    }
}
