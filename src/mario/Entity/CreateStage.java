package mario.Entity;

import java.awt.Graphics;
import java.util.List;

public class CreateStage {
	
	Graphics bufferGraphic;
	List<Block> list_Block;
	
	public CreateStage(Graphics bufferGraphic, List<Block> list_Block) {
		this.bufferGraphic = bufferGraphic;
		this.list_Block = list_Block;
		
		render(bufferGraphic);
		
	}
	
	// 스테이지 그리기
	
	
	public void render(Graphics bufferGraphic) {
		
	     /* 맵 끝 테두리 */
	      // 좌표가 ~에서 ~까지 50씩증가한다.

	      // 가로 튀어나온부분 -100 ,100
	      for (int i = 0; i <= 5100; i += 50) {
	         if(i==750||i==850) continue; // 바닥공간 낙사 
	         list_Block.add(new Block(0, i, 5050));
	      } // i좌표 //y좌표

	      // 세로 왼쪽
	      for (int i = 0; i <= 5000; i += 50) {

	         list_Block.add(new Block(0, 0, i));
	      }
	      // 세로오른쪽
	      for (int i = 0; i <= 4950; i += 50) {

	         list_Block.add(new Block(0, 5000, i));

	      }
	      // 가운데선
	      for (int i = 50; i <= 5000; i += 50) {
	         list_Block.add(new Block(0, 2500, i));
	      }
	      
	      //상단가로
	      for (int i = 0; i <= 5000; i += 50) {
	         if(i==200||i==250) continue;
	         list_Block.add(new Block(0, i, 0));
	      }
	      
	      /* 맵 끝 테두리 */


	   // 바닥에서 가로벽돌 감싼곳
	   for (int i = 4600; i <= 4600; i += 50) { 
	         for (int y = 0; y <= 2400; y += 50) {
	            list_Block.add(new Block(0, y, i));
	         }
	      }

	   // 시작라인시작
	      int blockdelete =0;
	      int blockadd = 0;
	      
	      // 시작라인 피라미드 형
	      for (int i = 5000; i >= 4750; i -= 50) {
	         blockdelete += 50;
	         for (int y = 350 + blockdelete; y <= 700; y += 50) {
	            list_Block.add(new Block(0, y, i));
	         }
	      }
	      
	      blockdelete =0;
	      // 시작라인 반대 피라미드 형
	      for (int i = 5000; i >= 4750; i -= 50) {
	         blockdelete -= 50;
	         for (int y = 1250 + blockdelete; y >= 900; y -= 50) {
	            list_Block.add(new Block(0, y, i));
	         }
	      }

	      // 시작라인 계단일자형 블록
	      blockdelete =0;
	      for (int i = 4950; i >= 4650; i -= 100) {
	         blockdelete += 50;
	         for (int y = 1300 + blockdelete; y <= 1950; y += 50) {
	            list_Block.add(new Block(0, y, i));
	         }
	      }
	      // 중간라인 계단식 블록
	      blockdelete =0;
	      for (int i = 5000; i >= 4750; i -= 50) {
	         blockdelete += 50;
	         for (int y = 2000 + blockdelete; y <= 2500; y += 50) {
	            list_Block.add(new Block(0, y, i));
	         }
	      }

	         // 시작라인끝

	   // 2층라인시작

	      // 점프대 기준 블럭 4칸 세로기준: 4550 가로 는 상관 x 
	      for (int i = 4550; i >= 4250; i -= 50) {
	         list_Block.add(new Block(0, 150, i));

	      }
	      // 4칸 에 공백두고 3000좌표까지 이어짐
	      for (int i = 3900; i >= 3000; i -= 50) {
	         list_Block.add(new Block(0, 150, i));

	      }
	      // 점프대끝
	      
	      // 2층 피라미드형 좌표
	      blockadd = 0;
	      blockdelete =0;
	      for (int i = 4300; i >= 2950; i -= 50) {
	         blockdelete += 50;
	         blockadd -= 50;
	         // 점프대 기준으로잡아야함 가로기준
	         for (int y = 900 + blockdelete; y <= 2500 + blockadd; y += 50) {
	            if (i == 4150 || i == 4100 || i==3800) {  //공백칸만들기 
	               break;
	            }
	            list_Block.add(new Block(0, y, i));

	         }
	      }
	      
	      //2점프대두번쨰기준 가로 라인 만들기   
	      blockdelete =0;
	      for (int i = 4250; i >=4250 ; i -= 50) {
	         blockdelete += 50;
	      for (int y = 250 ; y <= 400; y += 50) {
	         if(y==300) continue;
	            list_Block.add(new Block(0, y, i));  
	         }
	      }      
	      
	      //2층 점프대 두번쨰기준 세로 라인만들기
	      for (int i = 4250; i >=3000 ; i -= 50) {
	         
	         list_Block.add(new Block(0, 400, i));
	      }
	      
	      
	      //2층 점프대 두번쨰기준  역피라미드 만들기
	      blockdelete =0;
	               //i가 3000보다 크거나같을떄까지 
	      for (int i = 4250; i >=3000; i -= 50) {
	         if(i==3950||i==4000||i==4050||i==4100 ||i==3600 ||i==3550 ||i==3500 ||i==3450||i==3400||i==3350) continue;   //공백만들기 
	         blockdelete += 50;   
	      for (int y = 450; y <= 450+blockdelete; y += 50) {   
	            list_Block.add(new Block(0, y, i));  
	         }
	      }   
	      
	      //2층  역피라미드 와 피라미드를 연결해주는계단
	      blockadd =0;
	      blockdelete=0;
	      for (int i = 4100; i >=3200 ; i -= 250) {
	         blockdelete += 150;
	         blockadd -=150;
	      for (int y = 650+blockdelete ; y <= 750+blockdelete; y += 50) {
	         if(i==3850||i==3350) continue;
	         
	            list_Block.add(new Block(0, y, i));  
	         }
	      }      
	      
	      
	      //계단식 2층 블록
	      blockdelete=0;
	      for (int i = 3500; i >=3000; i -= 50) {
	         blockdelete += 50;
	         
	         for (int y = 1800 ; y <= 1850; y += 50) {
	         
	            
	               list_Block.add(new Block(0, y+blockdelete, i));  
	            }
	         }      
	      
	         //2층계단식밑에 열쇠 먹는 가로 블록들
	         for (int y = 2200 ; y <= 2500; y += 50) {
	         
	            if(y==2300) continue;
	               list_Block.add(new Block(0, y,3250 ));  
	            }
	               
	         
	      
	         //계단식 2층 역방향
	         blockdelete=0;
	         for (int i = 3500; i >=3000; i -= 50) {
	            blockdelete += 50;
	            
	            for (int y = 1800 ; y <= 1850; y += 50) {
	            
	               
	                  list_Block.add(new Block(0, y+blockdelete, i));  
	               }
	            }      
	      
	         
	      
	         //계단식 2층 3000좌표 이어주는 다리 
	         blockdelete=0;
	            
	            for (int y = 1350 ; y <= 2250; y += 50) {
	               if(y==1750 || y==1800) continue;
	               
	                  list_Block.add(new Block(0, y, 2850));  
	               }
	                  
	      
	         //2층과 3층 테두리 가로 
	            for (int i = 150 ; i <= 2450; i += 50) {
	               
	                  list_Block.add(new Block(0, i, 2500));  
	               }
	            
	         //점프대옥상 2층라인 왼쪽세로 기준    
	            
	            for(int i = 2500 ; i <= 3000; i += 50) {
	               list_Block.add(new Block(0, 150, i));  
	            }
	         //2층 옥상 낚시용 블록가로 두갈래길   
	            for(int i = 400 ; i <= 2250; i += 50) {
	            list_Block.add(new Block(0, i, 2700));  
	            }
	            
	         //2층옥상 낚시용 두갈래길 막는 세로벽   
	            
	            for(int i = 2500 ; i <= 2650; i += 50) {
	               list_Block.add(new Block(0, 400, i));  
	               }
	            
	            for(int i = 500 ; i <= 2250; i += 100) {
	               list_Block.add(new Block(2, i, 2650));  
	               }
	         //2층끝
	            
	         //3층시작
	            //3층첫번쨰블록행
	            for(int i = 2250 ; i >= 50; i -= 350) {   
	            for(int y = 1000 ; y <= 1400; y += 50) {
	               list_Block.add(new Block(0, y, i));  
	               }
	            }
	         //3층 두번쨰블록행    
	            for(int i = 2050 ; i >= 50; i -= 350) {   
	               for(int y = 1600 ; y <= 1800; y += 50) {
	                  list_Block.add(new Block(0, y, i));  
	                  }
	               }
	               
	         //3층 세번쨰 블록행
	            
	            for(int i = 2100 ; i >= 50; i -= 350) {
	            
	               for(int y = 2100 ; y <= 2450; y += 50) {
	                  list_Block.add(new Block(0, y, i));  
	                  }
	               }
	         //3층 가장왼쪽2번쨰 블록행
	            for(int i = 2050 ; i >= 950; i -= 350) {
	               
	               for(int y = 700 ; y <= 800; y += 50) {
	                  list_Block.add(new Block(0, y, i));  
	                  }
	               }
	         //3층 가장왼쪽 1번쨰 블록행
	            for(int i = 2150 ; i >= 950; i -= 350) {
	               
	               for(int y = 50 ; y <= 350; y += 50) {
	                  list_Block.add(new Block(0, y, i));  
	                  }
	               }
	            
	            
	            // 3층 왼쪽 반대 피라미드 형
	            blockdelete =0;
	            for (int i = 800; i >= 0; i -= 50) {
	               blockdelete -= 50;
	               for (int y = 800 + blockdelete; y >= 0; y -= 50) {
	                  list_Block.add(new Block(0, y, i));
	               }
	            }
	            
	            

	      //이벤트 시작

	       //이벤트발생좌표 1층 블록부시기 그뒤에열쇠 넣기
	      blockdelete =0;
	         for(int i =5000; i>=4700; i-=100) {
	            blockdelete += 100;
	            
	            for(int y=1850; y<=1950; y+=100) {
	               list_Block.add(new Block(1, y, i));   //부시는상자
	               list_Block.add(new Block(4,1900,i)); //열쇠
	               
	            }
	         }

	      // 이벤트발생좌표 2층 왼쪽 점프대
	            for(int i =4550; i>=4550; i-=50) {
	               for(int y =50; y<=100; y+=50) {
	               list_Block.add(new Block(3, y, i));    //점프대블록
	               }
	         }   
	      //이벤트발생좌표  2층 피라미드 위쪽  열쇠넣기 
	         for(int i =3800; i>=3800; i-=50) {
	            for(int y =1800; y<=1900; y+=100) {
	            list_Block.add(new Block(1, y, i));         //부시는상자
	            for(int key =1; key<=1; key++) {
	               list_Block.add(new Block(4, 1850, 3800));//열쇠
	               list_Block.add(new Block(4, 1550, 3800));//열쇠
	               list_Block.add(new Block(1, 1600, 3800));//부시는상자
	               list_Block.add(new Block(1, 1500, 3800));//부시는상자
	               
	            }
	         }
	      }   
	      
	      //이벤트발생좌표  2층 피라미드 아래 열쇠넣기       
	            for (int i = 4150; i >= 4100; i -= 50) {
	               blockdelete += 50;
	               for (int y = 1600  ; y <= 1700; y += 100) {
	                  list_Block.add(new Block(1, y, i));   //부시는상자
	                  list_Block.add(new Block(4,1650,i));  //열쇠
	               }
	            }
	      //이벤트발생좌표 2층피라미드 오른쪽구석 열쇠      
	            list_Block.add(new Block(4,2450,4250));
	            
	      //이벤트발생좌표 1층과2층가는구간 벽으로막는 상자
	            for (int i = 4550; i >= 4500; i -= 50) {
	               blockdelete += 50;
	               for (int y = 2300  ; y <= 2300; y += 50) {
	                  list_Block.add(new Block(2, y, i));    //옮기는상자
	                  
	               }
	            }
	      //이벤트발생좌표 2층 역피라미드 위에서첫번쨰공간 일반벽돌 ,열쇠 
	      blockdelete =0;
	      for (int i = 3600; i >=3500; i -= 50) {
	         
	      for (int y = 500; y <= 900; y += 100) {
	      
	            list_Block.add(new Block(0, y, i));  
	            list_Block.add(new Block(4,450,3600));
	         }
	      }      
	      
	      //이벤트발생좌표 2층 역피라미드 일반벽
	      for(int i = 4100; i>=3950; i-=50) {
	         for (int y = 500; y <= 500; y += 100) {
	            if(y==500 &&i==4050 )continue;
	            else if(y==500 &&i==4000 )continue;
	         list_Block.add(new Block(0, y, i));  
	         
	         
	      }
	   }      
	      
	      
	      //이벤트발생  부시는상자
	      list_Block.add(new Block(1, 550, 4100));  
	      list_Block.add(new Block(1, 550, 4050)); 
	      list_Block.add(new Block(1, 550, 4000)); 
	      
	      
	      //2층피라미드꼭데기 움직이는상자
	      list_Block.add(new Block(2, 450 ,4100));
	      list_Block.add(new Block(2, 1400 ,3800));
	      
	      //2층피라미드 첫번쨰 3층 움직이는블럭
	      list_Block.add(new Block(2,1100,4150));
	      list_Block.add(new Block(2,1100,4100));
	      list_Block.add(new Block(2,1100,4050));
	      
	      //3층 점프대 방해 블록
	      list_Block.add(new Block(2,250,2450));
	      list_Block.add(new Block(2,250,2400));
	      list_Block.add(new Block(2,300,2450));
	      list_Block.add(new Block(2,300,2400));
	      
	   

	      //2층 열쇠좌표
	      list_Block.add(new Block(2, 1750 ,3550));  
	      list_Block.add(new Block(2, 1750 ,3500));  
	      list_Block.add(new Block(2, 1750 ,3450));  
	   
	      list_Block.add(new Block(4, 450 ,4100));
	      list_Block.add(new Block(4, 2250,3200));
	      list_Block.add(new Block(4,450,2650));
	   
	   }//createStage()
	   //0번일반벽돌
	   //점프대블록 3번
	   //부시는상자 1번
	   //움직이는 상자 2번
	   //열쇠  4번 
	   
	   //현재열쇠6개  8개 

	

}
