package Client.Controller;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Queue;

public class GameEngine {

    private static void playBeep() {
        String path = "./core/assets/raw/bruh.wav";
        try {
            SimpleAudioPlayer player = new SimpleAudioPlayer(path);
            player.play();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

    }

    public static boolean checkMove(Move move, Queue<Move> moves){
        //check if tile is crossable
        if(move.getWhere().getObstacle()!= null && !move.getWhere().getObstacle().isCrossable()){
            playBeep();
            System.out.println("This tile is not crossable");
            return true;
        }
        // check if tile will is occupied
        if(move.getWhere().getHero() != null){
            playBeep();
            System.out.println("This tile is occupied");
            return true;
        }
        // check if hero has moved yet.
        for (Move m:moves) {
            if(!m.equals(move) && m.getWho().equals(move.getWho())){
                playBeep();
                System.out.println("This hero has made a move already");
                return true;
            }
        }
        // check if tile will be occupied
        for (Move m:moves) {
            if(move.getWhere().equals(m.getWhere())){
                playBeep();
                System.out.println("This tile will be occupied");
                return true;
            }
        }
        return false;
    }

    public static class SimpleAudioPlayer
    {
        // to store current position
        Long currentFrame;
        Clip clip;
        // current status of clip
        String status;
        AudioInputStream audioInputStream;
        // constructor to initialize streams and clip
        public SimpleAudioPlayer(String filePath)
                throws UnsupportedAudioFileException, IOException, LineUnavailableException
        {
            // create AudioInputStream object
            audioInputStream =
                    AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            // create clip reference
            clip = AudioSystem.getClip();
            // open audioInputStream to the clip
            clip.open(audioInputStream);
            clip.loop(0);
        }

        // Method to play the audio
        public void play()
        {
            //start the clip
            clip.start();
            status = "play";
        }


        // Method to restart the audio
        public void restart(String filePath) throws IOException, LineUnavailableException,
                UnsupportedAudioFileException
        {
            clip.stop();
            clip.close();
            resetAudioStream(filePath);
            currentFrame = 0L;
            clip.setMicrosecondPosition(0);
            this.play();
        }

        // Method to stop the audio
        public void stop() throws UnsupportedAudioFileException,
                IOException, LineUnavailableException
        {
            currentFrame = 0L;
            clip.stop();
            clip.close();
        }

        // Method to reset audio stream
        public void resetAudioStream(String filePath) throws UnsupportedAudioFileException, IOException,
                LineUnavailableException
        {
            audioInputStream = AudioSystem.getAudioInputStream(
                    new File(filePath).getAbsoluteFile());
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

    }

}
