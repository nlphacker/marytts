
package marytts.language.cn;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import marytts.config.LanguageConfig;
import marytts.config.MaryConfig;
import marytts.exceptions.MaryConfigurationException;
import marytts.language.cn.ChineseConfig;

import org.junit.Test;


public class ChineseConfigTest {

	@Test
	public void isNotMainConfig() throws MaryConfigurationException {
		MaryConfig m = new ChineseConfig();
		assertFalse(m.isMainConfig());
	}
	
	@Test
	public void canGet() {
		MaryConfig m = MaryConfig.getLanguageConfig(new Locale("zh"));
                System.out.print("here1\n");
		assertNotNull(m);
                System.out.print("here2\n");
		assertTrue(((LanguageConfig)m).getLocales().contains(new Locale("zh")));
                System.out.print("here3\n");
	}
	
	
	@Test
	public void hasChineseLocale() throws MaryConfigurationException {
		LanguageConfig e = new ChineseConfig();
		assertTrue(e.getLocales().contains(new Locale("zh")));
	}
}
