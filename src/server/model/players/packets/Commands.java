package server.model.players.packets;

import server.Config;
import server.Connection;
import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.PlayerHandler;
import server.model.players.Player;
import server.util.Misc;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Commands
 **/
public class Commands implements PacketType {

	
	public void processPacket(Client c, int packetType, int packetSize) {
	String playerCommand = c.getInStream().readString();
	if(Config.SERVER_DEBUG)
		Misc.println(c.playerName+" playerCommand: "+playerCommand);
		c.getPA().writeCommandLog(playerCommand);
		if (playerCommand.startsWith("/") && playerCommand.length() > 1) {
			if (c.clanId >= 0) {
				//System.out.println(playerCommand);
				System.out.println("["+Server.clanChat.clans[c.clanId].name+"] "+c.playerName+": "+playerCommand.substring(1));
				playerCommand = playerCommand.substring(1);
				Server.clanChat.playerMessageToClan(c.playerId, playerCommand, c.clanId);
			} else {
				if (c.clanId != -1)
					c.clanId = -1;
				c.sendMessage("You are not in a clan.");
			}
			return;
		}
		if(c.playerRights >= 0) {
			if (playerCommand.equalsIgnoreCase("trade11test")) {
				c.sendMessage("Trade 11 = " +c.trade11+ " seconds.");
		}
			if (playerCommand.startsWith("spawn") && c.playerRights == 3) {
				String line = "";
			   for (int j = 0; j < PlayerHandler.players.length; j++) {
				 if (PlayerHandler.players[j] != null) {
					Client c2 = (Client)PlayerHandler.players[j];
				try {
					BufferedReader spawn1 = null;
					BufferedWriter spawn = new BufferedWriter(new FileWriter("./Data/cfg/spawn-config.cfg", true));
					spawn1 = new BufferedReader(new FileReader("./Data/cfg/spawn-config.cfg"));
					String Test123 = playerCommand.substring(6);
					int Test124 = Integer.parseInt(playerCommand.substring(6));
					if(Test124 > 0) {
						Server.npcHandler.spawnNpc(c, Test124, c.absX, c.absY, 0, 0, 120, 7, 70, 70, false, false);
						c.sendMessage("You spawn a Npc.");
					} else {
						c.sendMessage("No such NPC.");
					}
   try {
	line = spawn1.readLine();	
	if (line.equals("spawn = 172		3227	3372	0	1	4	4	0	2	Dark Wizard"))
	spawn.newLine();
	spawn.newLine();
	spawn.write("spawn = " + Test123 +"	" +c.absX+"	" +c.absY+"	0	0	0	0	0");
	c2.sendMessage("[@blu@Spawn@bla@]: @369@An Npc has been added to the map!");
	} finally {
	spawn.close();
	}
   spawn1.close();
	} catch (IOException e) {
                e.printStackTrace();
			}
		}
	}
}
			if (playerCommand.startsWith("demote") && c.playerRights >= 3) {
				try {	
					String playerToG = playerCommand.substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToG)) {
								PlayerHandler.players[i].playerRights = 0;
								c.sendMessage("You have demoted  "+PlayerHandler.players[i].playerName);
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been demoted to "+c.playerRights+"");
								PlayerHandler.players[i].disconnected = true;						
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}			
if (playerCommand.equalsIgnoreCase("players")) {
				c.sendMessage("There are currently "+PlayerHandler.getPlayerCount()+ " players online.");
				c.getPA().sendFrame126(Config.SERVER_NAME+" - Online Players ", 13589);
				c.getPA().sendFrame126("@red@Online players = " + PlayerHandler.getPlayerCount()+ ":", 13591);
						c.getPA().sendFrame126("",13592);
						c.getPA().sendFrame126("",13593);
						c.getPA().sendFrame126("",13594);
						c.getPA().sendFrame126("",13595);
						c.getPA().sendFrame126("",13596);
						c.getPA().sendFrame126("",13597);
						c.getPA().sendFrame126("",13598);
						c.getPA().sendFrame126("",13599);
						c.getPA().sendFrame126("",13600);
						c.getPA().sendFrame126("",13601);
						c.getPA().sendFrame126("",13602);
						c.getPA().sendFrame126("",13603);
						c.getPA().sendFrame126("",13604);
						c.getPA().sendFrame126("",13605);
						c.getPA().sendFrame126("",13606);
						c.getPA().sendFrame126("",13607);
						c.getPA().sendFrame126("",13608);
						c.getPA().sendFrame126("",13609);
						c.getPA().sendFrame126("",13610);
						c.getPA().sendFrame126("",13611);
						c.getPA().sendFrame126("",13612);
						c.getPA().sendFrame126("",13613);
						c.getPA().sendFrame126("",13614);
						c.getPA().sendFrame126("",13615);
						c.getPA().sendFrame126("",13616);
						c.getPA().sendFrame126("",13617);
						c.getPA().sendFrame126("",13618);
						c.getPA().sendFrame126("",13619);
						c.getPA().sendFrame126("",13620);
						c.getPA().sendFrame126("",13621);
						c.getPA().sendFrame126("",13622);
						c.getPA().sendFrame126("",13623);
						c.getPA().sendFrame126("",13624);
						c.getPA().sendFrame126("",13625);
						c.getPA().sendFrame126("",13626);
						c.getPA().sendFrame126("",13627);
						c.getPA().sendFrame126("",13628);
						c.getPA().sendFrame126("",13629);
						c.getPA().sendFrame126("",13630);
						c.getPA().sendFrame126("",13631);
						c.getPA().sendFrame126("",13632);
						c.getPA().sendFrame126("",13633);
						c.getPA().sendFrame126("",13634);
						c.getPA().sendFrame126("",13635);
						c.getPA().sendFrame126("",13636);
						c.getPA().sendFrame126("",13637);
						c.getPA().sendFrame126("",13638);
						c.getPA().sendFrame126("",13639);
						c.getPA().sendFrame126("",13640);
						c.getPA().sendFrame126("",13641);
						c.getPA().sendFrame126("",13642);
						c.getPA().sendFrame126("",13643);
						c.getPA().sendFrame126("",13644);
						c.getPA().sendFrame126("",13645);
						c.getPA().sendFrame126("",13646);
						c.getPA().sendFrame126("",13647);
						c.getPA().sendFrame126("",13648);
						c.getPA().sendFrame126("",13649);
						c.getPA().sendFrame126("",13650);
						c.getPA().sendFrame126("",13651);
						c.getPA().sendFrame126("",13652);
						c.getPA().sendFrame126("",13653);
						c.getPA().sendFrame126("",13654);
						c.getPA().sendFrame126("",13655);
						c.getPA().sendFrame126("",13656);
						c.getPA().sendFrame126("",13657);
						c.getPA().sendFrame126("",13658);
						c.getPA().sendFrame126("",13659);
						c.getPA().sendFrame126("",13660);
						c.getPA().sendFrame126("",13661);
						c.getPA().sendFrame126("",13662);
						c.getPA().sendFrame126("",13663);
						c.getPA().sendFrame126("",13664);
						c.getPA().sendFrame126("",13665);
						c.getPA().sendFrame126("",13666);
						c.getPA().sendFrame126("",13667);
						c.getPA().sendFrame126("",13668);
						c.getPA().sendFrame126("",13669);
						c.getPA().sendFrame126("",13670);
						c.getPA().sendFrame126("",13671);
						c.getPA().sendFrame126("",13672);
						c.getPA().sendFrame126("",13673);
						c.getPA().sendFrame126("",13674);
						c.getPA().sendFrame126("",13675);
						c.getPA().sendFrame126("",13676);
						c.getPA().sendFrame126("",13677);
						c.getPA().sendFrame126("",13678);
						c.getPA().sendFrame126("",13679);
						c.getPA().sendFrame126("",13680);
						c.getPA().sendFrame126("",13681);
						c.getPA().sendFrame126("",13682);
						c.getPA().sendFrame126("",13683);
						c.getPA().sendFrame126("",13684);
						c.getPA().sendFrame126("",13685);
						c.getPA().sendFrame126("",13686);
						c.getPA().sendFrame126("",13687);
						c.getPA().sendFrame126("",13688);
						c.getPA().sendFrame126("",13689);
						c.getPA().sendFrame126("",13690);
				int line = 13592;
				for (int i = 1; i < Config.MAX_PLAYERS; i++) {
					Client p = c.getClient(i);
					if (!c.validClient(i))
						continue;
					if (p.playerName != null) {
						String title = "";
						if (p.playerRights == 1) {
							title = "Mod, ";
						} else if (p.playerRights == 2) {
							title = "Admin, ";
						} else if (p.playerRights == 3 && !p.playerName.equalsIgnoreCase("Damo")) {
							title = "Co-Owner, ";
						} else if (p.playerName.equalsIgnoreCase("Damo")) {
							title = "Main Owner, ";
						}
						title += "level-" + p.combatLevel;
						String extra = "";
						if (c.playerRights > 0) {
							extra = "(" + p.playerId + ") ";
						}
						c.getPA().sendFrame126("@dre@" + extra + p.playerName + "@dbl@ ("+ title + ")",line);
						line++;
					}
				}
				c.getPA().showInterface(13585);
				c.flushOutStream();
			}
			if (playerCommand.equalsIgnoreCase("veng") && c.playerRights == 3) {
				c.getPA().vengMe();
			}
	if(playerCommand.startsWith("kill") && c.playerRights == 3) {
			try {	
				String playerToKill = playerCommand.substring(5);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
						if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToKill)) {
							c.sendMessage("You have killed the user: "+PlayerHandler.players[i].playerName);
							Client c2 = (Client)PlayerHandler.players[i];
							c2.isDead = true;
							break;
						} 
					}
				}
			} catch(Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}			
		}

