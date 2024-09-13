import org.junit.Test;

/**
 * @Author : ZhangYiXin
 * @create 2024/9/13 22:41
 */
public class HammingUtilsTest {
    @Test
    public void getHammingDistanceTest() {
        String str0 = TxtIOUtils.readTxt("D:/app/test/orig.txt");
        String str1 = TxtIOUtils.readTxt("D:/app/test/orig_0.8_add.txt");
        int distance = HammingUtils.hammingDistance(SimHashUtils.get(str0), SimHashUtils.get(str1));
        System.out.println("海明距离：" + distance);
        System.out.println("相似度: " + (100 - distance * 100 / 128) + "%");
    }
}
