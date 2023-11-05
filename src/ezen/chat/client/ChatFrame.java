package ezen.chat.client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import ezen.chat.protocol.MessageBuilder;
import ezen.chat.protocol.MessageType;

/**
 * 채팅창 프레임 구현
 * @author 박상훈
 */
public class ChatFrame extends Frame {
	
	Label nicknameL;
	TextField nicknameTF, inputTF;
	Button loginB, sendB;
	TextArea messageTA, nicknameList;
	
	Choice userChoice;
	
	Panel northP, southP;
	
	ChatClient chatClient;
	String nickName;
	
	public ChatFrame() {
		this("No-Title");
	}
	
	public ChatFrame(String title) {
		super(title);
		// 닉네임& 연결버튼
		nicknameL = new Label("닉네임");
		nicknameTF = new TextField();
		loginB = new Button("연 결");
		//채팅 입력
		inputTF = new TextField();
		// 채팅 전송
		sendB = new Button("보내기");
		// 채팅창
		messageTA = new TextArea();
		// 채팅방 참가자 리스트
		nicknameList = new TextArea(10, 10);
		
		userChoice = new Choice();
		userChoice.addItem("전체에게");
		
		northP = new Panel(new BorderLayout(5, 5));
		southP = new Panel(new BorderLayout(5, 5));
	}
	
	/**
	 * 컴포넌트 배치
	 */
	public void init() {
//		Frame의 디폴트레이아웃매니저 : BorderLayout
		northP.add(nicknameL, BorderLayout.WEST);
		northP.add(nicknameTF, BorderLayout.CENTER);
		northP.add(loginB, BorderLayout.EAST);
		
		southP.add(userChoice, BorderLayout.WEST);
		southP.add(inputTF, BorderLayout.CENTER);
		southP.add(sendB, BorderLayout.EAST);
		
		add(northP, BorderLayout.NORTH);
		add(messageTA, BorderLayout.CENTER);
		add(nicknameList, BorderLayout.EAST);
		add(southP, BorderLayout.SOUTH);
	}
	
	/**
	 *  닉네임 작성 후 서버 연결
	 */
	private void connect() {
		nickName = nicknameTF.getText();
		if(!Validator.hasText(nickName)) {
			JOptionPane.showMessageDialog(this, "닉네임을 입력하세요");
			return;
		}
		chatClient = new ChatClient(this);
		try {
			chatClient.connectServer();
			setEnable(false);
			chatClient.sendMessage(MessageBuilder.build(MessageType.CONNECT, nickName));
			chatClient.receiveMessage();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "ChatServer를 연결할 수 없습니다..");
		}
	}
	
	/**
	 * 닉네임 입력시 비활성화
	 * @param enable 활성화 여부
	 */
	private void setEnable(boolean enable) {
		nicknameTF.setEnabled(enable);
		loginB.setEnabled(enable);
	}
	
	/**
	 * 메세지 출력 메소드
	 * @param message 출력 메세지
	 */
	public void appendMessage(String message) {
		messageTA.append(message + "\n");
	}
	
	/**
	 * 메시지창 초기화
	 */
	public void clearMessage() {
		messageTA.setText("");
	}
	
	/**
	 * 채팅 참여자 리스트에 이름 추가
	 * @param nickNameList
	 */
	public void appendNicknameList(String nickNameList) {
		nicknameList.append(nickNameList );
	}
	
	/**
	 *  채팅 참여자 리스트 초기화
	 */
	public void clearNicknameList() {
		nicknameList.setText("");
	}
	
	
	public void appendNicknameChoice(String nickNameList) {
		userChoice.removeAll();
		userChoice.add("전체에게");
		String[] nickNames = nickNameList.split("\n");
		for (String nickName : nickNames) {
			userChoice.add(nickName);
		}
	}
	
	/**
	 * 서버로 TF에 작성한 메세지를 보냄 (공백 시  예외처리 유효성 검사 완)
	 */
	private void sendChatMessage() {
		String message = inputTF.getText();
		if(Validator.hasText(message)) {
			try {
				// DM 전송에 따른 분기
				if(userChoice.getSelectedIndex() != 0) {
					String receiveNickname = userChoice.getSelectedItem();
					chatClient.sendMessage(MessageBuilder.build(MessageType.DM_MESSAGE, nickName,  receiveNickname, message));
				}else {
					chatClient.sendMessage(MessageBuilder.build(MessageType.CHAT_MESSAGE, nickName, message));
				}
				inputTF.setText("");
			} catch (IOException e) {}
		}
	}
	
	/**
	 * 서버로 연결해제 메시지 전달
	 */
	private void disConnect() {
		try {
			chatClient.sendMessage(MessageBuilder.build(MessageType.DIS_CONNECT, nickName));
			exit();
		} catch (IOException e) {
			
		}
	}
	
	/**
	 * 프레임 닫고 종료
	 */
	private void exit() {
		setVisible(false);
		dispose();
		System.exit(0);
	}
	
	public void addEventListener() {
		/**
		 * 종료 처리 이벤트
		 */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				disConnect();
			}
		});
		
		/**
		 * 연결 처리 이벤트
		 */
		loginB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		
		nicknameTF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		
		/**
		 * 메시지 입력 이벤트 
		 */
		inputTF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendChatMessage();
			}
		});
		
		/**
		 * 메시지 입력 엔터키 이벤트
		 */
		sendB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendChatMessage();
			}
		});
		
		/**
		 *  닉네임 선택 이벤트
		 */
		userChoice.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED	) {
					String selectedUser = userChoice.getSelectedItem();
				}
			}
		});
	}
}