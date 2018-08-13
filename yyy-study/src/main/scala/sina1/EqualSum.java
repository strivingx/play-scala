package sina1;

public class EqualSum {
    public static void main(String[] args) {
        System.out.println(find(new int[] {1, 2, 3, 9}, 8));
        System.out.println(find(new int[] {1, 3, 4, 5}, 8));
    }

    private static boolean find(int[] data, int sum) {
        int left = 0, right = data.length - 1, temp;
        while (left <= right) {
            temp = data[left] + data[right];
            if (temp > sum) {
                right--;
            } else if (temp < sum) {
                left++;
            } else {
                return true;
            }
        }
        return false;
    }

}
