public class kigyok {
    public String fajta;
    public int hossz;
    public String eloffordulas;
    public String merges;

    public kigyok(String sor) {
        String[] tomb = sor.split(";");
        fajta = tomb[0];
        hossz = Integer.parseInt(tomb[1]);
        eloffordulas = tomb[2];
        merges = tomb[3];
    }

    public String getFajta() { return fajta; }
    public int getHossz() { return hossz; }
    public String getEloffordulas() { return eloffordulas; }
    public String getMerges() { return merges; }
}
