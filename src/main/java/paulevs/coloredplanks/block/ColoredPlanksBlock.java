package paulevs.coloredplanks.block;

import net.minecraft.block.material.Material;
import net.minecraft.level.BlockView;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import paulevs.coloredplanks.listener.ClientListener;

public class ColoredPlanksBlock extends TemplateBlock {
	private final byte index;
	
	public ColoredPlanksBlock(Identifier id, byte index) {
		super(id, Material.WOOD);
		setTranslationKey(id);
		setSounds(PLANKS.sounds);
		setHardness(PLANKS.getHardness());
		this.index = index;
	}
	
	@Override
	public int getColorMultiplier(BlockView blockView, int x, int y, int z) {
		return ClientListener.COLORS[index];
	}
	
	@Override
	public int getBaseColor(int meta) {
		return ClientListener.COLORS[index];
	}
}
