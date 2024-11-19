package Main;

import java.awt.*;
import javax.swing.JPanel;

//trzeba dodac "runnable" zby moc stworzyc nowy watek dla gry
//wymaga to jakiejs dziwnej funkcji uruchamiajacaej program
public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 1280, HEIGHT = 720;
    final int fps = 60;


    Thread gameThread;  //tworzenie obiektu klasy watek
    PlayManager pm; //tworzenie naszego menedzera rozgrywki

    public GamePanel() {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        //dodawanie keylistenera
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        pm = new PlayManager();
    }

    public void LaunchGame(){
        gameThread = new Thread(this);
        gameThread.start();
        //metoda .start() automatycznie odpala metode "run"
    }
    @Override
    public void run() {

        //gameloop
        double drawInterval = 1000000000/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                //repaint wywoluje metode paintComponent
                delta--;
            }
        }
    }

    private void update(){

        if(!KeyHandler.pausePressed && !pm.gameOver){
            pm.update();
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);    //jak to zakomentujesz, to wszystko jest biale. Za pomoca "super" dziedziczysz z rodzica klasy

        Graphics2D g2 = (Graphics2D) g; //sparowanie naszego obiektu g2 z wbudowanym obiektem g, ktory zawsze przekazuje sie do metody "paintComponent"
        pm.draw(g2);
    }
}
