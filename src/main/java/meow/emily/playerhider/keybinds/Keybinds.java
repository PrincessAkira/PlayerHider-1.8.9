package meow.emily.playerhider.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class Keybinds {
    public static KeyBinding toggleemily;
    public static KeyBinding opengui;

    public static void register() {
        // Toggle Mod
        togglesrp = new KeyBinding("key.toggleemily", Keyboard.KEY_NONE, "PlayerHider");
        ClientRegistry.registerKeyBinding(togglesrp);

        // opengui = new KeyBinding("key.opengui", Keyboard.KEY_NONE, "PlayerHider");
        // ClientRegistry.registerKeyBinding(opengui);
    }
}
