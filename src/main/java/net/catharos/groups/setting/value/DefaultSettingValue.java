package net.catharos.groups.setting.value;

/**
 * Represents a EmptyPermissionValue
 */
public class DefaultSettingValue implements SettingValue {

    public static final DefaultSettingValue FALSE = new DefaultSettingValue(SettingState.FALSE);
    public static final DefaultSettingValue TRUE = new DefaultSettingValue(SettingState.TRUE);
    public static final DefaultSettingValue UNSET = new DefaultSettingValue(SettingState.UNSET);

    private final SettingState state;

    public DefaultSettingValue(SettingState state) {
        this.state = state;
    }

    @Override
    public byte[] binaryValue() {
        switch (state) {
            case TRUE:
                return new byte[]{1};
            case FALSE:
                return new byte[]{2};
            case UNSET:
                return new byte[]{3};
            default:
                throw new IllegalStateException("Invalid SettingState!");
        }
    }

    @Override
    public boolean booleanValue() {
        return state.getValue();
    }
}
