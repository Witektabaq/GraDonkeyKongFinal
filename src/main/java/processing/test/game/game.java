package processing.test.game;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.List; 
import java.util.HashSet; 
import ketai.ui.*; 
import android.view.MotionEvent; 
import android.media.MediaPlayer; 
import android.content.res.Resources; 
import android.content.res.AssetFileDescriptor; 
import android.content.res.AssetManager; 
import android.content.Context; 
import android.app.Activity; 
import android.os.Bundle; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class game extends PApplet {

short screenW=800;short screenH=600;
 
 
 
 
 List<GameCharacter> characters;
 HashSet<Character> activeKeys;
 
  
  public void clearMove(){
activeKeys.remove('a');    activeKeys.remove('d');activeKeys.remove('o');    activeKeys.remove('l');//activeKeys.remove('w');
  }
  

int backgroundColor=0;


  KetaiGesture gesture;
  public boolean surfaceTouchEvent(MotionEvent event) { 
  //call to keep mouseX and mouseY constants updated
  super.surfaceTouchEvent(event);
  //forward events
  return gesture.surfaceTouchEvent(event);
}
  public void onTap(float x, float y){
  }
  public void onLongPress(float x, float y)                      
{
  myPressed(x,y);
}
 public void myPressed(float x,float y){
    float mousex=x;float mousey=y;
   int delta=height/4;
   if(mousex>width-width/4 && mousey>delta && mousey<height-delta){
   clearMove();keyClick('d');
     //keyClick('d');      
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
  public void mouseReleased(){
    //if(play==1)gameFlow();
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


// void keyPressed()
//{
//  // If the key is between 'A'(65) to 'Z' and 'a' to 'z'(122)
//  if((key >= 'A' && key <= 'Z') || (key >= 'a' && key <= 'z')) {
//    //if(keysUsedInGame.contains(key)) 
//    {
//      keyClick(key);
//      //playerCharacter.walk();
//    }
//  }
//}
// void keyReleased()
//{
//  // If the key is between 'A'(65) to 'Z' and 'a' to 'z'(122)
//  //if((key >= 'A' && key <= 'Z') || (key >= 'a' && key <= 'z')) 
//  {
//    //if(key == 'w' || key == 'W') 
//    {
//      activeKeys.remove(key);
//      //playerCharacter.nothing();
//    }
//  }
//}
 interface Jumpable{public void jump();}interface Drawable{public void drawIt();}

 short gravity=1;
  
//abstract class Command<T>
//{
//abstract public void execute(T o);
//}
//class JumpCommand extends Command<Jumpable>{
//  public void execute(Jumpable j){j.jump();}
//}
 
 
 PFont myFont;
 int score;
 
 //NeuroEvolution xor;
 
   
//import processing.sound.*;
//SoundFile backgroundMusic,hitSound;
 PImage ladder;PImage barrel;PImage platform;PImage ladyRight;PImage ladyLeft;PImage woodworker;
 MusicPlayer mp;
  public void setup()  {   
    mp = new MusicPlayer(this.getActivity());
    //xor = new NeuroEvolution(2,6,1,100);
    //backgroundMusic = new SoundFile(this, "bensound-groovyhiphop.mp3");
    //backgroundMusic.loop();
    ladder=loadImage("ladder.png");barrel=loadImage("barrel.png");platform=loadImage("platform.png");
    ladyLeft=loadImage("ladyLeft.png");ladyRight=loadImage("ladyRight.png");woodworker=loadImage("woodworker.png");
    
    
    myFont = createFont("Georgia", 32);
  textFont(myFont);
  textSize(32);
    
    gesture = new KetaiGesture(this);
    frameRate(25);
    orientation(LANDSCAPE);  
          //size(800,600); // Set the size of the window 
          screenW=(short)width;screenH=(short)height;
           //smooth(); 
            //keysUsedInGame = new HashSet<Character>();keysUsedInGame.add('w');keysUsedInGame.add('s');keysUsedInGame.add('a');keysUsedInGame.add('d');keysUsedInGame.add('q');keysUsedInGame.add('e');
setupGame();
           } 
           int worstHigh=698;int ascore;
           Ladder firstLadder;
           public void setupGame(){
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
           public void calcScore(){
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
           
           public void gameFlow(){
                  
     background(backgroundColor);
     
     calcScore();
     fill(255);
     text("score : "+score, 20, 60);
     if(play==2){text("ascore : "+ascore/100, 200, 25);
     text("enemPos : "+(int)(100*inputs[2]), 400, 25);
     //text("yourPos : "+(int)(100*inputs[0]), 600, 25);
     //text("ypoints : "+ypoints, 600, 25);//ypoints
     }
     //text("playerY : "+playerCharacter.getY(), 10, 20); 
     
     backgroundColor=0;
   fill(0);
   boolean airFlag=!(playerCharacter.state instanceof LadderState) ? true:false;
   boolean ladderFlag=false;
   
   //if(playerCharacter.state instanceof LadderState)println("LadderState" );
   
   for(GameCharacter gameCharacter : characters){
     if(gameCharacter!=playerCharacter){
       
       
         if(gameCharacter instanceof Woodworker ){
   fill(60,42,42);
   if (playerCharacter.checkForCollision(gameCharacter)==DOWN || playerCharacter.checkForCollision(gameCharacter)==UP||
   playerCharacter.checkForCollision(gameCharacter)==RIGHT||playerCharacter.checkForCollision(gameCharacter)==LEFT){
     mp.play(2);
     playerCharacter.startPos();
     level++;enemySpeed++;curHighRec=worstHigh;score+=10;//*f(lvl)?
     //playerCharacter.setX((short)(screenW/2)); playerCharacter.setY((short)(screenH-25));//x=(short)(screenW/2);y=(short)(screenH-25);
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
       //playerCharacter.printMe();//println("startPos");
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
           
                      
   public void drawChooseScreen(){background(255);fill(0);pushMatrix();translate(width/4,height/2);text("PLAY !", 0, 0);translate(width/2,0);text("WATCH !", 0, 0);popMatrix();};
   
   NeuroEvolution neuroEvolution;boolean on=false;
   
 public void brainTurnOn(){
   println("NEW BRAIN!");
   neuroEvolution = new NeuroEvolution(1,6,2,10);//in: enemypos, mypos,enemyspeed,myspeed,ladderpos; ou:jumpL,jumpR,goL,goR,ladU;
   on=true;
   cur = getNext();
 }
 
 public void keyClick(char c){if(activeKeys.size()==0)activeKeys.add(c);}//println(c+" was clicked");}
 
 public void jumpOverEnemy(){
   Enemy theEnemy = closestEnemy();
   if(theEnemy==null)return;
   if(theEnemy.getX()>playerCharacter.getX())  keyClick('e');//activeKeys.add('e');
   else                                        keyClick('q');//activeKeys.add('q');
 }
 
 public void seekLadder(){
   //platform = new Platform((short)(120),(short)(screenH-500),(short)(screenW-120),(short)(20));
           if(playerCharacter.getY()<screenH-500){if(playerCharacter.getX()>120 &&playerCharacter.getX()<130)keyClick('q');else keyClick('a');}//if(playerCharacter.state instanceof JumpingState)return;}
   
   
           if(playerCharacter.ladderCollision==true){
           Enemy e = closestEnemy();int delta=50;
         if(e!=null){
           if(abs(dist(e.getX(),e.getY(),playerCharacter.getX(),playerCharacter.getY()))<delta
               &&e.getY()<playerCharacter.getY()
               &&abs(dist(e.getX(),0,playerCharacter.getX(),0))<delta){return;}} 
         keyClick('o');return;}
   
           //platform = new Platform((short)(20),(short)(screenH-100),(short)(screenW-20),(short)(20));characters.add(platform);
           //platform = new Platform((short)(40),(short)(screenH-180),(short)(screenW-40),(short)(20));characters.add(platform);           
           //platform = new Platform((short)(60),(short)(screenH-260),(short)(screenW-60),(short)(20));characters.add(platform);
           //platform = new Platform((short)(80),(short)(screenH-340),(short)(screenW-80),(short)(20));characters.add(platform);
           //platform = new Platform((short)(100),(short)(screenH-420),(short)(screenW-100),(short)(20));characters.add(platform);
           //platform = new Platform((short)(120),(short)(screenH-500),(short)(screenW-120),(short)(20));characters.add(platform);           
           //platform = new Platform((short)(0),(short)(screenH-540),(short)(120),(short)(20));characters.add(platform);           

           if(playerCharacter.getY()>screenH-100){seekLadder(0);}
           else if(playerCharacter.getY()>screenH-180){seekLadder(1);}
           else if(playerCharacter.getY()>screenH-260){seekLadder(2);}
           else if(playerCharacter.getY()>screenH-340){seekLadder(3);}
           else if(playerCharacter.getY()>screenH-420){seekLadder(4);}
           else if(playerCharacter.getY()>screenH-500){seekLadder(5);}
           
 }
 public void seekLadder(int id){int epsilon=20;int ladderx;
   if(id%2==0){ladderx=screenW/4;}
   else {ladderx=3*screenW/4;}if(playerCharacter.getX()>ladderx){if((playerCharacter.getX()-ladderx)<epsilon){ playerCharacter.x-=1;}else keyClick('a');}
 else if(playerCharacter.getX()<ladderx){if((ladderx-playerCharacter.getX())<epsilon){playerCharacter.x+=1;}else keyClick('d');}//else {keyClick('o');}
              //ladder = new Ladder((short)(screenW/4),(short)(screenH-100));characters.add(ladder);
           //ladder = new Ladder((short)(3*screenW/4),(short)(screenH-180));characters.add(ladder);firstLadder=(Ladder)ladder;           
           //ladder = new Ladder((short)(screenW/4),(short)(screenH-260));characters.add(ladder);
           //ladder = new Ladder((short)(3*screenW/4),(short)(screenH-340));characters.add(ladder);
           //ladder = new Ladder((short)(screenW/4),(short)(screenH-420));characters.add(ladder);
           //ladder = new Ladder((short)(3*screenW/4),(short)(screenH-500));characters.add(ladder);
 }
 
 int curIndex;
 public NeuralNetwork getNext(){
   NeuralNetwork ret = neuroEvolution.getNext();
   curIndex = neuroEvolution.curIndex;
   return ret;
 }
 
 NeuralNetwork cur;int gen=1;double[] inputs = new double[3];
 
 public double neuralPos(GameCharacter gc){return neuralPosFromyx(gc.getY(),gc.getX());}//(1.0*gc.getY()*width+gc.getX())/(height*width); }
 
 public double neuralPosFromyx(short y,short x){return (1.0f*x*height+y)/(height*width)/2;}//720*1280);}//(height*width);}
  
 public Enemy closestEnemy(){               for(GameCharacter gameCharacter : characters){
                 if(gameCharacter instanceof Enemy )
                 if(abs(playerCharacter.getY()-gameCharacter.getY())<30){//if(playerCharacter.getY()>gameCharacter.getY()-10*dY && playerCharacter.getY()<gameCharacter.getY()+20*dY){
                   return (Enemy)gameCharacter;
                 }
               }return null;}
 Ladder nextLadder       ;       
 public Ladder closestLadder(){return firstLadder;
   //for(GameCharacter gameCharacter : characters){
   //              if(gameCharacter instanceof Ladder )
   //              if(playerCharacter.getY()>gameCharacter.getY()+gameCharacter.getH()-10*dY && playerCharacter.getY()<gameCharacter.getY()+gameCharacter.getH()+10*dY){
   //                nextLadder = (Ladder)gameCharacter;
   //                return nextLadder;//(Ladder)gameCharacter;
   //              }
   //            }return null;
             }
             
               int lasttimeout;int decisionsOfCur=0;
               int ypoints=0;
               public void kill(){
                 isLadderNextToMe();
                 
           //platform = new Platform((short)(60),(short)(screenH-260),(short)(screenW-60),(short)(20));characters.add(platform);
           //platform = new Platform((short)(80),(short)(screenH-340),(short)(screenW-80),(short)(20));characters.add(platform);
           //platform = new Platform((short)(100),(short)(screenH-420),(short)(screenW-100),(short)(20));characters.add(platform);
           //platform = new Platform((short)(120),(short)(screenH-500),(short)(screenW-120),(short)(20));characters.add(platform);
                 
                 int playery=playerCharacter.getY();
                 if(playery<screenH-260)ypoints=1;else if(playery<screenH-340)ypoints=2;else if(playery<screenH-420)ypoints=3;else if(playery<screenH-500)ypoints=4;
                 playerCharacter.dead=true;
                 mp.play(1);
               }int nextToWhichLadder=-1;int ladderPoints=0;
               public void isLadderNextToMe(){//Ladder l = closestLadder();if(l==null )return false;
               //if(playerCharacter.getX()+5>l.getX()&&playerCharacter.getX()-5<l.getX())return true;return false;
               
           //ladder = new Ladder((short)(screenW/4),(short)(screenH-260));characters.add(ladder);
           //ladder = new Ladder((short)(3*screenW/4),(short)(screenH-340));characters.add(ladder);
           //ladder = new Ladder((short)(screenW/4),(short)(screenH-420));characters.add(ladder);
           //ladder = new Ladder((short)(3*screenW/4),(short)(screenH-500));characters.add(ladder); 
           if(nextToWhichLadder==-1){ladderPoints=2829-(int)dist(screenW/4,screenH-100+80,playerCharacter.getX(),playerCharacter.getY());}//nextToWhichLadder=0;}//2000x2000
           //if(nextToWhichLadder== 0){nextToWhichLadder=1;ladderPoints+=2829-dist(3*screenW/4,screenH-340,playerCharacter.getX(),playerCharacter.getY());}
           //if(nextToWhichLadder== 1){nextToWhichLadder=2;ladderPoints+=2829-dist(screenW/4,screenH-420,playerCharacter.getX(),playerCharacter.getY());}
           //if(nextToWhichLadder== 2){nextToWhichLadder=3;ladderPoints+=2829-dist(3*screenW/4,screenH-500,playerCharacter.getX(),playerCharacter.getY());}
           
           
           }
   public void watch(){//System.out.println("WATCH MODE!");
   //if(isLadderNextToMe()){timeNextToLadder++;}
   gameFlow();
 if(!on)brainTurnOn();//if(neuroEvolution.wholeGeneration){ neuroEvolution.evalNextGen();gen++;neuroEvolution.wholeGeneration=false;}
 //else
 {
   clearMove();
   
   //if(frameCount%1200==0)System.out.println("frameCount-watchModeStarted = "+(frameCount-watchModeStarted));
   //if(score==0 && frameCount-watchModeStarted>600){playerCharacter.dead=true;System.out.println("TIME OUT!");lasttimeout=frameCount;}
   if(playerCharacter.dead){
   //playerCharacter.startPos(); 
   ascore=(score)*10000+ypoints*4000+ladderPoints;ladderPoints=0;setupGame();//ladderPoints<3000
   neuroEvolution.setScore(ascore);decisionsOfCur=0;cur = getNext();playerCharacter.dead=false;return;}
   
   Enemy theEnemy = closestEnemy();double velDiv=100.0f;
   //to set number of inputs/outputs go to line line 284
   inputs[1]=playerCharacter.getVx()/velDiv;//my velocity
   inputs[2]=theEnemy==null?-1:dist(theEnemy.getX(),theEnemy.getY(),playerCharacter.getX(),playerCharacter.getY())/2000;//distance to enemy
   inputs[0]=theEnemy==null?-1:(playerCharacter.getX()-theEnemy.getX())/(0.1f+abs(playerCharacter.getX()-theEnemy.getX()))*theEnemy.getVx()/velDiv;//enemy velocity
   if(theEnemy!=null)inputs[2]=inputs[2]/inputs[0]/100;//time to jump
   
   double delta = 0.03f; 
   if(inputs[2]!=-1&&abs((float)inputs[2])<=delta){//enemy is close
     if (inputs[0]!=-1&&(inputs[0]>0))jumpOverEnemy();else {playerCharacter.nothing();}}
   else seekLadder();
   
   double[] trueInputs = new double[1];trueInputs[0]=inputs[0];
   double[] decision = cur.predict(trueInputs);
   double theDec=decision[0];int theDecIndex=0;for(int i=1;i<decision.length;i++)if(theDec<decision[i]){theDec=decision[i];theDecIndex=i;}
   //System.out.println("decision : "+theDecIndex);
   decisionsOfCur++;if(decisionsOfCur>150 && score==0){decisionsOfCur=0;kill();return;}
   
   //do
   //if(inputs[0]==1)seekLadder();else jumpOverEnemy();
   //switch(theDecIndex){case 0: jumpOverEnemy();break;case 1: seekLadder();break;}//case 2: seekLadder();/*keyClick('a');*/break;case 3: /*keyClick('d');*/break;
   //case 4: /*keyClick('o');*/break;case 5: /*keyClick('l');*/break;default:{break;}}
   //if(frameCount%60==0)println("curBest : "+neuroEvolution.curBest);
   
 }}
   int play=0;int watchModeStarted;boolean chooseScreenDisplayed=true;
   
   //NeuralNetwork x;
   boolean speedUp=false;
   
   public void draw()  {
     mp.play(0);
          //choose what to do: play of watch...
     if(chooseScreenDisplayed==true) drawChooseScreen();
     if(play==1)gameFlow();
     else if(play==2)//for(int i=0;i<(speedUp?100:1);i++)
   if(!speedUp){watch();};//watchModeStarted=frameCount;}//frameRate(120);}

     }
     
     
     
     
    // frameRate(1);
     
     //for(int i=0;i<xor.pop.length;i++){
     //x = xor.getNext();
     //double[] inputs = new double[2];
     //inputs[0]=0;inputs[1]=0;
     //double err=0;
     //err+=abs((float)(x.predict(inputs)[0]-0));     
     //inputs[0]=0;inputs[1]=1;
     //err+=abs((float)(x.predict(inputs)[0]-1));     
     //inputs[0]=1;inputs[1]=0;
     //err+=abs((float)(x.predict(inputs)[0]-1));     
     //inputs[0]=1;inputs[1]=1;
     //err+=abs((float)(x.predict(inputs)[0]-0));     
     //xor.setScore(-err);     
     //}
     
     
     
     
     
     
     
     
     
     

//short screenW=800;short screenH=600;
 
// import java.util.List;
// import java.util.HashSet;
 
// List<GameCharacter> characters;
// HashSet<Character> activeKeys;
 
  
//  void clearMove(){
//activeKeys.remove('a');    activeKeys.remove('d');activeKeys.remove('o');    activeKeys.remove('l');//activeKeys.remove('w');
//  }
////  import ketai.ui.*;
////import android.view.MotionEvent;
//int backgroundColor=255;


//  //KetaiGesture gesture;
////  public boolean surfaceTouchEvent(MotionEvent event) { 
////  //call to keep mouseX and mouseY constants updated
////  super.surfaceTouchEvent(event);
////  //forward events
////  return gesture.surfaceTouchEvent(event);
////}
//  void onTap(float x, float y){
//  }
//  void onLongPress(float x, float y)                      
//{
//  myPressed(x,y);
//}
// void myPressed(float x,float y){
//    float mousex=x;float mousey=y;
//   int delta=height/4;
//   if(mousex>width-width/4 && mousey>delta && mousey<height-delta){
//   clearMove();
//     keyClick('d');      
//   }
//   else if(mousex>width/2&&mousex<width/2+width/4 && mousey>delta && mousey<height-delta){
//   clearMove();
//     keyClick('a');      
//   }
//   else if(mousex>width/2&&mousex<width && mousey<delta){
//        clearMove();
//     keyClick('o');  
//   }
//   else if(mousex>width/2&&mousex<width && mousey>height-delta){
//        clearMove();
//     keyClick('l');
//   }

// }
//  void mouseReleased(){
//    int deltay=height/4;
//    clearMove();
//    float x=mouseX;float y=mouseY;
//         if(x<width/2){
//        clearMove();
//        if(x<width/4){
//     keyClick('q');
//        }else {
//     keyClick('e');
//        }
//   }else{
//     if(y<deltay){
//     keyClick('o');
//        }else if(y>height-deltay){
//     keyClick('l');
//        }else if(x>3*width/4){
//          keyClick('d');
//        }
//        else{
//          keyClick('a');
//        }
//   }
// }

// PlayerCharacter playerCharacter;


//// void keyPressed()
////{
////  // If the key is between 'A'(65) to 'Z' and 'a' to 'z'(122)
////  if((key >= 'A' && key <= 'Z') || (key >= 'a' && key <= 'z')) {
////    //if(keysUsedInGame.contains(key)) 
////    {
////      keyClick(key);
////      //playerCharacter.walk();
////    }
////  }
////}
//// void keyReleased()
////{
////  // If the key is between 'A'(65) to 'Z' and 'a' to 'z'(122)
////  //if((key >= 'A' && key <= 'Z') || (key >= 'a' && key <= 'z')) 
////  {
////    //if(key == 'w' || key == 'W') 
////    {
////      activeKeys.remove(key);
////      //playerCharacter.nothing();
////    }
////  }
////}
// interface Jumpable{public void jump();}interface Drawable{public void drawIt();}

// short gravity=1;
  
////abstract class Command<T>
////{
////abstract public void execute(T o);
////}
////class JumpCommand extends Command<Jumpable>{
////  public void execute(Jumpable j){j.jump();}
////}
 
//  void setup()  {   
//    //gesture = new KetaiGesture(this);
//    frameRate(25);
//    orientation(LANDSCAPE);  
//          size(800,600); // Set the size of the window 
//          screenW=(short)width;screenH=(short)height;
//           smooth(); 
//            //keysUsedInGame = new HashSet<Character>();keysUsedInGame.add('w');keysUsedInGame.add('s');keysUsedInGame.add('a');keysUsedInGame.add('d');keysUsedInGame.add('q');keysUsedInGame.add('e');
//            activeKeys = new HashSet<Character>();
//           characters = new ArrayList<GameCharacter>();
           
//           GameCharacter platform = new Platform((short)(0),(short)(screenH-20));characters.add(platform);
//           platform = new Platform((short)(20),(short)(screenH-100),(short)(screenW-20),(short)(20));characters.add(platform);
//           platform = new Platform((short)(40),(short)(screenH-180),(short)(screenW-40),(short)(20));characters.add(platform);
           
//           platform = new Platform((short)(60),(short)(screenH-260),(short)(screenW-60),(short)(20));characters.add(platform);
//           platform = new Platform((short)(80),(short)(screenH-340),(short)(screenW-80),(short)(20));characters.add(platform);
//           platform = new Platform((short)(100),(short)(screenH-420),(short)(screenW-100),(short)(20));characters.add(platform);
//           platform = new Platform((short)(120),(short)(screenH-500),(short)(screenW-120),(short)(20));characters.add(platform);
           
//           platform = new Platform((short)(0),(short)(screenH-540),(short)(120),(short)(20));characters.add(platform);
//           //platform = new Platform((short)(160),(short)(screenH-580),(short)(screenW-160),(short)(20));characters.add(platform);
           
//           GameCharacter ladder = new Ladder((short)(screenW/4),(short)(screenH-100));characters.add(ladder);
//           ladder = new Ladder((short)(3*screenW/4),(short)(screenH-180));characters.add(ladder);
           
//           ladder = new Ladder((short)(screenW/4),(short)(screenH-260));characters.add(ladder);
//           ladder = new Ladder((short)(3*screenW/4),(short)(screenH-340));characters.add(ladder);
//           ladder = new Ladder((short)(screenW/4),(short)(screenH-420));characters.add(ladder);
//           ladder = new Ladder((short)(3*screenW/4),(short)(screenH-500));characters.add(ladder);
           
//           GameCharacter enemy = new Enemy((short)1);characters.add(enemy);
//           for(short i=2;i<7;i++){enemy = new Enemy((short)i);characters.add(enemy);}
           
//           GameCharacter woodworker = new Woodworker();characters.add(woodworker);
           
//           playerCharacter = new PlayerCharacter();
//           characters.add(playerCharacter);
//           }   
//           int t=0;
//           short enemySpeed=1;
//           int level=1;
//   void draw()  {
     
//     background(backgroundColor);
//     backgroundColor=255;
//   fill(0);
//   boolean airFlag=!(playerCharacter.state instanceof LadderState) ? true:false;
//   boolean ladderFlag=false;
   
//   //if(playerCharacter.state instanceof LadderState)println("LadderState" );
   
//   for(GameCharacter gameCharacter : characters){
//     if(gameCharacter!=playerCharacter){
       
       
//         if(gameCharacter instanceof Woodworker ){
//   fill(60,42,42);
//   if (playerCharacter.checkForCollision(gameCharacter)==DOWN || playerCharacter.checkForCollision(gameCharacter)==UP||
//   playerCharacter.checkForCollision(gameCharacter)==RIGHT||playerCharacter.checkForCollision(gameCharacter)==LEFT){
//     playerCharacter.startPos();
//     level++;enemySpeed++;
//     //playerCharacter.setX((short)(screenW/2)); playerCharacter.setY((short)(screenH-25));//x=(short)(screenW/2);y=(short)(screenH-25);
//   }
//   //collision check//if (playerCharacter.checkForCollision(gameCharacter)==DOWN || playerCharacter.checkForCollision(gameCharacter)==UP)
//     //ladderFlag=true;//playerCharacter.ladderCollision=true; else playerCharacter.ladderCollision=false;    
// }
       
       
       
       
       
//       if(gameCharacter instanceof Platform ){
//       if (playerCharacter.checkForCollision(gameCharacter)==DOWN){         //println("DOWN" );
//         if((playerCharacter.getIsInTheAir()&&playerCharacter.getVy()>0)||playerCharacter.state instanceof LadderState){
//           playerCharacter.setY((short)(gameCharacter.getY()-playerCharacter.getH()));
//           playerCharacter.setIsInTheAir(false);airFlag=false;
//   }playerCharacter.setVy((short)0);
   
// }else 
// if(airFlag)playerCharacter.setIsInTheAir(true); }
 
// if(gameCharacter instanceof Ladder ){
   
//   if (playerCharacter.checkForCollision(gameCharacter)==DOWN || playerCharacter.checkForCollision(gameCharacter)==UP)
//     ladderFlag=true;//playerCharacter.ladderCollision=true; else playerCharacter.ladderCollision=false; 
   
// }
//  if(gameCharacter instanceof Enemy ){
//   fill(60,42,42);
//   if (playerCharacter.checkForCollision(gameCharacter)==DOWN || playerCharacter.checkForCollision(gameCharacter)==UP||
//   playerCharacter.checkForCollision(gameCharacter)==RIGHT||playerCharacter.checkForCollision(gameCharacter)==LEFT){
//     playerCharacter.startPos();//playerCharacter.setX((short)(screenW/2)); playerCharacter.setY((short)(screenH-25));//x=(short)(screenW/2);y=(short)(screenH-25);
//   }
//   //collision check//if (playerCharacter.checkForCollision(gameCharacter)==DOWN || playerCharacter.checkForCollision(gameCharacter)==UP)
//     //ladderFlag=true;//playerCharacter.ladderCollision=true; else playerCharacter.ladderCollision=false;    
// }
//     }
 
 
//     if(playerCharacter.getX()<0||playerCharacter.getX()+playerCharacter.getW()>screenW||
//     playerCharacter.getY()<0||playerCharacter.getY()+playerCharacter.getH()>screenH){
//       //playerCharacter.printMe();//println("startPos");
//       playerCharacter.startPos();//playerCharacter.setX((short)(screenW/2)); playerCharacter.setY((short)(screenH-25));
//     }
//     gameCharacter.update();
//     gameCharacter.drawIt();
//   }     
//   if(ladderFlag)playerCharacter.ladderCollision=true; else playerCharacter.ladderCollision=false;
//     }
   class Enemy extends GameCharacter{
     short instNum;float angle=0;
   public void drawIt(){
     fill(0xffFC7405);//fill(255,0,0);    
     ellipse(x,y,w,h);//rect(x,y,w,h);
 //pushMatrix();
 //translate(x+w/2, y+h/2);
 //rotate(angle);
 //image(barrel, 0, 0);
 //popMatrix();
 }
   public void update(){if(x<=(short)(instNum*20))vx=enemySpeed;if(x>=screenW-w)vx=(short)(-enemySpeed);x+=vx;angle+=vx;}
   public Enemy(short numOfIns){ instNum=numOfIns;w=10;h=10;x=(short)(screenW-w);y=(short)(screenH-80*(numOfIns-1)-100-10);}
   //public Ladder(short x,short y,short w,short h){super(x,y);this.w=w;this.h=h;}
 }
 abstract class GameCharacter implements Drawable{
   //public void jump(){state = new JumpingState();}
   //public void run(){state = new RunningState();}
   //public void walk(){state = new WalkingState();}
   //public void nothing(){state = new NothingState();}
   abstract public void drawIt();//{rectMode(CENTER); rect(x,y-50,20,50);}
   abstract public void update();//{x+=vx;y+=vy;state.handleInput();}
   //State state;
   short x;short y;public short getX(){return x;}public short getY(){return y;}public void setX(short x){this.x=x;}public void setY(short y){this.y=y;}
   short vx;short vy;public void setVx(short vx){this.vx=vx;}public void setVy(short vy){this.vy=vy;}public short getVx(){return vx;}public short getVy(){return vy;}
   short ax;short ay;   public void setAx(short ax){this.ax=ax;}public void setAy(short ay){this.ay=ay;}
   short w;short h;public short getW(){return w;}public short getH(){return h;}
   public GameCharacter()
   {
     //x=(short)(w/2);y=h;
     //state = new NothingState();
   }
      public GameCharacter(short x,short y)
   {
     this.x=x;this.y=y;
   }
 }
  class JumpingState extends State{
    public void removeJumpKeys(){
      activeKeys.remove('q');activeKeys.remove('e');
    }
    public void handleInput(){      
      if(activeKeys.contains('q')){        
        playerCharacter.setVy((short)(-12));//-12
        playerCharacter.setVx((short)(-walkingSpeed));
      }else if(activeKeys.contains('e')){
        playerCharacter.setVy((short)(-12));//-12
        playerCharacter.setVx((short)(walkingSpeed));
      }
      removeJumpKeys();
      playerCharacter.nothing();
    }
    public void update(){
    }
 }
  class Ladder extends GameCharacter{
   public void drawIt(){
     //fill(60,42,42);
 //rect(x,y,w,h);
 image(ladder, x, y);
 }
   public void update(){}
   public Ladder(short x,short y){super(x,y);w=10;h=80;}
   public Ladder(short x,short y,short w,short h){super(x,y);this.w=w;this.h=h;}
 }
 class LadderState extends State{public void update(){}    
 public void handleInput(){   
   short epsilon=(short)(walkingSpeed/2);
      if(activeKeys.contains('o')){playerCharacter.setY((short)(playerCharacter.getY()-epsilon));}
      if(activeKeys.contains('l'))playerCharacter.setY((short)(playerCharacter.getY()+epsilon));
    //activeKeys.remove('o');activeKeys.remove('l')}
  }
  }
public class Matrix {
  private int rows,cols;
  private double[][] data;
  public Matrix(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.data = new double [this.rows][];
    for(int i=0;i<this.rows;i++)
    this.data[i]= new double [this.cols];
  }

  public Matrix copy() {
    Matrix m = new Matrix(this.rows, this.cols);
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        m.data[i][j] = this.data[i][j];
      }
    }
    return m;
  }

  public  Matrix fromArray(double[] arr) {
    Matrix m = new Matrix(arr.length, 1);
    for (int i = 0; i < m.rows; i++) {
        m.data[i][0] = arr[i];
    }
    return m;
  }

  public  Matrix subtract(Matrix a, Matrix b) {
    if (a.rows != b.rows || a.cols != b.cols) {
      System.out.println("Columns and Rows of A must match Columns and Rows of B.");
      return null;
    }

    // Return a new Matrix a-b
    Matrix ret = new Matrix(a.rows, a.cols);
        for (int i = 0; i < a.rows; i++) {
      for (int j = 0; j < a.cols; j++) {
        ret.data[i][j] = a.data[i][j]-b.data[i][j];
      }
    }
    return ret;
  }

  public double[] toArray() {
    double[] arr = new double[this.rows*this.cols];
    int index=0;
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        arr[index]=this.data[i][j];
        index++;
      }
    }
    return arr;
  }

  public void randomize() {
        for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        this.data[i][j]=random((float)1)*2-1;
      }
    }
  }
  public void add(Matrix m){
          if (this.rows != m.rows || this.cols != m.cols) {
        System.out.println("Columns and Rows of A must match Columns and Rows of B.");
        return;
      }
      for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        this.data[i][j]+=m.data[i][j];
      }
    }
  }
    public void add(double m){
      for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        this.data[i][j]+=m;
      }
    }
  }

  public Matrix transpose(Matrix m) {
    Matrix ret = new Matrix(m.cols, m.rows);
    for (int i = 0; i < ret.rows; i++) {
      for (int j = 0; j < ret.cols; j++) {
        ret.data[i][j]=m.data[j][i];
      }
    }
      return ret;
  }
  
  private double wierzRazyKulumna(int i,int j,Matrix a,Matrix b){
    double ret=0;
    for (int k = 0; k < a.cols; k++)ret+=a.data[i][k]*b.data[k][j];     
    return ret;
  }

  public Matrix multiply(Matrix a, Matrix b) {
    // Matrix product
    if (a.cols != b.rows) {
      System.out.println("Columns of A must match rows of B.");
      return null;
    }
    Matrix ret = new Matrix(a.rows, b.cols);
    for (int i = 0; i < ret.rows; i++) {
      for (int j = 0; j < ret.cols; j++) {
        double sum=0;
        ret.data[i][j]=wierzRazyKulumna(i,j,a,b);
      }
    }

      return ret;
  }

public void multiply(Matrix n) {
      if (this.rows != n.rows || this.cols != n.cols) {
        System.out.println("Columns and Rows of A must match Columns and Rows of B.");
        return;      
      }
     for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        double sum=0;
        this.data[i][j]*=n.data[i][j];
      }
    }
      
}

