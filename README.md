## Описание

Работу можно было сделать проще, но было интересно 
применить паттерны проектирования.

Директория .idea удалена из git, так как в ней хранят 
файлы и IDEA, и Android Studio и эти файлы несовместимы. 

В релизах — apk и отчёт.

## Получение apk

Перед компиляцией нужно преобразовать 
изображение app/gen_view/snowman.svg в 
формат Android View. 

Для этого нужно:

1. Преобразовать его в формат Android Vector Drawable на сайте http://inloop.github.io/svg2android/, выбрав опции «Import IDs from SVG as name», «Remove empty groups without attributes», «Bake transforms into path (experimental)»
2. Поместить результат в файл app/gen_view/image.xml
3. Выполнить скрипт на Python 3
app/gen_view/convert.py
