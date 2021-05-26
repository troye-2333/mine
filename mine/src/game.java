
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class game extends Container {
    int x;
    int y;
    int z;
    int lun=1;
    Timer timer=new Timer(1000,new timeListener());
    scoreboard scoreboard;
    board board;
    JPanel center=new JPanel();
    JPanel south=new JPanel();
    JButton back =new JButton("return");
    JButton cheat=new JButton("cheat");
    JButton cover=new JButton("cover");
    JButton reset=new JButton("reset");
    JButton save=new JButton("save");

    public game(int x, int y, int z,int lun){

        //设置属性（雷区大小xyz，每个玩家多少轮）
        this.x=x;
        this.y=y;
        this.z=z;
        this.lun=lun;

        //计分面板的lun是默认的1
        scoreboard= new scoreboard(lun);

        //添加计分面板和功能按钮
        this.setLayout(new BorderLayout());
        this.add(scoreboard,BorderLayout.NORTH);
        this.add(south,BorderLayout.SOUTH);
        setSouth();

        center.setLayout(new GridLayout(1,1));

    }

    //load
    public void loadGame(){
        this.x=board.x;
        this.y=board.y;
        this.z=board.z;
        this.lun=scoreboard.lun;
        this.add(board,BorderLayout.CENTER);
        this.add(scoreboard,BorderLayout.NORTH);
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                board.setState(i,j);
            }
        }
    }

    //功能按钮的设置以及监听器
    public void setSouth(){
        //布局管理器
        south.setLayout(new FlowLayout());

        //组件属性
        back.setSize(80,30);
        cheat.setSize(80,30);
        back.addActionListener(e -> {
            demo.frame.cardlayout.show(demo.frame.total,"start");
        });

        //添加作弊按钮的监听器
        cheatButton cheatButton=new cheatButton();
        cheat.addMouseListener(cheatButton);

        //添加重置雷区按钮的监听器
        reset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                refreshBoard();
            }
        });

        //添加cover按钮的监听器
        cover.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                board.cover();
                scoreboard.restart();
                timer.restart();
            }
        });

        //添加save监听器
        save.addActionListener(e -> {
            save();
        });

        //添加组件
        south.add(back);
        south.add(cheat);
        south.add(reset);
        south.add(cover);
        south.add(save);
        south.setVisible(true);
    }

    //cheatButton
    public class cheatButton implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {
            for(int i=0;i<x;i++){
                for(int j=0;j<y;j++){
                    state a= board.states[i][j];
                    if(a.s.equals("covered")&&board.turn[i][j]==-1){
                        board.btn[i][j].setForeground(Color.BLACK);
                        board.btn[i][j].setText("X");
                    }
                }
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            for(int i=0;i<x;i++){
                for(int j=0;j<y;j++){
                    state a= board.states[i][j];
                    board.setState(i,j);
                }
            }
        }
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }

    //游戏开始或重新开始
    public void refreshBoard(){
        //避免九格全雷
        do{
            try{
                this.remove(board);
            }catch (NullPointerException ignored){}
            board=new board(this.x,this.y,this.z);
//            board=new board(3,3,3);
            this.add(board,BorderLayout.CENTER);
        }while(board.miji());

        //初始化scoreboard
        scoreboard.lun=this.lun;
        scoreboard.restart();
        timer.start();

        //设置界面
        int h=100+50*y;
        int w=50*x;
        board.setSize(50*x,50*y);
        this.setSize(w,h);
    }

    //判断是否结束游戏
    public void victory(){
        if(scoreboard.end&&(!scoreboard.same)){
            timer.stop();
            String winname=scoreboard.winer.name;
            JOptionPane.showMessageDialog(this,winname+"won!");
            scoreboard.player1.sec=0;
            scoreboard.player2.sec=0;
            remove(board);
        }
        else if(scoreboard.end){
            timer.stop();
            JOptionPane.showMessageDialog(this,"Win-win!");
            scoreboard.player1.sec=0;
            scoreboard.player2.sec=0;
            remove(board);
        }
    }

    //timer
    public class timeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            setAI();
            scoreboard.onturn.sec++;
            try {
                scoreboard.refreshScoreboard();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if(scoreboard.onturn.sec>=10){
                scoreboard.clickOnce();
            }
        }
    }

    //ai
    public void setAI(){
        Random random = new Random();
        if(scoreboard.onturn.ai()){
            boolean ok=false;
            do{
                int r = random.nextInt(x);
                int c = random.nextInt(y);
                if(board.states[r][c]==state.covered){
                    if(board.turn[r][c] != -1){
                        board.left(r,c);
                    }
                    else{
                        board.right(r,c);
                    }
                    ok=true;
                }
            }while (!ok);
        }
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setZ(int z) {
        this.z = z;
    }
    public void setLun(int lun) {
        this.lun = lun;
    }

    public void save(){
        String path="bye.txt";
        File f=new File(path);
        if(f.exists()){
            while(f.exists()){
                path="b"+path;
                f=new File(path);
            }
        }
        try{
            FileWriter w=new FileWriter(f);
            StringBuilder h=new StringBuilder();
            h.append(x).append(" ").append(y).append(" ").append(z).append(" ");
            for(int i=0;i<x;i++){
                for(int j=0;j<y;j++){
                    h.append(board.turn[i][j]).append(" ");
                }
            }
            for(int i=0;i<x;i++){
                for(int j=0;j<y;j++){
                    int s;
                    if(board.states[i][j]==state.open){
                        s=0;
                    }
                    else if(board.states[i][j]==state.boom){
                        s=1;
                    }
                    else if(board.states[i][j]==state.covered){
                        s=2;
                    }
                    else{
                        s=3;
                    }
                    h.append(s).append(" ");
                }
            }
            w.write(h.toString());

            player s1=scoreboard.player1;
            player s2=scoreboard.player2;
            player s3=scoreboard.onturn;
            w.write(lun+" ");
            w.write(scoreboard.n+" ");
            w.write(s1.name+" ");
            w.write(s2.name+" ");
            w.write(s1.score+" ");
            w.write(s2.score+" ");
            w.write(s1.mistake+" ");
            w.write(s2.mistake+" ");
            w.write(s1.sec+" ");
            w.write(s2.sec+" ");
            w.write(s3.name+" ");
            w.flush();
            w.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
