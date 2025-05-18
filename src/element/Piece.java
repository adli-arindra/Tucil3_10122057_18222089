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

        public void movePlus() {
            if (orientation == 'H') {
                y += 1;
            } else if (orientation == 'V') {
                x += 1;
            }
        }

        public void moveMinus() {
            if (orientation == 'H') {
                y -= 1;
            } else if (orientation == 'V') {
                x -= 1;
            }
        }
    }
