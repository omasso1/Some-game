class Gracz{
 float x;
 float y;
 float vx;
 float vy;
 float r;
 float hitR;
 float poczatkoweZycie;
 float zycie;
 
 boolean isRIGHT;
 boolean isLEFT;
 boolean isUP;
 boolean isDOWN;
 ArrayList<Bonus> buff; 
 ArrayList<Pocisk> debuff;
 
 
 Gracz(){
  x=width/2;
  y=height/2;
  vx=4.5;
  vy=4.5;
  r=12;
  hitR=r;
  buff = new ArrayList<Bonus>();
  debuff =new ArrayList<Pocisk>();
  zycie = poczatkoweZycie = 100;
 }
 
 
 
 void setMove(int k, boolean r){
   switch(k){
    case UP:     isUP=r;      break;
    case DOWN:   isDOWN=r;    break;
    case LEFT:   isLEFT=r;    break;
    case RIGHT:  isRIGHT=r;   break;
    default:                  break;
   }
 }
 
  void move(){   
    if(isRIGHT) x+=vx;
    if(isLEFT)  x-=vx;
    if(isUP)    y-=vy;
    if(isDOWN)  y+=vy;
  }
  
  void rysuj(){
    fill(255);
    stroke(255);
   ellipse(x,y,r,r); 
   rysujBuff();
   rysujHP();
  }
  
  boolean hit(){
   for(int i=Pociski.size()-1;i>=0;i--){
     Pocisk po = Pociski.get(i);
     for(int j=0;j<2;j++){
       float x1 = po.punkty[j*2].x+po.poz.x;
       float y1 = po.punkty[j*2].y+po.poz.y;
       float x2 = po.punkty[j*2+1].x+po.poz.x;
       float y2 = po.punkty[j*2+1].y+po.poz.y;
       float u = ((x2-x1)*(this.x-x1) + (y2-y1)*(this.y-y1))/( (x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
       
       float x3 = x1 + u*(x2-x1);
       float y3 = y1 + u*(y2-y1);
      
        if(u>=1){
          x3=x2;
          y3=y2;
         }
       else if(u<=0){
         x3=x1;
         y3=y1;
         }
       if(dystans(x3,y3,this.x,this.y)<this.r/2){
       Pociski.remove(i);
       }
     if(dystans(x3,y3,this.x,this.y)<this.hitR/2){
       debuff.add(po);
       po.efekt();
       zycie-=po.obrazenia;
       return true;
     }
   }
  }
  return false;
 }
 void rysujBuff(){
  for(int i=buff.size()-1;i>=0;i--){
    Bonus b = buff.get(i);
    b.rysujEfekt();
  }
 }
 
  void rysujDebuff(){
    for(int i=debuff.size()-1;i>=0;i--){
    Pocisk b = debuff.get(i);
    b.rysujEfekt();
    }
 }
 
 void rysujHP(){
   fill(0,255,0);
   noStroke();
   rect(5,5,(width-10)*(zycie/poczatkoweZycie),10);
 }
 
 void hitBonus(){
   for(int i=Bonusy_gra.size()-1;i>=0;i--){
     Bonus b = Bonusy_gra.get(i);
  if(dystans(this.x,this.y,b.x,b.y)<b.r+1){
    punkty+=100;
    b.efekt();
    buff.add(b);
    Bonusy_gra.remove(i);
     }
   }
 }
 
 void sprawdzBuff(){
  for(int i=buff.size()-1;i>=0;i--){
   Bonus b = buff.get(i);
   if(b.czasBuffa<=0){
    b.cofnijEfekt();
    buff.remove(i);
   }
  }
 }
 
 void sprawdzDebuff(){
  for(int i=debuff.size()-1;i>=0;i--){
    Pocisk p = debuff.get(i);
    p.czasTrwania--;
    if(p.czasTrwania<=0){
      p.cofnijEfekt();
      debuff.remove(i);
    }
  }
 }
 
 void sprawdzSciany(){
  if(x<r) x=r;
  else if(x>width-r) x=width-r;
    if(y<r) y=r;
  else if(y>height-r) y=height-r;
 }
 
 
}
 float dystans(float x1, float y1, float x2, float y2){
   return sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
 }