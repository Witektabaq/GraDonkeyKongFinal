 abstract class GameCharacter implements Drawable{
   abstract public void drawIt();//{rectMode(CENTER); rect(x,y-50,20,50);}
   abstract public void update();//{x+=vx;y+=vy;state.handleInput();}
   short x;short y;public short getX(){return x;}public short getY(){return y;}public void setX(short x){this.x=x;}public void setY(short y){this.y=y;}
   short vx;short vy;public void setVx(short vx){this.vx=vx;}public void setVy(short vy){this.vy=vy;}public short getVx(){return vx;}public short getVy(){return vy;}
   short ax;short ay;   public void setAx(short ax){this.ax=ax;}public void setAy(short ay){this.ay=ay;}
   short w;short h;public short getW(){return w;}public short getH(){return h;}
   public GameCharacter()
   {
   }
      public GameCharacter(short x,short y)
   {
     this.x=x;this.y=y;
   }
 }