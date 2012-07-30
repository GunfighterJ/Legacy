package co.viocode.nexus.commands;

import co.viocode.nexus.Nexus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Unmute  implements CommandExecutor {

	private Nexus plugin;
	public Unmute(Nexus plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

		// invalid args
		if (args.length < 1 || args.length > 1)
			return false;

		// check if player
		Boolean isPlayer = true;
		if (!(sender instanceof Player))
			isPlayer = false;

		// init vars
		Player player = null;
		if (isPlayer)
			player = (Player) sender;
		Player target = Nexus.findOnlinePlayer(args[0]);

		// check permission
		if (isPlayer)
			if (!Nexus.checkPermission("nexus.mute", player))
				return true;

		// check if player is offline
		if (target == null) {
			sender.sendMessage(ChatColor.RED + "Player is not online.");
			return true;
		}

		// check if player is muted
		if (!Nexus.mute.contains(target)) {
			sender.sendMessage(ChatColor.RED + "Player is not muted.");
			return true;
		}

		// mute player
		Nexus.mute.remove(target);
		target.sendMessage(ChatColor.GREEN + sender.getName() + " unmuted you.");
		sender.sendMessage(ChatColor.GREEN + target.getName() + " is unmuted.");

		// end of command
		return true;
	}
}