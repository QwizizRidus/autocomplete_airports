# autocomplete_airports

My free interpretation of "Renue" test task I've found on the web. 
## Problem statement

<i>Требуется написать консольное Java-приложение (JDK 11), позволяющее быстро искать 
данные аэропортов по вводимому пользователем названию аэропорта и фильтрам.

Данные для программы берутся из файла . В нем находится таблица аэропортов 
со свойствами в формате CSV. Название аэропорта — 2 колонка. За что отвечают другие 
колонки — не важно, на них навешиваются фильтры. 

Фильтры — отношения равенства. 
Фильтр передается в формате: column[1]>10 & column[5]=’GKA’

Фильтры могут соединяться отношением И и ИЛИ. Также могут участвовать скобки для 
обозначения приоритета и группировки. Отношение И имеет более высокий приоритет 
нежели ИЛИ.</i>

## Example
Пользователь запускает приложение:
> java jar autocomplete_airports.jar // запуск приложения

После запуска программа выводит в консоль предложение ввести фильтр.
> Пользователь вводит: column[1]>10&column[5]=’GKA

Программа просит ввести начало имени аэропорта.
> Пользователь вводит: "Bo"

Программа выводит список всех строк из файла, вторая колонка которых начинается на "Bo", с учетом 
фильтров. После вывода всех строк программа должна вывести число найденных строк и время в 
миллисекундах, затраченное на поиск.

## Nonfunctional requirements
1. Перечитывать все строки файла при каждом поиске нельзя. В том числе читать только определенную колонку у каждой строки.
2. Создавать новые файлы или редактировать текущий нельзя. В том числе использовать СУБД.
3. Хранить весь файл в памяти нельзя. Не только в качестве массива байт, но и в структуре, которая так или иначе содержит все данные из файла.
4. Для корректной работы программе требуется не более 7 МБ памяти. Все запуски java –jar должны выполняться с jvm флагом -Xmx7m.
5. Скорость поиска должна быть максимально высокой с учетом требований выше.
6. Сложность поиска меньше чем O(n), где n число строк файла.
7. Должны соблюдаться принципы ООП и SOLID.
8. Ошибочные и краевые ситуации должны быть корректно обработаны.
9. Использовать готовые библиотеки для парсинга CSV формата нельзя.
