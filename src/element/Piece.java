package element;

import java.util.Objects;

public class Piece {
    public char label, orientation; // 'H' or 'V'
    public int x, y, length;
    public Piece(char label, char orientation, int x, int y, int length) {
        this.label = label;
        this.orientation = orientation;
        this.x = x;
        this.y = y;
        this.length = length;
    }

    public int getHash() {
        return Objects.hash(label, orientation, x, y, length);
    }
    
    public int[] positionPlus() {
        return orientation == 'H'
            ? new int[] { x + length, y }
            : new int[] { x, y + length };
    }

    public int[] positionMinus() {
        return orientation == 'H'
            ? new int[] { x - 1, y }
            : new int[] { x, y - 1 };
    }

    public void movePlus() {
        if (orientation == 'H') {
            x += 1;
        } else if (orientation == 'V') {
            y += 1;
        }
    }

    public void moveMinus() {
        if (orientation == 'H') {
            x -= 1;
        } else if (orientation == 'V') {
            y -= 1;
        }
    }
    
    public Piece copy() {
        return new Piece(label, orientation, x, y, length);
    }
}