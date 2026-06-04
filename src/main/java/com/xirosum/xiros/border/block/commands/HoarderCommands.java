/* (C)2026 */
package com.xirosum.xiros.border.block.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.xirosum.xiros.border.block.XirosBorderBlock;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class HoarderCommands {
    public static void register(
            CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher,
            CommandRegistryAccess commandRegistryAccess,
            CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(
                CommandManager.literal("hoarder")
                        .requires(source -> source.hasPermissionLevel(0))
                        .then(
                                CommandManager.literal("clear")
                                        .requires(source -> source.hasPermissionLevel(4))
                                        .executes(
                                                context -> {
                                                    XirosBorderBlock.hoarder.clear();
                                                    return 1;
                                                }))
                        .then(
                                CommandManager.literal("completion-rate")
                                        .executes(
                                                context -> {
                                                    // calculate how many blocks available and how
                                                    // many have been found
                                                    XirosBorderBlock.hoarder.displayCompletion(
                                                            context.getSource().getPlayer());
                                                    return 1;
                                                }))
                        .then(
                                CommandManager.literal("activate")
                                        .executes(
                                                context -> {
                                                    XirosBorderBlock.hoarderData.activateHoarder();
                                                    return 1;
                                                }))
                        .then(
                                CommandManager.literal("deactivate")
                                        .executes(
                                                context -> {
                                                    XirosBorderBlock.hoarderData
                                                            .deactivateHoarder();
                                                    return 1;
                                                }))
                        .then(
                                CommandManager.literal("scoreboard")
                                        .then(
                                                CommandManager.literal("track")
                                                        .executes(
                                                                context -> {
                                                                    XirosBorderBlock
                                                                            .hoarderScoreBoard
                                                                            .addHoarderToScoreboard();
                                                                    return 1;
                                                                }))
                                        .then(
                                                CommandManager.literal("display")
                                                        .executes(
                                                                context -> {
                                                                    if (!XirosBorderBlock
                                                                            .hoarderData
                                                                            .isActive()) {
                                                                        return 0;
                                                                    }
                                                                    XirosBorderBlock
                                                                            .hoarderScoreBoard
                                                                            .setScoreboardDisplayPosition(
                                                                                    1);
                                                                    return 1;
                                                                }))
                                        .then(
                                                CommandManager.literal("hide")
                                                        .executes(
                                                                context -> {
                                                                    if (!XirosBorderBlock
                                                                            .hoarderData
                                                                            .isActive()) {
                                                                        return 0;
                                                                    }
                                                                    XirosBorderBlock
                                                                            .hoarderScoreBoard
                                                                            .setScoreboardDisplayPosition(
                                                                                    -1);
                                                                    return 1;
                                                                }))));
    }
}
