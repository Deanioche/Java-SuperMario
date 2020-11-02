package mario;

import java.awt.Image;
import java.awt.Toolkit;

public class ImageBox {
	
	public static Image block[] = new Image[5];
	public static Image[][] mario_red = new Image[5][2]; //[모션][ 0 = 왼, 1 = 오]
	
	public ImageBox() {
		
		//블록
		block[0] = Toolkit.getDefaultToolkit().getImage("Image/Blocks/blocks_Brick.png");
		block[1] = Toolkit.getDefaultToolkit().getImage("Image/Blocks/blocks_Damaged.png");
		block[2] = Toolkit.getDefaultToolkit().getImage("Image/Blocks/blocks_Piramid.png");
		block[3] = Toolkit.getDefaultToolkit().getImage("Image/Blocks/blocks_Plate.png");
		block[4] = Toolkit.getDefaultToolkit().getImage("Image/Blocks/blocks_Question.png");
		
		/*
		 * 0 = 벽돌
		 * 1 = 깨짐
		 * 2 = 피라미드
		 * 3 = 철판
		 * 4 = 물음표
		 */
		
		//마리오_레드  [모션][ 0 = 왼, 1 = 오]
		// Stand 왼쪽, 오른쪽
		mario_red[0][0] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/left/stand(red).png");
		mario_red[0][1] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/right/standback(red).png");
		
		// Run1 왼쪽, 오른쪽
		mario_red[1][0] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/left/run(red).png");
		mario_red[1][1] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/right/runback(red).png");
		
		// Run2 왼쪽, 오른쪽
		mario_red[2][0] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/left/run2(red).png");
		mario_red[2][1] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/right/run2back(red).png");
		
		// Jump 왼쪽, 오른쪽
		mario_red[3][0] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/left/jump(red).png");
		mario_red[3][1] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/right/jumpback(red).png");
		
		// Push 왼쪽, 오른쪽
		mario_red[4][0] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/left/push(red).png");
		mario_red[4][1] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/right/pushback(red).png");
		
		/*
		 * [0][0] =	왼서
		 * [1][0] =	왼달
		 * [2][0] =	왼달2
		 * [3][0] =	왼점
		 * [4][0] =	왼밀
		 * 
		 * [0][1] =	오서
		 * [1][1] =	오달
		 * [2][1] =	오달2
		 * [3][1] =	오점
		 * [4][1] =	오밀
		 */
	}
}
