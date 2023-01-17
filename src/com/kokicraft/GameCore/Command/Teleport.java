package com.kokicraft.GameCore.Command;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.kokicraft.GameCore.Main;

public class Teleport implements CommandExecutor {

	private Main plugin;

	public Teleport(Main plugin) {
		this.plugin = plugin;
	}
	
	public final HashMap<Player, Player> hPlayers = new HashMap<Player, Player>();
	public final HashMap<Player, Integer> hDirection = new HashMap<Player, Integer>();

	public String notPlayerMsg = "You are not a player";
	public String selfTpMsg = "You can not tp yourself";
	public String notOnlineMsg = "Player not online";
	public String noPlayerMsg = "No specific player";
	public String noneSendMsg = "No one send you";
	public String didNotDieMsg = "you have yet die";

	public String alreadyHaveMsg = "%value% already have tp request";

	public String movedMsg = "You moved";
	public String othersMovedMsg = "%value% moved";

	public String tpMsg = "You tp to %value%";
	public String backMsg = "You return back";

	public String timeOutMsg1 = "You request to %value% is timeout";
	public String timeOutMsg2 = "You request from %value% is time out";

	public String denyMsg1 = "You request to %value% is denied";
	public String denyMsg2 = "You request from %value% is denied";

	public String tpaMsg1 = "%value% tp to you";
	public String tpaMsg2 = "You tp to %value%";

	public String timeMsg = "Will time out: %value%";
	public String tpDelayMsg = "tp delay: %value%";

	public String sendRequestMsg = "You sent request to %value%";
	public String receiveRequestMsg = "You receive tp from %value%";

	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {

		if (cmd.getName().equalsIgnoreCase("tp")) {
			if (args.length == 1) {
				if (sender instanceof Player) {
					String arg0 = args[0];
					Player player1 = (Player) sender;
					Player player2 = Bukkit.getPlayer(arg0);
					if (player2 != null && player2.isOnline()) {
						if (player1 != player2) {
							Location l = player2.getLocation();
							player1.teleport(l);
							player1.sendMessage(getMsg(tpMsg, player2.getName()));
						} else {
							player1.sendMessage(selfTpMsg);
						}
					} else {
						player1.sendMessage(notOnlineMsg);
					}
				} else {
					sender.sendMessage(notPlayerMsg);
				}
			} else {
				if (args.length == 2) {
					String arg0 = args[0];
					String arg1 = args[1];
					Player player1 = Bukkit.getPlayer(arg0);
					Player player2 = Bukkit.getPlayer(arg1);
					if (player1 != null && player1.isOnline() && player2 != null && player2.isOnline()) {
						if (player1 != player2) {
							Location loc = player2.getLocation();
							player1.teleport(loc);
						} else {
							sender.sendMessage(selfTpMsg);
						}
					} else {
						sender.sendMessage(notOnlineMsg);
					}
				} else {
					sender.sendMessage(noPlayerMsg);
				}
			}
			return true;
		}

		if (sender instanceof Player) {
			final Player player1 = (Player) sender;

			if (cmd.getName().equalsIgnoreCase("tphere")) {
				if (args.length == 1) {
					String arg0 = args[0];
					Player player2 = Bukkit.getPlayer(arg0);
					if (player2 != null && player2.isOnline()) {
						if (player1 != player2) {
							Location l = player1.getLocation();
							player2.teleport(l);
							player1.sendMessage(getMsg(tpMsg, player2.getName()));
						} else {
							player1.sendMessage(selfTpMsg);
						}
					} else {
						player1.sendMessage(notOnlineMsg);
					}
				} else {
					player1.sendMessage(noPlayerMsg);
				}
				return true;
			}

			if (cmd.getName().equalsIgnoreCase("tpa")) {
				if (args.length == 1) {
					String arg0 = args[0];
					final Player player2 = Bukkit.getPlayer(arg0);
					String tpRequestTimeoutString = plugin.getConfig().getString("tp-request-timeout");
					int tpRequestTimeoutTicks = Integer.parseInt(tpRequestTimeoutString);
					int tpRequestTimeoutSeconds = tpRequestTimeoutTicks * 20;
					if (player2 != null && player2.isOnline()) {
						if (player1 != player2) {
							if (hPlayers.containsKey(player2)) {
								player1.sendMessage(getMsg(alreadyHaveMsg, player2.getName()));
							} else {
								if (hDirection.containsKey(player2)) {
									hDirection.remove(player2);
								}
								hPlayers.put(player2, player1);
								hDirection.put(player2, 0);
								player1.sendMessage(getMsg(sendRequestMsg, player2.getName()));
								player2.sendMessage(getMsg(receiveRequestMsg, player1.getName()));
								receiveRequestMsg(player2);
								player2.sendMessage(getMsg(timeMsg, String.valueOf(tpRequestTimeoutSeconds)));
								if (hPlayers.containsKey(player1)) {
									plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
										public void run() {
											Player player2 = hPlayers.get(player1);
											hPlayers.remove(player1);
											hDirection.remove(player1);
											player1.sendMessage(getMsg(timeOutMsg1, player2.getName()));
											player2.sendMessage(getMsg(timeOutMsg2, player1.getName()));
										}
									}, tpRequestTimeoutSeconds);
								}
							}
						} else {
							player1.sendMessage(selfTpMsg);
						}
					} else {
						player1.sendMessage(notOnlineMsg);
					}
				} else {
					player1.sendMessage(noPlayerMsg);
				}
				return true;
			}

