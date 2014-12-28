package server.model.minigames;

import server.model.npcs.NPCHandler;
import server.model.players.Client;
import server.model.players.PlayerHandler;
import server.Server;

/**
 * @author Sanity
 */

public class PestControl {
	
	public PestControl() {
		
	}
	
	public final int GAME_TIMER = 140; //10 minute rounds.
	public final int WAIT_TIMER = 30; //Wait timer in boat
	
	public int gameTimer = -1;
	public int waitTimer = 15;
	public int properTimer = 0;
	
	public void process() {
		setInterface(); 
		if (properTimer > 0) {
			properTimer--;
			return;
		} else {
			properTimer = 4;
		}
		if (waitTimer > 0)
			waitTimer--;
		else if (waitTimer == 0)
			startGame();
		if (gameTimer > 0) {
			gameTimer--;
			if (allPortalsDead()) {
				endGame(true);
			}
		} else if (gameTimer == 0)
			endGame(false);
	}
	
	public void startGame() {
		if (playersInBoat() > 0) {
			gameTimer = GAME_TIMER;
			waitTimer = -1;
			//spawn npcs
			spawnNpcs();	
			//move players into game
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					if (PlayerHandler.players[j].inPcBoat()) {
						movePlayer(j);
					}			
				}		
			}
		} else {
			waitTimer = WAIT_TIMER;
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					if (PlayerHandler.players[j].inPcBoat()) {
					}			
				}		
			}
		}
	}
	
	public int playersInBoat() {
		int count = 0;
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].inPcBoat()) {
						count++;
				}
			}
		}
		return count;
	}
	
	public void endGame(boolean won) {
		gameTimer = -1;
		waitTimer = WAIT_TIMER;
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].inPcGame()) {
					Client c = (Client)PlayerHandler.players[j];
					c.getPA().movePlayer(2657, 2639, 0);
					if (won && c.pcDamage > 4) {
						c.sendMessage("Victory! You have been awarded 5 points by the Void knights.");
						c.pcPoints += 5;
						c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
						c.playerLevel[5] = c.getLevelForXP(c.playerXP[5]);
						c.specAmount = 10;
						c.getItems().addItem(995, c.combatLevel * 200);
						c.getPA().refreshSkill(3);
						c.getPA().refreshSkill(5);
					} else if (won) {
						c.sendMessage("The Void knights notice your lack of zeal.");
					} else {
						c.sendMessage("Defeat! You have not gained any points.");
					}
					c.pcDamage = 0;
					c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
					c.getCombat().resetPrayers();
				}			
			}		
		}

		for (int j = 0; j < NPCHandler.npcs.length; j++) {
			if (NPCHandler.npcs[j] != null) {
				if (NPCHandler.npcs[j].npcType >= 3777 && NPCHandler.npcs[j].npcType <= 3780)
					NPCHandler.npcs[j] = null;
			}			
		}
	}
	
	public boolean allPortalsDead() {
		int count = 0;
		for (int j = 0; j < NPCHandler.npcs.length; j++) {
			if (NPCHandler.npcs[j] != null) {
				if (NPCHandler.npcs[j].npcType >= 3777 && NPCHandler.npcs[j].npcType <= 3780)
					if (NPCHandler.npcs[j].needRespawn)
						count++;		
			}			
		}
		return count >= 4;	
	}
	
	public void movePlayer(int index) {
		Client c = (Client)PlayerHandler.players[index];
		if (c.combatLevel < 40) {
			c.sendMessage("You must have a combat level of atleast 40 to enter this boat.");
			return;
		}
		c.getPA().movePlayer(2658,2611,0);
	}

	public void setInterface() {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].inPcBoat()) {
					Client c = (Client)PlayerHandler.players[j];
					c.getPA().sendFrame126("Next Departure: "+waitTimer+"", 21120);
					c.getPA().sendFrame126("Players Ready: "+playersInBoat()+"", 21121);
					c.getPA().sendFrame126("(Need 1 to 25 players)", 21122);
					c.getPA().sendFrame126("Pest Points: "+c.pcPoints+"", 21123);
				}
				if (PlayerHandler.players[j].inPcGame()) {
					Client c = (Client)PlayerHandler.players[j];
					for (j = 0; j < NPCHandler.npcs.length; j++) {
						if (NPCHandler.npcs[j] != null) {
							if (NPCHandler.npcs[j].npcType == 3777)
								c.getPA().sendFrame126("" + NPCHandler.npcs[j].HP + "", 21111);
							if (NPCHandler.npcs[j].npcType == 3778)
								c.getPA().sendFrame126("" + NPCHandler.npcs[j].HP + "", 21112);
							if (NPCHandler.npcs[j].npcType == 3779)
								c.getPA().sendFrame126("" + NPCHandler.npcs[j].HP + "", 21113);
							if (NPCHandler.npcs[j].npcType == 3780)
								c.getPA().sendFrame126("" + NPCHandler.npcs[j].HP + "", 21113);
						}
					}
					c.getPA().sendFrame126("100", 21115);
					c.getPA().sendFrame126("0", 21116);
					c.getPA().sendFrame126("Time remaining: "+gameTimer+"", 21117);

				}
			}
		}
	}
	
	public void spawnNpcs() {
		Server.npcHandler.spawnNpc2(3777,2628,2591,0,0,250,0,0,100);
		Server.npcHandler.spawnNpc2(3778,2680,2588,0,0,250,0,0,100);
		Server.npcHandler.spawnNpc2(3779,2669,2570,0,0,250,0,0,100);
		Server.npcHandler.spawnNpc2(3780,2645,2569,0,0,250,0,0,100);
	}


}