public void multiply(double a) {
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        this.data[i][j]*=a;
      }
    }
}
public double maxArg(double a,double b){
  return a>=b?a:b;
}
public void activate(int whichFunc){
  //x => 1 / (1 + Math.exp(-x))
  if(0==whichFunc){
      for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        this.data[i][j] = 0>=this.data[i][j]?0:this.data[i][j];//1.0 / (1 + Math.exp(-this.data[i][j]));//0>=this.data[i][j]?0:this.data[i][j];
      }
    }
  }  
  //y => y * (1 - y)
}

public Matrix dActivate(Matrix m,int whichFunc){
  Matrix ret = m.copy();
  if(0==whichFunc){
      for (int i = 0; i < ret.rows; i++) {
      for (int j = 0; j < ret.cols; j++) {
        ret.data[i][j] = ret.data[i][j]*(1-ret.data[i][j]);
      }
    }
  }  
  //y => y * (1 - y)
  return ret;
}

  //map(func) {
  //  // Apply a function to every element of matrix
  //  for (let i = 0; i < this.rows; i++) {
  //    for (let j = 0; j < this.cols; j++) {
  //      let val = this.data[i][j];
  //      this.data[i][j] = func(val, i, j);
  //    }
  //  }
  //  return this;
  //}

  //static map(matrix, func) {
  //  // Apply a function to every element of matrix
  //  return new Matrix(matrix.rows, matrix.cols)
  //    .map((e, i, j) => func(matrix.data[i][j], i, j));
  //}
  
    public void randomChange(float d) {
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        double val = random(d)*2-d;
        this.data[i][j]+=val;
        if(this.data[i][j]>1)this.data[i][j]=1;
        if(this.data[i][j]<-1)this.data[i][j]=-1;
      }
    }
  }

  public void print() {
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        System.out.printf("%f\t",this.data[i][j]);
      }
      System.out.println();
    }
  }
}
  /*
serialize() {return JSON.stringify(this);}

  static deserialize(data) {
    if (typeof data == 'string') {
      data = JSON.parse(data);
    }
    let matrix = new Matrix(data.rows, data.cols);
    matrix.data = data.data;
    return matrix;
  }
}

if (typeof module !== 'undefined') {
  module.exports = Matrix;
}
*/







 
 public class MusicPlayer{
   
   String[] arr;
 
MediaPlayer snd = new MediaPlayer();
//AssetManager assets = this.getAssets();
AssetFileDescriptor fd;
Context context;
Activity act;
public MusicPlayer(Activity act){
    this.act = act;
    arr = new String[3];
    arr[0]="bensound-groovyhiphop.mp3";//playing
    arr[1]="die.mp3";//lost
    arr[2]="win.mp3";//won
  context = act.getApplicationContext();

}
public void play(int i){
  if (!snd.isPlaying()||i!=0) {
    try {
            
      //if(snd.isPlaying())
    {snd.stop(); snd.release();snd=null;snd=new MediaPlayer();}//snd.reset();}
      fd = context.getAssets().openFd(arr[i]);
      snd.setDataSource(fd.getFileDescriptor(),fd.getStartOffset(), fd.getLength());
      
      snd.prepare();
      snd.start();      
      //fd = context.getAssets().openFd("Ni-"+(int)random(1, 22)+".mp3");
      //snd.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
 
    }catch(Exception e){
      System.out.println(e);
      e.printStackTrace();
  }
  
}
 }
 
 }

