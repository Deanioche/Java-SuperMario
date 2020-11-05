package mario.Frame;

import java.awt.BorderLayout;
 import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import mario.ImageBox;

public class MarioClient extends JFrame implements ActionListener ,Runnable{
	
	private MarioCanvas marioCanvas;
	private String nickName;
	private JTextArea textArea;
	private JTextField textField;
	private JLabel minute, second,time;
	
	private JTable scoreTable;
	public DefaultTableModel model;
	public JButton btn_send, btn_start;
	private Image img;
	private Thread t;
	
	public MarioClient() {
		super("Mario");
		new ImageBox();
		
		JScrollPane scroll_chat = new JScrollPane(textArea);
		marioCanvas = new MarioCanvas(MarioClient.this);
		
		
		//라벨생성 위두개 분 초 
		second = new JLabel("0분");
		minute = new JLabel("0초");
		
		//텍스트필드
		textField = new JTextField("");
		
		//버튼생성
		btn_start =  new JButton("게임생성");
		btn_send = new JButton("보내기");
		
		//JButton btn_dd = new JButton("dsf");
		//btn_dd.setBounds(200,400,300,200);
		
		//등장인물 정보 생성
		Vector<String> vector = new Vector<String>();
		vector.add("플레이어");   //번호 이름색상 
		vector.add("열쇠갯수");
		
		
		
		//모델생성
		model = new DefaultTableModel(vector,0); //0번으로하겠다  이게기준인것이다 
		
		//테이블에 모델넣음
		scoreTable  = new  JTable(model);
		
		//보이지않게 뭔지모름
		scoreTable.setEnabled(false);
		
		//스크롤안에 테이블넣기 
		JScrollPane jsc = new JScrollPane(scoreTable);
		
		
		//시간초생성 
		
		time = new JLabel("시간");
		
		//폰트
		minute.setFont(new Font("MD개성체",Font.BOLD,20));
		second.setFont(new Font("MD개성체",Font.BOLD,20));
		time.setFont(new Font("MD개성체",Font.BOLD,20));
	
		//버튼생성
		btn_start = new JButton("시작");

		
		//채팅창스크롤 위치 ( 패널로 위치를잡고 센터 캠퍼스넣음)
		jsc.setPreferredSize(new Dimension(200,200));
		minute.setBounds(200,400,100,200);
		second.setBounds(250,400,100,200);
		
		//스크롤창 보이기 
		scroll_chat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//스크롤항상열려있기
	
		//채팅창하단 패널
		JPanel p_s_text = new JPanel(new BorderLayout());
		p_s_text.add("Center", textField);
		p_s_text.add("East", btn_send);
		p_s_text.setBounds(200, 400, 400, 300);
		
		//채팅창  패널
		JPanel p_text = new JPanel(new BorderLayout());
		p_text.add("South",p_s_text);
		p_text.add("Center",scroll_chat);
		
		
		//점수 패널
		JPanel p_score = new JPanel(new BorderLayout());
		p_score.add("Center",p_text);
		p_score.add("South",btn_start);
		p_score.add("North",jsc); //스크롤넣음 
		
		//채팅창 2 패널
		JPanel p_time= new JPanel(new FlowLayout());
		p_time.add(time);
		p_time.add(minute);
		p_time.add(second);
		p_time.setBackground(Color.ORANGE);
		
		
		
		//컨테이너 생성
//		Container c = getContentPane(); 
		//c.add(btn_dd);
		add("East",p_score);
		add("Center",marioCanvas);
		add("North",p_time);
		
		//c.add("West",p_text);
		
	
		
	
		
		
		textArea = new JTextArea();
		textArea.setEditable(false); //사용자가 텍스트를 입력할 수 있는지 없는지를 설정하고 반환한다.
		
		

	
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		  System.out.println("해상도 : " + res.width + " x " + res.height);
		  
		setSize(res.width,res.height);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLayout(null);
		
		
		
		//이벤트
		 btn_start.addActionListener(this);
		 btn_send.addActionListener(this);
		 
	
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					int result = JOptionPane.showConfirmDialog(MarioClient.this, "게임을 종료하시겠습니까?", "종료창",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						dispose();	
						
						
					}else if (result == JOptionPane.NO_OPTION) {
						
					}
				}
			});
		 
		 
		 
	}
	
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btn_start) {
				t = new Thread(this);
				t.start();
			}else {
					t = null; //종료시점 깃밧좌표를 만졌을떄 캐릭터가 
				}
			
		
	}
	@Override
	public void run() {
		 //   스레드시작
		
			
		for(int i =1; i<30; i++) {
			minute.setText(i+""+"분");
			for(int j =1;j<60; j++ ) {
				second.setText(j+""+"초");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
					}
				}
			}
		}
	
	public static void main(String[] args) {
		new MarioClient();
	}
}
