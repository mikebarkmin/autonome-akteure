import bGLOOP.GLKamera;
import bGLOOP.GLEntwicklerkamera;
import bGLOOP.GLLicht;
import bGLOOP.GLTextur;
import bGLOOP.GLQuader;
import bGLOOP.Sys;

public abstract class Welt {

  final int breite;
  final int laenge;
  final int kachelGroesse;

  private List<Akteur> objekte;
  private List<GLQuader> raster;
  private GLLicht licht;
  private GLKamera kamera;
  private boolean zeigeRaster;
  private boolean begrenzt;
  private long intervall;

  public Welt(int breite, int laenge, int kachelGroesse, boolean zeigeRaster) {
    this(breite, laenge, kachelGroesse);
    this.zeigeRaster = zeigeRaster;
    this.zeigeRaster(zeigeRaster);
  }

  public Welt(int breite, int laenge, int kachelGroesse) {
    this.kamera = new GLEntwicklerkamera();
    this.raster = new List<>();
    this.licht = new GLLicht();
    this.objekte = new List<>();
    this.begrenzt = true;
    this.intervall = 1000;

    this.breite = breite;
    this.laenge = laenge;
    this.kachelGroesse = kachelGroesse;
  }

  public GLLicht gibLicht() {
    return licht;
  }

  public GLKamera gibKamera() {
    return kamera;
  }

  public void setzeKameraPosition(int x, int y, int z) {
    this.kamera.setzePosition(x, y, z);
  }

  public void setzeKameraBlickpunkt(int x, int y, int z) {
    this.kamera.setzeBlickpunkt(x, y, z);
  }

  public boolean istBegrenzt() {
    return begrenzt;
  }

  public void setzeBegrenzt(boolean begrenzt) {
    this.begrenzt = begrenzt;
  }

  public int gibBreite() {
    return breite;
  }

  public int gibPixelBreite() {
    return breite * kachelGroesse;
  }

  public int gibLaenge() {
    return laenge;
  }

  public int gibPixelLaenge() {
    return laenge * kachelGroesse;
  }

  public int gibKachelGroesse() {
    return kachelGroesse;
  }

  public void starte() {
    List<Akteur> copyOfObjekte = new List<>();
    objekte.toFirst();
    while(objekte.hasAccess()) {
      Akteur objekt = objekte.getContent();
      copyOfObjekte.append(objekt);
      objekte.next();
    }

    while(true) {
      copyOfObjekte.toFirst();
      while(copyOfObjekte.hasAccess()) { 
        Akteur objekt = copyOfObjekte.getContent();
        objekt.agiere();
        copyOfObjekte.next();
      }
      Sys.warte(intervall);
    }
  }

  public void zeigeRaster(boolean r) {
    this.zeigeRaster = r;
    if(this.zeigeRaster) {
      this.zeichneRaster();
    } else {
      this.raster.toFirst();
      while(this.raster.hasAccess()) {
        this.raster.getContent().loesche();
        this.raster.remove();
      }
    }
  }

  private void zeichneRaster() {
    if (!this.raster.isEmpty()) {
      return;
    }
    GLTextur rasterTextur = new GLTextur("grafiken/raster.png");
    for(int x = 0; x < this.laenge; x++) {
      for(int z = 0; z < this.breite; z++) {
        int px = zuPixel(x);
        int pz = zuPixel(z);
        double rasterHoehe = 0.1;
        this.raster.append(new GLQuader(px, this.kachelGroesse / 2, pz, this.kachelGroesse, rasterHoehe, this.kachelGroesse, rasterTextur));
      }
    }
  }

  public int zuKachelAufgerundet(int pixel) {
    return (int) Math.ceil((double) pixel / kachelGroesse);
  }

  public int zuKachelAbgerundet(int pixel) {
    return (int) Math.floor((double) pixel / kachelGroesse);
  }

  public int zuPixel(int kachel) {
    return kachel * kachelGroesse;
  }

  public void fuegeObjektHinzu(Akteur objekt, int x, int y, int z) {
    if (objekt.gibWelt() != null) {
      if (objekt.gibWelt() == this) {
        return;
      }
      objekt.gibWelt().entferneObjekt(objekt);
    }

    objekt.fuegeWeltHinzu(x, y, z, this);
    this.objekte.append(objekt);
  }

  public void entferneObjekt(Akteur objekt) {
    if (objekt == null || objekt.gibWelt() != this) {
      return;
    }

    this.objekte.toFirst();
    while(this.objekte.hasAccess() && !this.objekte.getContent().equals(objekt)) {
      this.objekte.next();
    }
    this.objekte.getContent().entferne();
    this.objekte.remove();
    objekt.setzeWelt(null);
  }

  public void entferneObjekte(List<? extends Akteur> objekte) {
    while(objekte.hasAccess()) {
      this.entferneObjekt(objekte.getContent());
      this.objekte.next();
    }
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public <A> List<A> gibObjekte(Class<A> cls) {
    List<A> ergebnis = new List<>();

    this.objekte.toFirst();
    while(this.objekte.hasAccess()) {
      Akteur a = this.objekte.getContent();
      if (cls == null || cls.isInstance(a)) {
        ergebnis.append((A) a);
      }
      this.objekte.next();
    }

    return ergebnis;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public <A> List<A> gibObjekteBei(int x, int y, int z, Class<A> cls) {
    this.objekte.toFirst();
    List<A> ergebnis = new List<>();
    while(this.objekte.hasAccess()) {
      Akteur objekt = this.objekte.getContent();
      if(cls.isInstance(objekt)) {
        if(objekt.gibX() == x && objekt.gibY() == y && objekt.gibZ() == z) {
          ergebnis.append((A) objekt);
        }
      }
      this.objekte.next();
    }
    return new List<>();
  }

  public Akteur gibEinObjektBei(int x, int y, int z, Class<?> cls) {
    this.objekte.toFirst();
    while(this.objekte.hasAccess()) {
      Akteur objekt = this.objekte.getContent();
      if(cls.isInstance(objekt)) {
        if(objekt.gibX() == x && objekt.gibY() == y && objekt.gibZ() == z) {
          return objekt;
        }
      }
      this.objekte.next();
    }
    return null;
  }

  public Akteur gibEinSchneidenesObjekt(Akteur a, Class<?> cls) {
    return gibEinObjektBei(a.gibX(), a.gibY(), a.gibZ(), cls);
  }
}
