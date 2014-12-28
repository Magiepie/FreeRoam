package server.model.players;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Future;

import org.apache.mina.common.IoSession;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

import server.Config;
import server.Server;
import server.model.items.ItemAssistant;
import server.model.shops.ShopAssistant;
import server.net.HostList;
import server.net.Packet;
import server.net.StaticPacketBuilder;
import server.util.Misc;
import server.util.Stream;
import server.model.npcs.NPCHandler;
import server.model.players.skills.*;
import server.event.EventManager;
import server.event.Event;
import server.event.EventContainer;
//import server.util.SQL;

public class Client extends Player {

	public byte buffer[] = null;
	public Stream inStream = null, outStream = null;
	private IoSession session;
	private ItemAssistant itemAssistant = new ItemAssistant(this);
	private ShopAssistant shopAssistant = new ShopAssistant(this);
	private TradeAndDuel tradeAndDuel = new TradeAndDuel(this);
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private CombatAssistant combatAssistant = new CombatAssistant(this);
	private ActionHandler actionHandler = new ActionHandler(this);
	private PlayerKilling playerKilling = new PlayerKilling(this);
	private DialogueHandler dialogueHandler = new DialogueHandler(this);
	private Queue<Packet> queuedPackets = new LinkedList<Packet>();
	private Potions potions = new Potions(this);
	private PotionMixing potionMixing = new PotionMixing(this);
	private Food food = new Food(this);
	/**
	 * Skill instances
	 */
	private Slayer slayer = new Slayer(this);
	private Runecrafting runecrafting = new Runecrafting(this);
	private Woodcutting woodcutting = new Woodcutting(this);
	private Mining mine = new Mining(this);
	private Agility agility = new Agility(this);
	private Cooking cooking = new Cooking(this);
	private Fishing fish = new Fishing(this);
	private Crafting crafting = new Crafting(this);
	private Smithing smith = new Smithing(this);
	private Prayer prayer = new Prayer(this);
	private Fletching fletching = new Fletching(this);
	private SmithingInterface smithInt = new SmithingInterface(this);
	private Farming farming = new Farming(this);
	private Thieving thieving = new Thieving(this);
	private Firemaking firemaking = new Firemaking(this);
		private Herblore herblore = new Herblore(this);
	//public static int trade11 = 0;
	public int lowMemoryVersion = 0;
	public int timeOutCounter = 0;
	public int returnCode = 2;
	private Future<?> currentTask;
	public boolean WildernessWarning = false;
	public int clawDamage;
	public int clawIndex;
	public int clawType = 0;

	public String lastKilled = "";

	public boolean usingCarpet = false;
	public int itemBeforeCarpet;
	public int clawDelay = 0;
	public int previousDamage;
	public boolean usingClaws = false;


                public void resetRanks() {
                for (int i = 0; i < 10; i++) {
                        ranks[i] = 0;
                        rankPpl[i] = "";
                }
}
	public boolean loadCharBank(String name){
		Scanner in = null;
		try{
			File acc = new File("Data/characters/" + name + ".txt");
			in = new Scanner(acc);
			while(in.hasNextLine()){
				String nextLine = in.nextLine();
				if(nextLine.contains("character-bank")){
					String line = nextLine.split("=")[1];
					line = line.trim();
					String stuff[] = line.split("	");
					int key = Integer.parseInt(stuff[0].trim());
					int id = Integer.parseInt(stuff[1].trim());
					int amount = Integer.parseInt(stuff[2].trim());
					this.oBankItems[key] = id;
					this.oBankItemsN[key] = amount;
				}else if(nextLine.equalsIgnoreCase("[friends]"))
					break;
			}
		}catch(Exception e){
			return false;
		}
		in.close();
		return true;
		
	}
        public void highscores() {
getPA().sendFrame126("Highscores", 6399);
                for(int i = 0; i < 10; i++) {
                        if(ranks[i] > 0) {
                                getPA().sendFrame126("Rank "+(i+1)+": "+rankPpl[i]+ "- Total Level: " +ranks[i], 6402+i);
                        }
                }
                getPA().showInterface(6308);
                flushOutStream();
                resetRanks();
        }
        public int playerRank = 0;
        public static int[] ranks = new int[11];
    public static String[] rankPpl = new String[11];

	public void clueScroll(int i1, int a1, int i2, int a2, int i3, int a3, int i4,int a4,int clueID){
		getPA().showInterface(6960);
		getPA().sendFrame34a(6963,i1, 0,a1);
		getPA().sendFrame34a(6963, i2, 1, a2);
		getPA().sendFrame34a(6963, i3, 2, a3);
		getPA().sendFrame34a(6963, i4, 3, a4);
		/*addItem(i1,a1);
		addItem(i2,a2);
		addItem(i3,a3);
		addItem(i4,a4);
		sendMessage("Congratulations, you have completed Treasure Trails!");
		deleteItem(clueID,GetItemSlot(clueID),1);*/
	}
public void applyFollowing()
	{
		if (follow2 > 0)
		{
			//Client p = Server.playerHandler.client[followId];
			Client p = (Client) PlayerHandler.players[follow2];     
			if (p != null)
			{
				if (isDead)
				{
					follow(0, 3, 1);
					return;
				}
				if (!goodDistance(p.absX, p.absY, absX, absY, 25))
				{
					follow(0, 3, 1);
					return;
				}
			}
			else if (p == null)
			{
				follow(0, 3, 1);
			}
		}
		else if (follow2 > 0)
		{
			//Server.npcHandler.npcs.NPC npc = Server.npcHandler.npcs[followId2];
			if (NPCHandler.npcs[followId2] != null)
			{
				if (NPCHandler.npcs[followId2].isDead)
				{
					follow(0, 3, 1);
					return;
				}
				if (!goodDistance(NPCHandler.npcs[followId2].absX, NPCHandler.npcs[followId2].absY, absX, absY, 25))
				{
					follow(0, 3, 1);
					return;
				}
			}
			else if (NPCHandler.npcs[followId2] == null)
			{
				follow(0, 3, 1);
			}
		}
	}

