package Main;

import java.io.*;
import java.util.Scanner;

public class HighscoreTable {

    Pair [] pairs = new Pair[10];
    String username = null;

    //wywolywane na start gry
    public HighscoreTable() {
        try{
            BufferedReader br = new BufferedReader(new FileReader("highscores.txt"));
            String line;
            int i = 0;
            while((line = br.readLine()) != null && i<pairs.length){
                String[] parts = line.split(";");
                pairs[i] = new Pair(parts[0], Integer.parseInt(parts[1]));
                i++;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    //wywolywane w momencie
    public void checkAndUpdateHighscoreTable(String username, int score){

        Pair para = new Pair(username, score);

        //public void set result
        for(int i=0; i< pairs.length; i++){
            if(pairs[i].getValue()<para.getValue()){
                pairs[pairs.length-1] = null;
                for(int j= pairs.length-2; j>=i; j--){
                    pairs[j+1] = pairs[j];
                }
                pairs[i] = para;
                break;
            }
        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter("highscores.txt"))){
            for(int i=0; i< pairs.length; i++){
                bw.write(pairs[i].getKey() + ";" + pairs[i].getValue());
                bw.newLine();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}
