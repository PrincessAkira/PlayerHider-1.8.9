package meow.emily.playerhider.events;

import com.mojang.realmsclient.gui.ChatFormatting;
import meow.emily.playerhider.Emily;
import meow.emily.playerhider.config.ConfigHandler;
import meow.emily.playerhider.keybinds.Keybinds;
import meow.emily.playerhider.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class PlayerEventHandler {
    @SubscribeEvent
    public void onPrePlayerRender(RenderPlayerEvent.Pre e) {
        EntityPlayer enPlayer = e.entityPlayer;
        if (!ConfigHandler.renderPlayers && !enPlayer.equals(Minecraft.getMinecraft().thePlayer)) {
            String[] localPlayersToRender = ConfigHandler.playersToRender.split(",");
            String[] whitelistedPlayers = ConfigHandler.blacklistedPlayers.split(",");
            if (!Utils.isNPC(enPlayer)) {
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
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if (Keybinds.togglesrp.isPressed()) {
            if (ConfigHandler.renderPlayers) {
                ConfigHandler.renderPlayers = false;
                ConfigHandler.syncFromFields();
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + Emily.PREFIX + ChatFormatting.WHITE + "]" + " Blacklisted Players are now " + ChatFormatting.BOLD + ChatFormatting.DARK_RED + "off"));
            } else {
                ConfigHandler.renderPlayers = true;
                ConfigHandler.syncFromFields();
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + Emily.PREFIX + ChatFormatting.WHITE + "]" + " Blacklisted Players are now " + ChatFormatting.BOLD + ChatFormatting.GREEN + "on"));
            }
        }
    }
}