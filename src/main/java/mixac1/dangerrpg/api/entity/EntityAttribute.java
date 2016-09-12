package mixac1.dangerrpg.api.entity;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.RPGEntityData;
import mixac1.dangerrpg.capability.RPGEntityData.TypeStub;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgSyncEA;
import mixac1.dangerrpg.util.ITypeProvider;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Default entity attribute. It supports any Type, but you must create {@link ITypeProvider} for this Type. <br>
 * {@link LvlEAProvider} allows make this attribute lvlable.
 */
public class EntityAttribute<Type>
{
    public final String name;
    public final int    hash;
    public final ITypeProvider<? super Type> typeProvider;

    public EntityAttribute(ITypeProvider<? super Type> typeProvider, String name)
    {
        this.name = name;
        this.hash = name.hashCode();
        this.typeProvider = typeProvider;
    }

    public void init(EntityLivingBase entity)
    {
        getEntityData(entity).attributeMap.put(hash, new TypeStub<Type>((Type) typeProvider.getEmpty()));
        LvlEAProvider lvlProvider = getLvlProvider(entity);
        if (lvlProvider != null) {
            lvlProvider.init(entity);
        }
    }

    public void serverInit(EntityLivingBase entity)
    {
        setValueRaw((Type) RPGCapability.rpgEntityRegistr.getAttributesSet(entity).attributes.get(this).startValue,
                    entity);
    }

    public LvlEAProvider getLvlProvider(EntityLivingBase entity)
    {
        return RPGCapability.rpgEntityRegistr.getAttributesSet(entity).attributes.get(this).lvlProvider;
    }

    public boolean hasIt(EntityLivingBase entity)
    {
        return RPGCapability.rpgEntityRegistr.isRegistered(entity)
               && RPGCapability.rpgEntityRegistr.getAttributesSet(entity).attributes.containsKey(this);
    }

    public boolean isValid(Type value)
    {
        return typeProvider.isValid(value);
    }

    public boolean isValid(Type value, EntityLivingBase entity)
    {
        return isValid(value);
    }

    public RPGEntityData getEntityData(EntityLivingBase entity)
    {
        return RPGEntityData.get(entity);
    }

    /**
     * Get value without check<br>
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before use this method
     */
    @Deprecated
    public Type getValueRaw(EntityLivingBase entity)
    {
        return (Type) getEntityData(entity).attributeMap.get(hash).value;
    }

    /**
     * Set value without check<br>
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before use this method
     */
    @Deprecated
    public boolean setValueRaw(Type value, EntityLivingBase entity)
    {
        if (!value.equals(getValueRaw(entity))) {
            getEntityData(entity).attributeMap.get(hash).value = value;
            return true;
        }
        return false;
    }

    /**
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before use this method
     */
    public Type getValue(EntityLivingBase entity)
    {
        Type value = getValueRaw(entity);
        if (!isValid(value, entity)) {
            serverInit(entity);
            value = getValueRaw(entity);
        }
        return value;
    }

    /**
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before use this method
     */
    public void setValue(Type value, EntityLivingBase entity)
    {
        if (isValid(value, entity)) {
            if (setValueRaw(value, entity)) {
                sync(entity);
            }
        }
    }

    /**
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before use this method
     */
    public void addValue(Type value, EntityLivingBase entity)
    {
        setValue((Type) typeProvider.concat(getValue(entity), value), entity);
    }

    public void sync(EntityLivingBase entity)
    {
        if (RPGEntityData.isServerSide(entity)) {
            RPGNetwork.net.sendToAll(new MsgSyncEA(this, entity));
        }
    }

    public void toNBT(NBTTagCompound nbt, EntityLivingBase entity)
    {
        NBTTagCompound tmp = new NBTTagCompound();
        typeProvider.toNBT(getValue(entity), "value", tmp);
        LvlEAProvider lvlProvider = getLvlProvider(entity);
        if (lvlProvider != null) {
            tmp.setInteger("lvl", lvlProvider.getLvl(entity));
        }
        nbt.setTag(name, tmp);
    }

    public void fromNBT(NBTTagCompound nbt, EntityLivingBase entity)
    {
        NBTTagCompound tmp = (NBTTagCompound) nbt.getTag(name);
        if (tmp != null) {
            setValueRaw((Type) typeProvider.fromNBT("value", tmp), entity);
            LvlEAProvider lvlProvider = getLvlProvider(entity);
            if (lvlProvider != null) {
                lvlProvider.setLvl(tmp.getInteger("lvl"), entity);
            }
        }
        else {
            serverInit(entity);
        }
    }

    public String getDisplayValue(EntityLivingBase entity)
    {
        return typeProvider.toString(getValue(entity));
    }

    public String getDisplayName()
    {
        return DangerRPG.trans("ea.".concat(name));
    }

    public String getInfo()
    {
        return DangerRPG.trans(Utils.toString("ea.", name, ".info"));
    }

    @Override
    public final int hashCode()
    {
        return hash;
    }

    /********************************************************************/
    //T0D0: implements of EntityAttribute for default types

    public static class EABoolean extends EntityAttribute<Boolean>
    {
        public EABoolean(String name)
        {
            super(ITypeProvider.BOOLEAN, name);
        }
    }

    public static class EAInteger extends EntityAttribute<Integer>
    {
        public EAInteger(String name)
        {
            super(ITypeProvider.INTEGER, name);
        }
    }

    public static class EAFloat extends EntityAttribute<Float>
    {
        public EAFloat(String name)
        {
            super(ITypeProvider.FLOAT, name);
        }
    }

    public static class EAString extends EntityAttribute<String>
    {
        public EAString(String name)
        {
            super(ITypeProvider.STRING, name);
        }
    }

    public static class EANBT extends EntityAttribute<NBTTagCompound>
    {
        public EANBT(String name)
        {
            super(ITypeProvider.NBT_TAG, name);
        }
    }

    public static class EAItemStack extends EntityAttribute<ItemStack>
    {
        public EAItemStack(String name)
        {
            super(ITypeProvider.ITEM_STACK, name);
        }
    }
}
