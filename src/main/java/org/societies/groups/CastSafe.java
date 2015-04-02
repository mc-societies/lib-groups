package org.societies.groups;

import com.google.common.base.Preconditions;

/**
 * Represents a helper for safe casting of objects.
 */
public final class CastSafe {

    private CastSafe() {
        // Private constructor
    }

    @SuppressWarnings("unchecked")
    public static <T> T toGeneric(Object obj) {
        Preconditions.checkNotNull(obj, "The object must not be null!");

        return (T) obj;
    }

    public static <T> T toGeneric(Object obj, T defaultValue) {
        try {
            return toGeneric(obj);

        } catch (ClassCastException ignored) {
            return defaultValue;

        } catch (IllegalArgumentException ignored) {
            return defaultValue;
        }
    }

    public static int toInt(Object obj) {
        if (!(obj instanceof Integer)) {
            throwCastError(obj, Integer.class.getName());
        }

        return (Integer) obj;
    }

    public static int toInt(Object obj, int defaultValue) {
        try {
            return toInt(obj);

        } catch (ClassCastException ignored) {
            return defaultValue;
        }
    }

    public static long toLong(Object obj) {
        if (!(obj instanceof Long)) {
            throwCastError(obj, Long.class.getName());
        }

        return (Long) obj;
    }

    public static long toLong(Object obj, long defaultValue) {
        try {
            return toLong(obj);

        } catch (ClassCastException ignored) {
            return defaultValue;
        }
    }

    public static char toChar(Object obj) {
        if (!(obj instanceof Character)) {
            throwCastError(obj, Character.class.getName());
        }

        return (Character) obj;
    }

    public static byte toByte(Object obj) {
        if (!(obj instanceof Byte)) {
            throwCastError(obj, Byte.class.getName());
        }

        return (Byte) obj;
    }

    public static byte toByte(Object obj, byte defaultValue) {
        try {
            return toByte(obj);

        } catch (ClassCastException ignored) {
            return defaultValue;
        }
    }

    public static float toFloat(Object obj) {
        if (!(obj instanceof Float)) {
            throwCastError(obj, Float.class.getName());
        }

        return (Float) obj;
    }

    public static float toFloat(Object obj, float defaultValue) {
        try {
            return toFloat(obj);

        } catch (ClassCastException ignored) {
            return defaultValue;
        }
    }

    public static int[] toIntArray(Object obj) {
        if (!(obj instanceof int[])) {
            throwCastError(obj, "an integer array");
        }

        return (int[]) obj;
    }

    public static int[] toIntArray(Object obj, int[] defaultValue) {
        try {
            return toIntArray(obj);

        } catch (ClassCastException ignored) {
            return defaultValue;
        }
    }

    public static byte[] toByteArray(Object obj) {
        if (!(obj instanceof byte[])) {
            throwCastError(obj, "a byte array");
        }

        return (byte[]) obj;
    }

    public static byte[] toByteArray(Object obj, byte[] defaultValue) {
        try {
            return toByteArray(obj);

        } catch (ClassCastException ignored) {
            return defaultValue;
        }
    }

    public static short[] toShortArray(Object obj) {
        if (!(obj instanceof short[])) {
            throwCastError(obj, "a short array");
        }

        return (short[]) obj;
    }

    public static short[] toShortArray(Object obj, short[] defaultValue) {
        try {
            return toShortArray(obj);

        } catch (ClassCastException ignored) {
            return defaultValue;
        }
    }

    /**
     * Helper to throw a cast error using the given object.
     *
     * @param object The object
     * @param error  The exception message suffix
     */
    private static void throwCastError(Object object, String error) {
        if (object == null) {
            throw new ClassCastException("Cannot cast NULL object to " + error);
        }

        throw new ClassCastException("Failed to cast " + object.getClass().getCanonicalName() + " to " + error);
    }

}