class NeuralNetwork {
  private Matrix weights_ih,weights_ho,bias_h,bias_o;
  private int input_nodes,hidden_nodes,output_nodes; 
  double learning_rate;
  
    public NeuralNetwork(NeuralNetwork a) {
      this.input_nodes = a.input_nodes;
      this.hidden_nodes = a.hidden_nodes;
      this.output_nodes = a.output_nodes;

      this.weights_ih = a.weights_ih.copy();
      this.weights_ho = a.weights_ho.copy();

      this.bias_h = a.bias_h.copy();
      this.bias_o = a.bias_o.copy();
    }     
    
  public NeuralNetwork(int a, int b, int c) {
      this.input_nodes = a;
      this.hidden_nodes = b;
      this.output_nodes = c;

      this.weights_ih = new Matrix(this.hidden_nodes, this.input_nodes);
      this.weights_ho = new Matrix(this.output_nodes, this.hidden_nodes);
      this.weights_ih.randomize();
      this.weights_ho.randomize();

      this.bias_h = new Matrix(this.hidden_nodes, 1);
      this.bias_o = new Matrix(this.output_nodes, 1);
      this.bias_h.randomize();
      this.bias_o.randomize();


    // TODO: copy these as well
    this.setLearningRate();
    //this.setActivationFunction();


  }