			if (cmd.getName().equalsIgnoreCase("tpahere")) {
				if (args.length == 1) {
					String arg0 = args[0];
					final Player player2 = Bukkit.getPlayer(arg0);
					String tpRequestTimeoutString = plugin.getConfig().getString("tp-request-timeout");
					int tpRequestTimeoutTicks = Integer.parseInt(tpRequestTimeoutString);
					int tpRequestTimeoutSeconds = tpRequestTimeoutTicks * 20;
					if (player2 != null && player2.isOnline()) {
						if (player1 != player2) {
							if (hPlayers.containsKey(player2)) {
								player1.sendMessage(getMsg(alreadyHaveMsg, player2.getName()));
							} else {
								if (hDirection.containsKey(player2)) {
									hDirection.remove(player2);
								}
								hPlayers.put(player2, player1);
								hDirection.put(player2, 1);
								player1.sendMessage(getMsg(sendRequestMsg, player2.getName()));
								player2.sendMessage(getMsg(receiveRequestMsg, player1.getName()));
								receiveRequestMsg(player2);
								player2.sendMessage(getMsg(timeMsg, String.valueOf(tpRequestTimeoutSeconds)));
								if (hPlayers.containsKey(player1)) {
									plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
										public void run() {
											Player player2 = hPlayers.get(player1);
											hPlayers.remove(player1);
											hDirection.remove(player1);
											player1.sendMessage(getMsg(timeOutMsg1, player2.getName()));
											player2.sendMessage(getMsg(timeOutMsg2, player1.getName()));
										}
									}, tpRequestTimeoutSeconds);
								}
							}
						} else {
							player1.sendMessage(selfTpMsg);
						}
					} else {
						player1.sendMessage(notOnlineMsg);
					}
				} else {
					player1.sendMessage(noPlayerMsg);
				}
				return true;
			}

			if (cmd.getName().equalsIgnoreCase("tpaccept")) {
				String tpRequestDelayString = plugin.getConfig().getString("tp-request-delay");
				int tpRequestDelayTicks = Integer.parseInt(tpRequestDelayString);
				int tpRequestDelaySeconds = tpRequestDelayTicks * 20;
				if (hPlayers.containsKey(player1)) {
					final Player player2 = hPlayers.get(player1);
					if (player2 != null && player2.isOnline()) {
						int direction = hDirection.get(player1);
						if (direction == 0) {
							final Location l = player1.getLocation();
							final Double x1 = player2.getLocation().getX();
							final Double y1 = player2.getLocation().getY();
							final Double z1 = player2.getLocation().getZ();
							player1.sendMessage(getMsg(tpDelayMsg, String.valueOf(tpRequestDelaySeconds)));
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								public void run() {
									Double x2 = player2.getLocation().getX();
									Double y2 = player2.getLocation().getY();
									Double z2 = player2.getLocation().getZ();
									Double x = x1 - x2 + 0.5;
									Double y = y1 - y2 + 0.5;
									Double z = z1 - z2 + 0.5;
									if (x >= 0 && x <= 1 && y >= 0 && y <= 1 && z >= 0 && z <= 1) {
										player2.teleport(l);
										player1.sendMessage(getMsg(tpaMsg1, player2.getName()));
										player2.sendMessage(getMsg(tpaMsg2, player1.getName()));
									} else {
										player2.sendMessage(movedMsg);
										player1.sendMessage(getMsg(othersMovedMsg, player1.getName()));
									}
								}
							}, tpRequestDelaySeconds);
							hPlayers.remove(player1);
							hDirection.remove(player1);
						} else {
							final Location l = player2.getLocation();
							final Double x1 = player1.getLocation().getX();
							final Double y1 = player1.getLocation().getY();
							final Double z1 = player1.getLocation().getZ();
							player1.sendMessage(getMsg(tpDelayMsg, String.valueOf(tpRequestDelaySeconds)));
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								public void run() {
									Double x2 = player1.getLocation().getX();
									Double y2 = player1.getLocation().getY();
									Double z2 = player1.getLocation().getZ();
									Double x = x1 - x2 + 0.5;
									Double y = y1 - y2 + 0.5;
									Double z = z1 - z2 + 0.5;
									if (x >= 0 && x <= 1 && y >= 0 && y <= 1 && z >= 0 && z <= 1) {
										player1.teleport(l);
										player1.sendMessage(getMsg(tpaMsg1, player2.getName()));
										player2.sendMessage(getMsg(tpaMsg2, player1.getName()));
									} else {
										player1.sendMessage(movedMsg);
										player2.sendMessage(getMsg(othersMovedMsg, player1.getName()));
									}
								}
							}, tpRequestDelaySeconds);
						}
						hPlayers.remove(player1);
						hDirection.remove(player1);
					} else {
						player1.sendMessage(notOnlineMsg);
						hPlayers.remove(player1);
						hDirection.remove(player1);
					}
				} else {
					player1.sendMessage(noneSendMsg);
				}
				return true;
			}

			if (cmd.getName().equalsIgnoreCase("tpdeny")) {
				if (hPlayers.containsKey(player1)) {
					Player player2 = hPlayers.get(player1);
					hPlayers.remove(player1);
					hDirection.remove(player1);
					if (player2 != null && player2.isOnline()) {
						player2.sendMessage(getMsg(denyMsg2, player1.getName()));
						player1.sendMessage(getMsg(denyMsg1, player2.getName()));
					} else {
						player1.sendMessage(notOnlineMsg);
						hPlayers.remove(player1);
						hDirection.remove(player1);
					}
				} else {
					player1.sendMessage(noneSendMsg);
				}
				return true;
			}

		} else {
			sender.sendMessage(notPlayerMsg);
		}
		return true;
	}

	public String getMsg(String s, String replace) {
		s.replace("%value%", replace);
		return s;
	}

	public void receiveRequestMsg(Player p) {
		p.sendMessage(ChatColor.GRAY + " Use " + ChatColor.YELLOW + "/tpaccept" + ChatColor.GRAY + " to accept.");
		p.sendMessage(ChatColor.GRAY + " Use " + ChatColor.YELLOW + "/tpdeny" + ChatColor.GRAY + " to deny.");
	}
}
