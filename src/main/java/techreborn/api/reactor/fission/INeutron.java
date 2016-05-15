package techreborn.api.reactor.fission;

import techreborn.api.reactor.NeighborStack;

public interface INeutron {
    boolean isFastNeutron();
    void setFastNeutron(boolean state);
    NeighborStack.DIRECTIONS getDirection();
    void setDirection(NeighborStack.DIRECTIONS direction);
}
