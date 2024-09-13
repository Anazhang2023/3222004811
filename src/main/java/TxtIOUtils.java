/**
 * @Author : ZhangYiXin
 * @create 2024/9/13 21:48
 */
import java.io.*;

public class TxtIOUtils {

    public static String readTxt(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line);
                contentBuilder.append("\n"); // 添加换行符，保留文件中的换行
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 在实际应用中，应该处理这个异常，而不是直接打印堆栈信息
            return null;
        }
        return contentBuilder.toString();
    }

        // writeTxt 方法接受两个参数，一个是需要写入的字符串内容，另一个是文件名
        public static void writeTxt(double content, String fileName) {
            // 使用 try-with-resources 语句确保文件资源被自动关闭
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true))) {
                // 将内容写入文件
                writer.write(String.valueOf(content));
                writer.newLine(); // 写入换行符，确保每个值在文件中独占一行
            } catch (IOException e) {
                // 异常处理，打印异常信息
                e.printStackTrace();
            }
        }
}
