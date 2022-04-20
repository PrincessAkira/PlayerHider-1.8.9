package me.syfe.srp;

import me.syfe.srp.commands.SRPCommand;
import me.syfe.srp.config.ConfigHandler;
import me.syfe.srp.events.SRPEventHandler;
import me.syfe.srp.keybinds.Keybinds;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = SRP.MODID, name = SRP.NAME, version = SRP.VERSION)
public class SRP
{
    public static final String MODID = "PH";
    public static final String NAME = "PlayerHider";
    public static final String VERSION = "1.0";
    public static Minecraft mc = Minecraft.getMinecraft();

    public static final Logger LOGGER = LogManager.getLogger("PlayerHider");

    public static final String PREFIX = "PH";

    @Mod.Instance(SRP.MODID)
    public static SRP instance;

    @Mod.EventHandler
    public void preLoad(FMLPreInitializationEvent event){
        ConfigHandler.preInit();
        Keybinds.register();
    }
    @Mod.EventHandler
    public void load(FMLInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(new SRPEventHandler());
        FMLCommonHandler.instance().bus().register(new SRPEventHandler());

        ClientCommandHandler.instance.registerCommand(new SRPCommand());
    }
}