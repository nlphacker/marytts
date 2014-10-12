package marytts.language.cn;

import net.sourceforge.pinyin4j.PinyinHelper;  
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;  
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;  
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;  
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;  
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;  
  
public class ChinesePinyinConverter {  
	
    public static String getPinYin(String inputString) {  
          
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();  
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);  
        format.setVCharType(HanyuPinyinVCharType.WITH_V);  
  
        StringBuffer output = new StringBuffer("");  
  
        try {          	
        	String pinyin = PinyinHelper.toHanyuPinyinString(inputString, format, "");
        	if (pinyin != null)
        	{
        		output.append(pinyin);
        	}
        } catch (BadHanyuPinyinOutputFormatCombination e) {  
            e.printStackTrace();  
        }  
        return output.toString();  
    }  
      
    public static void main(String[] args) {  
        String chs = "我是中国人! I'm Chinese!";  
        System.out.println(chs);  
        System.out.println(getPinYin(chs));  
    }        
}  