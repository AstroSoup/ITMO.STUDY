#!usr/bin/bash
# создание дерева каталогов в соответствии с ТЗ
mkdir lab0
touch lab0/ampharos4
mkdir lab0/bisharp6
mkdir lab0/ferroseed5
touch lab0/ferrothorn5
mkdir lab0/gible9
touch lab0/vanilluxe2
mkdir lab0/bisharp6/totodile
touch lab0/bisharp6/elgyem
touch lab0/bisharp6/breloom
mkdir lab0/ferroseed5/slakoth
touch lab0/ferroseed5/amoonguss
touch lab0/ferroseed5/rattata
mkdir lab0/gible9/poochyena
touch lab0/gible9/dewott
touch lab0/gible9/dusclops
# заполнение файлов в соответствии с ТЗ
echo $'Способности Thunder Wave Thundershock Cotton Spore Charge\nTake Down Electro Ball Confuse Ray Thunderpunch Power Gem Discharge\nCotton Guard Signal Beam Light Screen Thunder' > lab0/ampharos4
echo $'Развитые\nспособности Analytic' > lab0/bisharp6/elgyem
echo $'weigth=86.4 height=47.0 atk=13\ndef=8' > lab0/bisharp6/breloom
echo $'Развитые способности\nRegenerator' > lab0/ferroseed5/amoonguss
echo $'Способности Tackle Tail Whip Quick Attack Focus\nEnergy Bite Pursuit Hyper Fang Sucker Punch Crunch Assurance Super\nFang Double-Edge Endeavor' > lab0/ferroseed5/rattata
echo $'Способности Overgrow\nUnbreakable Iron Barbs Battle Armor' > lab0/ferrothorn5
echo $'satk=8 sdef=6\nspd=6' > lab0/gible9/dewott
echo $'satk=6 sdef=13 spd=4' > lab0/gible9/dusclops
echo $'Ходы Icy Wind Iron\nDefence Magic Coat Magnet Rise Signal Beam Sleep Talk Snore Uproar\nWeather Ball⧧' > lab0/vanilluxe2
# установка прав на файлы и каталоги в соответствии с ТЗ
chmod u-w lab0/ampharos4
chmod 573 lab0/bisharp6
chmod 537 lab0/bisharp6/totodile
chmod u-w lab0/bisharp6/elgyem
chmod 404 lab0/bisharp6/breloom
chmod 375 lab0/ferroseed5
chmod 751 lab0/ferroseed5/slakoth
chmod g+w lab0/ferroseed5/amoonguss
chmod go-r lab0/ferroseed5/rattata
chmod u-w lab0/ferroseed5/rattata
chmod a-rwx lab0/ferrothorn5
chmod o+rw lab0/ferrothorn5
chmod 576 lab0/gible9
chmod 711 lab0/gible9/poochyena
chmod 404 lab0/gible9/dewott
chmod u-rw lab0/gible9/dusclops
chmod ug-rw lab0/vanilluxe2
# объединение файлов lab0/gible9/dusclops, lab0/ferroseed5/rattata, в новый файл lab0/ampharos4_36
chmod 700 lab0/gible9/dusclops
cat lab0/gible9/dusclops lab0/ferroseed5/rattata > lab0/ampharos4_36
chmod 044 lab0/gible9/dusclops
# создание символической ссылки на ferroseed5
cd lab0
ln -s ferroseed5 Copy_78
cd ..
# копирование файла
cd lab0
chmod 700 gible9
chmod u+r ferrothorn5
cp ferrothorn5 gible9/poochyena/ferrothorn5
chmod 576 gible9
chmod 006 ferrothorn5
cd ..
# создание символической ссылки на vanilluxe2
cd lab0
chmod 700 vanilluxe2
chmod 700 gible9
ln -s ../vanilluxe2 gible9/dusclopsvanilluxe
chmod 004 vanilluxe2
chmod 576 gible9
cd ..
# создание жесткой ссылки на ampharos4
cd lab0
chmod 700 bisharp6
ln ampharos4 bisharp6/breloomampharos
chmod 573 bisharp6
cd ..
# копирование содержимого файла ferrothorn5 в rattataferrothorn
chmod u+r lab0/ferrothorn5
cat lab0/ferrothorn5 > lab0/ferroseed5/rattataferrothorn
chmod 006 lab0/ferrothorn5
# копирование директории 
chmod -R 700 lab0/gible9
cp -R lab0/gible9 lab0/gible9/poochyena
chmod 576 lab0/gible9
chmod 644 lab0/gible9/dusclopsvanilluxe
chmod 711 lab0/gible9/poochyena
chmod 404 lab0/gible9/dewott
chmod 044 lab0/gible9/dusclops
# рекурсивный подсчет количества символов в файлах чье имя начинается на d
#for line in $(ls lab0/d* lab0/*/d* ~lab0/*/*/d* 2> /dev/null | grep -v ":$");do wc -m "$line"; done 2>&1 | sort -nr
#ls -dp lab0/* lab0/*/* lab0/*/*/* | grep -v "/$" | grep "/d" | xargs wc -m | sort -nr | tail -2
echo "Вывод 1:"
wc -m $(ls -dp lab0/* lab0/*/* lab0/*/*/* | grep -v "/$" | grep "/d") | sort -nr | tail -2

