package fr.fyz.hcf.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.MaxWgamer.julyapi.Account;
import fr.MaxWgamer.julyapi.bukkit.JulyAPIBukkit;
import fr.MaxWgamer.julyapi.managers.FactionRank;
import fr.fyz.hcf.faction.Faction;
import fr.fyz.hcf.faction.FactionManager;
import fr.fyz.hcf.faction.FactionPlayer;
import fr.fyz.hcf.faction.FactionPlayerManager;
import fr.fyz.hcf.faction.InvitationManager;

public class CommandFACTION implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				reply(p, new String[] { "info", p.getName() });
			} else {
				reply(p, args);
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	private void reply(Player p, String[] args) {
		CommandConditions conditions = CommandConditions.getConditionsByName(args[0]);
		if (!CommandConditions.canExecute(args[0], args, p)) {
			if (conditions != null) {
				p.sendMessage("§cError, command arguments : /f " + args[0] + " " + conditions.getTemplate());
			} else {
				p.sendMessage("§cError, invalid command");
			}
			return;
		}
		CommandConditions cc = CommandConditions.getConditionsByName(args[0]);
		switch (cc) {
		case INFO:
			if (args.length == 1) {
				reply(p, new String[] { "info", p.getName() });
				return;
			}
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
			if (target == null) {
				p.sendMessage("§cError, the targeted player don't exist");
				return;
			}
			Faction f = FactionManager.getFactionByPlayer(target.getUniqueId());
			if (f == null) {
				p.sendMessage("§cError, the targeted player don't have a faction");
				return;
			}
			p.sendMessage("§7Name : §e" + f.getName());
			p.sendMessage("§7Description : §e" + f.getDesc());
			p.sendMessage("");
			StringBuilder sb = new StringBuilder();
			for (UUID members : f.getPlayers()) {
				OfflinePlayer member = Bukkit.getPlayer(members);
				Account acc = JulyAPIBukkit.getInstance().getAccount(members);
				sb.append(acc.getFactionRank().getName() + " " + member.getName() + "§7, ");
			}
			p.sendMessage("§7Members :");
			p.sendMessage(sb.substring(sb.length() - 1, 0));
			p.sendMessage("");
			p.sendMessage("§7Bank : §e" + f.getBank() + "$");
			p.sendMessage("§7DTR : §e" + f.getDTR() + "§7/§e" + f.getMaxDTR());
			break;
		case JOIN:
			OfflinePlayer of = Bukkit.getOfflinePlayer(args[1]);
			if (of != null && of.isOnline()) {
				if (of.getUniqueId() == p.getUniqueId()) {
					p.sendMessage("§cError : You can't join yourself!");
					return;
				}
				Player from = of.getPlayer();
				Faction Ffrom = FactionManager.getFactionByPlayer(from.getUniqueId());
				Faction Fto = FactionManager.getFactionByPlayer(p.getUniqueId());
				if (Fto == null) {
					if (InvitationManager.isInvited(p.getUniqueId(), from.getUniqueId())) {
						if (Ffrom != null) {
							InvitationManager.removeInvitation(p.getUniqueId(), from.getUniqueId());
							Ffrom.addPlayer(p.getUniqueId(), FactionRank.MEMBER);
							FactionManager.updateFaction(Ffrom);
							p.sendMessage("§aYou just joined the faction §e" + Ffrom.getName() + "§a !");
							Ffrom.sendMessageToEveryone("§e" + p.getName() + "§a just joined the faction!");
						}
					}
				} else {
					p.sendMessage("§cError : You already are in a faction.");
				}
			}
			break;
		case INVITE:
			OfflinePlayer of1 = Bukkit.getOfflinePlayer(args[1]);
			if (of1 != null && of1.isOnline()) {
				if (of1.getUniqueId() == p.getUniqueId()) {
					p.sendMessage("§cError : You can't invite yourself!");
					return;
				}
				Player t_1 = of1.getPlayer();
				Faction pfac = FactionManager.getFactionByPlayer(p.getUniqueId());
				Faction t_1fac = FactionManager.getFactionByPlayer(t_1.getUniqueId());
				if (pfac != null) {
					FactionPlayer fp = FactionPlayerManager.getPlayer(p);
					if (fp.getRank().getPower() >= FactionRank.MOD.getPower()) {
						if (t_1fac == null) {
							if (!InvitationManager.isInvited(of1.getUniqueId(), p.getUniqueId())) {
								InvitationManager.addInvitation(of1.getUniqueId(), p.getUniqueId());
								p.sendMessage("§aYou just send an invitation to §e" + of1.getName()
										+ "§a for the faction §e" + pfac.getName() + "§a ");
								pfac.sendMessageToEveryone("§e" + p.getName() + " §ajust send an invitation to §e"
										+ of1.getName() + " §ato join the faction.");
								t_1.sendMessage("§aYou just receive an invitation from §e" + p.getName()
										+ " §ato the faction §e" + pfac.getName() + " §a! Type §e/f join " + p.getName()
										+ " §ato join it.");
							} else {
								p.sendMessage("§cError : " + t_1.getName() + " is already invited.");
								return;
							}
						} else {
							p.sendMessage("§cError : " + t_1.getName() + " is already in a faction.");
							return;
						}
					} else {
						p.sendMessage("§cError : You don't have the permission to do that in the faction.");
					}
				} else {
					p.sendMessage("§cError : You are not in a faction.");
				}
			}
			break;
		case LEAVE:
			Faction Fto = FactionManager.getFactionByPlayer(p.getUniqueId());
			if (Fto != null) {
				if (FactionPlayerManager.getPlayer(p).getRank() == FactionRank.LEADER) {
					p.sendMessage(
							"§cError, you can leave the faction because you are the leader ! You can promote someone leader or disband the faction.");
					return;
				}
				Fto.removePlayer(p.getUniqueId());
				FactionManager.updateFaction(Fto);
				Fto.sendMessageToEveryone("§e" + p.getName() + " §cjust leave the faction.");
				p.sendMessage("§aYou just leave the faction §e" + Fto.getName() + " §a!");
			} else {
				p.sendMessage("§cError : You already are in a faction.");
			}
			break;
		case OPEN:
			break;
		case CLOSE:
			break;
		case CREATE:
			Faction ff = FactionManager.getFactionByPlayer(p.getUniqueId());
			if (ff == null) {
				StringBuilder sb1 = new StringBuilder();
				for(int i = 1; i < args.length; i++) {
					sb1.append(args[i]+" ");
				}
				Faction created = new Faction(UUID.randomUUID(), sb1.toString(), "", 0, new ArrayList<>(), new ArrayList<>());
				FactionManager.createFaction(created);
				created.addPlayer(p.getUniqueId(), FactionRank.LEADER);
				p.sendMessage("§aYou just created the faction §e"+sb1+" §a!");
				
			} else {
				p.sendMessage("§cError, you already have a faction.");
			}
			break;
		case DISBAND:
			break;
		case NAME:
			break;
		case DESC:
			break;
		case SETHOME:
			break;
		case UNSETHOME:
			break;
		case KICK:
			break;
		case PROMOTE:
			break;
		case DEMOTE:
			break;
		case CLAIM:
			break;
		case TOP:
			break;
		default:
			OfflinePlayer trg = Bukkit.getOfflinePlayer(args[1]);
			if (trg != null) {
				reply(p, new String[] { "info", trg.getName() });
			} else {
				p.sendMessage("§cError, invalid command");
			}
			break;
		}
	}
}
