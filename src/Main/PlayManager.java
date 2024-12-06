package Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import mino.Block;
import mino.Mino;
import mino.Mino_L1;
import mino.Mino_L2;
import mino.Mino_T;
import mino.Mino_Bar;
import mino.Mino_Square;
import mino.Mino_Z1;
import mino.Mino_Z2;


//tutaj:
//rysujemy przestrzen w ktorej odbywa sie gra
//zarzadzamy tetrominami
//tu sie dzieja akce w grze, np.
//usuwanie dolnej linii, dodawanie wyniku, obracanie figur (?)

public class PlayManager {

    //Main play area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    //Mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;

    //rozszerzalna tablica blokow, z ktorych kazdy ma: wspolrzedna x, wspolrzedna y, kolor i rozmiar
    public static ArrayList<Block> staticBlocks = new ArrayList<>();
    boolean gameOver = false;

    int level = 1;
    int lines = 0;
    int score = 0;
    int singleLineScore;

    HighscoreTable hst = new HighscoreTable();
    String username;

    //inne
    public static int dropInterval = 40;    //jedno mino spada o jedna dlugosc bloku co 30 klatek, czyli pol sekundy

    public PlayManager() {

        while(username == null){
            Scanner sc = new Scanner(System.in);
            username = sc.nextLine();
        }

        left_x = GamePanel.WIDTH/2 - WIDTH/2;
        right_x = left_x + WIDTH;
        top_y= 50;
        bottom_y = top_y + HEIGHT;

        //ustawienie tego, gdzie tetromino maja sie respic
        MINO_START_X = left_x + WIDTH/2 - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;

        //ustawianie reczne pierwszego tetromino
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);

        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
    }

    private Mino pickMino(){
        Mino mino = null;
        int i = new Random().nextInt(7);

        switch(i){
            case 0: mino = new Mino_L1(); break;
            case 1: mino = new Mino_L2(); break;
            case 2: mino = new Mino_T(); break;
            case 3: mino = new Mino_Z1(); break;
            case 4: mino = new Mino_Z2(); break;
            case 5: mino = new Mino_Square(); break;
            case 6: mino = new Mino_Bar(); break;
        }

        return mino;
    }

    public void update(){
        if(!currentMino.active){
            //przekazywanie blokow danego tetromino do statycznych blokow
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            if(currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y){
                gameOver = true;

                hst.checkAndUpdateHighscoreTable(username, score);
            }

            currentMino.deactivating = false;

            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

            //w momencie kiedy tetromino osiadzie sprawdzamy czy usuwamy linie
            checkDelete();
        }
        else{
            currentMino.update();
            //***************************
            if(currentMino.bottomPressed){
                score++;
                currentMino.bottomPressed = false;
            }
            //***************************
        }
    }

    public void checkDelete(){

        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int linesCount = 0;

        while(x < right_x && y < bottom_y){

            for(int i = 0; i < staticBlocks.size(); i++){
                if(staticBlocks.get(i).x == x && staticBlocks.get(i).y == y){
                    //nie wiemy w jakiej kolejnosci sa poukladane bloki
                    //wiec niech samo ogarnie i jesli znajdziemy 12 blokow ktore
                    //ma aktualne x i y to to te ktorych szukamy
                    blockCount++;
                }
            }

            x+=Block.SIZE;

            if(x == right_x){

                if(blockCount == 12){
                    for(int i = staticBlocks.size() - 1; i >= 0; i--){
                        //trzeba "sciagac" bloki z gory, bo inaczej wraz z usuwaniem od pierwszego elementu
                        //cala arraylist sie przesuwa w dol, czyli elementy zmieniaja swoj indeks
                        if(staticBlocks.get(i).y == y){
                            staticBlocks.remove(i);
                        }
                    }
                    lines++;
                    //to liczy wszystkie linie w calej grze
                    linesCount++;
                    //to dobija liczy ile lini podczas jednego usuwania

                    if(lines % 8 == 0 && dropInterval > 1){
                        level++;
                        if(dropInterval>=7){
                            dropInterval -=5;
                        }
                        else{
                            dropInterval--;
                        }
                    }

                    //rzad zostal usuniety, wiec teraz y kazdego z pozostalych blokow trzeba zwiekszyc o Block.SIZE;
                    for(int i = 0; i < staticBlocks.size(); i++){
                        if(staticBlocks.get(i).y < y){  //tego if'a trzeba dodac, zeby nie przesuwac najnizszego
                                                        //rzedu ponizej okna gry
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }

                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }

        //dodawanie wyniku
        if(linesCount > 0){
            singleLineScore = 10 * level;
            score += singleLineScore * linesCount;
        }
    }

    public void draw(Graphics2D g2){

        //rysowanie przestrzeni gry
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x, top_y, WIDTH, HEIGHT);

        //rysowanie okienka z nastepnym
        int x = right_x+100;
        int y = bottom_y-200;
        g2.drawRect(x,y,200,200);

        //tworzenie napisu "NEXT"
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawString("NEXT: ", x+60, y+40);

        y = top_y;
        g2.drawRect(x,y,250,250);
        x +=25;
        g2.drawString("LEVEL: " + level, x, y+60);
        g2.drawString("LINES: " + lines, x, y+120);
        g2.drawString("SCORE: " + score, x, y+180);

        //rysowanie tablicy wynikow z napisem highscores
        x = left_x - 350;
        y = top_y;
        g2.drawRect(x,y,250,420);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawString("HIGHSCORES: ", x+10, y-10);

        //wypisywanie najwyzszych wynikow
        x = left_x - 350 + 10;
        y = top_y + 30;
        int space = 40;

        g2.setFont(new Font("Arial", Font.PLAIN, 25));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for(int i = 0; i < hst.pairs.length; i++){
            g2.drawString(hst.pairs[i].getKey() + "   " + hst.pairs[i].getValue() + " pts", x, y);
            y+=space;
        }

        //narysuj aktualne tetromino
        if(currentMino != null){
            currentMino.draw(g2);
        }

        if(nextMino != null){
            nextMino.draw(g2);
        }

        //rysowanie blokow ktore juz sa ulozone
        for(int i = 0; i < staticBlocks.size(); i++){
            staticBlocks.get(i).draw(g2);
        }

        //rysowanie pauzy
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(50f));
        if(gameOver){
            g2.setColor(Color.red);
            x = left_x + 35;
            y = top_y + 320;
            g2.drawString("GAME OVER", x, y);
        }
        if(KeyHandler.pausePressed){
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("PAUSED", x, y);
        }
    }
}
