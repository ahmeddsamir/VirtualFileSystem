import java.io.Serializable;
import java.util.ArrayList;

public class Directory implements Serializable {
    private String directoryPath;
    private ArrayList<VirtualFile>  files = new ArrayList<VirtualFile>();
    private ArrayList<Directory>  subDirectories = new ArrayList<Directory>();

    private boolean deleted = false;

    public Directory(String path){
        directoryPath = path;
    }

    public void addFile(VirtualFile file){
        files.add(file);
    }

    public void addSubDirectory(Directory subDirectory){
        subDirectories.add(subDirectory);
    }

    public ArrayList<Directory> getSubDirectories(){
        return subDirectories;
    }

    public ArrayList<VirtualFile> getFiles(){
        return files;
    }

    public String getDirectoryPath(){
        return directoryPath;
    }

    public void printDirectoryStructure(int level) {
        if(level == 0){
            System.out.println("<root>");
        }
        for (VirtualFile file: files) {
            System.out.println(file.getFilePath().substring(file.getFilePath().lastIndexOf("/") + 1));
        }
        for (Directory directory: subDirectories) {
            System.out.println(directory.getDirectoryPath().substring(directory.getDirectoryPath().lastIndexOf("/") + 1));
            directory.printDirectoryStructure(++level);
        }
    }
}

