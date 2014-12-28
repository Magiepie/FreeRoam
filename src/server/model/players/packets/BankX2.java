package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;

/**
 * Entering an X amount of items to be banked, traded, or duelled.
 */

public class BankX2 implements PacketType {
	
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int amount = c.getInStream().readDWord();
		if (amount == 0) {
			amount = 1;
		}
		switch (c.xInterfaceId) {
			case 5064: 
			if(c.inTrade) {
				c.sendMessage("You can't store items while trading!");
				return;
			}
			c.getItems().bankItem(c.playerItems[c.xRemoveSlot] , c.xRemoveSlot, amount > c.getItems().getItemAmount(c.xRemoveId) ? c.getItems().getItemAmount(c.xRemoveId) : amount); 
			break;
				
			case 5382: c.getItems().fromBank(c.bankItems[c.xRemoveSlot] , c.xRemoveSlot, amount > c.getItems().getBankAmount(c.xRemoveId) ? c.getItems().getBankAmount(c.xRemoveId) : amount); break;
				
			case 3322:
				if (c.duelStatus <= 0) {
					c.getTradeAndDuel().tradeItem(c.xRemoveId, c.xRemoveSlot, amount > c.getItems().getItemAmount(c.xRemoveId) ? c.getItems().getItemAmount(c.xRemoveId) : amount);
				} else {				
					c.getTradeAndDuel().stakeItem(c.xRemoveId, c.xRemoveSlot, amount > c.getItems().getItemAmount(c.xRemoveId) ? c.getItems().getItemAmount(c.xRemoveId) : amount);
				}
				break;
				
			case 3415:
				if (c.duelStatus <= 0) { 
					//c.getTradeAndDuel().fromTrade(c.xRemoveId, c.xRemoveSlot, amount > c.getItems().getItemAmount(c.xRemoveId) ? c.getItems().getItemAmount(c.xRemoveId) : amount);
				} 
				break;
				
			case 6669: c.getTradeAndDuel().fromDuel(c.xRemoveId, c.xRemoveSlot, amount > c.getItems().getItemAmount(c.xRemoveId) ? c.getItems().getItemAmount(c.xRemoveId) : amount); break;			
		}
	}
}