  public double[] predict(double[] input_array) {
    Matrix tmp = new Matrix(1,1);
    // Generating the Hidden Outputs
    Matrix inputs = tmp.fromArray(input_array);
    Matrix hidden = tmp.multiply(this.weights_ih, inputs);
    hidden.add(this.bias_h);
    // activation function!
    hidden.activate(0);

    // Generating the output's output!
    Matrix output = tmp.multiply(this.weights_ho, hidden);
    output.add(this.bias_o);
    output.activate(0);

    // Sending back to the caller!
    return output.toArray();
  }

  public void setLearningRate(double learning_rate) {
    this.learning_rate = learning_rate;
  }
    public void setLearningRate() {
    this.learning_rate = 0.1f;
  }









  //public void train(double[] input_array, double[] target_array) {
  //  Matrix tmp = new Matrix(1,1);
  //  // Generating the Hidden Outputs
  //  Matrix inputs = tmp.fromArray(input_array);
  //  Matrix hidden = tmp.multiply(this.weights_ih, inputs);
  //  hidden.add(this.bias_h);
  //  // activation function!
  //  hidden.activate(0);

  //  // Generating the output's output!
  //  Matrix outputs = tmp.multiply(this.weights_ho, hidden);
  //  outputs.add(this.bias_o);
  //  outputs.activate(0);

