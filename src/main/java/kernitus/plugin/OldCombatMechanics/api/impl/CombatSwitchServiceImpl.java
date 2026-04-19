package kernitus.plugin.OldCombatMechanics.api.impl;

import kernitus.plugin.OldCombatMechanics.ModuleLoader;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.api.CombatSwitchService;
import kernitus.plugin.OldCombatMechanics.utilities.Config;

public class CombatSwitchServiceImpl implements CombatSwitchService {

    private final OCMMain main;

    public CombatSwitchServiceImpl(OCMMain main) {
        this.main = main;
    }

    @Override
    public boolean isGlobalSwitchEnabled() {
        return Config.globalSwitchEnabled();
    }

    @Override
    public void setGlobalSwitchEnabled(boolean value) {
        Config.setGlobalSwitchEnabled(value);
        broadcastModesetChange();
        saveConfig();
    }

    @Override
    public void setGlobalModeset(String modeset) {
        Config.setGlobalModeset(modeset);
        if (isGlobalSwitchEnabled()) {
            broadcastModesetChange();
        }

        saveConfig();
    }

    private void broadcastModesetChange() {
        main.getServer().getOnlinePlayers().forEach(player -> {
            ModuleLoader.getModules().forEach(module -> module.onModesetChange(player));
        });
    }

    private void saveConfig() {
        main.getServer().getScheduler().runTaskAsynchronously(main, main::saveConfig);
    }
}
