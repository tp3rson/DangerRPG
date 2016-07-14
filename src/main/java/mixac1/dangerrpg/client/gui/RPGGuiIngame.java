package mixac1.dangerrpg.client.gui;

import org.lwjgl.opengl.GL11;

import mixac1.dangerrpg.api.item.ILvlableItem;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemShoot;
import mixac1.dangerrpg.capability.PlayerData;
import mixac1.dangerrpg.capability.playerattr.PlayerAttributes;
import mixac1.dangerrpg.util.RPGCommonHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class RPGGuiIngame extends Gui
{
    public static Minecraft mc = Minecraft.getMinecraft();
    public static FontRenderer fr = mc.fontRenderer;
    public ScaledResolution res;
    
    public static final ResourceLocation TEXTURE = new ResourceLocation("DangerRPG:textures/gui/player_bar.png");
    
    private static int textureWidth = 139;
    private static int textureHeight = 59;
    
    private static int healthBarOffsetX = 54;
    private static int healthBarOffsetY = 22;
    private static int healthBarIndent = 10;
    private static int healthBarWidth = 81;
    private static int healthBarHeight = 5;
    private static int healthBarOffsetU = 175;
    private static int healthBarOffsetV = 0;
    
    private static int hungerIconOffsetX = 6;
    private static int hungerIconOffsetY = 54;
    private static int hungerBarOffsetX = hungerIconOffsetX + 2;
    private static int hungerBarOffsetY = hungerIconOffsetY + 11;
    private static int hungerBarIndent = 14;
    private static int hungerBarWidth = 5;
    private static int hungerBarHeight = 31;
    private static int hungerBarOffsetU = 175;
    private static int hungerBarOffsetV = 35;
    private static int hungerIconOffsetU = 195;
    private static int hungerIconOffsetV = 35;
    private static int hungerIconWidth = 9;
    private static int hungerIconHeight = 9;
    
    private static int line1OffsetX = 39;
    private static int line1OffsetY = 3;
    private static int line1Width = 101;
    private static int line1Height = 16;
    
    private static int line2OffsetX = 2;
    private static int line2OffsetY = 40;
    private static int line2Width = 34;
    private static int line2Height = 13;
    
    private static int chargeWidth = 101;
    private static int chargeHeight = 5;
    private static int chargeOffsetU = 0;
    private static int chargeOffsetV = 68;
    
    
    public void renderGameOverlay()
    {
        if (mc.playerController.gameIsSurvivalOrAdventure()) {
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            
            res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            int width = res.getScaledWidth();
            int height = res.getScaledHeight();
            
            renderPlayerBar(10, 10);
            renderChargeBar((width - chargeWidth) / 2, height - 40 - chargeHeight);
            
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_BLEND);
        }
    }
    
    private void renderPlayerBar(int offsetX, int offsetY)
    {
        GuiInventory.func_147046_a(offsetX + 18, offsetY + 37, 16, 30F, 00f, mc.thePlayer);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(TEXTURE); 
        drawTexturedModalRect(offsetX, offsetY, 0, 0, textureWidth, textureHeight);
        
        float currHealth = mc.thePlayer.getHealth();
        float absorbHealth = mc.thePlayer.getAbsorptionAmount();
        float maxHealth = mc.thePlayer.getMaxHealth() + absorbHealth;
        if (maxHealth > 0) {
            int procHealth = getProcent(currHealth, maxHealth, healthBarWidth);
            int procAbsorb = getProcent(absorbHealth, maxHealth, healthBarWidth);
            if (mc.thePlayer.isPotionActive(Potion.wither)) {
                drawTexturedModalRect(offsetX + healthBarOffsetX, offsetY + healthBarOffsetY, healthBarOffsetU, healthBarOffsetV + healthBarHeight * 3, healthBarWidth, healthBarHeight);
            }
            else {
                if (procHealth > 0) {
                    if (mc.thePlayer.isPotionActive(Potion.poison)) {
                        drawTexturedModalRect(offsetX + healthBarOffsetX, offsetY + healthBarOffsetY, healthBarOffsetU, healthBarOffsetV + healthBarHeight * 2, procHealth, healthBarHeight);
                    }    
                    else {
                        drawTexturedModalRect(offsetX + healthBarOffsetX, offsetY + healthBarOffsetY, healthBarOffsetU, healthBarOffsetV, procHealth, healthBarHeight);
                    }
                }
                if (procAbsorb > 0) {
                    drawTexturedModalRect(offsetX + healthBarOffsetX + procHealth, offsetY + healthBarOffsetY, healthBarOffsetU + procHealth, healthBarOffsetV + healthBarHeight, procAbsorb, healthBarHeight);
                }
            }
        }
        
        float currMana = PlayerAttributes.CURR_MANA.getValue(mc.thePlayer);
        float maxMana = PlayerAttributes.MANA.getValue(mc.thePlayer);
        if (maxMana > 0) {
            int procMana = getProcent(currMana, maxMana, healthBarWidth);
            if (procMana > 0) {
                drawTexturedModalRect(offsetX + healthBarOffsetX, offsetY + healthBarOffsetY + healthBarIndent, healthBarOffsetU, healthBarOffsetV + healthBarHeight * 4, procMana, healthBarHeight);
            }
        }
        
        float currArmor = RPGCommonHelper.calcTotalPhisicResistance(mc.thePlayer);
        int proc = getProcent(currArmor, 100F, healthBarWidth);
        if (proc > 0) {
            drawTexturedModalRect(offsetX + healthBarOffsetX, offsetY + healthBarOffsetY + healthBarIndent * 2, healthBarOffsetU, healthBarOffsetV + healthBarHeight * 5, proc, healthBarHeight);
        }
        
        currArmor = RPGCommonHelper.calcTotalMagicResistance(mc.thePlayer);
        proc = getProcent(currArmor, 100F, healthBarWidth);
        if (proc > 0) {
            drawTexturedModalRect(offsetX + healthBarOffsetX, offsetY + healthBarOffsetY + healthBarIndent * 3, healthBarOffsetU, healthBarOffsetV + healthBarHeight * 6, proc, healthBarHeight);
        }
        
        int level = mc.thePlayer.getFoodStats().getFoodLevel();
        proc = getProcent(level, 20F, hungerBarHeight);
        if (level < 20) {
            drawTexturedModalRect(offsetX + hungerBarOffsetX, offsetY + hungerBarOffsetY, hungerBarOffsetU, hungerBarOffsetV, hungerBarWidth, hungerBarHeight);
            if (mc.thePlayer.isPotionActive(Potion.hunger)) {
                drawTexturedModalRect(offsetX + hungerIconOffsetX, offsetY + hungerIconOffsetY, hungerIconOffsetU + hungerIconWidth, hungerIconOffsetV, hungerIconWidth, hungerIconHeight);
                drawTexturedModalRect(offsetX + hungerBarOffsetX, offsetY + hungerBarOffsetY, hungerBarOffsetU + hungerBarWidth * 2, hungerBarOffsetV, hungerBarWidth, proc);
            }
            else {
                drawTexturedModalRect(offsetX + hungerIconOffsetX, offsetY + hungerIconOffsetY, hungerIconOffsetU, hungerIconOffsetV, hungerIconWidth, hungerIconHeight);
                drawTexturedModalRect(offsetX + hungerBarOffsetX, offsetY + hungerBarOffsetY, hungerBarOffsetU + hungerBarWidth, hungerBarOffsetV, hungerBarWidth, proc);
            }
        }
        
        int air = mc.thePlayer.getAir();
        proc = getProcent(air, 300F, hungerBarHeight);
        if (air < 300) {
            drawTexturedModalRect(offsetX + hungerIconOffsetX + hungerBarIndent, offsetY + hungerIconOffsetY, hungerIconOffsetU + hungerIconWidth * 2, hungerIconOffsetV, hungerIconWidth, hungerIconHeight);
            drawTexturedModalRect(offsetX + hungerBarOffsetX + hungerBarIndent, offsetY + hungerBarOffsetY, hungerBarOffsetU, hungerBarOffsetV, hungerBarWidth, hungerBarHeight);
            drawTexturedModalRect(offsetX + hungerBarOffsetX + hungerBarIndent, offsetY + hungerBarOffsetY, hungerBarOffsetU + hungerBarWidth * 3, hungerBarOffsetV, hungerBarWidth, proc);
        }
        
        String s = fr.trimStringToWidth(mc.thePlayer.getDisplayName(), line1Width - 6);
        fr.drawStringWithShadow(s, offsetX + line1OffsetX + (line1Width - fr.getStringWidth(s)) / 2, offsetY + line1OffsetY + (line1Height - fr.FONT_HEIGHT) / 2, 0xFFFFFF);
        
        s = String.valueOf(PlayerData.get(mc.thePlayer).getLvl());
        fr.drawStringWithShadow(s, offsetX + line2OffsetX + (line2Width - fr.getStringWidth(s)) / 2, offsetY + line2OffsetY + (line2Height - fr.FONT_HEIGHT) / 2, 0xFFFFFF);
    }
    
    public void renderChargeBar(int offsetX, int offsetY)
    {  
        ItemStack stack;
        if ((stack = mc.thePlayer.getItemInUse()) != null && stack.getItemUseAction() == EnumAction.bow) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(TEXTURE); 
            drawTexturedModalRect(offsetX, offsetY, chargeOffsetU, chargeOffsetV, chargeWidth, chargeHeight);
            
            ILvlableItemShoot ilvl = (ILvlableItemShoot) (stack.getItem() instanceof ILvlableItemShoot ? stack.getItem() : ILvlableItem.DEFAULT_BOW);
            int useDuration = mc.thePlayer.getItemInUseDuration();
            float maxCharge = ilvl.getMaxCharge(stack, mc.thePlayer);        
            int proc = getProcent(useDuration, maxCharge, chargeWidth);
            if (proc > 0) {
                drawTexturedModalRect(offsetX, offsetY, chargeOffsetU, chargeOffsetV + chargeHeight, proc, chargeHeight);
            }
        }
    }
    
    private int getProcent(float curr, float max, int width)
    { 
        int value = (int) (curr /  max * width);    
        return value == 0 && curr != 0 ? 1 : value > width ? width : value;
    }
}
