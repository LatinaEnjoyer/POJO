package TextTurnBasedRogueLike;

public class TextWriter {
    private static final long delay = 17;

    public static void print(String text){
        for(int i = 0; i < text.length(); i++){
            System.out.print(text.charAt(i));
            System.out.flush();

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {}
        }
        System.out.println();
    };
}


