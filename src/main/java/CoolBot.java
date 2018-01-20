import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import repositories.MapRepo;
import services.RepoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoolBot extends TelegramLongPollingBot {
    private RepoService repoService = new RepoService();

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new CoolBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "CoolBot";
    }

    @Override
    public String getBotToken() {
        return "501209732:AAFkfiOg9TW4BZL3FVoIhtb39nGmg0Tf7dI";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            final String text = message.getText();
            switch (text) {
                case "/start":
                    sendMsg(message, "Type you note here");
                    break;
                case "/history":
                    historyProceed(message);
                    break;
                case "/help":
                    sendMsg(message, Util.HELP);
                    break;
                default:
                    if(text.matches("^[\\\\/]#[a-zA-z0-9]+$")) {
                        String tags = repoService.allNotesWithHashTag(message);
                        sendMsg(message, "Your notes of " + tags);
                    }
                    else {
                        repoService.add(message);
                        sendMsg(message, "Your note has been added");
                    }

            }
        }
    }

    private void historyProceed(Message message) {
        String answer = repoService.getAllNotesOfUser(message.getFrom()).toString();
        sendMsg(message, "There are all your notes:\n" + answer);
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
