package com.araeosia.pyrotechnician;

import com.araeosia.pyrotechnician.utils.FireShow;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class CommandListener implements CommandExecutor, Listener {
	
	private Pyrotechnician plugin;
	
	public CommandListener(Pyrotechnician plugin){
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender cs, Command cmnd, String commandLabel, String[] args) {
		if(cs.hasPermission("pyrotechnician.use")){
			if(args.length==0){
				sendHelp(cs);
			}else if(args[0].equalsIgnoreCase("show")){
				if(args.length==4){
					if(plugin.getShowHandler().getShow(args[3])!=null){
						cs.sendMessage("This NPCs FireShow has been set to ");
					}else{
						cs.sendMessage("Cannot find a FireShow by that name!");
					}
				}else if(args.length==2){
					cs.sendMessage("This NPCs FireShow is currently set to ");
				}else{
					sendHelp(cs);
				}
			}else if(args[0].equalsIgnoreCase("shows")){
				String output = "FireShows: ";
				for(FireShow fs : plugin.getShowHandler().getShows().values()){
					if(output.length()==11){
						output = fs.getName();
					}else{
						output = output+", "+fs.getName();
					}
				}
				cs.sendMessage(output);
			}else{
				sendHelp(cs);
			}
		}else{
			cs.sendMessage("Sorry, you don't have permission to use this command.");
		}
		return false;
	}
	
	private void sendHelp(CommandSender cs){
		String[] help = new String[5];
		help[0] = "Pyrotechnician commands: [Required] (Optional)";
		help[1] = "----------------------------------------------";
		help[2] = "/pyro show - Display the current show of the selected Pyro";
		help[3] = "/pyro show set [Name] - Pick a show";
		help[4] = "/pyro shows - List available shows";
		cs.sendMessage(help);
	}
	
}
