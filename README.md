# Semestrální práce BI-TJV - klient

### Spuštění
Pro spuštění je potřeba nejprve správně spustit databázi a server, který se nachází v samostatném repozitáři: [server](https://gitlab.fit.cvut.cz/ryntluka/tjv---semestralka)

### Použití
Po spuštění aplikace, běží klient na následující [adrese](http://localhost:8081), server využívá port 8080 a klient port 8081.

V pravé části stránky je rozcestník s odkazy na správu jednotlivých entit:
*   [Customers](http://localhost:8081)
*   [Planets](http://localhost:8081/products)
*   [Products](http://localhost:8081/planets)

Samotná stránka pak v sobě obsahuje dole tabulku s entitami a nahoře toolbar s vyhledáváním a přidáním nové entity. Vyhledávání funguje na základě rovnosti atributů a tlačítko `Add entity` otevře formulář pro vytvoření nové entity. Formulář musí být vyplněný, jinak entita nevznikne a uživatel na to bude upozorněn. Ve všech formulářích fungují klávesové zkratky Enter pro uložení a Esc pro zavření formuláře. Při kliknutí na jednu entitu se otevře formulář, podobný tomu pro vytvoření, ale bez navíc s kolonkou id, které je automaticky generované a tlačítkem delete. Delete pak slouží ke smazání aktuálně vybraného záznamu a Save k aktualizaci záznamu.

Na stránce Products je navíc tlačítko, které reprezentuje operaci order/removeOrder ze serveru. Po kliknutí na něj se otevře formulář, ve kterém můžeme vybrat zákazník a produkt, který si objednal. Odpovídající produkt bude poté aktualizován jako objednaný. Při duplicitě objednávek od jednoho zákazníka je vidět počet objednávek.