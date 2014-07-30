package net.catharos.groups.setting;

/**
 * Represents a Setting
 */
public class Setting {

    private final int id;
    private final String description;

    public Setting(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public boolean implies(Setting permission) {
        return false;
    }

    public String getDescription() {
        return description;
    }

    public int getID() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Setting setting = (Setting) o;

        return id == setting.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
