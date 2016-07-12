package mixac1.dangerrpg.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.GemType;
import mixac1.dangerrpg.client.gui.RPGGuiIngame;
import mixac1.dangerrpg.init.RPGConfig;
import mixac1.dangerrpg.init.RPGGuiHandlers;
import mixac1.dangerrpg.init.RPGKeyBinds;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.item.IUseItemExtra;
import mixac1.dangerrpg.network.MsgUseItemExtra;
import mixac1.dangerrpg.network.MsgUseItemSpecial;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

@SideOnly(Side.CLIENT)
public class ClientEventHandlers
{
    public static Minecraft mc = Minecraft.getMinecraft(); 
    private static RPGGuiIngame guiIngame = new RPGGuiIngame();
    
    @SubscribeEvent
    public void renderRPGGuiIngame(RenderGameOverlayEvent event)
    {
        if (!event.isCancelable() && event.type == ElementType.EXPERIENCE && RPGConfig.mainEnableModGui) {
            guiIngame.renderGameOverlay();
        }
    }
    
    @SubscribeEvent
    public void renderDisableOldBars(RenderGameOverlayEvent.Pre event)
    {
        if (RPGConfig.mainEnableModGui) {
            if (event.type == ElementType.HEALTH ||
                event.type == ElementType.ARMOR ||
                event.type == ElementType.FOOD ||
                event.type == ElementType.AIR) {
                event.setCanceled(true);
            }
        }
    }
    
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event)
	{
		if (Minecraft.getMinecraft().inGameHasFocus) {
			EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;

			if (player == null) {
				return;
			}

			ItemStack stack = player.getCurrentEquippedItem();
			if (RPGKeyBinds.specialItemKey.getIsKeyPressed() &&
				stack != null &&
				GemType.GEM_SPECIAL_ATTACK.hasIt(stack)) {
				RPGNetwork.net.sendToServer(new MsgUseItemSpecial());
			} 
			else if (RPGKeyBinds.extraItemKey.getIsKeyPressed() &&
					 stack != null &&
					 stack.getItem() instanceof IUseItemExtra) {
				RPGNetwork.net.sendToServer(new MsgUseItemExtra());
			}
			else if (RPGKeyBinds.infoBookKey.getIsKeyPressed()) {
				player.openGui(DangerRPG.instance, RPGGuiHandlers.GUI_INFO_BOOK, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
			}
		}
	}
}