package Mario;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MarioDBTable extends JFrame implements ActionListener{
 
	//1.필드선언
	private JButton updateBtn, deleteBtn, exitBtn;
	private Vector<String> vector;
	private DefaultTableModel model; //테이블생성용
	private JTable table;
	//private DefaultListModel<MarioDTO> listModel; //DAO데이터 수령용
	
	
	public MarioDBTable() {
		//★★★★★5.DB정보를 List에 담
		MarioDAO dao = MarioDAO.getInstance(); //DAO객체 생성
		List<MarioDTO> dtoList = dao.getMarioList(); 
		//DB에 있는 쿼리를 객체로 받아 List(dtolist)에 추가, 그 내용을 여기서 dtoList변수에 다시 받음
		for(MarioDTO dto : dtoList) {
			//테이블을 만들때는 vector에 한번 저장하고 다시 model에 객체 추가 -> 여기서 vector에 저장?
			//객체의 내용중에 String타입이 아닌것도 포함 -> +"" 사용?
			//defaultListModel과 defaultTableModel의 메소드가 다름 -> 맞는 메소드가 무엇인지 확인
			//MarioDTO에 선언된 필드와 여기서 저장된 필드의 갯수에 차이가 있음, 영향이 있는지?
			Vector<String> v = new Vector<String>();
			v.add(dto.getClientAccount());
			
			model.addRow(v);
		}
		
		
		//4.테이블 필드명 생성
		vector = new Vector<String>();
		vector.add("일련번호");
		vector.add("아이디");
		vector.add("비밀번호"); //★★★★★비밀번호 보호 필요??
		vector.add("이름");
		vector.add("나이");
		vector.add("닉네임");
		vector.add("성별");
		vector.add("개인정보보호");
		vector.add("점수");
		vector.add("소요시간");
		vector.add("순위");
		
		//5.테이블 생성
		model = new DefaultTableModel(vector,0) {
			public boolean isCellEditable(int row, int column) {
				return (column != 0) ? true : false;
			}	
		};
		table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
		
		
		//3.컴포넌트 생성
		updateBtn = new JButton("수정");
		deleteBtn = new JButton("삭제");
		exitBtn = new JButton("종료");
		
		JPanel p1 = new JPanel();
		p1.add(updateBtn);
		p1.add(deleteBtn);
		p1.add(exitBtn);
		
		Container container = this.getContentPane();
		container.add("South",p1);
		container.add("Center",scroll);
		
		//2.프레임 생성
		setBounds(700, 300, 1000, 300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void event() {
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		exitBtn.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == updateBtn) {
			updateArticle();
		}else if(e.getSource() == deleteBtn) {
			deleteArticle();
		}else if(e.getSource() == exitBtn) {
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
	}
	
	private void updateArticle() {
		//int seq = 선택한 테이블 레코드
		
		//DB
		MarioDAO dao = MarioDAO.getInstance();
		//dao.updateArticle(seq);
		
		JOptionPane.showMessageDialog(null, "회원 정보가 수정되었습니다", "회원정보 수정", JOptionPane.INFORMATION_MESSAGE);
		//테이블 새로 출력 : tableModel.set
	}
	
	private void deleteArticle() {
		//int seq = 선택한 테이블 레코드
		
		//DB
		MarioDAO dao = MarioDAO.getInstance();
		//dao.deleteArticle(seq);
		
		JOptionPane.showMessageDialog(null, "회원 정보가 삭제되었습니다", "회원정보 삭제", JOptionPane.INFORMATION_MESSAGE);
		//테이블 새로 출력 : tableModel.removeRow(row);
	}

	
	

	public static void main(String[] args) {
		new MarioDBTable().event();

	}


}
