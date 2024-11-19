package mino;

import java.awt.*;

public class Mino_Bar extends Mino {

    public Mino_Bar() {
        create(Color.cyan);  // Ustawienie koloru tetromino I na cyan
    }

    public void setXY(int x, int y) {
        // Kształt początkowy (poziomy):
        // o o o o
        b[0].x = x;
        b[0].y = y;
        b[1].x = x - Block.SIZE;
        b[1].y = y;
        b[2].x = x + Block.SIZE;
        b[2].y = y;
        b[3].x = x + 2 * Block.SIZE;
        b[3].y = y;
    }

    public void getDirection1() {  // Pionowa
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x - Block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x + Block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x + 2 * Block.SIZE;
        tempB[3].y = b[0].y;
        updateXY(1);
    }

    public void getDirection2() {  // Pozioma
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y + 2 * Block.SIZE;
        updateXY(2);
    }

    public void getDirection3() {  // Powrót do pionowej
        getDirection1();
        updateXY(3);
    }

    public void getDirection4() {  // Powrót do poziomej
        getDirection2();
        updateXY(4);
    }
}


