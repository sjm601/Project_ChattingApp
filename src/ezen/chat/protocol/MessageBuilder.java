package ezen.chat.protocol;

/**
 * 채팅메시지 빌더 유틸리티
 * @author 박상훈
 */
public class MessageBuilder {
	
	public static final String delimeter = "★";
	
	public static String build(MessageType messageType, String... tokens) {
		StringBuilder sb = new StringBuilder();
		sb.append(messageType);
		for (String token : tokens) {
			sb.append(delimeter);
			sb.append(token);
		}
		return sb.toString();
	}
	
	public static String[] parse(String  message) {
		String[] tokens = message.split(delimeter);
		return tokens;
	}
	
}