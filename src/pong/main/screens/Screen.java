package pong.main.screens;

import java.util.ArrayList;

import pong.main.ObjectInstantiator.GameObjects;
import pong.main.game_objects.BaseScreenObject;

public class Screen {

	private ArrayList<BaseScreenObject> objectsInScreen = null;
	private ArrayList<GameObjects> objectsToInstantiate = null;
	private boolean baseScreenObjectOrGameObjectsArray; // True for BSO false
														// for GO

	public Screen(BaseScreenObject... baseGameObjects) {
		this.objectsInScreen = new ArrayList<>();
		baseScreenObjectOrGameObjectsArray = true;
		for (BaseScreenObject bgo : baseGameObjects)
			addItem(bgo);
	}

	public Screen(GameObjects... objectsToInstantiate) {
		this.objectsToInstantiate = new ArrayList<>();
		baseScreenObjectOrGameObjectsArray = false;
		for (GameObjects go : objectsToInstantiate)
			addItem(go);
	}

	public ArrayList<?> getList() {
		return baseScreenObjectOrGameObjectsArray ? objectsInScreen : objectsToInstantiate;
	}

	public Class<? extends Object> getArrayListType() {
		return baseScreenObjectOrGameObjectsArray ? BaseScreenObject.class : GameObjects.class;
	}

	private void addItem(BaseScreenObject bgo) {
		objectsInScreen.add(bgo);
	}

	private void addItem(GameObjects go) {
		objectsToInstantiate.add(go);
	}

}
