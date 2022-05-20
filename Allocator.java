public interface Allocator {
    void allocate(VirtualFile file);
    void deallocate(VirtualFile file);
}
