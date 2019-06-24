import bGLOOP.GLHimmel;
import bGLOOP.GLBoden;

class MeineWelt extends Welt {

  private static int KACHEL_GROESSE = 20;
  private static int BREITE = 10;
  private static int LAENGE = 10;

  public MeineWelt() {
    super(BREITE, LAENGE, KACHEL_GROESSE, true);

    this.setzeKameraPosition(91,255,84);
    this.setzeKameraBlickpunkt(85,-58,88);

    new GLHimmel();
    new GLBoden();

    for(int x = 0; x < LAENGE; x++) {
      this.fuegeObjektHinzu(new Wand(), x, 0, 0);
      this.fuegeObjektHinzu(new Wand(), x, 0, BREITE);
    }

    for(int y = 0; y < BREITE; y++) {
      this.fuegeObjektHinzu(new Wand(), 0, 0, y);
      this.fuegeObjektHinzu(new Wand(), LAENGE, 0, y);
    }

    this.fuegeObjektHinzu(new StaubsaugRoboter(), 5, 0, 5);
    this.fuegeObjektHinzu(new Wand(), 8, 0, 5);
    this.fuegeObjektHinzu(new Wand(), 7, 0, 4);

    this.fuegeObjektHinzu(new Staub(), 2, 0, 2);
    this.fuegeObjektHinzu(new Staub(), 2, 0, 4);
    this.fuegeObjektHinzu(new Staub(), 4, 0, 8);
    this.fuegeObjektHinzu(new Staub(), 7, 0, 2);
    this.fuegeObjektHinzu(new Staub(), 3, 0, 2);
    this.fuegeObjektHinzu(new Staub(), 5, 0, 4);
  }

  public static void main(String[] args) {
    Welt s = new MeineWelt();
    s.starte();
  }
}
