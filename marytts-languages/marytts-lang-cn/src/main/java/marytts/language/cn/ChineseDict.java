package marytts.language.cn;

import java.net.*;
import java.io.*;
import java.nio.charset.*;
import java.util.regex.*;
import java.util.*;

import org.hsqldb.persist.Log;

// this implements a class to load CE dict from resource
public class ChineseDict {	
	
	private Hashtable<String, String> dict = new Hashtable<String, String>();
	private Hashtable<String, Integer> syllableDict = new Hashtable<String, Integer>();
	private HashSet<String> initialDict;
	private HashSet<String> finalDict;
	
	private String syllables = "a1, a2, ai1, ai2, ai3, ai4, ai5, an1, an2, an3, an4, ang1, ang2, ang4, ao1, ao2, ao3, ao4, ba1, ba2, ba3, ba4, ba5, bai1, bai2, bai3, bai4, bai5, ban1, ban3, ban4, ban5, bang1, bang3, bang4, bao1, bao2, bao3, bao4, bao5, bei1, bei3, bei4, bei5, ben1, ben3, ben4, beng1, beng2, beng3, beng4, bi1, bi2, bi3, bi4, bian1, bian3, bian4, bian5, biao1, biao3, biao4, bie1, bie2, bie3, bie4, bin1, bin4, bing1, bing3, bing4, bing5, bo1, bo2, bo3, bo4, bo5, bu1, bu2, bu3, bu4, bu5, ca1, ca3, cai1, cai2, cai3, cai4, cai5, can1, can2, can3, can4, cang1, cang2, cao1, cao2, cao3, cao4, ce4, cen1, cen2, ceng1, ceng2, ceng4, ceng5, cha1, cha2, cha3, cha4, cha5, chai1, chai2, chai3, chai4, chan1, chan2, chan3, chan4, chan5, chang1, chang2, chang3, chang4, chang5, chao1, chao2, chao3, chao5, che1, che3, che4, che5, chen1, chen2, chen3, chen4, chen5, cheng1, cheng2, cheng3, cheng4, cheng5, chi1, chi2, chi3, chi4, chi5, chong1, chong2, chong3, chong4, chou1, chou2, chou3, chou4, chou5, chu1, chu2, chu3, chu4, chu5, chua1, chuai1, chuai2, chuai3, chuai4, chuan1, chuan2, chuan3, chuan4, chuang1, chuang2, chuang3, chuang4, chui1, chui2, chui4, chun1, chun2, chun3, chun5, chuo1, chuo4, ci1, ci2, ci3, ci4, cong1, cong2, cou4, cu1, cu2, cu3, cu4, cuan1, cuan2, cuan4, cui1, cui3, cui4, cun1, cun2, cun3, cun4, cun5, cuo1, cuo2, cuo3, cuo4, cuo5, da1, da2, da3, da4, da5, dai1, dai3, dai4, dan1, dan3, dan4, dan5, dang1, dang3, dang4, dang5, dao1, dao3, dao4, dao5, de1, de2, de4, de5, dei3, den4, deng1, deng3, deng4, di1, di2, di3, di4, di5, dia3, dian1, dian3, dian4, dian5, diao1, diao3, diao4, die1, die2, die5, ding1, ding3, ding4, ding5, diu1, dong1, dong3, dong4, dong5, dou1, dou3, dou4, dou5, du1, du2, du3, du4, du5, duan1, duan3, duan4, dui1, dui4, dun1, dun3, dun4, duo1, duo2, duo3, duo4, duo5, e1, e2, e3, e4, ei1, en1, en4, en5, eng1, er2, er3, er4, fa1, fa2, fa3, fa4, fa5, fan1, fan2, fan3, fan4, fan5, fang1, fang2, fang3, fang4, fang5, fei1, fei2, fei3, fei4, fen1, fen2, fen3, fen4, fen5, feng1, feng2, feng3, feng4, feng5, fiao4, fo2, fou2, fou3, fu1, fu2, fu3, fu4, fu5, ga1, ga2, ga3, ga4, ga5, gai1, gai3, gai4, gan1, gan3, gan4, gang1, gang3, gang4, gang5, gao1, gao3, gao4, gao5, ge1, ge2, ge3, ge4, ge5, gei3, gei5, gen1, gen2, gen3, gen4, geng1, geng3, geng4, ging1, gong1, gong3, gong4, gong5, gou1, gou3, gou4, gu1, gu3, gu4, gu5, gua1, gua3, gua4, gua5, guai1, guai3, guai4, guai5, guan1, guan3, guan4, guang1, guang3, guang4, guang5, gui1, gui3, gui4, gui5, gun3, gun4, guo1, guo2, guo3, guo4, guo5, ha1, ha2, ha3, ha4, ha5, hai1, hai2, hai3, hai4, hai5, han1, han2, han3, han4, han5, hang1, hang2, hang4, hao1, hao2, hao3, hao4, hao5, he1, he2, he4, he5, hei1, hen2, hen3, hen4, heng1, heng2, heng4, hong1, hong2, hong3, hong4, hou1, hou2, hou3, hou4, hou5, hu1, hu2, hu3, hu4, hu5, hua1, hua2, hua4, hua5, huai2, huai4, huan1, huan2, huan3, huan4, huan5, huang1, huang2, huang3, huang4, huang5, hui1, hui2, hui3, hui4, hun1, hun2, hun4, hun5, huo1, huo2, huo3, huo4, huo5, ji1, ji2, ji3, ji4, ji5, jia1, jia2, jia3, jia4, jia5, jian1, jian3, jian4, jian5, jiang1, jiang3, jiang4, jiang5, jiao1, jiao2, jiao3, jiao4, jiao5, jie1, jie2, jie3, jie4, jie5, jin1, jin3, jin4, jing1, jing3, jing4, jing5, jiong1, jiong3, jiu1, jiu3, jiu4, jiu5, ju1, ju2, ju3, ju4, ju5, juan1, juan3, juan4, jue1, jue2, jue3, jun1, jun4, ka1, ka3, kai1, kai3, kai4, kai5, kan1, kan3, kan4, kan5, kang1, kang2, kang4, kao1, kao3, kao4, ke1, ke2, ke3, ke4, ke5, kei1, ken3, ken4, keng1, keng5, kong1, kong3, kong4, kou1, kou3, kou4, kou5, ku1, ku3, ku4, ku5, kua1, kua3, kua4, kuai3, kuai4, kuai5, kuan1, kuan3, kuang1, kuang2, kuang3, kuang4, kui1, kui2, kui3, kui4, kun1, kun3, kun4, kuo4, la1, la2, la3, la4, la5, lai2, lai4, lai5, lan2, lan3, lan4, lan5, lang1, lang2, lang3, lang4, lang5, lao1, lao2, lao3, lao4, lao5, le1, le4, le5, lei1, lei2, lei3, lei4, lei5, leng1, leng2, leng3, leng4, leng5, li1, li2, li3, li4, li5, lia3, lian2, lian3, lian4, lian5, liang2, liang3, liang4, liang5, liao1, liao2, liao3, liao4, liao5, lie1, lie3, lie4, lie5, lin1, lin2, lin3, lin4, ling1, ling2, ling3, ling4, ling5, liu1, liu2, liu3, liu4, liu5, long1, long2, long3, long4, long5, lou1, lou2, lou3, lou4, lou5, lu1, lu2, lu3, lu4, lu5, lu:2, lu:3, lu:4, lu:e4, luan2, luan3, luan4, lun1, lun2, lun4, luo1, luo2, luo3, luo4, luo5, ma1, ma2, ma3, ma4, ma5, mai2, mai3, mai4, man1, man2, man3, man4, man5, mang1, mang2, mang3, mang5, mao1, mao2, mao3, mao4, mao5, me5, mei2, mei3, mei4, mei5, men1, men2, men4, men5, meng1, meng2, meng3, meng4, mi1, mi2, mi3, mi4, mi5, mian2, mian3, mian4, mian5, miao1, miao2, miao3, miao4, mie1, mie4, mie5, min2, min3, ming2, ming3, ming4, ming5, miu4, mo1, mo2, mo3, mo4, mo5, mou1, mou2, mou3, mou4, mu2, mu3, mu4, mu5, na1, na2, na3, na4, na5, nai3, nai4, nai5, nan1, nan2, nan3, nan4, nan5, nang1, nang2, nang3, nang4, nang5, nao1, nao2, nao3, nao4, nao5, ne2, ne4, ne5, nei3, nei4, nen4, nen5, neng2, ni1, ni2, ni3, ni4, nian1, nian2, nian3, nian4, niang2, niang4, niang5, niao3, niao4, nie1, nie2, nie4, nie5, nin2, ning2, ning3, ning4, niu1, niu2, niu3, niu4, niu5, nong2, nong4, nong5, nou4, nu2, nu3, nu4, nu:3, nu:4, nu:e4, nuan3, nun2, nuo2, nuo3, nuo4, o1, ou1, ou3, ou4, pa1, pa2, pa4, pa5, pai1, pai2, pai3, pai4, pai5, pan1, pan2, pan4, pang1, pang2, pang3, pang4, pao1, pao2, pao3, pao4, pao5, pei1, pei2, pei4, pen1, pen2, pen5, peng1, peng2, peng3, peng4, peng5, pi1, pi2, pi3, pi4, pi5, pian1, pian2, pian3, pian4, piao1, piao2, piao3, piao4, pie1, pie3, pin1, pin2, pin3, pin4, ping1, ping2, po1, po2, po3, po4, po5, pou1, pou2, pou3, pu1, pu2, pu3, pu4, qi1, qi2, qi3, qi4, qi5, qia1, qia3, qia4, qian1, qian2, qian3, qian4, qian5, qiang1, qiang2, qiang3, qiang4, qiao1, qiao2, qiao3, qiao4, qiao5, qie1, qie2, qie3, qie4, qie5, qin1, qin2, qin3, qin4, qin5, qing1, qing2, qing3, qing4, qing5, qiong1, qiong2, qiu1, qiu2, qiu3, qiu5, qu1, qu2, qu3, qu4, qu5, quan1, quan2, quan3, quan4, quan5, que1, que2, que4, que5, qun1, qun2, ran2, ran3, rang1, rang2, rang3, rang4, rang5, rao2, rao3, rao4, re3, re4, ren2, ren3, ren4, ren5, reng1, reng2, reng4, ri4, rong1, rong2, rong3, rou2, rou4, ru2, ru3, ru4, ruan2, ruan3, rui2, rui3, rui4, run2, run4, ruo2, ruo4, sa1, sa3, sa4, sa5, sai1, sai4, san1, san3, san4, san5, sang1, sang3, sang4, sang5, sao1, sao3, sao4, sao5, se4, se5, sen1, seng1, sha1, sha2, sha3, sha4, shai1, shai3, shai4, shan1, shan3, shan4, shan5, shang1, shang3, shang4, shang5, shao1, shao2, shao3, shao4, shao5, she1, she2, she3, she4, she5, shei2, shen1, shen2, shen3, shen4, shen5, sheng1, sheng2, sheng3, sheng4, sheng5, shi1, shi2, shi3, shi4, shi5, shou1, shou2, shou3, shou4, shou5, shu1, shu2, shu3, shu4, shu5, shua1, shua3, shua4, shuai1, shuai3, shuai4, shuan1, shuan4, shuang1, shuang3, shui3, shui4, shui5, shun3, shun4, shuo1, shuo4, shuo5, si1, si3, si4, si5, song1, song2, song3, song4, song5, sou1, sou3, sou4, sou5, su1, su2, su4, su5, suan1, suan3, suan4, sui1, sui2, sui3, sui4, sui5, sun1, sun3, suo1, suo3, suo5, ta1, ta3, ta4, ta5, tai1, tai2, tai4, tai5, tan1, tan2, tan3, tan4, tan5, tang1, tang2, tang3, tang4, tang5, tao1, tao2, tao3, tao4, tao5, te4, teng2, teng5, ti1, ti2, ti3, ti4, ti5, tian1, tian2, tian3, tian4, tiao1, tiao2, tiao3, tiao4, tiao5, tie1, tie3, tie4, tie5, ting1, ting2, ting3, ting5, tong1, tong2, tong3, tong4, tong5, tou1, tou2, tou3, tou4, tou5, tu1, tu2, tu3, tu4, tu5, tuan1, tuan2, tuan3, tuan4, tui1, tui2, tui3, tui4, tun1, tun2, tun3, tun4, tun5, tuo1, tuo2, tuo3, tuo4, tuo5, wa1, wa2, wa3, wa4, wa5, wai1, wai3, wai4, wai5, wan1, wan2, wan3, wan4, wang1, wang2, wang3, wang4, wang5, wei1, wei2, wei3, wei4, wei5, wen1, wen2, wen3, wen4, weng1, weng3, weng4, wo1, wo3, wo4, wo5, wu1, wu2, wu3, wu4, wu5, xi1, xi2, xi3, xi4, xi5, xia1, xia2, xia4, xia5, xian1, xian2, xian3, xian4, xian5, xiang1, xiang2, xiang3, xiang4, xiang5, xiao1, xiao2, xiao3, xiao4, xie1, xie2, xie3, xie4, xie5, xin1, xin2, xin3, xin4, xin5, xing1, xing2, xing3, xing4, xing5, xiong1, xiong2, xiong4, xiong5, xiu1, xiu3, xiu4, xu1, xu2, xu3, xu4, xu5, xuan1, xuan2, xuan3, xuan4, xue1, xue2, xue3, xue4, xun1, xun2, xun4, xun5, ya1, ya2, ya3, ya4, ya5, yan1, yan2, yan3, yan4, yan5, yang1, yang2, yang3, yang4, yang5, yao1, yao2, yao3, yao4, yao5, ye1, ye2, ye3, ye4, ye5, yi1, yi2, yi3, yi4, yi5, yin1, yin2, yin3, yin4, ying1, ying2, ying3, ying4, ying5, yo1, yo5, yong1, yong2, yong3, yong4, you1, you2, you3, you4, you5, yu1, yu2, yu3, yu4, yu5, yuan1, yuan2, yuan3, yuan4, yue1, yue3, yue4, yun1, yun2, yun3, yun4, za1, za2, za3, za5, zai1, zai3, zai4, zai5, zan1, zan2, zan3, zan4, zan5, zang1, zang3, zang4, zao1, zao2, zao3, zao4, zao5, ze2, ze4, zei2, zen3, zen4, zeng1, zeng2, zeng4, zha1, zha2, zha3, zha4, zha5, zhai1, zhai2, zhai3, zhai4, zhan1, zhan3, zhan4, zhang1, zhang3, zhang4, zhang5, zhao1, zhao2, zhao3, zhao4, zhe1, zhe2, zhe3, zhe4, zhe5, zhen1, zhen3, zhen4, zheng1, zheng3, zheng4, zhi1, zhi2, zhi3, zhi4, zhi5, zhong1, zhong3, zhong4, zhou1, zhou2, zhou3, zhou4, zhou5, zhu1, zhu2, zhu3, zhu4, zhu5, zhua1, zhua3, zhuai3, zhuai4, zhuan1, zhuan3, zhuan4, zhuan5, zhuang1, zhuang4, zhuang5, zhui1, zhui4, zhun1, zhun3, zhuo1, zhuo2, zi1, zi3, zi4, zi5, zong1, zong3, zong4, zou1, zou3, zou4, zu1, zu2, zu3, zuan1, zuan3, zuan4, zui1, zui3, zui4, zun1, zun3, zun4, zuo1, zuo2, zuo3, zuo4, zuo5";
	private HashSet<String> validSyllabeDict = new HashSet<String>();

	
	public ChineseDict()
	{
		// fill valid syllable dict
		{
			String[] syls = syllables.split(",");
			for (int i = 0; i < syls.length; i++)
			{
				String s = syls[i].trim();
				if (!validSyllabeDict.contains(s))
				{
					validSyllabeDict.add(s);
				}				
			}
		}
		
		
		BufferedReader reader = null;
		Pattern pat = Pattern.compile("([^\\s]+)\\s+([^\\s]+)\\s+\\[([^\\]]+)\\].*");
		
		
		/*
		// Pattern pat = Pattern.compile("([^\\s]+)\\s+([^\\s]+)\\s+\\[([^\\]]+)\\]");				
		Matcher m1 = pat.matcher("21三體綜合症 21三体综合症 [er4 shi2 yi1 san1 ti3 zong1 he2 zheng4] /trisomy/Down's syndrome/");
		boolean b = Pattern.matches("([\\S]+)", "aa");
	    b = Pattern.matches("([\\S]+)", "21三體綜合症 21三体综合症 ");
	    b = Pattern.matches("([\\S]+)", "21三體綜合症");
	    b = Pattern.matches("([^\\s]+)\\s+([^\\s]+)\\s+\\[([^\\]]+)\\].+", "21三體綜合症 21三体综合症 [er4 shi2 yi1 san1 ti3 zong1 he2 zheng4] /trisomy/Down's syndrome/");
	       */

		try
		{
			InputStream stream = getClass().getClassLoader().getResourceAsStream("marytts/language/cn/lexicon/cedict_1_0_ts_utf-8_mdbg.txt");
			reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
			String line;
			int countDupWords = 0;
			int countInvalidLine = 0;
			int countCommentLine = 0;
			int totalLine = 0;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				totalLine++;
				if (!line.startsWith("#"))
				{
					Matcher m = pat.matcher(line);
					if (m.matches())
					{
						if ( m.groupCount() == 3)
						{
							if (!dict.containsKey(m.group(2)))
							{
								String key = m.group(2);
								String value = m.group(3).toLowerCase();
								
								Boolean valid = true;
								String[] syls = value.split("\\s");
								for (int i = 0; i < syls.length; i++)
								{
									if (validSyllabeDict.contains(syls[i]))
									{
										if (syllableDict.containsKey(syls[i]))
										{
											syllableDict.put(syls[i], ((Integer)syllableDict.get(syls[i])) + 1);
										}
										else
										{
											syllableDict.put(syls[i], 1);
										}
									}
									else
									{
										valid = false;
									}
								}										

								if (valid)
								{
									// put valid into dict
									dict.put(key, ConvertPinyinToPron(syls));
								}
								else
								{
									countInvalidLine++;
								}
							}
							else
							{
								// TODO: dup word are actually polyphone,
								// need to handle later
								countDupWords++;
							}
						}
						else
						{
							countInvalidLine++;
						}
					}
					else
					{
						countInvalidLine++;						
					}
				}
				else
				{
					countCommentLine++;
				}
			}
			
			
			AnalyzeSyllables();
			if (reader != null)
			{
				reader.close();
			}
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	int getWords()
	{
		return dict.size();
	}
	
	// look up dict first, if not found, try to get pron char by char 
	String lookup(String word)
	{
		String result = null;
		if (dict.containsKey(word))
		{
			result = dict.get(word);
		}
		
		if (result == null)
		{
			result = getSpelloutPron(word);
		}
		
		return result;
	}
	
	// get pron char by char
	String getSpelloutPron(String word)
	{
		String pron = null;
		if (!word.isEmpty() && word.length() > 1)
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < word.length(); i++)
			{
				String p =  lookup(word.substring(i, i + 1));
				if (p != null)
				{
					if (sb.length() > 0)
					{
						sb.append(" - ");
					}
					
					sb.append(p);					
				}				
			}	
			
			if (sb.length() > 0)
			{
				pron = sb.toString();
			}
		}
		
