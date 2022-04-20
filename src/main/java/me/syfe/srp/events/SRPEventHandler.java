package me.syfe.srp.events;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.syfe.srp.SRP;
import me.syfe.srp.gui.SRPGui;
import me.syfe.srp.util.TickScheduler;
import me.syfe.srp.util.Utils;
import me.syfe.srp.config.ConfigHandler;
import me.syfe.srp.keybinds.Keybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class SRPEventHandler {
    @SubscribeEvent
    public void onPrePlayerRender(RenderPlayerEvent.Pre e){
        EntityPlayer enPlayer = e.entityPlayer;
        if(!ConfigHandler.renderPlayers && !enPlayer.equals(Minecraft.getMinecraft().thePlayer))  {
            String[] localPlayersToRender = ConfigHandler.playersToRender.split(",");
            String[] whitelistedPlayers = ConfigHandler.whitelistedPlayers.split(",");
            if(!Utils.isNPC(enPlayer)){
                e.setCanceled(false);
                for (String s : localPlayersToRender) {
                    if (s.equals(enPlayer.getGameProfile().getName())) {
                        e.setCanceled(true);
                    }
                }
                for (String whitelistedPlayer : whitelistedPlayers) {
                    if (whitelistedPlayer.equals(enPlayer.getGameProfile().getName())) {
                        e.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e){
        if(Keybinds.togglesrp.isPressed()){
            if(ConfigHandler.renderPlayers){
                ConfigHandler.renderPlayers = false;
                ConfigHandler.syncFromFields();
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + SRP.PREFIX + ChatFormatting.WHITE + "]" + " Blacklisted Players are now " + ChatFormatting.BOLD + ChatFormatting.DARK_RED + "off"));
            } else {
                ConfigHandler.renderPlayers = true;
                ConfigHandler.syncFromFields();
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + SRP.PREFIX + ChatFormatting.WHITE + "]" + " Blacklisted Players are now " + ChatFormatting.BOLD + ChatFormatting.GREEN + "on"));
            }
        }
        //if(Keybinds.opengui.isPressed()){
        //    TickScheduler.INSTANCE.schedule(0, () -> Minecraft.getMinecraft().displayGuiScreen(new SRPGui()));
        //}
    }
}