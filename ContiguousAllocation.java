public class ContiguousAllocation implements Allocator{
    @Override
    public void allocate(VirtualFile file) {
        boolean[] systemBlocks = FileSystem.getFileSystem().getSystemBlocks();
        int index = -1;
        int freeBlocks = 0;
        int tempSize = Integer.MAX_VALUE;

        //Get the starting index of the best fit in memory
        for (int i = 0; i < systemBlocks.length; i++){
            freeBlocks = 0;
            //If current block is full continue
            if(systemBlocks[i]){
               continue;
            }
            else{
                //If current block is empty, loop te get its size
                int j;
                for(j = i; j < systemBlocks.length; j++){
                    if(systemBlocks[j]){
                        //i = j - 1;
                        break;
                    }
                    freeBlocks++;
                }
                //If size is sufficient for the file and is smaller than tempSize, update tempSize and index
                if(freeBlocks >= file.getSize() && freeBlocks < tempSize){
                    index = i;
                    tempSize = freeBlocks;
                }
                i = j - 1;
            }
        }

        //After determining the start index, start allocating blocks
        for(int i = index; i < file.getSize() + index; i++){
            file.addBlock(i);
            FileSystem.getFileSystem().setTrue(i);
        }
    }

    @Override
    public void deallocate(VirtualFile file) {
        file.deleteFile();
        for (int block : file.getAllocatedBlocks()) {
            FileSystem.getFileSystem().setFalse(block);
        }
    }
}
