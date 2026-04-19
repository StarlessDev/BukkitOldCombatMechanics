package kernitus.plugin.OldCombatMechanics.api.impl;

import kernitus.plugin.OldCombatMechanics.ModuleLoader;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.api.CombatSwitchService;
import kernitus.plugin.OldCombatMechanics.utilities.Config;

import java.util.Objects;

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
        if (isGlobalSwitchEnabled() == value) {
            return;
        }

        Config.setGlobalSwitchEnabled(value);
        broadcastModesetChange();
        saveConfig();
    }

    @Override
    public void setGlobalModeset(String modeset) {
        if (Objects.equals(Config.globalSwitchModeset(), modeset)) {
            return;
        }

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
