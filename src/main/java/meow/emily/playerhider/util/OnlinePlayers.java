package meow.emily.playerhider.util;

import meow.emily.playerhider.Emily;
import net.minecraft.client.network.NetworkPlayerInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OnlinePlayers {
    public static String[] getListOfPlayerUsernames() {
        Collection<NetworkPlayerInfo> players = Emily.mc.getNetHandler().getPlayerInfoMap();
        List<String> list = new ArrayList<>();
        for (NetworkPlayerInfo info : players)
            list.add(info.getGameProfile().getName());
        return list.toArray(new String[0]);
    }
}
