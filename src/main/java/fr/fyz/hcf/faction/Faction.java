package fr.fyz.hcf.faction;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;

import fr.MaxWgamer.julyapi.Account;
import fr.MaxWgamer.julyapi.bukkit.JulyAPIBukkit;
import fr.MaxWgamer.julyapi.managers.FactionRank;
import fr.fyz.hcf.faction.json.ListJSON;

public class Faction {
	
	private String name;
	private String desc;
	private UUID uuid;
	private int bank;
	private List<UUID> players;
	private List<Chunk> claims;
	
	public Faction(UUID uuid, String name, String desc, int bank, List<UUID> players, List<Chunk> claims) {
		this.name = name;
		this.claims = claims;
		this.bank = bank;
		this.uuid = uuid;
		this.desc = desc;
		this.players = players;
	}
	
	public List<Chunk> getClaims() {
		return claims;
	}
	
	public String getSerializedClaims() {
		return new ListJSON<Chunk>().serialize(claims);
	}
	
	public int getBank() {
		return bank;
	}
	
	public void sendMessageToEveryone(String message) {
		for(UUID uuid : players) {
			OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
			if(p.isOnline()) {
				p.getPlayer().sendMessage(message);
			}
		}
	}
	
	public void setBank(int bank) {
		this.bank = bank;
	}
	
	public void addBank(int bank) {
		this.bank = this.bank+bank;
	}
	
	public void removeBank(int bank) {
		this.bank = this.bank-bank;
	}
	
	public int getMaxDTR() {
		return players.size();
	}
	
	public int getDTR() {
		int i = 0;
		for(UUID uuid : players) {
			Account acc = JulyAPIBukkit.getInstance().getAccount(uuid);
			if(acc.getLives() > 0) {
				i++;
			}
		}
		return i;
	}
	
	public boolean canBeAssaulted() {
		return getDTR() <= 0;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}
	
	public List<UUID> getPlayers() {
		return players;
	}
	
	public void addPlayer(UUID p, FactionRank rank) {
		players.add(p);
		Account acc = JulyAPIBukkit.getInstance().getAccount(p);
		acc.setFactionId(this.uuid);
		acc.setFactionRank(rank);
	}
	
	public void removePlayer(UUID p) {
		players.remove(p);
		Account acc = JulyAPIBukkit.getInstance().getAccount(p);
		acc.setFactionId(null);
	}

	public String getSerializedPlayers() {
		return new ListJSON<UUID>().serialize(players);
	}
	
}
