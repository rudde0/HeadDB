package tsp.headdb.core.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tsp.headdb.core.util.Utils;
import tsp.smartplugin.builder.item.ItemBuilder;
import tsp.smartplugin.inventory.Button;
import tsp.smartplugin.inventory.PagedPane;
import tsp.smartplugin.inventory.Pane;
import tsp.smartplugin.utils.StringUtils;

import java.util.Set;

public class CommandSettings extends SubCommand {

    public CommandSettings() {
        super("settings", "st");
    }

    @Override
    public void handle(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            getLocalization().sendConsoleMessage("noConsole");
            return;
        }

        Set<String> languages = getLocalization().getData().keySet();
        Pane pane = new Pane(1, StringUtils.colorize(getLocalization().getMessage(player.getUniqueId(), "menu.settings.name").orElse("&cHeadDB - Settings")));
        pane.addButton(new Button(new ItemBuilder(Material.BOOK)
                .name(getLocalization().getMessage(player.getUniqueId(), "menu.settings.language.name").orElse("&cLanguage"))
                .setLore(getLocalization().getMessage(player.getUniqueId(), "menu.settings.language.available").orElse("&7Languages Available: &e%size%").replace("%size%", String.valueOf(languages.size())))
                .build(), e -> {
            e.setCancelled(true);
            PagedPane langPane = new PagedPane(4, 6, Utils.translateTitle(getLocalization().getMessage(player.getUniqueId(), "menu.settings.language.title").orElse("&cHeadDB &7- &eSelect Language").replace("%languages%", "%size%"), languages.size(), "Selector: Language"));
            for (String lang : languages) {
                langPane.addButton(new Button(new ItemBuilder(Material.PAPER)
                        .name(getLocalization().getMessage(player.getUniqueId(), "menu.settings.language.format").orElse(ChatColor.YELLOW + lang).replace("%language%", lang))
                        .build(), langEvent -> {
                    e.setCancelled(true);
                    getLocalization().setLanguage(player.getUniqueId(), lang);
                    getLocalization().sendMessage(player.getUniqueId(), "languageChanged", msg -> msg.replace("%language%", lang));
                }));
            }

            langPane.open(player);
        }));

        pane.open(player);
    }

}
