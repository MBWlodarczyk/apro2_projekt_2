package client.view.sprites;

import client.model.terrain.Terrain;
import com.badlogic.gdx.graphics.Texture;

public class TerrainSprite extends EntitySprite<Terrain> {

    public TerrainSprite(Terrain terrain, Texture texture) {
        super(terrain, texture);
    }
}
