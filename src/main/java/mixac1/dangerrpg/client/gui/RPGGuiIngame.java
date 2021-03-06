package mixac1.dangerrpg.client.gui;

import org.lwjgl.opengl.GL11;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.IRPGEntity;
import mixac1.dangerrpg.capability.EntityAttributes;
import mixac1.dangerrpg.capability.RPGEntityHelper;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.PlayerAttributes;
import mixac1.dangerrpg.capability.data.RPGEntityProperties;
import mixac1.dangerrpg.client.gui.GuiMode.GuiModeType;
import mixac1.dangerrpg.hook.HookArmorSystem;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGConfig.ClientConfig;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;

public class RPGGuiIngame extends Gui
{
    public static final RPGGuiIngame INSTANCE = new RPGGuiIngame();

    public Minecraft mc = Minecraft.getMinecraft();
    public FontRenderer fr = mc.fontRenderer;

    public static final ResourceLocation TEXTURE = new ResourceLocation(DangerRPG.MODID, "textures/gui/gui_in_game.png");

    private int textureW = 139;
    private int textureH = 59;

    private int part1X;
    private int part1Y;
    private int part1U;
    private int part1V;
    private int part1Width;
    private int part1H;

    private int part2X;
    private int part2Y;
    private int part2U;
    private int part2V;
    private int part2W;
    private int part2H;

    private int part3X;
    private int part3Y;
    private int part3U;
    private int part3V;
    private int part3W;
    private int part3H;

    private int barIconX;
    private int barIconY;
    private int barIconW;
    private int barIconH;
    private int barIconU;
    private int barIconV;

    private int barX;
    private int barY;
    private int barW;
    private int barH;
    private int barU;
    private int barV;

    private int chargeW;
    private int chargeH;
    private int chargeU;
    private int chargeV;

    private GuiModeType mode;

    public RPGGuiIngame()
    {
        update(GuiMode.curr());
    }

    public void update(GuiModeType type)
    {
        mode = type;
        if (!mode.isSimple) {
            part1X = 0;
            part1Y = 0;
            part1U = 0;
            part1V = 0;
            part1Width = 36;
            part1H = 40;

            part2X = 38;
            part2Y = 2;
            part2U = 36;
            part2V = 0;
            part2W = 101;
            part2H = 16;

            part3X = 1;
            part3Y = 39;
            part3U = 36;
            part3V = 16;
            part3W = 34;
            part3H = 13;

            barIconX = 41;
            barIconY = 20;
            barIconW = 10;
            barIconH = 10;
            barIconU = 165;
            barIconV = 0;

            barX = barIconX + 12;
            barY = barIconY + 2;
            barW = 81;
            barH = 5;
            barU = 175;
            barV = 0;

            chargeW = 101;
            chargeH = 5;
            chargeU = 0;
            chargeV = 68;
        }
        else {
            part2X = part1X;
            part2Y = part1Y;
            part3X = part2W + 1;
            part3Y = part2Y + 1;

            barIconX = part1X;

            barX = barIconX + 12;
            barY = barIconY + 2;
        }
    }

    public void renderGameOverlay(ScaledResolution res)
    {
        mc.mcProfiler.startSection("rpgBar");
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        int width = res.getScaledWidth();
        int height = res.getScaledHeight();

        renderEntityBar(mc.thePlayer, ClientConfig.d.guiPlayerHUDOffsetX, ClientConfig.d.guiPlayerHUDOffsetY, ClientConfig.d.guiPlayerHUDIsInvert, res);
        renderChargeBar(ClientConfig.d.guiChargeIsCentered ? (width - chargeW) / 2 : ClientConfig.d.guiChargeOffsetX, height - ClientConfig.d.guiChargeOffsetY);
        renderEnemyBar(ClientConfig.d.guiEnemyHUDOffsetX, ClientConfig.d.guiEnemyHUDOffsetY, ClientConfig.d.guiEnemyHUDIsInvert, res);

        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        mc.mcProfiler.endSection();
    }

