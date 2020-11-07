                                        Document Service


	    Aplikacja obsługuje certyfikaty z rozszerzeniem ‘.cer’, 
    w folderze ‘certificate’ zgenerowany wzór za pomocą java keytools.

	    W folderze ‘db-scripts’ skrypty do stworzenia użytkownika bazy danych MySql 
    ta stworzenia odpowiedniej tabeli.

	    ‘main-service’ odpowiada za logowanie użytkowników serwisu, 
    przekierowanie zapytań z front-endu do dwóch zbalansowanych mikroserwisów 
    ‘document-service-one’, ‘document-service-two’ ta rendering stron. 