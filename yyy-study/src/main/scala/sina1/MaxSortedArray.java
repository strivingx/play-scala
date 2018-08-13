package sina1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MaxSortedArray {
    public static void main(String[] args) {
        long[][] arr = new long[][] {
                {1, 2, 3},
                {7, 8, 9},
                {4, 5, 6}
        };
        for (long l : new MaxSortedArray().topn(arr, 5))
            System.out.print(l + " ");
        System.out.println();
    }

    class Node {
        long value;
        int rowIndex, colIndex;

        public Node(long value, int rowIndex, int colIndex) {
            this.value = value;
            this.rowIndex = rowIndex;
            this.colIndex = colIndex;
        }

        @Override
        public String toString() {
            return value + " " + rowIndex + " " + colIndex;
        }
    }

    class Heap {
        private Node[] nodes;
        private int size = 0;
        private Comparator<Node> comparator = (o1, o2) -> o2.value == o1.value ? 0 : (o1.value > o2.value ? 1 : -1);

        public void setComparator(Comparator<Node> comparator) {
            this.comparator = comparator;
        }

        public Heap(long[] data, int colIndex) {
            makeHeap(data, colIndex);
        }

        private void makeHeap(long[] data, int colIndex) {
            nodes = new Node[data.length];
            for (int i = 0; i < data.length; i++) {
                push(new Node(data[i], i, colIndex));
            }
        }

        private Node pop() {  // 自顶向下更新
            if (size == 0) return null;
            swap(0, --size);
            int selectedChild;
            // 左右孩子较大的与父节点交换
            int current = 0;
            while (current < size) {
                selectedChild = selectChild(current, size - 1);
                if (selectedChild == -1 || comparator.compare(nodes[current], nodes[selectedChild]) >= 0) {
                    break;
                } else {
                    swap(current, selectedChild);
                    current = selectedChild;
                }
            }
            return nodes[size];
        }

        private void push(Node node) { // 自底向上更新
            if (node == null) return;
            int current = size;
            nodes[current] = node;
            int parent = (current - 1) >> 1;
            while (parent >= 0) {
                if (comparator.compare(nodes[current], nodes[parent]) > 0) {
                    swap(current, parent);
                } else {
                    break;
                }
                current = parent;
                parent = (current - 1) >> 1;
            }
            size++;
        }

        private void swap(int current, int child) {
            Node temp = nodes[current];
            nodes[current] = nodes[child];
            nodes[child] = temp;
        }

        public int selectChild(int current, int endIndex) {
            int left = current * 2 + 1, right = current * 2 + 2;
            if (left > endIndex) return -1;
            if (right > endIndex) return left;
            return comparator.compare(nodes[left], nodes[right]) > 0 ? left : right;
        }
    }

    Long[] topn(long[][] array, int k) {
        if(array.length == 0 || array[0].length == 0)
            return new Long[0];
        long[] data = new long[array.length];
        int rowIndex = 0, colIndex;
        for (int i = 0; i < array.length; i++) {
            data[i] = array[i][array[i].length - 1];
            if (data[rowIndex] < data[i]) {
                rowIndex = i;
            }
        }
        Heap heap = new Heap(data, array[0].length - 1);
        List<Long> result = new ArrayList();
        for (int i = 0; i < k; i++) {
            Node max = heap.pop();
            result.add(max.value);
            if (max.colIndex > 0) {
                rowIndex = max.rowIndex;
                colIndex = max.colIndex - 1;
                heap.push(new Node(array[rowIndex][colIndex], rowIndex, colIndex));
            }
        }
        return result.toArray(new Long[result.size()]);
    }

}
