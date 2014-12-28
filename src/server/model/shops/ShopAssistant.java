package server.model.shops;

import server.Config;
import server.Server;
import server.model.items.Item;
import server.model.players.Client;
import server.model.players.PlayerHandler;
import server.world.ShopHandler;

public class ShopAssistant {

	private Client c;
	
	public ShopAssistant(Client client) {
		this.c = client;
	}
	
	/**
	*Shops
	**/
	
	public void openShop(int ShopID){		
		c.getItems().resetItems(3823);
		resetShop(ShopID);
		c.isShopping = true;
		c.myShopId = ShopID;
		c.getPA().sendFrame248(3824, 3822);
		c.getPA().sendFrame126(ShopHandler.ShopName[ShopID], 3901);
	}
		//if(itemId != itemId) {
		//	c.sendMessage("Don't dupe or you will be IP Banned");
		//	return false;
		//}
	public void updatePlayerShop() {
		for (int i = 1; i < Config.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				if (PlayerHandler.players[i].isShopping == true && PlayerHandler.players[i].myShopId == c.myShopId && i != c.playerId) {
					PlayerHandler.players[i].updateShop = true;
				}
			}
		}
	}
	
	
	public void updateshop(int i){
		resetShop(i);
	}
	
	public void resetShop(int ShopID) {
		synchronized(c) {
			int TotalItems = 0;
			for (int i = 0; i < ShopHandler.MaxShopItems; i++) {
				if (ShopHandler.ShopItems[ShopID][i] > 0) {
					TotalItems++;
				}
			}
			if (TotalItems > ShopHandler.MaxShopItems) {
				TotalItems = ShopHandler.MaxShopItems;
			}
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(3900);
			c.getOutStream().writeWord(TotalItems);
			int TotalCount = 0;
			for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
				if (ShopHandler.ShopItems[ShopID][i] > 0 || i <= ShopHandler.ShopItemsStandard[ShopID]) {
					if (ShopHandler.ShopItemsN[ShopID][i] > 254) {
						c.getOutStream().writeByte(255); 					
						c.getOutStream().writeDWord_v2(ShopHandler.ShopItemsN[ShopID][i]);	
					} else {
						c.getOutStream().writeByte(ShopHandler.ShopItemsN[ShopID][i]);
					}
					if (ShopHandler.ShopItems[ShopID][i] > Config.ITEM_LIMIT || ShopHandler.ShopItems[ShopID][i] < 0) {
						ShopHandler.ShopItems[ShopID][i] = Config.ITEM_LIMIT;
					}
					c.getOutStream().writeWordBigEndianA(ShopHandler.ShopItems[ShopID][i]);
					TotalCount++;
				}
				if (TotalCount > TotalItems) {
					break;
				}
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();	
		}
	}
	
	
	public double getItemShopValue(int ItemID, int Type, int fromSlot) {
		double ShopValue = 1;
		double TotPrice = 0;
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					ShopValue = Server.itemHandler.ItemList[i].ShopValue;
				}
			}
		}
		
		TotPrice = ShopValue;

		if (ShopHandler.ShopBModifier[c.myShopId] == 1) {
			TotPrice *= 1; 
			TotPrice *= 1;
			if (Type == 1) {
				TotPrice *= 1; 
			}
		} else if (Type == 1) {
			TotPrice *= 1; 
		}
		return TotPrice;
	}
	
	public int getItemShopValue(int itemId) {
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == itemId) {
					return (int)Server.itemHandler.ItemList[i].ShopValue;
				}
			}	
		}
		return 0;
	}
	
	
	
	/**
	*buy item from shop (Shop Price)
	**/
	
	public void buyFromShopPrice(int removeId, int removeSlot){
		int ShopValue = (int)Math.floor(getItemShopValue(removeId, 0, removeSlot));
		ShopValue *= 1.15;
		String ShopAdd = "";
		if (c.myShopId == 17 || c.myShopId == 4 || c.myShopId == 12) {
			c.sendMessage(c.getItems().getItemName(removeId)+": currently costs " + getSpecialItemValue(removeId) + " points.");
			return;
		}
		if (c.myShopId == 14) {
			c.sendMessage(c.getItems().getItemName(removeId)+": currently costs " + getSpecialItemValue(removeId) + " Vote points.");
			return;
		}
		if (c.myShopId == 19 ) {
			c.sendMessage("This item current costs " + c.getItems().getUntradePrice(removeId) + " coins.");
			return;
		}
		if (ShopValue >= 1000 && ShopValue < 1000000) {
			ShopAdd = " (" + (ShopValue / 1000) + "K)";
		} else if (ShopValue >= 1000000) {
			ShopAdd = " (" + (ShopValue / 1000000) + " million)";
		}
		c.sendMessage(c.getItems().getItemName(removeId)+": currently costs "+ShopValue+" coins"+ShopAdd);
	}
	
	public int getSpecialItemValue(int id) {
		switch (id) {
			case 6889:
			case 6914:
			return 200;
			case 6916:
			case 6918:
			case 6920:
			case 6922:
			case 6924:
			case 6199:
			return 50;
			case 13887:
			case 13893:
			return 450;
			case 13899:
			return 1000;
			case 13742:
			case 13744:
			return 550;
			case 15038:
			return 750;
			case 13870:
			case 13873:
			case 13876:
			case 15887:
			case 15888:
			return 300;
			case 11728:
			return 225;
			case 13896:
			case 13890:
			case 13884:
			return 450;
			case 15001:
			case 15040:
			return 550;
			case 15000:
			return 250;
			case 2438:
			return 5;
			case 13902:
			return 800;
			case 15037:
			case 11724:
			case 11726:
			return 700;
			case 13858:
			case 13861:
			case 13864:
			return 425;
			case 13867:
			return 500;
			case 13738:
			return 450;
			case 11718:
			case 11720:
			case 11722:
			return 650;
			case 11663:
			case 11664:
			case 11665:
			case 8842:
			case 3481:
			case 3483:
			case 3485:
			case 3486:
			case 3488:
			return 30;
			case 8839:
			case 8840:
			return 75;
			case 10499:
			return 20;
			case 8845:
			return 5;
			case 13739:
			case 13740:
			return 850;
			case 15039:
			return 1100;
			case 8846:
			return 10;
			case 8847:
			return 15;
			case 8848:
			return 20;
			case 14484:
			return 850;
			case 8849:
			case 8850:
			return 25;
			case 7462:
			return 40;
			case 10551:
			return 60;
			case 15018:
			case 15019:
			case 15020:
			case 15220:
			return 150;
			case 15990:
			return 800;
			case 15999:
			return 150;
			case 15998:
			return 200;
			case 11716:
			case 11730:
			return 400;
			case 15997:
			return 250;
			case 15996:
			return 300;
			case 15995:
			return 350;
			case 15994:
			return 400;
			case 15993:
			return 450;
			case 15992:
			return 500;
			case 15991:
			return 550;
			case 15989:
			return 600;
			case 15988:
			return 650;
			case 15987:
			return 700;
			case 11694:
			return 700;
			case 11696:
			case 11698:
			case 11700:
			return 550;
		}
		return 0;
	}
	
	
	
	/**
	*Sell item to shop (Shop Price)
	**/
	/*public void sellToShopPrice(int removeId, int removeSlot) {
		for (int i : Config.ITEM_SELLABLE) {
			if (i == removeId) {
				c.sendMessage("You can't sell "+c.getItems().getItemName(removeId).toLowerCase()+".");
				return;
			} 
		}
		boolean IsIn = false;
		if (Server.shopHandler.ShopSModifier[c.myShopId] > 1) {
			for (int j = 0; j <= Server.shopHandler.ShopItemsStandard[c.myShopId]; j++) {
				if (removeId == (Server.shopHandler.ShopItems[c.myShopId][j] - 1)) {
					IsIn = true;
					break;
				}
			}
		} else {
			IsIn = true;
		}
		if (IsIn == false) {
			c.sendMessage("You can't sell "+c.getItems().getItemName(removeId).toLowerCase()+" to this shop.");
		} else {
			int ShopValue = (int)Math.floor(getItemShopValue(removeId, 1, removeSlot));
			String ShopAdd = "";
			if (ShopValue >= 1000 && ShopValue < 1000000) {
				ShopAdd = " (" + (ShopValue / 1000) + "K)";
			} else if (ShopValue >= 1000000) {
				ShopAdd = " (" + (ShopValue / 1000000) + " million)";
			}
			c.sendMessage(c.getItems().getItemName(removeId)+": shop will buy for "+ShopValue+" coins"+ShopAdd);
		}
	}*/
