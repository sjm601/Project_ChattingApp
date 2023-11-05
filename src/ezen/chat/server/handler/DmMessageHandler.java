package ezen.chat.server.handler;

import java.io.IOException;

import ezen.chat.protocol.MessageBuilder;
import ezen.chat.server.ChatHandler;
import ezen.chat.server.ChatServer;

/**
 * DM 메시지에 대한 응답 처리
 * @author 박상훈
 *
 */
public class DmMessageHandler implements MessageListener{

	@Override
	public void doResponse(ChatServer chatServer, ChatHandler chatHandler, String clientMessage) throws IOException {
		String[] tokens = MessageBuilder.parse(clientMessage);
		String receiveName = tokens[2];
		System.out.println(receiveName);
		ChatHandler receiveChatHandler = chatServer.findClient(receiveName);
		System.out.println(receiveChatHandler);
		if(receiveChatHandler != null) {
			receiveChatHandler.sendMessage(clientMessage);
		}
	}
	

}
