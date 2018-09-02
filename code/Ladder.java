  class Ladder extends GameCharacter{
   public void drawIt(){
 image(ladder, x, y);
 }
   public void update(){}
   public Ladder(short x,short y){super(x,y);w=10;h=80;}
   public Ladder(short x,short y,short w,short h){super(x,y);this.w=w;this.h=h;}
 }