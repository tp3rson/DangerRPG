package mixac1.dangerrpg.item.tool;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemTool;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.LvlableItem.ItemAttributesMap;
import mixac1.dangerrpg.init.RPGItems;
import mixac1.dangerrpg.init.RPGOther.RPGCreativeTabs;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class ItemRPGPickaxe extends ItemPickaxe implements ILvlableItemTool, IHasBooksInfo
{
    RPGToolMaterial toolMaterial;

    public ItemRPGPickaxe(RPGToolMaterial toolMaterial)
    {
        super(toolMaterial.material);
        this.toolMaterial = toolMaterial;
        setUnlocalizedName(RPGItems.getRPGName(getItemComponent(this), getToolMaterial(this)));
        setTextureName(Utils.toString(DangerRPG.MODID, ":tools/", unlocalizedName));
        setCreativeTab(RPGCreativeTabs.tabRPGAmunitions);
        setMaxStackSize(1);
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player)
    {
        return DangerRPG.trans("rpgstr.no_info_yet");
    }

    @Override
    public RPGToolComponent getItemComponent(Item item)
    {
        return RPGItemComponent.PICKAXE;
    }

    @Override
    public RPGToolMaterial getToolMaterial(Item item)
    {
        return toolMaterial;
    }

    @Override
    public void registerAttributes(Item item, ItemAttributesMap map)
    {
        LvlableItem.registerParamsItemTool(item, map);
    }
}
