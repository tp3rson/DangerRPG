package mixac1.dangerrpg.capability.itemattr;

import mixac1.dangerrpg.api.item.IADynamic;
import net.minecraft.item.ItemStack;

public class IACurrExp extends IADynamic
{
    public IACurrExp(String name)
    {
        super(name);
    }
    
    @Override
    public void init(ItemStack stack) {}
    
    @Override
    public void lvlUp(ItemStack stack) {}
}