  //  // Convert array to matrix object
  //  Matrix targets = tmp.fromArray(target_array);

  //  // Calculate the error
  //  // ERROR = TARGETS - OUTPUTS
  //  Matrix output_errors = tmp.subtract(targets, outputs);

  //  // let gradient = outputs * (1 - outputs);
  //  // Calculate gradient
  //  Matrix gradients = tmp.dActivate(hidden, 0);
  //  gradients.multiply(output_errors);
  //  gradients.multiply(this.learning_rate);


  //  // Calculate deltas
  //  Matrix hidden_T = tmp.transpose(hidden);
  //  Matrix weight_ho_deltas = tmp.multiply(gradients, hidden_T);

  //  // Adjust the weights by deltas
  //  this.weights_ho.add(weight_ho_deltas);
  //  // Adjust the bias by its deltas (which is just the gradients)
  //  this.bias_o.add(gradients);

  //  // Calculate the hidden layer errors
  //  Matrix who_t = tmp.transpose(this.weights_ho);
  //  Matrix hidden_errors = tmp.multiply(who_t, output_errors);

  //  // Calculate hidden gradient
  //  Matrix hidden_gradient = tmp.dActivate(hidden, 0);
  //  hidden_gradient.multiply(hidden_errors);
  //  hidden_gradient.multiply(this.learning_rate);

