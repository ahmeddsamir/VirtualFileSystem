import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class SpaceManager implements Serializable {
    private Allocator allocator;

    public void setAllocationMethod(Allocator allocator){
        this.allocator = allocator;
    }

    public void allocate(VirtualFile file){
        allocator.allocate(file);
    }

    public void deallocate(VirtualFile file){
        file.deleteFile();
        FileSystem.getFileSystem().decrementAllocatedSpace(file.getSize());
        for (int block : file.getAllocatedBlocks()) {
            FileSystem.getFileSystem().setFalse(block);
        }
    }

    public void deleteDirectory(Directory directory){
        Queue<Directory> directories = new LinkedList<Directory>();
        directories.add(directory);
        while(!directories.isEmpty()){
            directory = directories.poll();
            directory.deleteDirectory();
            for (VirtualFile file : directory.getFiles()) {
                deallocate(file);
            }
            for (Directory toBeDeleted : directory.getSubDirectories()) {
                directories.add(toBeDeleted);
            }
        }
    }
}
