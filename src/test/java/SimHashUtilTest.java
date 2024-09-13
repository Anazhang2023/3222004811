import org.junit.Test;

/**
 * @Author : ZhangYiXin
 * @create 2024/9/13 22:41
 */
public class SimHashUtilTest {
    @Test
    public void getHashTest(){
        String[] strings = {"余华", "是", "一位", "真正", "的", "作家"};
        for (String string : strings) {
            String stringHash = SimHashUtils.get(string);
            System.out.println(stringHash.length());
            System.out.println(stringHash);
        }
    }
    @Test
    public void getSimHashTest(){
        String str0 = TxtIOUtils.readTxt("D:/app/test/orig.txt");
        String str1 = TxtIOUtils.readTxt("D:/app/test/orig_0.8_add.txt");
        System.out.println(SimHashUtils.get(str0));
        System.out.println(SimHashUtils.get(str1));
    }
}
