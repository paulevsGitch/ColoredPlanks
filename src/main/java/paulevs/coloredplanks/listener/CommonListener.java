package paulevs.coloredplanks.listener;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeRegistry;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent.Vanilla;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.recipe.FuelRegistry;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.util.Identifier;
import paulevs.coloredplanks.ColoredPlanks;
import paulevs.coloredplanks.block.ColoredPlanksBlock;
import paulevs.coloredplanks.block.ColoredPlanksButtonBlock;
import paulevs.coloredplanks.block.ColoredPlanksFenceBlock;
import paulevs.coloredplanks.block.ColoredPlanksPressurePlateBlock;
import paulevs.coloredplanks.block.ColoredPlanksStairsBlock;
import paulevs.coloredplanks.block.SlabUtil;

import java.util.ArrayList;
import java.util.List;

public class CommonListener {
	public static final List<Block> BLOCKS = new ArrayList<>();
	
	@EventListener
	public void onBlockRegister(BlockRegistryEvent event) {
		String[] suffixes = new String[] {
			"_black",
			"_red",
			"_green",
			"_brown",
			"_blue",
			"_purple",
			"_cyan",
			"_light_gray",
			"_gray",
			"_pink",
			"_lime",
			"_yellow",
			"_light_blue",
			"_magenta",
			"_orange",
			"_white"
		};
		
		for (byte i = 0; i < 16; i++) {
			Identifier id = ColoredPlanks.id("colored_planks" + suffixes[i]);
			BLOCKS.add(new ColoredPlanksBlock(id, i));
		}
		
		for (byte i = 0; i < 16; i++) {
			Identifier id = ColoredPlanks.id("colored_planks_stairs" + suffixes[i]);
			BLOCKS.add(new ColoredPlanksStairsBlock(id, BLOCKS.get(i), i));
		}
		
		for (byte i = 0; i < 16; i++) {
			BLOCKS.add(SlabUtil.makeHalfSlab("colored_planks", suffixes[i], BLOCKS.get(i), i));
		}
		
		for (byte i = 0; i < 16; i++) {
			Identifier id = ColoredPlanks.id("colored_planks_fence" + suffixes[i]);
			BLOCKS.add(new ColoredPlanksFenceBlock(id, i));
		}
		
		for (byte i = 0; i < 16; i++) {
			Identifier id = ColoredPlanks.id("colored_planks_pressure_plate" + suffixes[i]);
			BLOCKS.add(new ColoredPlanksPressurePlateBlock(id, i));
		}
		
		for (byte i = 0; i < 16; i++) {
			Identifier id = ColoredPlanks.id("colored_planks_button" + suffixes[i]);
			BLOCKS.add(new ColoredPlanksButtonBlock(id, i));
		}
		
		/*for (Block block : BLOCKS) {
			Identifier id = BlockRegistry.INSTANCE.getId(block);
			System.out.println("\t\t\"" + id + "\",");
		}*/
	}
	
	@EventListener
	public void onRecipesRegister(RecipeRegisterEvent event) {
		if (event.recipeId == RecipeRegisterEvent.Vanilla.SMELTING.type()) {
			for (Block block :CommonListener.BLOCKS) {
				FuelRegistry.addFuelItem(block.asItem(), block.isFullCube() ? 300 : 100);
			}
			return;
		}
		
		if (event.recipeId == Vanilla.CRAFTING_SHAPED.type()) {
			List<Recipe> modRecipes = new ArrayList<>();
			List<Recipe> otherRecipes = new ArrayList<>();
			
			@SuppressWarnings("unchecked")
			List<Recipe> recipes = RecipeRegistry.getInstance().getRecipes();
			
			for (Recipe recipe : recipes) {
				Identifier id = ItemRegistry.INSTANCE.getId(recipe.getOutput().getType());
				if (id != null && id.namespace == ColoredPlanks.NAMESPACE) modRecipes.add(recipe);
				else otherRecipes.add(recipe);
			}
			
			for (int i = 0; i < modRecipes.size(); i++) {
				recipes.set(i, modRecipes.get(i));
			}
			
			for (int i = 0; i < otherRecipes.size(); i++) {
				recipes.set(i + modRecipes.size(), otherRecipes.get(i));
			}
		}
	}
}
