package paulevs.coloredplanks.block;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;
import paulevs.coloredplanks.ColoredPlanks;

public class SlabUtil {
	public static final EnumProperty<Direction> DIRECTION = EnumProperty.of("direction", Direction.class);
	public static final EnumProperty<Axis> AXIS = EnumProperty.of("axis", Axis.class);
	private static final boolean HAS_VBE = FabricLoader.getInstance().isModLoaded("vbe");
	private static Block halfSlab;
	private static Block fullSlab;
	
	public static Block makeHalfSlab(String name, String suffix, Block source, byte index) {
		if (HAS_VBE) makeVBESlabs(name, suffix, source, index);
		else makeDefaultSlabs(name, suffix, source, index);
		return halfSlab;
	}
	
	private static void makeDefaultSlabs(String name, String suffix, Block source, byte index) {
		halfSlab = new ColoredPlanksHalfSlabBlock(ColoredPlanks.id(name + "_slab_half" + suffix), source, index);
		fullSlab = new ColoredPlanksFullSlabBlock(ColoredPlanks.id(name + "_slab_full" + suffix), source, index);
		((ColoredPlanksHalfSlabBlock) halfSlab).setFullBlock(fullSlab);
		((ColoredPlanksFullSlabBlock) fullSlab).setHalfBlock(halfSlab);
	}
	
	private static void makeVBESlabs(String name, String suffix, Block source, byte index) {
		halfSlab = new ColoredPlanksHalfSlabVBEBlock(ColoredPlanks.id(name + "_slab_half" + suffix), source, index);
		fullSlab = new ColoredPlanksFullSlabVBEBlock(ColoredPlanks.id(name + "_slab_full" + suffix), source, index);
		((ColoredPlanksHalfSlabVBEBlock) halfSlab).setFullBlock(fullSlab);
		((ColoredPlanksFullSlabVBEBlock) fullSlab).setHalfBlock(halfSlab);
	}
}
