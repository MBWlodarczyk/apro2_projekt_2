package Client.GUI.Scenes;

import Client.GUI.SwordGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Hud {
    private static int roundsGen = 0;
    public Stage stage;
    Label movesLabel;
    Label roundsLabel;
    Label movesCountLabel;
    Label roundsCountLabel;
    private Viewport viewport; // nowy viewPort aby przy poruszaniu sie na mapie, hud zostwał w miejscu
    private int moves;
    private int rounds; // tutaj jeszcze moge ogarnac

    public Hud(SpriteBatch sb) {
        moves = 4;
        rounds = roundsGen++;
        viewport = new FillViewport(SwordGame.V_WIDTH, SwordGame.V_HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport, sb); //do organizaji widgetów (label)

        Table tabel = new Table();
        tabel.top();
        tabel.setFillParent(true);

        movesLabel = new Label("Pozostało ruchów", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        roundsLabel = new Label("Runda: ", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        movesCountLabel = new Label(String.valueOf(moves), new Label.LabelStyle(new BitmapFont(), Color.BLACK)); //może być ez tego
        roundsCountLabel = new Label(String.valueOf(rounds), new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        tabel.add(movesLabel).expandX().padTop(10);
        tabel.add(movesCountLabel).expandX().padTop(10);
        tabel.add(roundsLabel).expandX().padTop(10);
        tabel.add(roundsCountLabel).expandX().padTop(10);

        stage.addActor(tabel);
    }


}
