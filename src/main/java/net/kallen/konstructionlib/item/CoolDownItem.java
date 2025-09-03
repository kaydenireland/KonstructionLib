package net.kallen.konstructionlib.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public class CoolDownItem extends Item {
    public CoolDownItem(Properties pProperties) {
        super(pProperties);
    }


    public void applyCooldown(ServerPlayer serverPlayer, int coolDownTime){
        serverPlayer.getCooldowns().addCooldown(this, coolDownTime);
    }
}
