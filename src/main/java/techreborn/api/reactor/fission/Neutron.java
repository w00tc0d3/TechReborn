package techreborn.api.reactor.fission;

import techreborn.api.reactor.NeighborStack;

public class Neutron implements INeutron {
    boolean fastNeutron = false;
    NeighborStack.DIRECTIONS direction;

    public Neutron(boolean fastNeutron, NeighborStack.DIRECTIONS direction) {
        this.fastNeutron = fastNeutron;
        this.direction = direction;
    }

    @Override
    public boolean isFastNeutron() {
        return fastNeutron;
    }

    @Override
    public void setFastNeutron(boolean state) {
        this.fastNeutron = state;
    }

    @Override
    public NeighborStack.DIRECTIONS getDirection() {
        return direction;
    }

    @Override
    public void setDirection(NeighborStack.DIRECTIONS direction) {
        this.direction = direction;
    }
}
