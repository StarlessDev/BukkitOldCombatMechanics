package kernitus.plugin.OldCombatMechanics.api;

public interface CombatSwitchService {

    boolean isGlobalSwitchEnabled();

    void setGlobalSwitchEnabled(final boolean value);

    void setGlobalModeset(final String modeset);
}