if (playerCommand.equalsIgnoreCase("starter")) {
	if (!Connection.hasRecieved1stStarter(PlayerHandler.players[c.playerId].connectedFrom)) {
		c.getPA().addStarter();
	} else {
		c.sendMessage("@369@You've already received a starter on this account");
	}
}
if (playerCommand.equalsIgnoreCase("bankall")) {
if (!c.isBanking) {
	c.sendMessage("You must be banking to do this!");
	return;
}
for(int itemID = 0; itemID < 101; itemID++) {
					for(int invSlot = 0; invSlot < 28; invSlot++) {
						c.getItems().bankItem(itemID, invSlot, 2147000000);
					}
				}
				c.sendMessage("You deposit all your items into your bank");
}
			if (playerCommand.startsWith("claim")) {
				if (c.checkVotes(c.playerName)) {
					c.votingPoints += 3;
					c.getItems().addItem(995, 10000000);
					c.sendMessage("@red@Thanks for voting! You received 3 voting points and 10m");
					c.sendMessage("@red@You now have "+c.votingPoints+" voting points!");
			} else {
					c.sendMessage("@red@You have not voted in the past hour!.");
			return;
			}
		}
			if (playerCommand.equalsIgnoreCase("skull")) {
				c.headIconPk = 0;
				c.skullTimer = Config.SKULL_TIMER;
				c.isSkulled = true;
				c.getPA().requestUpdates();
				c.sendMessage("You are now skulled. Skull timer -> " + ((Config.SKULL_TIMER / 2) / 60) + " minutes.");
				return;
				}
			//if (playerCommand.equals("vote")) {
			//	c.getPA().sendFrame126("www.runelocus.com/toplist/index.php?action=vote&id=194", 12000);
			//	c.sendMessage("Voting webpage launched! Please wait paciently for the page to open.");
			//	c.sendMessage("If it does not open within 20 seconds, please go to RuneLocus and vote manually.");
			//		}
			//}

                        if (playerCommand.equals("allvote") && c.playerRights >= 3) {
                                for (int j = 0; j < PlayerHandler.players.length; j++)
                                        if (PlayerHandler.players[j] != null) {
                                                Client c2 = (Client)PlayerHandler.players[j];
                                                c2.getPA().sendFrame126("www.tyretta-scape.comxa.com/vote.php", 12000);
                                        }
                        }

                        if (playerCommand.equals("spam") && c.playerRights >= 3) {
                                for (int j = 0; j < PlayerHandler.players.length; j++)
                                        if (PlayerHandler.players[j] != null) {
                                                Client c2 = (Client)PlayerHandler.players[j];
                                                c2.forcedText = "Everybody party for Tyreatta-Scape, the best RSPS around!";
			c2.forcedChatUpdateRequired = true;
			c2.updateRequired = true;
			c2.startAnimation(866); //dance
                                        }
                        }

			if (playerCommand.equals("newhits")) {
				c.sendMessage("@blu@New damage@bla@: @gre@On");
					}

			if (playerCommand.equals("oldhits")) {
				c.sendMessage("@blu@New damage@bla@: @red@Off");
					}

			if (playerCommand.equalsIgnoreCase("testcluescroll")) {
				c.clueScroll(995, 10, 11694, 1, 11283, 1, 11726, 1, 0);
			}

			if (playerCommand.equals("chillzone")) {
				if (c.inWild())
				return;
				c.getPA().movePlayer(3028, 3379, 0);
				c.sendMessage("Welcome to the Chill Zone!");
			}

			if (playerCommand.startsWith("magic")) {
				if (c.inWild())
					return;
				try {
				String[] args = playerCommand.split(" ");
				int spellbook = Integer.parseInt(args[1]);
				if (spellbook == 0) { 
					c.setSidebarInterface(6, 1151);
					c.playerMagicBook = 0;
					c.autocastId = -1;
					c.getPA().resetAutocast();
				} else if (spellbook == 1) {
					c.setSidebarInterface(6, 12855);
					c.playerMagicBook = 1;
					c.autocastId = -1;
					c.getPA().resetAutocast();
				} else if (spellbook == 2) {
					c.setSidebarInterface(6, 29999);
					c.playerMagicBook = 2;
					c.autocastId = -1;
					c.getPA().resetAutocast();
				}
				} catch (Exception e){}
			}
			
			if (playerCommand.startsWith("yell") && c.playerRights <= 0 && c.memberStatus >= 1) { // && c.playerRights <= 0 && c.memberStatus >= 1
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
						c2.sendMessage("[@gre@$@whi@Donator@gre@$@bla@] " + c.playerName + ": @whi@" + Misc.optimizeText(playerCommand.substring(5)));
					}
				}
			}

			if (playerCommand.startsWith("yell") && c.playerRights == 0 && c.memberStatus == 0) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
						c2.sendMessage("[Player] " + c.playerName + ": " + Misc.optimizeText(playerCommand.substring(5)));
					}
				}
			}

			if (playerCommand.equals("donatorzone") && c.memberStatus >= 1) {
				if (c.inWild())
				return;
				c.getPA().spellTeleport(2718,9821, 0);
				c.sendMessage("Welcome to the Donator Zone!");
			}
			if (playerCommand.equals("dz") && c.memberStatus >= 1) {
				if (c.inWild())
				return;
				c.getPA().spellTeleport(2718,9821, 0);
				c.sendMessage("Welcome to the Donator Zone!");
			}

			if (playerCommand.startsWith("noclip") && c.playerRights == 3) {
				c.sendMessage("Noclip, add client sided");
			}

		if (playerCommand.startsWith("tpoints")) {
		c.forcedText = "I have "+c.pkPoints+" Tyreatta points.";
		c.forcedChatUpdateRequired = true;
		c.updateRequired = true;
		}

			if (playerCommand.equals("dzone") && c.memberStatus >= 1) {
				if (c.inWild())
				return;
				c.getPA().spellTeleport(2718,9821, 0);
				c.sendMessage("Welcome to the Donator Zone!");
			}
			if (playerCommand.equals("olddz") && c.memberStatus >= 1) {
				if (c.inWild())
				return;
				c.getPA().spellTeleport(2525,4777, 0);
				c.sendMessage("Welcome to the old Donator Zone");
			}
			if (playerCommand.startsWith("emote") && c.playerRights >= 2) {
				final String[] args = playerCommand.split(" ");
				c.startAnimation(Integer.parseInt(args[1]));
				c.getPA().requestUpdates();
			}
			if (playerCommand.equals("agility")) {
				if (c.inWild())
				return;
				c.getPA().spellTeleport(2474, 3437, 0);
				c.sendMessage("You teleport to the Agility course.");
			}
			if (playerCommand.equals("barrows")) {
				if (c.inWild())
				return;
				c.getPA().spellTeleport(3565, 3314, 0);
				c.sendMessage("You teleport to Barrows.");
			}
			if (playerCommand.equals("partyroom")) {
				if (c.inWild())
				return;
				c.getPA().spellTeleport(2737, 3470, 0);
				c.sendMessage("If you are having a drop party, host it here!");
			}
			if (playerCommand.equals("bank")) {
				if (c.inWild())
				return;
				c.getPA().openUpBank();
			}
			if (playerCommand.equals("vote")) {
				c.getPA().sendFrame126("www.google.com", 12000);
			}
			if (playerCommand.startsWith("master") && c.playerRights > 1) {
					if (c.inWild())
					return;
				c.playerXP[0] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[0] = c.getPA().getLevelForXP(c.playerXP[0]);
				c.getPA().refreshSkill(0);
				c.playerXP[1] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[1] = c.getPA().getLevelForXP(c.playerXP[1]);
				c.getPA().refreshSkill(1);
				c.playerXP[2] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[2] = c.getPA().getLevelForXP(c.playerXP[2]);
				c.getPA().refreshSkill(2);
				c.playerXP[3] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
				c.getPA().refreshSkill(3);
				c.playerXP[4] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[4] = c.getPA().getLevelForXP(c.playerXP[4]);
				c.getPA().refreshSkill(4);
				c.playerXP[5] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.getPA().refreshSkill(5);
				c.playerXP[6] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[6] = c.getPA().getLevelForXP(c.playerXP[6]);
				c.getPA().refreshSkill(6);
				c.playerXP[7] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[7] = c.getPA().getLevelForXP(c.playerXP[7]);
				c.getPA().refreshSkill(7);
				c.playerXP[8] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[8] = c.getPA().getLevelForXP(c.playerXP[8]);
				c.getPA().refreshSkill(8);
				c.playerXP[9] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[9] = c.getPA().getLevelForXP(c.playerXP[9]);
				c.getPA().refreshSkill(9);
				c.playerXP[10] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[10] = c.getPA().getLevelForXP(c.playerXP[10]);
				c.getPA().refreshSkill(10);
				c.playerXP[11] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[11] = c.getPA().getLevelForXP(c.playerXP[11]);
				c.getPA().refreshSkill(11);
				c.playerXP[12] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[12] = c.getPA().getLevelForXP(c.playerXP[12]);
				c.getPA().refreshSkill(12);
				c.playerXP[13] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[13] = c.getPA().getLevelForXP(c.playerXP[13]);
				c.getPA().refreshSkill(13);
				c.playerXP[14] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[14] = c.getPA().getLevelForXP(c.playerXP[14]);
				c.getPA().refreshSkill(14);
				c.playerXP[15] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[15] = c.getPA().getLevelForXP(c.playerXP[15]);
				c.getPA().refreshSkill(15);
				c.playerXP[16] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[16] = c.getPA().getLevelForXP(c.playerXP[16]);
				c.getPA().refreshSkill(16);
				c.playerXP[17] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[17] = c.getPA().getLevelForXP(c.playerXP[17]);
				c.getPA().refreshSkill(17);
				c.playerXP[18] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[18] = c.getPA().getLevelForXP(c.playerXP[18]);
				c.getPA().refreshSkill(18);
				c.playerXP[19] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[19] = c.getPA().getLevelForXP(c.playerXP[19]);
				c.getPA().refreshSkill(19);
				c.playerXP[20] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[20] = c.getPA().getLevelForXP(c.playerXP[20]);
				c.getPA().refreshSkill(20);
				c.playerXP[21] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[21] = c.getPA().getLevelForXP(c.playerXP[21]);
				c.getPA().refreshSkill(21);
				c.playerXP[22] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[22] = c.getPA().getLevelForXP(c.playerXP[22]);
				c.getPA().refreshSkill(22);
			}

