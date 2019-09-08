package com.travelBill.telegram;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.travelBill.telegram.ChatType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateRequestMapperTest {
    private Update update = mock(Update.class);
    private Message message = mock(Message.class);
    private CallbackQuery callbackQuery = mock(CallbackQuery.class);
    private Chat chat = mock(Chat.class);
    private UpdateRequestMapper updateRequestMapperSpy = spy(new UpdateRequestMapper());

    @Test
    void getChat_ShouldReturnMessageChat_WhenMessageIsPresent() {
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.getChat()).thenReturn(chat);

        assertEquals(chat, new UpdateRequestMapper().getChat(update));
    }

    @Test
    void getChat_ShouldReturnCallbackMessageChat_WhenMessageIdIsAbsentAndCallbackIsPresent() {
        when(update.hasMessage()).thenReturn(false);
        when(update.getCallbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.getMessage()).thenReturn(message);
        when(message.getChat()).thenReturn(chat);

        assertEquals(chat, new UpdateRequestMapper().getChat(update));
    }

    @Test
    void mapTo_shouldMapUpdateFieldsToRequest() {
        String textMessage = "Text message";
        String callbackQueryData = "callbackQueryData";
        int messageId = 321;

        when(update.hasMessage()).thenReturn(true);
        when(chat.getId()).thenReturn(123L);
        when(chat.isUserChat()).thenReturn(true);

        UpdateRequestMapper updateRequestMapper = new UpdateRequestMapper();
        UpdateRequestMapper updateRequestMapperSpy = spy(updateRequestMapper);

        doReturn(chat).when(updateRequestMapperSpy).getChat(update);
        doReturn(textMessage).when(updateRequestMapperSpy).getMessage(update);
        doReturn(callbackQueryData).when(updateRequestMapperSpy).getCallbackQueryData(update);
        doReturn(callbackQueryData).when(updateRequestMapperSpy).getCallbackQueryData(update);
        doReturn(messageId).when(updateRequestMapperSpy).getMessageId(update);
        doReturn(PRIVATE).when(updateRequestMapperSpy).getChatType(update);
        doReturn(true).when(updateRequestMapperSpy).isGroupChatCreated(update);

        Request request = updateRequestMapperSpy.mapTo(update);
        assertEquals(chat.getId(), request.chatId);
        assertEquals(textMessage, request.message);
        assertEquals(callbackQueryData, request.callbackQueryData);
        assertEquals(messageId, request.messageId);
        assertEquals(PRIVATE, request.chatType);
        assertTrue(request.isGroupChatCreated);
    }

    @Test
    void getMessage_shouldReturnTextMessage_WhenSimpleTextMessageReceived() {
        String expectedText = "123";
        when(update.hasMessage()).thenReturn(false);
        when(update.hasCallbackQuery()).thenReturn(true);
        when(update.getCallbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.getMessage()).thenReturn(message);
        when(message.getText()).thenReturn(expectedText);

        assertEquals(expectedText, new UpdateRequestMapper().getMessage(update));
    }

    @Test
    void getMessage_shouldReturnTextMessage_WhenCallbackQueryReceived() {
        when(message.hasText()).thenReturn(true);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        String textMessage = "123";
        when(message.getText()).thenReturn(textMessage);

        assertEquals(textMessage, new UpdateRequestMapper().getMessage(update));
    }

    @Test
    void getChatType_shouldReturnPrivateChatType_WhenChatIsUserChat() {
        when(update.hasMessage()).thenReturn(true);
        when(chat.isUserChat()).thenReturn(true);
        when(chat.isGroupChat()).thenReturn(false);
        when(chat.isChannelChat()).thenReturn(false);
        when(chat.isSuperGroupChat()).thenReturn(false);

        doReturn(chat).when(updateRequestMapperSpy).getChat(update);

        assertEquals(PRIVATE, updateRequestMapperSpy.getChatType(update));
    }

    @Test
    void getChatType_shouldReturnGroupChatType_WhenChatIsGroupChat() {
        when(update.hasMessage()).thenReturn(true);
        when(chat.isUserChat()).thenReturn(false);
        when(chat.isGroupChat()).thenReturn(true);
        when(chat.isChannelChat()).thenReturn(false);
        when(chat.isSuperGroupChat()).thenReturn(false);

        doReturn(chat).when(updateRequestMapperSpy).getChat(update);

        assertEquals(GROUP, updateRequestMapperSpy.getChatType(update));
    }


    @Test
    void getChatType_shouldReturnChanelChatType_WhenChatIsChanelChat() {
        when(update.hasMessage()).thenReturn(true);
        when(chat.isUserChat()).thenReturn(false);
        when(chat.isGroupChat()).thenReturn(false);
        when(chat.isChannelChat()).thenReturn(true);
        when(chat.isSuperGroupChat()).thenReturn(false);

        doReturn(chat).when(updateRequestMapperSpy).getChat(update);

        assertEquals(CHANEL, updateRequestMapperSpy.getChatType(update));
    }

    @Test
    void getChatType_shouldReturnSuperGroupChatType_WhenChatIsSuperGroupChat() {
        when(update.hasMessage()).thenReturn(true);
        when(chat.isUserChat()).thenReturn(false);
        when(chat.isGroupChat()).thenReturn(false);
        when(chat.isChannelChat()).thenReturn(false);
        when(chat.isSuperGroupChat()).thenReturn(true);

        doReturn(chat).when(updateRequestMapperSpy).getChat(update);

        assertEquals(SUPER_GROUP, updateRequestMapperSpy.getChatType(update));
    }

    @Test
    void getChatType_shouldThrowException_WhenChatIsUnknown() {
        when(update.hasMessage()).thenReturn(true);
        when(chat.isUserChat()).thenReturn(false);
        when(chat.isGroupChat()).thenReturn(false);
        when(chat.isChannelChat()).thenReturn(false);
        when(chat.isSuperGroupChat()).thenReturn(false);

        doReturn(chat).when(updateRequestMapperSpy).getChat(update);

        assertThrows(IllegalArgumentException.class, () -> updateRequestMapperSpy.getChatType(update));
    }


    @Test
    void getChatTitle_shouldReturnChatTitle() {
        String title = "title";
        doReturn(chat).when(updateRequestMapperSpy).getChat(update);
        when(chat.getTitle()).thenReturn(title);
        assertEquals(title, updateRequestMapperSpy.getChatTitle(update));
    }

    @Test
    void getCallbackQueryData_shouldReturnCallbackQueryData_whenCallbackProvided() {
        String data = "data";
        when(update.hasCallbackQuery()).thenReturn(true);
        when(update.getCallbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.getData()).thenReturn(data);

        assertEquals(data, updateRequestMapperSpy.getCallbackQueryData(update));
    }

    @Test
    void getCallbackQueryData_shouldReturnNull_whenCallbackIsNotProvided() {
    }

    @Test
    void isGroupChatCreated_shouldReturnTrue_WhenGroupChatCreated() {
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.getGroupchatCreated()).thenReturn(true);
        when(message.getSuperGroupCreated()).thenReturn(false);

        assertTrue(new UpdateRequestMapper().isGroupChatCreated(update));
    }

    @Test
    void isGroupChatCreated_shouldReturnTrue_WhenSuperGroupChatCreated() {
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.getGroupchatCreated()).thenReturn(false);
        when(message.getSuperGroupCreated()).thenReturn(true);

        assertTrue(new UpdateRequestMapper().isGroupChatCreated(update));
    }

    @Test
    void getMessageId_shouldReturnMessageIdFromCallbackQuery_WhenUpdateWithCallbackQueryProvided() {
        when(update.hasMessage()).thenReturn(false);
        when(update.hasCallbackQuery()).thenReturn(true);
        when(update.getCallbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.getMessage()).thenReturn(message);
        when(message.getMessageId()).thenReturn(123);

        assertEquals(123, new UpdateRequestMapper().getMessageId(update));
    }

    @Test
    void getMessageId_shouldReturnMessageIdFromMessage_WhenUpdateWithTextMessageProvided() {
        when(update.hasMessage()).thenReturn(true);
        when(update.hasCallbackQuery()).thenReturn(false);
        when(update.getMessage()).thenReturn(message);
        when(message.getMessageId()).thenReturn(321);

        assertEquals(321, new UpdateRequestMapper().getMessageId(update));
    }

    @Test
    void getMessageId_shouldThrowException_WhenMessageAndCallbackQueryNotProvided() {
        when(update.hasMessage()).thenReturn(false);
        when(update.hasCallbackQuery()).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> updateRequestMapperSpy.getMessageId(update));
    }

}