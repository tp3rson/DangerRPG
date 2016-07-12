package mixac1.dangerrpg.item.tool;

import mixac1.dangerrpg.entity.projectile.EntityThrowLvlItem;
import mixac1.dangerrpg.entity.projectile.EntityThrowTomahawk;
import mixac1.dangerrpg.item.RPGToolComponent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RPGWeaponTomahawk extends RPGThrowableWeapon
{
	public RPGWeaponTomahawk(ToolMaterial toolMaterial, RPGToolComponent toolComponent, String name)
	{
		super(toolMaterial, toolComponent, name);
	}

	@Override
	protected EntityThrowLvlItem getThrowEntity(World world, EntityLivingBase entityliving, ItemStack itemstack)
	{
		return new EntityThrowTomahawk(world, entityliving, itemstack, 1.1F, 3F);
	}
}
