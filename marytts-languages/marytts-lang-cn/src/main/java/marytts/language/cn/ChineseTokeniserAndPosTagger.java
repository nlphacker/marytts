package marytts.language.cn;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import marytts.datatypes.MaryData;
import marytts.datatypes.MaryDataType;
import marytts.datatypes.MaryXML;
import marytts.modules.InternalModule;
import marytts.util.dom.MaryDomUtils;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import java.io.StringReader;

import com.chenlb.mmseg4j.ComplexSeg;
import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MMSeg;
import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.Word;

public class ChineseTokeniserAndPosTagger extends InternalModule
{
	ChineseDict chineseDict;
    public ChineseTokeniserAndPosTagger() {
    	//declares input as RAWMARYXML and output as TOKENS for locale zh-CN
    	super("ChineseTokeniser", MaryDataType.RAWMARYXML, MaryDataType.PARTSOFSPEECH, new Locale("zh"));
    	
    	chineseDict = new ChineseDict();
    	
    	logger.info("word count:" + chineseDict.getWords());
    	String pron = chineseDict.lookup("吖");
    	pron = chineseDict.lookup("A");    	
    	pron = chineseDict.lookup("ＡＢＣ");    	
    	pron = chineseDict.lookup("吖A");    	
    	pron = chineseDict.lookup("同行");
    	pron = chineseDict.lookup("银行");
    	pron = chineseDict.lookup("A啊啊");
    	pron = chineseDict.lookup("中国古代");    	
    }
    
    public MaryData process(MaryData d)
    throws Exception
    {    
	NodeIterator tokenIterator = MaryDomUtils.createNodeIterator(d.getDocument(), "t");
    	if(tokenIterator.nextNode()!=null)
    	{
    		//the document has already been tokenized
    		//TODO: get POS and pronunciation for each element
    		MaryData result = new MaryData(outputType(), d.getLocale());
    		result.setDocument(d.getDocument());
            return result;	
    	}    	
    	
        Document doc = d.getDocument();
        
        NodeIterator ni = MaryDomUtils.createNodeIterator(doc, "p");

        Node paragraphNode;

        while ((paragraphNode = ni.nextNode()) != null) {
		String EndPunctuations = "？！。";
		String AllPunctuations = "？！。“‘（）；?!.\"'()[];";
			
        	String text = MaryDomUtils.getPlainTextBelow(paragraphNode);
        	
        	//cut into sentences 
        	List<String> phrases = new ArrayList<String>();
        	String currPhrase = "";
        	for(int i=0;i<text.length();i++)
        	{
			boolean endofSentence = EndPunctuations.indexOf(text.charAt(i)) >= 0;			
        		currPhrase += text.charAt(i);
        		if(endofSentence)
        		{
        			phrases.add(currPhrase);
        			currPhrase = "";
        		}
        	}

        	if(currPhrase.length()>0)
        	{
        		phrases.add(currPhrase);
        	}
        	
        	//remove paragraph text
        	setParagraphText((Element)paragraphNode,"");
            
        	for(String phrase:phrases)
        	{
        		Element sentence = MaryXML.createElement(doc, MaryXML.SENTENCE);

				Dictionary dic = Dictionary.getInstance();
				Seg seg = null;
				seg = new ComplexSeg(dic);
				MMSeg mmSeg = new MMSeg(new StringReader(phrase), seg);
				Word word = null;
				while((word=mmSeg.next())!=null) {
			            	Element createdToken = MaryXML.createElement(doc, MaryXML.TOKEN);
	
					if (AllPunctuations.indexOf(word.getString()) >= 0) {
			            		createdToken.setAttribute("pos", "$" + word.getString());					
					}
					else {
						createdToken.setAttribute("pos", "CONTENT");
					}
					
					String pinyin = chineseDict.lookup(word.getString());
					if (pinyin != null)
					{
						createdToken.setAttribute("sounds_like", pinyin);
					}
	
	  	            MaryDomUtils.setTokenText(createdToken, word.getString());
	
	  	            logger.info(word.getString()+" -> "+word.getStartOffset());
	  	            logger.info(", "+word.getEndOffset()+", "+ word.getType());
	    	            	        sentence.appendChild(createdToken);
				}	
	            
			paragraphNode.appendChild(sentence);
        	}
        }
        
        MaryData result = new MaryData(outputType(), d.getLocale());
        
        result.setDocument(doc);
        return result;
    }

    public static void setParagraphText(Element t, String s)
    {
        if (!t.getNodeName().equals(MaryXML.PARAGRAPH))
            throw new DOMException(DOMException.INVALID_ACCESS_ERR,
                                   "Only " + MaryXML.PARAGRAPH + " elements allowed, received " +
                                   t.getNodeName() + ".");
        // Here, we rely on the fact that a t element has at most
        // one TEXT child with non-whitespace content:
        Document doc = t.getOwnerDocument();
        NodeIterator textIt = ((DocumentTraversal)doc).
            createNodeIterator(t, NodeFilter.SHOW_TEXT, null, false);
        Text text = null;
        String textString = null;
        while ((text = (Text) textIt.nextNode()) != null) {
            textString = text.getData().trim();
            if (!textString.equals("")) break;
        }
        if (text == null) { // token doesn't have a non-whitespace text child yet
            text = (Text)t.getOwnerDocument().createTextNode(s);
            t.appendChild(text);
        } else { // found the one text element with non-whitespace content
            // overwrite it:
            text.setData(s);
        }
    }

}
