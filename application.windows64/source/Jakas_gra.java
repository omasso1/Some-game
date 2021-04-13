import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Jakas_gra extends PApplet {

PImage pomoc2;
PImage pomoc1;
PImage poziom_trudnosci;
float punkty;
float poziom;
Gracz p;
ArrayList<Pocisk> Pociski;
ArrayList<Bonus> Bonusy_gra;
boolean Menu;
boolean Pomoc1;
boolean Pomoc2;
boolean PoziomTrudnosci;
boolean Przegrana;
public void setup(){
 
 background(51);
  punkty=0;
  p= new Gracz();
  Pociski = new ArrayList <Pocisk>();
  Bonusy_gra = new ArrayList<Bonus>();
  frameRate(60);
  frameRate= 60;
  Menu=true;
  Pomoc1=false;
  Pomoc2 = false;
  PoziomTrudnosci = false;
  Przegrana=false;
  pomoc2 = loadImage("pomoc.png");
  pomoc1 = loadImage("Pomoc1.png");
  poziom_trudnosci = loadImage("poziom_trudnosci.png");
  poziom=2;
}


/**********************************/

public void draw(){
  if(Menu==true)
MENU();
  else if(Pomoc2==true)
Pomoc2();
  else if(Pomoc1==true)
Pomoc1();
 else if(PoziomTrudnosci ==true)
PoziomTrudnosci();
  
else{
  background(51);
respBuffow();
respPociskow();
ruch();
sprawdz();
rysuj();
if(poziom!=4)
punkty+=0.25f*poziom;
else
punkty+=1.25f;
}
}

/**********************************/

public void keyPressed(){
 p.setMove(keyCode, true);
 //noLoop();
}

public void keyReleased(){
  //loop();
 p.setMove(keyCode,false);
}

public void mouseReleased(){
  if(Menu==true){
  if( mouseX>210 && mouseX<510 &&
  mouseY>220 && mouseY<265){
  Menu=false;
  start_();
  }
  else if(mouseX >120 && mouseX<510 &&
  mouseY>300 && mouseY<345){
  Pomoc1=true;
Menu=false;  
}
   else if(mouseX >120 && mouseX<510 &&
  mouseY>380 && mouseY<425){
    Menu=false;
  PoziomTrudnosci=true;
 }
  }
  

 else if(PoziomTrudnosci ==true){
    if(mouseX > 145 && mouseX < 355 &&
   mouseY > 200 && mouseY <245){
    poziom = 1;
    PoziomTrudnosci = false;
    Menu=true;
   }
   
   else if(mouseX > 145 && mouseX < 355 &&
   mouseY > 300 && mouseY <345){
    poziom = 2;
    PoziomTrudnosci = false;
    Menu=true;
   }
   
   else if(mouseX > 145 && mouseX < 355 &&
   mouseY > 400 && mouseY <445){
    poziom = 3;
    PoziomTrudnosci = false;
    Menu=true;
   }
   
   else if(mouseX > 145 && mouseX < 355 &&
   mouseY > 500 && mouseY <545){
    poziom = 4;
    PoziomTrudnosci = false;
    Menu=true;
   }
 }
  
  
 else  if(Pomoc1==true){
   Pomoc1=false;
   Pomoc2=true;
 }
 
 else if(Pomoc2 ==true){
   if(mouseX>120 && mouseX<320 &&
   mouseY > 520 && mouseY<565){
    Pomoc2=false;
    Menu=true;
   }
   if(mouseX>400 && mouseX<600 &&
   mouseY > 520 && mouseY<565){
   Pomoc2=false;
   start_();
 }
 }
 else if(Przegrana ==true){
   loop();
   Przegrana=false;
   Menu=true;
 }
}

public void ruch(){
 p.move();
 for(Pocisk po : Pociski){
   po.move();
 }
 
}

public void rysuj(){
  p.rysuj();
  wyswietlPunkty();
 for(Pocisk po : Pociski){
  po.rysuj(); 
 }
 for(Bonus b : Bonusy_gra){
   b.rysuj();
 }
}

public void sprawdz(){ 
  if(p.hit()){
   if(p.zycie<=0){
    przegrana();
   }
  }
 
 
 zycieBonusow();
 zyciePociskow();
 p.sprawdzSciany();
 p.hitBonus();
 p.sprawdzBuff();
 p.sprawdzDebuff();
 }



public void reset(){
 for(int i=Pociski.size()-1;i>=0;i--){
  Pociski.remove(i); 
 }
 for(int i=Bonusy_gra.size()-1;i>=0;i--){
  Bonusy_gra.remove(i); 
 }
 for(int i=p.buff.size()-1;i>=0;i--){
  Bonus b =p.buff.get(i);
  b.cofnijEfekt();
  p.buff.remove(i); 
 }
  for(int i=p.debuff.size()-1;i>=0;i--){
  Pocisk b =p.debuff.get(i);
  b.cofnijEfekt();
  p.debuff.remove(i); 
 }
 p.zycie = p.poczatkoweZycie;
 
}

public void zyciePociskow(){
 for(int i=Pociski.size()-1;i>=0;i--){
 if(Pociski.get(i).usun<=0){
   Pociski.remove(i); 
 }
 }
}

public void zycieBonusow(){
 for(int i=Bonusy_gra.size()-1;i>=0;i--){
   Bonus b = Bonusy_gra.get(i);
   b.czasZycia--;
   if(b.czasZycia<=0) Bonusy_gra.remove(i);
 }
 
}

public void wyswietlPunkty(){
  textAlign(BASELINE);
  fill(255);
   String p = "Punkty: ";
   p += floor(punkty);
   textSize(20);
   text(p,10,50);
}

public void przegrana(){
  reset();
  Przegrana=true;
  p.x =width/2-10;
  p.y = height/2+20;
 background(51);
 fill(255,0,0);
 noStroke();
 rect(width/2-160,height/2-110,300,200);
 fill(0);
 String k = "Przegrale\u015b";
 String p = "Punkty: " + PApplet.parseInt(punkty);
 text(k,width/2-60,height/2-20);
 text(p,width/2-60,height/2);
 noLoop();
}

public void respBuffow(){
 if(random(1)<0.001f){
   Bonusy_gra.add(new zmniejszRozmiar()); 
  }
   if(random(1)<0.001f){
   Bonusy_gra.add(new Speed()); 
  }
   if(random(1)<0.001f){
   Bonusy_gra.add(new Tarcza()); 
  }
   if(random(1)<0.001f){
   Bonusy_gra.add(new Apteczka()); 
  } 
}

public void respPociskow(){
  if(poziom!=4){
  if(random(1)<0.1f){
  Pociski.add(new Pocisk());
  }
    if(random(1)<0.01f*poziom){
  Pociski.add(new PociskObrazenia());
  }
    if(random(1)<0.01f*poziom){
  Pociski.add(new PociskZwieksz());
  }
    if(random(1)<0.01f*poziom){
  Pociski.add(new PociskSlow());
   }
  }
  else{
    
    if(random(1)<0.01f*poziom/2){
  Pociski.add(new PociskObrazenia());
  }
    if(random(1)<0.01f*poziom/2){
  Pociski.add(new PociskZwieksz());
  }
    if(random(1)<0.2f){
  Pociski.add(new PociskSlow());
   } 
  }
  
}
public void start_(){
  punkty=0;
  Pomoc2=false;
  Menu=false;
 for(int i=0;i<20;i++){
  respPociskow(); 
 }
  
}


public void MENU(){
 background(51);
fill(255,0,0);
rect(100,145,520,370);
fill(200,0,0);
stroke(0);
strokeWeight(2);
rect(210,220,300,45);
rect(210,300,300,45);
rect(210,380,300,45);
fill(0);
textAlign(CENTER);
textSize(30);
text("START",360,255);
text("Pomoc",360,335);
text("Poziom trudno\u015bci",360,415);
}

public void Pomoc1(){
background(51);
fill(255,0,0);
rect(100,145,520,370);
fill(200,0,0);
  image(pomoc1,103,150);
  textSize(30);
  textAlign(CENTER);
  fill(0);
  text("Naci\u015bnij myszk\u0105 aby przej\u015b\u0107 dalej",360,460);
}

public void Pomoc2(){
 Pomoc2=true;
 background(51);
 fill(255,0,0);
 rect(100,145,520,450);
 textAlign(CENTER);
 fill(0);
 textSize(30);
 text("BONUSY",220,190);
 text("POCISKI",500,190);
  image(pomoc2,103,200);
  noFill();
 rect(120,520,200,45);
 text("MENU",220,554);
 rect(400,520,200,45);
 text("START",500,554);
}

public void PoziomTrudnosci(){
 PoziomTrudnosci = true;
 background(51);
  image(poziom_trudnosci,99,144);
}
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
  vx=4.5f;
  vy=4.5f;
  r=12;
  hitR=r;
  buff = new ArrayList<Bonus>();
  debuff =new ArrayList<Pocisk>();
  zycie = poczatkoweZycie = 100;
 }
 
 
 
 public void setMove(int k, boolean r){
   switch(k){
    case UP:     isUP=r;      break;
    case DOWN:   isDOWN=r;    break;
    case LEFT:   isLEFT=r;    break;
    case RIGHT:  isRIGHT=r;   break;
    default:                  break;
   }
 }
 
  public void move(){   
    if(isRIGHT) x+=vx;
    if(isLEFT)  x-=vx;
    if(isUP)    y-=vy;
    if(isDOWN)  y+=vy;
  }
  
  public void rysuj(){
    fill(255);
    stroke(255);
   ellipse(x,y,r,r); 
   rysujBuff();
   rysujHP();
  }
  
  public boolean hit(){
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
 public void rysujBuff(){
  for(int i=buff.size()-1;i>=0;i--){
    Bonus b = buff.get(i);
    b.rysujEfekt();
  }
 }
 
  public void rysujDebuff(){
    for(int i=debuff.size()-1;i>=0;i--){
    Pocisk b = debuff.get(i);
    b.rysujEfekt();
    }
 }
 
 public void rysujHP(){
   fill(0,255,0);
   noStroke();
   rect(5,5,(width-10)*(zycie/poczatkoweZycie),10);
 }
 
 public void hitBonus(){
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
 
 public void sprawdzBuff(){
  for(int i=buff.size()-1;i>=0;i--){
   Bonus b = buff.get(i);
   if(b.czasBuffa<=0){
    b.cofnijEfekt();
    buff.remove(i);
   }
  }
 }
 
 public void sprawdzDebuff(){
  for(int i=debuff.size()-1;i>=0;i--){
    Pocisk p = debuff.get(i);
    p.czasTrwania--;
    if(p.czasTrwania<=0){
      p.cofnijEfekt();
      debuff.remove(i);
    }
  }
 }
 
 public void sprawdzSciany(){
  if(x<r) x=r;
  else if(x>width-r) x=width-r;
    if(y<r) y=r;
  else if(y>height-r) y=height-r;
 }
 
 
}
 public float dystans(float x1, float y1, float x2, float y2){
   return sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
 }
class Pocisk{
 PVector poz;
 PVector speed;
 PVector[] punkty;
 float obrazenia;
 float czasTrwania;
 int usun;
 float w;
 float h;
 float angle;
 Pocisk(){
   czasTrwania=0;
   usun = width+200;
   w=25;
   h=5;
   int cz = floor(random(1,4));
   speed = new PVector(0,0);
   poz = new PVector(0,0);
   if(poziom<=2) obrazenia=25;
   if(poziom==3) obrazenia =34;
   if(poziom==4) obrazenia=50;
   
   switch(cz){
    case 1: poz.y = random(-w,w-30); poz.x = random(-100,width+100); break;
    case 2: poz.x = random(width+w,width+w+30); poz.y = random(-100,height+100); break;
    case 3: poz.y = random(height+w,height+w+30); poz.x = random(-100,width+100); break;
    case 4: poz.x = random(-w,-w-30); poz.y = random(-100,height+100); break;
    default:  break;
   }
   
   float cel_x=p.x+ random(-200,200);
   float cel_y = p.y + random(-200,200);
   PVector cel = new PVector(cel_x,cel_y);
   
   speed = PVector.sub(cel,poz); 
   speed.limit(4);
   
   
   
   punkty = new PVector [4];
   punkty[0]=new PVector(0,0);
   punkty[1]=new PVector(0,0);
   punkty[2]=new PVector(0,0);
   punkty[3]= new PVector(0,0);
   
  policzKat();
 }
 
 public void move(){
  this.poz.add(speed); 
  this.usun-=4;
 }
 
 public void rysuj(){
 pushMatrix();
  beginShape();
  translate(poz.x,poz.y);
  fill(255);
  stroke(255);
  vertex(0,0);
  vertex(punkty[1].x,punkty[1].y);
  vertex(punkty[3].x,punkty[3].y);
  vertex(punkty[2].x,punkty[2].y);
  endShape();
  popMatrix();
 }
 
 public void policzKat(){
   angle = speed.heading2D();
   punkty[1].x = w * cos(angle);
   punkty[1].y = w * sin(angle);
   punkty[2].x = h * cos(-(PI/2-angle));
   punkty[2].y = h * sin(-(PI/2-angle));
   punkty[3] = PVector.add(punkty[1],punkty[2]); 
 }
 public void efekt(){
   
 }
 
 public void cofnijEfekt(){
   
 }
 public void rysujEfekt(){
 
 }
 
}

class PociskSlow extends Pocisk{
  PociskSlow(){
   super(); 
   czasTrwania=frameRate*5 *(floor(poziom/3));
  }
  
  public void efekt(){
    p.vx-=1.5f;
    p.vy-=1.5f;
  }
  
  public void cofnijEfekt(){
   p.vx+=1.5f;
   p.vy+=1.5f;
  }
  
  public void rysuj(){
    fill(255,0,255);
    noStroke();
    pushMatrix();
  beginShape();
  translate(poz.x,poz.y);
  vertex(0,0);
  vertex(punkty[1].x,punkty[1].y);
  vertex(punkty[3].x,punkty[3].y);
  vertex(punkty[2].x,punkty[2].y);
  endShape();
  popMatrix();
  fill(255);
  }
  public void rysujEfekt(){
    
  }
  
}


class PociskObrazenia extends Pocisk{
  PociskObrazenia(){
   super(); 
   obrazenia*=2;
  }
  
  public void efekt(){
  }
  
  public void cofnijEfekt(){
  }
  
  public void rysuj(){
    fill(255,0,0);
    noStroke();
    pushMatrix();
  beginShape();
  translate(poz.x,poz.y);
  vertex(0,0);
  vertex(punkty[1].x,punkty[1].y);
  vertex(punkty[3].x,punkty[3].y);
  vertex(punkty[2].x,punkty[2].y);
  endShape();
  popMatrix();
  fill(255);
  }
  public void rysujEfekt(){
    
  }
}


class PociskZwieksz extends Pocisk{
  PociskZwieksz(){
   super(); 
   w=35;
   h=10;
   policzKat();
   czasTrwania=frameRate*5 * (floor(poziom/3));
  }
  
  public void efekt(){
    p.r+=5;
    p.hitR+=5;
  }
  
  public void cofnijEfekt(){
    p.r-=5;
    p.hitR-=5;
  }
  
  public void rysuj(){
    fill(0,255,255);
    noStroke();
    pushMatrix();
  beginShape();
  translate(poz.x,poz.y);
  vertex(0,0);
  vertex(punkty[1].x,punkty[1].y);
  vertex(punkty[3].x,punkty[3].y);
  vertex(punkty[2].x,punkty[2].y);
  endShape();
  popMatrix();
  fill(255);
  }
  public void rysujEfekt(){
    
  }
}
abstract class Bonus{
 float x;
 float y;
 float czasZycia;
 float czasBuffa;
 float r;
 
 Bonus(){
   r=35;
  x = random(10,width-10) ;
  y = random(10,height-10);
  czasZycia = frameRate*10;
  czasBuffa = frameRate*5;
 }
 
  public abstract void rysuj();
  public abstract void efekt();
  public abstract void cofnijEfekt();
  public abstract void rysujEfekt();
}


class Tarcza extends Bonus{
  Tarcza(){
 super();
  }
  
  public void rysuj(){
    noFill();
    stroke(255,0,0);
    ellipse(this.x,this.y,r,r);
    stroke(200,0,0);
    ellipse(this.x,this.y,r-1,r-1);
    stroke(150,0,0);
    ellipse(this.x,this.y,r-2,r-2);
    stroke(150,0,0);
    ellipse(this.x,this.y,r-3,r-3);
    stroke(100,0,0);
    ellipse(this.x,this.y,r-4,r-4);
    stroke(100,0,0);
    ellipse(this.x,this.y,r-5,r-5);
    stroke(0,255,0);
    fill(0,255,0);
    rect(this.x-13,this.y+18, 25 * (czasZycia/(frameRate * 10)),5);
  }
  
  public void efekt(){
   p.hitR=-20;
  }
  
  public void rysujEfekt(){
   noFill();
   stroke(255,0,0);
    ellipse(p.x,p.y,r,r);
    stroke(200,0,0);
    ellipse(p.x,p.y,r-1,r-1);
    stroke(150,0,0);
    ellipse(p.x,p.y,r-2,r-2);
    stroke(150,0,0);
    ellipse(p.x,p.y,r-3,r-3);
    stroke(100,0,0);
    ellipse(p.x,p.y,r-4,r-4);
    stroke(100,0,0);
    ellipse(p.x,p.y,r-5,r-5);
   czasBuffa--;
  }
  
  public void cofnijEfekt(){
   p.hitR = p.r; 
  }
}


class Speed extends Bonus{
  ArrayList<PVector> poz = new ArrayList <PVector>();
 Speed(){
  super(); 
  poz.add(new PVector(p.x,p.y));
  poz.add(new PVector(p.x,p.y));
  poz.add(new PVector(p.x,p.y));
 }
 
  public void rysuj(){
    pushMatrix();
    translate(x,y);
    beginShape();
    noFill();
    stroke(255);
    ellipse(0,0,r,r);
    vertex(2,-8);
    fill(255,255,0);
    vertex(0,-1);
    vertex(6,-1);
    vertex(-2,8);
    vertex(1,1);
    vertex(-6,1);
    
    endShape(CLOSE);
    popMatrix();
    rect(this.x-13,this.y+18, 25 * (czasZycia/(frameRate * 10)),5);
  }
  
  public void efekt(){
    p.vx+=1;
    p.vy+=1;
  }
  
  public void cofnijEfekt(){
    p.vx-=1;
    p.vy-=1;
  }
  public void rysujEfekt(){
    for(int i=0;i<poz.size();i++){
      fill(255,255,255,100);
     ellipse(poz.get(i).x,poz.get(i).y,p.r,p.r); 
    }
    poz.remove(0);
    poz.add(new PVector(p.x,p.y));
    czasBuffa--;
  }
}

class Apteczka extends Bonus{
  int leczenie;
 Apteczka(){
  super();
  czasBuffa = 0;
  leczenie = 25;
 }
 
 public void rysuj(){
   pushMatrix();
beginShape();
translate(x,y);
noFill();
stroke(255);
ellipse(0,0,r,r);
fill(0,255,0);
vertex(-3,-7);
vertex(3,-7);
vertex(3,-3);
vertex(7,-3);
vertex(7,3);
vertex(3,3);
vertex(3,7);
vertex(-3,7);
vertex(-3,3);
vertex(-7,3);
vertex(-7,-3);
vertex(-3,-3);
endShape(CLOSE);
popMatrix();
rect(this.x-13,this.y+18, 25 * (czasZycia/(frameRate * 10)),5);
 }
 
 public void rysujEfekt(){
   
 }
 public void efekt(){
   p.zycie+=leczenie;
   if(p.zycie>p.poczatkoweZycie){
    p.zycie = p.poczatkoweZycie; 
   }
 }
 public void cofnijEfekt(){
   
 }
}

class zmniejszRozmiar extends Bonus{
  zmniejszRozmiar(){
   super(); 
  }
  public void rysuj(){
    beginShape();
    pushMatrix();
    translate(x,y);
    noFill();
    stroke(255);
    ellipse(0,0,r,r);
    fill(0,255,0);
    vertex(-2,0);
    vertex(-9,5);
    vertex(-9,2);
    vertex(-15,2);
    vertex(-15,-2);
    vertex(-9,-2);
    vertex(-9,-5);
    endShape(CLOSE);
    point(0,0);
    beginShape();
    vertex(2,0);
    vertex(9,5);
    vertex(9,2);
    vertex(15,2);
    vertex(15,-2);
    vertex(9,-2);
    vertex(9,-5);
    endShape(CLOSE);
    popMatrix();
    rect(this.x-13,this.y+18, 25 * (czasZycia/(frameRate * 10)),5);
  }
  
  public void rysujEfekt(){
  czasBuffa--;
  }
  
  public void efekt(){
    p.r-=2.5f;
    p.hitR-=2.5f;
  }
  
  public void cofnijEfekt(){
    p.r+=2.5f;
    p.hitR+=2.5f;
  }
}
  public void settings() {  size(720,720); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Jakas_gra" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
