= Текст задания

#image("assets/image.png")


+ Используем JUnit 5
+ Сценарии сборки необходимо реализовать на gradle. При этом,использовать встроенные таски gradle (например, реализовать свой build через команду build из коробки и т.д.) запрещено.
+ Тестовые сценарии необходимо реализовать для компонента, проверяющего попадание в область

= Листинг билд-файла
#raw(lang: "java",read("/web-lab-3/build.gradle") ,block: true)

= Пример исполнения цели alt
```java
package ru.astrosoup.weblab3.DTOs.authorisation;

import lombok.Data;

@Data
public class WoginDto {
    private String usewname;
    private String passwowd;
}

```

= Листинг тестов
#raw(lang: "java",read("/web-lab-3/src/test/java/ru/astrosoup/weblab3/services/hit/HitServiceTest.java") ,block: true)

= Вывод
В ходе лабораторной работы я преумножил свои знания в написании билд скриптов для системы сборки gradle, написал модульные тесты на JUnit 5 с использованием Mockito, разобрался с внутренней работой gradle.


#image("assets/meme.png")