import java.io.Serializable;

public interface Allocator extends Serializable {
    void allocate(VirtualFile file);
    void deallocate(VirtualFile file);
}
