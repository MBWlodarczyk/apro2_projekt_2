package Client.GUI;

import Client.Controller.Client;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {

    private final int size;
    private Assets assets;
    private SpriteBatch batch;
    private Texture grassTexture, wallTexture, waterTexture, forestTexture, bushTexture, rockTexture,coveredGrassTexture;
    private GameObjcet[][] gameObjcets;
    private Client client;


    public MyGdxGame(int size) throws Exception {
        this.client = new Client();
        this.size = size;
        gameObjcets = new GameObjcet[size][size];
        System.out.println(client.getReceived().getMap().toString());
	}

    @Override  //tu inicjuemy wsyztsko
    public void create() {
        assets = new Assets();
        assets.lodad();
        assets.manager.finishLoading();

		batch = new SpriteBatch();

        if (assets.manager.update()) {
            loadData();
            rewriteMap();
        }
    }

    /**
     * Init all of GameObjcets
     */
    private void rewriteMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
//                Texture texture = checktextute(i, j);
//                gameObjcets[i][j] = new GameObjcet(texture);
                gameObjcets[i][j] = new GameObjcet(grassTexture);

                gameObjcets[i][j].x = i * gameObjcets[i][j].height;
                gameObjcets[i][j].y = 512 - (j+1) * gameObjcets[i][j].width;
            }
        }
        gameObjcets[2][3].setTexture(wallTexture);
//        System.out.println(client.getReceived());

    }


    /**
     * Check what is te texture of that field
     *
     * @param i i index
     * @param j j index
     * @return return texture
     */
    private Texture checktextute(int i, int j) {
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
			case CoveredGrass: return coveredGrassTexture;
            default:
                return grassTexture; //tutaj jak na razie defultowo jest dawaniae testruy trwawy, bo nie ma wsyztskich testur xd
        }
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

        batch.end();;
    }

	/**
	 * Changes on the map updated there
	 */
	private void update() {
        rewriteMap();
        //mousePosition();
    }

	private void mousePosition() {
		System.out.println("x: "+Gdx.input.getX()+"   y: "+Gdx.input.getY());



	}

	/**
	 * Load data using assets manager
	 */
	private void loadData() {
		grassTexture = assets.manager.get("grass.png", Texture.class);
		waterTexture = assets.manager.get("water.png", Texture.class);
		forestTexture = assets.manager.get("forest.png", Texture.class);
		wallTexture = assets.manager.get("wall.png", Texture.class);
		bushTexture = assets.manager.get("bush.png", Texture.class);
		rockTexture = assets.manager.get("rock.png", Texture.class);

	}

    @Override
    public void dispose() {
        batch.dispose();
    }
}
