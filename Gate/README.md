Obsah složky Gate
================
Složka Code obsahuje soubor Gate.zip. Po extrahování obsahuje soubor složku GATE, která dále obsahuje 3 složky. První z nich "GATEprojects" slouží jako datastore do programu Gate. Po jeho nahrání obsahuje 2 corpusy. Testing\_Corpus obsahuje 50 ručně i automaticky anotovaných dokumentů, které slouží jako zdroj učení pro "Batch Learning PR", jeden z Gate procesorů pro strojové učení. Druhý corpus, Testing\_Corpus, pak obsahuje pouze 2 dokumenty se základními automatickými anotacemi, které následně slouží k otestování znalostí stroje.

Další složka "savedFiles" je automaticky generovanou složkou Gate "Batch Learning" procesorem. Zde má stroj uložený zdroj naučených znalostí, které pak aplikuje na dokumenty k analýze polarity.

Poslední složkou je "ConfigFiles", kde jsou uloženy 2 různé verze konfiguračního souboru pro "Batch Learning PR". Tyto konfigurační soubory jsou zásadní z hlediska způsobu, kterým se následně stroj učí a kde hledá určené spojitosti.