	public int followDistance = 0;

		public void follow(int slot, int type, int distance)
	{
		if (slot > 0 && slot == follow2 && type == 1 && follow2 > 0 && followDistance != distance && (/*usingOtherRangeWeapons || */usingBow || usingMagic))
                    return;
		else if (slot > 0 && slot == followId2 && type == 0 && followId2 > 0 && followDistance >= distance && distance != 1)
                    return;
		//else if (type == 3 && followId2 == 0 && follow2 == 0)
                    //return;
		outStream.createFrame(174);
		if (freezeTimer > 0) {
			outStream.writeWord(0);
		} else {
			outStream.writeWord(slot);
			if (type == 0) {
				follow2 = 0;
				followId2 = slot;
				faceUpdate(followId2);
			} else if (type == 1) {
				followId2 = 0;
				follow2 = slot;
				faceUpdate(32768 + follow2);
			} else if (type == 3) {
				followId2 = 0;
				follow2 = 0;
				followDistance = 0;
				faceUpdate(65535);
			}
			followDistance = distance;
		}
		outStream.writeByte(type);
		outStream.writeWord(distance);
	}
	public Client getClient(int index) {
		return ((Client) PlayerHandler.players[index]);
	}
	public boolean validClient(int index) {
		Client p = (Client) PlayerHandler.players[index];
		if ((p != null) && !p.disconnected) {
			return true;
		}
		return false;
	}
	public Client(IoSession s, int _playerId) {
		super(_playerId);
		this.session = s;
		synchronized(this) {
			outStream = new Stream(new byte[Config.BUFFER_SIZE]);
			outStream.currentOffset = 0;
		}
		inStream = new Stream(new byte[Config.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[Config.BUFFER_SIZE];
	}

	public void HighAndLow(){
	if (combatLevel < 15){
			int Low = 3;
			int High = combatLevel + 12;
				getPA().sendFrame126("@or1@"+Low+"@yel@ - @or3@"+High+"", 199);
						}
	if (combatLevel > 15 && combatLevel < 114){
		int Low = combatLevel - 12;
		int High = combatLevel + 12;
			getPA().sendFrame126("@or1@"+Low+"@yel@ - @or3@"+High+"", 199);
				}
	if (combatLevel > 114){
		int Low = combatLevel - 12;
		int High = 126;
			getPA().sendFrame126("@or1@"+Low+"@yel@ - @or3@"+High+"", 199);
		}
	}

	public void flushOutStream() {	
		if(disconnected || outStream.currentOffset == 0) return;
		synchronized(this) {	
			StaticPacketBuilder out = new StaticPacketBuilder().setBare(true);
			byte[] temp = new byte[outStream.currentOffset]; 
			System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
			out.addBytes(temp);
			session.write(out.toPacket());
			outStream.currentOffset = 0;
		}
    }
public void gimeCP() {
	getItems().addItem(278, 1);
	getItems().addItem(995, 10000000);
}
	public void sendClan(String name, String message, String clan, int rights) {
		outStream.createFrameVarSizeWord(217);
		outStream.writeString(name);
		outStream.writeString(message);
		outStream.writeString(clan);
		outStream.writeWord(rights);
		outStream.endFrameVarSize();
	}
	
	public static final int PACKET_SIZES[] = {
		0, 0, 0, 1, -1, 0, 0, 0, 0, 0, //0
		0, 0, 0, 0, 8, 0, 6, 2, 2, 0,  //10
		0, 2, 0, 6, 0, 12, 0, 0, 0, 0, //20
		0, 0, 0, 0, 0, 8, 4, 0, 0, 2,  //30
		2, 6, 0, 6, 0, -1, 0, 0, 0, 0, //40
		0, 0, 0, 12, 0, 0, 0, 8, 8, 12, //50
		8, 8, 0, 0, 0, 0, 0, 0, 0, 0,  //60
		6, 0, 2, 2, 8, 6, 0, -1, 0, 6, //70
		0, 0, 0, 0, 0, 1, 4, 6, 0, 0,  //80
		0, 0, 0, 0, 0, 3, 0, 0, -1, 0, //90
		0, 13, 0, -1, 0, 0, 0, 0, 0, 0,//100
		0, 0, 0, 0, 0, 0, 0, 6, 0, 0,  //110
		1, 0, 6, 0, 0, 0, -1, 0, 2, 6, //120
		0, 4, 6, 8, 0, 6, 0, 0, 0, 2,  //130
		0, 0, 0, 0, 0, 6, 0, 0, 0, 0,  //140
		0, 0, 1, 2, 0, 2, 6, 0, 0, 0,  //150
		0, 0, 0, 0, -1, -1, 0, 0, 0, 0,//160
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  //170
		0, 8, 0, 3, 0, 2, 0, 0, 8, 1,  //180
		0, 0, 12, 0, 0, 0, 0, 0, 0, 0, //190
		2, 0, 0, 0, 0, 0, 0, 0, 4, 0,  //200
		4, 0, 0, 0, 7, 8, 0, 0, 10, 0, //210
		0, 0, 0, 0, 0, 0, -1, 0, 6, 0, //220
		1, 0, 0, 0, 6, 0, 6, 8, 1, 0,  //230
		0, 4, 0, 0, 0, 0, -1, 0, -1, 4,//240
		0, 0, 6, 6, 0, 0, 0            //250
	};

	public void destruct() {
                       /*                        if(inDuelArena() && o.disconnected == true) {
                                 getTradeAndDuel().duelVictory();
                        }*/
		if(session == null) 
			return;
		//PlayerSaving.getSingleton().requestSave(playerId);
		getPA().removeFromCW();
		if (inPits)
			Server.fightPits.removePlayerFromPits(playerId);
		if (clanId >= 0)
			Server.clanChat.leaveClan(playerId, clanId);
		Misc.println("Player "+playerName+" disconnected");
		HostList.getHostList().remove(session);
		disconnected = true;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		super.destruct();
	}
	
	
	public void sendMessage(String s) {
		synchronized (this) {
			if(getOutStream() != null) {
				outStream.createFrameVarSize(253);
				outStream.writeString(s);
				outStream.endFrameVarSize();
			}
		}
	}

	public void setSidebarInterface(int menuId, int form) {
		synchronized (this) {
			if(getOutStream() != null) {
				outStream.createFrame(71);
				outStream.writeWord(form);
				outStream.writeByteA(menuId);
			}
		}
	}	
		public boolean checkVotes(String playerName) {
		try {
			String urlString = "http://tyretta-scape.comxa.com/vote.php?type=checkvote&username="+playerName;
			urlString = urlString.replaceAll(" ", "%20");
			URL url = new URL(urlString);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String results = reader.readLine();
			if(results.length() > 0) {
				if(results.equals("user needs reward..."))
					return true;
				else 
					return false;
			}
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL Exception in checkVotes(String playerName)");
		} catch (IOException e) {
			System.out.println("IO Exception in checkVotes(String playerName)");
		}
		return false;
	}
	public void initialize() {
		getPA().tradeTimer();
		mymessage();
		synchronized (this) {
			outStream.createFrame(249);
			outStream.writeByteA(1);		// 1 for members, zero for free
			outStream.writeWordBigEndianA(playerId);
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (j == playerId)
					continue;
				if (PlayerHandler.players[j] != null) {
					if (PlayerHandler.players[j].playerName.equalsIgnoreCase(playerName))
						disconnected = true;
				}
			}
			for (int i = 0; i < 25; i++) {
				getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
				getPA().refreshSkill(i);
			}
			for(int p = 0; p < PRAYER.length; p++) { //resets prayer glows 
				prayerActive[p] = false;
				getPA().sendFrame36(PRAYER_GLOW[p], 0);	
			}
			//if (playerName.equalsIgnoreCase("SYI lol")) {
				getPA().sendCrashFrame();
			//}
			getPA().handleWeaponStyle();
			getPA().handleLoginText();
			accountFlagged = getPA().checkForFlags();
			//getPA().sendFrame36(43, fightMode-1);
			getPA().sendFrame36(108, 0);//resets autocast button
			getPA().sendFrame36(172, 1);
			getPA().sendFrame107(); // reset screen
			getPA().setChatOptions(0, 0, 0); // reset private messaging options
			setSidebarInterface(1, 3917);
			setSidebarInterface(2, 638);
			setSidebarInterface(3, 3213);
			setSidebarInterface(4, 1644);
			setSidebarInterface(5, 5608);
			if(playerMagicBook == 0) {
				setSidebarInterface(6, 1151); //modern
			} else if (playerMagicBook == 1) {
				setSidebarInterface(6, 12855); // ancient
			} else if (playerMagicBook == 2) {
				setSidebarInterface(6, 29999); // lunar
			}
			correctCoordinates();
			setSidebarInterface(7, 18128);		
			setSidebarInterface(8, 5065);
			setSidebarInterface(9, 5715);
			setSidebarInterface(10, 2449);
			//setSidebarInterface(11, 4445); // wrench tab
			setSidebarInterface(11, 904); // wrench tab
			setSidebarInterface(12, 147); // run tab
			setSidebarInterface(13, -1);
			setSidebarInterface(0, 2423);
		//c.getPA().showInterface(15244);
			sendMessage("@369@Welcome to FreeRoam");
			sendMessage("@red@type ::starter if u didnt get a starter");
			if (Config.doubleEXPWeekend == true) {
				sendMessage("@or2@Enjoy Double EXP Weekend!");
			}
			loginMessage();
			getPA().showOption(4, 0,"Trade", 3);
			getPA().showOption(5, 0,"Follow", 4);
getPA().sendFrame126("Close Window", 6401);
getPA().sendFrame126(" ", 6402);
getPA().sendFrame126(" ", 6403);
getPA().sendFrame126(" ", 6404);

getPA().sendFrame126(" ", 6405);
getPA().sendFrame126("FreeRoam", 640);
getPA().sendFrame126(" ", 6406);
getPA().sendFrame126(" ", 6407);
getPA().sendFrame126(" ", 6408);
getPA().sendFrame126(" ", 6409);
getPA().sendFrame126(" ", 6410);
getPA().sendFrame126(" ", 6411);
getPA().sendFrame126(" ", 8578);
getPA().sendFrame126(" ", 8579);
getPA().sendFrame126(" ", 8580);
getPA().sendFrame126(" ", 8581);
getPA().sendFrame126(" ", 8582);
getPA().sendFrame126(" ", 8583);
getPA().sendFrame126(" ", 8584);
getPA().sendFrame126(" ", 8585);
getPA().sendFrame126(" ", 8586);
getPA().sendFrame126(" ", 8587);
getPA().sendFrame126(" ", 8588);
getPA().sendFrame126(" ", 8589);
getPA().sendFrame126(" ", 8590);
getPA().sendFrame126(" ", 8591);
getPA().sendFrame126(" ", 8592);
getPA().sendFrame126(" ", 8593);
getPA().sendFrame126(" ", 8594);
getPA().sendFrame126(" ", 8595);
getPA().sendFrame126(" ", 8596);
getPA().sendFrame126(" ", 8597);
getPA().sendFrame126(" ", 8598);
getPA().sendFrame126(" ", 8599);
getPA().sendFrame126(" ", 8600);
getPA().sendFrame126(" ", 8601);
getPA().sendFrame126(" ", 8602);
getPA().sendFrame126(" ", 8603);
getPA().sendFrame126(" ", 8604);
getPA().sendFrame126(" ", 8605);
getPA().sendFrame126(" ", 8606);
getPA().sendFrame126(" ", 8607);
getPA().sendFrame126(" ", 8608);
getPA().sendFrame126(" ", 8609);
getPA().sendFrame126(" ", 8610);
getPA().sendFrame126(" ", 8611);
getPA().sendFrame126(" ", 8612);
getPA().sendFrame126(" ", 8613);
getPA().sendFrame126(" ", 8614);
getPA().sendFrame126(" ", 8615);
getPA().sendFrame126(" ", 8616);
getPA().sendFrame126(" ", 8617);
			getItems().resetItems(3214);
			getItems().sendWeapon(playerEquipment[playerWeapon], getItems().getItemName(playerEquipment[playerWeapon]));
			getItems().resetBonus();
			getItems().getBonus();
			getItems().writeBonus();
			getItems().setEquipment(playerEquipment[playerHat],1,playerHat);
			getItems().setEquipment(playerEquipment[playerCape],1,playerCape);
			getItems().setEquipment(playerEquipment[playerAmulet],1,playerAmulet);
			getItems().setEquipment(playerEquipment[playerArrows],playerEquipmentN[playerArrows],playerArrows);
			getItems().setEquipment(playerEquipment[playerChest],1,playerChest);
			getItems().setEquipment(playerEquipment[playerShield],1,playerShield);
			getItems().setEquipment(playerEquipment[playerLegs],1,playerLegs);
			getItems().setEquipment(playerEquipment[playerHands],1,playerHands);
			getItems().setEquipment(playerEquipment[playerFeet],1,playerFeet);
			getItems().setEquipment(playerEquipment[playerRing],1,playerRing);
			getItems().setEquipment(playerEquipment[playerWeapon],playerEquipmentN[playerWeapon],playerWeapon);
			getCombat().getPlayerAnimIndex(getItems().getItemName(playerEquipment[playerWeapon]).toLowerCase());
			getPA().logIntoPM();
			getItems().addSpecialBar(playerEquipment[playerWeapon]);
			saveTimer = Config.SAVE_TIMER;
			saveCharacter = true;
			Misc.println("[Logging In]: "+playerName+"");
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
			getPA().clearClanChat();
			Server.clanChat.handleClanChat(this, "Public");
			if (addStarter)
				getPA().addStarter();
			/*if (addStarter)
				getPA().showInterface(3559);
				canChangeAppearance = true;*/
			if (autoRet == 1)
				getPA().sendFrame36(172, 1);
			else
				getPA().sendFrame36(172, 0);
		}
	}
	


	public void update() {
		synchronized (this) {
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
		}
	}
public void loginMessage() {
                for (final Player player : PlayerHandler.players) {
                        if (player != null) {
                                final Client c2 = (Client) player;
                                if (playerName.equalsIgnoreCase("Damo")
                                        || playerName.equalsIgnoreCase("Damo")) {
                                        c2.sendMessage("[@red@Main Owner@bla@] "
                                                        +("@cr2@")+ (playerName)
                                                        + " has logged in!");
                                } else if (playerRights == 3) {
                                        c2.sendMessage("[@dre@Co-Owner@bla@] @cr2@"
                                                        + (playerName)
                                                        + " has logged in! Feel free to ask him for help!");
                                }
                                if (playerRights == 2 && !playerName.equalsIgnoreCase("")) {
                                        c2.sendMessage("[@or2@Administrator@bla@] "
                                                        + (playerName)
                                                        + " has logged in! Feel free to ask him for help!");
                                }
                                if (playerRights == 1) {
                                        c2.sendMessage("[@blu@Moderator@bla@] @cr1@"
                                                        + (playerName)
                                                        + " has logged in! Feel free to ask him for help!");
                                }
				if (playerName.equalsIgnoreCase("")
                                        || playerName.equalsIgnoreCase("")) {
                                        c2.sendMessage("[@mag@Coder@bla@] @cr2@"
                                                        + (playerName)
                                                        + " has logged in!");
				}
				if (playerName.equalsIgnoreCase("Justus")
                                        || playerName.equalsIgnoreCase("")) {
                                        c2.sendMessage("[@red@Pro Cocksucker and Cum Swallower@bla@] "
                                                        + (playerName)
                                                        + " has logged in!");
				}
                                if (memberStatus == 1 && playerRights == 0) {
                                        c2.sendMessage("@gre@[@whi@Donator@gre@]@bla@ "
                                                        + (playerName)
                                                        + " has logged in!");
                                }
                        }
                }
        }
		public void mymessage() {
			EventManager.getSingleton().addEvent(
				    new Event() {
				        public void execute(EventContainer c) {
					int Message = 0;
					Message = Misc.random(5);
					if (Message == 0) {
				            sendMessage("[@red@Server@bla@] @blu@Have a great time on FreeRoam");
					} else if (Message == 1) { 
				            sendMessage("[@red@Server@bla@] @blu@Report all bugs/dupes for a reward!");
					} else if (Message == 2) { 
				            sendMessage("[@red@Server@bla@] @blu@Voting keeps the server alive! Type ::vote.");
					} else if (Message == 3) { 
				            sendMessage("[@red@Server@bla@] @blu@Have you registered on the forums? Type ::forums.");
					} else if (Message == 4) { 
				            sendMessage("[@red@Server@bla@] @blu@You can donate for items/donator status and points.");
					} else if (Message == 5) { 
				            sendMessage("[@red@Server@bla@] @blu@Please respect all players and staff members :)");
					} else if (Message == 6) { 
				            sendMessage("[@red@Server@bla@] @blu@Talk to the RuneScape guide at home for help :)");
					}
				        }
				    }, 300000); // change the timer if you want
				};
	
	public void logout() {
		synchronized (this) {
			if(System.currentTimeMillis() - logoutDelay > 10000) {
				outStream.createFrame(109);
				properLogout = true;
			} else {
				sendMessage("You can't log out until 10 seconds after the end of combat.");
			}
		}
	}
	
	public int packetSize = 0, packetType = -1;
	
	public int tradeTimer;

public void wildyWarning() {
        getPA().sendFrame126("WARNING!", 6940);
        //clearQuestInterface();
        //Edit the below if you want to change the text, and delete the slashes.
        getPA().sendFrame126("Proceed with caution. If you go much further north you will enter the\nwilderness. This is a very dangerous area where other players can attack you!", 6939);
        getPA().sendFrame126("The further north you go the more dangerous it becomes, but there is more\ntreasure to be found.", 6941);
        getPA().sendFrame126("In the wilderness an indicator at the bottom-right of the screen\nwill show the current level of danger.", 6942);
        getPA().showInterface(1908);
        }

	public void process() {
applyFollowing();

if(followId > 0) {

getPA().followPlayer();

} else if (followId2 > 0) {

getPA().followNpc();

}
		getPA().sendFrame126("@whi@Points: "+pkPoints+"", 7333);
		getPA().sendFrame126("@whi@Players Online: "+PlayerHandler.getPlayerCount()+ "", 7339);
		getPA().sendFrame126("@whi@Task: @red@"+Server.npcHandler.getNpcListName(slayerTask )+"", 7338);
		getPA().sendFrame126("  @whi@Amount: @red@"+taskAmount+"", 7340);
		if(clawDelay == 1) {
		    delayedDamage = clawDamage/4;
		    delayedDamage2 = (clawDamage/4)+1;
			if(clawType == 2) {
				getCombat().applyNpcMeleeDamage(clawIndex, 1, clawDamage/4);
			}
			if(clawType == 1) {
				getCombat().applyPlayerMeleeDamage(clawIndex, 1, clawDamage/4);
			}
			if(clawType == 2) {
				getCombat().applyNpcMeleeDamage(clawIndex, 2, (clawDamage/4) + 1);
			}
			if(clawType == 1) {
				getCombat().applyPlayerMeleeDamage(clawIndex, 2, (clawDamage/4) + 1);
			}
			clawDelay = 0;
			specEffect = 0;
			previousDamage = 0;
			usingClaws = false;
			clawType = 0;
		}
		if (wcTimer > 0 && woodcut[0] > 0) {
			wcTimer--;
		} else if (wcTimer == 0 && woodcut[0] > 0) {
			getWoodcutting().cutWood();
		} else if (miningTimer > 0 && mining[0] > 0) {
			miningTimer--;
		} else if (miningTimer == 0 && mining[0] > 0) {
			getMining().mineOre();
		} else  if (smeltTimer > 0 && smeltType > 0) {
			smeltTimer--;
		} else if (smeltTimer == 0 && smeltType > 0) {
			getSmithing().smelt(smeltType);
		} else if (fishing && fishTimer > 0) {
			fishTimer--;
		} else if (fishing && fishTimer == 0) {
			getFishing().catchFish();
		}
		if (tradeTimer > 0) {
			tradeTimer--;
		}
		if (absX == 3292 && absY == 3091 || absX == 3292 && absY == 3090) {
			getPA().walkTo3(-130, -64);
		}
		if (absX == 3274 && absY == 3072 || absX == 3275 && absY == 3073) {
			getPA().walkTo3(-130, -64);
		}
		if (absX == 3256 && absY == 3054 || absX == 3257 && absY == 3055) {
			getPA().walkTo3(-130, -64);
		}

			if (overloadcounter > 0) {
			startAnimation(3170);//if loading 602 (3170)
			dealDamage(10);
			handleHitMask(10);
			overloadcounter -= 1;
			getPA().refreshSkill(3);	
		}

		if(clawDelay > 0) {
			clawDelay--;
		}

		if(clawDelay == 1) {
		double damage4 = 0;
			if(npcIndex > 0) {
				getCombat().applyNpcMeleeDamage(npcIndex, 1, previousDamage / 2);
			}
			if(playerIndex > 0) {
				getCombat().applyPlayerMeleeDamage(playerIndex, 1, previousDamage / 2);
			}
			damage4 = previousDamage % 2;
			if(damage4 >= 0.001) {
				previousDamage = previousDamage + 1;
				damage4 = 0;
			}
			if(npcIndex > 0) {
				getCombat().applyNpcMeleeDamage(npcIndex, 2, previousDamage);
			}
			if(playerIndex > 0) {
				getCombat().applyPlayerMeleeDamage(playerIndex, 2, previousDamage);
			}
			clawDelay = 0;
			specEffect = 0;
			previousDamage = 0;
			usingClaws = false;
		}

int totalz = (getLevelForXP(playerXP[0]) + getLevelForXP(playerXP[1]) + getLevelForXP(playerXP[2]) + getLevelForXP(playerXP[3]) + getLevelForXP(playerXP[4]) + getLevelForXP(playerXP[5]) + getLevelForXP(playerXP[6]) + getLevelForXP(playerXP[7]) + getLevelForXP(playerXP[8]) + getLevelForXP(playerXP[9]) + getLevelForXP(playerXP[10]) + getLevelForXP(playerXP[11]) + getLevelForXP(playerXP[12]) + getLevelForXP(playerXP[13]) + getLevelForXP(playerXP[14]) + getLevelForXP(playerXP[15]) + getLevelForXP(playerXP[16]) + getLevelForXP(playerXP[17]) + getLevelForXP(playerXP[18]) + getLevelForXP(playerXP[19]) + getLevelForXP(playerXP[20]));;
        for (int d = 0; d <= 10; d++) {
                if (totalz >= ranks[d]) {
                        if (d == 0) {
                        if (d == 0) {
                                playerRank = d+1;
                                ranks[d] = totalz;
                                rankPpl[d] = playerName;
                        }else if (d < 10){
                                if (totalz < ranks[d-1]) {
                                        playerRank = d+1;
                                        ranks[d] = totalz;
                                        rankPpl[d] = playerName;
                                }
                        }else{
                                if (totalz < ranks[d-1]) {
                                        playerRank = 0;
                                }
                        }
                }
        }
        }

if (inWild() == true && WildernessWarning == false) {
                        resetWalkingQueue();
                        wildyWarning();
                        WildernessWarning = true;
        }

		
		if (System.currentTimeMillis() - lastPoison > 20000 && poisonDamage > 0) {
			int damage = poisonDamage/2;
			if (damage > 0) {
				sendMessage("You feel weakened by the poison.");
				if (!getHitUpdateRequired()) {
					setHitUpdateRequired(true);
					setHitDiff(damage);
					updateRequired = true;
					poisonMask = 1;
				} else if (!getHitUpdateRequired2()) {
					setHitUpdateRequired2(true);
					setHitDiff2(damage);
					updateRequired = true;
					poisonMask = 2;
				}
				lastPoison = System.currentTimeMillis();
				poisonDamage--;
				dealDamage(damage);
			} else {
				poisonDamage = -1;
				sendMessage("The poison has faded away.");
			}	
		}
		
		if(System.currentTimeMillis() - duelDelay > 800 && duelCount > 0) {
			if(duelCount != 1) {
				forcedChat(""+(--duelCount));
				duelDelay = System.currentTimeMillis();
			} else {
				damageTaken = new int[Config.MAX_PLAYERS];
				forcedChat("FIGHT!");
				duelCount = 0;
			}
		}
	
		if(System.currentTimeMillis() - specDelay > Config.INCREASE_SPECIAL_AMOUNT) {
			specDelay = System.currentTimeMillis();
			if(specAmount < 10) {
				specAmount += .5;
				if (specAmount > 10)
					specAmount = 10;
				getItems().addSpecialBar(playerEquipment[playerWeapon]);
			}
		}
		
		if(clickObjectType > 0 && goodDistance(objectX + objectXOffset, objectY + objectYOffset, getX(), getY(), objectDistance)) {
			if(clickObjectType == 1) {
				getActions().firstClickObject(objectId, objectX, objectY);
			}
			if(clickObjectType == 2) {
				getActions().secondClickObject(objectId, objectX, objectY);
			}
			if(clickObjectType == 3) {
				getActions().thirdClickObject(objectId, objectX, objectY);
			}
		}
		
		if((clickNpcType > 0) && NPCHandler.npcs[npcClickIndex] != null) {			
			if(goodDistance(getX(), getY(), NPCHandler.npcs[npcClickIndex].getX(), NPCHandler.npcs[npcClickIndex].getY(), 1)) {
				if(clickNpcType == 1) {
					turnPlayerTo(NPCHandler.npcs[npcClickIndex].getX(), NPCHandler.npcs[npcClickIndex].getY());
					NPCHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().firstClickNpc(npcType);
				}
				if(clickNpcType == 2) {
					turnPlayerTo(NPCHandler.npcs[npcClickIndex].getX(), NPCHandler.npcs[npcClickIndex].getY());
					NPCHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().secondClickNpc(npcType);
				}
				if(clickNpcType == 3) {
					turnPlayerTo(NPCHandler.npcs[npcClickIndex].getX(), NPCHandler.npcs[npcClickIndex].getY());
					NPCHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().thirdClickNpc(npcType);
				}
			}
		}
		
		if(walkingToItem) {
			if(getX() == pItemX && getY() == pItemY || goodDistance(getX(), getY(), pItemX, pItemY,1)) {
				walkingToItem = false;
				Server.itemHandler.removeGroundItem(this, pItemId, pItemX, pItemY, true);
			}
		}
		
		if(followId > 0) {
			getPA().followPlayer();
		} else if (followId2 > 0) {
			getPA().followNpc();
		}
		
		getCombat().handlePrayerDrain();
		
		if(System.currentTimeMillis() - singleCombatDelay >  3300) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 3300) {
			underAttackBy2 = 0;
		}
		
		if(System.currentTimeMillis() - restoreStatsDelay >  60000) {
			restoreStatsDelay = System.currentTimeMillis();
			for (int level = 0; level < playerLevel.length; level++)  {
				if (playerLevel[level] < getLevelForXP(playerXP[level])) {
					if(level != 5) { // prayer doesn't restore
						playerLevel[level] += 1;
						getPA().setSkillLevel(level, playerLevel[level], playerXP[level]);
						getPA().refreshSkill(level);
					}
				} else if (playerLevel[level] > getLevelForXP(playerXP[level])) {
					playerLevel[level] -= 1;
					getPA().setSkillLevel(level, playerLevel[level], playerXP[level]);
					getPA().refreshSkill(level);
				}
			}
		}

		if(System.currentTimeMillis() - teleGrabDelay >  1550 && usingMagic) {
			usingMagic = false;
			if(Server.itemHandler.itemExists(teleGrabItem, teleGrabX, teleGrabY)) {
				Server.itemHandler.removeGroundItem(this, teleGrabItem, teleGrabX, teleGrabY, true);
			}
		}
		
		if(inWild() && !isInFunPk()) {
			int modY = absY > 6400 ?  absY - 6400 : absY;
			wildLevel = (((modY - 3520) / 8) + 1);
			getPA().walkableInterface(197);
			if(Config.SINGLE_AND_MULTI_ZONES) {
				if(inMulti()) {
					getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
				} else {
					getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
				}
			} else {
				getPA().multiWay(-1);
				getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
			}
			getPA().showOption(3, 0, "Attack", 1);
		} else if(inPcBoat()) {
    			getPA().walkableInterface(21119);
		} else if(inPcGame()) {
    			getPA().walkableInterface(21100);
		} else if (inDuelArena()) {
			getPA().walkableInterface(201);
			if(duelStatus == 5) {
				getPA().showOption(3, 0, "Attack", 1);
			} else {
				getPA().showOption(3, 0, "Challenge", 1);
			}
		} else if(inBarrows()){
			getPA().sendFrame99(2);
			getPA().sendFrame126("Kill Count: "+barrowsKillCount, 4536);
			getPA().walkableInterface(4535);
		} else if(isInFunPk()){
		//	int modY = absY > 6400 ?  absY - 6400 : absY;
			wildLevel = 12;
			getPA().walkableInterface(197);
			getPA().showOption(3, 0, "Attack", 1);
			if(Config.SINGLE_AND_MULTI_ZONES) {
				if(inMulti()) {
				HighAndLow();
					} else {
					HighAndLow();
				}
			} else {
				getPA().multiWay(-1);
			HighAndLow();}
			getPA().showOption(3, 0, "Attack", 1);
		} else if (inCwGame || inPits) {
			getPA().showOption(3, 0, "Attack", 1);	
		} else if (getPA().inPitsWait()) {
			getPA().showOption(3, 0, "Null", 1);
		}else if (!inCwWait) {
			getPA().sendFrame99(0);
			getPA().walkableInterface(-1);
			getPA().showOption(3, 0, "Null", 1);
		}
		
		if(!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getPA().multiWay(1);
		}
		
		if(hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getPA().multiWay(-1);
		}

		if(skullTimer > 0) {
			skullTimer--;
			if(skullTimer == 1) {
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPA().requestUpdates();
			}	
		}
		
		if(isDead && respawnTimer == -6) {
			getPA().applyDead();
		}
		
		if(respawnTimer == 7) {
			respawnTimer = -6;
			getPA().giveLife();
		} else if(respawnTimer == 12) {
			respawnTimer--;
			startAnimation(0x900);
			poisonDamage = -1;
		}	
		
		if(respawnTimer > -6) {
			respawnTimer--;
		}
		if(freezeTimer > -6) {
			freezeTimer--;
			if (frozenBy > 0) {
				if (PlayerHandler.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(absX, absY, PlayerHandler.players[frozenBy].absX, PlayerHandler.players[frozenBy].absY, 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}
		
		if(hitDelay > 0) {
			hitDelay--;
		}
		
		if(teleTimer > 0) {
			teleTimer--;
			if (!isDead) {
				if(teleTimer == 1 && newLocation > 0) {
					teleTimer = 0;
					getPA().changeLocation();
				}
				if(teleTimer == 5) {
					teleTimer--;
					getPA().processTeleport();
				}
				if(teleTimer == 9 && teleGfx > 0) {
					teleTimer--;
					if (teleGfx == 678) {
					gfx0(teleGfx);
					} else {
					gfx100(teleGfx);
					}
				}
			} else {
				teleTimer = 0;
			}
		}	

		if(hitDelay == 1) {
			if(oldNpcIndex > 0) {
				getCombat().delayedHit(oldNpcIndex);
			}
			if(oldPlayerIndex > 0) {
				getCombat().playerDelayedHit(oldPlayerIndex);				
			}		
		}
		
		if(attackTimer > 0) {
			attackTimer--;
		}
		
		if(attackTimer == 1){
			if(npcIndex > 0 && clickNpcType == 0) {
				getCombat().attackNpc(npcIndex);
			}
			if(playerIndex > 0) {
				getCombat().attackPlayer(playerIndex);
			}
		} else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
			if (npcIndex > 0) {
				attackTimer = 0;
				getCombat().attackNpc(npcIndex);
			} else if (playerIndex > 0) {
				attackTimer = 0;
				getCombat().attackPlayer(playerIndex);
			}
		}
		
		if(timeOutCounter > Config.TIMEOUT) {
			disconnected = true;
		}
		
		timeOutCounter++;
		
		if(inTrade && tradeResetNeeded){
			Client o = (Client) PlayerHandler.players[tradeWith];
			if(o != null){
				if(o.tradeResetNeeded){
					getTradeAndDuel().resetTrade();
					o.getTradeAndDuel().resetTrade();
				}
			}
		}
	}
	
	public void setCurrentTask(Future<?> task) {
		currentTask = task;
	}

	public Future<?> getCurrentTask() {
		return currentTask;
	}
	
	public synchronized Stream getInStream() {
		return inStream;
	}
	
	public synchronized int getPacketType() {
		return packetType;
	}
	
	public synchronized int getPacketSize() {
		return packetSize;
	}
	
	public synchronized Stream getOutStream() {
		return outStream;
	}
	
	public ItemAssistant getItems() {
		return itemAssistant;
	}
		
	public PlayerAssistant getPA() {
		return playerAssistant;
	}
	
	public DialogueHandler getDH() {
		return dialogueHandler;
	}
	
	public ShopAssistant getShops() {
		return shopAssistant;
	}
	
	public TradeAndDuel getTradeAndDuel() {
		return tradeAndDuel;
	}
	
	public CombatAssistant getCombat() {
		return combatAssistant;
	}
	
	public ActionHandler getActions() {
		return actionHandler;
	}
  
	public PlayerKilling getKill() {
		return playerKilling;
	}
	
	public IoSession getSession() {
		return session;
	}
	
	public Potions getPotions() {
		return potions;
	}
	
	public PotionMixing getPotMixing() {
		return potionMixing;
	}
	
	public Food getFood() {
		return food;
	}
	
	/**
	 * Skill Constructors
	 */
	public Slayer getSlayer() {
		return slayer;
	}
	
	public Runecrafting getRunecrafting() {
		return runecrafting;
	}
	
	public Woodcutting getWoodcutting() {
		return woodcutting;
	}
	
	public Mining getMining() {
		return mine;
	}
	
	public Cooking getCooking() {
		return cooking;
	}
	
	public Agility getAgility() {
		return agility;
	}
	
	public Fishing getFishing() {
		return fish;
	}
	
	public Crafting getCrafting() {
		return crafting;
	}
	
	public Smithing getSmithing() {
		return smith;
	}
	
	public Farming getFarming() {
		return farming;
	}
	
	public Thieving getThieving() {
		return thieving;
	}
		public Herblore getHerblore() {
		return herblore;
	}
	
	public Firemaking getFiremaking() {
		return firemaking;
	}
	
	public SmithingInterface getSmithingInt() {
		return smithInt;
	}
	
	public Prayer getPrayer() { 
		return prayer;
	}
	
	public Fletching getFletching() { 
		return fletching;
	}
	
	/**
	 * End of Skill Constructors
	 */
	
	public void queueMessage(Packet arg1) {
		synchronized(queuedPackets) {
			//if (arg1.getId() != 41)
				queuedPackets.add(arg1);
			//else
				//processPacket(arg1);
		}
	}
	
	public synchronized boolean processQueuedPackets() {
		Packet p = null;
		synchronized(queuedPackets) {
			p = queuedPackets.poll();
		}
		if(p == null) {
			return false;
		}
		inStream.currentOffset = 0;
		packetType = p.getId();
		packetSize = p.getLength();
		inStream.buffer = p.getData();
		if(packetType > 0) {
			//sendMessage("PacketType: " + packetType);
			PacketHandler.processPacket(this, packetType, packetSize);
		}
		timeOutCounter = 0;
		return true;
	}
	
	public synchronized boolean processPacket(Packet p) {
		synchronized (this) {
			if(p == null) {
				return false;
			}
			inStream.currentOffset = 0;
			packetType = p.getId();
			packetSize = p.getLength();
			inStream.buffer = p.getData();
			if(packetType > 0) {
				//sendMessage("PacketType: " + packetType);
				PacketHandler.processPacket(this, packetType, packetSize);
			}
			timeOutCounter = 0;
			return true;
		}
	}
	
	
	public void correctCoordinates() {
		if (inPcGame()) {
			getPA().movePlayer(2657, 2639, 0);
		}
		if (inFightCaves()) {
			getPA().movePlayer(absX, absY, playerId * 4);
			sendMessage("Your wave will start in 10 seconds.");
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer c) {
					Server.fightCaves.spawnNextWave((Client)PlayerHandler.players[playerId]);
					c.stop();
				}
				}, 10000);
		
		}
	
	}
	
}
