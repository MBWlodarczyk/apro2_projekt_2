package Client.Controller;

import Client.Model.map.GameMap;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Queue;

public class GameEngine {
    //Public methods section

    /**
     * Class to get all possible field to apply move.
     *
     * @param map  Map to check.
     * @param move Move to check.
     * @return boolean array of field where can be applied and where cannot.
     */
    public static boolean[][] getValid(GameMap map, Move move) {

        boolean[][] marked = new boolean[map.getMap().length][map.getMap()[0].length];
        dfs(map, marked, move.getFrom().getY(), move.getFrom().getX(), move.getWhat().getRange());
        return marked;
    }

    /**
     * Method to check is the move is valid.
     *
     * @param map  Map to check.
     * @param move Move to check.
     * @return true if possible, false otherwise.
     */
    public static boolean isValid(GameMap map, Move move) {
        boolean[][] marked = getValid(map, move);
        return marked[move.getWhere().getY()][move.getWhere().getX()];
    }

    /**
     * Method to check if move is along the rules
     * @param move
     * @param moves (queue)
     * @return
     */
    public static boolean checkMove(Move move, Queue<Move> moves){
        //check if tile is crossable
        if(move.getWhere().getObstacle()!= null && !move.getWhere().getObstacle().isCrossable()){
            playBeep("./core/assets/raw/bruh.wav",0);
            System.out.println("This tile is not crossable");
            return true;
        }
        // check if tile will is occupied
        if(move.getWhere().getHero() != null){
            playBeep("./core/assets/raw/bruh.wav", 0);
            System.out.println("This tile is occupied");
            return true;
        }
        // check if hero has moved yet.
        for (Move m:moves) {
            if(!m.equals(move) && m.getWho().equals(move.getWho())){
                playBeep("./core/assets/raw/bruh.wav", 0);
                System.out.println("This hero has made a move already");
                return true;
            }
        }
        // check if tile will be occupied
        for (Move m:moves) {
            if(move.getWhere().equals(m.getWhere())){
                playBeep("./core/assets/raw/bruh.wav", 0);
                System.out.println("This tile will be occupied");
                return true;
            }
        }
        return false;
    }

    //Private methods section
    /**
     * DFS method to look for valid fields
     */
    private static void dfs(GameMap map, boolean[][] marked, int y, int x, int distance) {
        marked[y][x] = true;
        if (fieldValid(map, y, x - 1) && distance > 0) {
            dfs(map, marked, y, x - 1, distance - 1);
        }
        if (fieldValid(map, y, x + 1) && distance > 0) {
            dfs(map, marked, y, x + 1, distance - 1);
        }
        if (fieldValid(map, y - 1, x) && distance > 0) {
            dfs(map, marked, y - 1, x, distance - 1);
        }
        if (fieldValid(map, y + 1, x) && distance > 0) {
            dfs(map, marked, y + 1, x, distance - 1);
        }
    }

    /**
     * Method to help dfs validate if the field is not null or wall.
     */
    private static boolean fieldValid(GameMap map, int y, int x) {
        return y < map.getMap().length && y >= 0 && x >= 0 && x < map.getMap()[0].length && (map.getMap()[y][x].getObstacle() == null || map.getMap()[y][x].getObstacle().isCrossable());//Typy ktore nie moga byc przekroczone tutaj dodawac
    }


    /**
     * method that uses SimpleAudioPlayer to play music
     * @param filePath
     */
    private static void playBeep(String filePath, int mode) {
        try {
            SimpleAudioPlayer player = new SimpleAudioPlayer(filePath);
            player.play(mode);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

    }

    /**
     * class for music player
     */
    private static class SimpleAudioPlayer
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
        public void play(int mode)
        {
            if(mode < 0){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }else clip.loop(mode);
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
            this.play(0);
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
            clip.loop(0);
        }

    }

}
