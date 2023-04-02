package nmccoy.legendgear.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityJar extends TileEntity {

	public ItemStack contents = null;
	
	public TileEntityJar() {
		// TODO Auto-generated constructor stub
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("Item"))
        {
            this.contents = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("Item"));
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);

        if (this.contents != null)
        {
            par1NBTTagCompound.setCompoundTag("Item", this.contents.writeToNBT(new NBTTagCompound()));
        }
        else
        	par1NBTTagCompound.removeTag("Item");
    }

}
