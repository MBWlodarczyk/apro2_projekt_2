//package Client.GUI;
//
//import Client.Controller.Client;
//import Client.Model.Heroes.Archer;
//import Client.Model.Heroes.Paladin;
//import Client.Model.Heroes.Warrior;
//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//
//
//public class MyGdxGame extends ApplicationAdapter {
//
//    private final int size;
//    private final float zoom;
//    private Assets assets;
//    private SpriteBatch batch;
//    private Sprite sprite;
//    private OrthographicCamera camera;
//
//    private Texture grassTexture, wallTexture, waterTexture, forestTexture, bushTexture, rockTexture,
//                    paladinTexture, warriorTexture, archerTexture,
//                    edgeTexture;
//    private GameObject[][] gameObjects;
//    private Client client;
//
//
//    public MyGdxGame(int size) throws Exception {
//        this.client = new Client();
//        this.size = size;
//        gameObjects = new GameObject[size][size];
////        System.out.println(client.getReceived().getMap().toString());
//        zoom = 0.05f;
//	}
//
//    @Override  //tu inicjuemy wsyztsko
//    public void create() {
//        camera = new OrthographicCamera(512,512);
//        assets = new Assets();
//        assets.load();
//        assets.manager.finishLoading();
//
//		batch = new SpriteBatch();
////		sprite = new Sprite();
//
//        if (assets.manager.update()) {
//            loadData();
//            rewriteMap();
//        }
////        sprite = new Sprite(paladinTexture,0,0,edgeTexture.getWidth(),edgeTexture.getHeight());
//        sprite = new Sprite(edgeTexture,0,0,edgeTexture.getWidth(),edgeTexture.getHeight());
//    }
//
//    /**
//     * Init all of GameObjcets
//     */
//    private void rewriteMap() {
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//
//                gameObjects[i][j] = new GameObject(checkTexture(i,j),checkHero(i,j),null);
//                gameObjects[i][j].x = i * gameObjects[i][j].height;
//                gameObjects[i][j].y = 512 - (j+1) * gameObjects[i][j].width;
//            }
//        }
//    }
//
//
//    /**
//     * Check what is te texture of that field
//     *
//     * @param i i index
//     * @param j j index
//     * @return return texture
//     */
//    private Texture checkTexture(int i, int j) {
//        switch (client.getReceived().getMap()[i][j].getType()) {
//            case Grass:
//                return grassTexture;
//            case Water:
//                return waterTexture;
//            case Forest:
//                return forestTexture;
//            case Wall
//                return wallTexture;
//            case Rock:
//                return rockTexture;
//            case Bush:
//                return bushTexture;
//            default:
//                return grassTexture; //tutaj jak na razie defultowo jest dawaniae testruy trwawy, bo nie ma wsyztskich testur xd
//        }
//    }
//
//    /**
//     * Check what is the texture of hero
//     * @param i
//     * @param j
//     * @return
//     */
//    private Texture checkHero(int i, int j){
//        if(client.getReceived().getMap()[i][j].getHero() != null) { //tutaj jest problem jesli nie ma hero
//            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Paladin.class))
//                return paladinTexture; //tutuaj pozmieniać, dodać do
//
//            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Warrior.class))
//                return warriorTexture;
//            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Archer.class))
//                return archerTexture;
//        }
//        return null;
//    }
//
//    private Texture checkObstacle(int i, int j){
//        if(client.getReceived().getMap()[i][j].getObstacle() != null) { //tutaj jest problem jesli nie ma hero
//            if (client.getReceived().getMap()[i][j].getObstacle().getClass().equals(Paladin.class))
//                return paladinTexture; //tutuaj pozmieniać, dodać do
//
//
//        }
//        return null;
//    }
//
//
//
//    /**
//     * Render method
//     */
//    @Override
//    public void render() {
//        update();
//
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        batch.begin();
//
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                gameObjects[i][j].draw(batch);
//            }
//        }
//        sprite.draw(batch);
//
//        batch.end();
//    }
//
//	/**
//	 * Changes on the map updated there
//	 */
//	private void update() {
//        rewriteMap();
//        mouseUpdate(); // dodatkow  w create sprite = new Sprite(edgeTexture,0,0,edgeTexture.getWidth(),edgeTexture.getHeight());
//        cameraUpdate();
//        exit();
//    }
//
//    private void exit(){
//	    if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
//	        System.exit(0);
//        }
//    }
//    //cover filed
//	private void mouseUpdate() {
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                if(gameObjects[i][j].contains(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY())){
//                    sprite.setPosition(gameObjects[i][j].x*camera.zoom, gameObjects[i][j].y*camera.zoom);
//                }
//            }
//        }
//	}
//
//	private void cameraUpdate(){
//        camera.update();
//        batch.setProjectionMatrix(camera.combined);
//        camera.position.set(Gdx.input.getX(),512-Gdx.input.getY(),0);
//
//        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
//            camera.zoom += zoom;
//        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT))
//            camera.zoom -= zoom;
//
//    }
//
//
//
//	/**
//	 * Load data using assets manager
//	 */
//	private void loadData() {
//		grassTexture = assets.manager.get("field/grass.png", Texture.class);
//		waterTexture = assets.manager.get("field/water.png", Texture.class);
//		forestTexture = assets.manager.get("field/forest.png", Texture.class);
//		wallTexture = assets.manager.get("field/wall.png", Texture.class);
//		bushTexture = assets.manager.get("field/bush.png", Texture.class);
//		rockTexture = assets.manager.get("field/rock.png", Texture.class);
//
//        paladinTexture = assets.manager.get("heroes/paladin.png",Texture.class);
//        warriorTexture = assets.manager.get("heroes/warrior.png",Texture.class);
//        archerTexture = assets.manager.get("heroes/archer.png",Texture.class);
//
//		edgeTexture = assets.manager.get("special/edge.png",Texture.class);
//	}
//
//    @Override
//    public void dispose() {
//        batch.dispose();
//        assets.dispose();
//    }
//}
