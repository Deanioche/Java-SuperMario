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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;

public class MarioDBTable extends JFrame implements ActionListener, TableColumnModelListener {
 
	//1.필드선언
	private JButton updateBtn, deleteBtn, exitBtn;
	private Vector<String> vector;
	private DefaultTableModel model; //테이블생성용
	private JTable table;
	//private DefaultListModel<MarioDTO> listModel; //DAO데이터 수령용
	
	
	public MarioDBTable() {
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
				return (column != 0 && column != 1) ? true : false;
			}	
		};
		table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
		
		//6.DB정보를 List에 담기
		MarioDAO dao = MarioDAO.getInstance(); //DAO객체 생성
		List<MarioDTO> dtoList = dao.getMarioList(); 
		//DB에 있는 쿼리를 객체로 받아 List(dtolist)에 추가, 그 내용을 여기서 dtoList변수에 다시 받음
		for(MarioDTO dto : dtoList) {
			Vector<String> v = new Vector<String>();
			v.add(dto.getSeq()+"");
			v.add(dto.getClientAccount());
			v.add(dto.getPassword());
			v.add(dto.getRealName());
			v.add(dto.getAge() + "");
			v.add(dto.getNickname());
			v.add(dto.getGender() + "");
			v.add(dto.getInfoAgree() + "");
			v.add(dto.getScore() + "");
			v.add(dto.getGoalTime());
			v.add(dto.getPlayerRank() + "");
			
			model.addRow(v);	
		}
	
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
	
	
	//★★★★★★★★미완성
	private void updateArticle() {
		
		int seq = (int)table.getValueAt(table.getSelectedRow(), 0);
		System.out.println("seq " + seq);
		
		
		
		//DB
		
		
		JOptionPane.showMessageDialog(null, "회원 정보가 수정되었습니다", "회원정보 수정", JOptionPane.INFORMATION_MESSAGE);
		//테이블 새로 출력 : tableModel.set
	}
	
	//★★★★★★★★미완성
	private void deleteArticle() {
		//int seq = 선택한 테이블 레코드
		
		//DB
		
		
		JOptionPane.showMessageDialog(null, "회원 정보가 삭제되었습니다", "회원정보 삭제", JOptionPane.INFORMATION_MESSAGE);
		//테이블 새로 출력 : tableModel.removeRow(row);
	}

	
	
	//★★★★★★★★미완성
	@Override
	public void columnAdded(TableColumnModelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void columnRemoved(TableColumnModelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void columnMoved(TableColumnModelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void columnMarginChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void columnSelectionChanged(ListSelectionEvent e) {
		System.out.println("seq = " + table.getSelectedColumn());
		
	}


	public static void main(String[] args) {
		new MarioDBTable().event();
		
	}
}
