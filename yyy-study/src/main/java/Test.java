import java.util.Stack;
/*
非递归法：后序遍历
1.进栈时候，把值同时压入路径的向量数组，修正路径的和
2.出栈时候，先判断和是否相等，且该节点是否是叶节点，
判断完成后保持和栈一致，抛出路径，修改路径的和
3.向量数组和栈的操作要保持一致
https://www.nowcoder.com/questionTerminal/b736e784e3e34731af99065031301bca
*/
public class Test {
    class Node {
        int val;
        Node left, right;
    }
    public void aaa(Node root, int sum) {
        Stack<Node> stack = new Stack<>();
        Node cur;
        stack.push(root);
        int curSum = 0;
        while (cur != null || !stack.isEmpty()) {
            cur = stack.peek();
            while(cur != null) {
                stack.push(cur);
                curSum += cur.val;
                cur = cur.left != null ? cur.left : cur.right;
            }

            cur = stack.peek();
            if (curSum == sum && cur.left == null && cur.right == null)            {
            }
            stack.pop();
            curSum -=cur.val;
            Node parent = stack.peek();
            if(!stack.isEmpty() && parent.left == cur) {
                cur = stack.peek().right;
            } else {
                cur = null;
            }
        }
    }
}

