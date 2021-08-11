package fr.fyz.hcf.faction;

import org.bukkit.entity.Player;

import fr.MaxWgamer.julyapi.Account;
import fr.MaxWgamer.julyapi.bukkit.JulyAPIBukkit;

public class FactionPlayerManager {
	
	public static FactionPlayer getPlayer(Player p) {
		Account acc = JulyAPIBukkit.getInstance().getAccount(p.getUniqueId());
		return new FactionPlayer(p.getUniqueId(), acc.getFactionRank());
	}

}
