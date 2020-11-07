package mario.Frame;

import java.io.Serializable;
import java.util.ArrayList;

import mario.Server.Protocols;
import mario.dto.MarioDTO;

public class ListDTO implements Serializable{
	
	private Protocols protocol;
	private ArrayList<MarioDTO> list_PlayerInfo;
	
	public ListDTO(ArrayList<MarioDTO> list, Protocols protocol) {
		
		this.list_PlayerInfo = list;
		this.protocol = protocol;
	}

	public Protocols getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocols protocol) {
		this.protocol = protocol;
	}

	public ArrayList<MarioDTO> getList_PlayerInfo() {
		return list_PlayerInfo;
	}

	public void setList_PlayerInfo(ArrayList<MarioDTO> list_PlayerInfo) {
		this.list_PlayerInfo = list_PlayerInfo;
	}

	@Override
	public String toString() {
		return "ListDTO [protocol=" + protocol + ", list_PlayerInfo=" + list_PlayerInfo + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
