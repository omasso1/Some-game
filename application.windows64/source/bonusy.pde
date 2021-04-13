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
 
  abstract void rysuj();
  abstract void efekt();
  abstract void cofnijEfekt();
  abstract void rysujEfekt();
}


class Tarcza extends Bonus{
  Tarcza(){
 super();
  }
  
  void rysuj(){
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
  
  void efekt(){
   p.hitR=-20;
  }
  
  void rysujEfekt(){
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
  
  void cofnijEfekt(){
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
 
  void rysuj(){
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
  
  void efekt(){
    p.vx+=1;
    p.vy+=1;
  }
  
  void cofnijEfekt(){
    p.vx-=1;
    p.vy-=1;
  }
  void rysujEfekt(){
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
 
 void rysuj(){
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
 
 void rysujEfekt(){
   
 }
 void efekt(){
   p.zycie+=leczenie;
   if(p.zycie>p.poczatkoweZycie){
    p.zycie = p.poczatkoweZycie; 
   }
 }
 void cofnijEfekt(){
   
 }
}

class zmniejszRozmiar extends Bonus{
  zmniejszRozmiar(){
   super(); 
  }
  void rysuj(){
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
  
  void rysujEfekt(){
  czasBuffa--;
  }
  
  void efekt(){
    p.r-=2.5;
    p.hitR-=2.5;
  }
  
  void cofnijEfekt(){
    p.r+=2.5;
    p.hitR+=2.5;
  }
}