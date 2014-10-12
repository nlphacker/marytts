package marytts.language.cn;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import marytts.datatypes.MaryData;
import marytts.datatypes.MaryDataType;
import marytts.datatypes.MaryXML;
import marytts.exceptions.MaryConfigurationException;
import marytts.fst.FSTLookup;
import marytts.modules.InternalModule;
import marytts.modules.phonemiser.AllophoneSet;
import marytts.modules.phonemiser.TrainedLTS;
import marytts.util.dom.MaryDomUtils;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.traversal.NodeIterator;

public class ChinesePhonemiser extends InternalModule
{
    protected Map<String, List<String>> userdict;
    protected FSTLookup lexicon;
    protected TrainedLTS lts;
    protected boolean removeTrailingOneFromPhones = true;

    protected AllophoneSet allophoneSet;
    
    public ChinesePhonemiser()
    throws IOException,  MaryConfigurationException
    {
        super("Phonemiser", MaryDataType.WORDS, MaryDataType.PHONEMES, new Locale("zh"));
    }
    
    public MaryData process(MaryData d)
        throws Exception
    {
        Document doc = d.getDocument();
        NodeIterator it = MaryDomUtils.createNodeIterator(doc, doc, MaryXML.TOKEN);
        Element t = null;
        while ((t = (Element) it.nextNode()) != null) {
                String text;
                
                // Do not touch tokens for which a transcription is already
                // given (exception: transcription contains a '*' character:
                if (t.hasAttribute("ph") &&
                    !t.getAttribute("ph").contains("*")) {
                    continue;
                } 
 
 
                text = MaryDomUtils.tokenText(t);
                
                String pos = null;
                // use part-of-speech if available
                if (t.hasAttribute("pos")){
                    pos = t.getAttribute("pos");
                }
                
                if (text != null && !text.equals("") && (pos==null || !pos.startsWith("$")/*punctuation*/)) {               
                	String ph = null;
                	String g2pMethod = "lexicon";
                	if (t.hasAttribute("sounds_like"))
                	{                	
                		ph = t.getAttribute("sounds_like");
                	}
                	else
                	{
                		// maybe English world
                	}

                    if (ph != null && ph.length() > 0) {
                        setPh(t, ph.toString());
                        t.setAttribute("g2p_method", g2pMethod);
                    }
            }
        }
        MaryData result = new MaryData(outputType(), d.getLocale());
        result.setDocument(doc);
        return result;
    }
    
    
    protected void setPh(Element t, String ph)
    {
        if (!t.getTagName().equals(MaryXML.TOKEN))
            throw new DOMException(DOMException.INVALID_ACCESS_ERR,
                                   "Only t elements allowed, received " +
                                   t.getTagName() + ".");
        if (t.hasAttribute("ph")) {
            String prevPh = t.getAttribute("ph");
            // In previous sampa, replace star with sampa:
            String newPh = prevPh.replaceFirst("\\*", ph);
            t.setAttribute("ph", newPh);
        } else {
            t.setAttribute("ph", ph);
        }
    }
}
