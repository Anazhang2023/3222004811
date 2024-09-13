/**
 * @Author : ZhangYiXin
 * @create 2024/9/13 20:26
 */

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.List;

public class SimHashUtils {
    public static final BigInteger BIGINT_0 = BigInteger.valueOf(0);
    public static final BigInteger BIGINT_1 = BigInteger.valueOf(1);
    public static final BigInteger BIGINT_2 = BigInteger.valueOf(2);
    public static final BigInteger BIGINT_1000003 = BigInteger.valueOf(1000003);
    public static final BigInteger BIGINT_2E64M1 = BIGINT_2.pow(64).subtract(BIGINT_1);

    /**
     * 计算一段正文的simHash
     * 警告：修改该方法，修改HanLp分词结果（如新增停用词），会导致计算出的SimHash发生变化。
     *
     * @param text 需要计算的文本
     * @return 返回simHash，64位的0-1字符串。如果文本过短则返回null。
     */
    public static String get(String text) {
        if (text == null) {
            return null;
        }
        text = preprocessData(text);
        int sumWeight=0;
        int maxWeight = 0;
        int[] bits = new int[64];
        List<Term> termList = HanLP.segment(text);
        for (Term term : termList) {
            String word = term.word;
            String nature = term.nature.toString();
            // 去除标点符号和停用词,这里可以使用自定义停用词表
            if (nature.startsWith("w") || CoreStopWordDictionary.contains(word)) {
                continue;
            }
            BigInteger wordHash = getWordHash(word);
            int wordWeight = getWordWeight(word);
            if (wordWeight == 0) {
                continue;
            }
            sumWeight += wordWeight;
            if (maxWeight < wordWeight) {
                maxWeight = wordWeight;
            }
            // 逐位将计算好的词哈希乘以权重，记录到保存用的数组上。
            // 如果该位哈希为1，则加上对应的权重，反之减去对应的权重。
            for (int i = 0; i < 64; i++) {
                BigInteger bitMask = BIGINT_1.shiftLeft(63 - i);
                if (wordHash.and(bitMask).signum() != 0) {
                    bits[i] += wordWeight;
                } else {
                    bits[i] -= wordWeight;
                }
            }
        }

        // 将保存的位统计结果降维，处理成0/1字符串并返回
        StringBuilder simHashBuilder = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            if (bits[i] > 0) {
                simHashBuilder.append("1");
            } else {
                simHashBuilder.append("0");
            }
        }
        return simHashBuilder.toString();
    }

    /**
     * 预处理数据
     *
     * @param textContent
     * @return
     */
    public static String preprocessData(String textContent) {
        if (StringUtils.isBlank(textContent)) {
            return textContent;
        }
        //全角转半角
        textContent = ToDBC(textContent);
        //繁体转换简体
        textContent = HanLP.convertToSimplifiedChinese(textContent);
        //去除各类标签和特殊字符
        textContent = removeTag(textContent);
        return textContent;
    }

    /**
     * 全角转半角字符
     *
     * @param input
     * @return
     */

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                //全角空格为12288，半角空格为32
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
            //其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
            {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }


    /**
     * 去除标签 和特殊字符
     *
     * @param text
     * @return
     */
    public static String removeTag(String text) {
        if (null == text || text.isEmpty()) {
            return "";
        }
        text = text.replaceAll("[`|+|•|/|<|>|《|》|_|＂|·|。|“|”|「|\"|」|:|：|.|。|，|．|；|\\-|？|！|,|;|?|!|\t|\\[|\\]|(|)|{|}|【|】|（|）|｜|\\|、|]", "");
        text = text.replaceAll("[#|…]", "").replaceAll("&quot &gt", "")
                .replaceAll("\\s+", " ")
                .replaceAll("[^\u4E00-\u9FA5]", "");//去除emoji图像;
        text = text.replaceAll("\\s+", "").replaceAll(" ", "").replaceAll("　", "");
        text = text.replaceAll("\\s+", "").replaceAll(" +", "").replaceAll("\\u2003", "")
                .replaceAll(" ", "").replaceAll("[\\s*|\t|\r|\n|\r\n|]", "").replaceAll("&nbsp;", "").replaceAll("nbsp", "");
        text = text.replaceAll("[\u007f-\u009f]|\u00ad|[\u0483-\u0489]|[\u0559-\u055a]|\u058a|"
                + "[\u0591-\u05bd]|\u05bf|[\u05c1-\u05c2]|[\u05c4-\u05c7]|[\u0606-\u060a]|[\u063b-\u063f]|\u0674|"
                + "[\u06e5-\u06e6]|\u070f|[\u076e-\u077f]|\u0a51|\u0a75|\u0b44|[\u0b62-\u0b63]|[\u0c62-\u0c63]|"
                + "[\u0ce2-\u0ce3]|[\u0d62-\u0d63]|\u135f|[\u200b-\u200f]|[\u2028-\u202e]|\u2044|\u2071|[\uf701-\uf70e]|"
                + "[\uf710-\uf71a]|\ufb1e|[\ufc5e-\ufc62]|\ufeff|\ufffc", "");
        text = text.replace("０", "").replace("１", "")
                .replace("２", "").replace("３", "").replace("４", "")
                .replace("５", "").replace("６", "").replace("７", "")
                .replace("８", "").replace("９", "").toLowerCase().trim();
        text = text.replace("0", "").replace("1", "")
                .replace("2", "").replace("3", "").replace("4", "")
                .replace("5", "").replace("6", "").replace("7", "")
                .replace("8", "").replace("9", "").toLowerCase().trim();
        return text;
    }

    /**
     * 获取一个单词的哈希值
     * 警告：修改该方法会导致计算出的SimHash发生变化。
     *
     * @param word 输入的单词
     * @return 返回哈希
     */
    private static BigInteger getWordHash(String word) {
        if (StringUtils.isBlank(word)) {
            return BIGINT_0;
        }
        char[] sourceArray = word.toCharArray();
        BigInteger hash = BigInteger.valueOf(((long) sourceArray[0]) << 12);
        for (char ch : sourceArray) {
            BigInteger chInt = BigInteger.valueOf(ch);
            hash = hash.multiply(BIGINT_1000003).xor(chInt).and(BIGINT_2E64M1);
        }
        hash = hash.xor(BigInteger.valueOf(word.length()));
        return hash;
    }

    /**
     * 获取一个单词的权重。
     * 警告：修改该方法会导致计算出的SimHash发生变化。
     *
     * @param word 输入单词
     * @return 输出权重
     */
    private static int getWordWeight(String word) {
        if (StringUtils.isBlank(word)) {
            return 0;
        }
        int length = word.length();
        if (length == 1) {
            // 只有长度为1的词，哈希后位数不够（40位左右），所以权重必须很低，否则容易导致高位哈希全部为0。
            return 1;
        } else if (word.charAt(0) >= 0x3040) {
            if (length == 2) {
                return 8;
            } else {
                return 16;
            }
        } else {
            if (length == 2) {
                return 2;
            } else {
                return 4;
            }
        }
    }
}
