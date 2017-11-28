package com.ferreusveritas.exampletrees.trees;

import com.ferreusveritas.dynamictrees.trees.DynamicTree;
import com.ferreusveritas.exampletrees.ExampleTrees;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class TreeIron extends DynamicTree {

	public TreeIron() {
		super(ExampleTrees.MODID, "iron", 0);
		
		//Immensely slow-growing, stocky tree that pulls trace amounts of iron from the dirt
		setBasicGrowingParameters(0.5f, 10.0f, getUpProbability(), getLowestBranchHeight(), 0.1f);

		//Set up primitive log. This controls what is dropped on harvest, block hardness, flammability, etc.
		IBlockState primLog = ExampleTrees.blocks.ironLog.getDefaultState();
		setPrimitiveLog(primLog, new ItemStack(primLog.getBlock()));
		
		//Set up primitive leaves. This controls what is dropped on shearing, branch support, leaves replacement, etc.
		IBlockState primLeaves = Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
		setPrimitiveLeaves(primLeaves, new ItemStack(primLeaves.getBlock(), 1, primLeaves.getValue(BlockColored.COLOR).getMetadata()));
		
		//Set the dynamic sapling
		setDynamicSapling(ExampleTrees.blocks.ironSapling.getDefaultState());
		
		//Let's pretend that iron trees have a hard time around water because of rust or something
		envFactor(Type.BEACH, 0.1f);
		envFactor(Type.WET, 0.25f);
		envFactor(Type.WATER, 0.25f);
		envFactor(Type.DRY, 1.05f);
	}
	
	@Override
	public boolean isBiomePerfect(Biome biome) {
		//Let's pretend that Dry Mesa biomes have a lot of iron in the clays that help these trees grow.
		return BiomeDictionary.hasType(biome, Type.MESA);
	}
	
	@Override
	public boolean isAcceptableSoil(IBlockState soilBlockState) {
		Block block = soilBlockState.getBlock();
		
		//Make the tree able to grow on sand and hardened clay so it can spawn in Mesa biomes
		if(block == Blocks.STAINED_HARDENED_CLAY || block == Blocks.HARDENED_CLAY || block == Blocks.SAND) {
			return true;
		}
		//Also make it able to grow on the traditional surfaces
		return super.isAcceptableSoil(soilBlockState);
	}
	
	/*@Override
	public void addJoCodes() {
		//Disable adding of JoCodes
	}*/
	
}