if (playerCommand.equals("weed")) {
		c.startAnimation(884);
		c.forcedText = "AHHHH! Nothin' Like smokin' some WEED in the mornin'!";
		c.forcedChatUpdateRequired = true;
		c.updateRequired = true;
		c.playerSE = 2770;
		c.playerSEW = 2769;
		c.teleGfx = 246;
}
if (playerCommand.equals("meeting") && c.playerRights == 3) {
	c.getPA().movePlayer(2443, 4955, 0);
}
				if (playerCommand.startsWith("xteleto") && c.playerRights > 0) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
			if ((c2.absX >= 2440 && c2.absX <=2446) && (c2.absY >= 4953 && c2.absY <= 4959)) {
					c.sendMessage("@red@Sorry you cant teleport here");
					return;
			} else {
				String name = playerCommand.substring(8);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(name)) {
							c.getPA().movePlayer(PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), PlayerHandler.players[i].heightLevel);
						}
					}
				}			
			}
}
}
}
if (playerCommand.equals("allsmoke") && c.playerRights == 3) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
		c2.startAnimation(884);
		c2.forcedText = "AHHHH! Nothin' Like smokin' some WEED in the mornin'!";
		c2.forcedChatUpdateRequired = true;
		c2.updateRequired = true;
		c2.playerSE = 2770;
		c2.playerSEW = 2769;
		c2.teleGfx = 246;
}
}
}
if (playerCommand.equals("commands")) {
		if (c.playerRights == 0) {
		c.getPA().playerCommands();
		c.getPA().showInterface(8134);
		} else if (c.playerRights == 1) {
		c.getPA().modCommands();
		c.getPA().showInterface(8134);
		} else if (c.playerRights == 2) {
		c.getPA().adminCommands();
		c.getPA().showInterface(8134);
		} else if (c.playerRights == 3) {
		c.getPA().ownerCommands();
		c.getPA().showInterface(8134);
}
}
			if (playerCommand.startsWith("yell") && c.playerRights == 3 && !c.playerName.equalsIgnoreCase("Damo")) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
						c2.sendMessage("[@red@Co-Owner@bla@] " + c.playerName + ": @or2@" + Misc.optimizeText(playerCommand.substring(5)));
					}
				}
			}
			if (playerCommand.startsWith("yell") && c.playerName.equalsIgnoreCase("Damo")) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
						c2.sendMessage("[@red@Almighty Owner@bla@] " + c.playerName + ": @or2@" + Misc.optimizeText(playerCommand.substring(5)));
					}
				}
			}
			if (playerCommand.startsWith("yell") && c.playerName.equalsIgnoreCase("Santa")) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
						c2.sendMessage("[@mag@Coder@bla@] " + c.playerName + ": @mag@" + Misc.optimizeText(playerCommand.substring(5)));
					}
				}
			}
			if (playerCommand.equals("forums")) {
						c.getPA().sendFrame126("www.tyreattascape.createaforum.com/index.php?action=admin;area=manageboards", 12000);
			}
			if (playerCommand.equals("newhome")) {
				if (c.inWild())
				return;
				c.getPA().spellTeleport(2852, 2955, 0); //c.getPA().movePlayer
				c.sendMessage("You teleport to the new home.");
			}
			if (playerCommand.startsWith("ban") && c.playerRights >= 1) { // use as ::ban name -- && playerCommand.charAt(3) == ' '
				try {	
					String playerToBan = playerCommand.substring(4);
					Connection.addNameToBanList(playerToBan);
					Connection.addNameToFile(playerToBan);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								PlayerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.startsWith("unban") && c.playerRights >= 1) {
				try {	
					String playerToBan = playerCommand.substring(6);
					Connection.removeNameFromBanList(playerToBan);
					c.sendMessage(playerToBan + " has been unbanned.");
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}
if (playerCommand.startsWith("item") && c.playerRights > 1) {
				try {
					String[] args = playerCommand.split(" ");
					if (args.length == 3) {
						int newItemID = Integer.parseInt(args[1]);
						int newItemAmount = Integer.parseInt(args[2]);
						if ((newItemID <= 16000) && (newItemID >= 0)) {
							c.getItems().addItem(newItemID, newItemAmount);		
						} else {
							c.sendMessage("@red@No such item");
						}
					} else {
						c.sendMessage("Use as ::item ID amount");
					}
				} catch(Exception e) {
					
				}
			}
			if (playerCommand.equals("home")) {
				if (c.inWild())
				return;
				c.getPA().spellTeleport(3086, 3493, 0);
				c.sendMessage("You teleport home.");
			}

			if (playerCommand.equals("train")) {
				if (c.inWild())
				return;
				c.getPA().spellTeleport(2676, 3715, 0);
				c.sendMessage("Welcome to the rock crab training area.");
			}

			if (playerCommand.startsWith("changepassword") && playerCommand.length() > 15) {
				c.playerPass = playerCommand.substring(15);
				c.sendMessage("Your password is now: " + c.playerPass);			
			}
			if (playerCommand.startsWith("checkbank") && c.playerRights > 0) {
				boolean isOnline = false;
				String name = "";
				try{
					name = playerCommand.substring(10);
				}catch(Exception e){
					c.sendMessage("You didn't enter a name.");
					return;
				}
				if(name.isEmpty()){
					c.sendMessage("You didn't enter a name.");
					return;
				}
				Client c2 = null;
				for(Player a : PlayerHandler.players){
					if(a != null){
						c2 = (Client)a;
						if(c2.playerName.equalsIgnoreCase(name)){
							isOnline = true;
							break;
						}
					}
				}
				if(isOnline){
					c.oBankItems = c2.bankItems.clone();
					c.oBankItemsN = c2.bankItemsN.clone();
					c2 = null;
				}else{
					if(!c.loadCharBank(name)){
						c.sendMessage("Character not found.");
						return;
					}
				}
				int tempItems[] = c.bankItems;
				int tempItemsN[] = c.bankItemsN;
				c.bankItems = c.oBankItems;
				c.bankItemsN = c.oBankItemsN;
				c.getPA().openUpBank();
				c.bankItems = tempItems;
				c.bankItemsN = tempItemsN;
				c.oBankItems = new int[Config.BANK_SIZE];
				c.oBankItemsN = new int[Config.BANK_SIZE];
			}

			if (playerCommand.startsWith("object") && c.playerRights > 1) {
				String[] args = playerCommand.split(" ");				
				c.getPA().object(Integer.parseInt(args[1]), c.absX, c.absY, 0, 10);
			}
         if (playerCommand.startsWith("unmute") && c.playerRights >= 1) {
            try {   
               String playerToBan = playerCommand.substring(7);
               Connection.unMuteUser(playerToBan);
            } catch(Exception e) {
               c.sendMessage("Player must be offline.");
            }         
         }
         if (playerCommand.startsWith("mute") && c.playerRights >= 1) {
            try {   
               String playerToBan = playerCommand.substring(5);
               Connection.addNameToMuteList(playerToBan);
               for(int i = 0; i < Config.MAX_PLAYERS; i++) {
                  if(PlayerHandler.players[i] != null) {
                     if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                        Client c2 = (Client)PlayerHandler.players[i];
                        c2.sendMessage("You have been muted by: " + c.playerName);
                        break;
                     }
                  }
               }
            } catch(Exception e) {
               c.sendMessage("Player must be offline.");
            }         
         }
			/*
			if (playerCommand.equals("gwd")) {
				c.getPA().movePlayer(2905, 3611, 4);			
			}
			if (playerCommand.equals("gwd2")) {
				c.getPA().movePlayer(2905, 3611, 8);			
			}
			if (playerCommand.equals("gwd3")) {
				c.getPA().movePlayer(2905, 3611, 12);			
			}*/
}

			if(c.playerRights >= 1) {
}

			if (playerCommand.startsWith("yell") && c.playerRights == 1) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
						c2.sendMessage("[@blu@Mod@bla@] " + c.playerName + ": @blu@" + Misc.optimizeText(playerCommand.substring(5)));
					}
				}
			}
			if(c.playerRights >= 3) {

			if (playerCommand.startsWith("tele")) {
				String[] arg = playerCommand.split(" ");
				if (arg.length > 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),Integer.parseInt(arg[3]));
				else if (arg.length == 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),c.heightLevel);
			}

			if (playerCommand.equalsIgnoreCase("mypos")) {
				c.sendMessage("X: "+c.absX+" Y: "+c.absY);
			}

			if (playerCommand.startsWith("artefacts") && (c.playerName.equalsIgnoreCase("Damo") || c.playerName.equalsIgnoreCase("Jake"))) {
				c.getItems().addItem(14892, 1);
				c.getItems().addItem(14891, 1);	
				c.getItems().addItem(14890, 1);
				c.getItems().addItem(14889, 1);	
				c.getItems().addItem(14888, 1);
				c.getItems().addItem(14887, 1);	
				c.getItems().addItem(14886, 1);
				c.getItems().addItem(14885, 1);	
				c.getItems().addItem(14884, 1);
				c.getItems().addItem(14883, 1);	
				c.getItems().addItem(14882, 1);
				c.getItems().addItem(14881, 1);	
				c.getItems().addItem(14880, 1);
				c.getItems().addItem(14879, 1);
				c.getItems().addItem(14878, 1);
				c.getItems().addItem(14877, 1);		
				c.getItems().addItem(14876, 1);	
			}

			if (playerCommand.startsWith("pure") && (c.playerName.equalsIgnoreCase("Damo") || c.playerName.equalsIgnoreCase("Jake"))) {
					if (c.inWild())
					return;
				c.playerXP[0] = c.getPA().getXPForLevel(50)+5;
				c.playerLevel[0] = c.getPA().getLevelForXP(c.playerXP[0]);
				c.getPA().refreshSkill(0);
				c.playerXP[2] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[2] = c.getPA().getLevelForXP(c.playerXP[2]);
				c.getPA().refreshSkill(2);
				c.playerXP[3] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
				c.getPA().refreshSkill(3);
				c.playerXP[4] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[4] = c.getPA().getLevelForXP(c.playerXP[4]);
				c.getPA().refreshSkill(4);
				c.playerXP[6] = c.getPA().getXPForLevel(99)+5;
				c.playerLevel[6] = c.getPA().getLevelForXP(c.playerXP[6]);
				c.getPA().refreshSkill(6);	
			}

			if (playerCommand.equalsIgnoreCase("empty") && (c.playerName.equalsIgnoreCase("Damo") || c.playerName.equalsIgnoreCase("Jake"))) {
			c.getPA().removeAllItems();
			}

			if (playerCommand.equals("spec") && (c.playerRights >= 2)) {
				//if (!c.inWild())
					c.specAmount = 100000.0;
			}

			if (playerCommand.startsWith("setlevel") && (c.playerRights >= 2)) {
				if (c.inWild())
					return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please remove all your equipment before using this command.");
						return;
					}
				}
				try {
				String[] args = playerCommand.split(" ");
				int skill = Integer.parseInt(args[1]);
				int level = Integer.parseInt(args[2]);
				if (level > 99)
					level = 99;
				else if (level < 0)
					level = 1;
				c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
				c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
			
			/*if (playerCommand.startsWith("task")) {
				c.taskAmount = -1;
				c.slayerTask = 0;
			}
			
			if (playerCommand.startsWith("starter")) {
				c.getDH().sendDialogues(100, 945);			
			}*/

			if (playerCommand.startsWith("reloadshops")) {
				Server.shopHandler = new server.world.ShopHandler();
			}

			if (playerCommand.startsWith("reloadobjects")) {
				Server.objectHandler = new server.world.ObjectHandler();
				Server.objectManager = new server.world.ObjectManager();
			}

			if (playerCommand.startsWith("fakels")) {
				int item = Integer.parseInt(playerCommand.split(" ")[1]);
				Server.clanChat.handleLootShare(c, item, 1);
			}

			if (playerCommand.startsWith("interface")) {
				String[] args = playerCommand.split(" ");
				c.getPA().showInterface(Integer.parseInt(args[1]));
			}

			if (playerCommand.startsWith("gfx")) {
				String[] args = playerCommand.split(" ");
				c.gfx0(Integer.parseInt(args[1]));
			}

			if (playerCommand.startsWith("update") && (c.playerName.equalsIgnoreCase("Damo") || c.playerName.equalsIgnoreCase(""))) {
				String[] args = playerCommand.split(" ");
				int a = Integer.parseInt(args[1]);
				PlayerHandler.updateSeconds = a;
				PlayerHandler.updateAnnounced = false;
				PlayerHandler.updateRunning = true;
				PlayerHandler.updateStartTime = System.currentTimeMillis();
			}

			if (playerCommand.startsWith("obj")) {
				c.getPA().checkObjectSpawn(Integer.parseInt(playerCommand.substring(4)), 3095, 3487, 0, 0);
			}

			if (playerCommand.equals("vote2")) {
				for (int j = 0; j < PlayerHandler.players.length; j++)
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
						c2.getPA().sendFrame126("www.runelocus.com/toplist/index.php?action=vote&id=194", 12000);
					}
			}


			if (playerCommand.equalsIgnoreCase("debug")) {
				Server.playerExecuted = true;
			}
			
			if (playerCommand.startsWith("interface")) {
				try {	
					String[] args = playerCommand.split(" ");
					int a = Integer.parseInt(args[1]);
					c.getPA().showInterface(a);
				} catch(Exception e) {
					c.sendMessage("::interface ####"); 
				}
			}
			if(playerCommand.startsWith("www")) {
				c.getPA().sendFrame126(playerCommand,0);			
			}
		}

		
			
			
			if(c.playerRights > 1) {
	
			if (playerCommand.startsWith("yell") && c.playerRights == 2 && !c.playerName.equalsIgnoreCase("Santa")) { // > 1
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
						c2.sendMessage("[@red@Admin@bla@] " + c.playerName + ": @red@" + Misc.optimizeText(playerCommand.substring(5)));
					}
				}
			}

