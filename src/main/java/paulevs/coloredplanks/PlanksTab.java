package paulevs.coloredplanks;

import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;
import paulevs.bhcreative.api.CreativeTab;

public class PlanksTab extends CreativeTab {
	private final ItemStack[] preview = new ItemStack[16];
	private int addIndex;
	
	public PlanksTab(Identifier id) {
		super(id);
	}
	
	@Override
	public void addItem(ItemStack item) {
		super.addItem(item);
		if (addIndex == 16) return;
		preview[addIndex++] = item;
	}
	
	@Override
	public ItemStack getIcon() {
		int index = (int) (System.currentTimeMillis() / 1000) & 15;
		return preview[index];
	}
}
