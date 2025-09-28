package al.dmitriy.dev.adminapp.service;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.invoices.CreateInvoiceLink;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TelegramBotMethods {

/*
    public SendMessage receiveWebAppButtonMessage(long chatId, String messageText, String buttonText, String callbackData, String url) {
        WebAppInfo webAppInfo = new WebAppInfo("https://dmitriy-al.github.io/Miniapp/");

        SentWebAppMessage sentWebAppMessage = new SentWebAppMessage("" + chatId);

        SendMessage sendMessage = receiveCreatedSendMessage(chatId, messageText);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> firstRowInlineButton = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonText);
        button.setCallbackData(callbackData);
        button.setWebApp(webAppInfo);
        firstRowInlineButton.add(button);
        rowsInline.add(firstRowInlineButton);

        inlineKeyboardMarkup.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }
 */


    public SendMessage receiveInlineKeyboardSendMessage(long chatId, String text) { // клавиатура к сообщению бота
        SendMessage sendMessage = receiveCreatedSendMessage(chatId, text);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>(); // коллекция коллекций с горизонтальным рядом кнопок, создаёт вертикальный ряд кнопок
        List<InlineKeyboardButton> firstRowInlineButton = new ArrayList<>(); // коллекция с горизонтальным рядом кнопок
        List<InlineKeyboardButton> secondRowInlineButton = new ArrayList<>(); // коллекция с горизонтальным рядом кнопок
        List<InlineKeyboardButton> thirdRowInlineButton = new ArrayList<>(); // коллекция с горизонтальным рядом кнопок

        InlineKeyboardButton leftTopButton = new InlineKeyboardButton();
        leftTopButton.setText("Кнопка 1");
        leftTopButton.setCallbackData("%1");
        firstRowInlineButton.add(leftTopButton);

        InlineKeyboardButton rightTopButton = new InlineKeyboardButton();
        rightTopButton.setText("Кнопка 2");
        rightTopButton.setCallbackData("%2");
        firstRowInlineButton.add(rightTopButton);

        InlineKeyboardButton setVacationButton = new InlineKeyboardButton();
        setVacationButton.setText("Кнопка 3");
        setVacationButton.setCallbackData("%3");
        secondRowInlineButton.add(setVacationButton);

        InlineKeyboardButton deleteVacationButton = new InlineKeyboardButton();
        deleteVacationButton.setText("Кнопка 4");
        deleteVacationButton.setCallbackData("%4");
        thirdRowInlineButton.add(deleteVacationButton);

        rowsInline.add(firstRowInlineButton); // размещение набора кнопок в вертикальном ряду
        rowsInline.add(secondRowInlineButton); // размещение набора кнопок в вертикальном ряду
        rowsInline.add(thirdRowInlineButton);

        inlineKeyboardMarkup.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        return sendMessage;
    }


    public EditMessageText receiveInlineKeyboardEditMessage(long chatId, long messageId, String data) { // клавиатура к изменённому сообщению бота
        EditMessageText editMessageText = receiveEditMessageText(chatId, messageId, data);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>(); // коллекция коллекций с горизонтальным рядом кнопок, создаёт вертикальный ряд кнопок
        List<InlineKeyboardButton> firstRowInlineButton = new ArrayList<>(); // коллекция с горизонтальным рядом кнопок
        List<InlineKeyboardButton> secondRowInlineButton = new ArrayList<>(); // коллекция с горизонтальным рядом кнопок
        List<InlineKeyboardButton> thirdRowInlineButton = new ArrayList<>(); // коллекция с горизонтальным рядом кнопок

        InlineKeyboardButton leftTopButton = new InlineKeyboardButton();
        leftTopButton.setText("Переход к меню оплаты");
        leftTopButton.setUrl("https://t.me/$0Ce70hNG4UlpCwAAuBDQ8Rv716w");
        leftTopButton.setCallbackData("@1"); // запись мз поля tempSetDoctor, для быстрого возврата к дате в расписании доктора из метода chooseTime()
        firstRowInlineButton.add(leftTopButton);

        InlineKeyboardButton rightTopButton = new InlineKeyboardButton();
        rightTopButton.setUrl("https://i.postimg.cc/vBPzBQvV/turtle.jpg"); // клик по кнопке открывает диалоговое окно с предложением перейти по ссылке
        rightTopButton.setText("Ссылка для перехода");
        rightTopButton.setCallbackData("@2"); // запись мз поля tempSetDoctor, для быстрого возврата к дате в расписании доктора из метода chooseTime()
        firstRowInlineButton.add(rightTopButton);

        InlineKeyboardButton midButton = new InlineKeyboardButton();
        midButton.setSwitchInlineQueryCurrentChat("switchInlineQuery");
        midButton.setText("Вызов всплывающего окна");
        midButton.setCallbackData("@3");
        secondRowInlineButton.add(midButton);

        InlineKeyboardButton downButton = new InlineKeyboardButton();
        downButton.setText("Вызов alert-надписи");
        downButton.setCallbackData("@4");
        thirdRowInlineButton.add(downButton);

        rowsInline.add(firstRowInlineButton); // размещение набора кнопок в вертикальном ряду
        rowsInline.add(secondRowInlineButton); // размещение набора кнопок в вертикальном ряду
        rowsInline.add(thirdRowInlineButton);

        inlineKeyboardMarkup.setKeyboard(rowsInline);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);

        return editMessageText;
    }

    // Набор кнопок экранной клавиатуры
    public void setKeyBoard(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true); // уменьшенные кнопки
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add("Самая верхняя кнопка");
        keyboardRows.add(firstRow);

        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add("Вторая сверху кнопка");
        keyboardRows.add(secondRow);

        KeyboardRow thirdRow = new KeyboardRow();
        thirdRow.add("3 сверху левая кнопка");
        thirdRow.add("3 сверху правая кнопка");
        keyboardRows.add(thirdRow);

        KeyboardRow fourthRow = new KeyboardRow();
        fourthRow.add("4 сверху левая кнопка");
        fourthRow.add("4 сверху правая кнопка");
        keyboardRows.add(fourthRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }

    // Набор больших кнопок экранной клавиатуры
    public void bigKeyBoard(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow topRow = new KeyboardRow();
        topRow.add("1 Кнопка верх");
        topRow.add("2 Кнопка верх");
        keyboardRows.add(topRow);

        KeyboardRow lowRow = new KeyboardRow();
        lowRow.add("1 Кнопка низ");
        lowRow.add("2 Кнопка низ");
        keyboardRows.add(lowRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }

    // Одноразовая клавиатура
    public void oneTimeKeyBoard(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);  // одноразовая клавиатура
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow topRow = new KeyboardRow();
        topRow.add("1 Кнопка верх");
        topRow.add("2 Кнопка верх");
        keyboardRows.add(topRow);

        KeyboardRow lowRow = new KeyboardRow();
        lowRow.add("1 Кнопка низ");
        lowRow.add("2 Кнопка низ");
        keyboardRows.add(lowRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }

    // Отправка изображения и установка одноразовой экранной клавиатуры
    public SendPhoto receiveCreatedSendPhotoFromNet(long chatId, String photoUrl) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(photoUrl)); // установить изображение по ссылке из сети

        // Добавление одноразовой клавиатуры
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);  // одноразовая клавиатура
        List<KeyboardRow> keyboardRow = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("1 Кнопка");
        row.add("2 Кнопка");
        keyboardRow.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRow);
        sendPhoto.setReplyMarkup(replyKeyboardMarkup);

        return sendPhoto;
    }


    // Метод для тестов меню товара
    public SendPhoto receiveInlineKeyboardOneKeySendMessage(long chatId, String text) {
        SendPhoto sendPhoto = receiveCreatedSendPhotoFromNet(chatId, "");
        sendPhoto.setCaption("Охлаждающий прибор");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>(); // коллекция коллекций с горизонтальным рядом кнопок, создаёт вертикальный ряд кнопок
        List<InlineKeyboardButton> firstRowInlineButton = new ArrayList<>(); // коллекция с горизонтальным рядом кнопок

        InlineKeyboardButton leftTopButton = new InlineKeyboardButton();
        leftTopButton.setText("Хочу купить!");
        leftTopButton.setUrl("https://t.me/$0Ce70hNG4UlpCwAAuBDQ8Rv716w");
        leftTopButton.setCallbackData(""); // запись мз поля tempSetDoctor, для быстрого возврата к дате в расписании доктора из метода chooseTime()
        firstRowInlineButton.add(leftTopButton);

        rowsInline.add(firstRowInlineButton); // размещение набора кнопок в вертикальном ряду

        inlineKeyboardMarkup.setKeyboard(rowsInline);
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);

        return sendPhoto;
    }


    // Отправка аудио сообщения с прикреплённым текстовым сообщением
    public SendAudio receiveCreatedSendAudioFromNet(long chatId, String text) {
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(chatId);
        sendAudio.setCaption(text); // прикреплённое сообщения
        sendAudio.setTitle(text); // Текст прикреплённый к записи
        sendAudio.setAudio(new InputFile("https://zvukogram.com/zvuk/75053/"));

        // Добавление одноразовой клавиатуры
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);  // одноразовая клавиатура
        List<KeyboardRow> keyboardRow = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("1 Кнопка");
        row.add("2 Кнопка");
        keyboardRow.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRow);
        sendAudio.setReplyMarkup(replyKeyboardMarkup);

        return sendAudio;
    }

    // Отправка аудио сообщения с прикреплённым текстовым сообщением
    public SendAudio receiveCreatedSendAudioFromDisc(long chatId, String text) {
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(chatId);
        sendAudio.setCaption(text); // прикреплённое сообщения
        sendAudio.setTitle("Текст прикреплённый к записи"); // Текст прикреплённый к записи
        sendAudio.setAudio(new InputFile(new File("C:\\sound.mp3")));
        return sendAudio;
    }


    // Используйте этот метод, когда вам нужно сообщить пользователю, что что-то происходит на стороне бота. Статус устанавливается на 5 секунд или меньше (когда от вашего бота приходит сообщение, клиенты Telegram сбрасывают статус ввода).
    public SendChatAction receiveCreatedFromDisc(long chatId, String text) {
        SendChatAction chatAction = new SendChatAction();
        chatAction.setAction(ActionType.FINDLOCATION);
        return chatAction;
    }

    // Отправка спойлера изображения с прикреплённым сообщением
    public SendPhoto receiveCreatedSendPhotoFromDisc(long chatId, String text) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setCaption(text); // прикреплённое сообщения
        sendPhoto.setHasSpoiler(true); // заблюренное изображение, открывается по клику
        sendPhoto.setPhoto(new InputFile(new File("C:\\pic.jpg"))); // установить изображение из файла на диске
        return sendPhoto;
    }


    // Отправка сообщения gif с прикреплённым сообщением
    public SendAnimation receiveCreatedSendAnimation(long chatId, String text) {
        SendAnimation sendAnimation = new SendAnimation();
        sendAnimation.setChatId(chatId);
        sendAnimation.setCaption(text); // прикреплённое сообщения
        sendAnimation.setAnimation(new InputFile(new File("C:\\gif.gif")));// setDuration - продолжительность
        return sendAnimation;
    }

    // Редактирование отправленного сообщения
    public EditMessageText receiveEditMessageText(long chatId, long messageId, String messageText) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId((int) messageId);
        editMessageText.setText(messageText);
        return editMessageText;
    }

    // Отправка сообщения
    public SendMessage receiveCreatedSendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        return sendMessage;
    }

    // Отправка документа
    public SendDocument receiveCreatedSendDocument(long chatId) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setDocument(new InputFile(new File("C:\\jsonfile.json")));
        // sendDocument.setProtectContent(true); при установке true полученный документ нельзя переотправить другому контакту из чата
        // sendDocument.setReplyMarkup(); добавить экранную клавиатуру
        return sendDocument;
    }

    // Всплывающее окно с сообщением (alert)
    public AnswerCallbackQuery receiveCreateAnswerCallbackQuery(String text, boolean showAlert, String callbackQueryId){
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackQueryId);
        answerCallbackQuery.setText(text); // прикреплённое сообщения
        answerCallbackQuery.setShowAlert(showAlert); // Присутствие/отсутствие всплывающего окна (текст выводится в любом случае)
        return answerCallbackQuery;
    }

    // Метод удаляет любое сообщение
    public DeleteMessage deleteMessage(long chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(messageId);
        deleteMessage.setChatId(chatId);
        return deleteMessage;
    }


    /* Используйте этот метод, когда вам нужно сообщить пользователю, что на стороне бота что-то происходит. Статус устанавливается на 5 секунд или меньше
    (когда приходит сообщение от вашего бота, клиенты Telegram сбрасывают его статус ввода). Возвращает True при успешном выполнении.
    Пример: ImageBot требуется некоторое время для обработки запроса и загрузки изображения. Вместо отправки текстового сообщения типа “Получение изображения,
    пожалуйста, подождите ...”, бот может использовать sendChatAction с действием = upload_photo. Пользователь увидит статус “отправка фотографии” для бота. */
    public SendChatAction receiveCreatedSendChatAction(long chatId) {
        SendChatAction chatAction = new SendChatAction();
        chatAction.setChatId(chatId);
        chatAction.setAction(ActionType.UPLOADPHOTO);
        return chatAction;
    }


    public CreateInvoiceLink receiveCreatedInvoiceLinc(@NonNull String title, @NonNull String description, @NonNull String payload, @NonNull String providerToken, @NonNull String currency, @NonNull List<LabeledPrice> prices,
                                                       String photoUrl, Integer photoSize, Integer photoWidth, Integer photoHeight, Boolean needName, Boolean needPhoneNumber, Boolean needEmail, Boolean needShippingAddress,
                                                       Boolean isFlexible, Boolean sendPhoneNumberToProvider, Boolean sendEmailToProvider, String providerData, Integer maxTipAmount, List<Integer> suggestedTipAmounts) {

        CreateInvoiceLink createInvoiceLink = new CreateInvoiceLink(title, description, payload, providerToken, currency, prices, photoUrl, photoSize, photoWidth, photoHeight,
                needName, needPhoneNumber, needEmail, needShippingAddress, isFlexible, sendPhoneNumberToProvider, sendEmailToProvider, providerData, maxTipAmount, suggestedTipAmounts);
        return createInvoiceLink;
    }

    // Метод меняет медиаданные сообщения
    protected EditMessageMedia receiveEditMessageMedia(long chatId, String pictureLinc, String descriptionText) {
        EditMessageMedia editMessageMedia = new EditMessageMedia();
        editMessageMedia.setMessageId(1);
        editMessageMedia.setChatId(chatId);
        editMessageMedia.setMedia(new InputMediaPhoto(pictureLinc));
        return editMessageMedia;
    }



    /////////////////////////////////////////////////////////////////   TEST   /////////////////////////////////////////////////////////////////////////////////



    public SendPhoto receiveSendPhotoMenu(long chatId, String imgText, String imgLinc, String buttonText, String callbackData, String invoiceLincUrl) {
        SendPhoto sendPhoto = receiveCreatedSendPhotoFromNet(chatId, imgLinc);
        sendPhoto.setCaption(imgText);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>(); // коллекция коллекций с горизонтальным рядом кнопок, создаёт вертикальный ряд кнопок
        List<InlineKeyboardButton> firstRowInlineButton = new ArrayList<>(); // коллекция с горизонтальным рядом кнопок

        InlineKeyboardButton leftTopButton = new InlineKeyboardButton();
        leftTopButton.setText(buttonText);
        leftTopButton.setUrl(invoiceLincUrl);
        leftTopButton.setCallbackData(callbackData); // запись мз поля tempSetDoctor, для быстрого возврата к дате в расписании доктора из метода chooseTime()
        firstRowInlineButton.add(leftTopButton);

        rowsInline.add(firstRowInlineButton); // размещение набора кнопок в вертикальном ряду

        inlineKeyboardMarkup.setKeyboard(rowsInline);
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        return sendPhoto;
    }



}
