package team.devblook.shrimp.home;

import java.util.Objects;

public final class Home {
    private final String name;
    private final HomePosition position;

    public Home(String name, HomePosition position) {
        this.name = name;
        this.position = position;
    }

    public String name() {
        return name;
    }

    public HomePosition position() {
        return position;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Home) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position);
    }

    @Override
    public String toString() {
        return "Home[" +
                "name=" + name + ", " +
                "position=" + position + ']';
    }
}
