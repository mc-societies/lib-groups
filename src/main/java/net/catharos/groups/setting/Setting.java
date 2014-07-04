package net.catharos.groups.setting;

/**
 * Represents a Setting
 */
public class Setting {

    private final String name, description;

    public Setting(String name) {this(name, "");}

    public Setting(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public boolean implies(Setting permission) {
        return false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Setting that = (Setting) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
