package paulevs.coloredplanks.block;

import net.minecraft.block.Block;
import net.minecraft.level.BlockView;
import net.modificationstation.stationapi.api.util.Identifier;
import paulevs.coloredplanks.listener.ClientListener;
import paulevs.vbe.block.VBEHalfSlabBlock;

public class ColoredPlanksHalfSlabVBEBlock extends VBEHalfSlabBlock {
	private final byte index;
	
	public ColoredPlanksHalfSlabVBEBlock(Identifier id, Block source, byte index) {
		super(id, source);
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

