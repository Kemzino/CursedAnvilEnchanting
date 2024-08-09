package net.kemzino.cae.global;

public class ModVariables {
    private static ModVariables instance;

    private boolean isNetheriteAnvilOpen;

    private ModVariables() {
        this.isNetheriteAnvilOpen = false;
    }

    public static synchronized ModVariables getInstance() {
        if (instance == null) {
            instance = new ModVariables();
        }
        return instance;
    }

    public boolean getIsNetheriteAnvilOpen() {
        return this.isNetheriteAnvilOpen;
    }

    public void setIsNetheriteAnvilOpen(boolean isNetheriteAnvilOpen) {
        this.isNetheriteAnvilOpen = isNetheriteAnvilOpen;
    }
}
