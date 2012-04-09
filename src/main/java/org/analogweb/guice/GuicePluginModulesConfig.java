package org.analogweb.guice;


import org.analogweb.ModulesBuilder;
import org.analogweb.PluginModulesConfig;
import org.analogweb.util.MessageResource;
import org.analogweb.util.PropertyResourceBundleMessageResource;
import org.analogweb.util.logging.Log;
import org.analogweb.util.logging.Logs;

/**
 * Google Guiceプラグインの既定の設定を行う{@link PluginModulesConfig}の実装です。<br/>
 * コントローラのインスタンスをGuiceより解決するよう設定されます。
 * @author snowgoose
 */
public class GuicePluginModulesConfig implements PluginModulesConfig {

    public static final MessageResource PLUGIN_MESSAGE_RESOURCE = new PropertyResourceBundleMessageResource(
            "org.analogweb.guice.analog-messages");
    private static final Log log = Logs.getLog(GuicePluginModulesConfig.class);

    @Override
    public ModulesBuilder prepare(ModulesBuilder builder) {
        log.log(PLUGIN_MESSAGE_RESOURCE, "IGB000001");
        builder.setInvocationInstanceProviderClass(GuiceContainerAdaptorFactory.class);
        return builder;
    }

}
