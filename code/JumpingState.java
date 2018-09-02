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