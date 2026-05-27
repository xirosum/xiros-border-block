package com.xirosum.xiros.border.block.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.xirosum.xiros.border.block.XirosBorderBlock;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class HoarderCommands{
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(
                CommandManager.literal("hoarder")
                        .requires(source -> source.hasPermissionLevel(0))
                        .then(
                                CommandManager.literal("clear")
                                        .requires(source -> source.hasPermissionLevel(4))
                                        .executes(context -> {
                                    XirosBorderBlock.hoarder.clear();
                                    return 1;
                                }))
                        .then(CommandManager.literal("completePercentage")
                                .executes(context -> {
                                    //calculate how many blocks available and how many have been found
                                    XirosBorderBlock.hoarder.displayCompletion(context.getSource().getPlayer());
                                    return 1;
                                })
                        )

        );
    }
}
