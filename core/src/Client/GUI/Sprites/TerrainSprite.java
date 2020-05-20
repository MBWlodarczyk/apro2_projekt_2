package Client.GUI.Sprites;

import Client.Model.terrain.Terrain;
import com.badlogic.gdx.graphics.Texture;

public class TerrainSprite extends EntitySprite<Terrain> {

    public TerrainSprite(Terrain terrain, Texture texture) {
        super(terrain, texture);
    }
}
