//package Client.GUI.Sprites;
//
//import Client.Controller.DistanceValidator;
//import Client.Controller.HandleInput;
//import Client.Controller.Move;
//import Client.GUI.Utility.Constants;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//
//import java.util.ArrayList;
//
//
//public class MoveDistanceSprite extends Sprite {
//
//    public ArrayList<Sprite> moveDistance;
//
//    public MoveDistanceSprite(Texture texture) {
//        super(texture, 0, 0, texture.getWidth(), texture.getHeight());
//        moveDistance = new ArrayList<>();
//    }
//
//    public void setSprites(boolean[][] marked){
//        for (int i = 1; i < Constants.GAME_SIZE-1; i++) {
//            for (int j = 1; j < Constants.GAME_SIZE - 1; j++) {
//                if (marked[i][j]) {
//                    setPosition(i * 32, 704 - (j + 1) * 32);
//                    moveDistance.add(this);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void draw(Batch batch) {
//        for (Sprite value : moveDistance)
//            value.draw(batch);
//        super.draw(batch);
//    }
//}
