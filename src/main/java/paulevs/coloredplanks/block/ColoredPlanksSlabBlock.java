package paulevs.coloredplanks.block;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.Block;
import net.minecraft.level.BlockView;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import paulevs.coloredplanks.listener.ClientListener;

public class ColoredPlanksSlabBlock extends TemplateBlock {
	private final Int2IntFunction textureGetter;
	private final byte index;
	
	public ColoredPlanksSlabBlock(Identifier id, Block source, byte index) {
		super(id, source.material);
		this.textureGetter = source::getTexture;
		setTranslationKey(id);
		setSounds(source.sounds);
		resistance = source.getHardness();
		hardness = source.getHardness();
		this.index = index;
	}
	
	@Override
	public int getTexture(int side) {
		return textureGetter.apply(side);
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