    private void renderEntityBar(EntityLivingBase entity, int offsetX, int offsetY, boolean isInverted, ScaledResolution res)
    {
        this.isInvert = isInverted;
        float tmpX, tmpY;

        if (isInverted) {
            offsetX = res.getScaledWidth() - offsetX;
        }

        if (!mode.isSimple) {
            GuiInventory.func_147046_a(offsetX + invert(18), offsetY + 37, 16, invert(30), 00f, entity);

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(TEXTURE);

            drawTexturedModalRect(offsetX + invert(part1X), offsetY + part1Y, part1U, part1V, part1Width, part1H, isInverted);
            drawTexturedModalRect(offsetX + invert(part2X), offsetY + part2Y, part2U, part2V, part2W, part2H, isInverted);
            drawTexturedModalRect(offsetX + invert(part3X), offsetY + part3Y, part3U, part3V, part3W, part3H, isInverted);
        }
        else {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(TEXTURE);

            drawTexturedModalRect(offsetX + invert(part2X), offsetY + part2Y, part2U, part2V, part2W, part2H, isInverted);
            drawTexturedModalRect(offsetX + invert(part3X), offsetY + part3Y, part3U, part3V, part3W, part3H, isInverted);
        }

        int yFal = 0, proc;
        float curr, max;
        String s;

        IRPGEntity iRPG = null;
        if (RPGEntityHelper.isRPGable(entity)) {
            RPGEntityProperties data = RPGEntityProperties.get(entity);
            if (data != null && data.checkValid()) {
                iRPG = RPGCapability.rpgEntityRegistr.get(entity).rpgComponent;
            }
        }

        if (!(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)) {

            boolean isPlayer = entity instanceof EntityPlayer;
            boolean hasHealth = true;
            boolean hasMana = isPlayer;
            boolean hasArmor = isPlayer;
            boolean hasMeleeDamage = iRPG != null && (iRPG.getEAMeleeDamage(entity) != null || isPlayer);
            boolean hasRangeDamage = iRPG != null && iRPG.getEARangeDamage(entity) != null;
            boolean hasFood = entity == mc.thePlayer && mc.thePlayer.getFoodStats().getFoodLevel() < 20;
            boolean hasAir = entity == mc.thePlayer && mc.thePlayer.getAir() < 300;

            int offsetHealth = 0, offsetMana = 0, offsetMeleeDmg = 0, offsetRangeDmg = 0;

            if (hasMana && hasHealth && ClientConfig.d.guiTwiceHealthManaBar) {
                drawTexturedModalRect(offsetX + invert(barIconX), offsetY + barIconY + yFal, barIconU, barIconV + barIconH * 2, barIconW, barIconH, isInverted);
                drawTexturedModalRect(offsetX + invert(barX), offsetY + barY + yFal - 2, barU, barV, barW, barH, isInverted);
                drawTexturedModalRect(offsetX + invert(barX), offsetY + barY + yFal + 2, barU, barV, barW, barH, isInverted);

                renderHealthBar(entity, offsetX, offsetY + yFal - 2, isInverted);
                renderManaBar(entity, offsetX, offsetY + yFal + 2, isInverted);

                offsetHealth = offsetMana = offsetY + barY + yFal;
                yFal += barIconH;
            }
            else {
                if (hasHealth) {
                    drawTexturedModalRect(offsetX + invert(barIconX), offsetY + barIconY + yFal, barIconU, barIconV + barIconH * 0, barIconW, barIconH, isInverted);
                    drawTexturedModalRect(offsetX + invert(barX), offsetY + barY + yFal, barU, barV, barW, barH, isInverted);
                    renderHealthBar(entity, offsetX, offsetY + yFal, isInverted);

                    offsetHealth = offsetY + barY + yFal;
                    yFal += barIconH;
                }
                if (hasMana) {
                    drawTexturedModalRect(offsetX + invert(barIconX), offsetY + barIconY + yFal, barIconU, barIconV + barIconH * 1, barIconW, barIconH, isInverted);
                    drawTexturedModalRect(offsetX + invert(barX), offsetY + barY + yFal, barU, barV, barW, barH, isInverted);
                    renderManaBar(entity, offsetX, offsetY + yFal, isInverted);

                    offsetMana = offsetY + barY + yFal;
                    yFal += barIconH;
                }
            }

            if (hasArmor) {
                drawTexturedModalRect(offsetX + invert(barIconX), offsetY + barIconY + yFal, barIconU, barIconV + barIconH * 3, barIconW, barIconH, isInverted);
                drawTexturedModalRect(offsetX + invert(barX), offsetY + barY + yFal - 2, barU, barV, barW, barH, isInverted);
                drawTexturedModalRect(offsetX + invert(barX), offsetY + barY + yFal + 2, barU, barV, barW, barH, isInverted);

                curr = HookArmorSystem.getTotalPhisicArmor();
                proc = getProcent(curr, 100F, barW);
                if (proc > 0) {
                    drawTexturedModalRect(offsetX + invert(barX), offsetY + barY + yFal - 2, barU, barV + barH * 6, proc, barH, isInverted);
                }
                else if (proc < 0) {
                    drawTexturedModalRect(offsetX + invert(barX), offsetY + barY + yFal - 2, barU, barV + barH * 4, -proc, barH, isInverted);
                }

                curr = HookArmorSystem.getTotalMagicArmor();
                proc = getProcent(curr, 100F, barW);
                if (proc > 0) {
                    drawTexturedModalRect(offsetX + invert(barX), offsetY + barY + yFal + 2, barU, barV + barH * 7, proc, barH, isInverted);
                }
                else if (proc < 0) {
                    drawTexturedModalRect(offsetX + invert(barX), offsetY + barY + yFal - 2, barU, barV + barH * 4, -proc, barH, isInverted);
                }

                yFal += barIconH;
            }

            if (hasFood && hasAir) {
                drawTexturedModalRect(offsetX + invert(barIconX), offsetY + barIconY + yFal, barIconU, barIconV + barIconH * 6, barIconW, barIconH, isInverted);
                if (mc.thePlayer.isPotionActive(Potion.hunger)) {
                    drawTexturedModalRect(offsetX + invert(barIconX), offsetY + barIconY + yFal, barIconU, barIconV + barIconH * 5, barIconW, barIconH, isInverted);
                }
                else {
                    drawTexturedModalRect(offsetX + invert(barIconX), offsetY + barIconY + yFal, barIconU, barIconV + barIconH * 4, barIconW, barIconH, isInverted);
                }
                renderFoodBar(entity, offsetX, offsetY + yFal - 2, isInverted);
                renderAirBar(entity, offsetX, offsetY + yFal + 2, isInverted);
                yFal += barIconH;
            }
            else {
                if (hasFood) {
                    if (mc.thePlayer.isPotionActive(Potion.hunger)) {
                        drawTexturedModalRect(offsetX + invert(barIconX), offsetY + barIconY + yFal, barIconU, barIconV + barIconH * 5, barIconW, barIconH, isInverted);
                    }
                    else {
                        drawTexturedModalRect(offsetX + invert(barIconX), offsetY + barIconY + yFal, barIconU, barIconV + barIconH * 4, barIconW, barIconH, isInverted);
                    }
                    renderFoodBar(entity, offsetX, offsetY + yFal, isInverted);
                    yFal += barIconH;
                }
                if (hasAir) {
                    drawTexturedModalRect(offsetX + invert(barIconX), offsetY + barIconY + yFal, barIconU, barIconV + barIconH * 6, barIconW, barIconH, isInverted);
                    renderAirBar(entity, offsetX, offsetY + yFal + 2, isInverted);
                    yFal += barIconH;
                }
            }

            if (hasMeleeDamage && mode.isDigital) {
                drawTexturedModalRect(offsetX + invert(barIconX), offsetY + barIconY + yFal, barIconU, barIconV + barIconH * 7, barIconW, barIconH, isInverted);

                offsetMeleeDmg = offsetY + barIconY + yFal;
                yFal += barIconH;
            }

            if (hasRangeDamage && mode.isDigital) {
                drawTexturedModalRect(offsetX + invert(barIconX), offsetY + barIconY + yFal, barIconU, barIconV + barIconH * 8, barIconW, barIconH, isInverted);

                offsetRangeDmg = offsetY + barIconY + yFal;
                yFal += barIconH;
            }

            if (mode.isDigital) {
                if (hasMana && hasHealth && ClientConfig.d.guiTwiceHealthManaBar) {
                    s = Utils.toString(genValueStr(entity.getHealth() + entity.getAbsorptionAmount()), "/", genValueStr(PlayerAttributes.CURR_MANA.getValue(entity)));
                    fr.drawStringWithShadow(s, offsetX + getOffsetX(s, barX + barW + 4, isInverted), offsetHealth + (barIconH - fr.FONT_HEIGHT) / 2 - 1, 0xFFFFFF);
                }
                else {
                    if (hasHealth) {
                        s = genValueStr(entity.getHealth() + entity.getAbsorptionAmount());
                        fr.drawStringWithShadow(s, offsetX + getOffsetX(s, barX + barW + 4, isInverted), offsetHealth + (barIconH - fr.FONT_HEIGHT) / 2 - 1, 0xFFFFFF);
                    }
                    if (hasMana) {
                        s = genValueStr(PlayerAttributes.CURR_MANA.getValue(entity));
                        fr.drawStringWithShadow(s, offsetX + getOffsetX(s, barX + barW + 4, isInverted), offsetMana + (barIconH - fr.FONT_HEIGHT) / 2 - 1, 0xFFFFFF);
                    }
                }
            }

            if (hasMeleeDamage && mode.isDigital) {
                s = genValueStr(iRPG.getEAMeleeDamage(entity).getValue(entity));
                fr.drawStringWithShadow(s, offsetX + getOffsetX(s, barX, isInverted), offsetMeleeDmg + (barIconH - fr.FONT_HEIGHT) / 2 + 1, 0xFFFFFF);
            }

            if (hasRangeDamage && mode.isDigital) {
                s = genValueStr(iRPG.getEARangeDamage(entity).getValue(entity));
                fr.drawStringWithShadow(s, offsetX + getOffsetX(s, barX, isInverted), offsetRangeDmg + (barIconH - fr.FONT_HEIGHT) / 2 + 1, 0xFFFFFF);
            }
        }

        // T0D0: name
        {
            s = entity.getCommandSenderName();
            fr.drawStringWithShadow(s, offsetX + getOffsetX(s, part2X + 1, part2W - 6, isInverted), offsetY + part2Y + (part2H - fr.FONT_HEIGHT) / 2 + 2, 0xFFFFFF);
        }

        // T0D0: lvl
        if (iRPG != null) {
            s = String.valueOf((int) EntityAttributes.LVL.getValue(entity));
            fr.drawStringWithShadow(s, offsetX + getOffsetX(s, part3X, part3W, isInverted), offsetY + part3Y + (part3H - fr.FONT_HEIGHT) / 2 + 1, 0xFFFFFF);
        }
    }

