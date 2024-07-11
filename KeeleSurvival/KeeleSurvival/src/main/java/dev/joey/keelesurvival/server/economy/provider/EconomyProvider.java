package dev.joey.keelesurvival.server.economy.provider;

import dev.joey.keelesurvival.server.economy.Storage;
import dev.joey.keelecore.util.UtilClass;
import dev.joey.keelesurvival.util.Util;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Collections;
import java.util.List;

public class EconomyProvider implements Economy {
    @Override
    public boolean isEnabled() {
        return Util.keeleSurvival.isEnabled();
    }

    @Override
    public String getName() {
        return "KeeleEconomy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 1000;
    }

    @Override
    public String format(double v) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String s) {
        return Storage.getPlayerBalance().containsKey(Bukkit.getOfflinePlayer(s).getUniqueId());
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return Storage.getPlayerBalance().containsKey(offlinePlayer.getUniqueId());
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return hasAccount(s);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return hasAccount(offlinePlayer);
    }

    @Override
    public double getBalance(String s) {
        return Storage.getPlayerBalance().get(Bukkit.getPlayer(s).getUniqueId());
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return Storage.getPlayerBalance().get(offlinePlayer.getUniqueId());
    }

    @Override
    public double getBalance(String s, String s1) {
        return getBalance(s);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return getBalance(offlinePlayer);
    }

    @Override
    public boolean has(String s, double v) {
        return Storage.getPlayerBalance().get(Bukkit.getPlayer(s).getUniqueId()) >= v;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return Storage.getPlayerBalance().get(offlinePlayer.getUniqueId()) >= v;
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return has(s, v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return has(offlinePlayer, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        if (Storage.getPlayerBalance().get(Bukkit.getPlayer(s).getUniqueId()) < v) {
            return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.FAILURE, "Insufficient funds");
        }
        Storage.getPlayerBalance().put(Bukkit.getPlayer(s).getUniqueId(),
                Storage.getPlayerBalance().get(Bukkit.getPlayer(s).getUniqueId()) - v);

        UtilClass.sendPlayerMessage(Bukkit.getPlayer(s), Storage.getPrefix() + v + " has been withdrawn from your account", UtilClass.success);

        return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        if (Storage.getPlayerBalance().get(offlinePlayer.getUniqueId()) < v) {
            return new EconomyResponse(v, getBalance(offlinePlayer), EconomyResponse.ResponseType.FAILURE, "Insufficient funds");
        }

        UtilClass.sendPlayerMessage(Bukkit.getPlayer(offlinePlayer.getUniqueId()), Storage.getPrefix() + v + " has been withdrawn from your account", UtilClass.success);
        Storage.getPlayerBalance().put(offlinePlayer.getUniqueId(),
                Storage.getPlayerBalance().get(offlinePlayer.getUniqueId()) - v);
        return new EconomyResponse(v, getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return withdrawPlayer(s, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return withdrawPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        Storage.getPlayerBalance().put(Bukkit.getPlayer(s).getUniqueId(), Storage.getPlayerBalance().get(Bukkit.getPlayer(s).getUniqueId()) + v);
        UtilClass.sendPlayerMessage(Bukkit.getPlayer(s), Storage.getPrefix() + v + " has been deposited into your account", UtilClass.success);
        return new EconomyResponse(v, getBalance(s), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        Storage.getPlayerBalance().put(offlinePlayer.getUniqueId(), Storage.getPlayerBalance().get(offlinePlayer.getUniqueId()) + v);
        UtilClass.sendPlayerMessage(Bukkit.getPlayer(offlinePlayer.getUniqueId()), Storage.getPrefix() + v + " has been deposited into your account", UtilClass.success);
        return new EconomyResponse(v, getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, null);

    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return depositPlayer(s, v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return depositPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks may be a feature in the future however currently we dont support them!");
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks may be a feature in the future however currently we dont support them!");

    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks may be a feature in the future however currently we dont support them!");

    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks may be a feature in the future however currently we dont support them!");

    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks may be a feature in the future however currently we dont support them!");

    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks may be a feature in the future however currently we dont support them!");

    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks may be a feature in the future however currently we dont support them!");

    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks may be a feature in the future however currently we dont support them!");

    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks may be a feature in the future however currently we dont support them!");

    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks may be a feature in the future however currently we dont support them!");

    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks may be a feature in the future however currently we dont support them!");

    }

    @Override
    public List<String> getBanks() {
        return Collections.emptyList();
    }

    @Override
    public boolean createPlayerAccount(String s) {

        if (Storage.getPlayerBalance().containsKey(Bukkit.getPlayer(s).getUniqueId())) {
            return false;
        } else {
            Storage.getPlayerBalance().put(Bukkit.getPlayer(s).getUniqueId(), Double.valueOf(Util.keeleSurvival.getConfig().get("starting-balance").toString()));
            return true;
        }
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        if (Storage.getPlayerBalance().containsKey(offlinePlayer.getUniqueId())) {
            return false;
        } else {
            Storage.getPlayerBalance().put(offlinePlayer.getUniqueId(), Double.valueOf(Util.keeleSurvival.getConfig().get("starting-balance").toString()));
            return true;
        }
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return createPlayerAccount(s);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return createPlayerAccount(offlinePlayer);
    }
}