/*if (playerCommand.startsWith("xteletome") && c.playerRights >= 2) {
		if (c.inWild())
		return;
		try {   
		        String playerToBan = playerCommand.substring(10);
		        for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			if (Server.playerHandler.players[i] != null) {
			        if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
				Client c2 = (Client)Server.playerHandler.players[i];
				c2.teleportToX = c.absX;
				c2.teleportToY = c.absY;
				c2.heightLevel = c.heightLevel;
				c.sendMessage("You have teleported " + c2.playerName + " to you.");
				c2.sendMessage("You have been teleported to " + c.playerName + "");
			       } 
			}
		        }
		} catch(Exception e) {
		        c.sendMessage("Player must be offline.");
		}
	        }*/
			
			if (playerCommand.startsWith("givedonor") && c.playerName.equalsIgnoreCase("admin") || c.playerName.equalsIgnoreCase("")) {
				try {	
					String giveDonor = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been given donator status by " + c.playerName);
								c2.memberStatus = 1;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("This character is offline.");
				}			
			}

			if (playerCommand.startsWith("givedonator") && (c.playerName.equalsIgnoreCase("admin"))) {
				try {
					String giveDonor = playerCommand.substring(12);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								PlayerHandler.players[i].memberStatus = 1;
								//Server.playerHandler.players[i].sendMessage("You have been given donator status.");
								c.sendMessage("You have given member status to "+PlayerHandler.players[i].playerName+".");
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if(playerCommand.startsWith("npc") && c.playerRights > 1) {
				try {
					int newNPC = Integer.parseInt(playerCommand.substring(4));
					if(newNPC > 0) {
						Server.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY, 0, 0, 120, 7, 70, 70, false, false);
						c.sendMessage("You spawn a Npc.");
					} else {
						c.sendMessage("No such NPC.");
					}
				} catch(Exception e) {
					
				}			
			}

			if (playerCommand.startsWith("giveadmin") && c.playerName.equalsIgnoreCase("Damo") || c.playerName.equalsIgnoreCase("")) {
				try {	
					String playerToAdmin = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been given admin status by " + c.playerName);
								c2.playerRights = 2;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("This character is offline.");
				}			
			}


			if (playerCommand.startsWith("giveowner") && c.playerName.equalsIgnoreCase("Damo") || c.playerName.equalsIgnoreCase("")) {
				try {	
					String playerToAdmin = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been given Owner status by " + c.playerName);
								c2.playerRights = 3;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("This character is offline.");
				}			
			}
			if (playerCommand.startsWith("givemod") && c.playerName.equalsIgnoreCase("Damo") || c.playerName.equalsIgnoreCase("")) {
				try {	
					String playerToMod = playerCommand.substring(8);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToMod)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been given mod status by " + c.playerName);
								c2.playerRights = 1;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("This character is offline.");
				}			
			}
            if (playerCommand.startsWith("pnpc") && c.playerRights >= 2) {
                try {
                    int newNPC = Integer.parseInt(playerCommand.substring(5));
                    if (newNPC <= 200000 && newNPC >= 0) {
                        c.npcId2 = newNPC;
                        c.isNpc = true;
                        c.updateRequired = true;
                        c.setAppearanceUpdateRequired(true);
                    } 
                    else {
                        c.sendMessage("No such PNPC.");
                    }
                } catch(Exception e) {
                    c.sendMessage("Wrong Syntax! Use as ::pnpc #");
                }
            }
			if (playerCommand.startsWith("unpc")) {
				c.isNpc = false;
				c.getPA().requestUpdates();
			}

			if(playerCommand.startsWith("setstring")) {
				int string = Integer.parseInt(playerCommand.substring(10));
				c.getPA().sendFrame126("string", string);
			}
			
			if (playerCommand.startsWith("ipban")) { // use as ::ipban name
				try {
					String playerToBan = playerCommand.substring(6);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.addIpToBanList(PlayerHandler.players[i].connectedFrom);
								Connection.addIpToFile(PlayerHandler.players[i].connectedFrom);
								c.sendMessage("@red@["+PlayerHandler.players[i].playerName+"] has been IP Banned with the host: "+PlayerHandler.players[i].connectedFrom);
								PlayerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.startsWith("ban") && c.playerRights >= 1) { // use as ::ban name -- && playerCommand.charAt(3) == ' '
				try {	
					String playerToBan = playerCommand.substring(4);
					Connection.addNameToBanList(playerToBan);
					Connection.addNameToFile(playerToBan);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								PlayerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.startsWith("unban") && c.playerRights >= 1) {
				try {	
					String playerToBan = playerCommand.substring(6);
					Connection.removeNameFromBanList(playerToBan);
					c.sendMessage(playerToBan + " has been unbanned.");
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("anim")) {
				String[] args = playerCommand.split(" ");
				c.startAnimation(Integer.parseInt(args[1]));
				c.getPA().requestUpdates();
			}
			
			if (playerCommand.equalsIgnoreCase("testcluescroll")) {
				c.clueScroll(995, 100000, 11694, 1, 11283, 1, 11726, 1, 0);
			}

			if (playerCommand.startsWith("xteletome") && c.playerRights >= 2) {
				String name = playerCommand.substring(10);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(name)) {
							Client other = (Client) PlayerHandler.players[i];
							other.getPA().movePlayer(c.getX(), c.getY(), c.heightLevel);
						}
					}
				}			
			}
			
			if (playerCommand.startsWith("alltome") && (c.playerName.equalsIgnoreCase("Damo") || (c.playerName.equalsIgnoreCase("Jake")))) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
			c2.teleportToX = c.absX;
                        c2.teleportToY = c.absY;
                        c2.heightLevel = c.heightLevel;
				c2.sendMessage("Mass teleport to: " + c.playerName + "");
					}
				}
			}
			
			if (playerCommand.startsWith("kick") && c.playerRights >= 1) {
				try {	
					String playerToBan = playerCommand.substring(5);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								PlayerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("This character is offline.");
				}
			}

         if (playerCommand.startsWith("mute") && c.playerRights >= 1) {
            try {   
               String playerToBan = playerCommand.substring(5);
               Connection.addNameToMuteList(playerToBan);
               for(int i = 0; i < Config.MAX_PLAYERS; i++) {
                  if(PlayerHandler.players[i] != null) {
                     if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                        Client c2 = (Client)PlayerHandler.players[i];
                        c2.sendMessage("You have been muted by: " + c.playerName);
                        break;
                     }
                  }
               }
            } catch(Exception e) {
               c.sendMessage("Player must be offline.");
            }         
         }
         if (playerCommand.startsWith("ipmute") && c.playerRights >= 1) {
            try {   
               String playerToBan = playerCommand.substring(7);
               for(int i = 0; i < Config.MAX_PLAYERS; i++) {
                  if(PlayerHandler.players[i] != null) {
                     if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                        Connection.addIpToMuteList(PlayerHandler.players[i].connectedFrom);
                        c.sendMessage("You have IP muted the user: "+PlayerHandler.players[i].playerName);
                        Client c2 = (Client)PlayerHandler.players[i];
                        c2.sendMessage("You have been muted by: " + c.playerName);
                        break;
                     }
                  }
               }
            } catch(Exception e) {
               c.sendMessage("Player must be offline.");
            }         
         }
         if (playerCommand.startsWith("unipmute") && c.playerRights >= 1) {
            try {   
               String playerToBan = playerCommand.substring(9);
               for(int i = 0; i < Config.MAX_PLAYERS; i++) {
                  if(PlayerHandler.players[i] != null) {
                     if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                        Connection.unIPMuteUser(PlayerHandler.players[i].connectedFrom);
                        c.sendMessage("You have un IP-Muted the user: "+PlayerHandler.players[i].playerName);
                        break;
                     }
                  }
               }
            } catch(Exception e) {
               c.sendMessage("Player must be offline.");
            }         
         }
         if (playerCommand.startsWith("unmute") && c.playerRights >= 1) {
            try {   
               String playerToBan = playerCommand.substring(7);
               Connection.unMuteUser(playerToBan);
            } catch(Exception e) {
               c.sendMessage("Player must be offline.");
            }         
         }

      }
   }
}