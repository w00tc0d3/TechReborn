package techreborn.api.reactor.fission;

import java.util.List;

public class ReactorFuelRegistry {
    private static List<IReactorFuel> fuelList;

    public static void addFuel(IReactorFuel fuel) {
        if(fuel == null)
            return;
        fuelList.add(fuel);
    }

    public static List<IReactorFuel> getFuelList() {
        return fuelList;
    }

    public static boolean containsFuel(IReactorFuel fuel) {
        return fuel != null && fuelList.contains(fuel);
    }
}
