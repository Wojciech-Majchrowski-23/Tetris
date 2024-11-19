package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    //implements implementuje metody (zachowania) interfejsow, czyli lacznikow miedzy
    //wspolpracojucymi ze soba stronami (np klawiatura z laptopem, tak jak tutaj) , stad te funkcje, ktorych
    //trzeba uzyc w naszej klasie

    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed;;

    @Override
    public void keyTyped(KeyEvent e) {}     //tego nie uzywamy w tetrisie

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            if(pausePressed)
                pausePressed = false;
            else
                pausePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}  //tego nie uzywamy w tetrisie
}

//keyhandler jest obowiazkowa klasa w kazdej grze wiec go sobie zapamietaj
