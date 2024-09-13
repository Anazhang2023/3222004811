import org.junit.Test;

/**
 * @Author : ZhangYiXin
 * @create 2024/9/13 21:41
 */
public class MainTest {
        @Test
        public void origAndAllTest(){
            String[] str = new String[6];
            str[0] = TxtIOUtils.readTxt("D:/app/test/orig.txt");
            str[1] = TxtIOUtils.readTxt("D:/app/test/orig_0.8_add.txt");
            str[2] = TxtIOUtils.readTxt("D:/app/test/orig_0.8_del.txt");
            str[3] = TxtIOUtils.readTxt("D:/app/test/orig_0.8_dis_1.txt");
            str[4] = TxtIOUtils.readTxt("D:/app/test/orig_0.8_dis_10.txt");
            str[5] = TxtIOUtils.readTxt("D:/app/test/orig_0.8_dis_15.txt");
            String ansFileName = "D:/app/test/ansAll.txt";
            for(int i = 0; i <= 5; i++){
                double ans = HammingUtils.hammingDistance(SimHashUtils.get(str[0]), SimHashUtils.get(str[i]));
                TxtIOUtils.writeTxt(ans, ansFileName);
            }
        }
}
