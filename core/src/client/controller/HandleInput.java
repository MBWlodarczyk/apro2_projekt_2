package client.controller;

import client.model.heroes.Hero;
import client.model.map.Field;
import client.model.skills.Stay;
import client.view.screens.PlayScreen;
import client.view.utility.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import static client.controller.HandleInput.ControllerState.*;
import static client.controller.Inputs.*;

/**
 * Class to handle all input from user
 */
public class HandleInput implements InputProcessor {

    public ControllerState currentState;
    private PlayScreen playScreen;
    private Field field;
    private ArrayList<Rectangle> rectangles;
    private Rectangle sendTurnRec, removeMoveRec;

    public HandleInput(PlayScreen playScreen) {
        this.currentState = IDLE;
        this.playScreen = playScreen;
        this.rectangles = new ArrayList<>();
    }


    public void addRectangles(float x, float y, float width, float height) {
        this.rectangles.add(new Rectangle(x, y, width, height));
    }

    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    public void addSendTurnRectangle(float x, float y, float width, float height) {
        sendTurnRec = new Rectangle(x, y, width, height);
    }

    public void addRemoveMoveRectangle(float x, float y, float width, float height) {
        removeMoveRec = new Rectangle(x, y, width, height);
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(buttonsTouch(screenX,screenY))
            return true;

        if (screenX < Constants.HEIGHT) {
            getCord(screenX, screenY); //zwraca cordy gdzie przycisnelismy
            field = playScreen.client.getReceived().getMap().getFieldsArray()[tab[0]][tab[1]];
            if (currentState == IDLE && field.getHero() != null && field.getHero().getOwner().equals(playScreen.swordGame.player)) {
                currentState = HERO_CHOSEN;
                y = tab[0];
                x = tab[1];
                return true;
            }
            if ((currentState == IDLE) && (field.getHero() != null)) anyHeroChosen = true;
            else anyHeroChosen = false;

            if (field.getHero() != null && !field.getHero().getOwner().equals(playScreen.swordGame.player)){
                anyHeroChosen = true;
                currentState = IDLE;
                return true;
            }
        }
        if (currentState == HERO_CHOSEN) {
            for (int i = 0; i < rectangles.size(); i++) {
                if (rectangles.get(i).contains(screenX, screenY)) {
                    skillChosen = i;
                    currentState = PERFORM_SKILL;
                    if (skillChosen == rectangles.size() - 1) { // if the last rectangle is chosen (exit) then and go to idle
                        currentState = IDLE;
                        return true;
                    }
                    if (playScreen.client.getReceived().getMap().getFieldsArray()[y][x].getHero().getSkills().get(skillChosen) instanceof Stay) {
                        performSkill(skillChosen);
                        currentState = IDLE;
                    }
                    return true;
                }
            }
        }
        if (currentState == PERFORM_SKILL) {
            performSkill(skillChosen);
            currentState = IDLE;
            return true;
        }
        return true;
    }

    private boolean buttonsTouch(int screenX, int screenY){
        if (sendTurnRec != null && sendTurnRec.contains(screenX, screenY) && !playScreen.client.isSend()) {
            sendTurn = true;
            return true;
        }
        if (removeMoveRec != null && removeMoveRec.contains(screenX, screenY)) {
            playScreen.client.getSend().removeLast();
            return true;
        }
        return false;
    }

    private void performSkill(int index) {
        System.out.println("performed skill: " + index);
        Field[][] fieldsArray = playScreen.client.getReceived().getMap().getFieldsArray();
        Field field = fieldsArray[y][x];
        Move move = new Move(field.getHero(), this.field, field, field.getHero().getSkills().get(index));
        if (GameEngine.isValid(playScreen.client.getReceived().getMap(), move)) {
            if (!GameEngine.checkMove(move, playScreen.client.getSend().getMoves())) {
                playScreen.client.getSend().addMove(move);
                System.out.println("Adding move...");
                if (!move.getWhat().getSoundPath().equals("")) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal(move.getWhat().getSoundPath()));
                    sound.play(0.6f);
                }
            } else {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/bruh.wav"));
                sound.play(0.6f);
            }
            System.out.println(playScreen.client.getSend().getMoves().size());
        }
    }


    public void getCord(int screenX, int screenY) {
        if (screenX < Constants.HEIGHT) {
            tab[0] = (screenX - screenX % Constants.TEXTURE_SIZE) / Constants.TEXTURE_SIZE;
            tab[1] = (screenY - screenY % Constants.TEXTURE_SIZE) / Constants.TEXTURE_SIZE;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public enum ControllerState {
        IDLE(0){
            @Override
            public String toString() {
                return super.toString();
            }
        },
        HERO_CHOSEN(1){
            @Override
            public String toString() {
                return super.toString();
            }
        },
        PERFORM_SKILL(2){
            @Override
            public String toString() {
                return super.toString();
            }
        },
        ;


        ControllerState(int index) {
        }
    }
}
