package server.world;

import java.util.ArrayList;

import server.model.objects.Object;
import server.util.Misc;
import server.model.players.Client;
import server.model.players.PlayerHandler;

/**
 * @author Sanity
 */

public class ObjectManager {

	public ArrayList<Object> objects = new ArrayList<Object>();
	private ArrayList<Object> toRemove = new ArrayList<Object>();
	public void process() {
		for (Object o : objects) {
			if (o.tick > 0)
				o.tick--;
			else {
				updateObject(o);
				toRemove.add(o);
			}		
		}
		for (Object o : toRemove) {
			if (isObelisk(o.newId)) {
				int index = getObeliskIndex(o.newId);
				if (activated[index]) {
					activated[index] = false;
					teleportObelisk(index);
				}
			}
			objects.remove(o);	
		}
		toRemove.clear();
	}
	
	public void removeObject(int x, int y) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client)PlayerHandler.players[j];
				c.getPA().object(-1, x, y, 0, 10);			
			}	
		}	
	}
	
	public void updateObject(Object o) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client)PlayerHandler.players[j];
				c.getPA().object(o.newId, o.objectX, o.objectY, o.face, o.type);			
			}	
		}	
	}
	
	public void placeObject(Object o) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client)PlayerHandler.players[j];
				if (c.distanceToPoint(o.objectX, o.objectY) <= 60)
					c.getPA().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
			}	
		}
	}
	
	public Object getObject(int x, int y, int height) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y && o.height == height)
				return o;
		}	
		return null;
	}
	
	public void loadObjects(Client c) {
		if (c == null)
			return;
		for (Object o : objects) {
			if (loadForPlayer(o,c))
				c.getPA().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
		}
		loadCustomSpawns(c);
		if (c.distanceToPoint(2813, 3463) <= 60) {
			c.getFarming().updateHerbPatch();
		}
	}
	
	public void loadCustomSpawns(Client c) {

			//left side
		c.getPA().checkObjectSpawn(1032, 3009, 3359, 1, 10);//wild sign, fun pk
		c.getPA().checkObjectSpawn(1032, 3009, 3360, 1, 10);//wild sign, fun pk
		c.getPA().checkObjectSpawn(1032, 3009, 3361, 1, 10);//wild sign, fun pk
		c.getPA().checkObjectSpawn(1032, 3009, 3365, 1, 10);//wild sign, fun pk
		c.getPA().checkObjectSpawn(1032, 3009, 3366, 1, 10);//wild sign, fun pk
		c.getPA().checkObjectSpawn(1032, 3009, 3367, 1, 10);//wild sign, fun pk
		c.getPA().checkObjectSpawn(1032, 3009, 3368, 1, 10);//wild sign, fun pk

			//right side
		c.getPA().checkObjectSpawn(1032, 3021, 3357, 3, 10);//wild sign, fun pk
		c.getPA().checkObjectSpawn(1032, 3021, 3358, 3, 10);//wild sign, fun pk
		c.getPA().checkObjectSpawn(1032, 3021, 3359, 3, 10);//wild sign, fun pk
		c.getPA().checkObjectSpawn(1032, 3021, 3360, 3, 10);//wild sign, fun pk
		c.getPA().checkObjectSpawn(1032, 3021, 3364, 3, 10);//wild sign, fun pk
		c.getPA().checkObjectSpawn(1032, 3021, 3365, 3, 10);//wild sign, fun pk
		c.getPA().checkObjectSpawn(1032, 3021, 3366, 3, 10);//wild sign, fun pk
		c.getPA().checkObjectSpawn(1032, 3021, 3367, 3, 10);//wild sign, fun pk

		c.getPA().checkObjectSpawn(410, 3019, 3357, 1, 10);
		c.getPA().checkObjectSpawn(410, 3089, 3501, 1, 10);
		c.getPA().checkObjectSpawn(409, 3014, 3366, 2, 10);
		c.getPA().checkObjectSpawn(409, 3329, 3330, 2, 0);
		c.getPA().checkObjectSpawn(6552, 3327, 3330, 1, 0);
		c.getPA().checkObjectSpawn(410, 3326, 3333, 1, 0);
		c.getPA().checkObjectSpawn(6552, 3017, 3366, 2, 10);
		c.getPA().checkObjectSpawn(6163, 3265, 3159, 0, 10); //one
		c.getPA().checkObjectSpawn(6165, 3267, 3159, 0, 10); //two
		c.getPA().checkObjectSpawn(6166, 3269, 3159, 0, 10); //three
		c.getPA().checkObjectSpawn(6164, 3271, 3159, 0, 10); //four
		c.getPA().checkObjectSpawn(6162, 3264, 3155, 1, 10);//stalls @Five@
		c.getPA().checkObjectSpawn(1755, 3055, 9774, 0, 0);
		c.getPA().checkObjectSpawn(1596, 3008, 3850, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3008, 3849, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3040, 10307, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3040, 10308, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3022, 10311, -1, 0);
		c.getPA().checkObjectSpawn(2472, 3089, 3498, 0, 10);
		c.getPA().checkObjectSpawn(4122, 3095, 3494, 0, 10);
		c.getPA().checkObjectSpawn(1596, 3022, 10312, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3044, 10341, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3044, 10342, 1, 0);
		c.getPA().checkObjectSpawn(2213, 3047, 9779, 1, 10);
		c.getPA().checkObjectSpawn(2213, 3080, 9502, 1, 10);
		c.getPA().checkObjectSpawn(2475, 3233, 9312, 1, 10);
		c.getPA().checkObjectSpawn(4551, 2522, 3595, 1, 10);
		c.getPA().checkObjectSpawn(3192, 3084, 3502, 1, 10); //highscores
		c.getPA().checkObjectSpawn(6552, 2844, 2958, -3, 10); // New home item(s)
		c.getPA().checkObjectSpawn(2213, 2851, 2957, -2, 10);
		c.getPA().checkObjectSpawn(2213, 2853, 2957, -2, 10);
		c.getPA().checkObjectSpawn(409, 2844, 2962, -3, 10);
c.getPA().checkObjectSpawn(11758, 2717, 9830, 0, 10);//bank booth at dz
c.getPA().checkObjectSpawn(11758, 2716, 9830, 0, 10);//bank booth at dz
c.getPA().checkObjectSpawn(11758, 2715, 9830, 0, 10);//bank booth at dz

		c.getPA().checkObjectSpawn(409, 3093, 3506, -2, 10);
		c.getPA().checkObjectSpawn(6552, 3096, 3506, -2, 10);

			//c.getPA().checkObjectSpawn(6552, 3423, 3562, -2, 10);
		//stalls
		c.getPA().checkObjectSpawn(6163, 2951, 3373, 3, 10);
		c.getPA().checkObjectSpawn(6165, 2951, 3371, 3, 10);
		c.getPA().checkObjectSpawn(6166, 2951, 3369, 3, 10);
		c.getPA().checkObjectSpawn(6164, 2951, 3367, 3, 10);
		c.getPA().checkObjectSpawn(6162, 2953, 3376, 3, 10);
		//premium isle banks

		c.getPA().checkObjectSpawn(2472, 2848, 2955, -2, 10);

//NEW stalls
		c.getPA().checkObjectSpawn(6163, 3175, 3403, 1, 10); //first stall
		c.getPA().checkObjectSpawn(6165, 3175, 3406, 1, 10); //second
		c.getPA().checkObjectSpawn(6166, 3175, 3409, 1, 10); //third
		c.getPA().checkObjectSpawn(6164, 3175, 3412, 1, 10); //fourth
		c.getPA().checkObjectSpawn(6162, 3175, 3415, 1, 10); //fifth

		c.getPA().checkObjectSpawn(4874, 3091, 3487, 1, 10); //first stall
		c.getPA().checkObjectSpawn(4875, 3093, 3487, 1, 10); //second
		c.getPA().checkObjectSpawn(4876, 3095, 3487, 1, 10); //third
		c.getPA().checkObjectSpawn(4877, 3097, 3487, 1, 10); //fourth
		c.getPA().checkObjectSpawn(4878, 3099, 3487, 1, 10); //fifth
//end of new stalls
		c.getPA().checkObjectSpawn(2213, 2529, 4778, 1, 10);
		c.getPA().checkObjectSpawn(2213, 2529, 4777, 1, 10);
		c.getPA().checkObjectSpawn(2213, 2529, 4776, 1, 10);


	//premium isle mining rocks
		//c.getPA().checkObjectSpawn(14859, 2523, 4771, 1, 10);
		//c.getPA().checkObjectSpawn(14859, 2524, 4771, 1, 10);
		//c.getPA().checkObjectSpawn(14859, 2525, 4771, 1, 10);
		//c.getPA().checkObjectSpawn(14859, 2526, 4771, 1, 10);
c.getPA().checkObjectSpawn(14859, 2726, 9815, 1, 10);
c.getPA().checkObjectSpawn(14859, 2726, 9814, 1, 10);
c.getPA().checkObjectSpawn(14859, 2726, 9813, 1, 10);
c.getPA().checkObjectSpawn(14859, 2726, 9812, 1, 10);


	//premium isle trees
		//c.getPA().checkObjectSpawn(1306, 2524, 4782, 1, 10);
		//c.getPA().checkObjectSpawn(1309, 2527, 4782, 1, 10);
c.getPA().checkObjectSpawn(1306, 2728, 9820, 1, 10);//new, magic
c.getPA().checkObjectSpawn(1309, 2727, 9817, 1, 10);//new, yew


	//premium isle smithing
		//c.getPA().checkObjectSpawn(2781, 2518, 4776, 4, 10);
		//c.getPA().checkObjectSpawn(2782, 2519, 4779, 1, 10);
c.getPA().checkObjectSpawn(2781, 2708, 9816, 4, 10);//new, furnace
c.getPA().checkObjectSpawn(2782, 2709, 9819, 1, 10);//new, anvil


	//premium isle altar
		//c.getPA().checkObjectSpawn(409, 2529, 4774, 3, 10);
c.getPA().checkObjectSpawn(409, 2721, 9828, -2, 10);
c.getPA().checkObjectSpawn(-1, 2722, 9828, 1, 10);


	//premium isle range
		c.getPA().checkObjectSpawn(2728, 2525, 4787, 0, 10);
		if (c.heightLevel == 0)
			c.getPA().checkObjectSpawn(2492, 2911, 3614, 1, 10);
		else
			c.getPA().checkObjectSpawn(-1, 2911, 3614, 1, 10);
	}
	
	public final int IN_USE_ID = 14825;
	public boolean isObelisk(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return true;
		}
		return false;
	}
	public int[] obeliskIds = {14829,14830,14827,14828,14826,14831};
	public int[][] obeliskCoords = {{3154,3618},{3225,3665},{3033,3730},{3104,3792},{2978,3864},{3305,3914}};
	public boolean[] activated = {false,false,false,false,false,false};
	
	public void startObelisk(int obeliskId) {
		int index = getObeliskIndex(obeliskId);
		if (index >= 0) {
			if (!activated[index]) {
				activated[index] = true;
				addObject(new Object(14825, obeliskCoords[index][0], obeliskCoords[index][1], 0, -1, 10, obeliskId,16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1], 0, -1, 10, obeliskId,16));
				addObject(new Object(14825, obeliskCoords[index][0], obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId,16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId,16));
			}
		}	
	}
	
	public int getObeliskIndex(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return j;
		}
		return -1;
	}
	
	public void teleportObelisk(int port) {
		int random = Misc.random(5);
		while (random == port) {
			random = Misc.random(5);
		}
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client)PlayerHandler.players[j];
				int xOffset = c.absX - obeliskCoords[port][0];
				int yOffset = c.absY - obeliskCoords[port][1];
				if (c.goodDistance(c.getX(), c.getY(), obeliskCoords[port][0] + 2, obeliskCoords[port][1] + 2, 1)) {
					c.getPA().startTeleport2(obeliskCoords[random][0] + xOffset, obeliskCoords[random][1] + yOffset, 0);
				}
			}		
		}
	}
	
	public boolean loadForPlayer(Object o, Client c) {
		if (o == null || c == null)
			return false;
		return c.distanceToPoint(o.objectX, o.objectY) <= 60 && c.heightLevel == o.height;
	}
	
	public void addObject(Object o) {
		if (getObject(o.objectX, o.objectY, o.height) == null) {
			objects.add(o);
			placeObject(o);
		}	
	}




}