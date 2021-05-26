import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Map;
import java.util.Random;

public class board extends JPanel {
    //基本属性
    int x,y,z;
    int leinum;

    //用到的图片：不用图片了

    //两个重要数组
    int [][]turn;
    JButton[][] btn;
    state [][] states;

    public board(int x, int y, int z) {
        //设置参数
        this.x = x;
        this.y = y;
        this.z = z;
        this.leinum=z;
        turn = new int[x][y];
        states=new state[x][y];
        setData();

        //设置按钮
        btn = new JButton[x][y];
        GridLayout grid = new GridLayout(x,y);
        setLayout(grid);
        setButtons();
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                setState(i,j);
            }
        }
    }

    public board(BufferedReader b){
        turn = new int[x][y];
        states=new state[x][y];
        try{
            String s=b.readLine();
            String []get=String.valueOf(s).split(" ");
            this.x=Integer.parseInt(get[0]);
            this.y = Integer.parseInt(get[1]);
            turn = new int[x][y];
            states=new state[x][y];
            this.z = Integer.parseInt(get[2]);
            for(int i=0;i<x;i++){
                for(int j=0;j<y;j++){
                    turn[i][j]=Integer.parseInt(get[i*y+j+3]);
                }
            }
            for(int i=0;i<x;i++){
                for(int j=0;j<y;j++){
                    int ss=Integer.parseInt(get[i*y+j+3+x*y]);
                    if(ss==0){
                        states[i][j]=state.open;
                    }
                    else if(ss==1){
                        states[i][j]=state.boom;
                    }
                    else if(ss==2){
                        states[i][j]=state.covered;
                    }
                    else{
                        states[i][j]=state.flag;
                    }
                }
            }
            int sum=0;
            for(int i=0;i<x;i++){
                for(int j=0;j<y;j++){
                    if(states[i][j]==state.covered&&turn[i][j]==-1){
                        sum++;
                    }
                }
            }
            leinum=sum;

        } catch (Exception e) {
            e.printStackTrace();
        }
        btn = new JButton[x][y];
        GridLayout grid = new GridLayout(x,y);
        setLayout(grid);
        setButtons();
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                setState(i,j);
            }
        }
    }

    public void setData() {
        Random ran = new Random();
        do {
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    turn[i][j] = 0;
                }
            }
            for (int i = 0; i < z; ) {
                int r = ran.nextInt(x);
                int c = ran.nextInt(y);
                if (turn[r][c] != -1) {
                    turn[r][c] = -1;
                    i++;
                }
            }
        } while (miji());
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (turn[i][j] == -1) continue;
                int tempCount = 0;
                if (i > 0 && j > 0 && turn[i - 1][j - 1] == -1) tempCount++;
                if (i > 0 && turn[i - 1][j] == -1) tempCount++;
                if (i > 0 && j < y - 1 && turn[i - 1][j + 1] == -1) tempCount++;
                if (j > 0 && turn[i][j - 1] == -1) tempCount++;
                if (j < y - 1 && turn[i][j + 1] == -1) tempCount++;
                if (i < x - 1 && j > 0 && turn[i + 1][j - 1] == -1) tempCount++;
                if (i < x - 1 && turn[i + 1][j] == -1) tempCount++;
                if (i < x - 1 && j < y - 1 && turn[i + 1][j + 1] == -1) tempCount++;
                turn[i][j] = tempCount;
            }
        }
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                states[i][j]=state.covered;
            }
        }
    }

    public boolean miji() {
        boolean mi = false;
        if (x > 2 && y > 2) {
            for (int i = 0; i < x - 2; i++) {
                for (int j = 0; j < y - 2; j++) {
                    if (turn[i][j] == -1 && turn[i + 1][j] == -1 && turn[i + 2][j] == -1 && turn[i][j + 1] == -1 && turn[i + 1][j + 1] == -1 && turn[i + 2][j + 1] == -1 && turn[i][j + 2] == -1 && turn[i + 1][j + 2] == -1 && turn[i + 2][j + 2] == -1) {
                        mi = true;
                        break;
                    }
                }
                if (mi) break;
            }
        }
        return mi;
    }

    public void setButtons() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                btn[i][j]=new JButton("");
                btn[i][j].setSize(30,30);
                btn[i][j].setBackground(new Color(192, 192, 192));
                buttonClick hi=new buttonClick();
                btn[i][j].addMouseListener(hi);
                this.add(btn[i][j]);
            }
        }
    }

    public class buttonClick extends MouseAdapter{
        public void mouseClicked(MouseEvent e) {
            JButton hh=(JButton) e.getSource();
            int fi=0;
            int fj=0;
            for (int i=0;i<x;i++){
                for(int j=0;j<y;j++){
                    if(btn[i][j]==hh){
                        fi=i;
                        fj=j;
                    }
                }
            }
            if(hh.isEnabled()){
                if (e.getButton() == 1) {
                    left(fi,fj);
                }
                else if (e.getButton() == 3) {
                    right(fi,fj);
                }
            }
        }
    }

    //根据不同的state，给按钮加上不同的图案
    public void setState(int i,int j){
        if(states[i][j]==state.covered){
            btn[i][j].setBackground(new Color(192,192,192));
            btn[i][j].setEnabled(true);
        }
        else if(states[i][j]==state.boom){
            btn[i][j].setEnabled(false);
            btn[i][j].setOpaque(true);
            btn[i][j].setBackground(Color.red);
            btn[i][j].setText("X");
        }
        else if(states[i][j]==state.open){
            int size=64/(x/3-2);

            Font font=new Font("SansSerif",Font.BOLD,size);
            btn[i][j].setFont(font);
            btn[i][j].setBackground(new Color(183, 230, 241));
            if(turn[i][j]!=0)
                btn[i][j].setText(""+turn[i][j]);
            btn[i][j].setEnabled(false);
        }
        else if(states[i][j]==state.flag){
            btn[i][j].setEnabled(false);
            btn[i][j].setOpaque(true);
            btn[i][j].setBackground(Color.orange);
            btn[i][j].setText("F");
        }
        if(turn[i][j]==0&&states[i][j]==state.open){
            for(int m=0;m<x;m++){
                for(int n=0;n<y;n++){
                    if(Math.abs(m-i)<2&&Math.abs(n-j)<2&&states[m][n]==state.covered&&turn[i][j]==0&&(m!=i|n!=j)){
                        states[m][n]=state.open;
                        setState(m,n);
                    }
                }
            }
        }
    }

    //点击cover按钮后，不改变雷区
    public void cover(){
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                btn[i][j].setText(null);
                btn[i][j].setBackground(new Color(192,192,192));
                states[i][j]=state.covered;
                setState(i,j);
            }
        }
    }

    //点击reset按钮后，重置雷区(方法在game中)

    //左击
    public void left(int i,int j){
        if(btn[i][j].isEnabled()){
            if(demo.frame.game.scoreboard.initial&turn[i][j]== -1){
                demo.frame.game.refreshBoard();
            }
            else{
                if (turn[i][j] != -1) {
                    states [i][j]=state.open;
                }
                else {
                    demo.frame.game.scoreboard.onturn.costscore();
                    demo.frame.game.board.leinum--;
                    states[i][j]=state.boom;
                }
                demo.frame.game.scoreboard.clickOnce();
                demo.frame.game.scoreboard.initial=false;
            }
        }
        setState(i,j);
        demo.frame.game.victory();
    }

    //右击
    public void right(int i,int j){
        if (turn[i][j] == -1) {
            states[i][j]=state.flag;
            demo.frame.game.scoreboard.onturn.addscore();
        } else {
            JOptionPane.showMessageDialog(btn[i][j], "错啦！这个不是雷！");
            demo.frame.game.scoreboard.onturn.addmistake();
            demo.frame.game.scoreboard.onturn.costscore();
            states[i][j]=state.open;
        }
        demo.frame.game.board.leinum--;
        demo.frame.game.scoreboard.clickOnce();
        setState(i,j);
        demo.frame.game.victory();
    }
    @Override
    public int getX() {
        return x;
    }
    @Override
    public int getY() {
        return y;
    }
}
