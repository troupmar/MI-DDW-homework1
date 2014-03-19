MI-DDW-homework1
================
Cílem tohoto projektu je aplikace sentimentální analýzy technikou strojového učení. K tomu byla využita aplikace Gate a pro strojové učení pak konktrétně plugin "Learning". Vše bylo implementováno v jazyce Java za pomoci Gate API.

Složka Code obsahuje 3 třídy v jazyce Java. Hlavní z nich "GateClient.java" představuje kostru aplikace sentimentální analýzy krátkých komentářů. Tato třída umožňuje načtení Training\_Corpusu, který obsahuje dokumenty již manuálně anotované v grafickém prostředí aplikace Gate. Dále pak načtení Testing\_Corpusu, který je v zápětí automaticky anotován. Na základě Training\_Corpusu se pak aplikace učí (využití "Learning" pluginu, konkrétně Batch Learning PR) a vzápětí uplatňuje své znalosti na Testing\_Corpusu. Další třídou je " 
