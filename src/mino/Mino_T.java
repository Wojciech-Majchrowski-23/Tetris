package mino;

import java.awt.*;

public class Mino_T extends Mino {

    public Mino_T() {
        create(Color.magenta);  // Setting the color of the T piece to magenta
    }

    public void setXY(int x, int y) {    // Defines the T shape with 4 blocks
        //
        //   o o o
        //     o
        b[0].x = x;                    // Center block
        b[0].y = y;
        b[1].x = b[0].x - Block.SIZE;  // Left block
        b[1].y = b[0].y;
        b[2].x = b[0].x + Block.SIZE;  // Right block
        b[2].y = b[0].y;
        b[3].x = b[0].x;               // Bottom block
        b[3].y = b[0].y + Block.SIZE;
    }

    public void getDirection1() { // First rotation
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x - Block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x + Block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y + Block.SIZE;
        updateXY(1);
    }

    public void getDirection2() { // Second rotation
        //   o
        //   o o
        //   o
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y + Block.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y - Block.SIZE;
        tempB[3].x = b[0].x + Block.SIZE;
        tempB[3].y = b[0].y;
        updateXY(2);
    }

    public void getDirection3() { // Third rotation
        //   o
        // o o o
        //
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x + Block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x - Block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y - Block.SIZE;
        updateXY(3);
    }

    public void getDirection4() { // Fourth rotation
        //   o
        // o o
        //   o
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x - Block.SIZE;
        tempB[3].y = b[0].y;
        updateXY(4);
    }
}
