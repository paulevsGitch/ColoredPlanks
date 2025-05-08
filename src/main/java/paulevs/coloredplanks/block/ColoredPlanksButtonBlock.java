package paulevs.coloredplanks.block;

import net.minecraft.level.BlockView;
import net.modificationstation.stationapi.api.template.block.TemplateButtonBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import paulevs.coloredplanks.listener.ClientListener;

public class ColoredPlanksButtonBlock extends TemplateButtonBlock {
	private final byte index;
	
	public ColoredPlanksButtonBlock(Identifier id, byte index) {
		super(id, 0);
		setTranslationKey(id);
		setHardness(PLANKS.getHardness() * 0.25F);
		setSounds(WOOD_SOUNDS);
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
