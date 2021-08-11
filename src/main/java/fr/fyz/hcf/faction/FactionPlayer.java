package fr.fyz.hcf.faction;

import java.util.UUID;

import fr.MaxWgamer.julyapi.managers.FactionRank;

public class FactionPlayer {
	
	private UUID uuid;
	private FactionRank rank;
	
	public FactionPlayer(UUID uuid, FactionRank rank) {
		this.uuid = uuid;
		this.rank = rank;
	}
	
	public UUID getUUID() {
		return uuid;
	}

	public FactionRank getRank() {
		return rank;
	}

}
