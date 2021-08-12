package fr.fyz.hcf.faction;

import java.util.UUID;

import org.bukkit.Chunk;

import fr.MaxWgamer.julyapi.Account;
import fr.MaxWgamer.julyapi.bukkit.JulyAPIBukkit;
import fr.MaxWgamer.julyapi.managers.FactionRank;
import fr.fyz.hcf.faction.json.ListJSON;

public class FactionManager {
	
	
	public static void createFaction(Faction f) {
		
		JulyAPIBukkit.getInstance().getMySQL()
		.update("INSERT INTO factions (uuid, name, description, players, claims) VALUES "
				+ "('"+f.getUUID()+"','"+f.getName()+"','"+f.getDesc()+"','"+f.getSerializedPlayers()+"','"+f.getSerializedClaims()+"'");
	}
	
	public static void updateFaction(Faction f) {
		JulyAPIBukkit.getInstance().getMySQL().update("UPDATE 'factions' WHERE 'uuid' = "+f.getUUID()+" SET (name, description, players, claims) ('"+f.getName()+"','"+f.getDesc()+"','"+f.getSerializedPlayers()+"','"+f.getSerializedClaims()+"'");
	}
	
	public static Faction getFactionByPlayer(UUID p) {
		Account acc = JulyAPIBukkit.getInstance().getAccount(p);
		if(acc.getFactionId() == null) {
			return null;
		}
		return getFactionByUUID(acc.getFactionId());
	}
	
	public static Faction getFactionByUUID(UUID faction) {
		JulyAPIBukkit.getInstance().getMySQL().query("SELECT * FROM 'factions' WHERE 'uuid' = "+faction, rs -> {
			try {
				if(rs.next()) {
					return new Faction(UUID.fromString(rs.getString("uuid")), rs.getString("name"), rs.getString("description"), rs.getInt("bank"), new ListJSON<UUID>().deserialize(rs.getString("players")), new ListJSON<Chunk>().deserialize(rs.getString("claims")));
				}
				return null;
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return rs;
		});
		return null;
	}
	
	public static void setFaction(UUID p, UUID faction) {
		UUID f;
		if(faction == null) {
			f = null;
		} else {
			f  = FactionManager.getFactionByUUID(faction).getUUID();
		}
		if(FactionManager.getFactionByPlayer(p) != null) {
			Faction old = FactionManager.getFactionByPlayer(p);
			old.removePlayer(p);
		}
		Account acc = JulyAPIBukkit.getInstance().getAccount(p);
		acc.setFactionId(f);
		acc.setFactionRank(FactionRank.MEMBER);
	}

}
