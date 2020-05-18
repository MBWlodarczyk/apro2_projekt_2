package Client.GUI.desktop;


import Client.GUI.SwordGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {

    public static void main(String[] arg) throws Exception {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1024;
        config.height = 704;
        config.resizable = false;
        new LwjglApplication(new SwordGame(), config);
    }
}
