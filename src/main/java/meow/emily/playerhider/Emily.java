package meow.emily.playerhider;

import meow.emily.playerhider.commands.EmilyCommand;
import meow.emily.playerhider.config.ConfigHandler;
import meow.emily.playerhider.events.PlayerEventHandler;
import meow.emily.playerhider.keybinds.Keybinds;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Emily.MODID, name = Emily.NAME, version = Emily.VERSION)
public class Emily {
    public static final String MODID = "PH";
    public static final String NAME = "PlayerHider";
    public static final String VERSION = "1.1";
    public static final Logger LOGGER = LogManager.getLogger("PlayerHider");
    public static final String PREFIX = "PH";
    public static Minecraft mc = Minecraft.getMinecraft();
    @Mod.Instance(Emily.MODID)
    public static Emily instance;

    @Mod.EventHandler
    public void preLoad(FMLPreInitializationEvent event) {
        ConfigHandler.preInit();
        Keybinds.register();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
        FMLCommonHandler.instance().bus().register(new PlayerEventHandler());

        ClientCommandHandler.instance.registerCommand(new EmilyCommand());
    }
}