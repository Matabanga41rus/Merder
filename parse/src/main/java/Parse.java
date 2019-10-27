import java.io.IOException;

public class Parse {
    public static void main(String[] args) throws IOException {
        HitechFmRu hitechFmRu = new HitechFmRu();

        while(true){
            hitechFmRu.parsePage();


            if(hitechFmRu.searchNews("земледелие ничего Хайтек"))
                System.out.println(hitechFmRu.getNews());

            try {
                Thread.sleep(2*60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
