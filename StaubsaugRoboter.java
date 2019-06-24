import bGLOOP.GLZylinder;
import bGLOOP.GLTextur;

class StaubsaugRoboter extends Akteur {

  private double hoehe;
  private double radius;

  public StaubsaugRoboter() {
  }

  public void fuegeWeltHinzu(int x, int y, int z, Welt welt) {
    super.fuegeWeltHinzu(x, y, z, welt);

    this.hoehe = welt.gibKachelGroesse() / 10;
    this.radius = welt.gibKachelGroesse() / 2;

    int px = gibXPixel();
    int py = (int) Math.round(gibYPixel() + this.hoehe / 2);
    int pz = gibZPixel();

    GLZylinder koerper = new GLZylinder(px, py, pz, this.radius, this.hoehe, new GLTextur("grafiken/staubsaugroboter.jpg"));
    koerper.drehe(90, 0, 0);
    this.anhaengen(koerper);
  }

  public boolean kontaktVorne() {
    int rot = gibRotation() % 360;

    if (rot == 0 && gibEinObjektMitVerschiebung(1, 0, 0, Wand.class) != null) {
      return true;
    } else if (rot == 180 && gibEinObjektMitVerschiebung(-1, 0, 0, Wand.class) != null) {
      return true;
    } else if (rot == 90 && gibEinObjektMitVerschiebung(0, 0, 1, Wand.class) != null) {
      return true;
    } else if (rot == 270 && gibEinObjektMitVerschiebung(0, 0, -1, Wand.class) != null) {
      return true;
    }
    return false;
  }

  public boolean kontaktLinks() {
    int rot = gibRotation() % 360;
    
    if (rot == 90 && gibEinObjektMitVerschiebung(1, 0, 0, Wand.class) != null) {
      return true;
    } else if (rot == 270 && gibEinObjektMitVerschiebung(-1, 0, 0, Wand.class) != null) {
      return true;
    } else if (rot == 180 && gibEinObjektMitVerschiebung(0, 0, 1, Wand.class) != null) {
      return true;
    } else if (rot == 0 && gibEinObjektMitVerschiebung(0, 0, -1, Wand.class) != null) {
      return true;
    }
    return false;
  }

  public boolean kontaktRechts() {
    int rot = gibRotation();
    
    if (rot == 270 && gibEinObjektMitVerschiebung(1, 0, 0, Wand.class) != null) {
      return true;
    } else if (rot == 90 && gibEinObjektMitVerschiebung(-1, 0, 0, Wand.class) != null) {
      return true;
    } else if (rot == 0 && gibEinObjektMitVerschiebung(0, 0, 1, Wand.class) != null) {
      return true;
    } else if (rot == 180 && gibEinObjektMitVerschiebung(0, 0, -1, Wand.class) != null) {
      return true;
    }
    return false;
  }

  public boolean staubVorhanden() {
    if (gibEinSchneidenesObjekt(Staub.class) != null) {
      System.out.println("Staub!!!");
      return true;
    }
    return false;
  }

  public void entferneStaub() {
    if (staubVorhanden()) {
      entferneBeruehrt(Staub.class);
    }
  }

  public void agiere() {
    while(kontaktVorne()) {
      rotiere(90);
    } 
    entferneStaub();
    bewege();
  }
}
