package org.societies.groups.setting;

import org.jetbrains.annotations.Nullable;
import org.societies.groups.setting.subject.Subject;
import org.societies.groups.setting.target.Target;

/**
 * Represents a VerifySetting
 */
public class DoubleSetting extends Setting<Double> {

    public DoubleSetting(String id) {
        super(id);
    }

    @Override
    public Double convert(Subject subject, Target target, byte[] value) {
        return java.nio.ByteBuffer.wrap(value).getDouble();
    }

    @Override
    public byte[] convert(Subject subject, Target target, @Nullable Double value) {
        if (value == null) {
            return new byte[0];
        }

        byte[] bytes = new byte[8];
        return java.nio.ByteBuffer.wrap(bytes).putDouble(value).array();
    }

    @Override
    public Double convertFromString(Subject subject, Target target, String value) throws SettingException {
        return Double.parseDouble(value);
    }

    @Override
    public String convertToString(Subject subject, Target target, @Nullable Double value) throws SettingException {
        if (value == null) {
            return "";
        }

        return value.toString();
    }
}
