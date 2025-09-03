package net.kallen.konstructionlib.item;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MagicMirrorItem extends CoolDownItem {


    private static final String COOLDOWN_TAG = "MirrorCooldown";
    private final int cooldownTime;
    private final SoundEvent sound;


    public MagicMirrorItem(Properties pProperties, int cooldownTime, SoundEvent sound) {
        super(pProperties.stacksTo(1));
        this.sound = sound;
        this.cooldownTime = cooldownTime;
    }
    public MagicMirrorItem(Properties pProperties, int cooldownTime) {
        super(pProperties.stacksTo(1));
        this.sound = SoundEvents.ENDERMAN_TELEPORT;
        this.cooldownTime = cooldownTime;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack itemInHand = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide && pPlayer instanceof ServerPlayer serverPlayer) { // Ensure server-side execution

            CompoundTag persistentData = serverPlayer.getPersistentData();


            long lastUsedTime = persistentData.getLong(COOLDOWN_TAG);
            long currentTime = serverPlayer.level().getGameTime();

            if ((currentTime < lastUsedTime + cooldownTime) && lastUsedTime > 0) {
                return InteractionResultHolder.fail(serverPlayer.getItemInHand(pUsedHand));
            }

            BlockPos spawnPos = serverPlayer.getRespawnPosition();
            if (spawnPos != null) {
                // Teleport the player safely
                serverPlayer.teleportTo(spawnPos.getX() + 0.5, spawnPos.getY() + 0.5, spawnPos.getZ() + 0.5);

                // Play a sound effect
                pLevel.playSound(null, serverPlayer.blockPosition(), sound, SoundSource.PLAYERS, 1.0F, 1.0F);



            }else{
                spawnPos = pLevel.getSharedSpawnPos();
                serverPlayer.teleportTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
            }

            persistentData.putLong(COOLDOWN_TAG, currentTime);
            serverPlayer.getCooldowns().addCooldown(this, cooldownTime);
            return InteractionResultHolder.success(itemInHand);

        }



        return InteractionResultHolder.pass(itemInHand);
    }


    public void applyCooldown(ServerPlayer serverPlayer, int coolDownTime){
        serverPlayer.getCooldowns().addCooldown(this, coolDownTime);
    }




}
