package Client.GUI.Sprites;

import Client.GUI.Utility.Constants;
import Client.Model.map.GameMap;
import Client.Model.map.Wall;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class WallSprite {

    private ArrayList<Sprite> wallSprites;
    private Texture texture;

    public WallSprite(Texture texture, GameMap gameMap) {
        this.texture = texture;
        this.wallSprites = new ArrayList<>();
        setPosition(gameMap);
        System.out.println(gameMap.toString());
    }

    private void setPosition(GameMap gameMap){
        Sprite s = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        for (int i = 0; i < Constants.GAME_SIZE; i++) {
            for (int j = 0; j < Constants.GAME_SIZE; j++) {
                if(gameMap.getMap()[i][j].getObstacle() instanceof Wall) {
                    s.setPosition(i * Constants.TEXTURE_SIZE, Constants.HEIGHT - (j + 1) * Constants.TEXTURE_SIZE);
                    //TODO NAPRAWIC T
                    wallSprites.add(s);
                }
            }
        }
    }

    public void draw(Batch batch){
        for(Sprite s : wallSprites)
            s.draw(batch);
    }


}
