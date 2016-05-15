package techreborn.api.reactor;

/* Helper to find adjacent stack. */
public class NeighborStack {
    public enum DIRECTIONS {
        NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST
    };

    public static DIRECTIONS convertNumberToDirection(int n) {
        switch(n) {
            case 0:
                return DIRECTIONS.NORTH;
            case 1:
                return DIRECTIONS.NORTH_EAST;
            case 2:
                return DIRECTIONS.EAST;
            case 3:
                return DIRECTIONS.SOUTH_EAST;
            case 4:
                return DIRECTIONS.SOUTH;
            case 5:
                return DIRECTIONS.SOUTH_WEST;
            case 6:
                return DIRECTIONS.WEST;
            case 7:
                return DIRECTIONS.NORTH_WEST;
            default:
                return null;
        }
    }

    // return slot = slotNumber if there is no adjacent item
    public static int getAdjacentSlotNumber(int slotNumber, int width, DIRECTIONS direction) {
        int slot = 0;
        switch(direction) {
            case NORTH:
                slot = slotNumber - width;
            case NORTH_EAST:
                slot = slotNumber - (width - 1);
            case EAST:
                slot = slotNumber + 1;
            case SOUTH_EAST:
                slot = slotNumber + (width + 1);
            case SOUTH:
                slot = slotNumber + width;
            case SOUTH_WEST:
                slot = slotNumber + (width - 1);
            case WEST:
                slot = slotNumber - 1;
            case NORTH_WEST:
                slot = slotNumber - (width + 1);
        }
        if(slot < 0)
            slot = slotNumber;
        return slot;
    }
}
