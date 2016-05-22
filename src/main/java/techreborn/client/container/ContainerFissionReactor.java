package techreborn.client.container;

import net.minecraft.entity.player.EntityPlayer;
import reborncore.common.container.RebornContainer;

public class ContainerFissionReactor extends RebornContainer {
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
