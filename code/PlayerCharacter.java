  class PlayerCharacter extends GameCharacter implements Jumpable{
    boolean dead=false;
    public String playerAsString(){return "x:"+x+", y:"+y;}
   public void jump(){state = new JumpingState();}
   public void walk(){state = new WalkingState();}
   public void nothing(){state = new NothingState();}
   public void ladder(){state = new LadderState();}
   PImage lady = ladyLeft;
   public void drawIt(){//rectMode(CENTER); 
 int scale=1;
 int displayedw=lady.width*scale;int displayedh=lady.height*scale;
 image(lady, x-displayedw+10, y-displayedh+10,displayedw,displayedh);
 }
   public Character checkForCollision(GameCharacter gameCharacter){
     
     short epsilon=9;
     
     if(gameCharacter instanceof Ladder){       
     if(!(x+w<gameCharacter.getX()||x>gameCharacter.getX()+gameCharacter.getW())){
              if(!(y+h<gameCharacter.getY()||y>gameCharacter.getY()+gameCharacter.getH()))
              {
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
       vy+=gravity;
       x+=vx;y+=vy;
       if(x<0)x=0;if(x>screenW-w)x=(short)(screenW-w);
       return;
     }//vy=0;
     if((ladderCollision && (activeKeys.contains('o')||activeKeys.contains('l')))||
     ((state instanceof LadderState)&&activeKeys.size()==0)
     )playerCharacter.ladder();else 

   if(activeKeys.contains('a')||activeKeys.contains('d'))playerCharacter.walk();
   else if(activeKeys.contains('q')||activeKeys.contains('e'))playerCharacter.jump();
   else playerCharacter.nothing();
   x+=vx;y+=vy;state.handleInput();
   if(x<0)x=0;if(x+w>screenW)x=(short)(screenW-w);
   
 }
   State state;
 boolean isInTheAir=true; boolean getIsInTheAir(){return isInTheAir;}void setIsInTheAir(boolean isInTheAir){this.isInTheAir= isInTheAir;}

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