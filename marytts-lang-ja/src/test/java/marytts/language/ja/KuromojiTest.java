package marytts.language.ja;


import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
public class KuromojiTest {
    public static void main(String[] args) {
        Tokenizer tokenizer = Tokenizer.builder().build();
        assertNotNull(tokenizer);
        
        for (Token token : tokenizer.tokenize("寿　司3　貫が　食　　　べたい。")) {
            
        	System.out.println(token.getSurfaceForm() + "\t" + token.getAllFeatures());
            
        	//9 tokens in the programmed version, pronunciation last
            assert(token.getAllFeaturesArray().length==9);
        }
    }
}
