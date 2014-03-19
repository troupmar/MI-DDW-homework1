Obsah složky code
================
Složka Code obsahuje 3 třídy v jazyce Java. Hlavní z nich "GateClient.java" představuje kostru aplikace sentimentální analýzy krátkých komentářů. Tato třída umožňuje načtení Training\_Corpusu, který obsahuje dokumenty již manuálně anotované v grafickém prostředí aplikace Gate. Dále pak načtení Testing\_Corpusu, který je v zápětí automaticky anotován základními anotacemi (tokenizer, sentence-splitter, POS-tagger, gazetteer). Na základě Training\_Corpusu se pak aplikace učí (využití "Learning" pluginu, konkrétně Batch Learning PR) a vzápětí uplatňuje své znalosti na Testing\_Corpusu. 

Druhou třídou je "Fb4j.java", která s pomocí Facebook4j API umožňuje stáhnout komentáře k nějakému zadanému postu. Bohužel v této práci nezbyl čas na integraci této třídy k aplikaci sentimentální analýzy a tak třída slouží pouze jako nástroj pro vytvoření kompletní aplikace k analýze komentářů z Facebooku. 

Poslední třídou je "Application.java", která slouží pouze k volání a testování instancí výše zmíněných tříd. 
