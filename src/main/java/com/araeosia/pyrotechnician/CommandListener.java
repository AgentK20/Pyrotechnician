package com.araeosia.pyrotechnician;

import com.araeosia.pyrotechnician.utils.FireShow;
import com.araeosia.pyrotechnician.utils.FireShow;
import com.araeosia.pyrotechnician.utils.Utils;
import java.util.List;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CommandListener implements CommandExecutor, Listener {

	private Pyrotechnician plugin;

	public CommandListener(Pyrotechnician plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender cs, Command cmnd, String commandLabel, String[] args) {
		if (cmnd.getName().equalsIgnoreCase("pyro")) {
			if (cs.hasPermission("pyrotechnician.use")) {
				if (args.length == 0) {
					sendHelp(cs);
				} else if (args[0].equalsIgnoreCase("show")) {
					if (args.length >= 3) {
						FireShow show = plugin.getShowHandler().getShow(Utils.compressList(Utils.arrayToList(args)));
						if (show != null) {
							if (cs instanceof Player) {
								Player p = (Player) cs;
								NPC npc = Utils.getSelectedNPC(p);
								if (npc != null) {
									PyroTrait trait = npc.getTrait(PyroTrait.class);
									trait.setShow(show);
									cs.sendMessage("This NPCs FireShow has been set to " + show.getKey() + ": "+show.getName());
								} else {
									cs.sendMessage("You must have selected a valid Pyrotechnician to set it's show!");
								}
							} else {
								cs.sendMessage("You must be a player to execute that command!");
							}
						} else {
							cs.sendMessage("Cannot find a FireShow by that name!");
						}
					} else if (args.length == 1) {
						if (cs instanceof Player) {
							Player p = (Player) cs;
							NPC npc = Utils.getSelectedNPC(p);
							if (npc != null) {
								PyroTrait trait = npc.getTrait(PyroTrait.class);
								FireShow show = trait.getActiveShow();
								if (show != null || trait.getFireShows().size()>0) {
									cs.sendMessage("This NPC's active FireShow is " + show.getKey() + ": "+show.getName());
								} else {
									cs.sendMessage("This NPC does not currently have a FireShow set.");
								}
							} else {
								cs.sendMessage("You must have selected a valid Pyrotechnician to set it's show!");
							}
						} else {
							cs.sendMessage("You must be a player to execute that command!");
						}
					} else {
						sendHelp(cs);
					}
					return true;
				} else if (args[0].equalsIgnoreCase("shows")) {
					String output = "FireShows: ";
					for (FireShow fs : plugin.getShowHandler().getShows().values()) {
						if (output.length() == 11) {
							output = fs.getKey();
						} else {
							output = output + ", " + fs.getKey();
						}
					}
					if(output.length() == 11){
						output = "There are currently not any FireShows loaded!";
					}
					cs.sendMessage(output);
				} else if (args[0].equalsIgnoreCase("load")){
					int before = plugin.getShowHandler().getShows().size();
					plugin.getShowHandler().loadShows();
					int newCount = plugin.getShowHandler().getShows().size()-before;
					cs.sendMessage("Loaded "+newCount+" new shows.");
				} else if (args[0].equalsIgnoreCase("execute")){
					if(cs instanceof Player){
						Player p = (Player) cs;
						NPC npc = Utils.getSelectedNPC(p);
						if(npc!=null){
							if(npc.hasTrait(PyroTrait.class)){
								PyroTrait trait = npc.getTrait(PyroTrait.class);
								trait.setActiveShow();
							}
						}
					}
				} else {
					sendHelp(cs);
				}
				
			} else {
				cs.sendMessage("Sorry, you don't have permission to use this command.");
			}
			return true;
		}
		return false;
	}

	private void sendHelp(CommandSender cs) {
		String[] help = new String[5];
		help[0] = "Pyrotechnician commands: [Required] (Optional)";
		help[1] = "----------------------------------------------";
		help[2] = "/pyro show - Display the current show of the selected Pyro";
		help[3] = "/pyro show set [Name] - Pick a show";
		help[4] = "/pyro shows - List available shows";
		cs.sendMessage(help);
	}
}
