package mino;

import Main.KeyHandler;
import Main.PlayManager;

import java.awt.*;

//to jest super klasa czyli rodzic. Wszystkie klasy poszczegolnych tetrominos bede dziedziczyc z tej klasy

public class Mino {

    public Block b[] = new Block[4];
    public Block tempB[] = new Block[4];    //dwie tablice blokow
    int autoDropCounter = 0;
    public int direction = 1;
    boolean leftcollision, rightcollision, bottomcollision;
    public boolean active = true;
    public boolean deactivating;
    int deactivateCounter = 0;

    public void create (Color c) {
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        tempB[0] = new Block(c);
        tempB[1] = new Block(c);
        tempB[2] = new Block(c);
        tempB[3] = new Block(c);
    }

    public void setXY (int x, int y) {}     //tutaj nic nie implementujemy, bo kazde tetromino bedzie mialo swoj ksztalt
    // na dobra sprawe ta funkcja powinna sie nazywac setShape

    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}
    public void checkMovementCollision(){

        leftcollision = false;
        rightcollision = false;
        bottomcollision = false;

        checkStaticBlockCollision();

        //sprawdzanie czy nie przekroczy lewej granicy
        for(int i = 0; i < b.length; i++){
            if(b[i].x == PlayManager.left_x)
                leftcollision = true;
        }
        //sprawdzanie prawej
        for(int i = 0; i < b.length; i++){
            if(b[i].x + Block.SIZE == PlayManager.right_x)
                rightcollision = true;
        }
        //sprawdzanie dolnej
        for(int i = 0; i < b.length; i++){
            if(b[i].y + Block.SIZE == PlayManager.bottom_y)
                bottomcollision = true;
        }
    };
    public void checkRotationCollision(){

        leftcollision = false;
        rightcollision = false;
        bottomcollision = false;

        checkStaticBlockCollision();

        //sprawdzanie czy nie przekroczy lewej granicy
        for(int i = 0; i < b.length; i++){
            if(tempB[i].x < PlayManager.left_x)
                leftcollision = true;
        }
        //sprawdzanie prawej
        for(int i = 0; i < tempB.length; i++){
            if(tempB[i].x + Block.SIZE > PlayManager.right_x)
                rightcollision = true;
        }
        //sprawdzanie dolnej
        for(int i = 0; i < tempB.length; i++){
            if(tempB[i].y + Block.SIZE > PlayManager.bottom_y)
                bottomcollision = true;
        }
    };
    private void checkStaticBlockCollision(){
        for(int i = 0; i < PlayManager.staticBlocks.size(); i++){

            int targetX = PlayManager.staticBlocks.get(i).x;
            int targetY = PlayManager.staticBlocks.get(i).y;

            //czyli stad wiemy, jak odniesc sie do pozycji wszystkich blokow, ktore sa juz w array list
            //kazdy z nich ma swoja wspolrzedna x oraz y, ktore zostaja z nimi juz na zawsze od momentu
            //kiedy tetromino skladajce sie z nich zostanie ustawione

            //do sprawdzania bierzemy nasze aktualne tetromino, tak jak w przypadku dwoch funkcji wyzej
            //sprawdzanie bloku pod spodem
            for(int j = 0; j < b.length; j++){
                if(b[j].x == targetX && b[j].y + Block.SIZE == targetY){
                    bottomcollision = true;
                }
            }
            //sprawdzanie bloku po lewej
            for(int j = 0; j < b.length; j++){
                if(b[j].x - Block.SIZE == targetX && b[j].y == targetY){
                    leftcollision = true;
                }
            }
            //sprawdzanie bloku po prawej
            for(int j = 0; j < b.length; j++){
                if(b[j].x + Block.SIZE == targetX && b[j].y == targetY){
                    rightcollision = true;
                }
            }
        }
    }

    //nie implementujemy tutaj tych metod, bo dla kazdego tetromina beda inne

    public void updateXY (int direction){   //tu juz implementujemy, bo ta funkcja ustawia nam na nowo tetromino

        checkRotationCollision();

        if(!leftcollision && !rightcollision && !bottomcollision){
            this.direction = direction;//zeby wiedziec w ktorej pozycji jest w danym momencie
            b[0].x = tempB[0].x;
            b[0].y = tempB[0].y;
            b[1].x = tempB[1].x;
            b[1].y = tempB[1].y;
            b[2].x = tempB[2].x;
            b[2].y = tempB[2].y;
            b[3].x = tempB[3].x;
            b[3].y = tempB[3].y;
            //na tym ciag funkcji sie konczy - pozycje zostaja zupdatowane
        }

    }
    public void update (){

        if(deactivating){
            deactivating();
        }

        //fragment metody odpowiedzialny za poruszanie
        if(KeyHandler.upPressed){
            switch(direction){
                case 1: getDirection2(); break; //jezeli obecnie mamy pozycje 1, to po kliknieciu wywolaj pozycje 2
                case 2: getDirection3(); break; //jezeli obecnie mamy pozycje 2, to po kliknieciu wywolaj pozycje 3
                case 3: getDirection4(); break; //itd
                case 4: getDirection1(); break;
            }
            KeyHandler.upPressed = false;
        }

        checkMovementCollision();

        if(KeyHandler.downPressed){
            if(!bottomcollision){
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;
                KeyHandler.downPressed = false;
                //dla dobra rozgrywki zresetuj tutaj autodrop
                autoDropCounter = 0;
            }

        }
        if(KeyHandler.leftPressed){
            if(!leftcollision){
                b[0].x -= Block.SIZE;
                b[1].x -= Block.SIZE;
                b[2].x -= Block.SIZE;
                b[3].x -= Block.SIZE;
                KeyHandler.leftPressed = false;
            }

        }
        if(KeyHandler.rightPressed){
            if(!rightcollision){
                b[0].x += Block.SIZE;
                b[1].x += Block.SIZE;
                b[2].x += Block.SIZE;
                b[3].x += Block.SIZE;
                KeyHandler.rightPressed = false;
            }

        }

        if(bottomcollision){
            deactivating = true;    //nie dajemy od razu "active = false", zeby byl czas na przesuniecie bloku po tym jak juz dotknie ziemi
        }
        else{
            //fragment metody odpowiedzialny za ciagle spadanie
            autoDropCounter++;
            if(autoDropCounter == PlayManager.dropInterval){
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;
                autoDropCounter = 0;
            }
        }


    }
    private void deactivating(){

        deactivateCounter++;

        if(deactivateCounter == 60){
            deactivateCounter = 0;
            checkMovementCollision();   //sprawdzanie czy dalej jest kontakt z dolem
        }
        if(bottomcollision){
            active = false;
        }

    }
    public void draw (Graphics2D g2){

        int margin = 2;
        g2.setColor(b[0].c);
        g2.fillRect(b[0].x+margin, b[0].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
        g2.fillRect(b[1].x+margin, b[1].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
        g2.fillRect(b[2].x+margin, b[2].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
        g2.fillRect(b[3].x+margin, b[3].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
    }

}
