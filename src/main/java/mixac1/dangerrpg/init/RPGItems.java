package mixac1.dangerrpg.init;

import cpw.mods.fml.common.registry.GameRegistry;
import mixac1.dangerrpg.item.ItemSniperBow;
import mixac1.dangerrpg.item.RPGArmorMaterial;
import mixac1.dangerrpg.item.RPGItemBow;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.item.TestWand;
import mixac1.dangerrpg.item.armor.ItemMageArmor;
import mixac1.dangerrpg.item.armor.RPGItemArmor;
import mixac1.dangerrpg.item.gem.GemWeaponWitherSkull;
import mixac1.dangerrpg.item.tool.RPGAxe;
import mixac1.dangerrpg.item.tool.RPGHoe;
import mixac1.dangerrpg.item.tool.RPGMultiTool;
import mixac1.dangerrpg.item.tool.RPGPickaxe;
import mixac1.dangerrpg.item.tool.RPGSpade;
import mixac1.dangerrpg.item.tool.RPGWeapon;
import mixac1.dangerrpg.item.tool.RPGWeaponKnife;
import mixac1.dangerrpg.item.tool.RPGWeaponTomahawk;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public abstract class RPGItems
{
    public static Item swordTraining = new RPGWeapon(RPGToolMaterial.WOOD, RPGItemComponent.TRAINING, "sword_training");

    public static Item swordObsidian     = new RPGWeapon(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.SWORD);
    public static Item swordBedrock      = new RPGWeapon(RPGToolMaterial.BEDROCK,      RPGItemComponent.SWORD);
    public static Item swordBlackMatter  = new RPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.SWORD);
    public static Item swordWhiteMatter  = new RPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.SWORD);

    public static Item naginataIron        = new RPGWeapon(RPGToolMaterial.IRON,         RPGItemComponent.NAGINATA);
    public static Item naginataGold        = new RPGWeapon(RPGToolMaterial.GOLD,         RPGItemComponent.NAGINATA);
    public static Item naginataDiamond     = new RPGWeapon(RPGToolMaterial.DIAMOND,      RPGItemComponent.NAGINATA);
    public static Item naginataObsidian    = new RPGWeapon(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.NAGINATA);
    public static Item naginataBedrock     = new RPGWeapon(RPGToolMaterial.BEDROCK,      RPGItemComponent.NAGINATA);
    public static Item naginataBlackMatter = new RPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.NAGINATA);
    public static Item naginataWhiteMatter = new RPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.NAGINATA);

    public static Item katanaIron        = new RPGWeapon(RPGToolMaterial.IRON,            RPGItemComponent.KATANA);
    public static Item katanaGold        = new RPGWeapon(RPGToolMaterial.GOLD,            RPGItemComponent.KATANA);
    public static Item katanaDiamond     = new RPGWeapon(RPGToolMaterial.DIAMOND,         RPGItemComponent.KATANA);
    public static Item katanaObsidian    = new RPGWeapon(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.KATANA);
    public static Item katanaBedrock     = new RPGWeapon(RPGToolMaterial.BEDROCK,      RPGItemComponent.KATANA);
    public static Item katanaBlackMatter = new RPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.KATANA);
    public static Item katanaWhiteMatter = new RPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.KATANA);

    public static Item scytheIron        = new RPGWeapon(RPGToolMaterial.IRON,         RPGItemComponent.SCYTHE);
    public static Item scytheGold        = new RPGWeapon(RPGToolMaterial.GOLD,         RPGItemComponent.SCYTHE);
    public static Item scytheDiamond     = new RPGWeapon(RPGToolMaterial.DIAMOND,      RPGItemComponent.SCYTHE);
    public static Item scytheObsidian    = new RPGWeapon(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.SCYTHE);
    public static Item scytheBedrock     = new RPGWeapon(RPGToolMaterial.BEDROCK,      RPGItemComponent.SCYTHE);
    public static Item scytheBlackMatter = new RPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.SCYTHE);
    public static Item scytheWhiteMatter = new RPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.SCYTHE);

    public static Item hammerIron        = new RPGWeapon(RPGToolMaterial.IRON,         RPGItemComponent.HAMMER);
    public static Item hammerGold        = new RPGWeapon(RPGToolMaterial.GOLD,         RPGItemComponent.HAMMER);
    public static Item hammerDiamond     = new RPGWeapon(RPGToolMaterial.DIAMOND,      RPGItemComponent.HAMMER);
    public static Item hammerObsidian    = new RPGWeapon(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.HAMMER);
    public static Item hammerBedrock     = new RPGWeapon(RPGToolMaterial.BEDROCK,      RPGItemComponent.HAMMER);
    public static Item hammerBlackMatter = new RPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.HAMMER);
    public static Item hammerWhiteMatter = new RPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.HAMMER);

    public static Item tomahawkIron        = new RPGWeaponTomahawk(RPGToolMaterial.IRON,         RPGItemComponent.TOMAHAWK);
    public static Item tomahawkGold        = new RPGWeaponTomahawk(RPGToolMaterial.GOLD,         RPGItemComponent.TOMAHAWK);
    public static Item tomahawkDiamond     = new RPGWeaponTomahawk(RPGToolMaterial.DIAMOND,      RPGItemComponent.TOMAHAWK);
    public static Item tomahawkObsidian    = new RPGWeaponTomahawk(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.TOMAHAWK);
    public static Item tomahawkBedrock     = new RPGWeaponTomahawk(RPGToolMaterial.BEDROCK,      RPGItemComponent.TOMAHAWK);
    public static Item tomahawkBlackMatter = new RPGWeaponTomahawk(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.TOMAHAWK);
    public static Item tomahawkWhiteMatter = new RPGWeaponTomahawk(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.TOMAHAWK);

    public static Item knifeIron        = new RPGWeaponKnife(RPGToolMaterial.IRON,         RPGItemComponent.KNIFE);
    public static Item knifeGold        = new RPGWeaponKnife(RPGToolMaterial.GOLD,         RPGItemComponent.KNIFE);
    public static Item knifeDiamond     = new RPGWeaponKnife(RPGToolMaterial.DIAMOND,      RPGItemComponent.KNIFE);
    public static Item knifeObsidian    = new RPGWeaponKnife(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.KNIFE);
    public static Item knifeBedrock     = new RPGWeaponKnife(RPGToolMaterial.BEDROCK,      RPGItemComponent.KNIFE);
    public static Item knifeBlackMatter = new RPGWeaponKnife(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.KNIFE);
    public static Item knifeWhiteMatter = new RPGWeaponKnife(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.KNIFE);

    public static Item axeObsidian     = new RPGAxe(RPGToolMaterial.OBSIDIAN);
    public static Item axeBedrock      = new RPGAxe(RPGToolMaterial.BEDROCK);
    public static Item axeBlackMatter  = new RPGAxe(RPGToolMaterial.BLACK_MATTER);
    public static Item axeWhiteMatter  = new RPGAxe(RPGToolMaterial.WHITE_MATTER);

    public static Item hoeObsidian     = new RPGHoe(RPGToolMaterial.OBSIDIAN);
    public static Item hoeBedrock      = new RPGHoe(RPGToolMaterial.BEDROCK);
    public static Item hoeBlackMatter  = new RPGHoe(RPGToolMaterial.BLACK_MATTER);
    public static Item hoeWhiteMatter  = new RPGHoe(RPGToolMaterial.WHITE_MATTER);

    public static Item pickaxeObsidian     = new RPGPickaxe(RPGToolMaterial.OBSIDIAN);
    public static Item pickaxeBedrock      = new RPGPickaxe(RPGToolMaterial.BEDROCK);
    public static Item pickaxeBlackMatter  = new RPGPickaxe(RPGToolMaterial.BLACK_MATTER);
    public static Item pickaxeWhiteMatter  = new RPGPickaxe(RPGToolMaterial.WHITE_MATTER);

    public static Item shovelObsidian     = new RPGSpade(RPGToolMaterial.OBSIDIAN);
    public static Item shovelBedrock      = new RPGSpade(RPGToolMaterial.BEDROCK);
    public static Item shovelBlackMatter  = new RPGSpade(RPGToolMaterial.BLACK_MATTER);
    public static Item shovelWhiteMatter  = new RPGSpade(RPGToolMaterial.WHITE_MATTER);

    public static Item multitoolIron        = new RPGMultiTool(RPGToolMaterial.IRON);
    public static Item multitoolGold        = new RPGMultiTool(RPGToolMaterial.GOLD);
    public static Item multitoolDiamond     = new RPGMultiTool(RPGToolMaterial.DIAMOND);
    public static Item multitoolObsidian    = new RPGMultiTool(RPGToolMaterial.OBSIDIAN);
    public static Item multitoolBedrock     = new RPGMultiTool(RPGToolMaterial.BEDROCK);
    public static Item multitoolBlackMatter = new RPGMultiTool(RPGToolMaterial.BLACK_MATTER);
    public static Item multitoolWhiteMatter = new RPGMultiTool(RPGToolMaterial.WHITE_MATTER);

    public static Item[] armorObsidian    = RPGItemArmor.createFullSet(RPGArmorMaterial.OBSIDIAN,     "armor");
    public static Item[] armorBedrock     = RPGItemArmor.createFullSet(RPGArmorMaterial.BEDROCK,      "armor");
    public static Item[] armorBlackMatter = RPGItemArmor.createFullSet(RPGArmorMaterial.BLACK_MATTER, "armor");
    public static Item[] armorWhiteMatter = RPGItemArmor.createFullSet(RPGArmorMaterial.WHITE_MATTER, "armor");

    public static Item[] mageArmorCloth       = ItemMageArmor.createFullSet(RPGArmorMaterial.CLOTH,        "mage_armor");
    public static Item[] mageArmorIron        = ItemMageArmor.createFullSet(RPGArmorMaterial.IRON,         "mage_armor");
    public static Item[] mageArmorGold        = ItemMageArmor.createFullSet(RPGArmorMaterial.GOLD,         "mage_armor");
    public static Item[] mageArmorDiamond     = ItemMageArmor.createFullSet(RPGArmorMaterial.DIAMOND,      "mage_armor");
    public static Item[] mageArmorObsidian    = ItemMageArmor.createFullSet(RPGArmorMaterial.OBSIDIAN,     "mage_armor");
    public static Item[] mageArmorBedrock     = ItemMageArmor.createFullSet(RPGArmorMaterial.BEDROCK,      "mage_armor");
    public static Item[] mageArmorBlackMatter = ItemMageArmor.createFullSet(RPGArmorMaterial.BLACK_MATTER, "mage_armor");
    public static Item[] mageArmorWhiteMatter = ItemMageArmor.createFullSet(RPGArmorMaterial.WHITE_MATTER, "mage_armor");

    public static Item shadowBow = new RPGItemBow   (RPGItemComponent.SHADOW_BOW, 3F);
    public static Item sniperBow = new ItemSniperBow(RPGItemComponent.SNIPER_BOW, 4F);

    public static Item gemWitherSkull = new GemWeaponWitherSkull("gem_wither_skull");

    public static Item testWand = new TestWand("test_wand");


    public static void load()
    {
        registerItems();
        addRecipes();
    }

    private static void registerItems()
    {
        registerItem(testWand);

        registerItem(swordTraining);

        registerItem(knifeIron);
        registerItem(tomahawkIron);
        registerItem(katanaIron);
        registerItem(naginataIron);
        registerItem(scytheIron);
        registerItem(hammerIron);
        registerItem(multitoolIron);

        registerItem(knifeGold);
        registerItem(tomahawkGold);
        registerItem(katanaGold);
        registerItem(naginataGold);
        registerItem(scytheGold);
        registerItem(hammerGold);
        registerItem(multitoolGold);

        registerItem(knifeDiamond);
        registerItem(tomahawkDiamond);
        registerItem(katanaDiamond);
        registerItem(naginataDiamond);
        registerItem(scytheDiamond);
        registerItem(hammerDiamond);
        registerItem(multitoolDiamond);

        registerItem(knifeObsidian);
        registerItem(tomahawkObsidian);
        registerItem(swordObsidian);
        registerItem(katanaObsidian);
        registerItem(naginataObsidian);
        registerItem(scytheObsidian);
        registerItem(hammerObsidian);
        registerItem(axeObsidian);
        registerItem(shovelObsidian);
        registerItem(pickaxeObsidian);
        registerItem(hoeObsidian);
        registerItem(multitoolObsidian);

        registerItem(knifeBedrock);
        registerItem(tomahawkBedrock);
        registerItem(swordBedrock);
        registerItem(katanaBedrock);
        registerItem(naginataBedrock);
        registerItem(scytheBedrock);
        registerItem(hammerBedrock);
        registerItem(axeBedrock);
        registerItem(shovelBedrock);
        registerItem(pickaxeBedrock);
        registerItem(hoeBedrock);
        registerItem(multitoolBedrock);

        registerItem(knifeBlackMatter);
        registerItem(tomahawkBlackMatter);
        registerItem(swordBlackMatter);
        registerItem(katanaBlackMatter);
        registerItem(naginataBlackMatter);
        registerItem(scytheBlackMatter);
        registerItem(hammerBlackMatter);
        registerItem(axeBlackMatter);
        registerItem(shovelBlackMatter);
        registerItem(pickaxeBlackMatter);
        registerItem(hoeBlackMatter);
        registerItem(multitoolBlackMatter);

        registerItem(knifeWhiteMatter);
        registerItem(tomahawkWhiteMatter);
        registerItem(swordWhiteMatter);
        registerItem(katanaWhiteMatter);
        registerItem(naginataWhiteMatter);
        registerItem(scytheWhiteMatter);
        registerItem(hammerWhiteMatter);
        registerItem(axeWhiteMatter);
        registerItem(shovelWhiteMatter);
        registerItem(pickaxeWhiteMatter);
        registerItem(hoeWhiteMatter);
        registerItem(multitoolWhiteMatter);

        registerItem(shadowBow);
        registerItem(sniperBow);

        registerItemArray(armorObsidian);
        registerItemArray(armorBedrock);
        registerItemArray(armorBlackMatter);
        registerItemArray(armorWhiteMatter);

        registerItemArray(mageArmorCloth);
        registerItemArray(mageArmorIron);
        registerItemArray(mageArmorGold);
        registerItemArray(mageArmorDiamond);
        registerItemArray(mageArmorObsidian);
        registerItemArray(mageArmorBedrock);
        registerItemArray(mageArmorBlackMatter);
        registerItemArray(mageArmorWhiteMatter);

        registerItem(gemWitherSkull);
    }

    private static void addRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(swordBlackMatter, 1), new Object[]{ " # ", " # ", " X ",('X'), Items.blaze_rod, ('#'), Items.iron_ingot});
    }

    private static void registerItem(Item item)
    {
        GameRegistry.registerItem(item, item.getUnlocalizedName());
    }

    private static void registerItemArray(Item[] array)
    {
        for (Item item : array) {
            registerItem(item);
        }
    }

    public static String getRPGName(RPGToolComponent toolComponent, RPGToolMaterial toolMaterial)
    {
        return Utils.toString(toolComponent.name, "_", toolMaterial.name);
    }

    public static String getRPGName(RPGArmorMaterial armorMaterial)
    {
        return Utils.toString("_", armorMaterial.name);
    }
}
