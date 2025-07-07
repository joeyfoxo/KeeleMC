package dev.joey.keelecore.admin.commands;

import dev.joey.keelecore.managers.supers.SuperCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;
import com.sun.management.OperatingSystemMXBean;

public class DebugCommand extends SuperCommand {

    private final Logger logger = Bukkit.getLogger();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        try {
            String internalIp = InetAddress.getLocalHost().getHostAddress();
            String externalIp = "Unavailable";
            try {
                URL url = new URL("https://api.ipify.org");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    externalIp = in.readLine();
                }
            } catch (Exception e) {
                logger.warning("Failed to fetch external IP: " + e.getMessage());
            }

            int port = Bukkit.getServer().getPort();

            Runtime runtime = Runtime.getRuntime();
            long totalMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();
            long usedMemory = totalMemory - freeMemory;
            long maxMemory = runtime.maxMemory();
            double memoryUsagePercent = (double) usedMemory / maxMemory * 100;

            int cpuCores = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
            String cpuLoad = "Unavailable";

            try {
                OperatingSystemMXBean sunOsBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                double processCpuLoad = sunOsBean.getProcessCpuLoad();
                if (processCpuLoad >= 0) {
                    cpuLoad = String.format("%.2f", processCpuLoad * 100);
                }
            } catch (ClassCastException ignored) {}

            logInfo("---------- Debug Info ----------");
            logInfo("Internal IP Address: " + internalIp);
            logInfo("External IP Address: " + externalIp);
            logInfo("Port: " + port);
            logInfo(String.format("Memory Usage: %.2f%% (%.2f MB / %.2f MB)",
                    memoryUsagePercent,
                    usedMemory / 1024.0 / 1024.0,
                    maxMemory / 1024.0 / 1024.0));
            logInfo("CPU Cores: " + cpuCores);
            logInfo("CPU Usage: " + cpuLoad + "%");

            sender.sendMessage("§bDebug info logged to console.");
        } catch (Exception e) {
            logger.warning("Error while generating debug info: " + e.getMessage());
            e.printStackTrace();
            sender.sendMessage("§cFailed to generate debug info.");
        }

        return true;
    }

    private void logInfo(String message) {
        logger.info("[DebugCommand] " + message);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        return List.of();
    }
}