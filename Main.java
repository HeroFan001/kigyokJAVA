import java.io.*;
import java.util.*;

public class Main {

    void main() {
        // 0) Beolvasás
        List<kigyok> Kigyok = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("kigyok.csv"), "UTF-8")) {
            scanner.nextLine(); // fejléc
            while (scanner.hasNextLine()) {
                String sor = scanner.nextLine();
                if (!sor.isBlank()) Kigyok.add(new kigyok(sor));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("0) Összesen " + Kigyok.size() + " kígyó adata beolvasva");

        int mergesDb = 0, nemMergesDb = 0;
        for (kigyok k : Kigyok) {
            if (k.merges.equals("Igen")) mergesDb++;
            else nemMergesDb++;
        }
        System.out.println("   Közülük " + mergesDb + " mérges és " + nemMergesDb + " nem mérges");

        // 1) Teljes hossz méterben (2 tizedes)
        int osszHossz = 0;
        for (kigyok k : Kigyok) osszHossz += k.hossz;
        System.out.printf("%n1) A kígyók teljes hossza méterben: %.2fm%n", osszHossz / 100.0);

        // 2) Leghosszabb mérges kígyó
        kigyok leghosszabb = null;
        for (kigyok k : Kigyok) {
            if (k.merges.equals("Igen")) {
                if (leghosszabb == null || k.hossz > leghosszabb.hossz) leghosszabb = k;
            }
        }
        System.out.println("2) A leghosszabb mérges kígyó: " + leghosszabb.fajta
                + " (" + leghosszabb.hossz + "cm)");

        // 3) Földrészek ABC sorrendben, vesszővel elválasztva
        Set<String> foldreszetSet = new TreeSet<>();
        for (kigyok k : Kigyok) foldreszetSet.add(k.eloffordulas);
        StringBuilder foldresz = new StringBuilder();
        for (String f : foldreszetSet) {
            if (foldresz.length() > 0) foldresz.append(", ");
            foldresz.append(f);
        }
        System.out.println("\n3) A kígyók származási helye (abc): " + foldresz);

        // 4) Véletlenszerű mérges kígyó
        List<kigyok> mergesLista = new ArrayList<>();
        for (kigyok k : Kigyok) if (k.merges.equals("Igen")) mergesLista.add(k);
        Random rnd = new Random();
        kigyok valasztott = mergesLista.get(rnd.nextInt(mergesLista.size()));
        System.out.println("\n4) Egy véletlenszerűen kiválasztott mérges kígyó: " + valasztott.fajta);
        System.out.println("   Származási helye " + valasztott.eloffordulas + ", hossza " + valasztott.hossz + "cm");

        // 5) Faj az utolsó szó alapján, darabszám
        System.out.println("\n5) Adott fajhoz (abc) tartozó kígyók darabszáma:");
        Map<String, Integer> fajStat = new TreeMap<>();
        for (kigyok k : Kigyok) {
            String[] szavak = k.fajta.split(" ");
            String fajNev = szavak[szavak.length - 1];
            fajStat.put(fajNev, fajStat.getOrDefault(fajNev, 0) + 1);
        }
        for (Map.Entry<String, Integer> e : fajStat.entrySet()) {
            System.out.println("   " + e.getKey() + " : " + e.getValue() + " féle");
        }

        // 6) Utolsó Mamba fajtája
        kigyok utolsoMamba = null;
        for (kigyok k : Kigyok) {
            String[] szavak = k.fajta.split(" ");
            if (szavak[szavak.length - 1].equals("Mamba")) utolsoMamba = k;
        }
        System.out.println("\n6) Az utolsó Mamba fajtája: " + utolsoMamba.fajta);

        // 7) kobra.txt
        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream("kobra.txt"), "UTF-8"))) {
            
            for (kigyok k : Kigyok) {
                String[] szavak = k.fajta.split(" ");
                if (szavak[szavak.length - 1].equals("Kobra")) {
                    pw.println(k.fajta + " (" + k.hossz + "cm)");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\n7) Minden Kobra mentve a kobra.txt fájlba");
    }
}
