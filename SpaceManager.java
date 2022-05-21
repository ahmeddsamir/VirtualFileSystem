import java.io.Serializable;

public class SpaceManager implements Serializable {
    private Allocator allocator;

    public void setAllocationMethod(Allocator allocator){
        this.allocator = allocator;
    }

    public void allocate(VirtualFile file){
        allocator.allocate(file);
    }

    public void deallocate(VirtualFile file){
        allocator.deallocate(file);
    }
}
