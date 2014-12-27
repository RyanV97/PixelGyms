package com.ryanv97.pixelgyms.commands;

import com.ryanv97.pixelgyms.PixelGyms;
import com.ryanv97.pixelgyms.util.Reference;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class GymAdminCommand extends CommandBase
{

    private final String[] commands = new String[]{"create","remove","list","tp","addPlayer","removePlayer","listPlayers","nextPlayer","reload"};

    @Override
    public String getCommandName() {
        return "gymadmin";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return Reference.messagePrefix+Reference.colorRed+"/gymadmin "+Reference.colorRed+"<create/remove/list/tp/addPlayer/removePlayer/listPlayers"+Reference.colorRed+"/nextPlayer/reload>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        EntityPlayer player = getPlayer(commandSender, commandSender.getCommandSenderName());

        if (args.length < 1) {
            commandSender.addChatMessage(new ChatComponentText(getCommandUsage(commandSender)));
            return;
        }

        if (args[0].equalsIgnoreCase("create")) {
            if(args.length==1){
                commandSender.addChatMessage(new ChatComponentText(Reference.colorRed+"/gymadmin create <Gym Name> <Level Cap> - Creates a Gym "+Reference.colorRed+"and sets the warp at your position"));
            }
            if(args.length==2){
                commandSender.addChatMessage(new ChatComponentText(Reference.messagePrefix + Reference.colorRed + "Error: Please specify a level cap!"));
                commandSender.addChatMessage(new ChatComponentText(Reference.messagePrefix + Reference.colorRed+"/gymadmin create "+args[1]+" <Level Cap>"));
            }
            if (args.length > 2) {
                PixelGyms.config.addGym(args[1], player.posX, player.posY, player.posZ, Integer.parseInt(args[2]), player);
            }
        }
        if (args[0].equalsIgnoreCase("remove")) {
            if(args.length==1){
                commandSender.addChatMessage(new ChatComponentText(Reference.colorRed+"/gymadmin remove <Gym Name> - Removes the given Gym."));
            }
            if (args.length>=2) {
                PixelGyms.config.removeGym(args[1], player);
            }
        }
        if (args[0].equalsIgnoreCase("tp")) {
            if(args.length==1){
                commandSender.addChatMessage(new ChatComponentText(Reference.colorRed+"/gymadmin tp <Gym Name> [Player Name]- Teleports you or the given player to a specific gym warp."));
            }else {
                if (args.length > 2) {
                    try {
                        player = getPlayer(commandSender, args[2]);
                    } catch (Exception excep) {
                        commandSender.addChatMessage(new ChatComponentText(Reference.messagePrefix + Reference.colorRed + "Error: Player is not online or does not exist!"));
                    }
                }
                PixelGyms.gymHandler.teleportPlayer(player, args[1]);
            }
        }
        if (args[0].equalsIgnoreCase("addPlayer")) {
            if(args.length==1) {
                commandSender.addChatMessage(new ChatComponentText(Reference.colorRed+"/gymadmin addPlayer <Player Name> <Gym Name> - Adds a player to the given Gym."));
            }
            if (args.length > 2) {
                PixelGyms.gymHandler.addPlayer(getPlayer(commandSender, args[1]), args[2]);
            } else {
                player.addChatComponentMessage(new ChatComponentText(Reference.messagePrefix + Reference.colorRed + "Error: No Player specified!"));
            }
        }
        if (args[0].equalsIgnoreCase("removePlayer")) {
            if(args.length==1){
                commandSender.addChatMessage(new ChatComponentText(Reference.colorRed+"/gymadmin removePlayer <Player Name> - Removes a player "+Reference.colorRed+"from the queue they're in."));
            }
            if (args.length > 1) {
                PixelGyms.gymHandler.removePlayer(getPlayer(commandSender, args[1]));
            }
        }
        if (args[0].equalsIgnoreCase("nextPlayer")) {
            if(args.length==1){
                commandSender.addChatMessage(new ChatComponentText(Reference.colorRed+"/gymadmin nextPlayer <Gym Name> - Moves the queue along for the given Gym."));
            }else {
                PixelGyms.gymHandler.nextPlayer(args[1], player);
            }
        }
        if (args[0].equalsIgnoreCase("listPlayers")) {
            if(args.length==1){
                commandSender.addChatMessage(new ChatComponentText(Reference.colorRed+"/gymadmin listPlayers <Gym Name> - Lists all players in this Gym's queue."));
            }
            PixelGyms.gymHandler.listPlayers(player, args[1]);
        }
        if (args[0].equalsIgnoreCase("list")) {
            PixelGyms.gymHandler.listGyms(player);
        }
        if (args[0].equalsIgnoreCase("reload")) {
            PixelGyms.gymHandler.loadGyms();
            PixelGyms.config.reload();
            player.addChatComponentMessage(new ChatComponentText(Reference.messagePrefix+"Successfully reloaded Gyms!"));
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if(args.length==1)
        {
            return getListOfStringsMatchingLastWord(args, this.commands);
        }
        if(args.length==2)
        {
            if(args[0].equalsIgnoreCase("create")){

            }
        }
        return null;
    }
}