package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;

/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ClickItem implements PacketType {

	public void processPacket(Client c, int packetType, int packetSize) {
		int itemSlot = c.getInStream().readUnsignedWordA();
		int itemId = c.getInStream().readUnsignedWordBigEndian();
		if (c.usingCarpet) {
			return;
		}
		if (itemId != c.playerItems[itemSlot] - 1) {
			return;
		}
		if (itemId == 8007) {
			c.getPA().teleTab("varrock");
		}
		if (itemId == 8008) {
			c.getPA().teleTab("lumbridge");
		}
		if (itemId == 8009) {
			c.getPA().teleTab("falador");
		}
		if (itemId == 8010) {
			c.getPA().teleTab("camelot");
		}
		if (itemId == 8011) {
			c.getPA().teleTab("ardy");
		}
		if (itemId == 7478) {
			c.pkPoints += 10;
			c.sendMessage("@red@The dragon token gives you 10 Tyreatta points!");
			c.getItems().deleteItem(7478, 1);
		}
		//PvP artefacts.
		if(itemId == 14892) {
			c.getItems().deleteItem(14892, 1);
			c.getItems().addItem(995, 100000);
			c.sendMessage("You activate the mysterious artefact and recieve 100,000 gold.");
		}
		if(itemId == 14891) {
			c.getItems().deleteItem(14891, 1);
			c.getItems().addItem(995, 200000);
			c.sendMessage("You activate the mysterious artefact and recieve 200,000 gold.");
		}
		if(itemId == 14890) {
			c.getItems().deleteItem(14890, 1);
			c.getItems().addItem(995, 300000);
			c.sendMessage("You activate the mysterious artefact and recieve 300,000 gold.");
		}
		if(itemId == 14889) {
			c.getItems().deleteItem(14889, 1);
			c.getItems().addItem(995, 400000);
			c.sendMessage("You activate the mysterious artefact and recieve 400,000 gold.");
		}
//mystery box
		if (itemId == 6199) {
                int mysteryReward = Misc.random(100);
                if (mysteryReward == 1) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(454, 30);
                	c.sendMessage("You open the box... And get some coal..");
		}
		else if (mysteryReward == 2) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(452, 4);
               		c.sendMessage("You open the box... And get some rune ore..");
		}
		else if (mysteryReward == 3) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(140, 7);
                	c.sendMessage("You open the box... And get some prayer pots");
		}
		else if (mysteryReward == 4) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(392, 20);
                	c.sendMessage("You open the box... And get some Manta's");
		}
		else if (mysteryReward == 5) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(995, 5000000);
                	c.sendMessage("You open the box... And get 5m! Nice");
		}
		else if (mysteryReward == 6) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(561, 50);
                	c.sendMessage("You open the box... And get some nature runes..");
		}
		else if (mysteryReward == 7) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(563, 30);
               		c.sendMessage("You open the box... And get some law runes..");
		}
		else if (mysteryReward == 8) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(565, 60);
                	c.sendMessage("You open the box... And get some blood runes");
		}
		else if (mysteryReward == 9) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(560, 65);
                	c.sendMessage("You open the box... And get some death runes");
		}
		else if (mysteryReward == 10) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(556, 300);
               		c.sendMessage("You open the box... And get some air runes");
		}
		else if (mysteryReward == 11) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(4151, 1);
               		c.sendMessage("You open the box... And get a whip!");
		}
		else if (mysteryReward == 12) {
                	c.getItems().deleteItem(6199, 1);
			c.pkPoints += 20;
               		c.sendMessage("You open the box... And get @red@20 Tyreatta points!");
		}
		else if (mysteryReward == 13) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(2464, 1);
               		c.sendMessage("You open the box... And get some.. Flowers!?");
		}
		else if (mysteryReward == 14) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(3853, 1);
               		c.sendMessage("You open the box... And get a games necklace..");
		}
		else if (mysteryReward == 15) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(14888, 1);
               		c.sendMessage("You open the box... And get some artefact..");
		}
		else if (mysteryReward == 16) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(995, 10000000);
               		c.sendMessage("You open the box... And get 10m! Nice!");
		}
		else if (mysteryReward == 17) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(11694, 1);
               		c.sendMessage("You open the box... And get the top prize! Congrats :)");
		}
		else if (mysteryReward == 18) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(7478, 1);
               		c.sendMessage("You open the box... And get a dragon token!");
		}
		else if (mysteryReward == 19) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(14484, 1);
               		c.sendMessage("You open the box... And get an amulet of fury!");
		}
		else if (mysteryReward == 20) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(6585, 1);
               		c.sendMessage("You open the box... And get and get dragon claws!!");
		}
		else if (mysteryReward > 30) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(995, 1000000);
               		c.sendMessage("You open the box... And get 1m..");
		}
		else if (mysteryReward == 21) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(4151, 1);
               		c.sendMessage("You open the box... And get a whip!");
		}
		else if (mysteryReward == 22) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(4151, 1);
               		c.sendMessage("You open the box... And get a whip!");
		}
		else if (mysteryReward == 23) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(7478, 5);
               		c.sendMessage("You open the box... And get 5 dragon tokens!");
		}
		else if (mysteryReward == 24) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(6570, 1);
               		c.sendMessage("You open the box... And get a fire cape");
		}
		else if (mysteryReward == 25) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(6199, 1);
               		c.sendMessage("You open the box... And get another msytery box!");
		}
		else if (mysteryReward == 26) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(4151, 1);
               		c.sendMessage("You open the box... And get another msytery box!");
		}
		else if (mysteryReward == 27) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(4151, 1);
               		c.sendMessage("You open the box... And get another msytery box!");
		}
		else if (mysteryReward == 28) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(993, 1);
               		c.sendMessage("You open the box... And get another a barrows key!");
		}
		else if (mysteryReward == 29) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(993, 2);
               		c.sendMessage("You open the box... And get another 2 barrows keys!");
		}
		else if (mysteryReward == 30) {
                	c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(993, 3);
               		c.sendMessage("You open the box... And get another 3 barrows keys!");
		}

	}
		if(itemId == 14888) {
			c.getItems().deleteItem(14888, 1);
			c.getItems().addItem(995, 500000);
			c.sendMessage("You activate the mysterious artefact and recieve 500,000 gold.");
		}
		if(itemId == 14887) {
			c.getItems().deleteItem(14887, 1);
			c.getItems().addItem(995, 600000);
			c.sendMessage("You activate the mysterious artefact and recieve 600,000 gold.");
		}
		if(itemId == 14886) {
			c.getItems().deleteItem(14886, 1);
			c.getItems().addItem(995, 700000);
			c.sendMessage("You activate the mysterious artefact and recieve 700,000 gold.");
		}
		if(itemId == 14885) {
			c.getItems().deleteItem(14885, 1);
			c.getItems().addItem(995, 800000);
			c.sendMessage("You activate the mysterious artefact and recieve 800,000 gold.");
		}
		if(itemId == 14884) {
			c.getItems().deleteItem(14884, 1);
			c.getItems().addItem(995, 900000);
			c.sendMessage("You activate the mysterious artefact and recieve 900,000 gold.");
		}
		if(itemId == 14883) {
			c.getItems().deleteItem(14883, 1);
			c.getItems().addItem(995, 1000000);
			c.sendMessage("You activate the mysterious artefact and recieve 1,000,000 gold.");
		}
		if(itemId == 14882) {
			c.getItems().deleteItem(14882, 1);
			c.getItems().addItem(995, 2000000);
			c.sendMessage("You activate the mysterious artefact and recieve 2,000,000 gold.");
		}
		if(itemId == 14881) {
			c.getItems().deleteItem(14881, 1);
			c.getItems().addItem(995, 3000000);
			c.sendMessage("You activate the mysterious artefact and recieve 3,000,000 gold.");
		}
		if(itemId == 14880) {
			c.getItems().deleteItem(14880, 1);
			c.getItems().addItem(995, 4000000);
			c.sendMessage("You activate the mysterious artefact and recieve 4,000,000 gold.");
		}
		if(itemId == 14879) {
			c.getItems().deleteItem(14879, 1);
			c.getItems().addItem(995, 4000000);
			c.sendMessage("You activate the mysterious artefact and recieve 5,000,000 gold.");
		}
		if(itemId == 14878) {
			c.getItems().deleteItem(14878, 1);
			c.getItems().addItem(995, 10000000);
			c.sendMessage("You activate the mysterious artefact and recieve 10,000,000 gold.");
		}
		if(itemId == 14877) {
			c.getItems().deleteItem(14877, 1);
			c.getItems().addItem(995, 5000000);
			c.sendMessage("You activate the mysterious artefact and recieve 5,000,000 gold.");
		}
		if(itemId == 14876) {
			c.getItems().deleteItem(14876, 1);
			c.getItems().addItem(995, 10000000);
			c.sendMessage("You activate the mysterious artefact and recieve 10,000,000 gold.");
		}
		if (itemId == 4251) {
			c.getPA().movePlayer(3565, 3316, 0);
			c.sendMessage("You empty the ectophial.");
			c.getItems().deleteItem(4251,c.getItems().getItemSlot(4251), 1);
			c.getItems().addItem(4252, 1);
		}
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			int a = itemId;
			if (a == 5509)
				pouch = 0;
			if (a == 5510)
				pouch = 1;
			if (a == 5512)
				pouch = 2;
			if (a == 5514)
				pouch = 3;
			c.getPA().fillPouch(pouch);
			return;
		}
		if (c.getHerblore().isUnidHerb(itemId))
			c.getHerblore().handleHerbClick(itemId);
		if (c.getFood().isFood(itemId))
			c.getFood().eat(itemId,itemSlot);
		//ScriptManager.callFunc("itemClick_"+itemId, c, itemId, itemSlot);
		if (c.getPotions().isPotion(itemId))
			c.getPotions().handlePotion(itemId,itemSlot);
		if (c.getPrayer().isBone(itemId))
			c.getPrayer().buryBone(itemId, itemSlot);
		if (itemId == 952) {
			if(c.inArea(3553, 3301, 3561, 3294)) {
				c.teleTimer = 3;
				c.newLocation = 1;
			} else if(c.inArea(3550, 3287, 3557, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 2;
			} else if(c.inArea(3561, 3292, 3568, 3285)) {
				c.teleTimer = 3;
				c.newLocation = 3;
			} else if(c.inArea(3570, 3302, 3579, 3293)) {
				c.teleTimer = 3;
				c.newLocation = 4;
			} else if(c.inArea(3571, 3285, 3582, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 5;
			} else if(c.inArea(3562, 3279, 3569, 3273)) {
				c.teleTimer = 3;
				c.newLocation = 6;
			}
		}
	}

}
