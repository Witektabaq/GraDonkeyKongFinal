   class Enemy extends GameCharacter{
     short instNum;float angle=0;
   public void drawIt(){
     fill(#FC7405);//fill(255,0,0);    
     ellipse(x,y,w,h);//rect(x,y,w,h);
 }
   public void update(){if(x<=(short)(instNum*20))vx=enemySpeed;if(x>=screenW-w)vx=(short)(-enemySpeed);x+=vx;angle+=vx;}
   public Enemy(short numOfIns){ instNum=numOfIns;w=10;h=10;x=(short)(screenW-w);y=(short)(screenH-80*(numOfIns-1)-100-10);}
 }