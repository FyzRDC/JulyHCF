package fr.fyz.hcf.faction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class InvitationManager {
	
	private static HashMap<UUID, List<UUID>> invites = new HashMap<>();
	
	public static boolean isInvited(UUID invited, UUID inviting) {
		List<UUID> invitations = invites.get(invited);
		return invitations != null && invitations.contains(inviting);
	}
	
	public static void addInvitation(UUID invited, UUID inviting) {
		List<UUID> invitations = invites.get(invited);
		if(invitations == null) {
			invitations = new ArrayList<>();
		}
		invitations.add(inviting);
	}
	
	public static void removeInvitation(UUID invited, UUID inviting) {
		List<UUID> invitations = invites.get(invited);
		if(invitations != null && invitations.contains(inviting)) {
			invitations.remove(inviting);
		}
	}

}
