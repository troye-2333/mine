import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class demo {
    public static myframe frame = new myframe("扫雷");
    public static void main(String[] args) {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int w=screensize.width;
        int h=screensize.height-50;
        frame.setSize(w,h);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playMusic();
    }
    static void playMusic() {// 背景音乐播放
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("E:/hh/music1.wav"));
            AudioFormat aif = ais.getFormat();
            final SourceDataLine sdl;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
            sdl = (SourceDataLine) AudioSystem.getLine(info);
            sdl.open(aif);
            sdl.start();
            FloatControl fc = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
            // value可以用来设置音量，从0-2.0
            double value = 2;
            float dB = (float) (Math.log(value == 0.0 ? 0.0001 : value) / Math.log(10.0) * 20.0);
            fc.setValue(dB);
            int nByte = 0;
            final int SIZE = 1024 * 64;
            byte[] buffer = new byte[SIZE];
            while (nByte != -1) {
                nByte = ais.read(buffer, 0, SIZE);
                sdl.write(buffer, 0, nByte);
            }
            sdl.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
