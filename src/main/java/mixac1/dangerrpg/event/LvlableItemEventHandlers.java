package mixac1.dangerrpg.event;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.itemattr.ItemAttributes;
import mixac1.dangerrpg.capability.playerattr.PlayerAttributes;
import mixac1.dangerrpg.event.ItemStackEvent.AddInformationEvent;
import mixac1.dangerrpg.event.ItemStackEvent.HitEntityEvent;
import mixac1.dangerrpg.event.ItemStackEvent.OnLeftClickEntityEvent;
import mixac1.dangerrpg.util.RPGCommonHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

public class LvlableItemEventHandlers
{
	@SubscribeEvent
	public boolean onLeftClickEntity(OnLeftClickEntityEvent e)
	{
		if (e.entity instanceof EntityLivingBase) {
			if (PlayerAttributes.SPEED_COUNTER.getValue(e.player) != 0) {
				e.setCanceled(true);
				return true;
			}
			((EntityLivingBase) e.entity).hurtResistantTime = 0;
		}
		
		return false;
	}
	
	@SubscribeEvent
	public boolean hitEntity(HitEntityEvent e)
	{
		if (e.attacker instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e.attacker;
			
			if (ItemAttributes.KNOCKBACK.hasIt(e.stack)) {
				RPGCommonHelper.knockBack(e.entity, e.attacker, ItemAttributes.KNOCKBACK.get(e.stack, player));
			}
			
			e.entity.hurtResistantTime = 0;
			if (ItemAttributes.STR_MUL.hasIt(e.stack)) {
				e.entity.attackEntityFrom(DamageSource.causePlayerDamage(player),
					PlayerAttributes.STRENGTH.getValue(player) * ItemAttributes.STR_MUL.get(e.stack));
			}
			
			if (ItemAttributes.MELEE_SPEED.hasIt(e.stack)) {
				float speed = ItemAttributes.MELEE_SPEED.get(e.stack, player);
				PlayerAttributes.SPEED_COUNTER.setValue((int) (speed < 0 ? 0 : speed), player);
			}
		}
		
		return true;
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void addInformation(AddInformationEvent e)
	{
		if (LvlableItem.isLvlable(e.stack)) {
			e.list.add(EnumChatFormatting.GOLD + ItemAttributes.LEVEL.getDispayName().concat(": ") +
					 (int) ItemAttributes.LEVEL.get(e.stack));
			e.list.add(EnumChatFormatting.GRAY + ItemAttributes.CURR_EXP.getDispayName().concat(": ") +
					 (int) ItemAttributes.CURR_EXP.get(e.stack) + "/" + (int) ItemAttributes.MAX_EXP.get(e.stack));
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e)
	{
		if (e.phase == TickEvent.Phase.START && !e.player.worldObj.isRemote) {
			float tmp;
			if (e.player != null && (tmp = PlayerAttributes.SPEED_COUNTER.getValue(e.player)) > 0) {
				PlayerAttributes.SPEED_COUNTER.setValue(tmp - 1, e.player);
			}
			
			if (e.player.worldObj.getTotalWorldTime() % 20 == 0 &&
			    PlayerAttributes.CURR_MANA.getValue(e.player) < PlayerAttributes.MANA.getValue(e.player) &&
			    (tmp = PlayerAttributes.MANA_REGEN.getValue(e.player)) != 0) {
				PlayerAttributes.CURR_MANA.addValue(tmp, e.player);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerTickClient(TickEvent.PlayerTickEvent e)
	{
		if (e.phase == TickEvent.Phase.END) {
			if (e.player == null) {
				return;
			}
			
			if (e.player.swingProgressInt == 1) {
				ItemStack stack = e.player.getCurrentEquippedItem();
				if (stack != null && ItemAttributes.REACH.hasIt(stack)) {	
					MovingObjectPosition object = RPGCommonHelper.getMouseOver(0, ItemAttributes.REACH.get(stack) + 4);
					
					if (object != null && object.entityHit != null && object.entityHit != e.player && object.entityHit.hurtResistantTime == 0) {
						FMLClientHandler.instance().getClient().playerController.attackEntity(e.player, object.entityHit);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void breakSpeed(BreakSpeed e)
	{
		if (ForgeHooks.canToolHarvestBlock(e.block, e.metadata, e.entityPlayer.inventory.getCurrentItem())) {
			e.newSpeed += PlayerAttributes.EFFICIENCY.getValue(e.entityPlayer);
		}
	}
}
