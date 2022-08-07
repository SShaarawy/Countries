# Countries
![Ekran Alıntısı1](https://user-images.githubusercontent.com/32748494/183294137-9cf8b0aa-84c6-479d-afa2-7e25e1c78e71.PNG)
![Ekran Alıntısı2](https://user-images.githubusercontent.com/32748494/183294141-697a4cd5-c765-49a5-b91c-4515be0de98b.PNG)

![Ekran Alıntısı3](https://user-images.githubusercontent.com/32748494/183294145-617a882f-f6a8-4936-885a-e63282bb904e.PNG)
![Ekran Alıntısı4](https://user-images.githubusercontent.com/32748494/183294146-192c02c1-2bc9-495e-9e34-1622bfb98f97.PNG)

-- App gets 10 countries from below endpoint and shows them in home screen.(1)
-- If the users want to save the countries, they can do that by clicking the star in the same line.
-- The Countries which is starred will be shown in the saved screen.
-- In both home and saved screens, users can access the details of any country by clicking country names.
-- App gets the details of countries from below endpoint by using "code" parameter.(2)
-- Button inside the details screen navigates to wikiData for mroe information about country.

(1) https://wft-geo-db.p.rapidapi.com/v1/geo/countries

(2) https://wft-geo-db.p.rapidapi.com/v1/geo/countries/{code}
    for example: https://wft-geo-db.p.rapidapi.com/v1/geo/countries/US
