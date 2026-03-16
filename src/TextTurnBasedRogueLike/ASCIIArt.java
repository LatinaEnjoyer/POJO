package TextTurnBasedRogueLike;

public class ASCIIArt {
    public static void goblinArt(){
        System.out.println(
                "       ,      ,\n" +
                        "      /(.-\"\"-.)\\\n" +
                        "  |\\  \\/      \\/  /|\n" +
                        "  | \\ / =.  .= \\ / |\n" +
                        "  \\( \\   o\\/o   / )/\n" +
                        "   \\_, '-/  \\-' ,_/\n" +
                        "     /   \\__/   \\\n" +
                        "     \\ \\__/\\__/ /\n" +
                        "   ___\\ \\|--|/ /___\n" +
                        " /`    \\      /    `\\\n" +
                        "/       '----'       \\"
        );
    }

    public static void undeadArt(){
        System.out.println(
                "      .-.\n" +
                        "     (o o)\n" +
                        "      |=|\n" +
                        "     __|__\n" +
                        "   //.=|=.\\\\\n" +
                        "  // .=|=. \\\\\n" +
                        "  \\\\ .=|=. //\n" +
                        "   \\\\(_=_)//\n" +
                        "    (:| |:)\n" +
                        "     || ||\n" +
                        "     () ()\n" +
                        "     || ||\n" +
                        "     || ||\n" +
                        "    ==' '=="
        );
    }

    public static void main(String[] args) {
        undeadArt();
        goblinArt();
    }
}
