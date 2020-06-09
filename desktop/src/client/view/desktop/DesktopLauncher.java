package client.view.desktop;


import client.view.SwordGame;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1024;
        config.height = 704;
        config.resizable = false;
        config.addIcon("special/logo.png", FileType.Internal);
        new LwjglApplication(new SwordGame(), config);
    }
}