# вывод 2 последних файлов из списка всех файлов лабы оканчивающихся на m с атрибутами, отсортированного по количеству жестких ссылок
echo "Вывод 2"
ls -l -R 2> /dev/null 2> >(grep 'Permission denied' > /dev/null) | grep "m$" | sort -k2,2 -n -r | tail -n 2

# вывод содержимого файлов название которых начинается на f отсортированное в прямом лексикографическом порядке
echo "Вывод 3"
cat lab0/f* lab0/*/f* 2> >(grep 'Permission denied' >&1) | sort

#Рекурсивно вывести содержимое файлов с номерами строк из директории lab0, имя которых начинается на 'd', строки отсортировать по имени z->a, добавить вывод ошибок доступа в стандартный поток вывода 
#for line in $(ls lab0/d* lab0/*/d* ~lab0/*/*/d* 2> /dev/null | grep -v ":$");do cat -n "$line"; done 2> >(grep 'Permission denied' >&1) 2> >(grep -v 'Permission denied' >&2) | sort -r
#ls -dp lab0/* lab0/*/* lab0/*/*/* | grep -v "/$" | grep "/d" | xargs cat -n | sort -r
echo "Вывод 4"
cat -n $(ls -dp lab0/* lab0/*/* lab0/*/*/* | grep -v "/$" | grep "/d") | sort -r

#Вывести три последних элемента рекурсивного списка имен и атрибутов файлов в директории lab0, начинающихся на символ 'a', список отсортировать по возрастанию даты модификации файла, ошибки доступа перенаправить в файл в директории /tmp
echo "Вывод 5"
ls -dpltr lab0/* lab0/*/* lab0/*/*/* 2> >(grep "Permission denied" >/tmp/mistake) | grep "/a" | cat -n
cat /tmp/mistake
#Вывести рекурсивно список имен и атрибутов файлов в директории lab0, содержащих строку "to", список отсортировать по убыванию даты доступа к файлу, ошибки доступа перенаправить в файл в директории /tmp
echo "Вывод 6"
ls -dplt lab0/* lab0/*/* lab0/*/*/* | grep "to" 2> >(grep "Permission denied" >/tmp/mistake)
cat /tmp/mistake
# Удаление файлов в соответствии с ТЗ
chmod u+w lab0/ampharos4
rm lab0/ampharos4
chmod 700 lab0/bisharp6
chmod 700 lab0/bisharp6/breloom
rm lab0/bisharp6/breloom
chmod 700 lab0/gible9
rm lab0/gible9/dusclopsvanillu*
rm lab0/bisharp6/breloomamphar*
chmod -R 700 lab0/gible9
rm -r lab0/gible9
rm -r lab0/ferroseed5/slakoth
