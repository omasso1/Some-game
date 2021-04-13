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
 
 void move(){
  this.poz.add(speed); 
  this.usun-=4;
 }
 
 void rysuj(){
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
 
 void policzKat(){
   angle = speed.heading2D();
   punkty[1].x = w * cos(angle);
   punkty[1].y = w * sin(angle);
   punkty[2].x = h * cos(-(PI/2-angle));
   punkty[2].y = h * sin(-(PI/2-angle));
   punkty[3] = PVector.add(punkty[1],punkty[2]); 
 }
 void efekt(){
   
 }
 
 void cofnijEfekt(){
   
 }
 void rysujEfekt(){
 
 }
 
}

class PociskSlow extends Pocisk{
  PociskSlow(){
   super(); 
   czasTrwania=frameRate*5 *(floor(poziom/3));
  }
  
  void efekt(){
    p.vx-=1.5;
    p.vy-=1.5;
  }
  
  void cofnijEfekt(){
   p.vx+=1.5;
   p.vy+=1.5;
  }
  
  void rysuj(){
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
  void rysujEfekt(){
    
  }
  
}


class PociskObrazenia extends Pocisk{
  PociskObrazenia(){
   super(); 
   obrazenia*=2;
  }
  
  void efekt(){
  }
  
  void cofnijEfekt(){
  }
  
  void rysuj(){
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
  void rysujEfekt(){
    
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
  
  void efekt(){
    p.r+=5;
    p.hitR+=5;
  }
  
  void cofnijEfekt(){
    p.r-=5;
    p.hitR-=5;
  }
  
  void rysuj(){
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
  void rysujEfekt(){
    
  }
}