    private void renderHealthBar(EntityLivingBase entity, int offsetX, int offsetY, boolean isInverted)
    {
        float curr = entity.getHealth();
        float absorbHealth = entity.getAbsorptionAmount();
        float max = EntityAttributes.HEALTH.getSafe(entity, entity.getMaxHealth() + absorbHealth);
        if (max > 0) {
            int proc = getProcent(curr, max, barW);
            int procAbsorb = getProcent(absorbHealth, max, barW);
            if (entity.isPotionActive(Potion.wither)) {
                drawTexturedModalRect(offsetX + invert(barX), offsetY + barY, barU, barV + barH * 4, barW, barH, isInverted);
            }
            else {
                if (proc > 0) {
                    if (entity.isPotionActive(Potion.poison)) {
                        drawTexturedModalRect(offsetX + invert(barX), offsetY + barY, barU, barV + barH * 3, proc, barH, isInverted);
                    }
                    else {
                        drawTexturedModalRect(offsetX + invert(barX), offsetY + barY, barU, barV + barH * 1, proc, barH, isInverted);
                    }
                }
                if (procAbsorb > 0) {
                    drawTexturedModalRect(offsetX + invert(barX) + proc, offsetY + barY, barU + proc, barV + barH * 2, procAbsorb, barH, isInverted);
                }
            }
        }
    }

