import org.junit.Test;

/**
 * @Author : ZhangYiXin
 * @create 2024/9/13 22:49
 */

public class TxtIOUtilsTest {
    @Test
    public void readTxtTest() {
        // 路径存在，正常读取
        String str = TxtIOUtils.readTxt("D:/app/test/orig.txt");
        String[] strings = str.split(" ");
        for (String string : strings) {
            System.out.println(string);
        }
    }
    @Test
    public void writeTxtTest() {
        // 路径存在，正常写入
        double[] elem = {0.11, 0.22, 0.33, 0.44, 0.55};
        for (int i = 0; i < elem.length; i++) {
            TxtIOUtils.writeTxt(elem[i], "D:/app/test/ans.txt");
        }
    }
    @Test
    public void readTxtFailTest() {
        // 路径不存在，读取失败
        String str = TxtIOUtils.readTxt("D:/app/test/none.txt");
    }
    @Test
    public void writeTxtFailTest() {
        // 路径错误，写入失败
        double[] elem = {0.11, 0.22, 0.33, 0.44, 0.55};
        for (int i = 0; i < elem.length; i++) {
            TxtIOUtils.writeTxt(elem[i], "User:/app/test/ans.txt");
        }
    }
}
