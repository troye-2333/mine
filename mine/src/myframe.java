import javax.sound.sampled.*;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;
import java.net.URL;

public class myframe extends JFrame {
    int x;
    int y;
    int z;
    int n;//这个对应着game和scoreboard的lun
    private static final String path = "save.sav";
    myframe sv;

    //总框架setLayout出start和game两个卡片面板

    CardLayout cardlayout=new CardLayout();
    Container total=getContentPane();
    start start=new start();
    game game;

    public myframe(String title){
        super(title);

        //game初始化，防止报错
        game=new game(1,1,1,1);

        //total是cardLayout布局
        total.setLayout(cardlayout);
        total.add(start,"start");
        total.add(game,"game");

        //start,begin->开始游戏
        start.setSize(500,600);
        beginListener beginListener= new beginListener();
        start.begin.addActionListener(beginListener);
    }

    //将start界面中输入的x,y,z,names,n都输入到新游戏game中
    //并开始新游戏，并提示这是第一位玩家的时间
    public void startGame(){
        game.setX(this.x);
        game.setY(this.y);
        game.setZ(this.z);
        game.setLun(this.n);
        game.refreshBoard();
        System.out.println("Now it's "+game.scoreboard.player1.name+"'s turn.");
    }

    //begin按钮
    public  class beginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //设置雷区大小和地雷个数
            x=Integer.parseInt(start.xfield.getText());
            y=Integer.parseInt(start.yfield.getText());
            z=Integer.parseInt(start.zfield.getText());

            //设置players名字
            String n1=start.names1.getText();
            String n2=start.names2.getText();
            game.scoreboard.player1.name=n1;
            game.scoreboard.player2.name=n2;

            //设置每回合点击数
            n=Integer.parseInt(start.tfield.getText());

            //确保x<=24且y<=30
            if(x>24|y>30){
                JOptionPane.showMessageDialog(start,"The board is too big!\nPlease reset the board." );
            }
            else if(z>x*y/2){
                JOptionPane.showMessageDialog(start,"There are too many bombs in the board!\nPlease reset the number of bombs");
            }
            else if(n<1|n>5){
                JOptionPane.showMessageDialog(start,"Please reset the number of times you want to click in around!");
            }

            //游戏初始化
            else{
                startGame();
                cardlayout.show(total,"game");
            }
        }
    }

    public void load(){
        JFileChooser fc=new JFileChooser();
        fc.showOpenDialog(null);
        File f=fc.getSelectedFile();
        if(f!=null){
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                try{
                    game.remove(game.board);
                    game.remove(game.scoreboard);
                }catch (NullPointerException ignored){}
                game.board=new board(br);
                BufferedReader br2 = new BufferedReader(new FileReader(f));
                game.scoreboard=new scoreboard(br2);
                cardlayout.show(total,"game");
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }
}
