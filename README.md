

📘 Calculator — Android Kalkulator
Preprost, natančen in lepo oblikovan Android kalkulator, napisan v Javi.
Podpira osnovne matematične operacije, odstotke, znanstveno notacijo, negativna števila in formatiranje po evropskem sistemu (vejica kot decimalno ločilo).

✨ Funkcije
✔ Osnovne operacije
Seštevanje (+)

Odštevanje (−)

Množenje (×)

Deljenje (÷)

✔ Napredne funkcije
Odstotki (%)

Znanstvena notacija za zelo velika števila

Podpora za negativna števila (tudi kot prvi vnos)

Samodejno formatiranje števil:

tisočice → .

decimalke → ,

✔ Logika pravega kalkulatorja
Nadaljevanje računanja po pritisku =

Pravilno zaporedno računanje (npr. 5 + 3 × 2)

Shranjevanje prve vrednosti (firstValue)

Shranjevanje trenutne operacije (currentOp)

Preprečevanje napačnih vnosov (npr. več decimalnih vejic)

🧠 Kako deluje
📌 Formatiranje števil
Aplikacija uporablja:

java
DecimalFormat formatter = new DecimalFormat("#,###.##########", symbols);
To omogoča:

evropski zapis števil

tisočice s piko

decimalke z vejico

do 10 decimalk

📌 Znanstvena notacija
Če je število večje od 1e10, se prikaže v obliki:

Koda
1,234567890e12
📌 Logika operacij
Kalkulator uporablja tri ključne spremenljivke:

firstValue → prva številka v izrazu

currentOp → trenutna operacija

lastWasEqual → ali je bil nazadnje pritisnjen =

📌 Zaporedne operacije
Primer:

Koda
5 + 3 × 2
Kalkulator izvede:

5 + 3 = 8

8 × 2 = 16

To omogoča blok:

java
if (!Double.isNaN(firstValue) && currentOp != '0' && screen2Display.getText().length() > 0) {
    calculate();
}
📌 Deljenje z 0
Java samodejno vrne:

Koda
Infinity
Kalkulator to pravilno prikaže kot:

Koda
∞
📱 UI
Aplikacija ima dva zaslona:

🔹 Zgornji zaslon (screen1Display)
Prikazuje matematični izraz, npr.:

Koda
5 × 3 =
🔹 Spodnji zaslon (screen2Display)
Prikazuje trenutno vneseno številko ali rezultat.

🛠 Tehnologije
Java

Android SDK

DecimalFormat

TextView

Button

OnClickListener

📂 Struktura projekta
Koda
MainActivity.java
activity_main.xml
res/
    layout/
    drawable/
    values/
🚀 Namestitev
Odpri projekt v Android Studio

Zaženi na emulatorju ali fizični napravi

Uživaj v preprostem, hitrem kalkulatorju

🧑‍💻 Avtor
Rok Piculin  
Android Calculator — Java

📄 Licenca
Ta projekt je odprtokoden.
Uporabi ga, spremeni ga, izboljšaj ga.



<img width="337" height="592" alt="image" src="https://github.com/user-attachments/assets/02dd667f-b5e7-44c9-a3cf-da8df59cfa03" />









