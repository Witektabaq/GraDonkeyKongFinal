import android.media.MediaPlayer;
import  android.content.res.Resources;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.Context;
import android.app.Activity;
import android.os.Bundle;
 
 public class MusicPlayer{
   
   String[] arr;
 
MediaPlayer snd = new MediaPlayer();
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
    {snd.stop(); snd.release();snd=null;snd=new MediaPlayer();}//snd.reset();}
      fd = context.getAssets().openFd(arr[i]);
      snd.setDataSource(fd.getFileDescriptor(),fd.getStartOffset(), fd.getLength());
      
      snd.prepare();
      snd.start();      
 
    }catch(Exception e){
      System.out.println(e);
      e.printStackTrace();
  }
  
}
 }
 
 }