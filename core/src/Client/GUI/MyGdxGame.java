package Client.GUI;

import Client.Controller.Client;
import Client.GUI.Entities;
import Client.Model.Heroes.Paladin;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static Client.GUI.Entities.Palad;

public class MyGdxGame extends ApplicationAdapter {

    private final int size;
    private Assets assets;
    private SpriteBatch batch;
    private Sprite sprite;
    private Texture grassTexture, wallTexture, waterTexture, forestTexture, bushTexture, rockTexture,
                    paladinTexture,
                    edgeTexture;
    private GameObjcet[][] gameObjcets;
    private Client client;
    private GameObjcet obiekcik;


    public MyGdxGame(int size) throws Exception {
        this.client = new Client();
        this.size = size;
        gameObjcets = new GameObjcet[size][size];
        System.out.println(client.getReceived().getMap().toString());
	}

    @Override  //tu inicjuemy wsyztsko
    public void create() {
        assets = new Assets();
        assets.load();
        assets.manager.finishLoading();

		batch = new SpriteBatch();

        if (assets.manager.update()) {
            loadData();
            rewriteMap();
        }
        sprite = new Sprite(paladinTexture,0,0,edgeTexture.getWidth(),edgeTexture.getHeight());
//        sprite = new Sprite(edgeTexture,0,0,edgeTexture.getWidth(),edgeTexture.getHeight());
    }

    /**
     * Init all of GameObjcets
     */
    private void rewriteMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Texture filedTexture = checkTexture(i, j);
                gameObjcets[i][j] = new GameObjcet(filedTexture);
                gameObjcets[i][j].x = i * gameObjcets[i][j].height;
                gameObjcets[i][j].y = 512 - (j+1) * gameObjcets[i][j].width;
            }
        }
    }


    /**
     * Check what is te texture of that field
     *
     * @param i i index
     * @param j j index
     * @return return texture
     */
    private Texture checkTexture(int i, int j) {
//        System.out.println(client.getReceived());
//        System.out.println(client.getReceived().getMap()[5][5].getHero().getClass().getSuperclass());
        checkHero(5,5);
        switch (client.getReceived().getMap()[i][j].getType()) {
            case Grass:
                return grassTexture;
            case Water:
                return waterTexture;
            case Forest:
                return forestTexture;
            case Wall:
                return wallTexture;
            case Rock:
                return rockTexture;
            case Bush:
                return bushTexture;
            default:
                return grassTexture; //tutaj jak na razie defultowo jest dawaniae testruy trwawy, bo nie ma wsyztskich testur xd
        }
    }

    private Texture checkHero(int i, int j){
        if(client.getReceived().getMap()[5][5].getHero().getClass().equals(Paladin.class)){
            return paladinTexture; //tutuaj pozmieniać, dodać do
        }
        return null;
    }



    /**
     * Render method
     */
    @Override
    public void render() {

        update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameObjcets[i][j].draw(batch);
            }
        }
        sprite.draw(batch);

        batch.end();;
    }

	/**
	 * Changes on the map updated there
	 */
	private void update() {
        rewriteMap();
        mousePosition();
    }

    //cover filed
	private void mousePosition() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(gameObjcets[i][j].contains(Gdx.input.getX(),512 - Gdx.input.getY())){
                    sprite.setPosition(gameObjcets[i][j].x,gameObjcets[i][j].y);
                }
            }
        }


	}

	/**
	 * Load data using assets manager
	 */
	private void loadData() {
		grassTexture = assets.manager.get("field/grass.png", Texture.class);
		waterTexture = assets.manager.get("field/water.png", Texture.class);
		forestTexture = assets.manager.get("field/forest.png", Texture.class);
		wallTexture = assets.manager.get("field/wall.png", Texture.class);
		bushTexture = assets.manager.get("field/bush.png", Texture.class);
		rockTexture = assets.manager.get("field/rock.png", Texture.class);

        paladinTexture = assets.manager.get("heroes/paladin.png",Texture.class);

		edgeTexture = assets.manager.get("special/edge.png",Texture.class);
	}

    @Override
    public void dispose() {
        batch.dispose();
    }
}
