import bGLOOP.GLBewegbaresObjekt;

public abstract class Akteur {
  int x;
  int y;
  int z;
  int rotation = 0;
  Welt welt;

  private List<GLBewegbaresObjekt> objekte;

  public Akteur() {
    objekte = new List<>();
  }

  public Akteur(Welt welt) {
    this();
    this.welt = welt;
  }

  public void agiere() {}

  public int gibX() {
    return x;
  }

  public int gibXPixel() {
    return x * welt.gibKachelGroesse();
  }

  public int gibY() {
    return y;
  }

  public int gibYPixel() {
    return y * welt.gibKachelGroesse();
  }
  
  public int gibZ() {
    return z;
  }

  public int gibZPixel() {
    return z * welt.gibKachelGroesse();
  }

  public int gibRotation() {
    return rotation;
  }

  public void setzeRotation(int rotation) {
    this.rotiere(-this.rotation + rotation);
  }

  public void rotiere(int rotation) {
    this.rotation += rotation;
    this.objekte.toFirst();
    while(this.objekte.hasAccess()) {
      GLBewegbaresObjekt objekt = this.objekte.getContent();
      objekt.drehe(0, -rotation, 0);
      this.objekte.next();
    }
  }

  public void setzeWelt(Welt welt) {
    this.welt = welt;
  }

  public Welt gibWelt() {
    return this.welt;
  }
  
  public void setzePixelPosition(int x, int y, int z) {
    if (welt != null) {
      x = (int) Math.round(x / (double) welt.gibKachelGroesse());
      y = (int) Math.round(y / (double) welt.gibKachelGroesse());
      z = (int) Math.round(z / (double) welt.gibKachelGroesse());

      this.setzePosition(x, y, z);
    }
  }

  public void setzePosition(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;

    if(welt != null && welt.istBegrenzt()) {
      this.x = limitiere(x, welt.gibLaenge());
      this.z = limitiere(z, welt.gibBreite());
    }
  }

  public void fuegeWeltHinzu(int x, int y, int z, Welt welt) {
    this.setzeWelt(welt);
    this.setzePosition(x, y, z);
  }

  public boolean istAmWeltrand() {
    return (x <= 0 || z <= 0 || x >= welt.gibLaenge() - 1 || z >= welt.gibBreite() - 1);
  }

  public void anhaengen(GLBewegbaresObjekt objekt) {
    this.objekte.append(objekt);
  }

  public void bewege() {
    this.bewege(1);
  }

  public void bewege(int steps) {
    int oldx = x;
    int oldz = z;
    double radiant = Math.toRadians(this.rotation % 360);
    int dx = (int) Math.round(steps * Math.cos(radiant));
    int dz = (int) Math.round(steps * Math.sin(radiant));
    this.setzePosition(x + dx, y, z + dz);

    if ((dx == 0 && dz == 0) || (oldx == x && oldz == z)) {
      return;
    }

    this.objekte.toFirst();
    while(this.objekte.hasAccess()) {
      GLBewegbaresObjekt objekt = this.objekte.getContent();
      objekt.verschiebe(welt.zuPixel(dx), 0, welt.zuPixel(dz));
      this.objekte.next();
    }
  }

  protected <A> List<A> gibObjekteMitVerschiebung(int dx, int dy, int dz, Class<A> cls) {
    return gibWelt().gibObjekteBei(x + dx, y + dy, z + dz, cls);
  }

  protected Akteur gibEinObjektMitVerschiebung(int dx, int dy, int dz, Class<?> cls) {
    return gibWelt().gibEinObjektBei(x + dx, y + dy, z + dz, cls);
  }

  public int limitiere(int v, int limit) {
    if (v < 0) {
      v = 0;
    }
    if (limit <= v) {
      v = limit - 1;
    }

    return v;
  }

  public <W> W gibWeltVomTyp(Class<W> weltKlasse) {
    return weltKlasse.cast(this.welt);
  }

  public Akteur gibEinSchneidenesObjekt(Class<?> cls) {
    return welt.gibEinSchneidenesObjekt(this, cls);
  }

  public boolean beruehrt(Class<?> cls) {
    return gibEinSchneidenesObjekt(cls) != null;
  }

  public void entferneBeruehrt(Class<?> cls) {
    Akteur a = (Akteur) gibEinSchneidenesObjekt(cls);
    if (a != null) {
      welt.entferneObjekt(a);
    }
  }

  public void entferne() {
    this.objekte.toFirst();
    while(this.objekte.hasAccess()) {
      GLBewegbaresObjekt gl = this.objekte.getContent();
      gl.loesche();
      this.objekte.remove();
    }
  }
}
