package mario;

import java.awt.Image;
import java.awt.Toolkit;

public class ImageBox {
	
	public static Image block[] = new Image[5];
	public static Image[] mario_red = new Image[10]; 
	
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
		mario_red[0] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/left/stand(red).png");
		mario_red[1] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/right/standback(red).png");
		
		// Run1 왼쪽, 오른쪽
		mario_red[2] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/left/run(red).png");
		mario_red[3] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/right/runback(red).png");
		
		// Run2 왼쪽, 오른쪽
		mario_red[4] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/left/run2(red).png");
		mario_red[5] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/right/run2back(red).png");
		
		// Jump 왼쪽, 오른쪽
		mario_red[6] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/left/jump(red).png");
		mario_red[7] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/right/jumpback(red).png");
		
		// Push 왼쪽, 오른쪽
		mario_red[8] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/left/push(red).png");
		mario_red[9] = Toolkit.getDefaultToolkit().getImage("Image/Mario/red/right/pushback(red).png");
		
		/*
		 *[0] =	왼서
		 *[1] =	오서
		 *[2] =	왼달
		 *[3] =	오달
		 *[4] =	왼달2
		 *[5] =	오달2
		 *[6] = 왼점
         *[7] = 오점
		 *[8] = 왼밀
         *[9] = 오밀   
		 */ 
	}     
}
             
  
          