  //  // Calcuate input->hidden deltas
  //  Matrix inputs_T = tmp.transpose(inputs);
  //  Matrix weight_ih_deltas = tmp.multiply(hidden_gradient, inputs_T);

  //  this.weights_ih.add(weight_ih_deltas);
  //  // Adjust the bias by its deltas (which is just the gradients)
  //  this.bias_h.add(hidden_gradient);

  //  // outputs.print();
  //  // targets.print();
  //  // error.print();
  //}
  
  
  
  
  
  
  
  
  
  

  //serialize() {
  //  return JSON.stringify(this);
  //}

  //static deserialize(data) {
  //  if (typeof data == 'string') {
  //    data = JSON.parse(data);
  //  }
  //  let nn = new NeuralNetwork(data.input_nodes, data.hidden_nodes, data.output_nodes);
  //  nn.weights_ih = Matrix.deserialize(data.weights_ih);
  //  nn.weights_ho = Matrix.deserialize(data.weights_ho);
  //  nn.bias_h = Matrix.deserialize(data.bias_h);
  //  nn.bias_o = Matrix.deserialize(data.bias_o);
  //  nn.learning_rate = data.learning_rate;
  //  return nn;
  //}


  // Adding function for neuro-evolution
  public NeuralNetwork copy() {
    return new NeuralNetwork(this);
  }

