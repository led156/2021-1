package hw2;

//import cse2010.homework2.DLinkedList;

/* Block will be used as a type argument */
class Block {
    private int size;    // size of a block
    private int start;   // start address of a block
    private int end;     // end address of a block

    public Block(int size, int start, int end) {
        this.size = size;
        this.start = start;
        this.end = end;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "(" + size +", " + start + ", " + end + ")";
    }
}

public class MemoryManager {

    private DLinkedList<Block> heap = new DLinkedList<>();

    /**
     * Create a memory manager with capacity given as parameter.
     * @param capacity
     */
    public MemoryManager(int capacity) {
       /**/
    	heap.addFirst(new Block(capacity,0,capacity-1));
    }

    /**
     * Returns a block information allocated for the requested size.
     * @param size  the size of requested memtory
     * @return  a block of the requested size if satisfieed, throws `OutOfMemoryException` exception otherwise.
     */
    public Block malloc(int size) {
        /**/
    	for(Node<Block> node = heap.getFirst(); node.getItem() != null ; node = node.getNext()) {
    		if(size<=node.getItem().getSize()) {
    			int Nsize = node.getItem().getSize();
    			int Nstart = node.getItem().getStart();
    			int Nend = node.getItem().getEnd();
    			
    			if(Nsize != size) {
    				heap.addAfter(node, new Node<Block>(new Block(Nsize-size, Nstart+size, Nend), null, null));
    			}
    			heap.remove(node);
    			return new Block(size, node.getItem().getStart(), node.getItem().getStart() + size - 1);
        	}
    	}

    	throw new OutOfMemoryException("");
    }

    /**
     * Add a freed block to the free memory block list.
     * @param block size  the size of requested memtory
     */
    public void free(Block block) {
        /**/

        //
        if (heap.getFirst() == null) {
            heap.addFirst(block);
        }

        //
        else {
            Node<Block> node = heap.getFirst();


            while (node.getItem() != null && block.getStart() > node.getItem().getStart()) {
                node = node.getNext();
            }

            heap.addBefore(node, new Node<>(block, null, null));

            node = node.getPrev();

            int nodeStart = node.getItem().getStart();
            int nodeEnd = node.getItem().getEnd();
            int nodeSize = node.getItem().getSize();

            Node<Block> nodeP = node.getPrev();
            Node<Block> nodeN = node.getNext();


            if(nodeP.getItem()!=null && nodeN.getItem()!=null){
                Block temp = node.getItem();

                if(nodeStart-1 == nodeP.getItem().getEnd()){
                    temp.setStart(nodeP.getItem().getStart());
                    temp.setSize(nodeSize + nodeP.getItem().getSize());
                    heap.remove(nodeP);
                }

                if(nodeEnd+1 == nodeN.getItem().getStart()){
                    temp.setEnd(nodeN.getItem().getEnd());
                    temp.setSize(temp.getSize() + nodeN.getItem().getSize());
                    heap.remove(nodeN);
                }

                node.setItem(temp);
            }

            else if(nodeP.getItem()!=null){
                if(nodeStart-1 == nodeP.getItem().getEnd()){
                    node.setItem(new Block(nodeSize + nodeP.getItem().getSize(), nodeP.getItem().getStart(), nodeEnd));
                    heap.remove(nodeP);
                }
            }

            else if(nodeN.getItem()!=null){
                if(nodeEnd+1 == nodeN.getItem().getStart()){
                    node.setItem(new Block(nodeSize + nodeN.getItem().getSize(), nodeStart, nodeN.getItem().getEnd()));
                    heap.remove(nodeN);
                }
            }
        }
    }







    /**
     * Returns the capacity of the free storage.
     * @return  capacity of the free storage
     */
    public int getCapacity() {
        /**/
    	int capacity = 0;
    	for(Node<Block> node = heap.getFirst(); node.getItem() != null; node = node.getNext()) {
    		capacity += node.getItem().getSize();
    	}
        return capacity;
    }

    // for testing purpose only
    DLinkedList<Block> getHeap() {
        return heap;
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}


