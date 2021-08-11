package fr.fyz.hcf.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import fr.fyz.hcf.utils.Cuboid;

public enum Zones {
	
	SAFEZONE("", new Cuboid(new Location(Bukkit.getWorld("world"), 0, 100, 0), new Location(Bukkit.getWorld("world"), 0, 100, 0)), false, false),
	WARZONE("", new Cuboid(new Location(Bukkit.getWorld("world"), 0, 100, 0), new Location(Bukkit.getWorld("world"), 0, 100, 0)), true, false),
	WILLDERNESS("", new Cuboid(new Location(Bukkit.getWorld("world"), 0, 100, 0), new Location(Bukkit.getWorld("world"), 0, 100, 0)), true, true);

	private String name;
	private Cuboid zone;
	private boolean canPvP;
	private boolean canInterract;
	
	Zones(String name, Cuboid zone, boolean canPvP, boolean canInterract) {
		this.name = name;
		this.zone = zone;
		this.canPvP = canPvP;
		this.canInterract = canInterract;
	}
	
	public String getName() {
		return name;
	}
	
	public Cuboid getZone() {
		return zone;
	}
	
	public boolean canPvP() {
		return canPvP;
	}
	
	public boolean canInterract() {
		return canInterract;
	}
	
	
	
}