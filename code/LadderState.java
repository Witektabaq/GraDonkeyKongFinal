 class LadderState extends State{public void update(){}    
 public void handleInput(){   
   short epsilon=(short)(walkingSpeed/2);
      if(activeKeys.contains('o')){playerCharacter.setY((short)(playerCharacter.getY()-epsilon));}
      if(activeKeys.contains('l'))playerCharacter.setY((short)(playerCharacter.getY()+epsilon));
  }
  }