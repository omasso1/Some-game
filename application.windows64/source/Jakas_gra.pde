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
void setup(){
 size(720,720);
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

void draw(){
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
punkty+=0.25*poziom;
else
punkty+=1.25;
}
}

/**********************************/

void keyPressed(){
 p.setMove(keyCode, true);
 //noLoop();
}

void keyReleased(){
  //loop();
 p.setMove(keyCode,false);
}

void mouseReleased(){
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

void ruch(){
 p.move();
 for(Pocisk po : Pociski){
   po.move();
 }
 
}

void rysuj(){
  p.rysuj();
  wyswietlPunkty();
 for(Pocisk po : Pociski){
  po.rysuj(); 
 }
 for(Bonus b : Bonusy_gra){
   b.rysuj();
 }
}

void sprawdz(){ 
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



void reset(){
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

void zyciePociskow(){
 for(int i=Pociski.size()-1;i>=0;i--){
 if(Pociski.get(i).usun<=0){
   Pociski.remove(i); 
 }
 }
}

void zycieBonusow(){
 for(int i=Bonusy_gra.size()-1;i>=0;i--){
   Bonus b = Bonusy_gra.get(i);
   b.czasZycia--;
   if(b.czasZycia<=0) Bonusy_gra.remove(i);
 }
 
}

void wyswietlPunkty(){
  textAlign(BASELINE);
  fill(255);
   String p = "Punkty: ";
   p += floor(punkty);
   textSize(20);
   text(p,10,50);
}

void przegrana(){
  reset();
  Przegrana=true;
  p.x =width/2-10;
  p.y = height/2+20;
 background(51);
 fill(255,0,0);
 noStroke();
 rect(width/2-160,height/2-110,300,200);
 fill(0);
 String k = "Przegraleś";
 String p = "Punkty: " + int(punkty);
 text(k,width/2-60,height/2-20);
 text(p,width/2-60,height/2);
 noLoop();
}

void respBuffow(){
 if(random(1)<0.001){
   Bonusy_gra.add(new zmniejszRozmiar()); 
  }
   if(random(1)<0.001){
   Bonusy_gra.add(new Speed()); 
  }
   if(random(1)<0.001){
   Bonusy_gra.add(new Tarcza()); 
  }
   if(random(1)<0.001){
   Bonusy_gra.add(new Apteczka()); 
  } 
}

void respPociskow(){
  if(poziom!=4){
  if(random(1)<0.1){
  Pociski.add(new Pocisk());
  }
    if(random(1)<0.01*poziom){
  Pociski.add(new PociskObrazenia());
  }
    if(random(1)<0.01*poziom){
  Pociski.add(new PociskZwieksz());
  }
    if(random(1)<0.01*poziom){
  Pociski.add(new PociskSlow());
   }
  }
  else{
    
    if(random(1)<0.01*poziom/2){
  Pociski.add(new PociskObrazenia());
  }
    if(random(1)<0.01*poziom/2){
  Pociski.add(new PociskZwieksz());
  }
    if(random(1)<0.2){
  Pociski.add(new PociskSlow());
   } 
  }
  
}
void start_(){
  punkty=0;
  Pomoc2=false;
  Menu=false;
 for(int i=0;i<20;i++){
  respPociskow(); 
 }
  
}


void MENU(){
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
text("Poziom trudności",360,415);
}

void Pomoc1(){
background(51);
fill(255,0,0);
rect(100,145,520,370);
fill(200,0,0);
  image(pomoc1,103,150);
  textSize(30);
  textAlign(CENTER);
  fill(0);
  text("Naciśnij myszką aby przejść dalej",360,460);
}

void Pomoc2(){
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

void PoziomTrudnosci(){
 PoziomTrudnosci = true;
 background(51);
  image(poziom_trudnosci,99,144);
}