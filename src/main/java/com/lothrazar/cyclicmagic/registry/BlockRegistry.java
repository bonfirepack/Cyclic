package com.lothrazar.cyclicmagic.registry;
import java.util.ArrayList;
import com.lothrazar.cyclicmagic.ModMain;
import com.lothrazar.cyclicmagic.block.BlockBucketStorage;
import com.lothrazar.cyclicmagic.block.BlockBuilder;
import com.lothrazar.cyclicmagic.block.BlockConveyor;
import com.lothrazar.cyclicmagic.block.BlockDimensionOre;
import com.lothrazar.cyclicmagic.block.BlockDimensionOre.SpawnType;
import com.lothrazar.cyclicmagic.block.BlockScaffolding;
import com.lothrazar.cyclicmagic.block.BlockSprout;
import com.lothrazar.cyclicmagic.block.BlockUncrafting;
import com.lothrazar.cyclicmagic.item.ItemSproutSeeds;
import com.lothrazar.cyclicmagic.item.itemblock.ItemBlockScaffolding;
import com.lothrazar.cyclicmagic.util.Const;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockRegistry {
  public static ArrayList<Block> blocks = new ArrayList<Block>();
  public static BlockScaffolding block_fragile;
  public static BlockUncrafting uncrafting_block;
  public static BlockBucketStorage block_storelava;
  public static BlockBucketStorage block_storewater;
  public static BlockBucketStorage block_storemilk;
  public static BlockBucketStorage block_storeempty;
  public static BlockDimensionOre nether_gold_ore;
  public static BlockDimensionOre nether_coal_ore;
  public static BlockDimensionOre nether_lapis_ore;
  public static BlockDimensionOre nether_emerald_ore;
  public static BlockDimensionOre end_redstone_ore;
  public static BlockDimensionOre end_coal_ore;
  public static BlockDimensionOre end_lapis_ore;
  public static BlockDimensionOre end_emerald_ore;
  public static BlockDimensionOre nether_diamond_ore;
  public static BlockDimensionOre end_diamond_ore;
  private static boolean spawnersUnbreakable;
  public static BlockBuilder builder_block;
  //lots of helpers/overrides with defaults
  public static void registerBlock(Block b, String name) {
    registerBlock(b, name, false);
  }
  private static void registerBlock(Block b, String name, boolean isHidden) {
    registerBlock(b, new ItemBlock(b), name, isHidden);
  }
  private static void registerBlock(Block b, ItemBlock ib, String name) {
    registerBlock(b, ib, name, false);
  }
  public static void registerBlock(Block b, ItemBlock ib, String name, boolean isHidden) {
    b.setRegistryName(name);
    b.setUnlocalizedName(name);
    GameRegistry.register(b);
    ib.setRegistryName(b.getRegistryName());
    GameRegistry.register(ib);
    if (isHidden == false) {
      b.setCreativeTab(ModMain.TAB);
    }
    blocks.add(b);
  }
  public static void register() {
    if (spawnersUnbreakable) {
      Blocks.MOB_SPAWNER.setBlockUnbreakable();
    }
    BlockConveyor plate_push = new BlockConveyor(0.16F, SoundEvents.BLOCK_ANVIL_BREAK);
    registerBlock(plate_push, "plate_push");
    plate_push.addRecipe();
    registerBlock(block_fragile, new ItemBlockScaffolding(block_fragile), BlockScaffolding.name);
    block_fragile.addRecipe();
    registerSprout();
    registerDimensionOres();
  }

  private static void registerDimensionOres() {
    //nether ores
    nether_gold_ore = new BlockDimensionOre(Items.GOLD_NUGGET, 0, 4);
    nether_gold_ore.setSpawnType(SpawnType.SILVERFISH, 1);
    registerBlock(nether_gold_ore, "nether_gold_ore");
    nether_coal_ore = new BlockDimensionOre(Items.COAL);
    nether_coal_ore.setSpawnType(SpawnType.SILVERFISH, 1);
    registerBlock(nether_coal_ore, "nether_coal_ore");
    nether_lapis_ore = new BlockDimensionOre(Items.DYE, EnumDyeColor.BLUE.getDyeDamage(), 3);
    nether_lapis_ore.setSpawnType(SpawnType.SILVERFISH, 2);
    registerBlock(nether_lapis_ore, "nether_lapis_ore");
    nether_emerald_ore = new BlockDimensionOre(Items.EMERALD);
    nether_emerald_ore.setSpawnType(SpawnType.SILVERFISH, 5);
    registerBlock(nether_emerald_ore, "nether_emerald_ore");
    nether_diamond_ore = new BlockDimensionOre(Items.DIAMOND);
    nether_diamond_ore.setSpawnType(SpawnType.SILVERFISH, 8);
    registerBlock(nether_diamond_ore, "nether_diamond_ore");
    //end ores
    end_redstone_ore = new BlockDimensionOre(Items.REDSTONE);
    end_redstone_ore.setSpawnType(SpawnType.ENDERMITE, 3);
    registerBlock(end_redstone_ore, "end_redstone_ore");
    end_coal_ore = new BlockDimensionOre(Items.COAL);
    end_coal_ore.setSpawnType(SpawnType.ENDERMITE, 1);
    registerBlock(end_coal_ore, "end_coal_ore");
    end_lapis_ore = new BlockDimensionOre(Items.DYE, EnumDyeColor.BLUE.getDyeDamage(), 3);
    end_lapis_ore.setSpawnType(SpawnType.ENDERMITE, 5);
    registerBlock(end_lapis_ore, "end_lapis_ore");
    end_emerald_ore = new BlockDimensionOre(Items.EMERALD);
    end_emerald_ore.setSpawnType(SpawnType.ENDERMITE, 8);
    registerBlock(end_emerald_ore, "end_emerald_ore");
    end_diamond_ore = new BlockDimensionOre(Items.DIAMOND);
    end_diamond_ore.setSpawnType(SpawnType.ENDERMITE, 8);
    registerBlock(end_diamond_ore, "end_diamond_ore");
  }
  
  private static void registerSprout() {
    BlockSprout sprout = new BlockSprout();
    registerBlock(sprout, "sprout", true);
    ItemRegistry.sprout_seed = new ItemSproutSeeds(sprout, Blocks.FARMLAND);
    ItemRegistry.sprout_seed.setUnlocalizedName("sprout_seed");
    ItemRegistry.registerItem(ItemRegistry.sprout_seed, "sprout_seed");
    ItemRegistry.itemMap.put("sprout_seed", ItemRegistry.sprout_seed);
    GameRegistry.addRecipe(new ItemStack(ItemRegistry.sprout_seed),
        "waw",
        "bEc",
        "wdw",
        'w', Items.WHEAT_SEEDS,
        'E', Items.EMERALD,
        'a', Items.BEETROOT_SEEDS,
        'b', Items.MELON_SEEDS,
        'c', Items.PUMPKIN_SEEDS,
        'd', Items.NETHER_WART);
  }
  public static void construct() {
    uncrafting_block = new BlockUncrafting();
    builder_block = new BlockBuilder();
    block_fragile = new BlockScaffolding();
  }
  public static void syncConfig(Configuration config) {
    String category = Const.ConfigCategory.blocks;
    spawnersUnbreakable = config.getBoolean("Spawners Unbreakable", category, true, "Make mob spawners unbreakable");
 
    builder_block.syncConfig(config);
  }
}
