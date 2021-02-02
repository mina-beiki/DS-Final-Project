import java.util.* ;
public class MinHeap {
    private double[] Heap;
    private int index;
    private int size;

    public MinHeap(int size) {
        this.size = size;
        this.index = 0;
        Heap = new double[size];
    }

    public double[] getHeap() {
        return Heap;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return (i * 2) + 1;
    }

    private int rightChild(int i) {
        return (i * 2) + 2;
    }

    private boolean isLeaf(int i) {
        if (rightChild(i) >= size || leftChild(i) >= size) {
            return true;
        }
        return false;
    }

    public void insert(double element) {
        if (index >= size) {
            return;
        }
        Heap[index] = element;
        int current = index;

        while (Heap[current] < Heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
        index++;
    }

    // removes and returns the minimum element from the heap
    public double remove() {
        // since its a min heap, so root = minimum
        double popped = Heap[0];
        if(index>0) {
            Heap[0] = Heap[--index];
            minHeapify(0);
        }
        return popped;
    }

    // heapify the node at i
    private void minHeapify(int i) {
        // If the node is a non-leaf node and any of its child is smaller
        if (!isLeaf(i)) {
            if (Heap[i] > Heap[leftChild(i)] ||
                    Heap[i] > Heap[rightChild(i)]) {
                if (Heap[leftChild(i)] < Heap[rightChild(i)]) {
                    swap(i, leftChild(i));
                    minHeapify(leftChild(i));
                } else {
                    swap(i, rightChild(i));
                    minHeapify(rightChild(i));
                }
            }
        }
    }

    public int getNodeIndex(double dist){
        for(int i=0 ; i<size ; i++){
            if(Heap[i]==dist){
                return i ;
            }
        }
        return 0 ;
    }

    public void deleteNode(int i){
        //delete the node from array and put the rightest node in place of it :
        double rightest = Heap[index];
        Heap[i] = rightest ;
        //min heapify :
        minHeap();
    }

    // builds the min-heap using the minHeapify
    public void minHeap() {
        for (int i = ((index - 1 )/ 2); i >= 1; i--) { //not sure ?
            minHeapify(i);
        }
    }

    // Function to print the contents of the heap
    public void printHeap() {
        for (int i = 0; i < (index / 2); i++) {
            System.out.print("Parent : " + Heap[i]);
            if (leftChild(i) < index)
                System.out.print(" Left : " + Heap[leftChild(i)]);
            if (rightChild(i) < index)
                System.out.print(" Right :" + Heap[rightChild(i)]);
            System.out.println();
        }
    }

    // swaps two nodes of the heap
    private void swap(int x, int y) {
        double tmp;
        tmp = Heap[x];
        Heap[x] = Heap[y];
        Heap[y] = tmp;
    }

    public double getRoot (){
        return Heap[0];
    }
}