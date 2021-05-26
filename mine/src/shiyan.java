import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class shiyan {
    public static void main(String[] args){
        int [][]turn=new int[17][18];
        Random ran=new Random();
        for (int i = 0; i <19;i++ ) {
            int r = ran.nextInt(16);
            int c = ran.nextInt(16);
            if(turn[r][c] != -1) {
                turn[r][c] = -1;
            }
        }
    }
}
