//package Client.GUI.Screens;
//
//import Client.Controller.Client;
//import Client.Controller.DistanceValidator;
//import Client.Controller.HandleInput;
//import Client.Controller.Move;
//import Client.GUI.Sprites.MouseSprite;
//import Client.GUI.Sprites.MoveDistanceSprite;
//import Client.GUI.Sprites.SkillPanelSprite;
////import Client.GUI.Sprites.WallSprite;
//import Client.GUI.SwordGame;
//import Client.GUI.Utility.Constants;
//import Client.GUI.Utility.GameObject;
//import Client.Model.Heroes.*;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.ui.Button;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.badlogic.gdx.utils.viewport.Viewport;
//
//import java.util.ArrayList;
//
//
//public class PlayScreen implements Screen {
//    public Client client;
//    public SwordGame swordGame;
////    private Hud hud;
//    private OrthographicCamera gameCam;
//    private Viewport gamePort;
//    private Texture grassTexture, wallTexture, waterTexture, forestTexture, bushTexture, rockTexture,
//            paladinTexture, warriorTexture, archerTexture, necromancerTexture, priestTexture, wizardTexture,
//            crossTexture, edgeTexture, healthTexture, bordTexture,skillPanelTexture;
//    public  GameObject[][] gameObjects;
//
//    private MouseSprite mouseSprite;
//    private SkillPanelSprite skillPanelSprite;
////    private WallSprite wallSprite;
//
//    private MoveDistanceSprite moveDistanceSprite;
//
////    private Sprite bord;
//
//
//
//    private HandleInput handleInput;
//    private ArrayList<Button> skills;
//    private int skillNumber;
//
//    public PlayScreen(SwordGame swordGame,boolean init) throws Exception {
//        this.swordGame = swordGame;
//        this.client = new Client(swordGame,init);
//        this.gameCam = new OrthographicCamera();
//        this.gamePort = new FitViewport(Constants.WIDTH, Constants.HEIGHT, gameCam);
//        this.gameObjects = new GameObject[22][22];
//        loadData();
//        this.mouseSprite = new MouseSprite(edgeTexture);
//        this.skillPanelSprite = new SkillPanelSprite(skillPanelTexture);
//        this.moveDistanceSprite = new MoveDistanceSprite(crossTexture);
////        this.wallSprite = new WallSprite(wallTexture,client.getReceived());
//
//        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
//
//
////        hud = new Hud(swordGame.batch);
//
//
////        bord = new Sprite(bordTexture,0,0,bordTexture.getWidth(),bordTexture.getHeight());
////        bord.setPosition(0,0);
//
//
//        skills = new ArrayList<>();
//
//        handleInput = new HandleInput(this, swordGame.size);
//        Gdx.input.setInputProcessor(handleInput);
//    }
//
//    public GameObject[][] getGameObjects() {
//        return gameObjects;
//    }
//
//    /**
//     * Init all of GameObjcets
//     */
//    private void rewriteMap() {
//        for (int i = 0; i < swordGame.size; i++) {
//            for (int j = 0; j < swordGame.size; j++) {
//                if (checkHero(i, j) == null)
//                    gameObjects[i][j] = new GameObject(null);
//                else {
//                    gameObjects[i][j] = new GameObject(checkHero(i, j), null, healthTexture, client.getReceived().getMap()[i][j].getHero().getHeathStatus());
//                }
//                gameObjects[i][j].x = i * gameObjects[i][j].height;
//                gameObjects[i][j].y = Constants.HEIGHT - (j + 1) * gameObjects[i][j].width;
//            }
//        }
//    }
//
//    private Texture checkHero(int i, int j) {
//        if (client.getReceived().getMap()[i][j].getHero() != null) {
//            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Paladin.class))
//                return paladinTexture;
//            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Warrior.class))
//                return warriorTexture;
//            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Archer.class))
//                return archerTexture;
//            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Necromancer.class))
//                return necromancerTexture;
//            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Wizard.class))
//                return wizardTexture;
//            if (client.getReceived().getMap()[i][j].getHero().getClass().equals(Priest.class))
//                return priestTexture;
//        }
//        return null;
//    }
//
//    private void distanceMove() {
//        if (handleInput.heroChosen) {
//            int[] tab = handleInput.getTab();
//            int x = handleInput.getX();
//            int y = handleInput.getY();
//            Move move = new Move(client.getReceived().getMap()[y][x].getHero(), client.getReceived().getMap()[tab[0]][tab[1]], client.getReceived().getMap()[y][x], client.getReceived().getMap()[y][x].getHero().getSkills().get(0));
//            boolean[][] marked = DistanceValidator.getValid(client.getReceived(), move);
//            moveDistanceSprite.setSprites(marked);
//        } else {
//             moveDistanceSprite.clear();
//        }
//    }
//
//    private void skillOptions() {
//        TextButton button;
//        String s;
//        if (handleInput.heroChosen) {
//            int[] tab = handleInput.getTab();
//            if(client.getReceived().getMap()[tab[0]][tab[1]].getHero()!=null) {
//                int size = client.getReceived().getMap()[tab[0]][tab[1]].getHero().getSkills().size();
//                for (int i = 0; i < size; i++) {
//                    s = client.getReceived().getMap()[tab[0]][tab[1]].getHero().getSkills().get(i).toString();
//                    button = new TextButton(s, swordGame.skin);
//                    button.setSize(250, 100);
//                    button.setPosition(720, Constants.HEIGHT - 100 * (i + 2));
//                    final int finalI = i;
//                    button.addListener(new ClickListener() {
//                        @Override
//                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                            skillNumber = finalI; //walk stay
//                            return super.touchDown(event, x, y, pointer, button);
//                        }
//                    });
//                    skills.add(button);
//                }
//            }
//        } else {
//            skills.clear();
//        }
//    }
//
//
//
//    private void update(float delta) {
//        rewriteMap();
//        gameCam.update();
//        distanceMove();
//        skillOptions();
//    }
//
//    @Override
//    public void render(float delta) {
//        update(delta);
//
//        Gdx.gl.glClearColor(0, 0, 0, 0);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        swordGame.batch.begin();
////        bord.draw(swordGame.batch);  //draw bord
//
////        wallSprite.draw(swordGame.batch);
//
//        skillPanelSprite.draw(swordGame.batch);  //draw skill panel
//
//        mouseSprite.draw(swordGame.batch); //draw mousePosition
//
//        moveDistanceSprite.draw(swordGame.batch);
//
//        //render textures
//        for (int i = 0; i < swordGame.size; i++) {
//            for (int j = 0; j < swordGame.size; j++) {
//                gameObjects[i][j].draw(swordGame.batch);
//            }
//        }
//
//        //render list skill
//        for (Button b : skills) b.draw(swordGame.batch, 1);
//
//
//        swordGame.batch.end();
//
////        swordGame.batch.setProjectionMatrix(hud.stage.getCamera().combined);
////        hud.stage.draw();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        gamePort.update(width, height);
//    }
//
//    @Override
//    public void show() {
//
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//
//    }
//
//    @Override
//    public void dispose() {
//
//    }
//
//    private void loadData() {
//        grassTexture = swordGame.assets.manager.get("field/grass.png", Texture.class);
//        waterTexture = swordGame.assets.manager.get("field/water.png", Texture.class);
//        forestTexture = swordGame.assets.manager.get("field/forest.png", Texture.class);
//        wallTexture = swordGame.assets.manager.get("field/wall.png", Texture.class);
//        bushTexture = swordGame.assets.manager.get("field/bush.png", Texture.class);
//        rockTexture = swordGame.assets.manager.get("field/rock.png", Texture.class);
//
//        paladinTexture = swordGame.assets.manager.get("heroes/paladin.png", Texture.class);
//        warriorTexture = swordGame.assets.manager.get("heroes/warrior.png", Texture.class);
//        archerTexture = swordGame.assets.manager.get("heroes/archer.png", Texture.class);
//        necromancerTexture = swordGame.assets.manager.get("heroes/necromancer.png", Texture.class);
//        wizardTexture = swordGame.assets.manager.get("heroes/wizard.png", Texture.class);
//        priestTexture = swordGame.assets.manager.get("heroes/priest.png", Texture.class);
//
//        crossTexture = swordGame.assets.manager.get("special/cross.png",Texture.class);
//        edgeTexture = swordGame.assets.manager.get("special/edge.png", Texture.class);
//        healthTexture = swordGame.assets.manager.get("special/health.png", Texture.class);
//        bordTexture = swordGame.assets.manager.get("special/bord.png",Texture.class);
//        skillPanelTexture = swordGame.assets.manager.get("special/skillPanel.png",Texture.class);
//    }
//}