import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import parseNews.HitechFmRu;
import parseNews.CopyParseNews;

public class CopyTelegramBot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new TelegramBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    private final String ID_MY_GROUP = "@merder404";

    private final String START_TEXT = "Приветствую. Я бот, который ищет новости и публикует их в группе @merder404. Для вывода списка команд введите /help";

    private final String HELP_TEXT = "Список команд:" +
            "\n/runParse - запустить поиск новостей и публикацию их в группе" +
            "\n/stopParse - остановить поиск" +
            "\n/addTags - добавить ключевые слова для поиска статей" +
            "\n/outTags - вывод ключевых слов" +
            "\n/cleanTags - очистить список ключевых слов" +
            "\n/statistics - вывод количества опубликованных новостей после запуска парсера";

    private final String RUN_PARSE_TEXT = "Парсер запущен";
    private final String STOP_PARSE_TEXT = "Парсер остановлен";

    private final String ADD_TAGS_TEXT = "Для добавления ключевых слов введите \"Tags: tag1 tag2 ...\" ";
    private final String CLEANER_TAGS_TEXT = "Ключевые слова очищенны";

    private volatile String tags = "";
    private final int INTERVAL_SEARCH_NEWS = 2*60*1000;
    private int numberPublication = 0;

    private boolean runParse = false;

    @Override
    public void onUpdateReceived(Update update) {
        final Message msg = update.getMessage();

        String command = msg.getText();

        String[] tags = command.split("\\s");

        if(tags[0].equals("Tags:") && tags.length > 2){
            for(int i = 1; i < tags.length; i++){
                this.tags.concat(tags[i] + " ");
            }
            sendMsgInChat(msg,"Ключевые слова успешно добавлены");
        }

        switch (command){
            case "/start":{
                sendMsgInChat(msg, START_TEXT);
            }break;

            case "/help":{
                sendMsgInChat(msg, HELP_TEXT);
            }break;

            case "/runParse":{
                if(runParse){
                    sendMsgInChat(msg,"Парсер уже запущен");
                    break;
                }
                sendMsgInChat(msg, RUN_PARSE_TEXT);

                HitechFmRu hitechFmRu = new HitechFmRu();

                runParse(hitechFmRu);
            }break;


            case "/stopParse":{
                sendMsgInChat(msg,STOP_PARSE_TEXT);

                numberPublication = 0;
                runParse = false;
            }break;


            case "/addTags":{
                sendMsgInChat(msg, ADD_TAGS_TEXT);
            }break;


            case "/outTags":{
                if(this.tags.length() > 2)
                    sendMsgInChat(msg,this.tags);
                else
                    sendMsgInChat(msg, "no tags");
            }break;


            case "/cleanTags":{
                this.tags = "";
                sendMsgInChat(msg, CLEANER_TAGS_TEXT);
            }break;


            case "/statistics" :{
                sendMsgInChat(msg, numberPublication + " опубликованных новостей");
            }break;

            default:{
                //sendMsgInChat(msg, "Команда \""+msg.getText()+"\" не опознана");
            }
        }
    }


    private void sendMsgInChat(Message msg, String text){
        SendMessage s = new SendMessage();
        s.setChatId(msg.getChatId());
        s.setText(text);

        send(s);
    }

    private void sendMsgInGroup(String text){
        SendMessage s = new SendMessage();
        s.setChatId(ID_MY_GROUP);
        s.setText(text);

        send(s);
    }


    private void send(SendMessage sendMessage){
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void runParse(final CopyParseNews parseNews) {
        runParse = true;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(!runParse)
                        break;

                    parseNews.parseArchive();

                    if(parseNews.searchNews()){
                        sendMsgInGroup(parseNews.getNews());
                        numberPublication++;
                    }

                    timeStopParse();

                    if(!runParse) break;
                }
            }
        });

        thread.start();
    }

    private void timeStopParse(){
        try {
            Thread.sleep(INTERVAL_SEARCH_NEWS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "Hi News";    }

    @Override
    public String getBotToken() {
        return "614446665:AAH499OmylS8euKQiZhTgBukIYZ9NrDBZpw";
    }
}
