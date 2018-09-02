 short walkingSpeed=4;
 class WalkingState extends State{
    public void handleInput(){
      if(activeKeys.contains('a')){
        playerCharacter.setVx((short)(-walkingSpeed));
      }
      else if(activeKeys.contains('d')){
        playerCharacter.setVx((short)(walkingSpeed));
      }
      else {}
    }
    public void update(){
    }
 }