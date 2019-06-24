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
  }

  public static void main(String[] args) {
    Welt s = new MeineWelt();

    new GLHimmel();
    new GLBoden();

    for(int x = 0; x < LAENGE; x++) {
      s.fuegeObjektHinzu(new Wand(), x, 0, 0);
      s.fuegeObjektHinzu(new Wand(), x, 0, BREITE);
    }

    for(int y = 0; y < BREITE; y++) {
      s.fuegeObjektHinzu(new Wand(), 0, 0, y);
      s.fuegeObjektHinzu(new Wand(), LAENGE, 0, y);
    }

    s.fuegeObjektHinzu(new StaubsaugRoboter(), 5, 0, 5);
    s.fuegeObjektHinzu(new Wand(), 8, 0, 5);
    s.fuegeObjektHinzu(new Wand(), 7, 0, 4);

    s.fuegeObjektHinzu(new Staub(), 2, 0, 2);
    s.fuegeObjektHinzu(new Staub(), 2, 0, 4);
    s.fuegeObjektHinzu(new Staub(), 4, 0, 8);
    s.fuegeObjektHinzu(new Staub(), 7, 0, 2);
    s.fuegeObjektHinzu(new Staub(), 3, 0, 2);
    s.fuegeObjektHinzu(new Staub(), 5, 0, 4);
    s.starte();
  }
}
