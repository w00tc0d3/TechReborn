package techreborn.api.reactor.fission;

import net.minecraftforge.fluids.FluidRegistry;

import java.util.List;

public class ReactorCoolantRegistry {
    private static List<ReactorCoolant> coolantList;

    public static void addCoolant(ReactorCoolant fluid) {
        if(fluid == null)
            return;
        FluidRegistry.registerFluid(fluid);
        coolantList.add(fluid);
    }

    public static List<ReactorCoolant> getCoolantList() {
        return coolantList;
    }

    public static boolean containsCoolant(IReactorCoolant coolant) {
        return coolant != null && coolantList.contains(coolant);
    }
}
