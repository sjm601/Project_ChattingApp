package ezen.chat.server;

import java.io.IOException;

/**
 * 서버 실행 어플리케이션
 * @author 박상훈
 */
public class EzenTalkServer {

	public static void main(String[] args) {
		ChatServer chatServer = new ChatServer();
		try {
			chatServer.startup();
		} catch (IOException e) {
			System.out.println("ChatServer 실행 중 예외 발생");
			System.out.println("-" + e.getMessage());
		}
	}

}