  // Accept an arbitrary function for mutation
  public void mutate(float d) {
    //boolean print = random(1)<1.0/60?true:false;
    //if(print)this.weights_ih.print();
    this.weights_ih.randomChange(d);
    //if(print){this.weights_ih.print();println();}
    
    this.weights_ho.randomChange(d);
    this.bias_h.randomChange(d);
    this.bias_o.randomChange(d);
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
    public void train(double[] input_array,double[]  target_array) {
    Matrix tmp = new Matrix(1,1);
    // Generating the Hidden Outputs
    Matrix inputs = tmp.fromArray(input_array);
    //inputs=tmp.transpose(inputs);
    //println(this.weights_ih.cols + " == "+inputs.rows + "\t"+this.weights_ih.rows + "      "+inputs.cols);
    Matrix hidden = tmp.multiply(this.weights_ih, inputs);
    //this.bias_h=tmp.transpose(this.bias_h);
    //println(hidden.rows + " "+ hidden.cols+ "\t== "+this.bias_h.rows + "      "+this.bias_h.cols);
    hidden.add(this.bias_h);
    //println("after adding");
    // activation function!
    hidden.activate(0);

    // Generating the output's output!
    Matrix outputs = tmp.multiply(this.weights_ho, hidden);
    outputs.add(this.bias_o);
    outputs.activate(0);

    // Convert array to matrix object
    Matrix targets = tmp.fromArray(target_array);

    // Calculate the error
    // ERROR = TARGETS - OUTPUTS
    Matrix output_errors = tmp.subtract(targets, outputs);
    println(""+output_errors.data[0][0]);

    // let gradient = outputs * (1 - outputs);
    // Calculate gradient
    Matrix gradients = tmp.dActivate(outputs, 0);
    gradients.multiply(output_errors);
    gradients.multiply(this.learning_rate);


    // Calculate deltas
    Matrix hidden_T = tmp.transpose(hidden);
    Matrix weight_ho_deltas = tmp.multiply(gradients, hidden_T);

    // Adjust the weights by deltas
    this.weights_ho.add(weight_ho_deltas);
    // Adjust the bias by its deltas (which is just the gradients)
    this.bias_o.add(gradients);

    // Calculate the hidden layer errors
    Matrix who_t = tmp.transpose(this.weights_ho);
    Matrix hidden_errors = tmp.multiply(who_t, output_errors);

    // Calculate hidden gradient
    Matrix hidden_gradient = tmp.dActivate(hidden, 0);
    hidden_gradient.multiply(hidden_errors);
    hidden_gradient.multiply(this.learning_rate);

    // Calcuate input->hidden deltas
    Matrix inputs_T = tmp.transpose(inputs);
    Matrix weight_ih_deltas = tmp.multiply(hidden_gradient, inputs_T);

    this.weights_ih.add(weight_ih_deltas);
    // Adjust the bias by its deltas (which is just the gradients)
    this.bias_h.add(hidden_gradient);

    // outputs.print();
    // targets.print();
    // error.print();
  }


}
class NeuroEvolution
{
  //boolean wholeGeneration=false;
  NeuralNetwork[] pop;
  double[] score;
  public NeuroEvolution(int in,int hi,int ou,int popSize){
    pop = new NeuralNetwork[popSize];
    score = new double[pop.length];
    for(int i=0;i<pop.length;i++){
      pop[i] = new NeuralNetwork(in,hi,ou);
    }
  }int curIndex=-1;
  
  public NeuralNetwork getNext(){
    curIndex++;if(curIndex==pop.length){curIndex=0;evalNextGen();}
    //System.out.println("getNext : "+curIndex);    
    return pop[curIndex];
  }
  
  public void setScore(double score){this.score[curIndex]=score;}//System.out.println("scoring : "+curIndex);} 
  
  public void printArr(double[] arr){for(int i=0;i<arr.length;i++)System.out.printf("%f\t",arr[i]);println();
}
  