		return pron;
	}
	
	
	// break pinyin string into pronunciation 
	static String ConvertPinyinToPron(String[] syls)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < syls.length; i++)
		{
			String p = BreakSyllables2(syls[i]);
			if (sb.length() > 0)
			{
				// syllable boundary
				sb.append(" - ");
			}
			
			sb.append(p);
		}
		
		return sb.toString();
	}
	
	// break chinese pinyin into initial and finals
	static String BreakSyllables2(String syl)
	{
		String[] arr = BreakSyllables(syl);
		String r = arr[0];
		if (arr[1] != null)
		{
			r += " " + arr[1];
		}
		
		return r;
	}
		
	// break chinese pinyin into initial and finals	
	static String[] BreakSyllables(String syl)
	{
		// 23 initials
		String[] initials = {
				"b",
				"p",
				"m", 
				"f",
				"d",
				"t",
				"n",
				"l",
				"g",
				"k",
				"h",
				"j",
				"q",
				"x",
				"z",
				"c",
				"s",
				"zh",
				"ch",
				"sh",	
				"r",
				"y",
				"w"
		};
		
		// 160+ finals
		// [a1, a2, a3, a4, a5, ai1, ai2, ai3, ai4, ai5, an1, an2, an3, an4, an5, ang1, ang2, ang3, ang4, ang5, ao1, ao2, ao3, ao4, ao5, e1, e2, e3, e4, e5, ei1, ei2, ei3, ei4, ei5, en1, en2, en3, en4, en5, eng1, eng2, eng3, eng4, eng5, er2, er3, er4, i1, i2, i3, i4, i5, ia1, ia2, ia3, ia4, ia5, ian1, ian2, ian3, ian4, ian5, iang1, iang2, iang3, iang4, iang5, iao1, iao2, iao3, iao4, iao5, ie1, ie2, ie3, ie4, ie5, in1, in2, in3, in4, in5, ing1, ing2, ing3, ing4, ing5, iong1, iong2, iong3, iong4, iong5, iu1, iu2, iu3, iu4, iu5, o1, o2, o3, o4, o5, ong1, ong2, ong3, ong4, ong5, ou1, ou2, ou3, ou4, ou5, u1, u2, u3, u4, u5, u:2, u:3, u:4, u:e4, ua1, ua2, ua3, ua4, ua5, uai1, uai2, uai3, uai4, uai5, uan1, uan2, uan3, uan4, uan5, uang1, uang2, uang3, uang4, uang5, ue1, ue2, ue3, ue4, ue5, ui1, ui2, ui3, ui4, ui5, un1, un2, un3, un4, un5, uo1, uo2, uo3, uo4, uo5]
		
		
		String[] arr = new String[2];
		
		if (syl.length() > 2)
		{
			String s = syl.substring(0, 2);
			if (s.equals("ch") || s.equals("zh") || s.equals("sh"))
			{
				arr[0] = s;
				arr[1] = syl.substring(2);
				return arr;
			}
		}
		
		if (syl.length() > 1)
		{
			String s = syl.substring(0, 1);
			for (int i = 0; i < initials.length; i++)
			{
				if (s.equals(initials[i]))
				{
					arr[0] = s;
					arr[1] = syl.substring(1);
					return arr;					
				}
			}			
		}
		
		arr[0] = syl;
		arr[1] = null;
		return arr;		
	}
	
	
	void AnalyzeSyllables()
	{
		// analyze the syllable, break into initial and finals 		
		String[] syls = syllableDict.keySet().toArray(new String[0]);
		Arrays.sort(syls);
		
		HashSet<String> initials = new HashSet<String>();
		HashSet<String> finals = new HashSet<String>();
 		for (int i = 0; i < syls.length; i++)
		{
			String[] arr = BreakSyllables(syls[i]);
			if (arr[1] != null)
			{
				if (!initials.contains(arr[0]))
				{
					initials.add(arr[0]);
				}

				if (!finals.contains(arr[1]))
				{
					finals.add(arr[1]);
				}
			}
			else
			{
				if (!finals.contains(arr[0]))
				{
					finals.add(arr[0]);
				}
			}
		}		
 		
 		initialDict = initials;
 		finalDict = finals;
 		
		String[] initialsSorted = initials.toArray(new String[0]);
		Arrays.sort(initialsSorted); 		
		
		String[] finalsSorted = finals.toArray(new String[0]);
		Arrays.sort(finalsSorted); 			
	}
}
