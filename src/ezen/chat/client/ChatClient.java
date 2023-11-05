package ezen.chat.client;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import ezen.chat.protocol.MessageBuilder;
import ezen.chat.protocol.MessageType;

/**
 * TCP/IP 기반의 ChatClient
 * @author 박상훈
 */
public class ChatClient {
	
	private static final String SERVER_IP = "localhost";
	private static final int SERVER_PORT = 7777;
	
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	
	ChatFrame chatFrame;
	
	public ChatClient(ChatFrame chatFrame) {
		this.chatFrame = chatFrame;
	}
	
	// 서버 연결
	public void connectServer() throws IOException {
		socket = new Socket(SERVER_IP, SERVER_PORT);
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
	}
	
	// 서버 연결 종료
	public void disConnectServer() throws IOException {
		if(socket != null) {
			socket.close();
		}
	}
	
	// 메시지 전송
	public void sendMessage(String message) throws IOException {
		out.writeUTF(message);
	}
	
	// 메시지 수신
	public void receiveMessage() {
		// 서버로부터 전송되는 메시지를 실시간 수신하기 위해 스레드 생성 및 시작
		Thread thread = new Thread() {
			public void run() {
				try {
					while (true) {
						String serverMessage = in.readUTF();
						String[] tokens = MessageBuilder.parse(serverMessage);

						MessageType messageType = MessageType.valueOf(tokens[0]);
						String senderNickName = tokens[1];
						
						// 클라이언트 전송 메시지 종류에 따른 처리
						switch (messageType) {
							case CONNECT:
								chatFrame.appendMessage("@@@@ "+senderNickName+"님이 연결하였습니다 @@@@");
								break;
							case USER_LIST:
								String nickNameList = tokens[2];
								chatFrame.clearNicknameList();
								
								nickNameList = nickNameList.replaceAll(",", "\n");
								chatFrame.appendNicknameList(nickNameList);
								chatFrame.appendNicknameChoice(nickNameList);
								break;
							case CHAT_MESSAGE:
								String chatMessge = tokens[2];
								chatFrame.appendMessage("["+senderNickName+"] : " + chatMessge);
								break;
							case DM_MESSAGE:
								String receiveNickname = tokens[2];
								String dm = tokens[3];
								chatFrame.appendMessage("♥♥♥ "+senderNickName+"님이 당신에게 보내신 DM입니다 ♥♥♥");
								chatFrame.appendMessage(" -> " + dm);
								break;
							case DIS_CONNECT:
								chatFrame.appendMessage("#### "+senderNickName+"님이 연결 해제하였습니다 ####");
								break;
						}
					}
				} catch (IOException e) {} 
				finally {}
			}
		};
		thread.start();
	}
}