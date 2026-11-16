package dev.md5n.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public final class Fireworks {
    private Fireworks() {}

    /** يطلق صاروخ فايروركس (من اليد الثانية أو الهوت بار) ويرجع true لو نجح. */
    public static boolean use() {
        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayerEntity player = mc.player;
        if (player == null || mc.interactionManager == null) return false;

        if (player.getOffHandStack().isOf(Items.FIREWORK_ROCKET)) {
            mc.interactionManager.interactItem(player, Hand.OFF_HAND);
            player.swingHand(Hand.OFF_HAND);
            return true;
        }

        PlayerInventory inv = player.getInventory();
        int slot = -1;
        for (int i = 0; i < 9; i++) {
            if (inv.getStack(i).isOf(Items.FIREWORK_ROCKET)) { slot = i; break; }
        }
        if (slot == -1) return false;

        int previous = inv.selectedSlot;
        inv.selectedSlot = slot;

        mc.interactionManager.interactItem(player, Hand.MAIN_HAND);
        player.swingHand(Hand.MAIN_HAND);
        inv.selectedSlot = previous;

        return true;
    }
}
