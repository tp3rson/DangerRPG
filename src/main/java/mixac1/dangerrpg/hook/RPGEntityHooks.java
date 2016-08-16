package mixac1.dangerrpg.hook;

import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import mixac1.dangerrpg.api.event.ItemStackEvent.HitEntityEvent;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.ea.PlayerAttributes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class RPGEntityHooks
{
    @Hook(injectOnExit = true, targetMethod = "<clinit>")
    public static void SharedMonsterAttributes(SharedMonsterAttributes attributes)
    {
        ((BaseAttribute) SharedMonsterAttributes.attackDamage).setShouldWatch(true);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void attackTargetEntityWithCurrentItem(EntityPlayer player, Entity entity)
    {
        if (MinecraftForge.EVENT_BUS.post(new AttackEntityEvent(player, entity))) {
            return;
        }

        ItemStack stack = player.getCurrentEquippedItem();
        if (stack != null) {
            if (stack.getItem().onLeftClickEntity(stack, player, entity)) {
                return;
            }
        }

        if (entity.canAttackWithItem() && !entity.hitByEntity(player)) {
            if (stack != null && LvlableItem.isLvlable(stack)) {
                if (entity instanceof EntityLivingBase) {
                    if (PlayerAttributes.SPEED_COUNTER.getValue(player) != 0) {
                        return;
                    }
                }
            }

            float dmg = (float) player.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            float knockback = 0;
            float moreDmg = 0.0F;

            if (entity instanceof EntityLivingBase) {
                moreDmg = EnchantmentHelper.getEnchantmentModifierLiving(player, (EntityLivingBase)entity);
                knockback += EnchantmentHelper.getKnockbackModifier(player, (EntityLivingBase)entity);
            }

            if (player.isSprinting()) {
                ++knockback;
            }

            if (dmg > 0.0F || moreDmg > 0.0F) {
                boolean crit = player.fallDistance > 0.0F
                            && !player.onGround
                            && !player.isOnLadder()
                            && !player.isInWater()
                            && !player.isPotionActive(Potion.blindness)
                            && player.ridingEntity == null
                            && entity instanceof EntityLivingBase;

                if (crit && dmg > 0.0F) {
                    dmg *= 1.5F;
                }

                dmg += moreDmg;

                boolean isFire = false;
                int fire = EnchantmentHelper.getFireAspectModifier(player);
                if (entity instanceof EntityLivingBase && fire > 0 && !entity.isBurning()) {
                    isFire = true;
                    entity.setFire(1);
                }

                if (stack != null && entity instanceof EntityLivingBase) {
                    HitEntityEvent event = new HitEntityEvent(stack, (EntityLivingBase) entity, player, dmg, knockback, false);
                    MinecraftForge.EVENT_BUS.post(event);
                    dmg = event.damage;
                    knockback = event.knockback;
                }

                if (entity.attackEntityFrom(DamageSource.causePlayerDamage(player), dmg)) {
                    if (knockback > 0) {
                        entity.addVelocity(-MathHelper.sin(player.rotationYaw * (float)Math.PI / 180.0F) * knockback * 0.5F,
                                            0.1D,
                                            MathHelper.cos(player.rotationYaw * (float)Math.PI / 180.0F) * knockback * 0.5F);
                        player.motionX *= 0.6D;
                        player.motionZ *= 0.6D;
                        player.setSprinting(false);
                    }

                    if (crit) {
                        player.onCriticalHit(entity);
                    }

                    if (moreDmg > 0.0F) {
                        player.onEnchantmentCritical(entity);
                    }

                    if (dmg >= 18.0F) {
                        player.triggerAchievement(AchievementList.overkill);
                    }

                    player.setLastAttacker(entity);

                    if (entity instanceof EntityLivingBase) {
                        EnchantmentHelper.func_151384_a((EntityLivingBase)entity, player);
                    }

                    EnchantmentHelper.func_151385_b(player, entity);
                    ItemStack itemstack = player.getCurrentEquippedItem();
                    Object object = entity;

                    if (entity instanceof EntityDragonPart) {
                        IEntityMultiPart ientitymultipart = ((EntityDragonPart)entity).entityDragonObj;

                        if (ientitymultipart != null && ientitymultipart instanceof EntityLivingBase) {
                            object = ientitymultipart;
                        }
                    }

                    if (itemstack != null && object instanceof EntityLivingBase) {
                        itemstack.hitEntity((EntityLivingBase)object, player);

                        if (itemstack.stackSize <= 0) {
                            player.destroyCurrentEquippedItem();
                        }
                    }

                    if (entity instanceof EntityLivingBase) {
                        player.addStat(StatList.damageDealtStat, Math.round(dmg * 10.0F));

                        if (fire > 0) {
                            entity.setFire(fire * 4);
                        }
                    }

                    player.addExhaustion(0.3F);
                }
                else if (isFire) {
                    entity.extinguish();
                }
            }
        }
    }
}