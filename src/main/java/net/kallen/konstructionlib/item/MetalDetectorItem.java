package net.kallen.konstructionlib.item;

import net.kallen.konstructionlib.sound.KonstructionSounds;
import net.kallen.konstructionlib.util.KonstructionTags;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Tier pTier, Properties pProperties, int durability) {
        super(pProperties.durability(durability));
    }

    Tiers tier;

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        if(!pContext.getLevel().isClientSide()){
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            boolean foundBlock = false;

            for(int i = 0; i <= positionClicked.getY() + 64; i++){
                BlockState state = pContext.getLevel().getBlockState(positionClicked.below(i));

                if(isvalueableBlock(state)){
                    outputValuableCoordinates(positionClicked.below(i), player, state.getBlock());
                    foundBlock = true;

                    pContext.getLevel().playSeededSound(null, positionClicked.getX(), positionClicked.getY(), positionClicked.getZ(),
                            KonstructionSounds.METAL_DETECTOR_FOUND_ORE.get(), SoundSource.BLOCKS, 1f, 1f, 0);

                    break;
                }

            }

            if(!foundBlock){
                player.sendSystemMessage(Component.literal("No Valueables Found."));
            }
        }

        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return InteractionResult.SUCCESS;
    }

    private void outputValuableCoordinates(BlockPos blockPos, Player player, Block block) {
        player.sendSystemMessage(Component.literal("Found " + I18n.get(block.getDescriptionId()) + " at "
                + "(" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")" ));
    }

    private boolean isvalueableBlock(BlockState state) {
        return state.is(KonstructionTags.Blocks.METAL_DETECTOR_VALUABLES);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.kallenlib.metal_detector.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