  double bestScoreEver=-Double.MAX_VALUE;
  double curBest=-Double.MAX_VALUE;
  
  public void evalNextGen(){
    System.out.println("nextGen!");
    //curIndex=-1;
    printArr(score);
    double bestScore=score[0];int bestScoreIndex=0;for(int i=0;i<pop.length;i++){if(bestScore<score[i]){bestScore=score[i];bestScoreIndex=i;}}
    curBest=bestScore;
    if(bestScoreEver<bestScore){bestScoreEver=bestScore;pop[0]=pop[bestScoreIndex].copy();}
    for(int i=1;i<pop.length;i++){pop[i]=pop[0].copy();pop[i].mutate(1.0f);}
  }
}
 class NothingState extends State{
    public void handleInput(){    
      clearMove();
      playerCharacter.setVx((short)(0));
      playerCharacter.setVy((short)(0));
    }
    public void update(){}
 }
 class Platform extends GameCharacter{
   public void drawIt(){image(platform, x, y,w,h);
 //rect(x,y,w,h);
 }
   public void update(){}
   public Platform(short x,short y){super(x,y);w=screenW;h=20;}
   public Platform(short x,short y,short w,short h){super(x,y);this.w=w;this.h=h;}
 }
  class PlayerCharacter extends GameCharacter implements Jumpable{
    boolean dead=false;
    public String playerAsString(){return "x:"+x+", y:"+y;}
   public void jump(){state = new JumpingState();}
   //public void run(){state = new RunningState();}
   public void walk(){state = new WalkingState();}
   public void nothing(){state = new NothingState();}
   public void ladder(){state = new LadderState();}
   PImage lady = ladyLeft;
   public void drawIt(){//rectMode(CENTER); 
 //fill(255,0,100);rect(x,y,w*5,h*5);fill(0,0,0);
 int scale=1;
 int displayedw=lady.width*scale;int displayedh=lady.height*scale;
 image(lady, x-displayedw+10, y-displayedh+10,displayedw,displayedh);
 }
   public Character checkForCollision(GameCharacter gameCharacter){
     
     //collision under playerCharacter
     short epsilon=9;
     
     if(gameCharacter instanceof Ladder){       
     if(!(x+w<gameCharacter.getX()||x>gameCharacter.getX()+gameCharacter.getW())){
              if(!(y+h<gameCharacter.getY()||y>gameCharacter.getY()+gameCharacter.getH()))
              {
                //println("Ladder collision");
   return DOWN;
              }
     }
   }
     
     
     if(vy>0)
     if(x+w>=gameCharacter.getX()&&x<=gameCharacter.getX()+gameCharacter.getW())
       if(y+h<=gameCharacter.getY()+epsilon &&y+h>=gameCharacter.getY()-epsilon)//||x>gameCharacter.getX()+gameCharacter.getW())
       {
   return DOWN;   
       }
       if(x+w>=gameCharacter.getX()&&x<=gameCharacter.getX()+gameCharacter.getW())
       if(gameCharacter.getY()<=y+h+epsilon &&gameCharacter.getY()>=y+h-epsilon)//||x>gameCharacter.getX()+gameCharacter.getW())
       {
   return UP;   
       }
       if(y+h>=gameCharacter.getY()&&y<=gameCharacter.getY()+gameCharacter.getH())
       if(gameCharacter.getX()<=x+w+epsilon &&gameCharacter.getX()>=x+w-epsilon)//||x>gameCharacter.getX()+gameCharacter.getW())
       {
   return RIGHT;   
       }
              if(y+h>=gameCharacter.getY()&&y<=gameCharacter.getY()+gameCharacter.getH())
       if(gameCharacter.getX()+gameCharacter.getW()<=x+w+epsilon &&gameCharacter.getX()+gameCharacter.getW()>=x+w-epsilon)//||x>gameCharacter.getX()+gameCharacter.getW())
       {
   return LEFT;   
       }
   return 0;
 } boolean ladderCollision=false;
   public void update(){
     if(isInTheAir){
       //if(vy<=0)
       vy+=gravity;
       x+=vx;y+=vy;
       if(x<0)x=0;if(x>screenW-w)x=(short)(screenW-w);
       return;
     }//vy=0;
     if((ladderCollision && (activeKeys.contains('o')||activeKeys.contains('l')))||
     ((state instanceof LadderState)&&activeKeys.size()==0)
     )playerCharacter.ladder();else 
   //if(activeKeys.contains('q')||activeKeys.contains('e'))playerCharacter.run();
   //else 
   if(activeKeys.contains('a')||activeKeys.contains('d'))playerCharacter.walk();
   else if(activeKeys.contains('q')||activeKeys.contains('e'))playerCharacter.jump();
   else playerCharacter.nothing();
   x+=vx;y+=vy;state.handleInput();
   if(x<0)x=0;if(x+w>screenW)x=(short)(screenW-w);
   
 }
   State state;
 boolean isInTheAir=true; public boolean getIsInTheAir(){return isInTheAir;}public void setIsInTheAir(boolean isInTheAir){this.isInTheAir= isInTheAir;}
   //short x;short y;short vx;short vy;short ax;short ay;
   //public void setVx(short vx){this.vx=vx;}public void setVy(short vy){this.vy=vy;}
   public PlayerCharacter()
   {
     w=2;h=2;
     x=(short)(screenW/2);y=(short)(screenH-25);
     state = new NothingState();
   }
   public void startPos(){
     isInTheAir=true;
     x=(short)(screenW/2);y=(short)(screenH-25);vx=vy=(short)0;
     state = new NothingState();
   }
   public void printMe(){println("x:"+x+"y:"+y+"vx:"+vx+"vy:"+vy);}
 }
 abstract class State {
   abstract public void handleInput();
   abstract public void update();
 }
 short walkingSpeed=4;
 class WalkingState extends State{
    public void handleInput(){
      if(activeKeys.contains('a')){
        //activeKeys.remove('a');
        playerCharacter.setVx((short)(-walkingSpeed));
      }
      else if(activeKeys.contains('d')){
        //activeKeys.remove('d');
        playerCharacter.setVx((short)(walkingSpeed));
      }
      else {}
        //playerCharacter.nothing();
    }
    public void update(){
    }
 }
    class Woodworker extends GameCharacter{
   public void drawIt(){//fill(0,0,255); 
    int scale=1;
 int displayedw=woodworker.width*scale;int displayedh=woodworker.height*scale;
 image(woodworker, x-displayedw+10, y-displayedh+10,displayedw,displayedh);
 }
   //println(x+", "+y);
   //rect(x,y,w,h);}
   public void update(){}
   public Woodworker(){ super((short)(0),(short)(screenH-550));w=10;h=10;}
   //public Ladder(short x,short y,short w,short h){super(x,y);this.w=w;this.h=h;}
 }
}