    private void renderManaBar(EntityLivingBase entity, int offsetX, int offsetY, boolean isInverted)
    {
        float curr = PlayerAttributes.CURR_MANA.getValue(entity);
        float max = PlayerAttributes.MANA.getValue(entity);
        if (max > 0) {
            int proc = getProcent(curr, max, barW);
            if (proc > 0) {
                drawTexturedModalRect(offsetX + invert(barX), offsetY + barY, barU, barV + barH * 5, proc, barH, isInverted);
            }
        }
    }

    private void renderFoodBar(EntityLivingBase entity, int offsetX, int offsetY, boolean isInverted)
    {
        float curr = mc.thePlayer.getFoodStats().getFoodLevel();
        int proc = getProcent(curr, 20F, barW);
        if (curr < 20) {
            drawTexturedModalRect(offsetX + invert(barX), offsetY + barY, barU, barV, barW, barH, isInverted);
            if (mc.thePlayer.isPotionActive(Potion.hunger)) {
                drawTexturedModalRect(offsetX + invert(barX), offsetY + barY, barU, barV + barH * 9, proc, barH, isInverted);
            }
            else {
                drawTexturedModalRect(offsetX + invert(barX), offsetY + barY, barU, barV + barH * 8, proc, barH, isInverted);
            }
        }
    }

