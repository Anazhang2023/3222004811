/**
 * @Author : ZhangYiXin
 * @create 2024/9/13 20:26
 */
public class HammingUtils {
    /**
     * 计算两个字符串的汉明距离
     *
     * @param a
     * @param b
     * @return
     */
    public static int hammingDistance(String a, String b) {
        if (a == null || b == null) {
            return -1;
        }
        if (a.length() != b.length()) {
            return -1;
        }
        int disCount = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                disCount++;
            }
        }
        return disCount;
    }
}
