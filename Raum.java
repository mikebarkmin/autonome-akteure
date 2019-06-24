import bGLOOP.GLQuader;
import bGLOOP.GLObjekt;
import bGLOOP.GLTextur;

class Raum extends Akteur {
  private int hoehe;
  private int breite;
  private int laenge;

  private double wandBreite = 0.2;

  private List<GLObjekt> waende;

  private GLObjekt boden;

  public Raum(int hoehe, int breite, int laenge) {
    this.hoehe = hoehe;
    this.breite = breite;
    this.laenge = laenge;
  }

  public void fuegeWeltHinzu(int x, int y, int z, Welt welt) {
    super.fuegeWeltHinzu(x, y, z, welt);

    this.hoehe = welt.zuPixel(hoehe);
    this.breite = welt.zuPixel(breite);
    this.laenge = welt.zuPixel(laenge);

    int kachelGroesse = welt.gibKachelGroesse();
    x = welt.zuPixel(x) - kachelGroesse / 2;
    y = welt.zuPixel(y) + this.hoehe / 2;
    z = welt.zuPixel(z) - kachelGroesse / 2;

    this.waende = new List<>();
    GLTextur wandTextur = new GLTextur("grafiken/wand.jpg");
    GLQuader wand1 = new GLQuader(
        x, y, z + this.laenge / 2, 
        this.wandBreite, this.hoehe, this.laenge,
        wandTextur);
    GLQuader wand2 = new GLQuader(
        x + this.breite, y, z + this.laenge / 2,
        this.wandBreite, this.hoehe, this.laenge,
        wandTextur);
    GLQuader wand3 = new GLQuader(
        x + this.breite / 2, y, z,
        this.breite, this.hoehe, this.wandBreite,
        wandTextur);
    GLQuader wand4 = new GLQuader(
        x + this.breite / 2, y, z + this.laenge,
        this.breite, this.hoehe, this.wandBreite,
        wandTextur);
    this.waende.append(wand1);
    this.waende.append(wand2);
    this.waende.append(wand3);
    this.waende.append(wand4);


    this.boden = new GLQuader(
        x + this.breite / 2, y - this.hoehe / 2, z + this.laenge / 2,
        this.breite, 0.2, this.laenge,
        new GLTextur("grafiken/teppich.jpg"));
  }

  public void agiere() {}
}
