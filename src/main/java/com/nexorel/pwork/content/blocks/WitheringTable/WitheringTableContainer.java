package com.nexorel.pwork.content.blocks.WitheringTable;

import com.nexorel.pwork.PRegister;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class WitheringTableContainer extends Container {

    public WitheringTableTile tileEntity;
    private PlayerEntity player;
    private IItemHandler playerinv;

    public WitheringTableContainer(int windowID, PlayerInventory playerInventory, PlayerEntity player, final WitheringTableTile tile) {
        super(PRegister.WITHER_CRAFTING_TABLE_CONTAINER.get(), windowID);

        this.tileEntity = tile;
        this.player = player;
        this.playerinv = new InvWrapper(playerInventory);

        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 124, 35)); // result

                addSlot(new SlotItemHandler(h, 1, 30, 17));
                addSlot(new SlotItemHandler(h, 2, 48, 17));
                addSlot(new SlotItemHandler(h, 3, 66, 17));
                addSlot(new SlotItemHandler(h, 4, 30, 35));
                addSlot(new SlotItemHandler(h, 5, 48, 35));
                addSlot(new SlotItemHandler(h, 6, 66, 35));
                addSlot(new SlotItemHandler(h, 7, 30, 53));
                addSlot(new SlotItemHandler(h, 8, 48, 53));
                addSlot(new SlotItemHandler(h, 9, 66, 53));

            });
        }
        layoutPlayerInventorySlots(8,84);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return stillValid(IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos()), player, PRegister.WITHER_CRAFTING_TABLE.get());
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else {
                if (index < 28) {
                    if (!this.moveItemStackTo(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.moveItemStackTo(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerinv, 10, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerinv, 0, leftCol, topRow, 9, 18);
    }
}
