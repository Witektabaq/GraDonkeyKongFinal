class Game{
	short screenW=800;short screenH=600;
 
 import java.util.List;
 import java.util.HashSet;
 
 List<GameCharacter> characters;
 HashSet<Character> activeKeys;
 
  
  void clearMove(){
activeKeys.remove('a');    activeKeys.remove('d');activeKeys.remove('o');    activeKeys.remove('l');//activeKeys.remove('w');
  }
  import ketai.ui.*;
import android.view.MotionEvent;
int backgroundColor=0;


  KetaiGesture gesture;
  public boolean surfaceTouchEvent(MotionEvent event) { 
  super.surfaceTouchEvent(event);  
  return gesture.surfaceTouchEvent(event);
}
  void onTap(float x, float y){
  }
  void onLongPress(float x, float y)                      
{
  myPressed(x,y);
}
 void myPressed(float x,float y){
    float mousex=x;float mousey=y;
   int delta=height/4;
   if(mousex>width-width/4 && mousey>delta && mousey<height-delta){
   clearMove();keyClick('d'); 
   }
   else if(mousex>width/2&&mousex<width/2+width/4 && mousey>delta && mousey<height-delta){
   clearMove();
     keyClick('a');      
   }
   else if(mousex>width/2&&mousex<width && mousey<delta){
        clearMove();
     keyClick('o');  
   }
   else if(mousex>width/2&&mousex<width && mousey>height-delta){
        clearMove();
     keyClick('l');
   }

 }
  void mouseReleased(){
    if(play==2){play=1;gameFlow();return;}
    if(chooseScreenDisplayed==true){if(mouseX<width/2)play=1;else play=2;chooseScreenDisplayed=false;return;}    
    clearMove();
    float x=mouseX;
         if(x<width/2){
        clearMove();
        if(x<width/4){
     keyClick('q');
        }else {
     keyClick('e');
        }
   }
 }

 PlayerCharacter playerCharacter;


 interface Jumpable{public void jump();}interface Drawable{public void drawIt();}

 short gravity=1;

 PFont myFont;
 int score;

 PImage ladder;PImage barrel;PImage platform;PImage ladyRight;PImage ladyLeft;PImage woodworker;
 MusicPlayer mp;
  void setup()  {   
    mp = new MusicPlayer(this.getActivity());
    ladder=loadImage("ladder.png");barrel=loadImage("barrel.png");platform=loadImage("platform.png");
    ladyLeft=loadImage("ladyLeft.png");ladyRight=loadImage("ladyRight.png");woodworker=loadImage("woodworker.png");
    
    
    myFont = createFont("Georgia", 32);
  textFont(myFont);
  textSize(32);
    
    gesture = new KetaiGesture(this);
    frameRate(25);
    orientation(LANDSCAPE);  
          screenW=(short)width;screenH=(short)height;
setupGame();
           } 
           int worstHigh=698;int ascore;
           Ladder firstLadder;
           void setupGame(){
             activeKeys = new HashSet<Character>();
           characters = new ArrayList<GameCharacter>();           
                      
           GameCharacter enemy = new Enemy((short)1);characters.add(enemy);
           for(short i=2;i<7;i++){enemy = new Enemy((short)i);characters.add(enemy);}
           
           GameCharacter platform = new Platform((short)(0),(short)(screenH-20));characters.add(platform);
           platform = new Platform((short)(20),(short)(screenH-100),(short)(screenW-20),(short)(20));characters.add(platform);
           platform = new Platform((short)(40),(short)(screenH-180),(short)(screenW-40),(short)(20));characters.add(platform);
           
           platform = new Platform((short)(60),(short)(screenH-260),(short)(screenW-60),(short)(20));characters.add(platform);
           platform = new Platform((short)(80),(short)(screenH-340),(short)(screenW-80),(short)(20));characters.add(platform);
           platform = new Platform((short)(100),(short)(screenH-420),(short)(screenW-100),(short)(20));characters.add(platform);
           platform = new Platform((short)(120),(short)(screenH-500),(short)(screenW-120),(short)(20));characters.add(platform);
           
           platform = new Platform((short)(0),(short)(screenH-540),(short)(120),(short)(20));characters.add(platform);
           //platform = new Platform((short)(160),(short)(screenH-580),(short)(screenW-160),(short)(20));characters.add(platform);
           
           GameCharacter ladder = new Ladder((short)(screenW/4),(short)(screenH-100));characters.add(ladder);
           ladder = new Ladder((short)(3*screenW/4),(short)(screenH-180));characters.add(ladder);firstLadder=(Ladder)ladder;
           
           ladder = new Ladder((short)(screenW/4),(short)(screenH-260));characters.add(ladder);
           ladder = new Ladder((short)(3*screenW/4),(short)(screenH-340));characters.add(ladder);
           ladder = new Ladder((short)(screenW/4),(short)(screenH-420));characters.add(ladder);
           ladder = new Ladder((short)(3*screenW/4),(short)(screenH-500));characters.add(ladder);

           
           GameCharacter woodworker = new Woodworker();characters.add(woodworker);
           
           playerCharacter = new PlayerCharacter();
           characters.add(playerCharacter);
           
           t=0;enemySpeed=1;level=1;curHighRec=worstHigh;score=0;
           }
           int t=0;
           short enemySpeed=1;
           int level=1;
           int curHighRec=worstHigh;
           
                      
           int dY=2;
           void calcScore(){
             int playerY = playerCharacter.getY();
             if(playerY<curHighRec){
               for(GameCharacter gameCharacter : characters){
                 if(gameCharacter instanceof Platform )
                 if(playerY==gameCharacter.getY()-dY){
                   curHighRec=playerY;
                   score++;               //*f(lvl)?
                   break;
                 }
               }//end for(GameCharacter gameCharacter : characters)

             }//end if(playerY<curHighRec)
           }//end calcScore
           
           void gameFlow(){
                  
     background(backgroundColor);
     
     calcScore();
     fill(255);
     text("score : "+score, 20, 60);
     if(play==2){text("ascore : "+ascore/100, 200, 25);
     text("enemPos : "+(int)(100*inputs[2]), 400, 25);
     }

     backgroundColor=0;
   fill(0);
   boolean airFlag=!(playerCharacter.state instanceof LadderState) ? true:false;
   boolean ladderFlag=false;

   for(GameCharacter gameCharacter : characters){
     if(gameCharacter!=playerCharacter){
       
       
         if(gameCharacter instanceof Woodworker ){
   fill(60,42,42);
   if (playerCharacter.checkForCollision(gameCharacter)==DOWN || playerCharacter.checkForCollision(gameCharacter)==UP||
   playerCharacter.checkForCollision(gameCharacter)==RIGHT||playerCharacter.checkForCollision(gameCharacter)==LEFT){
     mp.play(2);
     playerCharacter.startPos();
     level++;enemySpeed++;curHighRec=worstHigh;score+=10;//*f(lvl)?
   }   
 }

       if(gameCharacter instanceof Platform ){
       if (playerCharacter.checkForCollision(gameCharacter)==DOWN){         //println("DOWN" );
         if((playerCharacter.getIsInTheAir()&&playerCharacter.getVy()>0)||playerCharacter.state instanceof LadderState){
           playerCharacter.setY((short)(gameCharacter.getY()-playerCharacter.getH()));
           playerCharacter.setIsInTheAir(false);airFlag=false;
   }playerCharacter.setVy((short)0);
   
 }else 
 if(airFlag)playerCharacter.setIsInTheAir(true); }
 
 if(gameCharacter instanceof Ladder ){
   
   if (playerCharacter.checkForCollision(gameCharacter)==DOWN || playerCharacter.checkForCollision(gameCharacter)==UP)
     ladderFlag=true; 
   
 }
  if(gameCharacter instanceof Enemy ){
   fill(60,42,42);
   if (playerCharacter.checkForCollision(gameCharacter)==DOWN || playerCharacter.checkForCollision(gameCharacter)==UP||
   playerCharacter.checkForCollision(gameCharacter)==RIGHT||playerCharacter.checkForCollision(gameCharacter)==LEFT){
     playerCharacter.startPos();kill();//playerCharacter.setX((short)(screenW/2)); playerCharacter.setY((short)(screenH-25));//x=(short)(screenW/2);y=(short)(screenH-25);
   }    
 }
     }
 
 
     if(playerCharacter.getX()<0||playerCharacter.getX()+playerCharacter.getW()>screenW||
     playerCharacter.getY()<0||playerCharacter.getY()+playerCharacter.getH()>screenH){
       playerCharacter.startPos();//playerCharacter.setX((short)(screenW/2)); playerCharacter.setY((short)(screenH-25));
     }
     gameCharacter.update();
     if(gameCharacter instanceof PlayerCharacter)
   {if((activeKeys.contains('a')&& !(playerCharacter.state instanceof JumpingState))||activeKeys.contains('q')&& (playerCharacter.state instanceof WalkingState))
       {((PlayerCharacter)gameCharacter).lady=ladyLeft;}
 if((activeKeys.contains('d')&& !(playerCharacter.state instanceof JumpingState))||activeKeys.contains('e')&& (playerCharacter.state instanceof WalkingState))
 {((PlayerCharacter)gameCharacter).lady=ladyRight;}
 }
   gameCharacter.drawIt();
   }     
   if(ladderFlag)playerCharacter.ladderCollision=true; else playerCharacter.ladderCollision=false;
           }
           
                      
   void drawChooseScreen(){background(255);fill(0);pushMatrix();translate(width/4,height/2);text("PLAY !", 0, 0);translate(width/2,0);text("WATCH !", 0, 0);popMatrix();};
   
   NeuroEvolution neuroEvolution;boolean on=false;
   
 void brainTurnOn(){
   println("NEW BRAIN!");
   neuroEvolution = new NeuroEvolution(1,6,2,10);//in: enemypos, mypos,enemyspeed,myspeed,ladderpos; ou:jumpL,jumpR,goL,goR,ladU;
   on=true;
   cur = getNext();
 }
 
 void keyClick(char c){if(activeKeys.size()==0)activeKeys.add(c);}//println(c+" was clicked");}
 
 void jumpOverEnemy(){
   Enemy theEnemy = closestEnemy();
   if(theEnemy==null)return;
   if(theEnemy.getX()>playerCharacter.getX())  keyClick('e');//activeKeys.add('e');
   else                                        keyClick('q');//activeKeys.add('q');
 }
 
 void seekLadder(){
 
           if(playerCharacter.getY()<screenH-500){if(playerCharacter.getX()>120 &&playerCharacter.getX()<130)keyClick('q');else keyClick('a');}//if(playerCharacter.state instanceof JumpingState)return;}
   
   
           if(playerCharacter.ladderCollision==true){
           Enemy e = closestEnemy();int delta=50;
         if(e!=null){
           if(abs(dist(e.getX(),e.getY(),playerCharacter.getX(),playerCharacter.getY()))<delta
               &&e.getY()<playerCharacter.getY()
               &&abs(dist(e.getX(),0,playerCharacter.getX(),0))<delta){return;}} 
         keyClick('o');return;}
           if(playerCharacter.getY()>screenH-100){seekLadder(0);}
           else if(playerCharacter.getY()>screenH-180){seekLadder(1);}
           else if(playerCharacter.getY()>screenH-260){seekLadder(2);}
           else if(playerCharacter.getY()>screenH-340){seekLadder(3);}
           else if(playerCharacter.getY()>screenH-420){seekLadder(4);}
           else if(playerCharacter.getY()>screenH-500){seekLadder(5);}
           
 }
 void seekLadder(int id){int epsilon=20;int ladderx;
   if(id%2==0){ladderx=screenW/4;}
   else {ladderx=3*screenW/4;}if(playerCharacter.getX()>ladderx){if((playerCharacter.getX()-ladderx)<epsilon){ playerCharacter.x-=1;}else keyClick('a');}
 else if(playerCharacter.getX()<ladderx){if((ladderx-playerCharacter.getX())<epsilon){playerCharacter.x+=1;}else keyClick('d');}//else {keyClick('o');}

 }
 
 int curIndex;
 NeuralNetwork getNext(){
   NeuralNetwork ret = neuroEvolution.getNext();
   curIndex = neuroEvolution.curIndex;
   return ret;
 }
 
 NeuralNetwork cur;int gen=1;double[] inputs = new double[3];
 
 double neuralPos(GameCharacter gc){return neuralPosFromyx(gc.getY(),gc.getX());}//(1.0*gc.getY()*width+gc.getX())/(height*width); }
 
 double neuralPosFromyx(short y,short x){return (1.0*x*height+y)/(height*width)/2;}//720*1280);}//(height*width);}
  
 Enemy closestEnemy(){               for(GameCharacter gameCharacter : characters){
                 if(gameCharacter instanceof Enemy )
                 if(abs(playerCharacter.getY()-gameCharacter.getY())<30){//if(playerCharacter.getY()>gameCharacter.getY()-10*dY && playerCharacter.getY()<gameCharacter.getY()+20*dY){
                   return (Enemy)gameCharacter;
                 }
               }return null;}
 Ladder nextLadder       ;       
 Ladder closestLadder(){return firstLadder;

             }
             
               int lasttimeout;int decisionsOfCur=0;
               int ypoints=0;
               void kill(){
                 isLadderNextToMe();

                 int playery=playerCharacter.getY();
                 if(playery<screenH-260)ypoints=1;else if(playery<screenH-340)ypoints=2;else if(playery<screenH-420)ypoints=3;else if(playery<screenH-500)ypoints=4;
                 playerCharacter.dead=true;
                 mp.play(1);
               }int nextToWhichLadder=-1;int ladderPoints=0;
               void isLadderNextToMe(){//Ladder l = closestLadder();if(l==null )return false;

           if(nextToWhichLadder==-1){ladderPoints=2829-(int)dist(screenW/4,screenH-100+80,playerCharacter.getX(),playerCharacter.getY());}//nextToWhichLadder=0;}//2000x2000
           }
   void watch(){//System.out.println("WATCH MODE!");

   gameFlow();
 if(!on)brainTurnOn();//if(neuroEvolution.wholeGeneration){ neuroEvolution.evalNextGen();gen++;neuroEvolution.wholeGeneration=false;}
 //else
 {
   clearMove();
   
   if(playerCharacter.dead){
   
   ascore=(score)*10000+ypoints*4000+ladderPoints;ladderPoints=0;setupGame();//ladderPoints<3000
   neuroEvolution.setScore(ascore);decisionsOfCur=0;cur = getNext();playerCharacter.dead=false;return;}
   
   Enemy theEnemy = closestEnemy();double velDiv=100.0;

   inputs[1]=playerCharacter.getVx()/velDiv;//my velocity
   inputs[2]=theEnemy==null?-1:dist(theEnemy.getX(),theEnemy.getY(),playerCharacter.getX(),playerCharacter.getY())/2000;//distance to enemy
   inputs[0]=theEnemy==null?-1:(playerCharacter.getX()-theEnemy.getX())/(0.1+abs(playerCharacter.getX()-theEnemy.getX()))*theEnemy.getVx()/velDiv;//enemy velocity
   if(theEnemy!=null)inputs[2]=inputs[2]/inputs[0]/100;//time to jump
   
   double delta = 0.03; 
   if(inputs[2]!=-1&&abs((float)inputs[2])<=delta){//enemy is close
     if (inputs[0]!=-1&&(inputs[0]>0))jumpOverEnemy();else {playerCharacter.nothing();}}
   else seekLadder();
   
   double[] trueInputs = new double[1];trueInputs[0]=inputs[0];
   double[] decision = cur.predict(trueInputs);
   double theDec=decision[0];int theDecIndex=0;for(int i=1;i<decision.length;i++)if(theDec<decision[i]){theDec=decision[i];theDecIndex=i;}

   decisionsOfCur++;if(decisionsOfCur>150 && score==0){decisionsOfCur=0;kill();return;}

 }}
   int play=0;int watchModeStarted;boolean chooseScreenDisplayed=true;
   

   boolean speedUp=false;
   
   void draw()  {
     mp.play(0);
     if(chooseScreenDisplayed==true) drawChooseScreen();
     if(play==1)gameFlow();
     else if(play==2)//for(int i=0;i<(speedUp?100:1);i++)
   if(!speedUp){watch();};//watchModeStarted=frameCount;}//frameRate(120);}

} 

}