public void sellToShopPrice(int removeId, int removeSlot) {
		for (int i : Config.ITEM_SELLABLE) {
			if (i == removeId) {
				c.sendMessage("You can't sell "
						+ c.getItems().getItemName(removeId).toLowerCase()
						+ ".");
				return;
			}
		}
		boolean IsIn = false;
		if (ShopHandler.ShopSModifier[c.myShopId] > 1) {
			for (int j = 0; j <= ShopHandler.ShopItemsStandard[c.myShopId]; j++) {
				if (removeId == (ShopHandler.ShopItems[c.myShopId][j] - 1)) {
					IsIn = true;
					break;
				}
			}
		} else {
			IsIn = true;
		}
		if (IsIn == false) {
			c.sendMessage("You can't sell "
					+ c.getItems().getItemName(removeId).toLowerCase()
					+ " to this store.");
			return;
		}
		int ShopValue = (int) Math.floor(getItemShopValue(removeId, 1,
				removeSlot));
		String ShopAdd = "";
		if (ShopValue >= 1000 && ShopValue < 1000000) {
			ShopAdd = " (" + (ShopValue / 1000) + "K)";
		} else if (ShopValue >= 1000000) {
			ShopAdd = " (" + (ShopValue / 1000000) + " million)";
		}
		c.sendMessage(c.getItems().getItemName(removeId)
				+ ": shop will buy for " + ShopValue + " coins" + ShopAdd);
	}


	public boolean shopSellsItem(int itemID) {
		for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
			if(itemID == (ShopHandler.ShopItems[c.myShopId][i] - 1)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	@SuppressWarnings("unused")
	public boolean sellItem(int itemID, int fromSlot, int amount) {
			if(c.inTrade) {
            		c.sendMessage("You cant sell items to the shop while your in trade!");
           		return false;
            		}
		//if (c.myShopId == 14)
			//return false;
		for (int i : Config.ITEM_SELLABLE) {
			if (i == itemID) {
				c.sendMessage("You can't sell "+c.getItems().getItemName(itemID).toLowerCase()+".");
				return false;
			} 
		}
		if(c.playerRights == 2 && !Config.ADMIN_CAN_SELL_ITEMS) {
			c.sendMessage("Selling items as an admin has been disabled.");
			return false;
		}
		
		if (amount > 0 && itemID == (c.playerItems[fromSlot] - 1)) {
			if (ShopHandler.ShopSModifier[c.myShopId] > 1) {
				boolean IsIn = false;
				for (int i = 0; i <= ShopHandler.ShopItemsStandard[c.myShopId]; i++) {
					if (itemID == (ShopHandler.ShopItems[c.myShopId][i] - 1)) {
						IsIn = true;
						break;
					}
				}
				if (IsIn == false) {
					c.sendMessage("You can't sell "+c.getItems().getItemName(itemID).toLowerCase()+" to this store.");
					return false;
				}
			}

			if (amount > c.playerItemsN[fromSlot] && (Item.itemIsNote[(c.playerItems[fromSlot] - 1)] == true || Item.itemStackable[(c.playerItems[fromSlot] - 1)] == true)) {
				amount = c.playerItemsN[fromSlot];
			} else if (amount > c.getItems().getItemAmount(itemID) && Item.itemIsNote[(c.playerItems[fromSlot] - 1)] == false && Item.itemStackable[(c.playerItems[fromSlot] - 1)] == false) {
				amount = c.getItems().getItemAmount(itemID);
			}
			//double ShopValue;
			//double TotPrice;
			int TotPrice2 = 0;
			//int Overstock;
			for (int i = amount; i > 0; i--) {
				TotPrice2 = (int)Math.floor(getItemShopValue(itemID, 1, fromSlot));
				if (c.getItems().freeSlots() > 0 || c.getItems().playerHasItem(995)) {
					if (Item.itemIsNote[itemID] == false) {
						c.getItems().deleteItem(itemID, c.getItems().getItemSlot(itemID), 1);
					} else {
						c.getItems().deleteItem(itemID, fromSlot, 1);
					}
					c.getItems().addItem(995, TotPrice2);
					addShopItem(itemID, 1);
				} else {
					c.sendMessage("You don't have enough space in your inventory.");
					break;
				}
			}
			c.getItems().resetItems(3823);
			resetShop(c.myShopId);
			updatePlayerShop();
			return true;
		}
		return true;
	}
	
public boolean addShopItem(int itemID, int amount) {
		boolean Added = false;
		if (amount <= 0) {
			return false;
		}
		if (Item.itemIsNote[itemID] == true) {
			itemID = c.getItems().getUnnotedItem(itemID);
		}
		for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
			if ((ShopHandler.ShopItems[c.myShopId][i] - 1) == itemID) {
				ShopHandler.ShopItemsN[c.myShopId][i] += amount;
				Added = true;
			}
		}
		if (Added == false) {
			for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
				if (ShopHandler.ShopItems[c.myShopId][i] == 0) {
					ShopHandler.ShopItems[c.myShopId][i] = (itemID + 1);
					ShopHandler.ShopItemsN[c.myShopId][i] = amount;
					ShopHandler.ShopItemsDelay[c.myShopId][i] = 0;
					break;
				}
			}
		}
		return true;
	}
	
        //How much money does the user have?
        public int getCurrency(int currency){
                switch(currency){
                        case 1:
                                return c.getItems().getItemAmount(995);//GP
                        case 2:
                                return c.getItems().getItemAmount(6529);//Tokkul
                        case 3:
                                return c.pkPoints;//Pking Points
                        case 4:
                                return c.votingPoints;//Vote points
                        /*case 5:
                                return c.dungPoints;//Dungeoneering points
                        case 6:
                                return c.SPoints;//Slayer points*/
                        default:
                                return 0;
                }
        }
        
        public String  getNameForCurrency(int currency){
                switch(currency){
                        case 1:
                                return "coins";//GP
                        case 2:
                                return "tokkul";//Tokkul
                        case 3:
                                return "Points";//Pking Points
                        case 4:
                                return "vote points";//Vote points
                        case 5:
                                return "dungeoneering points";//Dungeoneering points
                        case 6:
                                return "slayer points";//Slayer points
                        default:
                                return "unknown";
                }
        }
        
        //Need to find a way to handle point remove.. A bit high :P
        public void handleCurrency(int currencyNumber,int amountToRemove){
                switch(currencyNumber){
                        case 1:
                                c.getItems().deleteItem(995, c.getItems().getItemSlot(995), amountToRemove);//Coins
                                break;
                        case 2:
                                c.getItems().deleteItem(6529, c.getItems().getItemSlot(6529), amountToRemove);//Tokkul
                                break;
                        case 3:
                                c.pkPoints -= amountToRemove;//Pking Points
                                break;
                        case 4:
                                c.votingPoints -= amountToRemove;//Vote points
                                break;
                      /*  case 5:
                                c.dungPoints -= amountToRemove;//Dungeoneering points
                                break;
                        case 6:
                                c.SPoints -= amountToRemove;//Slayer points
                                break;*/
                }
        }
        public void buyItem(int itemID, int fromSlot, int amount) {
                //This should be essentially equal how much money the player will spend. PI originally handled this separately.
                int totalPrice = ((int)Math .floor(getItemShopValue(itemID, 0, fromSlot)) * amount);
                int currNum = 1;//default currency is 1, which is gp.
                //Don't be a jackass when using this switch statement, it's very straightforward.
                switch(c.myShopId){
                        case 4:
                        case 17:
                        case 12:
                                currNum = 3;//Pk points
                                break;
                        case 29:
                        case 30:
                        case 31:
                                currNum = 2;//Tokkul
                                break;
                        case 48:
                                currNum = 6;//Slayer Points
                                break;
                        case 14:
                                currNum = 4;//Vote points
                                break;
                        case 79:
                                currNum = 5;//Dungeoneering points
                                break;
                        default:
                                currNum = 1;//Coins
                                break;
                }
               
                if(amount == 0){//If you somehow try and buy 0 of something...
                        c.sendMessage("Buy something you jackass.");
                }
               
                //If the currency is not coins, set the price to special items
                if(currNum > 1){
                        totalPrice = ((int)Math .floor(getSpecialItemValue(itemID) * amount));
                }
               
                if (c.myShopId == 19) {//Buying skillcapes - Will do something for this later...
                        skillBuy(itemID);
                        return;
                }
               
                if(itemID != itemID) {//Leaving this because somehow this stops a dupe?
                        c.sendMessage("Don't dupe or you will be IP Banned");
                        return;
                }
 
                if(!shopSellsItem(itemID))//Dupe prevention I assume
                        return;
 
                if (amount > ShopHandler.ShopItemsN[c.myShopId][fromSlot]) {//Just in case they go for more than the shop has.
                        amount = ShopHandler.ShopItemsN[c.myShopId][fromSlot];
                }
               // c.sendMessage(":"+getCurrency(currNum)+" Total Price:"+totalPrice);
                if (getCurrency(currNum) >= totalPrice) {//Checking if they have enough.
                        if((c.getItems().freeSlots() > amount) || (c.getItems().freeSlots() >= 0 && Item.itemStackable[itemID])){//Making sure they have enough spaces.
                                handleCurrency(currNum, totalPrice);//removing currency for item
                                if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
                                        ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
                                }
                        } else {
                                c.sendMessage("You don't have enough space in your inventory.");
                                return;
                        }
                } else {
                        c.sendMessage("You don't have enough "+getNameForCurrency(currNum)+" to buy this.");
                        return;
                }
                //These happen regardless...
                c.getItems().addItem(itemID, amount);
                ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= amount;
                ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
                //Resetting the shop
                c.getItems().resetItems(3823);
                resetShop(c.myShopId);
                updatePlayerShop();//Pretty sure we don't have player shops....
        }	
	
		public void handleOtherShop(int itemID) {
			if (c.myShopId == 17 || c.myShopId == 12) {
				if (c.pkPoints >= getSpecialItemValue(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.pkPoints -= getSpecialItemValue(itemID);
						c.getItems().addItem(itemID,1);	
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough points to buy this item.");			
				}
			} else if (c.myShopId == 4) {
				if (c.pkPoints >= getSpecialItemValue(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.pkPoints -= getSpecialItemValue(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough points to buy this item.");			
				}
			} else if (c.myShopId == 14) {
				if (c.votingPoints >= getSpecialItemValue(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.votingPoints -= getSpecialItemValue(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough vote points to buy this item.");			
				}

			}
		}
		
		public void openSkillCape() {
			int capes = get99Count();
			if (capes > 1)
				capes = 1;
			else
				capes = 0;
			c.myShopId = 19;
			setupSkillCapes(capes, get99Count());		
		}
		
		
		
		/*public int[][] skillCapes = {{0,9747,4319,2679},{1,2683,4329,2685},{2,2680,4359,2682},{3,2701,4341,2703},{4,2686,4351,2688},{5,2689,4347,2691},{6,2692,4343,2691},
									{7,2737,4325,2733},{8,2734,4353,2736},{9,2716,4337,2718},{10,2728,4335,2730},{11,2695,4321,2697},{12,2713,4327,2715},{13,2725,4357,2727},
									{14,2722,4345,2724},{15,2707,4339,2709},{16,2704,4317,2706},{17,2710,4361,2712},{18,2719,4355,2721},{19,2737,4331,2739},{20,2698,4333,2700}};*/
		public int[] skillCapes = {9747,9753,9750,9768,9756,9759,9762,9801,9807,9783,9798,9804,9780,9795,9792,9774,9771,9777,9786,9810,9765};
		public int get99Count() {
			int count = 0;
			for (int j = 0; j < c.playerLevel.length; j++) {
				if (c.getLevelForXP(c.playerXP[j]) >= 99) {
					count++;				
				}			
			}		
			return count;
		}
		
		public void setupSkillCapes(int capes, int capes2) {
			synchronized(c) {
				c.getItems().resetItems(3823);
				c.isShopping = true;
				c.myShopId = 24;
				c.getPA().sendFrame248(3824, 3822);
				c.getPA().sendFrame126("Skillcape Shop", 3901);
				
				int TotalItems = 0;
				TotalItems = capes2;
				if (TotalItems > ShopHandler.MaxShopItems) {
					TotalItems = ShopHandler.MaxShopItems;
				}
				c.getOutStream().createFrameVarSizeWord(53);
				c.getOutStream().writeWord(3900);
				c.getOutStream().writeWord(TotalItems);
				int TotalCount = 0;
				for (int i = 0; i < 21; i++) {
					if (c.getLevelForXP(c.playerXP[i]) < 99)
						continue;
					c.getOutStream().writeByte(1);
					c.getOutStream().writeWordBigEndianA(skillCapes[i] + 2);
					TotalCount++;
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();	
			}
		}
		
		public void skillBuy(int item) {
			int nn = get99Count();
			if (nn > 1)
				nn = 1;
			else
				nn = 0;			
			for (int j = 0; j < skillCapes.length; j++) {
				if (skillCapes[j] == item || skillCapes[j]+1 == item) {
					if (c.getItems().freeSlots() > 1) {
						if (c.getItems().playerHasItem(995,99000)) {
							if (c.getLevelForXP(c.playerXP[j]) >= 99) {
								c.getItems().deleteItem(995, c.getItems().getItemSlot(995), 99000);
								c.getItems().addItem(skillCapes[j] + nn,1);
								c.getItems().addItem(skillCapes[j] + 2,1);
							} else {
								c.sendMessage("You must have 99 in the skill of the cape you're trying to buy.");
							}
						} else {
							c.sendMessage("You need 99k to buy this item.");
						}
					} else {
						c.sendMessage("You must have at least 1 inventory spaces to buy this item.");					
					}				
				}
				if (skillCapes[j] == item) {
					if (c.getItems().freeSlots() >= 1) {
						if (c.getItems().playerHasItem(995,99000)) {
							if (c.getLevelForXP(c.playerXP[j]) >= 99) {
								c.getItems().deleteItem(995, c.getItems().getItemSlot(995), 99000);
								c.getItems().addItem(skillCapes[j] + nn,1);
								c.getItems().addItem(skillCapes[j] + 2,1);
							} else {
								c.sendMessage("You must have 99 in the skill of the cape you're trying to buy.");
							}
						} else {
							c.sendMessage("You need 99k to buy this item.");
						}
					} else {
						c.sendMessage("You must have at least 1 inventory spaces to buy this item.");					
					}
					break;				
				}			
			}
			c.getItems().resetItems(3823);			
		}
		
		public void openVoid() {
			synchronized(c) {
				c.getItems().resetItems(3823);
				c.isShopping = true;
				c.myShopId = 70;
				c.getPA().sendFrame248(3824, 3822);
				c.getPA().sendFrame126("Void Recovery", 3901);
				
				int TotalItems = 5;
				c.getOutStream().createFrameVarSizeWord(53);
				c.getOutStream().writeWord(3900);
				c.getOutStream().writeWord(TotalItems);
				for (int i = 0; i < c.voidStatus.length; i++) {
					c.getOutStream().writeByte(c.voidStatus[i]);
					c.getOutStream().writeWordBigEndianA(2519 + i * 2);
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();	
			}		
		}

		public void buyVoid(int item) {
			if (item > 2527 || item < 2518)
				return;
			//c.sendMessage("" + item);
			if (c.voidStatus[(item-2518)/2] > 0) {
				if (c.getItems().freeSlots() >= 1) {
					if (c.getItems().playerHasItem(995,c.getItems().getUntradePrice(item))) {
						c.voidStatus[(item-2518)/2]--;
						c.getItems().deleteItem(995,c.getItems().getItemSlot(995), c.getItems().getUntradePrice(item));
						c.getItems().addItem(item,1);
						openVoid();
					} else {
						c.sendMessage("This item costs " + c.getItems().getUntradePrice(item) + " coins to rebuy.");				
					}
				} else {
					c.sendMessage("I should have a free inventory space.");
				}
			} else {
				c.sendMessage("I don't need to recover this item from the void knights.");
			}
		}


}