    private void renderAirBar(EntityLivingBase entity, int offsetX, int offsetY, boolean isInverted)
    {
        float curr = mc.thePlayer.getAir();
        int proc = getProcent(curr, 300F, barW);
        if (curr < 300) {
            drawTexturedModalRect(offsetX + invert(barX), offsetY + barY, barU, barV, barW, barH, isInverted);
            drawTexturedModalRect(offsetX + invert(barX), offsetY + barY, barU, barV + barH * 10, proc, barH, isInverted);
        }
    }

    public void renderChargeBar(int offsetX, int offsetY)
    {
        ItemStack stack;
        if ((stack = mc.thePlayer.getItemInUse()) != null && stack.getItemUseAction() == EnumAction.bow && ItemAttributes.SHOT_SPEED.hasIt(stack)) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(TEXTURE);
            drawTexturedModalRect(offsetX, offsetY, chargeU, chargeV, chargeW, chargeH);

            int useDuration = mc.thePlayer.getItemInUseDuration();
            float maxCharge = ItemAttributes.SHOT_SPEED.get(stack, mc.thePlayer);
            float power = useDuration / maxCharge;
            power = (power * power + power * 2.0F) / 3.0F;
            int proc = getProcent(power, 1f, chargeW);
            if (proc > 0) {
                drawTexturedModalRect(offsetX, offsetY, chargeU, chargeV + chargeH, proc, chargeH);
            }

            if (ItemAttributes.MIN_CUST_TIME.hasIt(stack)) {
                float tmp = ItemAttributes.MIN_CUST_TIME.get(stack, mc.thePlayer);
                proc = getProcent(maxCharge * tmp, maxCharge, chargeW);
                drawTexturedModalRect(offsetX + proc, offsetY, chargeU, chargeV + chargeH * 2, 1, chargeH);
            }
        }
    }

    private void renderEnemyBar(int offsetX, int offsetY, boolean isInverted, ScaledResolution res)
    {
        MovingObjectPosition mop = RPGHelper.getMouseOver(0, 10);
        if (mop != null && mop.entityHit != null) {
            if (mop.entityHit instanceof EntityDragonPart) {
                if (((EntityDragonPart) mop.entityHit).entityDragonObj instanceof EntityDragon) {
                    renderEntityBar((EntityDragon) ((EntityDragonPart) mop.entityHit).entityDragonObj, offsetX, offsetY, true, res);
                }
            }
            else if (mop.entityHit instanceof EntityLivingBase) {
                renderEntityBar((EntityLivingBase) mop.entityHit, offsetX, offsetY, true, res);
            }
        }
    }

    private int getProcent(float curr, float max, int width)
    {
        curr = curr > max ? max : curr < -max ? -max : curr;
        return (int) Utils.alignment(curr /  max * width, -width, width);
    }

    private String genValueStr(float value)
    {
        if (value < 100) {
            return String.format("%.1f", value);
        }
        else if (value < 1000) {
            return String.format("%d", Math.round(value));
        }
        else if (value < 10000) {
            return String.format("%.2fK", Math.round(value) / 1000f);
        }
        else if (value < 100000) {
            return String.format("%.1fK", Math.round(value) / 1000f);
        }
        else if (value < 1000000) {
            return String.format("%dK", Math.round(value) / 1000);
        }
        else if (value < 10000) {
            return String.format("%.2fM", Math.round(value) / 1000000f);
        }
        else if (value < 100000) {
            return String.format("%.1fM", Math.round(value) / 1000000f);
        }
        else if (value < 1000000) {
            return String.format("%dM", Math.round(value) / 1000000);
        }
        else {
            return String.format("%dM", Math.round(value) / 1000000);
        }
    }

    private boolean isInvert = true;

    private double invert(double value)
    {
        return Utils.invert(value, isInvert);
    }

    private int invert(int value)
    {
        return (int) Utils.invert(value, isInvert);
    }

    private int getOffsetX(String s, int offset, int width, boolean isInverted)
    {
        s = fr.trimStringToWidth(s, width);
        int size = fr.getStringWidth(s);
        int value = (offset + (width - size) / 2);
        if (isInverted) {
            return - (value + size);
        }
        return value;
    }

    private int getOffsetX(String s, int offset, boolean isInverted)
    {
        if (isInverted) {
            int size = fr.getStringWidth(s);
            return - (offset + size);
        }
        return offset;
    }

    private void renderTestString(int x, int y, Object... str)
    {
        int i = 0;
        for (Object tmp : str) {
            fr.drawStringWithShadow(tmp.toString(), x, y + fr.FONT_HEIGHT * i++, 0xffffff);
        }
    }

    public void drawTexturedModalRect(int x, int y, int u, int v, int width, int heght, boolean isInverted)
    {
        float f = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();

        if (!isInverted) {
            tessellator.addVertexWithUV(x + 0,      y + heght,  this.zLevel, (u + 0) * f,       (v + heght) * f);
            tessellator.addVertexWithUV(x + width,  y + heght,  this.zLevel, (u + width) * f,   (v + heght) * f);
            tessellator.addVertexWithUV(x + width,  y + 0,      this.zLevel, (u + width) * f,   (v + 0) * f);
            tessellator.addVertexWithUV(x + 0,      y + 0,      this.zLevel, (u + 0) * f,       (v + 0) * f);
        }
        else {
            tessellator.addVertexWithUV(x - width,  y + heght,  this.zLevel, (u + width) * f,   (v + heght) * f);
            tessellator.addVertexWithUV(x + 0,      y + heght,  this.zLevel, (u + 0) * f,       (v + heght) * f);
            tessellator.addVertexWithUV(x + 0,      y + 0,      this.zLevel, (u + 0) * f,       (v + 0) * f);
            tessellator.addVertexWithUV(x - width,  y + 0,      this.zLevel, (u + width) * f,   (v + 0) * f);
        }

        tessellator.draw();
    }
}
