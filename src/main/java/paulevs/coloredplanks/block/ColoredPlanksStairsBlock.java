package paulevs.coloredplanks.block;

import net.minecraft.block.Block;
import net.minecraft.level.BlockView;
import net.modificationstation.stationapi.api.template.block.TemplateStairsBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import paulevs.coloredplanks.listener.ClientListener;

public class ColoredPlanksStairsBlock extends TemplateStairsBlock {
	private final byte index;
	
	public ColoredPlanksStairsBlock(Identifier id, Block block, byte index) {
		super(id, block);
		setTranslationKey(id);
		setLightOpacity(0);
		setHardness(block.getHardness());
		setSounds(block.sounds);
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
