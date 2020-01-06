package DiceWars;

import java.util.Objects;

public class Coordinates {

    //region Variables

    private int x;
    private int y;

    //endregion

    //region Constructor

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //endregion

    //region Getter

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    //endregion

    //region Override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return this.getX() == that.getX() &&
                this.getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getX(), this.getY());
    }

    //endregion
}
