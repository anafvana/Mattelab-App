# DAVE3600 Apputvikling - Mappeeksamen1: Mattelab
Simple Android application consisting of a Maths game for children, including statistics and customisable settings.

First deliverable assignment for DAVE3600 - App Development.

## Mappe 1 Apputvikling 2021

Dere skal lage en matteopplærings-app. 

Når applikasjonen startes skal man komme til et skjermbilde hvor mulige valg er Start spill, Se statistikk, Preferanser. Selve spillet viser addisjons regnestykker og knapper med tallene 0 til 9. Man skriver inn svar med knappene. Regnestykkene og svarene skal lagres som arrays i xml-fil. 

Det skal legges inn 15 regnestykker. Regnestykkene skal vises random når et spill er startet. Samme regnestykke skal ikke komme opp flere ganger i en sesjon. Antall riktige og gale svar skal vises på skjermen når det spilles. Standard er at et spill er 5 regnestykker, men dette kan settes til 5, 10 eller 15 i Preferanser.

Antall riktige og gale svar skal summeres opp og lagres slik at man neste gang kan gå inn på Statistikk og se disse. Det skal også være mulig å slette tidligere statistikk. 

Hvis spilleren avbryter spillet før det er ferdig skal det komme opp en dialogboks som spør om det virkelig skal avsluttes. Avbruttespill tas ikke med i statistikken. Hvis alle spørsmål er benyttet skal det komme en melding til brukeren om at det ikke er flere tilgjengelige spørsmål. 

Spillet skal ha ulik layout i stående og liggende modus. Skjermbildet skal benyttes på en så god måte som mulig og designvalg bør følge designregler fra developer.android.com. Husk å lag eget ikon til appen på desktop og skap gjenkjennelse gjennom alle skjermbilder. Målgruppen her er små barn så design bør være rettet mot denne gruppen.

Alle strenger skal ligge i strings.xml. 

I Preferanser skal man kunne velge mellom språkene norsk og tysk. Ved endring av språk skal alle strenger i spillet skifte til riktige verdier. Dere velger selv hvordan navigasjon mellom start-aktivitet og spill-aktivitet foregår, men pass på at ikke aktivitetene deres legger seg på stack. Fint om tilstanden bevares når emulator roteres. 

Det skal leveres med en rapport som skal ligge under res/raw folderen. Husk at filnavn må ha små bokstaver. Rapporten skal være i pdf-format navngis med studentnummer og vise skjermbildene og gangen i applikasjonen deres + begrunnelse for designvalg som farger, bruk av ikoner, navigasjonsmuligheter etc. Oppgi kildehenvisning til hvor dere har funnet opplysninger som støtter at design og navigasjonsvalger brukervennlige.

Minimumversjon Marshmellow API 23. Emulator som benyttes er Nexus SAPI 23480x800 hdpi. Ha studentnr med i domenenavn når dere oppretter prosjektet.

Husk at en mappeinnlevering er som en eksamen. Dere skal levere i Canvas. Dere har mange uker på å lage dette så en sykmelding og søknad om utsettelse bør leveres i god tid før innleveringsfrist. Ta kontakt hvis noe i kravspesifikasjonen er uklart.
