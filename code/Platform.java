 class Platform extends GameCharacter{
   public void drawIt(){image(platform, x, y,w,h);
 }
   public void update(){}
   public Platform(short x,short y){super(x,y);w=screenW;h=20;}
   public Platform(short x,short y,short w,short h){super(x,y);this.w=w;this.h=h;}
 }