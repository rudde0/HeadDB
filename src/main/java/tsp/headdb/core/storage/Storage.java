package tsp.headdb.core.storage;

import tsp.headdb.HeadDB;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Storage {

    private final Executor executor;
    private final PlayerStorage playerStorage;

    public Storage(int threads) {
        executor = Executors.newFixedThreadPool(threads, HeadDBThreadFactory.FACTORY);
        validateDataDirectory();
        playerStorage = new PlayerStorage(HeadDB.getInstance(), this);
    }

    public PlayerStorage getPlayerStorage() {
        return playerStorage;
    }

    public Executor getExecutor() {
        return executor;
    }

    private void validateDataDirectory() {
        //noinspection ResultOfMethodCallIgnored
        new File(HeadDB.getInstance().getDataFolder(), "data").mkdir();
    }

}
