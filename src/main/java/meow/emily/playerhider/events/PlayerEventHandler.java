package meow.emily.playerhider.events;

import com.mojang.realmsclient.gui.ChatFormatting;
import meow.emily.playerhider.Emily;
import meow.emily.playerhider.config.ConfigHandler;
import meow.emily.playerhider.keybinds.Keybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class PlayerEventHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if (Keybinds.toggleemily.isPressed()) {
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