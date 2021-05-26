import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.BufferedReader;

public class scoreboard extends JPanel {
    player player1=new player("player1");
    player player2=new player("player2");
    player onturn=player1;
    JPanel numtext=new JPanel();
    JPanel players=new JPanel();
    int nums;
    JLabel number=new JLabel("剩余地雷个数"+nums);
    boolean initial=true;
    int lun=1;
    int n=0;

    //判断游戏是否结束
    boolean end=false;
    boolean same=false;
    player winer;

    public scoreboard(int lun){
        //lun初始化时就已经确定了
        this.lun=lun;

        //左上角num面板，正上方是计分面板
        setLayout(new BorderLayout());
        add(players,BorderLayout.CENTER);
        add(numtext,BorderLayout.WEST);

        //设置num面板
        numtext.add(number);

        //设置players面板
        players.setLayout(new GridLayout(2,1));
        players.add(player1);
        players.add(player2);
        border();
    }

    public scoreboard(BufferedReader b){
        try{
            String s=b.readLine();
            String []get=String.valueOf(s).split(" ");
            int x=Integer.parseInt(get[0]);
            int y = Integer.parseInt(get[1]);

            this.lun=Integer.parseInt(get[2*x*y+3]);
            this.n=Integer.parseInt(get[2*x*y+4]);
            this.player1.name=get[2*x*y+5];
            this.player2.name=get[2*x*y+6];
            this.player1.score=Integer.parseInt(get[2*x*y+7]);
            this.player2.score=Integer.parseInt(get[2*x*y+8]);
            this.player1.mistake=Integer.parseInt(get[2*x*y+9]);
            this.player2.mistake=Integer.parseInt(get[2*x*y+10]);
            this.player1.sec=Integer.parseInt(get[2*x*y+11]);
            this.player2.sec=Integer.parseInt(get[2*x*y+12]);
            if(player1.name.equals(get[2*x*y+13])){
                onturn=player1;
            }
            else{
                onturn=player2;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        //左上角num面板，正上方是计分面板
        setLayout(new BorderLayout());
        add(players,BorderLayout.CENTER);
        add(numtext,BorderLayout.WEST);

        //设置num面板
        numtext.add(number);

        //设置players面板
        players.setLayout(new GridLayout(2,1));
        players.add(player1);
        players.add(player2);
        border();
    }

    //给玩家设置边框
    public void border(){
        TitledBorder title1=new TitledBorder(new LineBorder(Color.BLACK,1,true),"player1");
        TitledBorder title2=new TitledBorder(new LineBorder(Color.BLACK,1,true),"player2");
        player1.setBorder(title1);
        player2.setBorder(title2);
    }

    //刷新面板数据，没有地雷时判断winer
    public void refreshScoreboard(){
        player1.refreshPlayer();
        player2.refreshPlayer();
        nums=demo.frame.game.board.leinum;
        number.setText("剩余地雷个数"+nums);
        if(player1==onturn){
            player1.setBackground(new Color(150, 212, 243));
            player2.setBackground(null);
        }
        else{
            player2.setBackground(new Color(150, 212, 243));
            player1.setBackground(null);
        }

        shuaJudgeWin();
    }

    //两个玩家轮流点击，每一回合判断是否赢了
    public void takeTurn(){
        if(n>=lun){
            onturn.sec=0;
            if(onturn==player1){
                onturn=player2;
                this.n=0;
            }
            else if(onturn==player2){
                onturn=player1;
                this.n=0;
            }
            turnJudgeWin();
        }
    }

    //有效点击一次
    public void clickOnce() {
        n++;
        refreshScoreboard();
        takeTurn();
        System.out.println("Now it's "+onturn.name+"'s turn.");
    }

    //游戏重新开始，计分从零开始
    public void restart(){
        player1.score=0;
        player2.score=0;
        player1.mistake=0;
        player2.mistake=0;
        n=0;
        player1.sec=0;
        player2.sec=0;
        onturn=player1;
        end=false;
        same=false;
        winer=null;
        initial=true;
        refreshScoreboard();
    }

    //如果两人分数差值大于未发现地雷的个数，赢家诞生！
    public void turnJudgeWin(){
        int play1=player1.score;
        int play2=player2.score;
        if(play1-play2>nums|play2-play1>nums){
            if(play1-play2>nums){
                winer=player1;
            }
            else{
                winer=player2;
            }
            end=true;
        }
    }

    //如果地雷已经全部被发现了
    public void shuaJudgeWin(){
        int play1=player1.score;
        int play2=player2.score;
        if(nums==0){
            end=true;
            if(play1-play2>0){
                winer=player1;
            }
            else if(play1-play2<0){
                winer=player2;
            }
            else{
                if(player1.mistake>player2.mistake){
                    winer=player2;
                }
                else if(player1.mistake<player2.mistake){
                    winer=player1;
                }
                else {
                    same=true;
                }
            }
        }
    }

}
