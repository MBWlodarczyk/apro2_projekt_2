package Client.GUI.desktop;

import Client.GUI.MyGdxGame;
import Client.Gui_v2.SwordGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
//	public static void main (String[] arg) throws Exception {
//		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.width = 512;
//		config.height = 512;
//		config.resizable = false;
//		new LwjglApplication(new MyGdxGame(16), config);
//	}
public static void main (String[] arg) throws Exception {
	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	config.width = 704;
	config.height = 704;
	config.resizable = true;
	new LwjglApplication(new SwordGame(), config);
}
}
