package fr.fyz.hcf.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.MaxWgamer.julyapi.Account;
import fr.MaxWgamer.julyapi.bukkit.JulyAPIBukkit;
import fr.fyz.hcf.faction.Faction;
import fr.fyz.hcf.faction.FactionManager;

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
		if(!CommandConditions.canExecute(args[0], args, p)) {
			p.sendMessage("§cError, command arguments : "+CommandConditions.getConditionsByName(args[0]).getTemplate());
			return;
		}
		CommandConditions cc = CommandConditions.getConditionsByName(args[0]);
		switch(cc) {
		case LIST:
			break;
		case INFO:
			if(args.length == 1) {
				reply(p, new String[] { "info", p.getName() });
				return;
			}
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
			if(target == null) {
				p.sendMessage("§cError, the targeted player don't exist");
				return;
			}
			Faction f = FactionManager.getFactionByPlayer(target.getUniqueId());
			if(f == null) {
				p.sendMessage("§cError, the targeted player don't have a faction");
				return;
			}
			p.sendMessage("§7Name : §e"+f.getName());
			p.sendMessage("§7Description : §e"+f.getDesc());
			p.sendMessage("");
			StringBuilder sb = new StringBuilder();
			for(UUID members : f.getPlayers()) {
				OfflinePlayer member = Bukkit.getPlayer(members);
				Account acc = JulyAPIBukkit.getInstance().getAccount(members);
				sb.append(acc.getFactionRank().getName()+" "+member.getName()+"§7, ");
			}
			p.sendMessage("§7Members :");
			p.sendMessage(sb.substring(sb.length()-1, 0));
			p.sendMessage("");
			p.sendMessage("§7Bank : §e"+f.getDTR()+"§7/§e"+f.getMaxDTR());
			p.sendMessage("§7DTR : §e"+f.getDTR()+"§7/§e"+f.getMaxDTR());
			
			break;
		case JOIN:
			break;
		case LEAVE:
			break;
		case INVITE:
			break;
		case CREATE:
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
			Player trg = Bukkit.getPlayer(args[1]);
			if(trg != null) {
				reply(p, new String[] { "info", trg.getName()});
			}
			break;
		}
	}
}
