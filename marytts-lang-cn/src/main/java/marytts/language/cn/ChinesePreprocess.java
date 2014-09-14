package marytts.language.cn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import marytts.datatypes.MaryData;
import marytts.datatypes.MaryDataType;
import marytts.datatypes.MaryXML;
import marytts.modules.InternalModule;
import marytts.util.dom.MaryDomUtils;
import marytts.util.dom.NameNodeFilter;

import org.apache.log4j.Level;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.TreeWalker;

public class ChinesePreprocess extends InternalModule
{

	public ChinesePreprocess()
	{
		super("ChinesePreprocess",
				MaryDataType.PARTSOFSPEECH,
				MaryDataType.WORDS,
				new Locale("zh-CN"));
	}

	public MaryData process(MaryData d)
			throws Exception
			{
		Document doc = d.getDocument();
		logger.info("Expanding say-as elements...(cn)");
		expandSayasElements(doc);
		logger.info("Matching and expanding patterns...(cn)");
		matchAndExpandPatterns(doc);
		logger.info("Preprocess done (cn)");
		MaryData result = new MaryData(outputType(), d.getLocale());
		result.setDocument(doc);
		return result;
			}

	private void expandSayasElements(Document doc)
	{
		NodeList sayasElements = doc.getElementsByTagName(MaryXML.SAYAS);
		for(int i=0; i<sayasElements.getLength(); i++) {
			Element sayas = (Element) sayasElements.item(i);
			String type = sayas.getAttribute("type");
			logger.info("Expand sayas (cn)");

			// TODO: text normalization
		}

	}

	private void matchAndExpandPatterns(Document doc)
	{
		TreeWalker tw = ((DocumentTraversal)doc).createTreeWalker(
				doc, NodeFilter.SHOW_ELEMENT, new NameNodeFilter(MaryXML.TOKEN), false);
		Element t = null;
		while ((t = (Element) tw.nextNode()) != null) {
			//System.err.println("matching and expanding " + MaryDomUtils.tokenText(t));
			// Skip tokens inside say-as tags, as well as tokens
			// for which a pronunciation is given:
			if (MaryDomUtils.hasAncestor(t, MaryXML.SAYAS) ||
					t.hasAttribute("ph")
					//|| t.hasAttribute("sounds_like") every token has a "sounds_like" attribute from the tokeniser
					) {
				// ignore token
				continue;
			}

			// TODO: text normalization

		} // all tokens
	}

	/**
	 * Find the last token in the list of elements l.
	 * Starting from the last element in the list, if the element itself is a token,
	 * return it; else, if it has a direct or indirect descendant which is a token,
	 * return that one; else, go backwards in the list.
	 * @param l a list of elements
	 * @return the last token, or null if no such token can be found
	 */
	private Element getLastToken(List<Element> l) {
		if (l == null) throw new NullPointerException("Received null argument");
		if (l.isEmpty()) throw new IllegalArgumentException("Received empty list");
		for (int i=l.size()-1; i>=0; i--) {
			Element e = (Element) l.get(i);
			Element t = null;
			if (e.getTagName().equals(MaryXML.TOKEN)) {
				t = e;
			} else {
				t = MaryDomUtils.getLastElementByTagName(e, MaryXML.TOKEN);
			}
			if (t != null)
				return t;
		}
		return null;
	}

	/**
	 * Find the first token in the list of elements l.
	 * Starting from the first element in the list, if the element itself is a token,
	 * return it; else, if it has a direct or indirect descendant which is a token,
	 * return that one; else, go forward in the list.
	 * @param l a list of elements
	 * @return the first token, or null if no such token can be found
	 */
	private Element getFirstToken(List<Element> l) {
		if (l == null) throw new NullPointerException("Received null argument");
		if (l.isEmpty()) throw new IllegalArgumentException("Received empty list");
		for (int i=0; i<l.size(); i++) {
			Element e = (Element) l.get(i); 
			Element t = null;
			if (e.getTagName().equals(MaryXML.TOKEN)) {
				t = e;
			} else {
				t = MaryDomUtils.getFirstElementByTagName(e, MaryXML.TOKEN);
			}
			if (t != null)
				return t;
		}
		return null;
	}

}
