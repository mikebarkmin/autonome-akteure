import bGLOOP.GLWuerfel;
import bGLOOP.GLTextur;

class Wand extends Akteur {
  public void fuegeWeltHinzu(int x, int y, int z, Welt welt) {
    super.fuegeWeltHinzu(x, y, z, welt);
    x = gibXPixel();
    y = (int) Math.round(gibYPixel() + welt.gibKachelGroesse() / 2);
    z = gibZPixel();

    GLTextur wandTextur = new GLTextur("grafiken/wand.jpg");
    GLWuerfel wuerfel = new GLWuerfel(x, y, z, welt.gibKachelGroesse(), wandTextur);
    this.anhaengen(wuerfel);
  }
}
