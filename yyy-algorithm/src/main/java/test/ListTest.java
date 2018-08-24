package test;

class Node{
    int value;
    Node next;
}
public class ListTest {

    public static Node reverseWithRecursion(Node head){
        if(head == null || head.next == null){
            return head;
        } else{
            Node next = head.next;
            Node newHead = reverseWithRecursion(next);
            next.next = head;
            head.next = null;
            return newHead;
        }
    }

    public static Node reverseWithoutRecursion(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node pre = head, cur = head.next, next;

        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        head.next = null;
        return pre;
    }

    public static void main(String[] args) {

    }
}
