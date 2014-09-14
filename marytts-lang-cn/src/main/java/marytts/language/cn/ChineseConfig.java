package marytts.language.cn;

import marytts.config.LanguageConfig;
import marytts.exceptions.MaryConfigurationException;


public class ChineseConfig extends LanguageConfig {
	public ChineseConfig() throws MaryConfigurationException {
		super(ChineseConfig.class.getResourceAsStream("cn.config"));
	}
}
