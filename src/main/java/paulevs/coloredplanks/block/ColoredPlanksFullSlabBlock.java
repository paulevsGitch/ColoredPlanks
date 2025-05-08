package paulevs.coloredplanks.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager.Builder;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ColoredPlanksFullSlabBlock extends ColoredPlanksSlabBlock {
	private Block halfBlock;
	
	public ColoredPlanksFullSlabBlock(Identifier id, Block source, byte index) {
		super(id, source, index);
		disableAutoItemRegistration();
	}
	
	@Override
	public void appendProperties(Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(SlabUtil.AXIS);
	}
	
	public void setHalfBlock(Block halfBlock) {
		this.halfBlock = halfBlock;
	}
	
	@Override
	public List<ItemStack> getDropList(Level level, int x, int y, int z, BlockState state, int meta) {
		return Collections.singletonList(new ItemStack(halfBlock, 2));
	}
	
	@Override
	public void doesBoxCollide(Level level, int x, int y, int z, Box box, ArrayList list) {
		this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
		super.doesBoxCollide(level, x, y, z, box, list);
	}
}
