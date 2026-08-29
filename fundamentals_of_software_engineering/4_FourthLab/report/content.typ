= Текст задания

#image("assets/image.png")

= Листинг MBean'ов

#figure(
    caption: [Интерфейс первого Mbean],
    raw(read("../web-lab-3/src/main/java/ru/astrosoup/weblab3/monitoring/HitCheckerMBean.java"), block: true, lang: "java")
)


#figure(
    caption: [Имплементация HitCheckerMBean],
    raw(read("../web-lab-3/src/main/java/ru/astrosoup/weblab3/monitoring/HitChecker.java"), block: true, lang: "java")
)

#figure(
    caption: [Интерфейс второго Mbean],
    raw(read("../web-lab-3/src/main/java/ru/astrosoup/weblab3/monitoring/MissedHitCalculatorMBean.java"), block: true, lang: "java")
)

#figure(
    caption: [Имплементация MissedHitCalculatorMBean],
    raw(read("../web-lab-3/src/main/java/ru/astrosoup/weblab3/monitoring/MissedHitCalculator.java"), block: true, lang: "java")
)

= Показания JConsole
Для снятия показаний JConsole используем команду `jconsole -J-Djava.class.path=$JAVA_HOME/lib/jconsole.jar:./jboss-client.jar` для запуска. Нам нужен `jboss-client.jar` так как сервер WildFly не поддерживает удалённое подключение по JMX без него. Далее подключаемся к сервису при помощи `service:jmx:remote+http://localhost:9990`, выбираем `MBeans` и видим наши MBean'ы, а также их атрибуты и операции.
#figure(
    caption: [Страница MBeans и пользовательские Mbean'ы],
    image("assets/mbeans_page.png")
)

#figure(
    caption: [Показания вызванного метода],
    image("assets/shown_value.png")
)

#figure(
    caption: [Уведомление от HitChecker],
    image("assets/notification.png")
)

#figure(
    caption: [Мониторинг загруженных классов],
    image("assets/classes_loaded.png")
)

= Показания VisualVM
Мы также должны передать в classpath visualVM `jboss-client.jar`, чтобы подключаться к WildFly. 

Для того чтобы VisualVM отображал атрибуты MBean'ов, необходимо чтобы методы доступа к ним не имели аргументов. Введем дополнительные агрегированные метрики по всем пользователям.

Графики показаний MBean'ов:

#figure(
    caption: [График для HitCheckerMBean],
    image("assets/chart1.png")
)
#figure(
    caption: [График для MissedHitCalculatorMBean],
    image("assets/chart2.png")
)

Чтобы найти класс занимающий наибольшее количество памяти в куче, перейдем в `Sampler -> Memory`, и найдем в поиске пользовательский модуль (в данном случае `ru.astrosoup.weblab3`), и увидим, что наибольшую память занимает класс `HitEntity`:

#figure(
    caption: [Пользовательский класс, занимающий наибольшую память в куче],
    image("assets/fat_class.png")
)

= Устранение проблем с производительностью в заданной программе

Запустив программу, и открыв ее процесс в visualVM, можем заметить, что куча заполняется, а сборщик мусора не может освободить память. 
#figure(
    caption: [Показания VisualVM при запуске программы],
    image("assets/monitoring_supplied.png")
)

Такое поведение свидетельствует об утечке памяти.


Уменьшим кучу до 32 мб и запустим программу, чтобы вызвать `OutOfMemoryError` и подтвердить утечку:
```
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.base/java.util.concurrent.ConcurrentHashMap.transfer(ConcurrentHashMap.java:2507)
	at java.base/java.util.concurrent.ConcurrentHashMap.addCount(ConcurrentHashMap.java:2354)
	at java.base/java.util.concurrent.ConcurrentHashMap.putVal(ConcurrentHashMap.java:1075)
	at java.base/java.util.concurrent.ConcurrentHashMap.put(ConcurrentHashMap.java:1006)
	at java.base/java.util.Properties.put(Properties.java:1348)
	at java.base/java.util.Properties.load0(Properties.java:460)
	at java.base/java.util.Properties.load(Properties.java:385)
	at java.base/java.util.PropertyResourceBundle.<init>(PropertyResourceBundle.java:192)
	at java.base/java.util.PropertyResourceBundle.<init>(PropertyResourceBundle.java:169)
	at java.base/java.util.ResourceBundle$Control.newBundle0(ResourceBundle.java:3259)
	at java.base/java.util.ResourceBundle$Control.newBundle(ResourceBundle.java:3165)
	at java.base/java.util.ResourceBundle.loadBundle(ResourceBundle.java:1998)
	at java.base/java.util.ResourceBundle.findBundle(ResourceBundle.java:1784)
	at java.base/java.util.ResourceBundle.findBundle(ResourceBundle.java:1736)
	at java.base/java.util.ResourceBundle.findBundle(ResourceBundle.java:1736)
	at java.base/java.util.ResourceBundle.getBundleImpl(ResourceBundle.java:1670)
	at java.base/java.util.ResourceBundle.getBundleImpl(ResourceBundle.java:1600)
	at java.base/java.util.ResourceBundle.getBundleImpl(ResourceBundle.java:1555)
	at java.base/java.util.ResourceBundle.getBundle(ResourceBundle.java:935)
	at org.mozilla.javascript.Context.getMessage(Context.java:1945)
	at org.mozilla.javascript.Context.getMessage1(Context.java:1907)
	at org.mozilla.javascript.ScriptRuntime.getMessage1(ScriptRuntime.java:1977)
	at org.mozilla.javascript.NativeGlobal.typeError1(NativeGlobal.java:567)
	at org.mozilla.javascript.ScriptRuntime.call(ScriptRuntime.java:1182)
	at org.mozilla.javascript.gen.c107929.call(httpunit:0)
	at org.mozilla.javascript.gen.c107929.exec(httpunit)
	at org.mozilla.javascript.Context.evaluateReader(Context.java:820)
	at org.mozilla.javascript.Context.evaluateString(Context.java:784)
	at com.meterware.httpunit.javascript.JavaScript$JavaScriptEngine.executeScript(JavaScript.java:132)
	at com.meterware.httpunit.scripting.ScriptableDelegate.runScript(ScriptableDelegate.java:65)
	at com.meterware.httpunit.parsing.ScriptFilter.getTranslatedScript(ScriptFilter.java:151)
	at com.meterware.httpunit.parsing.ScriptFilter.endElement(ScriptFilter.java:131)
```

из дампа кучи можем заметить что в классе `JavaScript` есть статическое поле `_errorMessages`, в котором находится подозрительно много объектов.

#figure(
    caption: [Интересующее нас поле в дампе кучи],
    image("assets/heap_dump.png")
)

Более подробно рассмотрев класс заметим что это поле никогда не очищается, и при большом количестве ошибок может привести к заполнению кучи. Решением проблемы будет очистка после каждого вызова.

После добавления очистки в основной цикл Main класса, мы можем наблюдать нормальное поведение кучи, и отсутствие `OutOfMemoryError`:
#figure(
    caption: [Показания после исправления],
    image("assets/fixed.png")
)


= Вывод
В ходе выполнения лабораторной работы я познакомился с инструментами мониторинга JConsole и VisualVM, написал Mbean'ы для мониторинга и устранил утечку памяти в предложенной программе.