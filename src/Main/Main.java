package Main;

import javax.swing.JFrame;      //biblioteka do okien

public class Main {
    public static void main(String[] args){

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //zeby moc zamykac okno
        window.setResizable(false);

        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();      //rozmiar panelu staje sie rozmairem okna

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.LaunchGame();
    }
}

// skonczyles na 1:15:46 i teraz bedziesz dodawac wynik i poziom
