package ezen.chat.client;

/**
 * 채팅 앱 실행 애플리케이션
 * @author 박상훈
 */
public class ArsenalTalkApp {

	public static void main(String[] args) {
		ChatFrame chatFrame = new ChatFrame("::: 재밌는 대화 나누세요 :::");
		chatFrame.setSize(400, 500);
		chatFrame.init();
		chatFrame.addEventListener();
		chatFrame.setVisible(true);
	}

}
