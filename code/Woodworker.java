    class Woodworker extends GameCharacter{
   public void drawIt(){//fill(0,0,255); 
    int scale=1;
 int displayedw=woodworker.width*scale;int displayedh=woodworker.height*scale;
 image(woodworker, x-displayedw+10, y-displayedh+10,displayedw,displayedh);
 }
   public void update(){}
   public Woodworker(){ super((short)(0),(short)(screenH-550));w=10;h=10;}
 }