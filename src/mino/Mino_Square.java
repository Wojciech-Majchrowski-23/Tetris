package mino;

import java.awt.*;

public class Mino_Square extends Mino {

    public Mino_Square() {
        create(Color.yellow);  // Ustawienie koloru tetromino O na żółty
    }

    public void setXY(int x, int y) {  // Kształt kwadratu
        // o o
        // o o
        b[0].x = x;
        b[0].y = y;
        b[1].x = x + Block.SIZE;
        b[1].y = y;
        b[2].x = x;
        b[2].y = y + Block.SIZE;
        b[3].x = x + Block.SIZE;
        b[3].y = y + Block.SIZE;
    }
}
