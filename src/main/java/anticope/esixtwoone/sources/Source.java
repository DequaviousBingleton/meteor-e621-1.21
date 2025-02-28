package anticope.esixtwoone.sources;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import anticope.esixtwoone.E621Hud;

public abstract class Source {
    public enum Size {
        preview,
        sample,
        file
    }

    public enum SourceType {
        e621,
        e926,
        gelbooru,
        danbooru,
        safebooru,
        rule34,
        nekoslife
    }

    protected final Random random = new Random();

    public abstract void reset();

    protected abstract String randomImage(String filter, Size size);

    public String getRandomImage(String filter, Size size) {
        try {
            return randomImage(URLEncoder.encode(filter, StandardCharsets.UTF_8), size);
        } catch (Exception ex) {
            E621Hud.LOG.error("Failed to fetch an image.", ex);
        }
        return null;
    }

    public static Source getSource(SourceType type) {
        return switch (type) {
            case e621 -> new ESixTwoOne("https://e621.net");
            case e926 -> new ESixTwoOne("https://e926.net");
            case gelbooru -> new GelBooru("https://gelbooru.com", 700);
            case danbooru -> new DanBooru("https://danbooru.donmai.us", 700);
            case safebooru -> new GelBooru("https://safebooru.org", 700);
            case rule34 -> new GelBooru("https://api.rule34.xxx", 700);
            case nekoslife -> new NekosLife("https://nekos.life");
        };
    }
}
