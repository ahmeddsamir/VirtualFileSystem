import java.io.*;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Queue;

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

    public void createFile(String path, int size){
        try{
            VirtualFile newFile = new VirtualFile(size, path);
            searchDirectory(root, path.substring(0, path.lastIndexOf("/"))).addFile(newFile);
            spaceManager.allocate(newFile);
        }
        catch(Exception e){
            System.out.println("Path <" + path.substring(0, path.lastIndexOf("/")) + "> doesn't exist");
        }
    }

    public void createDirectory(String path){
        Directory newDirectory = new Directory(path);
        try{
            searchDirectory(root, path.substring(0, path.lastIndexOf("/"))).addSubDirectory(newDirectory);
        }
        catch(Exception e){
            System.out.println("Path <" + path.substring(0, path.lastIndexOf("/")) + "> doesn't exist");
        }
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

    public Directory searchDirectory(Directory currentDirectory, String path){
        Queue<Directory> directories = new LinkedList<Directory>();
        directories.add(currentDirectory);
        while(!directories.isEmpty()){
            currentDirectory = directories.poll();
            if(currentDirectory.getDirectoryPath().equals(path)){
                return currentDirectory;
            }
            for (Directory directory : currentDirectory.getSubDirectories()){
                directories.add(directory);
            }
        }
        return null;
    }

    /*public VirtualFile searchFile(Directory currentDirectory, String path){
        for (VirtualFile file : currentDirectory.getFiles()) {
            if(file.getFilePath().equals(path)){
                return file;
            }
        }
        for (Directory directory : currentDirectory.getSubDirectories()) {
            searchDirectory(directory, path);
        }
        return null;
    }*/

    public VirtualFile searchFile(Directory currentDirectory, String path){
        Queue<Directory> directories = new LinkedList<Directory>();
        directories.add(currentDirectory);
        while(!directories.isEmpty()){
            currentDirectory = directories.poll();
            for (VirtualFile file : currentDirectory.getFiles()){
                if(file.getFilePath().equals(path)){
                    return file;
                }
            }
            for (Directory directory : currentDirectory.getSubDirectories()){
                directories.add(directory);
            }
        }
        return null;
    }
}
