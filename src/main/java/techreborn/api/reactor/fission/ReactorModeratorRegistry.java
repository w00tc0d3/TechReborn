package techreborn.api.reactor.fission;

import java.util.List;

public class ReactorModeratorRegistry {
    private static List<IReactorModerator> moderatorList;

    public static void addModerator(IReactorModerator moderator) {
        if(moderator == null)
            return;
        moderatorList.add(moderator);
    }

    public static List<IReactorModerator> getModeratorList() {
        return moderatorList;
    }

    public static boolean containsModerator(IReactorModerator moderator) {
        return moderator != null && moderatorList.contains(moderator);
    }
}
