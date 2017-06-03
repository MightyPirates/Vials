package li.cil.vials.common.integration.tconstruct;

import li.cil.vials.common.integration.ModProxy;
import net.minecraftforge.fml.common.Loader;

/**
 * Created by lordjoda on 17.04.2017.
 */
public class ProxyTinkersConstruct implements ModProxy {
    public static final String MOD_ID = "tconstruct";

    @Override
    public boolean isAvailable() {
        return Loader.isModLoaded(MOD_ID);
    }

}
