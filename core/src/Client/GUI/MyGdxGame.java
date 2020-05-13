package Client.GUI;

import Client.Controller.Client;
import Client.Model.GameMap;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {

	private final int size;
	private Assets assets;
	private SpriteBatch batch;
	private Texture grassTexture,wallTexture,waterTexture,forestTexture;
	private GameObjcet[][] gameObjcets;
	private Client client;


	public MyGdxGame(int size) throws Exception {
		this.client= new Client();
		this.size = size;
		gameObjcets = new GameObjcet[size][size];
		System.out.println(client.getReceived().getMap().toString());;
	}

	@Override  //tu inicjuemy wsyztsko
	public void create () {
		assets = new Assets();
		assets.lodad();
		assets.manager.finishLoading();

		if(assets.manager.update()){
			loadData();
			init();
		}
	}

	/**
	 * Init all of GameObjcets
	 */
	private void init(){
		batch = new SpriteBatch();
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				Texture texture = checktextute(i,j);
				gameObjcets[i][j] = new GameObjcet(texture);
				gameObjcets[i][j].x = i* gameObjcets[i][j].height;
				gameObjcets[i][j].y = j * gameObjcets[i][j].width;
			}
		}
	}

	/**
	 * Check what is te texture of that field
	 * @param i i index
	 * @param j j index
	 * @return return texture
	 */
	private Texture checktextute(int i, int j) {
		switch(client.getReceived().getMap()[i][j].getType()){
			case Grass: return grassTexture;
			case Water: return waterTexture;
			case Forest: return forestTexture;
			case Wall: return wallTexture;
			default: return grassTexture;
		}
	}

	private void loadData(){
		grassTexture = assets.manager.get("grass.png",Texture.class);
		waterTexture = assets.manager.get("water.png",Texture.class);
		forestTexture = assets.manager.get("forest.png",Texture.class);
		wallTexture = assets.manager.get("wall.png",Texture.class);

	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++) {
				gameObjcets[i][j].draw(batch);
			}
		}

		batch.end();
	}

	private void update() {
		init();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
