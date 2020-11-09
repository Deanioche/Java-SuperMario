package mario;

import java.awt.Image;
import java.awt.Toolkit;

public class ImageBox {
	
	public static Image block[] = new Image[5];
	
	public static Image[] mario_red = new Image[10]; 
    public static Image[] mario_blue = new Image[10];
    public static Image[] mario_yellow = new Image[10];
    public static Image[] mario_green = new Image[10];
    public static Image[] mario_purple = new Image[10];
    
    public static Image dooli;
    
    public static Image background;

	public ImageBox() {
		
		//블록
		block[0] = Toolkit.getDefaultToolkit().getImage("Image/Blocks/blocks_Brick.png");
		block[1] = Toolkit.getDefaultToolkit().getImage("Image/Blocks/blocks_Damaged.png");
		block[2] = Toolkit.getDefaultToolkit().getImage("Image/Blocks/blocks_Piramid.png");
		block[3] = Toolkit.getDefaultToolkit().getImage("Image/Blocks/blocks_Plate.png");
		block[4] = Toolkit.getDefaultToolkit().getImage("Image/Blocks/blocks_Question.png");
		
		dooli = Toolkit.getDefaultToolkit().getImage("Image/dooli.png");
		
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
		
		
	     
	      //마리오_블루  [모션][ 0 = 왼, 1 = 오]
	      // Stand 왼쪽, 오른쪽
	      mario_blue[0] = Toolkit.getDefaultToolkit().getImage("Image/Mario/blue/left/stand(blue).png");
	      mario_blue[1] = Toolkit.getDefaultToolkit().getImage("Image/Mario/blue/right/standback(blue).png");
	      
	      // Run1 왼쪽, 오른쪽
	      mario_blue[2] = Toolkit.getDefaultToolkit().getImage("Image/Mario/blue/left/run(blue).png");
	      mario_blue[3] = Toolkit.getDefaultToolkit().getImage("Image/Mario/blue/right/runback(blue).png");
	      
	      // Run2 왼쪽, 오른쪽
	      mario_blue[4] = Toolkit.getDefaultToolkit().getImage("Image/Mario/blue/left/run2(blue).png");
	      mario_blue[5] = Toolkit.getDefaultToolkit().getImage("Image/Mario/blue/right/run2back(blue).png");
	      
	      // Jump 왼쪽, 오른쪽
	      mario_blue[6] = Toolkit.getDefaultToolkit().getImage("Image/Mario/blue/left/jump(blue).png");
	      mario_blue[7] = Toolkit.getDefaultToolkit().getImage("Image/Mario/blue/right/jumpback(blue).png");
	      
	      // Push 왼쪽, 오른쪽
	      mario_blue[8] = Toolkit.getDefaultToolkit().getImage("Image/Mario/blue/left/push(blue).png");
	      mario_blue[9] = Toolkit.getDefaultToolkit().getImage("Image/Mario/blue/right/pushback(blue).png");
	      
	      
	      
	      //마리오_옐로우  [모션][ 0 = 왼, 1 = 오]
	      // Stand 왼쪽, 오른쪽
	      mario_yellow[0] = Toolkit.getDefaultToolkit().getImage("Image/Mario/yellow/left/stand(yellow).png");
	      mario_yellow[1] = Toolkit.getDefaultToolkit().getImage("Image/Mario/yellow/right/standback(yellow).png");
	      
	      // Run1 왼쪽, 오른쪽
	      mario_yellow[2] = Toolkit.getDefaultToolkit().getImage("Image/Mario/yellow/left/run(yellow).png");
	      mario_yellow[3] = Toolkit.getDefaultToolkit().getImage("Image/Mario/yellow/right/runback(yellow).png");
	      
	      // Run2 왼쪽, 오른쪽
	      mario_yellow[4] = Toolkit.getDefaultToolkit().getImage("Image/Mario/yellow/left/run2(yellow).png");
	      mario_yellow[5] = Toolkit.getDefaultToolkit().getImage("Image/Mario/yellow/right/run2back(yellow).png");
	      
	      // Jump 왼쪽, 오른쪽
	      mario_yellow[6] = Toolkit.getDefaultToolkit().getImage("Image/Mario/yellow/left/jump(yellow).png");
	      mario_yellow[7] = Toolkit.getDefaultToolkit().getImage("Image/Mario/yellow/right/jumpback(yellow).png");
	      
	      // Push 왼쪽, 오른쪽
	      mario_yellow[8] = Toolkit.getDefaultToolkit().getImage("Image/Mario/yellow/left/push(yellow).png");
	      mario_yellow[9] = Toolkit.getDefaultToolkit().getImage("Image/Mario/yellow/right/pushback(yellow).png");

	      
	      
	      //마리오_그린  [모션][ 0 = 왼, 1 = 오]
	      // Stand 왼쪽, 오른쪽
	      mario_green[0] = Toolkit.getDefaultToolkit().getImage("Image/Mario/green/left/stand(green).png");
	      mario_green[1] = Toolkit.getDefaultToolkit().getImage("Image/Mario/green/right/standback(green).png");
	      
	      // Run1 왼쪽, 오른쪽
	      mario_green[2] = Toolkit.getDefaultToolkit().getImage("Image/Mario/green/left/run(green).png");
	      mario_green[3] = Toolkit.getDefaultToolkit().getImage("Image/Mario/green/right/runback(green).png");
	      
	      // Run2 왼쪽, 오른쪽
	      mario_green[4] = Toolkit.getDefaultToolkit().getImage("Image/Mario/green/left/run2(green).png");
	      mario_green[5] = Toolkit.getDefaultToolkit().getImage("Image/Mario/green/right/run2back(green).png");
	      
	      // Jump 왼쪽, 오른쪽
	      mario_green[6] = Toolkit.getDefaultToolkit().getImage("Image/Mario/green/left/jump(green).png");
	      mario_green[7] = Toolkit.getDefaultToolkit().getImage("Image/Mario/green/right/jumpback(green).png");
	      
	      // Push 왼쪽, 오른쪽
	      mario_green[8] = Toolkit.getDefaultToolkit().getImage("Image/Mario/green/left/push(green).png");
	      mario_green[9] = Toolkit.getDefaultToolkit().getImage("Image/Mario/green/right/pushback(green).png");

	      
	      
	      //마리오_퍼플  [모션][ 0 = 왼, 1 = 오]
	      // Stand 왼쪽, 오른쪽
	      mario_purple[0] = Toolkit.getDefaultToolkit().getImage("Image/Mario/purple/left/stand(purple).png");
	      mario_purple[1] = Toolkit.getDefaultToolkit().getImage("Image/Mario/purple/right/standback(purple).png");
	      
	      // Run1 왼쪽, 오른쪽
	      mario_purple[2] = Toolkit.getDefaultToolkit().getImage("Image/Mario/purple/left/run(purple).png");
	      mario_purple[3] = Toolkit.getDefaultToolkit().getImage("Image/Mario/purple/right/runback(purple).png");
	      
	      // Run2 왼쪽, 오른쪽
	      mario_purple[4] = Toolkit.getDefaultToolkit().getImage("Image/Mario/purple/left/run2(purple).png");
	      mario_purple[5] = Toolkit.getDefaultToolkit().getImage("Image/Mario/purple/right/run2back(purple).png");
	      
	      // Jump 왼쪽, 오른쪽
	      mario_purple[6] = Toolkit.getDefaultToolkit().getImage("Image/Mario/purple/left/jump(purple).png");
	      mario_purple[7] = Toolkit.getDefaultToolkit().getImage("Image/Mario/purple/right/jumpback(purple).png");
	      
	      // Push 왼쪽, 오른쪽
	      mario_purple[8] = Toolkit.getDefaultToolkit().getImage("Image/Mario/purple/left/push(purple).png");
	      mario_purple[9] = Toolkit.getDefaultToolkit().getImage("Image/Mario/purple/right/pushback(purple).png");

		
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
	      
	      
	      // 배경화면
	      
	      
	}     
}
             
  
          