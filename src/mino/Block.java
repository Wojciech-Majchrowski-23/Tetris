package mino;

import java.awt.*;

//klasa bloczek 30x30 (na sztywno) i o konkretnym kolorze (przekazywane do metody)

public class Block extends Rectangle {
    public int x, y;    //kazdy blok ma swoje wspolrzedne
    public static final int SIZE = 30;
    public Color c;

    public Block(Color c){
        this.c = c;
    }

    public void draw(Graphics2D g2){//ta sama funkcja do rysowania co w playmanagerze
        int margine = 2;
        g2.setColor(c);
        g2.fillRect(x + margine, y + margine, SIZE - (margine*2), SIZE - (margine*2));
    }
}
