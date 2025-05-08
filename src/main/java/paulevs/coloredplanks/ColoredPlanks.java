package paulevs.coloredplanks;

import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;

public class ColoredPlanks {
	public static final Namespace NAMESPACE = Namespace.of("coloredplanks");
	
	public static Identifier id(String name) {
		return NAMESPACE.id(name);
	}
}
