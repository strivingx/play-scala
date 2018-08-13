package algorithm;

import java.util.Arrays;

public class KMP {
    public static void main(String[] args) {
        Arrays.stream(getNext("abcabcabcdaacbc".toCharArray())).forEach(i -> System.out.println(i));
        System.out.println(kmp("abcabcabcdaabd".toCharArray(), "abd".toCharArray()));
    }

    public static int[] getNext(char[] chars) {
        int[] next = new int[chars.length];
        next[0] = -1;
        int k = -1;
        for (int i = 1; i < chars.length; i++) {
            while (k > -1 && chars[k + 1] != chars[i])
                k = next[k];
            if (chars[k + 1] == chars[i]) {
                k++;
            }
            next[i] = k;
        }
        return next;
    }

    public static int kmp(char[] str, char[] sub) {
        int[] next = getNext(sub);//计算next数组
        int k = -1;
        for (int i = 0; i < str.length; i++) {
            while (k > -1 && sub[k + 1] != str[i])//ptr和str不匹配，且k>-1（表示ptr和str有部分匹配）
                k = next[k];//往前回溯
            if (sub[k + 1] == str[i]) {
                k++;
            }
            if (k == sub.length - 1) {//说明k移动到ptr的最末端
                return i - sub.length + 1;//返回相应的位置
            }
        }
        return -1;
    }

}
