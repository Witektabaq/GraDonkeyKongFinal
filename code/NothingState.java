 class NothingState extends State{
    public void handleInput(){    
      clearMove();
      playerCharacter.setVx((short)(0));
      playerCharacter.setVy((short)(0));
    }
    public void update(){}
 }