package sina1;

import java.util.ArrayList;
import java.util.List;

public class IpUtil {
    public static void main(String[] args) throws Exception {
        System.out.println(ipToInt("192.168.0.1"));
        System.out.println(ipToStr(16820416));
    }

    public static int ipToInt(String ip) throws Exception {
        String[] arr = splitIp(ip);
        return ((toInt(arr[3]) & 0xFF) << 24) +
                ((toInt(arr[2]) & 0xFF) << 16) +
                ((toInt(arr[1]) & 0xFF) << 8) +
                (toInt(arr[0]) & 0xFF);
    }

    public static String[] splitIp(String ip) throws Exception {
        List<String> list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        for (char c : (ip + ".").toCharArray()) {
            if (c == '.') {
                list.add(sb.toString());
                sb.delete(0, sb.length());
            } else if (c >= '0' && c <= '9') {
                sb.append(c);
            } else {
                throw new Exception("char c " + c + " is not ip char");
            }
        }
        if (list.size() != 4)
            throw new Exception(ip + " does not match ip format");
        return list.toArray(new String[list.size()]);
    }

    public static int toInt(String str) throws Exception {
        int result = 0;
        for (char c : str.toCharArray()) {
            result *= 10;
            if (c >= '0' && c <= '9') {
                result += c - '0';
            } else {
                throw new Exception("char c " + c + " is not number");
            }
        }
        return result;
    }

    public static String ipToStr(int ip) {
        return (ip & 0xFF) + "." + (ip >> 8 & 0xFF) + "." + (ip >> 16 & 0xFF) + "." + (ip >> 24 & 0xFF);
    }
}