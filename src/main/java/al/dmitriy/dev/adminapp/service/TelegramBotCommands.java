package al.dmitriy.dev.adminapp.service;

import al.dmitriy.dev.adminapp.model.TestObject;
import al.dmitriy.dev.adminapp.model.TestObjectRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.invoices.CreateInvoiceLink;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Slf4j
@Component
public class TelegramBotCommands extends TelegramLongPollingBot {

    private final HashMap<Long, String> TEMP_DATA = new HashMap<>();
    private final TelegramBotMethods method = new TelegramBotMethods();

    @Autowired
    private TestObjectRepository testObjectRepository;

    public TelegramBotCommands() {
        super("5684975537:AAHNI1ulaYG9U0ifSlOet3r6DClVoPWlgUk");

        /** Меню команд бота */
        List<BotCommand> listOfCommands = new ArrayList<>(); // BotCommand - класс определённый в библиотеке telegrambots
        listOfCommands.add(new BotCommand("/start", "set a welcome message"));  /** первый параметр - команда, второй - краткое описание команды */
        listOfCommands.add(new BotCommand("/mydata", "info about user")); // правильно для бота организовать вывод информации о пользователе
        listOfCommands.add(new BotCommand("/deletedata", "delete info about user"));
        listOfCommands.add(new BotCommand("/help", "help to use bot"));
        listOfCommands.add(new BotCommand("/miniapp", "Open mini app"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null)); /** Реализация меню бота */
        } catch (TelegramApiException e) {
            log.error("Error bot's command list" + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.equals("1")) { // клавиатура
                SendMessage sendMessage = method.receiveCreatedSendMessage(chatId, "Клавиатура для команды 1");
                method.setKeyBoard(sendMessage);
                executeSendMessage(sendMessage);

            } else if (messageText.equals("2")) { // клавиатура
                SendMessage sendMessage = method.receiveCreatedSendMessage(chatId, "Клавиатура для команды 2");
                method.bigKeyBoard(sendMessage);
                executeSendMessage(sendMessage);

            } else if (messageText.equals("3")) { // клавиатура
                SendMessage sendMessage = method.receiveCreatedSendMessage(chatId, "Клавиатура для команды 3");
                method.oneTimeKeyBoard(sendMessage);
                executeSendMessage(sendMessage);

            } else if (messageText.equals("a")) { // клавиатура
                executeSendMessage(method.receiveInlineKeyboardSendMessage(chatId, "Выберите кнопку для SendMessage"));

            } else if (messageText.equals("s")) { // Фото файл из сети в сообщении
                executePhotoMessage(method.receiveInlineKeyboardOneKeySendMessage(chatId, "Охлаждающий прибор"));

            } else if (messageText.equals("d")) { // Фото файл в сообщении
                executePhotoMessage(method.receiveCreatedSendPhotoFromDisc(chatId, "Текст сообщения"));

            } else if (messageText.equals("f")) { // Фото файл в сообщении + клавиатура
                executePhotoMessage(receiveImageWithButtons(chatId));

            } else if (messageText.equals("g")) { // Аудио файл в сообщении
                executeSendAudioMessage(method.receiveCreatedSendAudioFromDisc(chatId, "Текст сообщения"));

            } else if (messageText.equals("h")) { // gif в сообщении
                executeSendAudioMessage(method.receiveCreatedSendAnimation(chatId, "Текст сообщения"));

            } else if (messageText.equals("i")) { // alert
                executeSendAudioMessage(method.receiveCreatedSendAnimation(chatId, "Текст сообщения"));
                //public AnswerCallbackQuery receiveCreateAnswerCallbackQuery(String text, boolean showAlert, String callbackQuery)
                executeAnswerCallbackQuery(method.receiveCreateAnswerCallbackQuery("Алерт ✅", true, String.valueOf(chatId)));


            } else if (messageText.equals("j")) { // gif в сообщении
                executeSendAudioMessage(method.receiveCreatedSendAnimation(chatId, "Текст сообщения"));

            } else if (messageText.equals("/start")) {
                SendMessage sendMessage = method.receiveCreatedSendMessage(chatId, "Команда /start");
                executeSendMessage(sendMessage);

            } else if (messageText.equals("/mydata")) {
                SendMessage sendMessage = method.receiveCreatedSendMessage(chatId, "Info about me");
                executeSendMessage(sendMessage);
//**************************************************************  CRUD  ***********************************************************************************************
            } else if (messageText.contains("create")) {
                String[] data = messageText.split(" ");
                int id = parseInt(data[1]); //new TestObject(id, data[2])
                TestObject testObject = new TestObject();
                testObject.setId(id);
                testObject.setName(data[2]);
                testObjectRepository.save(testObject);
                SendMessage sendMessage = method.receiveCreatedSendMessage(chatId, "testObjectRepository saved " + data[2] + " id= " + data[1]);
                executeSendMessage(sendMessage);

            }  else if (messageText.contains("show")) {
                Iterable<TestObject> testObjects = testObjectRepository.findAll();

                SendMessage sendMessage = method.receiveCreatedSendMessage(chatId, "Objects: " + testObjects);
                executeSendMessage(sendMessage);

            }  else if (messageText.contains("del")) {
                String[] data = messageText.split(" ");
                int id = parseInt(data[1]);
                testObjectRepository.deleteById(id);
              //  testObjectRepository.deleteByIdAllIgnoreCase(id);
                SendMessage sendMessage = method.receiveCreatedSendMessage(chatId, "Deleted");
                executeSendMessage(sendMessage);
//**********************************************************************************************************************************************************************
            }  else if (messageText.equals("l")) { // Отправка документа
                executeSendDocument(method.receiveCreatedSendDocument(chatId));

            } else if (TEMP_DATA.get(chatId) != null && TEMP_DATA.get(chatId).contains(messageText)) { // Реакция на клик кнопки одноразовой экранной клавиатуры
                SendMessage sendMessage = method.receiveCreatedSendMessage(chatId, "Вы выбрали " + messageText);
                method.setKeyBoard(sendMessage);
                executeSendMessage(sendMessage);
                TEMP_DATA.remove(chatId);

            } else if (TEMP_DATA.get(chatId) == null) {
                executeSendMessage(method.receiveCreatedSendMessage(chatId, "Такая команда отсутствует"));
            }

            if (TEMP_DATA.get(chatId) != null && TEMP_DATA.get(chatId).equals("Команда")) {
                // логика
            }

            // Если update содержит изменённое сообщение
        } else if (update.hasCallbackQuery()) {
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String callbackData = update.getCallbackQuery().getData();

            if (callbackData.contains("%1")) {
                executeEditMessageText(method.receiveInlineKeyboardEditMessage(chatId, messageId, "Выберите кнопку для EditMessage 1"));
            } else if (callbackData.contains("%2")) {
                executeEditMessageText(method.receiveInlineKeyboardEditMessage(chatId, messageId, "Выберите кнопку для EditMessage 2"));
            } else if (callbackData.contains("%3")) {
                executeEditMessageText(method.receiveInlineKeyboardEditMessage(chatId, messageId, "Выберите кнопку для EditMessage 3"));
            } else if (callbackData.contains("%4")) {
                executeEditMessageText(method.receiveInlineKeyboardEditMessage(chatId, messageId, "Выберите кнопку для EditMessage 4"));
            } else if (callbackData.contains("@4")) {
                executeAnswerCallbackQuery(method.receiveCreateAnswerCallbackQuery("Текст во всплывающем окне", false, update.getCallbackQuery().getId()));
            } else if (callbackData.contains("@3")) {
                executeAnswerCallbackQuery(method.receiveCreateAnswerCallbackQuery("Текст во всплывающем окне", true, update.getCallbackQuery().getId()));
            } else if (callbackData.contains("/miniapp")) {
                executeAnswerCallbackQuery(method.receiveCreateAnswerCallbackQuery("miniapp", true, update.getCallbackQuery().getId()));
            }

            //  Pre Checkout Query отвечает за обработку и утверждение платежа перед тем, как пользователь его совершит. Так можно проверить доступность товара на складе или уточнить стоимость.
        } else if (update.hasPreCheckoutQuery()) {
            PreCheckoutQuery preCheckoutQuery = update.getPreCheckoutQuery();
            String preCheckoutQueryId = preCheckoutQuery.getId();
            System.out.println(preCheckoutQuery.getOrderInfo() + "\n" + preCheckoutQuery.getFrom() + "\n" + preCheckoutQuery.getInvoicePayload() + "\n" + preCheckoutQuery.getCurrency()  + "\n" + preCheckoutQuery.getTotalAmount() + "\n" + preCheckoutQuery.getShippingOptionId());

            AnswerPreCheckoutQuery answerPreCheckoutQuery = new AnswerPreCheckoutQuery();
            answerPreCheckoutQuery.setPreCheckoutQueryId(preCheckoutQueryId);
            answerPreCheckoutQuery.setOk(true);
            answerPreCheckoutQuery.setErrorMessage("Причина отмены");

            try {
                execute(answerPreCheckoutQuery);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

    }


    @Override
    public String getBotUsername() {
        return "TestDemoUnicNameBot";
    }


    // Отправка изображения, установка одноразовой экранной клавиатуры и реакция на нажатие кнопки экранной клавиатуры
    public SendPhoto receiveImageWithButtons(long chatId) {
        String buttonText;
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setCaption("Выберите номер на картинке"); // прикреплённое сообщения
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(new File("C:\\cars.jpg"))); // установить изображение из файла на диске

        // Добавление одноразовой клавиатуры
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow topRow = new KeyboardRow();
        topRow.add("Кнопка 1");
        buttonText = "Кнопка 1";

        topRow.add("Кнопка 2");
        buttonText += "Кнопка 2";
        keyboardRows.add(topRow);

        KeyboardRow lowRow = new KeyboardRow();
        lowRow.add("Кнопка 3");
        buttonText += "Кнопка 3";

        lowRow.add("Кнопка 4");
        buttonText += "Кнопка 4";
        keyboardRows.add(lowRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendPhoto.setReplyMarkup(replyKeyboardMarkup);
        TEMP_DATA.put(chatId, buttonText); // сохраняется строка buttonText для сравнения с сообщением нажатой кнопки
        return sendPhoto;
    }


    public void executeSendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            //log.error("SendMessage execute error: " + e.getMessage());
        }
    }


    private void executeEditMessageText(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            //log.error("SendMessage execute error: " + e.getMessage());
        }
    }


    private void executePhotoMessage(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            //log.error("SendMessage execute error: " + e.getMessage());
        }
    }


    private void executeSendAudioMessage(SendAudio sendAudio) {
        try {
            execute(sendAudio);
        } catch (TelegramApiException e) {
            //log.error("SendMessage execute error: " + e.getMessage());
        }
    }


    private void executeAnswerCallbackQuery(AnswerCallbackQuery answerCallbackQuery) {
        try {
            execute(answerCallbackQuery);
        } catch (TelegramApiException e) {
            //log.error("SendMessage execute error: " + e.getMessage());
        }
    }


    private void executeSendAudioMessage(SendAnimation sendAnimation) {
        try {
            execute(sendAnimation);
        } catch (TelegramApiException e) {
            //log.error("SendMessage execute error: " + e.getMessage());
        }
    }


    private void executeSendDocument(SendDocument sendDocument) {
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            //log.error("SendMessage execute error: " + e.getMessage());
        }
    }

    // Используйте этот метод, когда вам нужно сообщить пользователю, что на стороне бота что-то происходит
    private void executeSendChatAction(SendChatAction sendChatAction) {
        try {
            execute(sendChatAction);
        } catch (TelegramApiException e) {
            //log.error("SendMessage execute error: " + e.getMessage());
        }
    }


    private String executeInvoiceLincAndSendMessage(CreateInvoiceLink createInvoiceLink) {
        String invoiceLincUrl = "null";
        try {
            invoiceLincUrl = execute(createInvoiceLink);
        } catch (TelegramApiException e) {
            log.error("SendMessage execute error: " + e.getMessage());
            System.out.println("Err: " + e.getMessage());
        }
        return invoiceLincUrl;
    }


    private void executeDeleteMessage(DeleteMessage deleteMessage) {
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {

        }
    }


    public void executeEditMessageMedia(EditMessageMedia editMessageMedia) {
        try {
            execute(editMessageMedia);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }


/*
    private <T> void executeT(T t) {
        try {
            execute(t);
        } catch (TelegramApiException e) {
            //log.error("SendMessage execute error: " + e.getMessage());
        }
    }
 */

    /////////////////////////////////////////////////////////////////   TEST   /////////////////////////////////////////////////////////////////////////////////



}
/*
Принято! Ваши тестовые настройки:
shopId 506751
shopArticleId 538350
Здесь всё. Теперь возвращайтесь к @BotFather — он пришлет тестовый платежный токен. Для оплаты используйте
данные тестовой карты: 1111 1111 1111 1026, 12/22, CVC 000.
Когда будете готовы принимать настоящие платежи, выберите у @BotFather своего бота, затем Bot Settings ->
Payments -> ЮKassa: платежи. Вам потребуется настоящий shopId, его выдают после регистрации в ЮKassa: https://yookassa.ru/joinups


Payment providers for TestDemoBot @TestDemoUnicNameBot.
1 method connected:
- ЮKassa Test: 381764678:TEST:62053
 */