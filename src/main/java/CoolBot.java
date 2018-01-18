import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoolBot extends TelegramLongPollingBot {
    private static Map<Integer, List<String>> history = new HashMap<>();

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
        System.out.println(history);
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            final String text = message.getText();
            switch (text) {
                case "/start":
                    sendMsg(message, "Type any string and I will tell you is it a palindrome");
                    break;
                case "/history":
                    historyProceed(message);
                    break;
                case "/help":
                    sendMsg(message, Util.HELP);
                    break;
                default:
                    history.putIfAbsent(message.getFrom().getId(), new ArrayList<>());
                    history.get(message.getFrom().getId()).add(text);
                    sendMsg(message, String.valueOf(Util.isPalindrome(text)));

            }
        }
    }

    private void historyProceed(Message message) {
        String answer = history.getOrDefault(message.getFrom().getId() , new ArrayList<>()).toString();
        sendMsg(message, "There are words you have already checked:\n" + answer);
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
