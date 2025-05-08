package paulevs.coloredplanks.listener;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.DyeItem;
import net.modificationstation.stationapi.api.client.event.color.block.BlockColorsRegisterEvent;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;
import net.modificationstation.stationapi.api.util.Identifier;
import paulevs.coloredplanks.ColoredPlanks;

public class ClientListener {
	public static final int[] COLORS = new int[16];
	
	private static void initColors() {
		if (COLORS[0] == 0) {
			for (byte i = 0; i < 16; i++) {
				COLORS[i] = i == 7 ? 0xC0C0C0 : DyeItem.COLORS[i];
			}
		}
	}
	
	@EventListener
	public void onBlockColorsRegister(BlockColorsRegisterEvent event) {
		initColors();
		for (byte i = 0; i < CommonListener.BLOCKS.size(); i++) {
			final int color = COLORS[i & 15];
			Block block = CommonListener.BLOCKS.get(i);
			event.blockColors.registerColorProvider((state, world, pos, tintIndex) -> color, block);
		}
	}
	
	@EventListener
	public void onItemColorsRegister(ItemColorsRegisterEvent event) {
		initColors();
		for (byte i = 0; i < CommonListener.BLOCKS.size(); i++) {
			final int color = COLORS[i & 15];
			Block block = CommonListener.BLOCKS.get(i);
			event.itemColors.register((stack, tintIndex) -> color, block);
		}
	}
	
	@EventListener
	public void onTextureRegister(TextureRegisterEvent event) {
		ExpandableAtlas blockAtlas = Atlases.getTerrain();
		Identifier id = ColoredPlanks.id("block/planks");
		int index = blockAtlas.addTexture(id).index;
		for (Block block : CommonListener.BLOCKS) block.texture = index;
	}
}
