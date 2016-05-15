package techreborn.api.reactor.fission;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

/* Use this to add reactor coolants */
public class ReactorCoolant extends Fluid implements IReactorCoolant {
    private int boilingPoint;
    private int heatCapacity;

    public ReactorCoolant(String fluidName, ResourceLocation still, ResourceLocation flowing, int boilingPoint, int heatCapacity) {
        super(fluidName, still, flowing);
        this.heatCapacity = heatCapacity;
        this.boilingPoint = boilingPoint;
    }

    @Override
    public int getBoilingPoint() {
        return boilingPoint;
    }

    @Override
    public int getHeatCapacity() {
        return heatCapacity;
    }
}
