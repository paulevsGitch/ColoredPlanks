package paulevs.coloredplanks.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.technical.PressurePlateTrigger;
import net.minecraft.level.BlockView;
import net.modificationstation.stationapi.api.template.block.TemplatePressurePlateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import paulevs.coloredplanks.listener.ClientListener;

public class ColoredPlanksPressurePlateBlock extends TemplatePressurePlateBlock {
	private final byte index;
	
	public ColoredPlanksPressurePlateBlock(Identifier id, byte index) {
		super(id, 0, PressurePlateTrigger.LIGHT, Material.WOOD);
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
