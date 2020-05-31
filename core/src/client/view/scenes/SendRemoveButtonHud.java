package client.view.scenes;

import client.controller.HandleInput;
import client.view.utility.Constants;
import client.view.utility.Drawable;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SendRemoveButtonHud implements Drawable {

    public Stage stage;
    private Viewport viewport;

    public SendRemoveButtonHud(SpriteBatch spriteBatch, Skin skin, HandleInput handleInput) {
//        super(spriteBatch);
        viewport = new FillViewport(Constants.WIDTH, Constants.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);
        sendTurnTextField(skin, handleInput);
        removeMoveTextField(skin, handleInput);
    }

    private void sendTurnTextField(Skin skin, HandleInput handleInput) {
        TextField sendTurn = new TextField("Send", skin);
        sendTurn.setSize(128, 32);
        sendTurn.setPosition(Constants.HEIGHT, 13 * Constants.TEXTURE_SIZE);
        handleInput.addSendTurnRectangle(Constants.HEIGHT, Constants.HEIGHT - (13 + 1) * Constants.TEXTURE_SIZE, 128, 32);
        stage.addActor(sendTurn);
    }

    private void removeMoveTextField(Skin skin, HandleInput handleInput) {
        TextField removeMove = new TextField("Remove", skin);
        removeMove.setSize(128, 32);
        removeMove.setPosition(Constants.HEIGHT + 5 * Constants.TEXTURE_SIZE, 13 * Constants.TEXTURE_SIZE);
        handleInput.addRemoveMoveRectangle(Constants.HEIGHT + 5 * Constants.TEXTURE_SIZE, Constants.HEIGHT - (13 + 1) * Constants.TEXTURE_SIZE, 128, 32);
        stage.addActor(removeMove);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        stage.draw();
    }
}
