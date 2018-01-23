package start;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import services.RepoService;

public class CoolBot extends TelegramLongPollingBot {
    private static final String HELP =
            "Type any string and bot will save it as a note \n" +
                    "Type '/history' to see list of notes you have already saved \n" +
                    "Add '#hashtag' at the end of your note to mark the note with a hashtag, for example 'Hunger games#books'\n" +
                    "To see all notes with particular hashtag type '/#hastag', for example '/#books' " +
                    "Type '/myhashtags' to see all hashtags you have used\n";
    private RepoService repoService = new RepoService();

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
                case "/myhashtags":
                    allHashtagsProceed(message);
                    break;
                case "/help":
                    sendMsg(message, HELP);
                    break;
                default:
                    if (text.matches("^[\\\\/]#[a-zA-z0-9]+$")) {
                        String tags = repoService.allNotesWithHashTag(message);
                        sendMsg(message, "Your notes of " + tags);
                    } else {
                        repoService.add(message);
                        sendMsg(message, "Your note has been saved");
                    }

            }
        }
    }

    private void allHashtagsProceed(Message message) {
        String answer = repoService.getAllHashtgsByUser(message.getFrom());
        sendMsg(message, "There are all your hashtags:\n" + answer);
    }

    private void historyProceed(Message message) {
        String answer = repoService.getAllNotesOfUser(message.getFrom());
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