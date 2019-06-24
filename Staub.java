import bGLOOP.GLTextur;
import bGLOOP.GLQuader;

class Staub extends Akteur {
  public void fuegeWeltHinzu(int x, int y, int z, Welt welt) {
    super.fuegeWeltHinzu(x, y, z, welt);
    x = gibXPixel();
    z = gibZPixel();

    GLTextur staubTextur = new GLTextur("grafiken/staub.png");
    GLQuader staub = new GLQuader(x, 0.2, z, welt.gibKachelGroesse(), 0.2, welt.gibKachelGroesse(), staubTextur);
    this.anhaengen(staub);
  }
}
