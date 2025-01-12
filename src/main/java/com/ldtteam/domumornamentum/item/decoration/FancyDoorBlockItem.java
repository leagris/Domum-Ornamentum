package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.decorative.FancyDoorBlock;
import com.ldtteam.domumornamentum.block.types.DoorType;
import com.ldtteam.domumornamentum.block.types.FancyDoorType;
import com.ldtteam.domumornamentum.block.vanilla.DoorBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FancyDoorBlockItem extends DoubleHighBlockItem
{
    private final FancyDoorBlock doorBlock;

    public FancyDoorBlockItem(final FancyDoorBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.doorBlock = blockIn;
    }

    @Override
    public @NotNull Component getName(final ItemStack stack)
    {
        final CompoundTag dataNbt = stack.getOrCreateTagElement("textureData");
        final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(dataNbt);

        final IMateriallyTexturedBlockComponent coverComponent = doorBlock.getComponents().get(0);
        final Block centerBlock = textureData.getTexturedComponents().getOrDefault(coverComponent.getId(), coverComponent.getDefault());
        final Component centerBlockName = BlockUtils.getHoverName(centerBlock);

        return new TranslatableComponent(Constants.MOD_ID + ".door.fancy.name.format", centerBlockName);
    }

    @Override
    public void appendHoverText(
      final @NotNull ItemStack stack, @Nullable final Level worldIn, final @NotNull List<Component> tooltip, final @NotNull TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        FancyDoorType doorType;
        try {
            if (stack.getOrCreateTag().contains("type"))
                doorType = FancyDoorType.valueOf(stack.getOrCreateTag().getString("type").toUpperCase());
            else
                doorType = FancyDoorType.FULL;
        } catch (Exception ex) {
            doorType = FancyDoorType.FULL;
        }

        tooltip.add(new TranslatableComponent(Constants.MOD_ID + ".origin.tooltip"));
        tooltip.add(new TextComponent(""));
        tooltip.add(new TranslatableComponent(
          Constants.MOD_ID + ".door.fancy.type.format",
          new TranslatableComponent(
            Constants.MOD_ID + ".door.fancy.type.name." + doorType.getTranslationKeySuffix()
          )
      ));
    }
}

