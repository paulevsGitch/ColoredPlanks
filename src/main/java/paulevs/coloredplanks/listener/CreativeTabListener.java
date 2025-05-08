package paulevs.coloredplanks.listener;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemStack;
import paulevs.bhcreative.registry.TabRegistryEvent;
import paulevs.coloredplanks.ColoredPlanks;
import paulevs.coloredplanks.PlanksTab;

public class CreativeTabListener {
	@EventListener
	public void registerTab(TabRegistryEvent event) {
		PlanksTab tab = new PlanksTab(ColoredPlanks.id("tab"));
		CommonListener.BLOCKS.forEach(block -> tab.addItem(new ItemStack(block)));
		event.register(tab);
	}
}
