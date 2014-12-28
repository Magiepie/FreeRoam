package server.model.players.packets;

import server.Config;
import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.PlayerSave;

/**
 * Drop Item
 **/
public class DropItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readUnsignedWordA();
		c.getInStream().readUnsignedByte();
		c.getInStream().readUnsignedByte();
		int slot = c.getInStream().readUnsignedWordA();
		if (Server.playerHandler.players[c.playerId].underAttackBy != 0) {
			if ((c.getShops().getItemShopValue(itemId)*.75) > 1000) {
			c.sendMessage("You can't drop items worth over 1,000 gold in combat.");
			return;
			}
		}
		if(c.inTrade) {
			c.sendMessage("You can't drop items while trading!");
			return;
		}
		if(!c.getItems().playerHasItem(itemId,1,slot)) {
			return;
		}
		if(c.tradeTimer > 0) {
			c.sendMessage("You must wait until your starter timer is up to drop items.");
			return;
		}
		if(c.trade11 > 0) {
			int mins = c.trade11 / 60;
			if (c.trade11 > 59) {
			c.sendMessage("@red@You can not drop items scince you are new so please wait another "+mins+" minutes.");
			} else if (c.trade11 < 60) {
			c.sendMessage("@red@You can not drop items scince you are new so please wait another "+c.trade11+" seconds.");
			}
		return;
		}
		if(c.arenas()) {
			c.sendMessage("You can't drop items inside the arena!");
			return;
		}

		boolean droppable = true;
		for (int i : Config.UNDROPPABLE_ITEMS) {
			if (i == itemId) {
				droppable = false;
				break;
			}
		}
		if(c.playerItemsN[slot] != 0 && itemId != -1 && c.playerItems[slot] == itemId + 1) {
			if(droppable) {
				if (c.underAttackBy > 0) {
					if (c.getShops().getItemShopValue(itemId) > 1000) {
						c.sendMessage("You can't drop items worth over 1,000 gold in combat.");
						return;
					}
				}
				Server.itemHandler.createGroundItem(c, itemId, c.getX(), c.getY(), c.playerItemsN[slot], c.getId());
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				PlayerSave.saveGame(c);
			} else {
				c.sendMessage("This item cannot be dropped.");
			}
		}

	}
}
