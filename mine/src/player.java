import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class player extends JPanel {
    //player的基本属性
    String name;
    int score=0;
    int mistake=0;
    int sec=0;

    //基本属性的显示
    JLabel namelabel=new JLabel("Name:"+name);
    JLabel scorel=new JLabel("Score:"+score);
    JLabel misl=new JLabel("Mistakes:"+mistake);
    JLabel timeL=new JLabel("Time:"+sec+"s");

    public player(String name){

        //添加基本属性和设置字体
        this.setLayout(new GridLayout(1,4));
        this.add(namelabel);
        this.add(scorel);
        this.add(misl);
        this.add(timeL);
        namelabel.setFont(new Font("Times New Roman",Font.BOLD,20));
        scorel.setFont(new Font("Times New Roman",Font.BOLD,20));
        misl.setFont(new Font("Times New Roman",Font.BOLD,20));
        timeL.setFont(new Font("Times New Roman",Font.BOLD,20));
    }

    //如果标记正确
    public void addscore(){
        score++;
    }

    //如果踩雷了
    public void costscore(){
        score--;
    }

    //如果标记错误
    public void addmistake(){
        mistake++;
    }

    //游戏重新开始
    public void refreshPlayer(){
        namelabel.setText("Name:"+name);
        scorel.setText("Score:"+score);
        misl.setText("Mistakes:"+mistake);
        timeL.setText("Time:"+sec+"s");
    }

    //判断该玩家是否为ai
    public boolean ai(){
        return name.equals("ai1") | name.equals("ai2");
    }

}
