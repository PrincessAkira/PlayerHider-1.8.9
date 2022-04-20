package me.syfe.srp.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.syfe.srp.SRP;
import me.syfe.srp.util.OnlinePlayers;
import me.syfe.srp.config.ConfigHandler;
//import me.syfe.srp.gui.SRPGui;
import me.syfe.srp.util.TickScheduler;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class SRPCommand extends CommandBase {


    /**
     * Gets the name of the command
     */
    public String getCommandName() {
        return "ph";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    /**
     * Gets the usage string for the command.
     */
    public String getCommandUsage(ICommandSender sender) {

        return ChatFormatting.WHITE + "------------" + ChatFormatting.AQUA + SRP.PREFIX + ChatFormatting.WHITE + "------------" + "\n" +
               // ChatFormatting.RED + "+" + ChatFormatting.BLUE + " /ph - Open GUI" + "\n" +
                ChatFormatting.RED + "+" + ChatFormatting.WHITE + " /ph add/remove - Add/remove players" + "\n" +
                ChatFormatting.RED + "+" + ChatFormatting.WHITE + " /ph blacklist add/remove/list - blacklist cmds" + "\n" +
                ChatFormatting.RED + "+" + ChatFormatting.WHITE + " /ph toggle - Toggles the mod on/off" + "\n" +
                ChatFormatting.RED + "+" + ChatFormatting.WHITE + " /ph list - Shows current rendered players" + "\n" +
                ChatFormatting.WHITE + "------------------------------------------";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("add")) {
                ConfigHandler.playersToRender += args[1] + ",";
                ConfigHandler.syncFromFields();
                player.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + SRP.PREFIX + ChatFormatting.WHITE + "]" + " Added player to be rendered " + ChatFormatting.BOLD + args[1]));
            } else if (args[0].equalsIgnoreCase("remove")) {
                String[] localNames = ConfigHandler.playersToRender.split(",");
                String namesToSave = "";
                for (int i = 0; i < localNames.length; i++) {
                    if(!args[1].equals(localNames[i])){
                        namesToSave += localNames[i] + ",";
                    }
                }
                ConfigHandler.playersToRender = namesToSave;

                ConfigHandler.syncFromFields();
                player.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + SRP.PREFIX + ChatFormatting.WHITE + "]" +" Removed player from being rendered " + ChatFormatting.BOLD + args[1]));
            } else if(args[0].equalsIgnoreCase("blacklist")){
                try {
                    if(args[1].equalsIgnoreCase("add")){
                        ConfigHandler.whitelistedPlayers += args[2] + ",";
                        ConfigHandler.syncFromFields();
                        player.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + SRP.PREFIX + ChatFormatting.WHITE + "]" +" Added player to blacklist " + ChatFormatting.BOLD + args[2]));
                        return;
                    } else if(args[1].equalsIgnoreCase("remove")) {
                        String[] localNames = ConfigHandler.whitelistedPlayers.split(",");
                        String namesToSave = "";
                        for (int i = 0; i < localNames.length; i++) {
                            if (!args[2].equals(localNames[i])) {
                                namesToSave += localNames[i] + ",";
                            }
                        }
                        ConfigHandler.whitelistedPlayers = namesToSave;

                        ConfigHandler.syncFromFields();

                        player.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + SRP.PREFIX + ChatFormatting.WHITE + "]" +" Removed player from blacklist " + ChatFormatting.BOLD + args[2]));
                        return;
                    } else if(args[1].equalsIgnoreCase("list")) {
                        String str = ConfigHandler.playersToRender;
                        if(!str.isEmpty()){
                            str = str.substring(0, str.length() -1);
                        } else {
                            str = "none";
                        }

                        player.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + SRP.PREFIX + ChatFormatting.WHITE + "]" +" Current blackisted players are " + ChatFormatting.BOLD + str));
                    } else {
                        String str = ConfigHandler.playersToRender;
                        if(!str.isEmpty()){
                            str = str.substring(0, str.length() -1);
                        } else {
                            str = "none";
                        }

                        player.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + SRP.PREFIX + ChatFormatting.WHITE + "]" +" Current blacklisted players are " + ChatFormatting.BOLD + str));
                    }
                } catch(Exception e){} finally {
                    String str = ConfigHandler.playersToRender;
                    if(!str.isEmpty()){
                        str = str.substring(0, str.length() -1);
                    } else {
                        str = "none";
                    }

                    player.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + SRP.PREFIX + ChatFormatting.WHITE + "]" +" Current blacklisted players are " + ChatFormatting.BOLD + str));
                }

            } else if (args[0].equalsIgnoreCase("toggle")) {

                if(ConfigHandler.renderPlayers){
                    ConfigHandler.renderPlayers = false;
                    ConfigHandler.syncFromFields();
                    player.addChatMessage(new ChatComponentText(ChatFormatting.AQUA + SRP.PREFIX + ChatFormatting.RED + " Rendering players is now " + ChatFormatting.BOLD + "off"));
                } else {
                    ConfigHandler.renderPlayers = true;
                    ConfigHandler.syncFromFields();
                    player.addChatMessage(new ChatComponentText(ChatFormatting.AQUA + SRP.PREFIX + ChatFormatting.GREEN + " Rendering players is now " + ChatFormatting.BOLD + "on"));
                }

            } else if (args[0].equalsIgnoreCase("list")) {
                String str = ConfigHandler.playersToRender;
                if(!str.isEmpty()){
                    str = str.substring(0, str.length() -1);
                } else {
                    str = "none";
                }

                player.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + SRP.PREFIX + ChatFormatting.WHITE + "]" +" Current hidden players are " + ChatFormatting.BOLD + str));
            } else if (args[0].equalsIgnoreCase("help")) {
                player.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
            } else {
                player.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
            }
        } //else {
          //  TickScheduler.INSTANCE.schedule(0, () -> Minecraft.getMinecraft().displayGuiScreen(new SRPGui()));
        //}
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "add","remove","toggle","blacklist","list","help");
        } else if (args.length >= 2) {
            String[] inConfig = ConfigHandler.playersToRender.split(",");
            String[] whitelist = ConfigHandler.whitelistedPlayers.split(",");
            String[] onlinePlayersLobby = OnlinePlayers.getListOfPlayerUsernames();
            if(args[0].equalsIgnoreCase("remove")) {
                return getListOfStringsMatchingLastWord(args, inConfig);
            }
            if (args[0].equalsIgnoreCase("add")) {
                return getListOfStringsMatchingLastWord(args, onlinePlayersLobby);
            }
            if (args[0].equalsIgnoreCase("blacklist")) {
                if(args[1].equalsIgnoreCase("add")){
                    return getListOfStringsMatchingLastWord(args, onlinePlayersLobby);
                } else if(args[1].equalsIgnoreCase("remove")){
                    return getListOfStringsMatchingLastWord(args, "blacklist");
                } else {
                    return getListOfStringsMatchingLastWord(args, "add","remove","list");
                }
            }
        }
        return null;
    }
}
