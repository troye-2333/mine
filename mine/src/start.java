
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class start extends JPanel {
    Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    int h=screensize.height;
    int w=screensize.width;
    //info在上面
    JPanel info=new JPanel();

    //创建边框
    JPanel north=new JPanel();
    JPanel h1=new JPanel();
    JPanel h2=new JPanel();
    JPanel h3=new JPanel();
    TitledBorder t1=new TitledBorder(new LineBorder(Color.GRAY,5),"雷区大小：");
    TitledBorder t2=new TitledBorder(new LineBorder(Color.GRAY,5),"每回合点击次数：");
    TitledBorder t3=new TitledBorder(new LineBorder(Color.GRAY,5),"玩家信息：");

    //创建中间的图片
    JPanel pic=new JPanel();
    ImageIcon back=new ImageIcon("E:\\hh\\back.png");
    JLabel picL=new JLabel(back);

    //info的组件
    JComboBox<String> size=new JComboBox<>();
    JLabel xx=new JLabel("行");
    JLabel yy=new JLabel("列");
    JLabel zz=new JLabel("雷数");
    JTextField xfield=new JTextField("9",3);
    JTextField yfield=new JTextField("9",3);
    JTextField zfield=new JTextField("10",3);

    JLabel name1=new JLabel("player1:");
    JLabel name2=new JLabel("player2:");
    JTextField names1=new JTextField("default1",10);
    JTextField names2=new JTextField("default2",10);

    JLabel turns=new JLabel("每轮点击次数");
    JTextField tfield=new JTextField("1",5);

    JPanel startSouth=new JPanel();
    JButton begin=new JButton("开始游戏");
    JButton read=new JButton("读档");

    public start(){


        //加入info和begin按钮
        setLayout(new BorderLayout());
        add(info,BorderLayout.CENTER);
        add(begin,BorderLayout.SOUTH);

        //background
        setPic();

        setInfo();

        //往start面板上加元素
        this.add(info,BorderLayout.CENTER);
        this.add(startSouth,BorderLayout.SOUTH);

        //
        setStartSouth();

        //begin按钮-->开始游戏
    }

    public void setPic(){
        picL.setSize(back.getIconWidth(),back.getIconHeight());
        back.setImage(back.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
        pic.setLayout(new GridLayout(1,1));
        pic.add(picL);
    }

    public void setInfo(){

        //info的布局
        info.setLayout(new BorderLayout());
        info.add(north,BorderLayout.NORTH);
        info.add(pic,BorderLayout.CENTER);
        info.setBackground(new Color(161, 198, 215));
        north.setLayout(new BoxLayout(north,BoxLayout.X_AXIS));
        north.add(h1);
        north.add(h2);
        north.add(h3);
        h1.setBorder(t1);
        h2.setBorder(t2);
        h3.setBorder(t3);

        //选择雷区大小
        //size
        size.addItem("初级");
        size.addItem("中级");
        size.addItem("高级");
        size.addItem("自定义");
        size.addItemListener(e -> {
            int index= size.getSelectedIndex();
            able(index);
            int x=1;
            int y=1;
            int z=1;
            if(index==0){
                x=9;
                y=9;
                z=10;
            }
            else if(index==1){
                x=16;
                y=16;
                z=40;
            }
            else if(index==2){
                x=16;
                y=30;
                z=99;
            }
            else if(index==3){
                x=Integer.parseInt(xfield.getText());
                y=Integer.parseInt(yfield.getText());
                z=Integer.parseInt(zfield.getText());
            }
            xfield.setText(String.valueOf(x));
            yfield.setText(String.valueOf(y));
            zfield.setText(String.valueOf(z));
        });

        h1.add(size);
        h1.add(xx);
        h1.add(xfield);
        h1.add(yy);
        h1.add(yfield);
        h1.add(zz);
        h1.add(zfield);

        h2.add(turns);
        h2.add(tfield);

        h3.add(name1);
        h3.add(names1);
        h3.add(name2);
        h3.add(names2);

        //设置默认值
        xfield.setEnabled(false);
        yfield.setEnabled(false);
        zfield.setEnabled(false);
    }

    public void able(int index){
        if(index==0|index==1|index==2) {
            xfield.setEnabled(false);
            yfield.setEnabled(false);
            zfield.setEnabled(false);
        }
        else if(index==3){
            xfield.setEnabled(true);
            yfield.setEnabled(true);
            zfield.setEnabled(true);
        }
    }

    public void setStartSouth(){
        startSouth.add(begin);
        startSouth.add(read);
        read.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                demo.frame.load();
                demo.frame.game.loadGame();
            }
        });
    }

}


